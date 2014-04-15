<!-- Copyright (C) 2014 Alice (Mingxun) Wu

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. -->
<HTML>
<HEAD>


<TITLE>Search Engine</TITLE>
</HEAD>

<BODY>


<%@ page import="java.sql.*,
	javax.portlet.ActionResponse.*, 
	javax.swing.*, 
	java.util.*, 
	java.text.*" %>
<%
/*
	String role = (String) session.getAttribute("PermissionLevel");
	Integer personId = (Integer) session.getAttribute("Person_Id");
        
	if (role == null || personId == null){
		response.sendRedirect("login.jsp");
    }*/
        	
	        
	/* Instantiate ArrayList for accmodating query results*/	
	ArrayList<String> rids = new ArrayList<String>();
	ArrayList<String> pids = new ArrayList<String>();
	ArrayList<String> dids = new ArrayList<String>();
	ArrayList<String> rdids = new ArrayList<String>();
	ArrayList<String> types = new ArrayList<String>();
	ArrayList<String> pdates = new ArrayList<String>();
	ArrayList<String> tdates = new ArrayList<String>();
	ArrayList<String> diags = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();
        
	out.println("<form action=search.jsp>");
	out.println("<input type=submit name=Back value='Go Back'><br><br>");
	out.println("</form>");
	out.println("<b>Find out more help information by clicking "
		+ "<a href='help.html#search' target='blank'>Help</a></b><br><br>");


	out.println("<form action=search.jsp>");
	out.println("<input type=text name=KeyWord align=right > " 
		+ "Enter keywords. If entering multiple keywords please use " 
		+ "'and' or 'or' as delimeters.<br>");
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
	out.println("<input type=submit name=Generate value='Go'><br>");
	out.println("</form>");
    
	/* 
	* before executing any search queries. We need to build a 
	* materialized view and related indecies
	*/

	Connection conn = null;	
	String driverName = "oracle.jdbc.driver.OracleDriver";
	String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";

	try {
		Class drvClass = Class.forName(driverName); 
		DriverManager.registerDriver((Driver) drvClass.newInstance());
	}catch(Exception ex){
		out.println("<hr>" + ex.getMessage() + "<hr>");
	}

	try {
		conn = DriverManager.getConnection(dbstring,"qali", 
			"Edmonton121");
		conn.setAutoCommit(false);
	}catch(Exception ex){
		out.println("<hr>" + ex.getMessage() + "<hr>");
	}

	Statement stmt = null;
	
	try{
		stmt = conn.createStatement();
	}catch(Exception ex){
		out.println("<hr>" + ex.getMessage() + "<hr>");
	}
    
	ResultSet rset = null;
	/*
	if(request.getParameter("Back") != null){
		try{
			conn.close();
		}catch(Exception ex){
			out.println(ex.getMessage());
		}

		if(role.equals("a")){
			response.sendRedirect("adminhomepage.jsp");	
		}else{
			response.sendRedirect("homepage.jsp");	
		}

	}else */
	if(request.getParameter("Generate") != null){
		
		/* compose the select columns , listing all columns that need
		 * to appear in the result table 
		 */
		String selectcols = "select record_id, rp.patient_id, rp.doctor_id,"
				+"radiologist_id,test_type, prescribing_date, "
				+"test_date, diagnosis, description";
		
		/* first update indexes to make sure the search is accurate*/
		String alterIndex1 = "ALTER index index_des REBUILD";
		String alterIndex2 = "ALTER index index_dia REBUILD";
		String alterIndex3 = "ALTER index index_name REBUILD";
		
    	
		/* validate the format of date */
		String from = request.getParameter("Start");
		String to = request.getParameter("End");
		String keyword = request.getParameter("KeyWord");
		
		if((keyword == null || keyword.equals("")) 
				&& (from == null || from.equals(""))
				&& (to == null || to.equals(""))){
			try {
				conn.close();
			}catch(Exception ex){
				out.println("<hr>" + ex.getMessage() + "<hr>");
			}
			JOptionPane.showMessageDialog(null,
					"Enter at least keywords or date period please!");
			response.sendRedirect("search.jsp");	
		}
		
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MMM-yyyy");
		sdformat.setLenient(false);

	   	try{
			if(from != null && !from.isEmpty() && !from.equals(""))
				sdformat.parse(from);
			
			if(to != null && !to.isEmpty() && !to.equals(""))
				sdformat.parse(to);
	   	}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Please check the date " 
	   			+ "format, make sure it's in dd-MMM-yyyy");   
			try{
				conn.close();
			}catch(Exception ex1){
				out.println(ex1.getMessage());
			}
			response.sendRedirect("search.jsp");
			return;
	   	}
                
		String order = request.getParameter("Order");
		String sqlOrder = "";
        
		if(order != null && order.equals("new")){

			sqlOrder = " Order by TEST_DATE DESC";        

		}else if (order != null && order.equals("old")){

			sqlOrder = " Order by TEST_DATE ASC";
		}else if((order == null || order.isEmpty()) 
				&& keyword != null && !keyword.isEmpty()){
        	
			sqlOrder = "Order by score(1) + 3 * score(2) + 6 * score(3) " +
				"desc";
		}
        
		String select = "";
		String where = "";
		
        
		/* build select from clause*/
		/*
		if(role.equals("p")){
			select  = selectcols + " from rp where patient_id='" 
				+ personId + "' and ";
		}else if (role.equals("r")){

			select  = selectcols + " from rp where radiologist_id='" 
			+ personId + "' and ";
		}else if (role.equals("d")){

			select  = selectcols + " from rp, family_doctor "
			+ "where rp.doctor_id ='" + personId + "' and "
			+ " family_doctor.doctor_id = rp.doctor_id "
			+ "and family_doctor.patient_id = rp.patient_id and ";
		}else{

			select  = selectcols + " from rp where ";
		}*/
		
		select  = selectcols + " from rp where ";
		

		/* add contains clause */
		if(keyword != null && !keyword.isEmpty() && !keyword.equals(""))
			where += "(contains(description, '" + keyword + "', 1) > 0" +
				" or contains(diagnosis, '" + keyword + "', 2) > 0" +
				" or contains(patient_name, '" + keyword + "', 3) > 0) ";
      	
		
		/* add test_date selection constraint */
		if(from != null && !from.isEmpty() && !from.equals("")){
			where = (where.length() > 0) ? where + " and " : where;
			where += " test_date >='" + from + "' ";
		}
			
		if(to != null && !to.isEmpty() && !to.equals("")){
			where = (where.length() > 0) ? where + " and " : where;
			where += " test_date<'" + to + "' "; 
		}
		select += where;
		
		/* add order by clause*/
		select += sqlOrder;
		
		
		try{
			stmt.execute(alterIndex1);
			stmt.execute(alterIndex2);
			stmt.execute(alterIndex3);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
			try{
				conn.close();
			}catch(Exception ex1){
				out.println(ex1.getMessage());
			}
			response.sendRedirect("search.jsp");
			return;
		}
        
		/* execute select query*/
		
		try{
			rset = stmt.executeQuery(select);
		}catch(SQLException ex){
			
			/* if the user uses and or or as their keyword, this error 
			 * will pop up. And the system will inform the user
			 */
			if(ex.getErrorCode() == 20000){
				JOptionPane.showMessageDialog(null, "Invalid search word. "
				+ "Please make sure you only use 'and' or 'or' "
				+ "as your delimiter.");
			}else{
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			try{
				conn.close();
			}catch(Exception ex1){
				out.println(ex1.getMessage());
			}
			response.sendRedirect("search.jsp");
			return;
		}
		
		/* ready to print the result table */
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

		/* retrieve the result */
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
		
		/* put the result into table*/
		for(int i = 0; i < rids.size(); i++){
			String pdate = (pdates.get(i) == null 
				|| pdates.get(i).isEmpty()) ?
				pdates.get(i) : pdates.get(i).substring(0, 10);
			String tdate = (tdates.get(i) == null
				|| tdates.get(i).isEmpty()) ?
				tdates.get(i) : tdates.get(i).substring(0, 10);

			out.println("<tr><td>"+ rids.get(i) + "</a></td>");
			out.println("    <td>"+ pids.get(i)+ "</a></td>");
			out.println("    <td>"+ dids.get(i) +"</a></td>");
			out.println("    <td>"+ rdids.get(i) +"</a></td>");
			out.println("    <td>"+ types.get(i) +"</a></td>");
			out.println("    <td>"+ pdate +"</a></td>");
			out.println("    <td>"+ tdate +"</a></td>");
			out.println("    <td>"+ diags.get(i) +"</a></td>");
			out.println("    <td>"+ description.get(i) +"</a></td>");
			
			String getPics = "select image_id from pacs_images " +
				"where record_id='" + rids.get(i) + "'";
			rset = stmt.executeQuery(getPics);
			out.println("<td>");
		    
			/* for each result, get all related images and call GetOnePic.class
			 * to display them.
			 */
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
		
		/* close connection*/
		try{
			conn.close();
		}catch(Exception ex){
			out.println(ex.getMessage());
		}
              	
	}else{
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
        
		/* build select from clause, varing as the person's class varies*/
		/*
		if(role.equals("p")){
			select += " from radiology_record where patient_id='" 
				+ personId + "'";
		}else if (role.equals("r")){

			select  += " from radiology_record where " 
			+ "radiologist_id='" + personId + "'";
		}else if (role.equals("d")){

			select  += " from radiology_record, family_doctor "
				+ "where radiology_record.doctor_id='" + personId + "' and"
				+ " radiology_record.doctor_id = family_doctor.doctor_id and"
				+ " radiology_record.patient_id = family_doctor.patient_id";
		}else{

			select  = selectcols + " from radiology_record";
		}
		*/
		select  = selectcols + " from radiology_record";
		select += " order by record_id";
		
		/* execute query to get all records */
        try{
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
				response.sendRedirect("search.jsp");
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

</BODY>
</HTML>
