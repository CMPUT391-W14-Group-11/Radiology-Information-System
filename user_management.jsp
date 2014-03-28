<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Radiology Information System Home">
<meta name="author" content="">
</head>

<%@ include file="/layout/headlib.jsp"%>
<body>
	<%@ include file="/layout/nav_bar.jsp"%>
	<div class="container">

	<div class="container">
		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
		<h1>User Management</h1>
		<br>
		<div>
		<ul class="nav" style="width:25%; float:left;">
			<li><a href="user_form.jsp"><i class="dark dark-plus" style="margin-right: 15px;"></i><span style="vertical-align:top;">Create New User</span></a></li>
		</ul>
		<ul class="nav" style="width:20%; float:left;">
		<li><a href="user_list.jsp"><i class="dark dark-uniF5C6-small" style="margin-right: 15px;"></i><span style="vertical-align:top;">View Users</span></a></li>
		</ul>	
		</div>
		<div>
			<form method="POST" action="users">

			<input name="searchUser" type="text" method="POST" placeholder="Enter username" style="width:70%; height:30px;">
			<span style="color:red;">${param.error}</span>
			<br><br>
			
				<p>
				<button class="button blue" type="submit" value="search">Search</button>
			</p>
			</form>
		</div>
		</div>
	</div>

	<%@ include file="/layout/footer.jsp"%>
	<script src="../assets/js/jquery.js"></script>
	<script src="../assets/js/bootstrap-transition.js"></script>
	<script src="../assets/js/bootstrap-alert.js"></script>
	<script src="../assets/js/bootstrap-modal.js"></script>
	<script src="../assets/js/bootstrap-dropdown.js"></script>
	<script src="../assets/js/bootstrap-scrollspy.js"></script>
	<script src="../assets/js/bootstrap-tab.js"></script>
	<script src="../assets/js/bootstrap-tooltip.js"></script>
	<script src="../assets/js/bootstrap-popover.js"></script>
	<script src="../assets/js/bootstrap-button.js"></script>
	<script src="../assets/js/bootstrap-collapse.js"></script>
	<script src="../assets/js/bootstrap-carousel.js"></script>
	<script src="../assets/js/bootstrap-typeahead.js"></script>
</body>
</html>

