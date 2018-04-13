<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="csci201.Post" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Dog Spotting</title>
		<script>
	  	window.onload = function(){
	  		var loggedin = <%=request.getSession().getAttribute("loggedin")%>;
	  		console.log(loggedin);
	  		if(loggedin===false){
	  			console.log("loggedin");
	  			window.location = "GuestPage.jsp";
	  		}
	  	}
	  	function logout(){
	  		var xhttp = new XMLHttpRequest();
	  		xhttp.open("GET", "Logout?", true); //synchronous
	  		xhttp.send();
	  		window.location.replace("GuestPage.jsp");	
	  	}
	  	</script>
	</head>
	<body>
		<% 
		  Post post = (Post)request.getAttribute("post");
		%>
	  <div class=container>
	     <p><%= post.getUsername() %></p>
	     <img src="<%= post.getImageURL() %>">
	     <p><%= post.getDescription() %></p>
	     <% for (int i = 0; i < post.getComments().size(); i++) { %>
	     <p><%= post.getComments().get(i).getUsername() %>: <%= post.getComments().get(i).getContent() %></p>
	     <% } %>
	     <form method="POST" action="CommentPost">
	       <input type="text" placeholder="New Comment..." class="form-control" name="comment"><br>
	       <input type="hidden" name="postid" value="<%= post.getPostID() %>">
	       <button type="submit">submit</button>
	     </form>
	  </div>
	</body>
</html>