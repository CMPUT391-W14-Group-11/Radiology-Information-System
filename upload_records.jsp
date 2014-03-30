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
		<h1>Add Record</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
                <span style="color:red;">${param.error}</span>
                <span style="color:black;">${param.message}</span>
			<form method="POST" action="upload" />
			<table>
				<TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Patient Username:</TH>
                                <TD><INPUT required TYPE="text" NAME="p_username">
                                </TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Doctor Username:</TH>
                                <TD><INPUT required TYPE="text" NAME="d_username"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Test Type:</TH>
                                <TD><INPUT TYPE="text" NAME="test_type"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Prescribing Date:</TH>
                                <TD><INPUT TYPE="date" NAME="prescription_date"  placeholder="YYYY-MM-DD"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Test Date:</TH>
                                <TD><INPUT TYPE="date" NAME="test_date"  placeholder="YYYY-MM-DD"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Diagnosis:</TH>
                                <TD><INPUT TYPE="text" SIZE="39" NAME="diagnosis"></TD>
                                </TR>
                                <TR VALIGN=TOP ALIGN=LEFT>
                                <TH>Description:</TH>
                                <TD><TEXTAREA COLS="50" ROWS="5" NAME="description"></TEXTAREA></TD>
                                </TR>

                                <tr>
                                <th>Upload: </th>
                                <td>
                                <input name="filepath" type="file" size="30" ></input>
                                </td>
                                </tr>
                                </table>
                                <br>
                                <INPUT TYPE="submit" NAME="save_record" VALUE="Submit Record">
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

