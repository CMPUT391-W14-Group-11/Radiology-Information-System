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
		<h1>Personal Information</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">
			<form method="POST" action="/UserRegistrationServlet"/>
			<table>

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
					<td><label for="address">Address:</label></td>
					<td colspan="2"><textarea class="form-control" rows="4"
							cols="5" name="Address"></textarea></td>
				</tr>

				<tr>
					<td><label for="email">Email Address:</label></td>
					<td><input required type="email" name="email" id="email" /></td>
				</tr>
				<tr>
					<td><label for="phone">Phone Number:</label></td>
					<td><input type="text" name="phone" id="phone" /></td>
				</tr>
			</table>
			<br> <br> <input type="submit" name="demand" value="Save">

			</form>

		</div>
	</div>
</body>
</html>

