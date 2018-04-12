<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dog Spotting</title>
<link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
  src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
  src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
String search = (String)request.getParameter("search");
%>

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
			<li><a href="TopRanked.jsp" type="button">Top</a></li>
			<li><a type="button">Username</a></li>
			<li><a type="button">Log Out</a></li>
      </ul>
    </div>
  </nav>
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
  </div>
  </div>

<script>
  var numOfPost = 0;
  var postEachPage = 20;
  var curCount = 0;
  
  $(document).ready(function() {
    $("#readMore").click();
  }); 
  $("#readMore").on("click", function() {
    numOfPost += postEachPage;
    curCount = 0;
    console.log("<%= search %>");
    $.post("Search", { search: "<%= search %>", limit: numOfPost }, function(responseJson) {
      $("#posts").empty();
      $.each(responseJson, function(index, post) {
        curCount++;
        $("#posts").append("<div class='container post thumbnail'><a href='PostPage?postID=" + post.postID + "'><img src='" + post.imageURL + "'></a></div>");
      });
      if (curCount <= numOfPost - postEachPage) {
        $("#readMoreButton").html("No more posts");
      }
    });
  });
  
</script>
</body>

</body>
</html>