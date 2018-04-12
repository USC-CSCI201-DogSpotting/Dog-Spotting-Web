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
	  		if(loggedin===false){
	  			console.log("loggedin");
	  			window.location = "GuestPage.jsp";
	  		}
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
	  </div>
	</body>
</html>