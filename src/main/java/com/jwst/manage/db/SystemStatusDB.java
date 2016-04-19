package com.jwst.manage.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jwst.common.util.DBUtils;
import com.jwst.manage.model.HostSystem;
import com.jwst.manage.model.SortedSet;

/**
 * DAO used to generate a list of public keys and systems associated
 * with them based on system profiles and users assigned to the profiles.
 */
public class SystemStatusDB {

    private static Logger log = LoggerFactory.getLogger(SystemStatusDB.class);


//    /**
//     * set the initial status for selected systems
//     *
//     * @param systemSelectIds systems ids to set initial status
//     * @param userId user id
//     * @param userType user type
//     */
//    public static void setInitialSystemStatus(List<Long> systemSelectIdse) {
//        Connection con = null;
//        try {
//            con = DBUtils.getConn();
//
////            //checks perms if to see if in assigned profiles
////            if (!Auth.MANAGER.equals(userType)) {
////                systemSelectIds = SystemDB.checkSystemPerms(con, systemSelectIds, userId);
////            }
////
////            //deletes all old systems
////            deleteAllSystemStatus(con, userId);
//            for (Long hostSystemId : systemSelectIds) {
//
//                HostSystem hostSystem= new HostSystem();
//                hostSystem.setId(hostSystemId);
//                hostSystem.setStatusCd(HostSystem.INITIAL_STATUS);
//
//                //insert new status
//                insertSystemStatus(con, hostSystem, userId);
//            }
//
//        } catch (Exception e) {
//            log.error(e.toString(), e);
//        }
//        DBUtils.closeConn(con);
//    }

    public static void setInitialSystemStatus(Long systemId) {
        Connection con = null;
        try {
            con = DBUtils.getConn();
            HostSystem hostSystem= new HostSystem();
            hostSystem.setSystemId(systemId);
            hostSystem.setStatusCd(HostSystem.INITIAL_STATUS);
                //insert new status
            insertSystemStatus(con, hostSystem);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        DBUtils.closeConn(con);
    }
    
    /**
     * deletes all records from status table for user
     *
     * @param con DB connection object
     * @param systemId system id
     */
    private static void deleteAllSystemStatus(Connection con, Long userId) {

        try {

            PreparedStatement stmt = con.prepareStatement("delete from status where user_id=?");
            stmt.setLong(1,userId);
            stmt.execute();
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }

    }


    /**
     * inserts into the status table to keep track of key placement status
     *
     * @param con                DB connection object
     * @param hostSystem systems for authorized_keys replacement
     * @param systemId system id
     * @return return status id
     */
    private static Long insertSystemStatus(Connection con, HostSystem hostSystem) {
    	Long id = null;
        try {

            PreparedStatement stmt = con.prepareStatement("insert into status (status_cd, system_id) values (?,?)",Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, hostSystem.getId());
            stmt.setString(2, hostSystem.getStatusCd());
            stmt.setLong(3, hostSystem.getSystemId());
            //stmt.getGeneratedKeys();
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return id;
    }

    /**
     * updates the status table to keep track of key placement status
     *
     * @param hostSystem systems for authorized_keys replacement
     * @param systemId system id
     */
    public static void updateSystemStatus(HostSystem hostSystem, Long systemId) {

        Connection con = null;
        try {
            con = DBUtils.getConn();

            updateSystemStatus(con, hostSystem, systemId);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        DBUtils.closeConn(con);

    }


    /**
     * updates the status table to keep track of key placement status
     *
     * @param con                DB connection
     * @param hostSystem systems for authorized_keys replacement
     * @param systemId system id
     */
    public static void updateSystemStatus(Connection con, HostSystem hostSystem, Long systemId) {

        try {

            PreparedStatement stmt = con.prepareStatement("update status set status_cd=? where id=? and system_id=?");
            stmt.setString(1, hostSystem.getStatusCd());
            stmt.setLong(2, hostSystem.getId());
            stmt.setLong(3, systemId);
            stmt.execute();
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }


    }


    /**
     * returns all key placement statuses
     * @param systemId system id
     */
    public static SortedSet getSortedSetStatus(Long systemId){

        SortedSet sortedSet= new SortedSet();

        sortedSet.setItemList(getAllSystemStatus(systemId));
        return sortedSet;

    }
    /**
     * returns all key placement statuses
     * @param systemId system id
     */
    public static List<HostSystem> getAllSystemStatus(Long systemId) {

        List<HostSystem> hostSystemList = new ArrayList<HostSystem>();
        Connection con = null;
        try {
            con = DBUtils.getConn();
            hostSystemList = getAllSystemStatus(con, systemId);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        DBUtils.closeConn(con);
        return hostSystemList;

    }

    /**
     * returns all key placement statuses
     *
     * @param con DB connection object
     * @param userId user id
     */
    private static List<HostSystem> getAllSystemStatus(Connection con, Long systemId) {

        List<HostSystem> hostSystemList = new ArrayList<HostSystem>();
        try {

            PreparedStatement stmt = con.prepareStatement("select * from status where system_id=? order by id asc");
            stmt.setLong(1, systemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HostSystem hostSystem = SystemDB.getSystem(con, rs.getLong("id"));
                hostSystem.setStatusCd(rs.getString("status_cd"));
                hostSystemList.add(hostSystem);
            }
            DBUtils.closeRs(rs);
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return hostSystemList;

    }


    /**
     * returns key placement status of system
     *
     * @param id id
     */
    public static HostSystem getSystemStatus(Long id) {

        Connection con = null;
        HostSystem hostSystem = null;
        try {
            con = DBUtils.getConn();

            PreparedStatement stmt = con.prepareStatement("select * from status where id=?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                hostSystem = SystemDB.getSystem(con, rs.getLong("id"));
                hostSystem.setStatusCd(rs.getString("status_cd"));
            }
            DBUtils.closeRs(rs);
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        DBUtils.closeConn(con);
        return hostSystem;


    }


    /**
     * returns the first system that authorized keys has not been tried
     *
     * @param systemId system id
     * @return hostSystem systems for authorized_keys replacement
     */
    public static HostSystem getNextPendingSystem(Long systemId) {

        HostSystem hostSystem = null;
        Connection con = null;
        try {
            con = DBUtils.getConn();
            PreparedStatement stmt = con.prepareStatement("select * from status where (status_cd like ? or status_cd like ? or status_cd like ?) and system_id=? order by id asc");
            stmt.setString(1,HostSystem.INITIAL_STATUS);
            stmt.setString(2,HostSystem.AUTH_FAIL_STATUS);
            stmt.setString(3,HostSystem.PUBLIC_KEY_FAIL_STATUS);
            stmt.setLong(4, systemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hostSystem = SystemDB.getSystem(con, rs.getLong("id"));
                hostSystem.setStatusCd(rs.getString("status_cd"));
            }
            DBUtils.closeRs(rs);
            DBUtils.closeStmt(stmt);

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        DBUtils.closeConn(con);
        return hostSystem;

    }








}





