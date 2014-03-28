<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Radiology Information System Home">
<meta name="author" content="">

<%@ page import="java.util.ArrayList, java.util.List"%>
<% 
	ArrayList<String> usernames = (ArrayList<String>)request.getAttribute("usernames");
	ArrayList<String> firstnames = (ArrayList<String>)request.getAttribute("firstnames");
	ArrayList<String> lastnames = (ArrayList<String>)request.getAttribute("lastnames");
	ArrayList<String> classes = (ArrayList<String>)request.getAttribute("classes");
	ArrayList<String> addresses = (ArrayList<String>)request.getAttribute("addresses");
	ArrayList<String> emails = (ArrayList<String>)request.getAttribute("emails");
	ArrayList<String> phones = (ArrayList<String>)request.getAttribute("phones");

	int arraySize = usernames.size();
%>

</head>

<%@ include file="/layout/headlib.jsp"%>
<body>
	<%@ include file="/layout/nav_bar.jsp"%>
	<div class="container">

	<div class="container">
		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
		<h1>Registered Users</h1>
			<table id="user-table">
			<thead>
			<tr>
			<th>Username</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>User Class</th>
			<th>Address</th>
			<th>Email</th>
			<th>Phone Number</th>
			<th>Edit</th>
			</tr>

			</thead>
			<tbody>
			<%
			int i = 0;
			while(i < arraySize) {
				out.println("<td>" + usernames.get(i) + "</td>");
				out.println("<td>" + firstnames.get(i) + "</td>");
				out.println("<td>" + lastnames.get(i) + "</td>");
				out.println("<td>" + classes.get(i) + "</td>");
				out.println("<td>" + addresses.get(i) + "</td>");
				out.println("<td>" + emails.get(i) + "</td>");
				out.println("<td>" + phones.get(i) + "</td>");
				out.println("<td><button value='editUser' type='submit' class='button'>View</td>");
				out.println("</tr>");
			}
			%>
			</tbody>

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

