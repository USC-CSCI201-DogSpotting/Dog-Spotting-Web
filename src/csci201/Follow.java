package csci201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Follow
 */
@WebServlet("/Follow")
public class Follow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/* database starts */
		// variables
		String followingUsername = request.getParameter("username"); // username of the user being followed
		String followerUsername = (String)request.getSession().getAttribute("currentusername"); // username of the user following the other
		boolean isFollow = Boolean.parseBoolean(request.getParameter("isFollow"));

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			// find following userID
			ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
			ps.setString(1, followingUsername);
			rs = ps.executeQuery();
			int followingUserID = 0;
			while (rs.next()) { // get userID
				followingUserID = rs.getInt("userID");
			}
			ps.close();
			rs.close();
			
			// find following userID
			ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
			ps.setString(1, followerUsername);
			rs = ps.executeQuery();
			int followerUserID = 0;
			while (rs.next()) { // get userID
				followerUserID = rs.getInt("userID");
			}
			
			if(!isFollow) { // add follow
				// check if follow relationship exists
				System.out.println("follower: " + followerUserID + " following: " + followingUserID);
				ps = conn.prepareStatement("SELECT followID FROM Follow WHERE followingID = ? AND followerID = ?");
				ps.setLong(1, followingUserID);
				ps.setLong(2, followerUserID);
				rs = ps.executeQuery();
				if (rs.next()) { // re-validate the follow
					ps = conn.prepareStatement("UPDATE Follow SET valid = 1 WHERE followID = ?");
					System.out.println("followID: " + rs.getInt("followID"));
					ps.setLong(1, rs.getInt("followID"));
					ps.executeUpdate();
				}else { // insert new follow
					ps = conn.prepareStatement("INSERT INTO Follow (followingID, followerID, valid) VALUES (?, ?, 1)");
					ps.setLong(1, followingUserID);
					ps.setLong(2, followerUserID);
					ps.executeUpdate();
				}
			}else { // invalidate follow
				ps = conn.prepareStatement("UPDATE Follow SET valid = 0 WHERE followingID = ? AND followerID = ?");
				ps.setLong(1, followingUserID);
				ps.setLong(2, followerUserID);
				ps.executeUpdate();
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
				if (st != null) {
					st.close();
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
	}

}
