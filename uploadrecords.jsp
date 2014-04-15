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
<body>
	<%@ include file="/layout/nav_bar.jsp"%>
<%@ page import="java.sql.*" %>
<%


/* without any request, the UI section is printed. */
out.println("<form action=uploadrecords.jsp method=post>");
out.println("Patient Id:<br> <input type=text "
+ "name=pid ><br>");
out.println("Doctor Id:<br><input type=text "
+"name=did ><br>");
out.println("Test Type:<br> <input type=text "
+"name=type maxlenght=24 ><br>");
out.println("Prescribing Date:<br><input type=text "
+ "name=pdate >(eg. 02-FEB-2011)<br>");
out.println("Test Date:<br> <input type=text "
+ "name=tdate >(eg. 03-AUG-2012)<br>");
out.println("Diagnosis:<br> <input type=text "
+ "name=diagnosis maxlength=128><br>");
out.println("Description:<br><input type=text "
+ "name=description maxlength=1024>"
+ "<br><br>");
out.println("<input type=submit name=SaveRecord value='Save New "
+ "Record'><br>");
out.println("</form>");
out.println("<hr>");

String error = "";
//String classType = (String) session.getAttribute("classtype");

//if(session.getAttribute("name") != null && request.getParameter("rSubmit") != null && classType.equals("r")){
     //establish the connection to the underlying database
if(request.getParameter("rSubmit") != null){

Connection conn = null;
     String driverName = "oracle.jdbc.driver.OracleDriver";
     String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
     try{
         //load and register the driver
Class drvClass = Class.forName(driverName);
     DriverManager.registerDriver((Driver) drvClass.newInstance());
     //Check for custom database signin
     if(session.getAttribute("dbuser") != null){
     String dbUser = (String) session.getAttribute("dbuser");
     String dbPass = (String) session.getAttribute("dbpass");
     conn = DriverManager.getConnection(dbstring, dbUser, dbPass);
     }
     else
         conn = DriverManager.getConnection(dbstring,"jsurya","ark123et");
conn.setAutoCommit(false);
}
     catch(Exception ex){
         out.println("<hr>" + ex.getMessage() + "<hr>");
     }

     //Get information from request
     String userName = (String) session.getAttribute("name");
String patname = request.getParameter("patname");
String docname = request.getParameter("docname");
String testtype = request.getParameter("testtype");
String presdate = request.getParameter("presdate");
String testdate = request.getParameter("testdate");
String diag = request.getParameter("diag");
String desc = request.getParameter("desc");

     //Select the user table from the underlying db and update persons
     Statement recstmt = conn.createStatement();
ResultSet recrset = recstmt.executeQuery("SELECT record_seq.nextval from dual");
recrset.next();
int recid = recrset.getInt(1);

//Insert record
PreparedStatement stmt = null;
     ResultSet rset = null;
String sql = "insert into radiology_record values("+recid+",?,?,?,?,?,?,?,?)";
try{
stmt = conn.prepareStatement(sql);
     stmt.setString(1, patname);
     stmt.setString(2, docname);
     stmt.setString(3, userName);
     stmt.setString(4, testtype);
     stmt.setString(5, presdate);
     stmt.setString(6, testdate);
     stmt.setString(7, diag);
     stmt.setString(8, desc);
stmt.executeUpdate();

conn.commit();
response.sendRedirect("uploadrecords.jsp?recid="+recid+"&result=recok");
}
     catch(Exception ex){
         error = ex.getMessage();
}

try{
         conn.close();
      }
        catch(Exception ex){
        error = ex.getMessage();
        }
}
else
error = "You are not signed in as a radiologist or did not submit";

if(!error.equals("")){
String title = "Upload Record";
%>
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
<%
}
%>
