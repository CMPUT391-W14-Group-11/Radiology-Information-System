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
<% 
	Db database = new Db();
	ArrayList<User> users = database.getUserAccounts(); 
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
		<h1>Registered Users</h1>
		<br>
		<span style="color:black;">${param.message}</span>
		<div>
			<form method="POST" action="users">
			<span style="color:red;">${param.error}</span>
			<input name="editUser" type="text" method="POST" placeholder="Enter username" style="width:90%; height:30px;">

			<button class="button blue" type="submit" name="searchUser">Search</button>
			</form>
		</div>
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
			<th>Profile</th>
			</tr>

			</thead>
			<br>
			<tbody>
			<form>   
			<%
			for (User u : users) {

				out.println("<td>" + u.getUsername() + "</td>");
				out.println("<td>" + u.getFirstName() + "</td>");
				out.println("<td>" + u.getLastName() + "</td>");
				out.println("<td>" + u.getUserClass() + "</td>");
				if(u.getAddress() != null) {
					out.println("<td>" + u.getAddress() + "</td>");
				}
				else {
					out.println("<td></td>");
				}
				if(u.getAddress() != null) {
					out.println("<td>" + u.getEmail() + "</td>");
				}
				else {
					out.println("<td></td>");
				}
				if(u.getAddress() != null) {
					out.println("<td>" + u.getPhone() + "</td>");
				}
				else {
					out.println("<td></td>");
				}	
				out.println("<td><button name='editUser' type='submit' class='button' value='" + u.getPersonID() + "'>Edit</td>");
				out.println("</tr>");
			}
			%>
			</form>
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

