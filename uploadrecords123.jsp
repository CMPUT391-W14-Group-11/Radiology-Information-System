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
		<h1>Upload Records</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
			<form method="POST" action="/UploadRecordServlet" />
			<table>
				<TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Patient Name:</TH>
                                <TD><INPUT TYPE="text" NAME="patient name"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Doctor Name:</TH>
                                <TD><INPUT TYPE="text" NAME="doctor name"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Test Type:</TH>
                                <TD><INPUT TYPE="text" NAME="testtype"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH COLSPAN=2><I>Valid Date Format "DD-MONTH-YYYY"</I></TH>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Prescribing Date:</TH>
                                <TD><INPUT TYPE="text" NAME="prescription date"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Test Date:</TH>
                                <TD><INPUT TYPE="text" NAME="testdate"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Diagnosis:</TH>
                                <TD><INPUT TYPE="text" SIZE="39" NAME="diagnosis"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Description:</TH>
                                <TD><TEXTAREA COLS="50" ROWS="5" NAME="description"></TEXTAREA></TD>
                                </TR>
                                </table>
                           
                                <INPUT TYPE="submit" NAME="rSubmit" VALUE="Add Record">
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

