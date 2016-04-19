package com.jwst.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtils {

    private static Logger log = LoggerFactory.getLogger(DBUtils.class);

    /**
     * returns DB connection
     *
     * @return DB connection
     */
    public static Connection getConn() {
        Connection con = null;
        try{
            con=DBPool.getDataSource().getConnection();

        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
        return con;
    }

    /**
     * close DB connection
     *
     * @param con DB connection
     */
    public static void closeConn(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
            con = null;
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }

    /**
     * Close DB statement
     *
     * @param stmt DB statement
     */
    public static void closeStmt(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            stmt = null;
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }

    }

    /**
     * close DB result set
     *
     * @param rs DB result set
     */
    public static void closeRs(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }
}
