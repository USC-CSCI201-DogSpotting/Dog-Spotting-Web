<!DOCTYPE html>
<html lang="en">
<head>
<title>Top Ranked Guest</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">DogSpotting</a><img src="none"></img>
    </div>
    <form class="navbar-form navbar-left" action="/action_page.php">
      <div class="input-group">
        <input type="text" id="search" class="form-control" placeholder="Search" name="search">
        <div class="input-group-btn">
          <button class="btn btn-default" type="submit">
            <i class="glyphicon glyphicon-search"></i>
          </button>
        </div>
      </div>
    </form>
    <ul class="nav navbar-nav">
      <li><a href="#">Home</a></li>
      <li><a href="#">Page 1</a></li>
      <li><a href="#">Page 2</a></li>
      <li><a href="#">Page 3</a></li>
    </ul>
  </div>
</nav>




	<div class="container">
		<span class="nav"> <img src="none"></img> <text>DogSpotting</text>
			<input type="text" placeholder="Search..">
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#myModal">Log In</button>
			<button type="button" class="btn btn-default" data-toggle="modal"
				data-target="#myModal2">Sign Up</button>
		</span> <br>
	</div>
	<div class="btn-group btn-group-justified" role="group"
		aria-label="...">
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default">Today</button>
		</div>
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default">This Week</button>
		</div>
		<div class="btn-group" role="group">
			<button type="button" class="btn btn-default">This Month</button>
		</div>
	</div>
	<!-- Trigger the modal with a button -->
	<br>

	<br>
	<!-- Blog Post -->
	<div class="card mb-4">
		<img class="card-img-top" src="http://placehold.it/750x300"
			alt="Card image cap">
		<div class="card-body">
			<h2 class="card-title">username<a href="#" class="btn btn-primary">follow</a></h2>
			<p class="card-text">Lorem ipsum dolor sit amet, consectetur
				adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex quis
				soluta, a laboriosam. Dicta expedita corporis animi vero voluptate
				voluptatibus possimus, veniam magni quis!</p>
		</div>
		<div class="card-footer text-muted">
				<span><i class="glyphicon glyphicon-heart"></i> 12 likes</span>
			<span><i class="glyphicon glyphicon-comment"></i> 2 comments</span>
		</div>
	</div>

	<br>
	<!-- Blog Post -->
	<div class="card mb-4">
		<img class="card-img-top" src="http://placehold.it/750x300"
			alt="Card image cap">
		<div class="card-body">
			<h2 class="card-title">username <a href="#" class="btn btn-primary">unfollow</a></h2>
			<p class="card-text">Lorem ipsum dolor sit amet, consectetur
				adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex quis
				soluta, a laboriosam. Dicta expedita corporis animi vero voluptate
				voluptatibus possimus, veniam magni quis!</p>
		</div>
		<div class="card-footer text-muted">
		<span><i class="glyphicon glyphicon-heart"></i> 12 likes</span>
			<span><i class="glyphicon glyphicon-comment"></i> 2 comments</span>
		</div>
	</div>

	<!-- Modal2 -->
	<div class="modal fade" id="myModal2" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Sign Up</h4>
				</div>
				<form action="/action_page.php">
					<div class="modal-body">
						Username:<input type="text" id="username"></><br>
						Password:<input type="password" id="password"></><br>
						Retype Password:<input type="password" id="password"></><br>
					</div>
					<div class="modal-footer">
						<button type="button" id="close" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<input class="btn btn-default" type="submit">
					</div>
				</form>
			</div>

		</div>
	</div>

	<div></div>

	<!-- Modal2 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Log In</h4>
				</div>
				<form action="/action_page.php">
					<div class="modal-body">
						Username:<input type="text" id="username"></><br>
						Password:<input type="password" id="password"></><br>
					</div>
					<div class="modal-footer">
						<button type="button" id="close" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<input class="btn btn-default" type="submit">
					</div>
				</form>
			</div>

		</div>
	</div>

</body>
</html>



