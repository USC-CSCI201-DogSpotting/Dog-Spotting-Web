<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="csci201.Post"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="guestfile.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Dog Spotting</title>
<script>
		window.onload = function(){
	  		var loggedin = <%=request.getSession().getAttribute("loggedin")%>;
	  		console.log(loggedin);
	  		if(loggedin===false || loggedin===null){
	  			console.log("loggedin");
	  			//window.location = "GuestPage.jsp";
	  			document.getElementById("guestusernavbar").innerHTML = "<li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModalg\">Log In</a></li><li><a href=\"#\" data-toggle=\"modal\" data-target=\"#myModalg2\">Sign Up</a></li>";
	  			document.getElementById("dogspottinglogo").innerHTML = "<a class=\"navbar-brand\" href=\"GuestPage.jsp\">DogSpotting</a>";
	  		}else{
	  	  		// socket
	  	  		var socketUsername = '<%=(String)request.getSession().getAttribute("currentusername")%>';
	  	  		socket = new WebSocket("ws://localhost:8080/DogSpotting/ws");
	  	  		socket.onopen = function(event){
	  	  			socket.send(socketUsername);
	  	  		}
	  			socket.onmessage = function(event){
	  				document.getElementById("notifyNum").innerHTML += event.data + "<br />";
	  			}
	  		}
	  	}
	  	function logout(){
	  		var xhttp = new XMLHttpRequest();
	  		xhttp.open("GET", "Logout?", false); //synchronous
	  		xhttp.send();
	  		window.location.replace("GuestPage.jsp");	
	  	}
	    function validate() {
	        console.log("here");
	        var requeststr = "Login?";
	        requeststr += "username="
	                + document.getElementById("loginusername").value;
	        requeststr += "&password="
	                + document.getElementById("loginpassword").value;
	        console.log(requeststr);
	        var xhttp = new XMLHttpRequest();
	        xhttp.open("POST", requeststr, false);
	        xhttp.send();
	        console.log(xhttp.responseText);
	        if (xhttp.responseText.trim().length > 0) {
	            console.log('login failed')
	            document.getElementById("login_err").innerHTML = xhttp.responseText;
	        } else {
	            console.log('login success')
	            window.location = "HomeFeed.jsp"
	        }
	    }
	    function validatesignup() {
	        console.log("here");
	        var requeststr = "Signup?";
	        requeststr += "username="
	                + document.getElementById("signupusername").value;
	        requeststr += "&password="
	                + document.getElementById("signuppassword").value;
	        requeststr += "&retypepassword="
	            + document.getElementById("signupretypepassword").value;
	        requeststr += "&url="
	            + document.getElementById("signupurl").value;
	        console.log(requeststr);
	        var xhttp = new XMLHttpRequest();
	        xhttp.open("POST", requeststr, false);
	        xhttp.send();
	        console.log(xhttp.responseText);
	        if (xhttp.responseText.trim().length > 0) {
	            console.log('Sign Up failed')
	            document.getElementById("signup_err").innerHTML = xhttp.responseText;
	        } else {
	            console.log('sign up success')
	            window.location = "HomeFeed.jsp"
	        }
	    }
	    function validatePost() {
	        console.log("here");
	        var requeststr = "NewPost?";
	        requeststr += "img="
	                + document.getElementById("img").value;
	        requeststr += "&description="
	                + document.getElementById("description").value;
	        requeststr += "&tag1="
	            + document.getElementById("tag1").value;
	        requeststr += "&tag2="
	            + document.getElementById("tag2").value;
	        requeststr += "&tag3="
	            + document.getElementById("tag3").value;
	        requeststr += "&tag4="
	            + document.getElementById("tag4").value;
	        requeststr += "&tag5="
	            + document.getElementById("tag5").value;
	        console.log(requeststr);
	        var xhttp = new XMLHttpRequest();
	        xhttp.open("POST", requeststr, false);
	        xhttp.send();
	        console.log(xhttp.responseText);
	        if(xhttp.responseText.trim().length>0){
				console.log('post failed');
				document.getElementById("inputError").innerHTML = xhttp.responseText;
	        }
	        else{
	        		console.log('post success');
	        		$('#myModal').modal('hide');
	        }
	    }
	  	</script>
