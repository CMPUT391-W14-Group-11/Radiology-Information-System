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
	<%@ page import="java.sql.*,
	javax.portlet.ActionResponse.*, 
	javax.swing.*, 
	java.util.*, 
	java.text.*" %>
<%
out.println("<p> SEARCH </p>");
out.println("<form action=search_2.jsp>");
out.println("<input type=text name=KeyWord align=right > "); 
	//+ "Enter keywords. If entering multiple keywords please use " 
	//+ "'and' or 'or' as delimeters.<br>");
out.println("<input type=text name=Start align=right > " 
	+ "From (eg.02-FEB-2012)");
out.println("<input type=text name=End align=right > " 
	+ "To (eg.02-FEB-2012)<br>");
out.println("<p> Please Select The Order of Your Search Result." 
	+ "(If leaving it blank, the result will be sorted by default" 
	+ " order.)</p>");
out.println("<label for=NewToOld> Most Recent First</label>");
out.println("<input type=radio name=Order id=NewToOld value=new>");
out.println("<label for=OldToNew> Least Recent First</label>");
out.println("<input type=radio name=Order id=OldToNew value=old><br>");
out.println("<input type=submit name=Generate value='ENTER'><br>");
out.println("</form>");
// Check if form submitted
//if(session.getAttribute("name") != null && request.getParameter("sSubmit") != null){
if(request.getParameter("sSubmit") != null){
	//If order by rank, keywords must be used
     if(request.getParameter("order").equals("Rank") && request.getParameter("keywords").equals("")){
     	out.println("<p style=\"color:red\">To order by rank, you must give a keyword!</p>");
     }else{
     
    	 //establish the connection to the underlying database
		Connection conn = null;
     	String driverName = "oracle.jdbc.driver.OracleDriver";
     	String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
     	try{
         //load and register the driver
			Class drvClass = Class.forName(driverName);
     		DriverManager.registerDriver((Driver) drvClass.newInstance());
     		//Check for custom database login
     		if(session.getAttribute("dbuser") != null){
     			String dbUser = (String) session.getAttribute("dbuser");
     			String dbPass = (String) session.getAttribute("dbpass");
     			conn = DriverManager.getConnection(dbstring, dbUser, dbPass);
     		}else
         		conn = DriverManager.getConnection(dbstring,"jsurya","ark123et");
			conn.setAutoCommit(false);
		}catch(Exception ex){
         out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
     	}
    
     //The start of the sql query
     String sql = "select ";
     String startDate = request.getParameter("startdate");
     String endDate = request.getParameter("enddate");
     String keywords = request.getParameter("keywords");
    
     //List of clauses for WHERE section
     ArrayList<String> clauses = new ArrayList<String>();
     //Add start and end date clauses
     if(!startDate.equals(""))
     clauses.add("test_date >= ? ");
     if(!endDate.equals(""))
     clauses.add("test_date <= ? ");
     if(!keywords.equals("")){
     //Add CONTAINS and SCRORE for rankings
     clauses.add("contains(r.patient_name, ?, 1) >= 0 and contains(r.diagnosis, ?, 2) >= 0 "+
     "and contains(r.description, ?, 3) >= 0 ");
     String score = "score(1)*6 + score(2)*3 + score(3) as rank, ";
     sql = sql.concat(score);
     }

     //Add extra clause depending on user class
     String userName = (String) session.getAttribute("name");
	String classtype = (String) session.getAttribute("classtype");
	if(classtype.equals("r"))
		clauses.add("r.radiologist_name = ? ");	
	else if(classtype.equals("d"))
		clauses.add("? IN (SELECT d.doctor_name from family_doctor d where d.patient_name = r.patient_name) ");
	else if(classtype.equals("p"))
		clauses.add("r.patient_name = ? ");
    
//Add ordering
     String orderBy = "";
     if(request.getParameter("order").equals("Rank"))
     orderBy = "rank DESC";
     else if(request.getParameter("order").equals("Most-Recent-First"))
     orderBy = "r.test_date DESC";
     else if(request.getParameter("order").equals("Most-Recent-Last"))
     orderBy = "r.test_date ASC";
    
     //Columns for table
     String start = "r.record_id, r.patient_name, r.doctor_name, r.radiologist_name, r.test_type, "+
     "to_char(r.prescribing_date, 'DD-MON-YYYY') as prescribing_date, to_char(r.test_date, 'DD-MON-YYYY') as test_date, "+
     "r.diagnosis, r.description from radiology_record r ";
sql = sql.concat(start);

	//Add in clauses to query
	boolean first = true;
	for (String value : clauses){
	if(first){
		first = false;
		sql = sql.concat("WHERE "+ value);
	}else
		sql = sql.concat(" AND "+ value);
	}	
	
	sql = sql.concat("ORDER BY "+orderBy);

//Set up prepared statement
PreparedStatement stmt = null;
     ResultSet rset = null;
try{
stmt = conn.prepareStatement(sql);
int i = 1;
if(!startDate.equals(""))
stmt.setString(i++, startDate);
if(!endDate.equals(""))
stmt.setString(i++, endDate);
if(!keywords.equals("")){
stmt.setString(i++, keywords);
stmt.setString(i++, keywords);
stmt.setString(i++, keywords);
}
if(!classtype.equals("a"))
stmt.setString(i++, userName);

//Get table column titles
rset = stmt.executeQuery();
ResultSetMetaData rsmd = rset.getMetaData();
int colCount = rsmd.getColumnCount();
int loop = 1;
if(!keywords.equals(""))
loop = 2;
out.println("<table id=\"border\"><tr>");
for (int j=loop; j<= colCount; j++) {
out.println("<th id=\"border\">"+rsmd.getColumnName(j)+"</th>");
}
out.println("<tr>");

//Print rows
while(rset != null && rset.next()){
if(!(!keywords.equals("") && rset.getInt(1) == 0)){
int recid = 2;
if(keywords.equals(""))
recid = 1;
out.println("<tr>");
for(int k=loop;k<=colCount; k++) {
out.println("<td id=\"border\">"+(rset.getString(k)).trim()+"</td>");
}
out.println("</tr>");

//Print pictures corresponding to row
Statement picStmt = conn.createStatement();
String pquery = "select image_id from pacs_images where record_id = "+rset.getString(recid);
ResultSet picset = picStmt.executeQuery(pquery);
if(picset != null && picset.next()){
out.println("<tr><td COLSPAN="+colCount+">Images for record_id "+rset.getString(recid)+":");
String end = "rec="+rset.getString(recid)+"&pic="+picset.getString(1);
            out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
            out.println("<img src=\"GetOnePic?"+end+"\" height=\"45\" width=\"60\"></a>");
while(picset.next()){
end = "rec="+rset.getString(recid)+"&pic="+picset.getString(1);
out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
out.println("<img src=\"GetOnePic?"+end+"\" height=\"45\" width=\"60\"></a>");
}
out.println("</td></tr>");
}
}
}

out.println("</table>");	
}
     catch(Exception ex){
         out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
}

try{
         conn.close();
      }
         catch(Exception ex){
        out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
         }
     }
}
else{
	//out.println("<p style=\"color:red\">You are not signed in.</p>");
	Connection conn = null;
 	String driverName = "oracle.jdbc.driver.OracleDriver";
 	String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
 	ResultSet rset = null;
 	Statement stmt = null;
	ArrayList<String> rids = new ArrayList<String>();
	ArrayList<String> pids = new ArrayList<String>();
	ArrayList<String> dids = new ArrayList<String>();
	ArrayList<String> rdids = new ArrayList<String>();
	ArrayList<String> types = new ArrayList<String>();
	ArrayList<String> pdates = new ArrayList<String>();
	ArrayList<String> tdates = new ArrayList<String>();
	ArrayList<String> diags = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();
 	
 	try{
     //load and register the driver
		Class drvClass = Class.forName(driverName);
 		DriverManager.registerDriver((Driver) drvClass.newInstance());
 		//Check for custom database login
 		if(session.getAttribute("dbuser") != null){
 			String dbUser = (String) session.getAttribute("dbuser");
 			String dbPass = (String) session.getAttribute("dbpass");
 			conn = DriverManager.getConnection(dbstring, dbUser, dbPass);
 		}else
     		conn = DriverManager.getConnection(dbstring,"jsurya","ark123et");
		conn.setAutoCommit(false);
	}catch(Exception ex){
     out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
 	}
	
	String selectcols = "select record_id, patient_id, doctor_id,"
			+"radiologist_id,test_type, prescribing_date, "
			+"test_date, diagnosis, description";
	
	/* before searching, we need to display a table with all 
	 * records that are available to the user
	 */
	out.println("<table BORDER=1>");
	out.println("<tr><td>Record ID</a></td>");
	out.println("    <td >Patient ID</a></td>");
	out.println("    <td >Doctor ID</a></td>");
	out.println("    <td >Radiologist ID </a></td>");
	out.println("    <td >Test Type</a></td>"); 
	out.println("    <td >Prescribing Date</a></td>"); 
	out.println("    <td >Test Date </a></td>"); 
	out.println("    <td >Diagnosis </a></td>"); 
	out.println("    <td >Description</a></td>"); 
	out.println("    <td >Medical Images</a></td>"); 
	
	String select = "select radiology_record.* ";
    
	select  = selectcols + " from radiology_record";
	select += " order by record_id";
	
	/* execute query to get all records */
    try{
    	stmt = conn.createStatement();
    	rset = stmt.executeQuery(select);
	}catch(Exception ex){
		out.println("<hr>" + ex.getMessage() + "<hr>");
	}
	

    /* retrieve the result and put them into a table*/
	while(rset != null && rset.next()){
		rids.add(rset.getString(1));
		pids.add(rset.getString(2));
		dids.add(rset.getString(3));
		rdids.add(rset.getString(4));
		types.add(rset.getString(5));
		pdates.add(rset.getString(6));
		tdates.add(rset.getString(7));
		diags.add(rset.getString(8));
		description.add(rset.getString(9));
	}

	for(int i = 0; i < rids.size(); i++){

		String pdate = (pdates.get(i) == null 
				|| pdates.get(i).isEmpty()) ? 
				pdates.get(i) : pdates.get(i).substring(0, 10);
		String tdate = (tdates.get(i) == null 
				|| tdates.get(i).isEmpty()) 
				? tdates.get(i) : tdates.get(i).substring(0, 10);

		out.println("<tr><td>"+ rids.get(i) + "</a></td>");
		out.println("    <td>"+ pids.get(i)+ "</a></td>");
		out.println("    <td>"+ dids.get(i) +"</a></td>");
		out.println("    <td>"+ rdids.get(i) +"</a></td>");
		out.println("    <td>"+ types.get(i) +"</a></td>");
		out.println("    <td>"+ pdate +"</a></td>"); 
		out.println("    <td>"+ tdate +"</a></td>"); 
		out.println("    <td>"+ diags.get(i) +"</a></td>");
		out.println("    <td>"+ description.get(i) +"</a></td>");
    	
		String getPics = "select image_id from pacs_images where" 
			+ " record_id='" + rids.get(i) + "'";
		try{
			rset = stmt.executeQuery(getPics);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
			try{
				conn.close();
			}catch(Exception ex1){
				out.println(ex1.getMessage());
			}
			response.sendRedirect("search_2_search.jsp");
			return;
		}
		
		out.println("<td>");
    	
		if(rset.next()){
			String pic_id = rset.getString(1);
			out.println("<a href=\"GetOnePic?big"+ pic_id 
					+ "\" target=\"_blank\">");
			out.println("<img src=\"GetOnePic?" + pic_id + "\"></a>");
		}else{
			out.println("N/A");
		}
    	
		while(rset.next()){
			String pic_id = rset.getString(1);
			out.println("<a href=\"GetOnePic?big"+ pic_id 
					+ "\" target=\"_blank\">");
			out.println("<img src=\"GetOnePic?" + pic_id + "\"></a>");

		}
		out.println("</td>");
	}
	out.println("</table>");
	
	try{
		conn.close();
	}catch(Exception ex){
		out.println(ex.getMessage());
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
</body>
</html>
