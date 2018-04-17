package csci201;

public class User {
	private int userID;
	private String username;
	private String userPicURL;
	
	public User(String username, String userPicURL) {
		this.username = username;
		this.userPicURL = userPicURL;
	}

	public User(int userID, String username, String userPicURL) {
		this.userID = userID;
		this.username = username;
		this.userPicURL = userPicURL;
	}

	public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public String getUserPicURL() {
		return userPicURL;
	}
}
