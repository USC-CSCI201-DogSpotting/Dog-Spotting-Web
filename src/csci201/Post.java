package csci201;

import java.util.List;

public class Post {
	int postID;
	String imageURL;
	String username;
	String description;
	List<String> tags;
	List<Comment> comments;
	int numOfLikes;
	Boolean isFollow;
	Boolean isLike;
	public Post(int postID, String imageURL, String username, String description, List<String> tags, List<Comment> comments) {
		this.postID = postID;
		this.imageURL = imageURL;
		this.username = username;
		this.description = description;
		this.tags = tags;
		this.comments = comments;
		this.isFollow = false;
		this.isLike = false;
	}
	public int getPostID() {
		return postID;
	}
	public String getImageURL() {
		return imageURL;
	}
	public String getUsername() {
		return username;
	}
	public String getDescription() {
		return description;
	}
	public List<String> getTags() {
		return tags;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public Boolean isFollow() {
		return isFollow;
	}
	public Boolean isLike() {
		return isLike;
	}
	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}
	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}
}

