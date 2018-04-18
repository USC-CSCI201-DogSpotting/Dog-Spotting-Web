package csci201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;

/**
 * Servlet implementation class PostPage
 */
@WebServlet("/PostPage")
public class PostPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/* database starts */
		// variables
		// for user
		boolean isLoggedin = false;
		String username = "a";
		// for post
		int postID = Integer.parseInt(request.getParameter("postID"));
		Post post = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			// get userID if loggedin
			int userID = 0;
			if(isLoggedin) {
				userID = Database.getUser(username).getUserID();
			}
			// get the post
			ps = conn.prepareStatement(
                    "SELECT * " + 
                    " FROM User u, Post p" + 
					" WHERE u.userID=p.userID AND postID=?");
			ps.setLong(1, postID); // set first variable in prepared statement
			rs = ps.executeQuery();
			// for each post
			ps2 = conn.prepareStatement("SELECT c.commentID, u.username, c.content FROM Comment c, User u " + 
					"WHERE postID=? AND c.userID = u.userID");
			if(isLoggedin) {
				ps3 = conn.prepareStatement("SELECT * FROM Likes WHERE userID = ? AND postID = ? AND valid = 1");
				ps3.setInt(1, userID);
				ps4 = conn.prepareStatement("Select * FROM Follow WHERE followerID = ? AND followingID = ?");
				ps4.setInt(1, userID);
			}
			while (rs.next()) {
				// load tags
				List<String> tags = new ArrayList<String>();
				if(rs.getString("tag1") != null) { tags.add(rs.getString("tag1")); }
				if(rs.getString("tag2") != null) { tags.add(rs.getString("tag2")); }
				if(rs.getString("tag3") != null) { tags.add(rs.getString("tag3")); }
				if(rs.getString("tag4") != null) { tags.add(rs.getString("tag4")); }
				if(rs.getString("tag5") != null) { tags.add(rs.getString("tag5")); }
				// load comments
				List<Comment> comments = new ArrayList<Comment>();
				ps2.setLong(1, postID); // set first variable in prepared statement
				rs2 = ps2.executeQuery();
				while(rs2.next()) {
					Comment tempComment = new Comment(rs2.getInt("commentID"), rs2.getString("username"), rs2.getString("content"));
					tempComment.getCommentOnThis();
					comments.add(tempComment);
				}
				post = new Post(postID, rs.getInt("lifelike"), rs.getString("image"), 
						rs.getString("username"), rs.getString("picture"), rs.getString("description"), tags, comments);
				// check like and comment if loggedin
				if(isLoggedin) {
					ps3.setInt(2, postID);
					rs3 = ps3.executeQuery();
					if(rs3.next()) {
						post.setIsLike(true);
					}
					ps3.close();
					int postUserID = rs.getInt("userID");
					ps4.setInt(2, postUserID);
					rs4 = ps4.executeQuery();
					if(rs4.next()) {
						post.setIsFollow(true);
					}
					ps4.close();
				}

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
		
		/* output Post post */
		
		String pageToForward = "/IndividualPost.jsp";
		request.setAttribute("post", post);
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(pageToForward);
		dispatch.forward(request, response);
	}

}