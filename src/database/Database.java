package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import csci201.User;

public class Database {
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	public synchronized static User getUser(String username) {
		String userPicURL = "";
		int userID = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			// find user info
			ps = conn.prepareStatement("SELECT userID, picture FROM User WHERE username=?");
			ps.setString(1, username); // set first variable in prepared statement
			rs = ps.executeQuery();
			while (rs.next()) { // get userID
				userID = rs.getInt("userID");
				userPicURL = rs.getString("picture");
			}
			ps.close();
			rs.close();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return new User(userID, username, userPicURL);
	}
}
