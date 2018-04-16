package csci201;

import java.util.List;

public class Poster {
	String imageURL;
	String username;
	String description;
	List<String> tags;
	List<Comment> comments;
	int numOfLikes;

	public Poster(String imageURL, String username, String description, List<String> tags, List<Comment> comments) {
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
