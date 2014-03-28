<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Sign in &middot; Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Login page for CMPUT 391 Radiology Information System">
<meta name="author" content="">

<%@ include file="/layout/headlib.jsp"%>

<%
   String username = null;
   try{
      username = (String) session.getAttribute("username");
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
   if (username != null) {
      session.removeAttribute("username");
      session.removeAttribute("email");
      session.removeAttribute("firstname");
      session.removeAttribute("lastname");
      session.removeAttribute("address");
      session.removeAttribute("email");
      session.removeAttribute("phone");

      session.setAttribute("error", "You have sucessfully been logged out.");
   } 
   else {
      session.setAttribute("error", "You have not logged in.");
   }
   response.sendRedirect("login.jsp");
%>
</head>

<body>
	<!-- javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
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
