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
<%@ page import="entities.*, java.util.ArrayList, java.util.List"%>

<body>
	<%@ include file="/layout/nav_bar.jsp"%>
	<div class="container">

	<div class="container">
		

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
		<h1>User Registration</h1>
			<form name="register" method="POST" action="register"/>
			<table>
				<tr>
					<td><label for="username">Username:</label></td>
					<td><input type="text" size="24" name="username" required
						title="Username " value=${Username}></td><span style="color:red;">${param.error2}</span>
				</tr>

				<tr>
					<td><label for="password">Password:</label></td>
					<td><input type="password" size="24" name="password" id="password"
						required value=${Password}></td>
				</tr>

				<tr>
					<td><label for="password2">Confirm Password:</label></td>
					<td><input required type="password" size="24" name="password2" 
						id="password2" onkeyup="checkPass(); return false;" value=${Password}> <span
						id="confirmMessage" class="confirmMessage"></span></td>
				</tr>

				<tr>
					<td><label for="class">Class: </label></td>
					<td><select name="class" required>
						<option disabled selected>-- Assign Role --</option>
						<option value="a" ${SelectA}>Administrator</option>
						<option value="p" ${SelectP}>Patient</option>
						<option value="d" ${SelectD}>Doctor</option>
						<option value="r" ${SelectR}>Radiologist</option>
					</select></td>
				</tr>

			</table>
		<h2>Personal Information</h2>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
			
			<table>

				<tr>
					<td><label for="first-name">First Name:</label></td>
					<td><input type="text" size="24" name="first-name" required
						title="First Name " value=${FirstName}></td>
				</tr>

				<tr>
					<td><label for="last-name">Last Name:</label></td>
					<td><input type="text" size="24" name="last-name" required
						title="Last Name " value=${LastName}></td>
				</tr>

				<tr>
					<td style="vertical-align: middle;"><label for="address">Address:</label></span></td>
					<td colspan="2"><textarea maxlength="128" class="form-control" rows="4"
							cols="5" name="address" >${Address}</textarea></td>
				</tr>

				<tr>
					<td><label for="email">Email Address:</label></td>
					<td><input required type="email" size="128" name="email" id="email" value=${Email} />
					<span style="color:red;">${param.error1}</span>
					</td>
				</tr>
				<tr>
					<td><label for="phone">Phone Number:</label></td>
					<td><input type="tel" name="phone" size="10" id="phone" value=${Phone}></td>
				</tr>
			</table>
			<br> <br> 
			<input type="hidden" id="person_id" name="person_id" value=${PersonID}>
			<input type="submit" name="demand" value="Save">

			</form>

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

