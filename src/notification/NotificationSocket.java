package notification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class NotificationSocket extends Thread {
	private static Map<String, Vector<Session>> sessions = new HashMap<String ,Vector<Session>> ();
	private static Map<String, Stack<String>> notifications = new HashMap<String, Stack<String>>();

	private String username;
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		System.out.println("This is the session id " + session.getId());
	}
	
	@OnMessage
	public void OnMessage(String message, Session session) { // create vector of sessions for the user with username
		System.out.println("getName: " + message);
		this.username = message;
		System.out.println("set this username: " + username);
		Vector<Session> userSessions = sessions.get(this.username);
		if(userSessions != null) { // already had sessions with the same username
			System.out.println("username existed.");
			userSessions.add(session);
			System.out.println("Added session vector for '" + username + "' with session ID " + session.getId());
		}else { // the first session with the username
			userSessions = new Vector<Session>();
			userSessions.add(session);
			sessions.put(this.username, userSessions);
			System.out.println("Created session vector for '" + username + "' with session ID " + session.getId());
		}
		// set num of notifications existed
		Stack<String> userNotifications = notifications.get(username);
		try {
			if(userNotifications != null) {
				session.getBasicRemote().sendText("" + userNotifications.size());
			}else {
				session.getBasicRemote().sendText("0");
			}
		} catch (IOException ioe) {
			System.out.println("IOException in getting initial num of notifications: " + ioe.getMessage());
		}
	}
	
	@OnClose
	public void close(Session session) { // remove the session for the web page, remove vector if no session exists
		Vector<Session> userSessions = sessions.get(this.username);
		userSessions.remove(session);
		System.out.println("Disconnecting by session " + session.getId());
		if(userSessions.size() == 0) {
			sessions.remove(this.username);
			System.out.println(username + " has logged out for all pages!");
		}
	}
	
	public static Stack<String> getUserNotifications(String username) {
		Stack<String> retVal = notifications.get(username);
		notifications.remove(username);
		broadcastNewNotification(username, 0);
		return retVal;
	}
	
	public static void addUserNotification(String username, String notification) {
		Stack<String> userNotifications = notifications.get(username);
		if(userNotifications != null) { // notifications for the user existed
			System.out.println("Username existed.");
			userNotifications.add(notification);
			System.out.println("Added notification stack for '" + username + "' with message: " + notification);
		}else { // create notification stack if not existed for the user
			userNotifications = new Stack<String>();
			userNotifications.add(notification);
			notifications.put(username, userNotifications);
			System.out.println("Created notification stack for '" + username + "' with message: " + notification);
		}
		broadcastNewNotification(username, userNotifications.size());
	}
	
	private static void broadcastNewNotification(String username, int numOfNotifications) {
		Vector<Session> userSessions = sessions.get(username);
		if(userSessions != null) {
			try {
				for(Session s: sessions.get(username)) {
					s.getBasicRemote().sendText("" + numOfNotifications);
				}
			} catch (IOException ioe) {
				System.out.println("IOException in braodcastNewNotification: " + ioe.getMessage());
			}
		}
	}
	/*
	@OnError
	public void error(Session session) {
		System.out.println("WebSocket Error!");
	}*/
}
