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
                        <td><input TYPE="text" required NAME="diagnosis" ></td>
                        </tr>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <th>From:</th>
                        <td><input TYPE="date" required NAME="fDate" placeholder="YYYY-MM-DD"></td>
                        </tr>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <th>To:</th>
                        <td><input TYPE="date" required NAME="tDate" placeholder="YYYY-MM-DD"></td>
                        </tr>
                        </table>
                        <br>
                        <input class="btn btn-primary btn-large" TYPE="submit" NAME="rSubmit" VALUE="Search">
                        </form>	
		</div>
		<div class="hero-unit" style="text-align:center">
		<br>
			<%

			ArrayList<RadiologyRecord> reports  = new ArrayList<RadiologyRecord>();
			if (request.getAttribute("reports") != null) {
				reports  = (ArrayList<RadiologyRecord>) request.getAttribute("reports");
			}
			else {

				reports = new ArrayList<RadiologyRecord>();
				Db database = new Db();
				ArrayList<Record> records = database.getAllDiagnosisReports();
				for (Record r : records) {
					User patient = database.getUser(r.getPatientID()); 
					reports.add(new RadiologyRecord(patient, r));
				}
				database.close();
			}

			if(!reports.isEmpty()) {
				
				out.println("<h1>Results</h1>");
				out.println("<table>");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>First Name</th>");
				out.println("<th>Last Name</th>");
				out.println("<th>Address</th>");
				out.println("<th>Phone Number</th>");
				out.println("<th>Diagnosis</th>");
				out.println("<th>Testing Date</th>");
				out.println("</tr>");
				out.println("</thead>");

				out.println("<tbody>");
				for (RadiologyRecord r : reports) {
					out.println("<td>" + r.getUser().getFirstName() + "</td>");
					out.println("<td>" + r.getUser().getLastName() + "</td>");
					out.println("<td>" + r.getUser().getAddress() + "</td>");
					out.println("<td>" + r.getUser().getPhone() + "</td>");
					out.println("<td>" + r.getRecord().getDiagnosis() + "</td>");
					out.println("<td>" + r.getRecord().getTestDate() + "</td>");
					out.println("</tr>");
				}
				out.println("</tbody>");
			}

			%>
			
			</table>
			
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

