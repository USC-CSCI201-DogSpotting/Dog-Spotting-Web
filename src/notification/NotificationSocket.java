package notification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class NotificationSocket {
	private static Map<String, String> sockets = new HashMap();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		System.out.println("This is the session id " + session.getId());
	}
	
	@OnMessage
	public void OnMessage(String message, Session session) {
		System.out.println(message);
		/*try {
			
		}catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}*/
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
	}
	
	/*@OnError
	public void error(Session session) {
		System.out.println("Error!");
	}*/
}
