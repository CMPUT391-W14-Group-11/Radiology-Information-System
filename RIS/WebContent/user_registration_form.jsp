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

<script type="text/javascript" src="js/password_check.js"></script>

<%@ include file="/layout/headlib.jsp"%>
<body>
	<%@ include file="/layout/nav_bar.jsp"%>
	<div class="container">
		<h1>User Registration Information</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
			<form method="POST" action="/UserRegistrationServlet"/>
			<table>
				<tr>
					<td><label for="username">Username:</label></td>
					<td><input type="text" name="username" required
						title="Username "></td>
				</tr>

				<tr>
					<td><label for="first-name">First Name:</label></td>
					<td><input type="text" name="first-name" required
						title="First Name " /></td>
				</tr>

				<tr>
					<td><label for="last-name">Last Name:</label></td>
					<td><input type="text" name="last-name" required
						title="Last Name " /></td>
				</tr>

				<tr>
					<td><label for="password">Password:</label></td>
					<td><input type="password" name="password" id="password" /></td>
				</tr>

				<tr>
					<td><label for="password2">Confirm Password:</label></td>
					<td><input type="password" name="password2" id="password2"
						onkeyup="checkPass(); return false;" /> <span id="confirmMessage"
						class="confirmMessage"></span></td>
				</tr>

				<tr>
					<td><label for="class">Class: </label></td>
					<td><select name="class" required>
							<option disabled selected>-- Assign Role --</option>
							<option value="a">Administrator</option>
							<option value="p">Patient</option>
							<option value="d">Doctor</option>
							<option value="r">Radiologist</option>
							</select></td>
				</tr>

				<tr>
					<td><label for="address">Address:</label></td>
					<td colspan="4"><textarea class="form-control" rows="4"
											cols="50" name="Address"></textarea></td>
				</tr>

				<tr>
					<td><label for="email">Email Address:</label></td>
					<td><input type="email" name="email" id="email" /></td>
				</tr>
				<tr>
					<td><label for="phone">Phone Number:</label></td>
					<td><input type="text" name="phone" id="phone" /></td>
				</tr>
			</table>
			<br> <br>
			<input type="submit" name="demand" value="Create User">

			</form>
			
		</div>
	</div>
</body>
</html>

