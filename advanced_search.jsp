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
		<h1>Advanced Search</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
		<span style="color:red;">${param.error}</span>
			<form method="POST" action="SearchServlet" />
			<table>
				<TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Keywords:</TH>
                                <TD><INPUT required TYPE="text" SIZE="60" NAME="keywords"> *Separate Keywords By Commas</TD>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Start Date:</TH>
                                <TD><INPUT placeholder="YYYY-MM-DD" TYPE="text" NAME="fDate"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>End Date:</TH>
                                <TD><INPUT placeholder="YYYY-MM-DD" TYPE="text" NAME="tDate"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Order By:</TH>
                                <td><select name="order" required>
					<option value="mrf" selected>Most-Recent First</option>
					<option value="mfl">Most-Recent Last</option>
				</select></td>

			</table>
			<br> <br> <input type="submit" name="searchRecords"
				value="Search Records">

			</form>

		</div>
	</div>
	<%@ include file="/layout/footer.jsp"%>
	
	<script type="text/javascript" src="js/password_check.js"></script>
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

