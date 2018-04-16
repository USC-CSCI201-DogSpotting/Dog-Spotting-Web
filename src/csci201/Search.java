package csci201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
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
		String tag = request.getParameter("search");
		int limit = Integer.parseInt(request.getParameter("limit"));
		List<Post> posts = new ArrayList<Post>();
		
		System.out.println(tag);

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
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
				ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
				ps.setString(1, username); // set first variable in prepared statement
				rs = ps.executeQuery();
				while (rs.next()) { // get userID
					userID = rs.getInt("userID");
				}
				ps.close();
				rs.close();
			}
			// get search results
			ps = conn.prepareStatement("SELECT u.picture, u.username, p.userID, p.postID, p.image, p.description, p.tag1, p.tag2, p.tag3, p.tag4, p.tag5 " +
					"FROM Post p, User u " +
					"WHERE p.userID = u.userID " +
					"AND (p.tag1 = ? " +
					"OR p.tag2 = ? " +
					"OR p.tag3 = ? " +
					"OR p.tag4 = ? " +
					"OR p.tag5 = ?) " +
					"LIMIT " + limit);
			ps.setString(1, tag);
			ps.setString(2, tag);
			ps.setString(3, tag);
			ps.setString(4, tag);
			ps.setString(5, tag);
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
			while (rs.next()) { // add in posts
				// load tags
				List<String> tags = new ArrayList<String>();
				if(rs.getString("tag1") != null) { tags.add(rs.getString("tag1")); }
				if(rs.getString("tag2") != null) { tags.add(rs.getString("tag2")); }
				if(rs.getString("tag3") != null) { tags.add(rs.getString("tag3")); }
				if(rs.getString("tag4") != null) { tags.add(rs.getString("tag4")); }
				if(rs.getString("tag5") != null) { tags.add(rs.getString("tag5")); }
				// load comments
				int postID = rs.getInt("postID");
				List<Comment> comments = new ArrayList<Comment>();
				ps2.setLong(1, postID); // set first variable in prepared statement
				rs2 = ps2.executeQuery();
				while(rs2.next()) {
					Comment tempComment = new Comment(rs2.getInt("commentID"), rs2.getString("username"), rs2.getString("content"));
					comments.add(tempComment);
				}
				Post tempPost = new Post(postID, rs.getString("image"), rs.getString("username"), rs.getString("picture"), rs.getString("description"), tags, comments);
				// check like and comment if loggedin
				if(isLoggedin) {
					ps3.setInt(2, postID);
					rs3 = ps3.executeQuery();
					if(rs3.next()) {
						tempPost.setIsLike(true);
					}
					ps3.close();
					int postUserID = rs.getInt("userID");
					ps4.setInt(2, postUserID);
					rs4 = ps4.executeQuery();
					if(rs4.next()) {
						tempPost.setIsFollow(true);
					}
					ps4.close();
				}
				posts.add(tempPost);
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
			try {
				if (rs2 != null) {
					rs2.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		/* database ends */
		
		/* output List<Post> posts */
		Gson gson = new Gson();
		String json = gson.toJson(posts);
//		System.out.println(limit + " " + posts.size());
//		System.out.println(json);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
}
