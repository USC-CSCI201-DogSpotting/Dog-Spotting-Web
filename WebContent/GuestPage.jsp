<!DOCTYPE html>
<html lang="en">
<head>
<title>Top Ranked Guest</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="guestfile.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
window.onload = function(){
	var loggedin = <%=request.getSession().getAttribute("loggedin")%>;
	if(loggedin===true){
		console.log("loggedin");
		window.location = "TopRanked.jsp";
	}
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
</script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">DogSpotting</a>
			</div>
			<form method="GET" class="navbar-form navbar-left" action="Search.jsp">
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
			<ul class="nav navbar-nav">
				<li><a href="#" data-toggle="modal" data-target="#myModal">Log
						In</a></li>
				<li><a href="#" data-toggle="modal" data-target="#myModal2">Sign
						Up</a></li>
			</ul>
		</div>
			<div class="btn-group btn-group-justified" role="group"
		aria-label="...">
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default" id="today">Today</button>
		</div>
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default" id="month">This Month</button>
		</div>
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default" id="year">This Year</button>
		</div>
	</div>
	</nav>
		<br>
	<br>
	<!-- Trigger the modal with a button -->
	<!-- Modal -->
	<div class="modal fade" id="myModal2" role="dialog">
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
						<br> Retype Password:<input type="password" id="signupretypepassword"></><br>
						<br> Profile Image URL Link:<input type="url" id="signupurl"></><br>
						<span id="signup_err" style="color: darkred; font-weight: bold"></span>
					</div>
					<div class="modal-footer">
						<button type="button" id="closesignup" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default" onclick="validatesignup()">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
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
	<div class="container" style="padding-top: 70px">
		<div id="posts">
		</div>
		<div id="readMoreButton">
			<button class="btn btn-default" id="readMore">Read More</button>
		</div>
	</div>
	<br>
	<br>
	<script>
 		var numOfPost = 0;
		var postEachPage = 20;
		var curCount = 0;
		var rank = 0;

		// Load posts when entering this page
		$(document).ready(function() {
			$("#noMore").css("display", "none");
			$("#readMore").click();
		});

		// Change to Daily
		$("#today").on("click", function() {
			$("#readMoreButton").css("display", "block");
			$("#noMore").css("display", "none");
			rank = 0;
			numOfPost = 0;
			$("#today").css({
				"background-color" : "#D8BFD8"
			});
			$("#month").css({
				"background-color" : "#D9D9F0"
			});
			$("#year").css({
				"background-color" : "#D9D9F0"
			});
			$("#readMore").click();
		})

		$("#month").on("click", function() {
			// Change to Monthly
			$("#readMoreButton").css("display", "block");
			$("#noMore").css("display", "none");
			rank = 1;
			numOfPost = 0;
			$("#month").css({
				"background-color" : "#D8BFD8"
			});
			$("#today").css({
				"background-color" : "#D9D9F0"
			});
			$("#year").css({
				"background-color" : "#D9D9F0"
			});
			$("#readMore").click();
		})

		$("#year").on("click", function() {
			// Change to Yearly
			$("#readMoreButton").css("display", "block");
			$("#noMore").css("display", "none");
			rank = 2;
			numOfPost = 0;
			$("#year").css({
				"background-color" : "#D8BFD8"
			});
			$("#month").css({
				"background-color" : "#D9D9F0"
			});
			$("#today").css({
				"background-color" : "#D9D9F0"
			});
			$("#readMore").click();
		})

		$("#readMore").on( "click", function() {
			  numOfPost += postEachPage; // Add max number of posts on this page
			  curCount = 0; // Count current number of posts
			  $.post("TopRank", { rank : rank, limit : numOfPost }, function(responseJson) {
				  $("#posts").empty(); 
				  // Add each post through the response Json string
				  $.each( responseJson, function(index, post) {
					  curCount++;
<<<<<<< HEAD
					  $("#posts").append("<div id='post' class='container post thumbnail'><span><img id=\"userprofpic\" src=\"" + post.userPicURL + "\"><text id=\"userusername\" href=\"#\">" + post.username + "</text></span><a href=\"#\"><img src=\"" +post.imageURL+"\"></a></div><br><br><br>")
=======
					  $("#posts").append("<div id='post' class='container post thumbnail'><span><img id=\"userprofpic\" src=\"" + post.user.userPicURL + "\"><a id=\"userusername\" href=\"#\">" + post.user.username + "</a></span><a href=\"#\"><img src=\"" +post.imageURL+"\"></a></div><br><br><br>")
>>>>>>> origin/deployment5
					});  
				  // No more posts
					if (curCount <= numOfPost - postEachPage) {
						$("#readMoreButton").css("display", "none");
						$("#noMore").css("display", "block");
					}
			});
		}); 
  </script>
</body>
</html>