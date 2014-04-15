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
	<div class="container">
		<h1>Generate Reports</h1>

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit" style="text-align: center">

		</div>
	</div>
	
	<form method="GET" action="reportg.jsp" />
			<table>
			<p></p>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <span style="color:red;">${param.message}</span>
                        <th>Diagnosis:</th>
                        <td><input TYPE="text" required NAME="diagnosis" ></td>
                        </tr>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <th>From:</th>
                        <td><input TYPE="date" required NAME="fDate" placeholder="DD-MON-YYYY(09-DEC-2013)"></td>
                        </tr>
                        <tr VALIGN=TOP ALIGN=LEFT>
                        <th>To:</th>
                        <td><input TYPE="date" required NAME="tDate" placeholder="DD-MON-YYYY(10-DEC-2013)"></td>
                        </tr>
                        </table>
                        <br>
                        <input class="btn btn-primary btn-large" TYPE="submit" NAME="rSubmit" VALUE="Search">
                        </form>	
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
String title = "Report Findings";
%>
<%
//Initialize variables
String begin = "";
String end = "";
String diagnosis = "";
String name = "";
String address = "";
String phone = "";
String test_date = "";

if( request.getParameter("rSubmit") != null){
begin = request.getParameter("fDate");
end = request.getParameter("tDate");
diagnosis = request.getParameter("diagnosis");
/*}

boolean check = (begin.equals("") || end.equals("") || diagnosis.equals(""));	
String classType = (String) session.getAttribute("classtype");
if(!check){*/
//Display title
out.println("<h3>Here are the users that have been diagnosed with "+diagnosis+" from "+begin+" to "+end+".</h3>");


//establish the connection to the underlying database
Connection conn = null;
     String driverName = "oracle.jdbc.driver.OracleDriver";
     String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
     try{
         //load and register the driver
Class drvClass = Class.forName(driverName);
     DriverManager.registerDriver((Driver) drvClass.newInstance());
     //Check for custom database info
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
         out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
     }

     //query the database for any records that fit the form
PreparedStatement stmt = null;
     ResultSet rset = null;
String sql = "select p.first_name, p.address, p.phone, r.record_id, to_char(r.test_date, 'DD-MON-YYYY') as test_date " +
"from radiology_record r,persons p where p.person_id= r.patient_id and " +
"r.diagnosis = ? and r.test_date BETWEEN ? and ? ORDER BY r.test_date asc";
     try{
     stmt = conn.prepareStatement(sql);
     stmt.setString(1, diagnosis);
     stmt.setString(2, begin);
     stmt.setString(3, end);
rset = stmt.executeQuery();
     }
        catch(Exception ex){
out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
     }

        ArrayList<String> list=new ArrayList<String>();
     while(rset != null && rset.next()){
     name = (rset.getString(1)).trim();
     address = (rset.getString(2)).trim();
     phone = (rset.getString(3)).trim();
     test_date = (rset.getString(5)).trim();
     if(!list.contains(name)){
     list.add(name);
     %>
     <TABLE>
     <TR VALIGN=TOP ALIGN=LEFT>
     <TH>Patient Name:</TH>
     <TD><%=name%></TD>
     </TR>
     <TR VALIGN=TOP ALIGN=LEFT>
     <TH>Address:</TH>
     <TD><%=address%></TD>
     </TR>
     <TR VALIGN=TOP ALIGN=LEFT>
     <TH>Phone:</TH>
     <TD><%=phone%></TD>
     </TR>
     <TR VALIGN=TOP ALIGN=LEFT>
     <TH>Test Date:</TH>
     <TD><%=test_date%></TD>
     </TR>
     </TABLE>
     <p>_________________________________________</p>
     <%
     }
     }	
	try{
         conn.close();
      }
        catch(Exception ex){
        out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
        }
}

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
</BODY>
</HTML>