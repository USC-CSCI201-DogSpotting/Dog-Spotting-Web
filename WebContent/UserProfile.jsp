<!DOCTYPE html>
<html lang="en">
<head>

<title>Home Feed User</title>
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

  function ImageExists(url){
	    var image = new Image();
	    image.src = url;
	    if (!image.complete) {
	    	console.log('bad image1')
	        return false;
	    }
	    else if (image.height == 0) {
	    	console.log('bad image2')
	        return false;
	    }

	    return true;
	}
  function validateImage(){
	  var exists = true; // ImageExists(document.getElementById("changeimage").value);
	  $.get(document.getElementById("changeimage").value)
	    .done(function() {
	        console.log('exists');
	   // })
	  //if(exists == true){
		 console.log("settings button image");
	        var requeststr = "UpdateProfilePic?";
	        //requeststr += "username="
	          //      + document.getElementById("username").value;
	        requeststr += "&changeimage="
	                + document.getElementById("changeimage").value;

	        console.log(requeststr);
	        var xhttp = new XMLHttpRequest();
	        xhttp.open("POST", requeststr, false);
	        xhttp.send();
	        console.log(xhttp.responseText);
	        if (xhttp.responseText.trim().length > 0) {
	            console.log('changing failed')
	            document.getElementById("image_err").innerHTML = xhttp.responseText;
	        } else {
	            console.log('changing image success')
	            //window.location = "UserProfile.jsp"
	           $("#settingsModal").modal('hide');
	        }
	  //}
	  })
	  .fail(function() {
	        console.log('failed');
	        console.log('image failed to load')
	          //window.location = "UserProfile.jsp"
			document.getElementById("image_err").innerHTML = "Error: could not find photo";
	    })
}

  function validateUsername(){
		 console.log("settings button username");
	        var requeststr = "UpdateUsername?";
	        //requeststr += "username="
	          //      + document.getElementById("username").value;
	        requeststr += "&changeusername="
	                + document.getElementById("changeusername").value;

	        console.log(requeststr);
	        var xhttp = new XMLHttpRequest();
	        xhttp.open("POST", requeststr, false);
	        xhttp.send();
	        console.log(xhttp.responseText);
	        if (xhttp.responseText.trim().length > 0) {
	            console.log('changing failed')
	            document.getElementById("username_err").innerHTML = xhttp.responseText;
	        } else {
	            console.log('changing username success')
	            //window.location = "UserProfile.jsp"
	            $("#settingsModal").modal('hide');
	        }
	}

  function validatePassword(){
		 console.log("settings button password");
	        var requeststr = "UpdatePassword?";
	        //requeststr += "username="
	          //      + document.getElementById("username").value;
	        requeststr += "&changepassword="
	                + document.getElementById("changepassword").value;
	        requeststr += "&retypepassword="
                + document.getElementById("retypepassword").value;

	        console.log(requeststr);
	        var xhttp = new XMLHttpRequest();
	        xhttp.open("POST", requeststr, false);
	        xhttp.send();
	        console.log(xhttp.responseText);
	        if (xhttp.responseText.trim().length > 0) {
	            console.log('changing failed')
	            document.getElementById("password_err").innerHTML = xhttp.responseText;
	        } else {
	            console.log('changing password success')
	            $("#settingsModal").modal('hide');
	        }
	}



	window.onload = function(){
  		var loggedin = <%=request.getSession().getAttribute("loggedin")%>;
  		console.log(loggedin);
  		if(loggedin===false || loggedin===null){
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
  		          console.log(event.data);
  		          if (event.data != 0) {
  		            $.post("GetNotifications", { username: socketUsername }, function(responseJson) {
  		              $.each(responseJson, function(index, notification) {
  		            	  var html = "";
  		            	  html+="<div id='notificationdiv'><img id='userprof' style='height: 15px; width: 15px; border-radius: 10px;' src=\"" + notification.user.userPicURL + "\">  " + notification.user.username +" : ";
  		            	  html+= notification.message + "</div><br>";
  		                document.getElementById("notifyNum").innerHTML += html;
  		              });
  		            });
  		          }
  		          //document.getElementById("notifyNum").innerHTML += event.data + "<br />";
  		        }
  				
  		}
  	}
  	function logout(){
  		var xhttp = new XMLHttpRequest();
  		xhttp.open("GET", "Logout?", false); //synchronous
  		xhttp.send();
  		window.location.replace("GuestPage.jsp");
  	}

		//var validChoice;
	 $(document).ready(function() {
		  $("#postsButton").on("click", function() {
			 var validChoice = true;
			  // should there be a limit to the amount of users shown?
			console.log("postButton working");
		    $.get("Profile",  { username: "<%=request.getParameter("username")%>"}, function(responseJson) {
		        	$("#posts").empty();
		        	var par = JSON.parse(responseJson.ownPosts);
		        //var par = $.parseJSON(responseJson.ownPosts);
		        	opts= "<h4 class='modal-title'>Past Posts</h4>";
		        	console.log("postButton parsing");
		        console.log("pastPosts: "  + par.description);
		        	$.each(par, function(index, item) {
		        		console.log("postButton creating tag");
		        		console.log("descrip: " +item.description + "postID:" + item.postID + "postImageurl:" + item.imageURL);
		        		 var html = "";
			                html += "<div id='post' class='container thumbnail'>";
			                html += "<span>" + "<img id='userprofpic' src='"+ item.user.userPicURL +"'>";
			                html += "<a type='button' onclick='userProfile(\""+ item.user.username + "\")'>" + item.user.username + "</a>";
			                html += "</span>"
			                html += "<a href='PostPage?postID=" + item.postID + "'><img src='" + item.imageURL + "'></a>";
			                html += "<div id='like' class=\"btn-group btn-group-justified\" role=\"group\" aria-label=\"...\">";
			                html += "<div class=\"btn-group\" role=\"group\"><button class='btn btn-default' id='l" + item.postID + "'>" + (item.isLike ? "Unlike" : "Like") + " " + (item.numOfLikes) + "</button></div>";
			                html += "<div class=\"btn-group\" role=\"group\"><button class='btn btn-default float-right' id='f" + item.postID + "'>" + (item.isFollow ? "Unfollow" : "Follow") + "</button></div>";
			                html += "</div>";
			                html += "</div>";
			        		
			        		opts += html;
		        });

		        	postsView(true, opts);
		        	console.log("postButton done appending");
		        // display one image
		    	});
		   });
		  });
		//var validChoice;
	 $(window).on('load', function() {
			 var validChoice = true;
			  // should there be a limit to the amount of users shown?
			console.log("postButton working");
		    $.get("Profile",  { username: "<%= request.getParameter("username") %>"}, function(responseJson) {

		        	$("#posts").empty();
		        	var par = JSON.parse(responseJson.ownPosts);
		        //var par = $.parseJSON(responseJson.ownPosts);
		        	opts= "<h4 class='modal-title'>Past Posts</h4>";
		        	console.log("postButton parsing");
		        console.log("pastPosts: "  + par.description);
		        	$.each(par, function(index, item) {
		        		console.log("postButton creating tag");
		        		console.log("descrip: " +item.description + "postID:" + item.postID + "postImageurl:" + item.imageURL);
		        		 var html = "";
			                html += "<div id='post' class='container thumbnail'>";
			                html += "<span>" + "<img id='userprofpic' src='"+ item.user.userPicURL +"'>";
			                html += "<a type='button' onclick='userProfile(\""+ item.user.username+ "\")'>" + item.user.username + "</a>";
			                html += "</span>"
			                html += "<a href='PostPage?postID=" + item.postID + "'><img src='" + item.imageURL + "'></a>";
			                html += "<div id='like' class=\"btn-group btn-group-justified\" role=\"group\" aria-label=\"...\">";
			                html += "<div class=\"btn-group\" role=\"group\"><button class='btn btn-default' id='l" + item.postID + "'>" + (item.isLike ? "Unlike" : "Like") + " " + (item.numOfLikes) + "</button></div>";
			                html += "<div class=\"btn-group\" role=\"group\"><button class='btn btn-default float-right' id='f" + item.postID + "'>" + (item.isFollow ? "Unfollow" : "Follow") + "</button></div>";
			                html += "</div>";
			                html += "</div>";
			        		
			        		
			        		opts += html;

		        	postsView(true, opts);
		        	console.log("postButton done appending");
		        // display one image
		    	});
		   });
		  });

	 $(document).ready(function() {
		  $("#likedButton").on("click", function() {
			  var validChoice = false;
			$.get("Profile", { username: "<%=request.getParameter("username")%>"}, function(responseJson) {
	        	$("#liked").empty();
	        	var par = JSON.parse(responseJson.likePosts);
	        //var par = $.parseJSON(responseJson.ownPosts);
	        	opts= "<h4 class='modal-title'>Liked Posts</h4>";
	        	console.log("likedButton parsing");
	        console.log("likedPosts: "  + par.description);
	        	$.each(par, function(index, item) {
	        		console.log("likedButton creating tag");
	        		console.log("descrip: " +item.description + "postID:" + item.postID + "postImageurl:" + item.imageURL);
	        		var html = "";
	                html += "<div id='post' class='container thumbnail'>";
	                html += "<span>" + "<img id='userprofpic' src='"+ item.user.userPicURL +"'>";
	                html += "<a type='button' onclick='userProfile(\""+ item.user.username+ "\")'>" + item.user.username + "</a>";
	                html += "</span>"
	                html += "<a href='PostPage?postID=" + item.postID + "'><img src='" + item.imageURL + "'></a>";
	                html += "<div id='like' class=\"btn-group btn-group-justified\" role=\"group\" aria-label=\"...\">";
	                html += "<div class=\"btn-group\" role=\"group\"><button class='btn btn-default' id='l" + item.postID + "'>" + (item.isLike ? "Unlike" : "Like") + " " + (item.numOfLikes) + "</button></div>";
	                html += "<div class=\"btn-group\" role=\"group\"><button class='btn btn-default float-right' id='f" + item.postID + "'>" + (item.isFollow ? "Unfollow" : "Follow") + "</button></div>";
	                html += "</div>";
	                html += "</div>";
	        		
	        		
	        		opts += html;
	        });
	        //	$("#liked").empty().append(opts)
	         postsView(false, opts);
	        	console.log("likedButton done appending");
	        	 // display one image
	    	});
	});
});

	function postsView(validChoice){
		if(validChoice == true){
			console.log("11true");
		 	$("#posts").empty().append(opts)
		}
		if(validChoice == false){
			console.log("false");
		 	$("#posts").empty().append(opts)
		}
	}

  //$(document).ready(function() {
  //$("#followersButton").on("click", function() {
	  $(document).on("click", "#followersButton", function() {
	  // should there be a limit to the amount of users shown?
    $.get("Profile",  { username: "<%=request.getSession().getAttribute("currentusername")%>"}, function(responseJson) {
        	$("#followers").empty();
       	const str = JSON.stringify(responseJson.followerUsernames);
       	console.log(str);

     console.log(JSON.parse(str));
       // var par = JSON.parse(str);
        var par = JSON.parse(responseJson.followerUsernames);
        var par1 = JSON.parse(responseJson.followerPics);
        	console.log("followers: "+ par);
        	opts= '';
        	var inputElement;
        	$.each(par, function(index, item) {
        		//opts += "<button onclick='otherProfile("+item+")'>" + item + "<button>";
        		//var str1 = String(item);
        		console.log("usernameinfollowers: " + this);
        		
    			$.each(par1, function(index1, type){ 	
        			//console.log("index1: " + index1 + " item1: " + item1);
      if(index == index1){
        				//console.log("index1: " + index1 + " item1: " + item1);
        	opts += "<li class=\"list-group-item\">";
        opts += "<img id='userprofpic' src='"+ type +"'>";
       }
  });	
      		opts += "<a type='button' onclick='otherProfile(\""+ item + "\")'>" + item + "</a></li>";	
			
        		$("#followers").empty().append(opts);
        });
    	});
   });
  //});

  $(document).ready(function() {
	  $("#followingButton").on("click", function() {
		  // should there be a limit to the amount of users shown?
	    $.post("Profile",  { username: "<%=request.getSession().getAttribute("currentusername")%>"},
				function(responseJson) {
					$("#following").empty();
					var par = JSON.parse(responseJson.followingUsernames);
					var par1 = JSON.parse(responseJson.followingPics);
					var opts = "";
					$.each(par,function(index, item) {
		        		
		    			$.each(par1, function(index1, type){ 	
		        			//console.log("index1: " + index1 + " item1: " + item1);
		      if(index == index1){
		        				//console.log("index1: " + index1 + " item1: " + item1);
		        	opts += "<li class=\"list-group-item\">";
		        opts += "<img id='userprofpic' src='"+ type +"'>";
		       }
		  		});
		      		opts += "<a type='button' onclick='otherProfile(\""+ item + "\")'>" + item + "</a></li>";
					$("#following").empty().append(opts)
					});
					});
				});
			});
	function validateNewPost() {
		console.log("here");
		var requeststr = "NewPost?";
		requeststr += "img=" + document.getElementById("img").value;
		requeststr += "&description="
				+ document.getElementById("description").value;
		requeststr += "&tag1=" + document.getElementById("tag1").value;
		requeststr += "&tag2=" + document.getElementById("tag2").value;
		requeststr += "&tag3=" + document.getElementById("tag3").value;
		requeststr += "&tag4=" + document.getElementById("tag4").value;
		requeststr += "&tag5=" + document.getElementById("tag5").value;
		console.log(requeststr);
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", requeststr, false);
		xhttp.send();
		console.log(xhttp.responseText);


      if(document.getElementById("img").value.trim().length == 0 || document.getElementById("description").value.trim().length == 0 ||
      		(document.getElementById("tag1").value.trim().length == 0||document.getElementById("tag2").value.trim().length == 0||
      				document.getElementById("tag3").value.trim().length == 0||document.getElementById("tag4").value.trim().length == 0||
      				document.getElementById("tag5").value.trim().length == 0)){
      		if(document.getElementById("img").value.trim().length == 0){
     		 	document.getElementById("inputError").innerHTML = xhttp.responseText;
     		 }
      		if(document.getElementById("description").value.trim().length == 0){
     		 	document.getElementById("inputError").innerHTML = xhttp.responseText;
     		 }
     		 if(document.getElementById("tag1").value.trim().length == 0||document.getElementById("tag2").value.trim().length == 0||
				document.getElementById("tag3").value.trim().length == 0||document.getElementById("tag4").value.trim().length == 0||
				document.getElementById("tag5").value.trim().length == 0){
      			document.getElementById("inputError").innerHTML = xhttp.responseText;
     		 }
      }
      else{
      		document.getElementById("inputCorrect").innerHTML = xhttp.responseText;
       	alert('Post Successful')
      		window.location = "HomeFeed.jsp"
      }
  }

  function userProfile(str){ // make a XMLRequest to send the username of the otheruser
	  //var str = item;
	  console.log("str: " +str);
	  var requeststr = "ValidateUsername?";
      requeststr += "otherusername="
              + str;
      var validInput = false;
	  var userVal = "<%=(String) session.getAttribute("currentusername")%>"
	  if(str == userVal)
		  {
		  	validInput = true;
		  	window.location = "UserProfile.jsp?username="+"<%=(String) session.getAttribute("currentusername")%>";
		  }
	  if(validInput == false){
      console.log(requeststr);
      var xhttp = new XMLHttpRequest();
      xhttp.open("POST", requeststr, true);
      xhttp.send();
      console.log(xhttp.responseText);
	  //HttpSession session  = request.getSession();
	  //session.setAttribute("otherusername",str);
	  window.location = "OtherProfile.jsp?otherusername="+str;
  	  console.log("other profile buton: " + str);
      /*var requeststr = "OtherProfile?";
      requeststr += "otherusername1="
              + str;

      console.log(requeststr);
      var xhttp = new XMLHttpRequest();
      xhttp.open("POST", requeststr, true);
      xhttp.send();
      console.log(xhttp.responseText);*/
	  }
  }

