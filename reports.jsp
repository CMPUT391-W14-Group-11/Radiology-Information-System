<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Radiology Information System Home">
<meta name="author" content="">

<%@ page import="entities.*, java.util.ArrayList, java.util.List"%>

</head>

<%@ include file="/layout/headlib.jsp"%>
<body>
	<%@ include file="/layout/nav_bar.jsp"%>
	<div class="container">
		

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
		<h1>Patient Reports</h1>
			<form method="GET" action="reports" />
			<table>
			<p></p>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <span style="color:red;">${param.message}</span>
                        <th>Diagnosis:</th>
                        <td><input TYPE="text" NAME="diagnosis" ></td>
                        </tr>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <th>From:</th>
                        <td><input TYPE="date" NAME="fDate" placeholder="YYYY-MM-DD"></td>
                        </tr>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <th>To:</th>
                        <td><input TYPE="date" NAME="tDate" placeholder="YYYY-MM-DD"></td>
                        </tr>
                        </table>
                        <br>
                        <input TYPE="submit" NAME="rSubmit" VALUE="Submit">
                        </form>	
		</div>

		<div class="hero-unit" style="text-align:center">
		
		<h1>Results</h1>
			<table id="user-table">
			<thead>
			<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Address</th>
			<th>Phone Number</th>
			<th>Testing Date</th>
			</tr>
			</thead>
			<%

			ArrayList<RadiologyRecord> reports  = (ArrayList<RadiologyRecord>) request.getAttribute("reports");

			if(reports != null) {
				
				out.println("<tbody>");
				for (RadiologyRecord r : reports) {
					out.println("<td>" + r.getUser().getFirstName() + "</td>");
					out.println("<td>" + r.getUser().getLastName() + "</td>");
					out.println("<td>" + r.getUser().getAddress() + "</td>");
					out.println("<td>" + r.getUser().getPhone() + "</td>");
					out.println("<td>" + r.getRecord().getTestDate() + "</td>");
					out.println("</tr>");
				}
				out.println("</tbody>");
			}
			else {
				out.println("<p>No patients to display</p>");
				out.println("<td></td>");
				out.println("<td></td>");
				out.println("<td></td>");
				out.println("<td></td>");
				out.println("<td></td>");
				out.println("</tr>");
			}

			%>
			
			</table>
			
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

