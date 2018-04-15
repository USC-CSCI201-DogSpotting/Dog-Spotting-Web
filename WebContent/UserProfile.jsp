<!DOCTYPE html>
<html lang="en">
<head>

  <title>Home Feed User</title>
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
  		xhttp.open("GET", "Logout?", false); //synchronous
  		xhttp.send();
  		window.location.replace("GuestPage.jsp");	
  	}
  	</script>
  <style>
  .list-group{
    max-height: 300px;
    overflow-y:scroll; 
	}
	</style>
</head>
<body>

<div class="container">
  	<span class="nav"> <img src="none"></img> <text>DogSpotting</text>
		<input type="text" id="search" placeholder="Search..">
<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">+</button>
<button type="button" class="btn btn-default">Top</button>
<button type="button" class="btn btn-default">Username</button>
<button type="button" class="btn btn-default" onclick="logout()">Log Out</button>
	</span>
	<br>
	<span class="tab" id="userInfo">
	<img src="none"></img>
	<text>User</text>
		<button class="tablinks" onclick="load('day')">Posts</button>
		<button class="tablinks" onclick="load('month')">Liked</button>
		<button type="button" class="tablinks" data-toggle="modal" data-target="#followersModal">Followers</button>
		<button type="button" class="tablinks" data-toggle="modal" data-target="#followingModal">Following</button>
		<button type="button" class="tablinks" data-toggle="modal" data-target="#settingsModal">Settings</button>
	</span>
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
  
  
    <!-- Modal followers -->
  <div class="modal fade" id="followersModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Followers</h4>
        </div>
        <div class="modal-body">
        <div class="list-group">
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Follower1</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Follower2</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Follower3</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Follower4</button>
        </div>
        </div>
        <div class="modal-footer">
          <button type="button" id="close" class="btn btn-default" data-dismiss="modal">Close</button>
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
          <h4 class="modal-title">Followers</h4>
        </div>
        <div class="modal-body">
        <div class="list-group">
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following1</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following2</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following3</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following4</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following1</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following2</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following3</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following4</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following1</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following2</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following3</button>
        <button type="button" class="list-group-item list-group-item-action"><img src="none">Following4</button>
        </div>
        </div>
        <div class="modal-footer">
          <button type="button" id="close" class="btn btn-default" data-dismiss="modal">Close</button>
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
        <div class="modal-body">
       <form action="/action_page.php">
  		<input type="file" name="pic" accept="image/*"><br>
  		<input type="text" id="description">Change Username:</><br>
  		<input type="password" id="description">Change Password:</><br>
  		<input type="password" id="description">Repeat Password:</><br>
  		<input type="submit">
		</form>
        </div>
        <div class="modal-footer">
          <button type="button" id="save" class="btn btn-default" data-dismiss="modal">Save</button>
        </div>
      </div>
      
    </div>
  </div>

</body>
</html>