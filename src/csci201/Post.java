package csci201;

import java.util.List;

class Comment {
	String username;
	String content;
	public Comment(String username, String content) {
		this.username = username;
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public String getContent() {
		return content;
	}
}

class Post {
	int postID;
	String imageURL;
	String username;
	String description;
	List<String> tags;
	List<Comment> comments;
	int numOfLikes;
	public Post(int postID, String imageURL, String username, String description, List<String> tags, List<Comment> comments) {
		this.postID = postID;
		this.imageURL = imageURL;
		this.username = username;
		this.description = description;
		this.tags = tags;
		this.comments = comments;
	}
	String getImageURL() {
		return imageURL;
	}
	String getUsername() {
		return username;
	}
	String getDescription() {
		return description;
	}
	List<String> getTags() {
		return tags;
	}
	List<Comment> getComments() {
		return comments;
	}
}