</script>

<style>
.list-group {
	max-height: 300px;
	overflow-y: scroll;
}
</style>
</head>
<body>

	<div class="container">

		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
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
				<ul class="nav navbar-nav">
					<li><a type="button" data-toggle="modal"
						data-target="#myModal">+</a></li>
					<li><a href="TopRanked.jsp" type="button">Top</a></li>
					<li><a type="button" onclick="location.href='UserProfile.jsp'"><%=(String) session.getAttribute("currentusername")%></a></li>
					<li><a type="button" onclick="logout()">Log Out</a></li>
										<li>
							<div class="dropdown show">
								<a type="button" class="dropdown-toggle" href="#"
									role="button" id="dropdownMenuLink" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false"> Notifications </a>

								<div id="notifyNum" style="width: 250px; padding: 10px;" class="dropdown-menu large" aria-labelledby="dropdownMenuLink">
								</div>
							</div>
					</li>
				</ul>
				<div id="notifyNum"></div>
			</div>
			<div id="userprofilestuff" class="btn-group btn-group-justified"
				role="group" aria-label="...">
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default" ><%=(String) session.getAttribute("currentusername")%></button>
				</div>
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default" id="postsButton">Posts</button>
				</div>
				
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default" id="likedButton">Liked</button>
				</div>
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default" data-toggle="modal"
						data-target="#followersModal" id="followersButton">Followers</button>
				</div>
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default" data-toggle="modal"
						data-target="#followingModal" id="followingButton">Following</button>
				</div>
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default" data-toggle="modal"
						data-target="#settingsModal">Settings</button>
				</div>
			</div>
		</nav>

	</div>
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
					<h4 class="modal-title">New Post</h4>
				</div>
				<div class="modal-body">
					<form action="/action_page.php">
						<!-- <input type="file" name="pic" accept="image/*"><br> -->
						Image URL: <input type="text" id="img" name="img"><br>
						Caption:<input type="text" id="description" name="description"></><br>
						Tag 1:<input type="text" id="tag1" name="tag1"></><br>
						Tag 2:<input type="text" id="tag2" name="tag2"></><br>
						Tag 3:<input type="text" id="tag3" name="tag3"></><br>
						Tag 4:<input type="text" id="tag4" name="tag4"></><br>
						Tag 5:<input type="text" id="tag5" name="tag5"></><br>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="close" class="btn btn-default"
						data-dismiss="modal">Close</button>
					<button type="button" id="post" class="btn btn-default"
						data-dismiss="modal" onclick="validateNewPost()">Post</button>
				</div>
			</div>

		</div>
	</div>


	<!-- Modal followers -->
	<div class="modal fade" id="followersModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Followers
				</div>
				<div class="modal-body">
					<ul class="list-group" id="followers"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" id="close" class="btn btn-default"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal following -->
	<div class="modal fade" id="followingModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Following
				</div>
				<div class="modal-body">
					<ul class="list-group"  id="following">
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" id="close" class="btn btn-default"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Settings -->
	<div class="modal fade" id="settingsModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Settings</h4>
				</div>
				<div id="changeform">
					<div class="modal-body">
						Image:<input type="text" id="changeimage" name="changeimage"></>
						<button type="button" class="btn btn-default"
							onclick="validateImage()">Save</button>
						<br> <span id="image_err"
							style="color: darkred; font-weight: bold"></span> <br>Change
						Username:<input type="text" id="changeusername"
							name="changeusername"></>
						<button type="button" class="btn btn-default"
							onclick="validateUsername()">Save</button>
						<br> <span id="username_err"
							style="color: darkred; font-weight: bold"></span> <br>Change
						Password:<input type="password" id="changepassword"
							name="changepassword"></> <br> Repeat Password:<input
							type="password" id="retypepassword" name="retypepassword"></>
						<button type="button" class="btn btn-default"
							onclick="validatePassword()">Save</button>
						<br> <span id="password_err"
							style="color: darkred; font-weight: bold"></span>
					</div>
					<div class="modal-footer">
						<button type="button" id="closelogin" class="btn btn-default"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container" style="padding-top: 70px">
		<div id="posts"></div>
	</div>
	<div class="container" style="padding-top: 70px">
		<div id="liked"></div>
	</div>
</body>
</html>
