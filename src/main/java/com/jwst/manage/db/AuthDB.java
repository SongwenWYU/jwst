package com.jwst.manage.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jwst.common.util.DBUtils;
import com.jwst.manage.model.Auth;

public class AuthDB {
	 private static Logger log = LoggerFactory.getLogger(AuthDB.class);
	 
	 public static Auth queryAuthByid(Long id){
		 Auth auth = null;
		 Connection con = DBUtils.getConn();
		 try {
	            con = DBUtils.getConn();
	            PreparedStatement stmt = con.prepareStatement("select * from  auth where id=?");
	            stmt.setLong(1, id);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	            	auth = new Auth();
	            	auth.setId(rs.getLong("id"));
	            	auth.setAuthToken(rs.getString("authToken"));
	            	auth.setSessionId(rs.getLong("sessionId"));
	            	auth.setUserId(rs.getLong("userId"));
	            	auth.setTimeout(rs.getLong("timeout"));
	            }
	            DBUtils.closeRs(rs);
	            DBUtils.closeStmt(stmt);
	        } catch (Exception e) {
	            log.error(e.toString(), e);
	        }
	        DBUtils.closeConn(con);
	        return auth;
	 }
	 
	 public static void deleteAuthById(Long id){
		 Connection con = DBUtils.getConn();
		 try {
	            con = DBUtils.getConn();
	            PreparedStatement stmt = con.prepareStatement("delete from  auth where id=?");
	            stmt.setLong(1, id);
	            stmt.executeUpdate();
	            DBUtils.closeStmt(stmt);
	        } catch (Exception e) {
	            log.error(e.toString(), e);
	        }
	        DBUtils.closeConn(con);
	 }
	 
	 public static void deleteAllAuth(){
		 Connection con = DBUtils.getConn();
		 try {
	            con = DBUtils.getConn();
	            PreparedStatement stmt = con.prepareStatement("delete from auth");
	            stmt.executeUpdate();
	            DBUtils.closeStmt(stmt);
	        } catch (Exception e) {
	            log.error(e.toString(), e);
	        }
	        DBUtils.closeConn(con);
	 }
}
