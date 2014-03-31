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
		<span style="color:red;">${param.error}</span>
		</div>
		<% if(doctors != null) {
			int d = doctors.get(0);
			database = new Db();
			for(int doctor_id : doctors) {
				ArrayList<User> patients = database.getPatients(doctor_id);
				User doc = database.getUser(doctor_id);
				out.println("<span style=&quot;text-align:left&quot;>");
				out.println("<h2>Dr. " + doc.getLastName() + ", " + doc.getFirstName() + "</h2>");
				out.println("</span>");
				out.println("<table id='" + doc.getPersonID() + "-table'>");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Username</th>");
				out.println("<th>First Name</th>");
				out.println("<th>Last Name</th>");
				out.println("<th>Address</th>");
				out.println("<th>Email</th>");
				out.println("<th>Phone Number</th>");
				out.println("<th>Select</th>");
				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>");
				out.println("<form action='users' method='POST'>");
				for (User u : patients) {
					out.println("<td name='" + doc.getPersonID() + "-patientUsernames'>" + u.getUsername() + "</td>");
					out.println("<td>" + u.getFirstName() + "</td>");
					out.println("<td>" + u.getLastName() + "</td>");
					out.println("<td>" + u.getAddress() + "</td>");
					out.println("<td>" + u.getEmail() + "</td>");
					out.println("<td>" + u.getPhone() + "</td>");
					out.println("<td><input name='removePatient' type='checkbox' value='" + doc.getPersonID() + "'></td>");
					out.println("</tr>");
				}
				out.println("</tbody>");
				out.println("</table>");
				out.println("<br>");

				out.println("<div class='container' vertical-align='middle'>");
				
				out.println("<input placeholder='Patient Username or ID' style='float:left; margin-left:0px''  name='addPatient-"+ doc.getPersonID() +"'>");
				out.println("<input type='submit' name='addTo-" + doc.getPersonID() + "' style='float:left; margin-left:10px' value='Add Patient'>");

				out.println("<input type='submit' value='Delete' style='float: right; margin-right: 20px' name='deletePatient-"+ doc.getPersonID());
				out.println("</div>");
				out.println("</form>");

			}
			database.close();
		}
		%>	
			
	<br>	
	</div>
	<%@ include file="/layout/footer.jsp"%>
</body>
</html>

