package test;

import java.io.IOException;
import java.util.Date;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.jwst.manage.socket.GetHttpSessionConfigurator;

@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
public class TestWebsocket {
	private Session session;  
    private static final Logger sysLogger = Logger.getLogger(TestWebsocket.class.getName());
    
    @OnOpen
    public void open(Session session, EndpointConfig config){
    	this.session = session;
    	sysLogger.info("*** WebSocket opened from sessionId " + session.getId());  
    }
    
    @OnClose
    public void close(){
    	sysLogger.info("*** WebSocket closed from sessionId " + this.session.getId());
    	
    }
    
    @OnMessage
    public void onMessage(String message){
    	sysLogger.info("*** WebSocket Received from sessionId " + this.session.getId() + ": " + message);  
    	try {
			session.getBasicRemote().sendText(new Date().toString() +" "+ this.session.getId()+" say: "+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
