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
		<h1>Report Generate Module</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
			<form method="POST" action="ReportServlet" />
			<table>
			<p></p>
                        <p>Time format: DD-MON-YYYY</p>
                        <TR VALIGN=TOP ALIGN=LEFT>
                        <TH>Diagnosis:</TH>
                        <TD><INPUT TYPE="text" NAME="diagnosis" ></TD>
                        </TR>
                        <TR VALIGN=TOP ALIGN=LEFT>
                        <TH>FROM:</TH>
                        <TD><INPUT TYPE="text" NAME="begin" ></TD>
                        </TR>
                        <TR VALIGN=TOP ALIGN=LEFT>
                        <TH>TO:</TH>
                        <TD><INPUT TYPE="text" NAME="end" ></TD>
                        </TR>
                        </TABLE>
                        <INPUT TYPE="submit" NAME="rSubmit" VALUE="Submit">
                        </FORM>	
				

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

