package com.jwst.manage.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jwst.common.util.DBUtils;
import com.jwst.manage.model.ApplicationKey;
import com.jwst.manage.util.EncryptionUtil;

/**
 * DAO that returns public / private key for the system generated private key
 */
public class PrivateKeyDB {

    private static Logger log = LoggerFactory.getLogger(PrivateKeyDB.class);

    /**
     * returns public private key for application
     * @return app key values
     */
    public static ApplicationKey getApplicationKey() {

        ApplicationKey appKey = null;

        Connection con = null;

        try {
            con = DBUtils.getConn();

            PreparedStatement stmt = con.prepareStatement("select * from  application_key");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                appKey= new ApplicationKey();
                appKey.setId(rs.getLong("id"));
                appKey.setPassphrase(EncryptionUtil.decrypt(rs.getString("passphrase")));
                appKey.setPrivateKey(EncryptionUtil.decrypt(rs.getString("private_key")));
                appKey.setPublicKey(rs.getString("public_key"));

            }
            DBUtils.closeRs(rs);
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        DBUtils.closeConn(con);


        return appKey;
    }





}
