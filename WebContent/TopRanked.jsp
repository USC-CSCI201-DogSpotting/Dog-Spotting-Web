<!DOCTYPE html>
<html lang="en">
<head>
  <title>Top Ranked User</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  	<span class="nav"> <img src="none"></img> <text>DogSpotting</text>
		<input type="text" placeholder="Search..">
<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">+</button>
<button type="button" class="btn btn-default">My Feed</button>
<button type="button" class="btn btn-default">Username</button>
<button type="button" class="btn btn-default">Log Out</button>
	</span>
	<br>
	<span class="tab" id="toprankfilter">
		<button class="tablinks" onclick="load('day')">Today</button>
		<button class="tablinks" onclick="load('month')">This
			Month</button>
		<button class="tablinks" onclick="load('week')">This
			Week</button>
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

</body>
</html>



