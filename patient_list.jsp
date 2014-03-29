<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Radiology Information System Home">
<meta name="author" content="">

<%@ page import="entities.*, java.util.*"%>
<% 
	Db database = new Db();
	ArrayList<Integer> doctors = database.getClassMembers("d"); 
	database.close();
%>

</head>

<%@ include file="/layout/headlib.jsp"%>
<body>
	<%@ include file="/layout/nav_bar.jsp"%>
	<div class="container">

	<div class="container">
		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align:center">
		<h1>Patients</h1>
		<span style="color:black;">${param.message}</span>
		</div>
		<% 
			database = new Db();
			for(int doctor_id : doctors) {
				ArrayList<User> patients = database.getPatients(doctor_id);
				User doc = database.getUser(doctor_id);
				out.println("<span style=&quot;text-align:left&quot;>");
				out.println("<h2>Dr. " + doc.getLastName() + ", " + doc.getFirstName() + "</h2>");
				out.println("</span>");
				out.println("<table>");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Username</th>");
				out.println("<th>First Name</th>");
				out.println("<th>Last Name</th>");
				out.println("<th>User Class</th>");
				out.println("<th>Address</th>");
				out.println("<th>Email</th>");
				out.println("<th>Phone Number</th>");
				out.println("<th>Select</th>");
				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>");
				out.println("<form action=&quot;users&quot; method=&quot;POST&quot;>");

				for (User u : patients) {
					out.println("<td>" + u.getUsername() + "</td>");
					out.println("<td>" + u.getFirstName() + "</td>");
					out.println("<td>" + u.getLastName() + "</td>");
					out.println("<td>" + u.getUserClass() + "</td>");
					out.println("<td>" + u.getAddress() + "</td>");
					out.println("<td>" + u.getEmail() + "</td>");
					out.println("<td>" + u.getPhone() + "</td>");
					out.println("<td><input name='removePatient' type='checkbox' value='" + u.getPersonID() + "'></td>");
					out.println("</tr>");
				}
				out.println("</tbody>");
				out.println("</table>");
				out.println("<br>");
				out.println("<button style='float:right;' name='submitRemoval' type='submit' value='" + doc.getPersonID() + "'>Remove");
				out.println("</form>");
			}
			database.close();
		%>	
			
	<br>	
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