</head>
<body>
	<% 
		  Post post = (Post)request.getAttribute("post");
		%>



	<div class="container">
		<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div id="dogspottinglogo" class="navbar-header">
				<a class="navbar-brand" href="TopRanked.jsp">DogSpotting</a>
			</div>
			<form method="GET" class="navbar-form navbar-left"
				action="Search.jsp">
				<div class="input-group">
					<input type="text" id="search" class="form-control"
						placeholder="Search" name="search">
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>
			<ul id="guestusernavbar" class="nav navbar-nav">
				<li><a type="button" data-toggle="modal" data-target="#myModal">+</a></li>
				<li><a href="HomeFeed.jsp" type="button">Feed</a></li>
				<li><a type="button"><%=request.getSession().getAttribute("currentusername")%></a></li>
				<li><a type="button" onclick="logout()">Log Out</a></li>
			</ul>
		</div>
		</nav>
	</div>
	<!-- Trigger the modal with a button -->

	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h1 class="modal-title">New Post</h1>
				</div>
				<div id="postform">
					<div class="modal-body">
						Image URL:<input type="url" id="img" name="img"><br>
						Caption:<input type="text" id="description" name="description"><br>
						Tag 1:<input type="text" id="tag1" name="tag1"><br>
						Tag 2:<input type="text" id="tag2" name="tag2"><br>
						Tag 3:<input type="text" id="tag3" name="tag3"><br>
						Tag 4:<input type="text" id="tag4" name="tag4"><br>
						Tag 5:<input type="text" id="tag5" name="tag5"><br> <span
							id="inputError" style="color: darkred; font-weight: bold"></span>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="closebutton" class="btn btn-default"
						data-dismiss="modal">Close</button>
					<button type="button" id="postbutton" class="btn btn-default"
						onclick="validatePost()">Post</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal guest sign up-->
	<div class="modal fade" id="myModalg2" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h1 class="modal-title">Sign Up</h1>
				</div>
				<div id="signupform">
					<div class="modal-body">
						Username:<input type="text" id="signupusername"></><br>
						<br> Password:<input type="password" id="signuppassword"></><br>
						<br> Retype Password:<input type="password"
							id="signupretypepassword"></><br> <br> Profile
						Image URL Link:<input type="url" id="signupurl"></><br>
						<span id="signup_err" style="color: darkred; font-weight: bold"></span>
					</div>
					<div class="modal-footer">
						<button type="button" id="closesignup" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							onclick="validatesignup()">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal guest log in-->
	<div class="modal fade" id="myModalg" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h1 class="modal-title">Log In</h1>
				</div>
				<div id="loginform">
					<div class="modal-body">
						Username:<input type="text" id="loginusername"></><br>
						<br> Password:<input type="password" id="loginpassword"></><br>
						<span id="login_err" style="color: darkred; font-weight: bold"></span>
					</div>
					<div class="modal-footer">
						<button type="button" id="closelogin" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default" onclick="validate()">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class=container>
		<div id="post" class='container post thumbnail'>
			<p><%= post.getUsername() %></p>
			<br> <img src="<%= post.getImageURL() %>">
			<p><%= post.getDescription() %></p>
					<% for (int i = 0; i < post.getComments().size(); i++) { %>
		<p><%= post.getComments().get(i).getUsername() %>:
			<%= post.getComments().get(i).getContent() %></p>
		<% } %>


		<form method="POST" action="CommentPost">
			<input type="text" placeholder="New Comment..." class="form-control"
				name="comment"><br> <input type="hidden" name="postid"
				value="<%= post.getPostID() %>">
			<button type="submit">submit</button>
		</form>
		</div>
	</div>

</body>
</html>