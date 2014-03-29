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
		<h1>Advanced Search Records</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
			<form method="POST" action="SearchServlet" />
			<table>
				<TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Keywords:</TH>
                                <TD><INPUT TYPE="text" SIZE="50" NAME="keywords"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TD COLSPAN=2><I>*Separate Keywords By Commas</I></TD>
                                </TR>
                                <tr><td><br></td></tr>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH COLSPAN=2><U>And/Or</U></TH>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH COLSPAN=2><I>Valid Date Format "DD-MON-YYYY"</I></TH>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Start Date:</TH>
                                <TD><INPUT TYPE="text" NAME="startdate"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>End Date:</TH>
                                <TD><INPUT TYPE="text" NAME="enddate"></TD>
                                </TR>
                                <tr><td><br></td></tr>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Order By:</TH>
                                <TH><SELECT NAME="order" SIZE="1">
				<tr>
					<td><label for="class">Class: </label></td>
					<td><select name="class" required>
							<option disabled selected>-- Rank --</option>
							<option value="a">Most-Recent First</option>
							<option value="p">Most-Recent Last</option>
					</select></td>
				</tr>

			</table>
			<br> <br> <input type="submit" name="demand"
				value="Search Results">

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

