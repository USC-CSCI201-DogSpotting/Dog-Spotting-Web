package csci201;

import java.util.ArrayList;
import java.util.List;

public class Comment {
	List<Comment> comments;
	String username;
	String content;
	public Comment(String username, String content) {
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
}
