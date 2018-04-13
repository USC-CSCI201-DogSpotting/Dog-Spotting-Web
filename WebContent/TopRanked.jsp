<!DOCTYPE html>
<html lang="en">
<head>
  <title>Top Ranked User</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

<div class="container">
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
      <li><button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">+</button></li>
      <li><a href="HomeFeed.jsp" type="button">Feed</a></li>
      <li><a type="button">Username</a></li>
      <li><a type="button" onclick="logout()">Log Out</a></li>
      </ul>
    </div>
  </nav>
	<div class="tab" id="toprankfilter">
		<button class="tablinks" id="today">Today</button>
    <button class="tablinks" id="week">This Week</button>
		<button class="tablinks" id="month">This Month</button>

	</div>
  <!-- Trigger the modal with a button -->

  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">New Post</h4>
        </div>
        <div class="modal-body">
       <form action="/action_page.php">
  		<input type="file" name="pic" accept="image/*"><br>
  		<input type="text" id="description">Description:</><br>
  		<input type="text" id="tag1">Tag 1:</><br>
  		<input type="text" id="tag2">Tag 2:</><br>
  		<input type="text" id="tag3">Tag 3:</><br>
  		<input type="text" id="tag4">Tag 4:</><br>
  		<input type="text" id="tag5">Tag 5:</><br>
  		<input type="submit">
		</form>
        </div>
        <div class="modal-footer">
          <button type="button" id="close" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" id="post" class="btn btn-default" data-dismiss="modal">Post</button>
        </div>
      </div>
      
    </div>
  </div>
  
  <div class="container" style="padding-top: 70px">
  <div id="posts">
  </div>
  <div id="readMoreButton">
  <button class="btn btn-primary" id="readMore">Read More</button>
  <p id="noMore">No more posts</p>
  </div>
  </div>

<script>
  var numOfPost = 0;
  var postEachPage = 20;
  var curCount = 0;
  var rank = 0;
  $("#noMore").css("display", "none");
  
  // Load posts when entering this page
  $(document).ready(function() {
    $("#readMore").click();
  });
  
  // Change to Daily
  $("#today").on("click", function() {
	  $("#readMoreButton").css("display", "block");
    $("#noMore").css("display", "none");
	  rank = 0;
	  numOfPost = 0;
	  $("#readMore").click();
  })
  
  // Change to Weekly
  $("#week").on("click", function() {
	  $("#readMoreButton").css("display", "block");
	  $("#noMore").css("display", "none");
    rank = 1;
    numOfPost = 0;
    $("#readMore").click();
  })
  
  // Change to Monthly
  $("#month").on("click", function() {
	  $("#readMoreButton").css("display", "block");
	  $("#noMore").css("display", "none");
    rank = 2;
    numOfPost = 0;
    $("#readMore").click();
  })
  
  $("#readMore").on("click", function() {
    numOfPost += postEachPage; // Add max number of posts on this page
    curCount = 0; // Count current number of posts
    $.post("TopRank", { rank: rank, limit: numOfPost }, function(responseJson) {
      $("#posts").empty();
      // Add each post through the response Json string
      $.each(responseJson, function(index, post) {
        curCount++;
        $("#posts").append("<div class='container post thumbnail'><a href='PostPage?postID=" + post.postID + "'><img src='" + post.imageURL + "'></a></div>");
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



