<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Generate Module</title>
</head>
<body>
<%@ page import="java.sql.*,
	javax.portlet.ActionResponse.*, 
		javax.swing.*, 
		java.util.*, 
		java.lang.*, 
		java.io.*, 
		java.text.*, 
		java.net.*, 
		org.apache.commons.fileupload.*,
		org.apache.commons.io.*, 
		java.awt.Image.*, 
		java.util.List, 
		javax.imageio.*, 
		java.awt.image.*, 
		oracle.sql.*, oracle.jdbc.*
"%>
<%
String begin = "";
String end = "";
String diagnosis = "";
String name = "";
String address = "";
String phone = "";
String test_date = "";

if( request.getParameter("Submit") != null){
	begin = request.getParameter("begin");
	end = request.getParameter("end");
	diagnosis = request.getParameter("diagnosis");
}

boolean check = (begin.equals("") || end.equals("") || diagnosis.equals(""));	
String classType = (String) session.getAttribute("classtype");
if(session.getAttribute("name") != null && classType.equals("a") && !check){
	out.println("<h3>These specfic users that have been diagnosed with "+diagnosis+" from "+begin+" to "+end+".</h3>");
	
	Connection connection = null;
	String driverName = "oracle.jdbc.driver.OracleDriver";
	String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	try{
		Class drvClass = Class.forName(driverName); 
		DriverManager.registerDriver((Driver) drvClass.newInstance());
		if(session.getAttribute("databaseUser") != null){
			String databaseUser = (String) session.getAttribute("databaseUser");
			String databasePass = (String) session.getAttribute("databasePass");
			connection = DriverManager.getConnection(dbstring, databaseUser, databasePass);
		}
		else
    		connection = DriverManager.getConnection(dbstring,"qali","Edmonton121");
		connection.setAutoCommit(false);
	}
	catch(Exception ex){
    	out.println("<p style=\"color:blue\">" + ex.getMessage() + "</p>");
	}

	PreparedStatement s = null;
	ResultSet resultset = null;
	String sql = "select p.user_name, p.address, p.phone, r.record_id, to_char(r.test_date, 'DD-MON-YYYY') as test_date " +
		"from radiology_record r,persons p where r.patient_name = p.user_name and " +
		"r.diagnosis = ? and r.test_date BETWEEN ? and ? ORDER BY r.test_date asc";
	try{
		s = connection.prepareStatement(sql);
		s.setString(1, diagnosis);
		s.setString(2, begin);
		s.setString(3, end);
        resultset = s.executeQuery();
	}
    catch(Exception ex){
        out.println("<p style=\"color:blue\">" + ex.getMessage() + "</p>");
	}

    ArrayList<String> list=new ArrayList<String>();
	while(resultset != null && resultset.next()){
		name = (resultset.getString(1)).trim();
		address = (resultset.getString(2)).trim();
		phone = (resultset.getString(3)).trim();
		test_date = (resultset.getString(5)).trim();
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
			<p></p>
			<%
		}
	}	
	try{
    	connection.close();
 	}
    catch(Exception ex){
   		out.println("<p style=\"color:blue\">" + ex.getMessage() + "</p>");
    }
}
else if(session.getAttribute("name") != null && classType.equals("a") && check){
	out.println("<p style=\"color:blue\">Invalid Input</p>");
}
else if(session.getAttribute("name") != null){
	out.println("<p style=\"color:blue\">Only administrator has the authority to genearte reports.</p>");
}
else {
	out.println("<p style=\"color:blue\">You are not signed in.</p>");
}
%>
</body>
</html>