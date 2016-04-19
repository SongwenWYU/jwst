package com.jwst.common.db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.common.jdbc.ScriptRunner;
import com.jwst.common.util.AppConfig;
import com.jwst.common.util.DBUtils;
import com.jwst.manage.util.EncryptionUtil;
import com.jwst.manage.util.RefreshAuthKeyUtil;
import com.jwst.manage.util.SSHUtil;

public class DBInit {

	private static Logger log = LoggerFactory.getLogger(DBUtils.class);
	
	public static void init(){
		Connection connection = null;
		Statement statement = null;
		//check if reset ssh application key is set
		boolean resetSSHKey = "true".equals(AppConfig.getProperty("resetApplicationSSHKey"));
		try {
			connection = DBUtils.getConn();
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from information_schema.tables where upper(table_name) = 'system' and table_schema='PUBLIC'");
			if (rs == null || !rs.next()) {
				resetSSHKey = true;
				ScriptRunner runner = new ScriptRunner(connection, false, false);
			    runner.setErrorLogWriter(null);
			    runner.setLogWriter(null);
			    runner.runScript(new FileReader(new File(DBUtils.class.getClassLoader().getResource("db").getPath()+"/j4t.sql")));
			}
			DBUtils.closeRs(rs);
			//if reset ssh application key then generate new key
			if (resetSSHKey) {

				//delete old key entry
				PreparedStatement pStmt = connection.prepareStatement("delete from application_key");
				pStmt.execute();
				DBUtils.closeStmt(pStmt);

				//generate new key and insert passphrase
				System.out.println("Setting Default SSH public/private key pair");

				//generate application pub/pvt key and get values
				String passphrase = SSHUtil.keyGen();
				String publicKey = SSHUtil.getPublicKey();
				String privateKey = SSHUtil.getPrivateKey();

				//insert new keys
				pStmt = connection.prepareStatement("insert into application_key (public_key, private_key, passphrase) values(?,?,?)");
				pStmt.setString(1, publicKey);
				pStmt.setString(2, EncryptionUtil.encrypt(privateKey));
				pStmt.setString(3, EncryptionUtil.encrypt(passphrase));
				pStmt.execute();
				DBUtils.closeStmt(pStmt);

				System.out.println("Default Generated Global Public Key:");
				System.out.println(publicKey);

				passphrase = null;
				publicKey = null;
				privateKey = null;

				//set config to default
				AppConfig.updateProperty("publicKey", "");
				AppConfig.updateProperty("privateKey", "");
				AppConfig.updateProperty("defaultSSHPassphrase", "${randomPassphrase}");
				
				//set to false
				AppConfig.updateProperty("resetApplicationSSHKey", "false");

			}
			//delete ssh keys
			SSHUtil.deletePvtGenSSHKey();
		} catch (Exception ex) {
            log.error(ex.toString(), ex);
		}

		DBUtils.closeStmt(statement);
		DBUtils.closeConn(connection);

		RefreshAuthKeyUtil.startRefreshAllSystemsTimerTask();
		log.info("init end!");
	}
	public static void main(String[] args) {
		DBInit.init();
	}
	
}
