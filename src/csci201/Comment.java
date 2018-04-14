package csci201;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comment {
	int commentID;
	List<Comment> comments;
	String username;
	String content;
	public Comment(int commentID, String username, String content) {
		this.commentID = commentID;
		this.username = username;
		this.content = content;
		comments = new ArrayList<Comment>();
	}
	public String getUsername() {
		return username;
	}
	public String getContent() {
		return content;
	}
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void getCommentOnThis() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			ps = conn.prepareStatement("SELECT c.commentID, u.username, c.content FROM Comment c, User u " + 
					"WHERE refcommentID=? AND c.userID = u.userID");
			ps.setInt(1, this.commentID); // set first variable in prepared statement
			rs = ps.executeQuery();
			while(rs.next()) {
				Comment tempComment = new Comment(rs.getInt("commentID"), rs.getString("username"), rs.getString("content"));
				tempComment.getCommentOnThis();
				this.addComment(tempComment);
			}
		} catch(SQLException sqle) {
			System.out.println("SQLException in getting comments on comment: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException in getting comments on comment: " + cnfe.getMessage());
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
			} catch(SQLException sqle) {
				System.out.println("SQLException in closing getCommentOnThis: " + sqle.getMessage());
			}
		}
	}
}
