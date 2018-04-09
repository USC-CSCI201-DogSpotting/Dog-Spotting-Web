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
      <a class="navbar-brand" href="#">DogSpotting</a>
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
      <li><a href="#" data-toggle="modal"
				data-target="#myModal">Log In</a></li>
      <li><a href="#" data-toggle="modal"
				data-target="#myModal2">Sign Up</a></li>
    </ul>
  </div>
</nav>
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
						Username:<input type="text" id="username"></><br><br>
						Password:<input type="password" id="password"></><br><br>
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

	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h1 class="modal-title">Log In</h1>
				</div>
				<form action="/action_page.php">
					<div class="modal-body">
						Username:<input type="text" id="username"></><br><br>
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



