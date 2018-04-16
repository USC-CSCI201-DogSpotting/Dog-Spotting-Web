package csci201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notification.NotificationSocket;

/**
 * Servlet implementation class Like
 */
@WebServlet("/Like")
public class Like extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/* database starts */
		// variables
		int postID = Integer.parseInt(request.getParameter("postID"));
		String username = (String)request.getSession().getAttribute("currentusername");
		boolean isLike = Boolean.parseBoolean(request.getParameter("isLike"));

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			// find userID
			ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			int userID = 0;
			while (rs.next()) { // get userID
				userID = rs.getInt("userID");
			}
			ps.close();
			rs.close();

			if(!isLike) { // add like
				// check if like relationship exists
				ps = conn.prepareStatement("SELECT likesID FROM Likes WHERE userID = ? AND postID = ?");
				System.out.println("Add Like");
				ps.setLong(1, userID);
				ps.setLong(2, postID);
				rs = ps.executeQuery();
				System.out.println("Execute Query");
				int likesID = 0;
				if (rs.next()) { // re-validate the like
					likesID = rs.getInt("likesID");

					ps.close();
					ps = conn.prepareStatement("UPDATE Likes SET valid = 1 WHERE likesID = ?");
					ps.setLong(1, likesID);
					ps.executeUpdate();
					ps.close();
				}else { // insert new like
					ps.close();
					ps = conn.prepareStatement("INSERT INTO Likes (userID, postID, valid) VALUES (?, ?, 1)");
					ps.setLong(1, userID);
					ps.setLong(2, postID);
					ps.executeUpdate();
					ps.close();
					// increase the like for the postID
					ps = conn.prepareStatement(
							"UPDATE Post " +
							"SET dailylike = dailylike + 1, " +
							"monthlylike = monthlylike + 1, " +
							"yearlylike = yearlylike + 1 " +
							"WHERE postID = ?");
					ps.setInt(1, postID);
					ps.executeUpdate();
					ps.close();
				}
				// get username of the post to push notification
				ps = conn.prepareStatement("SELECT u.username FROM User u, Post p WHERE u.userID = p.userID AND p.postID = ?");
				ps.setInt(1, postID);
				rs = ps.executeQuery();
				String postUsername = "";
				while(rs.next()) {
					postUsername = rs.getString("username");
				}
				NotificationSocket.addUserNotification(postUsername, username + " liked your post!");
			}else { // invalidate like
				ps.close();
				ps = conn.prepareStatement("UPDATE Likes SET valid = 0 WHERE userID = ? AND postID = ?");
				ps.setLong(1, userID);
				ps.setLong(2, postID);
				ps.executeUpdate();
				ps.close();
			}
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
		/* database ends */
		
		// check if ranks are up to date
		new RankUpdate();
	}

}