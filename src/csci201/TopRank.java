package csci201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class TopRank
 */
@WebServlet("/TopRank")
public class TopRank extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/* database starts */
		// variables
		int rankSelection = Integer.parseInt(request.getParameter("rank"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		// 0: daily, 1: weekly, 2: monthly
		List<Post> posts = new ArrayList<Post>();

		// check if ranks are up to date
		new RankUpdate();
		
		// load posts
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement st2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			if(rankSelection == 0) {
				ps = conn.prepareStatement("SELECT * FROM DailyRank r, Post p, User u " + 
						"WHERE r.postID = p.postID AND p.userID = u.userID " + " LIMIT " + limit);
			}else if(rankSelection == 1) {
				ps = conn.prepareStatement("SELECT * FROM WeeklyRank r, Post p, User u " +
						"WHERE r.postID = p.postID AND p.userID = u.userID " + " LIMIT " + limit);
			}else{
				ps = conn.prepareStatement("SELECT * FROM MonthlyRank r, Post p, User u " +
						"WHERE r.postID = p.postID AND p.userID = u.userID " + " LIMIT " + limit);
			}
			rs = ps.executeQuery();
			while (rs.next()) { // load ranked posts
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
				st2 = conn.createStatement();
				ps2 = conn.prepareStatement("SELECT u.username, c.content FROM Comment c, User u " + 
						"WHERE postID=? AND c.userID = u.userID");
				ps2.setLong(1, postID); // set first variable in prepared statement
				rs2 = ps2.executeQuery();
				while(rs2.next()) {
					Comment tempComment = new Comment(rs2.getString("username"), rs2.getString("content"));
					comments.add(tempComment);
				}
				Post tempPost = new Post(postID, rs.getString("image"), rs.getString("username"), rs.getString("description"), tags, comments);
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
			try {
				if (rs2 != null) {
					rs2.close();
				}
				if (st2 != null) {
					st2.close();
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
