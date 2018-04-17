package notification;

import csci201.User;
import database.Database;

public class Notification {
	private User user;
	private String message;
	
	public Notification(String username, String message) {
		user = Database.getUser(username);
		this.message = message;
	}

	public User getUser() {
		return user;
	}
	
	public String getUsername() {
		return user.getUsername();
	}
	
	public String getUserPicURL() {
		return user.getUserPicURL();
	}

	public String getMessage() {
		return message;
	}
}
