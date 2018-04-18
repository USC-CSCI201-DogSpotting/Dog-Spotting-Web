<!DOCTYPE html>
<html lang="en">
<head>
  <title>Top Ranked User</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="guestfile.css" />
  <script defer src="https://use.fontawesome.com/releases/v5.0.10/js/all.js" integrity="sha384-slN8GvtUJGnv6ca26v8EzVaR9DC58QEwsIk9q1QXdCU8Yu8ck/tL/5szYlBbqmS+" crossorigin="anonymous"></script>  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
	window.onload = function(){
  		var loggedin = <%=request.getSession().getAttribute("loggedin")%>;
  		console.log(loggedin);
  		if(loggedin===false){
  			console.log("loggedin");
  			window.location = "GuestPage.jsp";
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

<div class="container">
  	<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="TopRanked.jsp">DogSpotting</a>
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
 <li><a type="button" onclick="location.href='UserProfile.jsp'"><%=(String)session.getAttribute("currentusername")%></a></li>
      <li><a type="button" onclick="logout()">Log Out</a></li>
      </ul>
      <div id="notifyNum"> </div>
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
  		Tag 5:<input type="text" id="tag5" name="tag5"><br>
  		<span id="inputError" style="color: darkred; font-weight: bold"></span>
      </div>
      </div>
      <div class="modal-footer">
          <button type="button" id="closebutton" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" id="postbutton" class="btn btn-default" onclick="validate()">Post</button>
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
  var rank = 0; // type of rank
  var follow = Array();
  var like = Array();
  var numLike = Array();
  
  $(document).ready(function() {
    $("#readMore").click();
  });
  
  $("#today").on("click", function() {
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
  
  $("#readMore").on("click", function() {
    numOfPost += postEachPage;
    curCount = 0;
    $.post("TopRank", { rank: rank, limit: numOfPost }, function(responseJson) {
      $("#posts").empty();
      $.each(responseJson, function(index, post) {
        curCount++;
        follow[index] = post.isFollow;
        like[index] = post.isLike;
        var html = "";
        html += "<div class='container'>";
        html += "<div class='follow-btn'><p>" + post.user.username + "</p>";
        if (!(post.user.username === "<%= request.getSession().getAttribute("currentusername") %>")) {
            html += "<button class='btn btn-primary' id='f" + post.postID + "'>" + (post.isFollow ? "Unfollow" : "Follow") + "</button>";
        }
        html += "</div>"
        html += "<div class='container thumbnail'><a href='PostPage?postID=" + post.postID + "'><img src='" + post.imageURL + "'></a></div>";
        html += "<span id='l" + post.postID + "'>" + (post.isLike ? "<i class=\"fas fa-heart\"></i>" : "<i class=\"far fa-heart\"></i>") + (post.numOfLikes) + "</span>";
        html += "</div>";
        $("#posts").append(html);
        var curID = "#f" + post.postID;
        $(document).on("click", curID, function() {
            $.post("Follow", {username: post.user.username, isFollow: follow[index]});
            if (follow[index]) {
              follow[index] = false;
              this.innerText = "Follow";
            } else {
            	 follow[index] = true;
            	 this.innerText = "Unfollow";
            }
        });
        curID = "#l" + post.postID;
        $(document).on("click", curID, function() {
            $.post("Like", {postID: post.postID, isLike: like[index]});
            if (like[index]) {
                like[index] = false;
                numLike[index]--;
                this.innerHTML = "<i class=\"far fa-heart\"></i>" + numLike[index];
              } else {
                 like[index] = true;
                 numLike[index]++;
                 this.innerHTML = "<i class=\"fas fa-heart\"></i>" + numLike[index];
              }
        });
      });
      if (curCount <= numOfPost - postEachPage) {
        $("#readMoreButton").html("No more posts");
      }
    });
  });
</script>
</body>
</html>



