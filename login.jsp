<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Sign in &middot; Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Login page for CMPUT 391 Radiology Information System">
<meta name="author" content="">

<%@ include file="/layout/headlib.jsp"%>
</head>

<body>
<br>
	<div class="container" style="text-align:left;">

		<form name="login" class="form-signin" method="GET" action="login">
		<span style="text-align:center;"> 
			<h3>Welcome to the </h3>
			<h1>Radiology Information System</h1> </span>

			<input type="text" name="user_name" class="input-block-level"
				placeholder="Username"> <input name="password" type="password"
				class="input-block-level" placeholder="Password">

			<span style="color:red;">${param.message}</span>
			<br>
			<br>

			<button class="button blue" type="submit" value="signin">Sign in</button>
		</form>

	</div>
	<!-- /container -->

	<!-- javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
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
