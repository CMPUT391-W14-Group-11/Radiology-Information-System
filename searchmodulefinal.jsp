<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Reports</title>
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
	
	if(session.getAttribute("name") != null && request.getParameter("Submit") != null){
    	if(request.getParameter("order").equals("Rank") && request.getParameter("keywords").equals("")){
    		out.println("<p style=\"color:blue\">Keywords for search!</p>");
    	}
    	else{
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
    		catch(Exception e){
        		out.println("<p style=\"color:blue\">" + e.getMessage() + "</p>");
    		}
    	
    		String sql = "select ";
    		String startDate = request.getParameter("startdate");
    		String endDate = request.getParameter("enddate");
    		String keywords = request.getParameter("keywords");
    	    	
    		ArrayList<String> view = new ArrayList<String>();
    		if(!startDate.equals(""))
    			view.add("test_date >= ? ");
    		if(!endDate.equals(""))
    			view.add("test_date <= ? ");
    		if(!keywords.equals("")){ 	
    			view.add("contains(r.patient_name, ?, 1) >= 0 and contains(r.diagnosis, ?, 2) >= 0 "+
    						"and contains(r.description, ?, 3) >= 0 ");
    			String score = "score(1)*6 + score(2)*3 + score(3) as rank, ";
    			sql = sql.concat(score);
    		}
    		String userName = (String) session.getAttribute("name");
			String classtype = (String) session.getAttribute("classtype");
			if(classtype.equals("r"))
				view.add("r.radiologist_name = ? ");
			else if(classtype.equals("d"))
				view.add("? IN (SELECT d.doctor_name from family_doctor d where d.patient_name = r.patient_name) ");
			else if(classtype.equals("p"))
				view.add("r.patient_name = ? ");
    		String orderBy = "";
    		if(request.getParameter("order").equals("Rank"))
    			orderBy = "rank DESC";
    		else if(request.getParameter("order").equals("Most Recent First"))
    			orderBy = "r.test_date DESC";
    		else if(request.getParameter("order").equals("Most Recent Last"))
    			orderBy = "r.test_date ASC";
    		String start = "r.record_id, r.patient_name, r.doctor_name, r.radiologist_name, r.test_type, "+
    			"to_char(r.prescribing_date, 'DD-MON-YYYY') as prescribing_date, to_char(r.test_date, 'DD-MON-YYYY') as test_date, "+
    			"r.diagnosis, r.description from radiology_record r ";
			sql = sql.concat(start);

			boolean first = true;
			for (String value : view){
				if(first){
					first = false;
					sql = sql.concat("WHERE "+ value);
				}
				else
					sql = sql.concat(" AND "+ value);
			}		
			sql = sql.concat("ORDER BY "+orderBy);
			PreparedStatement s = null;
    		ResultSet resultset = null;
			try{
				s = connection.prepareStatement(sql);
				int i = 1;
				if(!startDate.equals(""))
					s.setString(i++, startDate);
	    		if(!endDate.equals(""))
	    			s.setString(i++, endDate);
	    		if(!keywords.equals("")){
	    			s.setString(i++, keywords);
	    			s.setString(i++, keywords);
	    			s.setString(i++, keywords);
	    		}
	    		if(!classtype.equals("a"))
	    			s.setString(i++, userName);

	        	resultset = s.executeQuery();
	            ResultSetMetaData resultmeta = resultset.getMetaData();
	            int coloumns = resultmeta.getColumnCount();
	            int check = 1;
	            if(!keywords.equals(""))
	            	check = 2;
	            out.println("<table id=\"border\"><tr>");
	            for (int j=check; j<= coloumns; j++) { 
					out.println("<th id=\"border\">"+resultmeta.getColumnName(j)+"</th>");
	            }
	            out.println("<tr>");
	           	while(resultset != null && resultset.next()){
	           		if(!(!keywords.equals("") && resultset.getInt(1) == 0)){
	           			int recid = 2;
	           			if(keywords.equals(""))
	           				recid = 1;
	           			out.println("<tr>");
	           			for(int k=check;k<=coloumns; k++) {
	           				out.println("<td id=\"border\">"+(resultset.getString(k)).trim()+"</td>");
	           			}
	           			out.println("</tr>");
	           			Statement picStmt = connection.createStatement();
	           			String pquery = "select image_id from pacs_images where record_id = "+resultset.getString(recid);
	           			ResultSet picset = picStmt.executeQuery(pquery);
	           			if(picset != null && picset.next()){
	           				out.println("<tr><td COLSPAN="+colCount+">Images for record_id "+resultset.getString(recid)+":");
	           				String end = "rec="+resultset.getString(recid)+"&pic="+picset.getString(1);
           					out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
           					out.println("<img src=\"GetOnePic?"+end+"\" height=\"45\" width=\"60\"></a>");
	           				while(picset.next()){
	           					end = "rec="+resultset.getString(recid)+"&pic="+picset.getString(1);
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
        		connection.close();
     		}
        	catch(Exception ex){
       			out.println("<p style=\"color:red\">" + ex.getMessage() + "</p>");
        	}
    	}
	}
	else{
		out.println("<p style=\"color:red\">You are not signed in.</p>");
	}

%>
</body>
</html>