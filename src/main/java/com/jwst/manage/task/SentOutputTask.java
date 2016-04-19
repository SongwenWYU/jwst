package com.jwst.manage.task;

import java.sql.Connection;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jwst.common.util.DBUtils;
import com.jwst.manage.model.SessionOutput;
import com.jwst.manage.util.SessionOutputUtil;

/**
 * class to send output to web socket client
 */
public class SentOutputTask implements Runnable {

    private static Logger log = LoggerFactory.getLogger(SentOutputTask.class);

    Session session;
    Long sessionId;

    public SentOutputTask(Long sessionId, Session session) {
        this.sessionId = sessionId;
        this.session = session;
    }

    public void run() {

        Gson gson = new Gson();

        Connection con = DBUtils.getConn();
        while (session.isOpen()) {
            List<SessionOutput> outputList = SessionOutputUtil.getOutput(con, sessionId);
            try {
                if (outputList != null && !outputList.isEmpty()) {
                    String json = gson.toJson(outputList);
                    //send json to session
                    this.session.getBasicRemote().sendText(json);
                }
                Thread.sleep(50);
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }
        }
        DBUtils.closeConn(con);
    }
}
