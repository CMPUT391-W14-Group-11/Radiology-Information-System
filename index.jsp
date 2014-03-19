<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Radiology Information System Home">
<meta name="author" content="">

<%@ include file="/layout/headlib.jsp"%>
</head>

<body>
<%@ include file="/layout/nav_bar.jsp"%>
	
	<div class="container">

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align:center">
			<h1>Search Records</h1>
			<form method="GET" action="servletbasics">
			<input type="text" method="POST" placeholder="Enter keywords" style="width:70%; height:30px;">
			<br></br>
			<p>
				<input type="submit" class="btn btn-primary btn-large">
			</p>
			</form>
		</div>

		<hr>

	</div>
	<!-- /container -->

	<!-- Le javascript
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
	
	<%@ include file="/layout/footer.jsp"%>
</body>
</html>
