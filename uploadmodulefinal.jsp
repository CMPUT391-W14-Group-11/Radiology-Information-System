<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Radiology Records</title>
</head>
<body>
<html>
<head>
<title>Upload a New Radiology Record</title>
</head>
<body>
<%@ page
	import="java.sql.*,
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
		oracle.sql.*, oracle.jdbc.*"%>
<% 
    
    
    out.println("<form action=homepage.jsp>");
    out.println("<input type=submit name=Back value='Go Back'><br>");
    out.println("</form>");
    Connection connection = null;
    String driverName = "oracle.jdbc.driver.OracleDriver";
    String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
   
    try {
 		Class drvClass = Class.forName(driverName); 
		DriverManager.registerDriver((Driver) drvClass.newInstance());
    }catch(Exception e){
        out.println("<hr>" + e.getMessage() + "<hr>");
    }
    
    try {
   		connection = DriverManager.getConnection(dbstring,"qali",
			"Edmonton121");
   		connection.setAutoCommit(false);
    }catch(Exception e){
		out.println("<hr>" + e.getMessage() + "<hr>");
		return;
    }

    Statement s = null;
    try{
    	s = connection.createStatement();
    }catch (Exception e){
    	out.println("<hr>" + e.getMessage() + "<hr>");
    	return;
    }
    
    ResultSet result = null;
    String sql = "";

    sql = "SELECT COUNT(*) AS NEXT_RID FROM RADIOLOGY_RECORD";
    result = s.executeQuery(sql);
    int recordid = 0;
    
    while(result != null && result.next()){
        recordid = result.getInt("NEXT_RID") + 1;
    }   
   
    Integer id = (Integer) session.getAttribute("Person_Id");

    String personid = "";
    String doctorid = "";
    String testtype = "";
    String prescribedate = "";
    String testdate = "";
    String diagnosis = "";
    String description = "";

    if(request.getParameter("SAVE") != null){   
    	personid = request.getParameter("person_id");
    	doctorid = request.getParameter("doctor_id");
        testtype = request.getParameter("test_type");
        prescribedate = request.getParameter("prescribe_date");
        testdate = request.getParameter("test_date");
        diagnosis = request.getParameter("diagnosis");
        description = request.getParameter("description");
      
        String validPid = "select count(*) from users where person_id = '" 
        	+ personid + "' and class = 'p'";
        String validDid = "select count(*) from users where person_id = '" 
	 		+ doctorid + "' and class = 'd'";
	 
        if (!personid.isEmpty()){
      
	    	try{
	        	int patient_id = Integer.parseInt(personid);	
	    	}catch (Exception ex){
	        	JOptionPane.showMessageDialog(null,"Id are all integars ");
	        	try{
	                connection.close();
				}
				catch(Exception e){
	                out.println("<hr>" + e.getMessage() + "<hr>");
				}
                response.sendRedirect("upload.jsp");
	    	}
	    	
	    	result = s.executeQuery(validPid);
	 
	    	int count = 0;
	   		while(result != null && result.next()){
	        	count =result.getInt(1);
	    	}
	    	
	   		if(count < 1){
	        	JOptionPane.showMessageDialog(null, "Invalid Input");
	        	
	        	try{
	                connection.close();
				}
				catch(Exception e){
	                out.println("<hr>" + e.getMessage() + "<hr>");
				}
	        	
	        	response.sendRedirect("upload.jsp");
	    	}
        }else{
	    	personid = null;      
      	} 	
      
      	if (!doctorid.isEmpty()){
      		try{
	    		int doctor_id = Integer.parseInt(doctorid);	
	 		}catch (Exception ex){
	    		JOptionPane.showMessageDialog(null,"Ids are all integars");
	    		try{
	                connection.close();
				}
				catch(Exception e){
	                out.println("<hr>" + e.getMessage() + "<hr>");
				}
            	response.sendRedirect("upload.jsp");	            	
	 		}
      	
	      	try{	
		 		result = s.executeQuery(validDid);	 
	      	}catch (Exception ex){
	      		out.println("<hr>" + ex.getMessage() + "<hr>");
	      	}
	      	
		 	int count = 0;
		 	while(result != null && result.next()){
		    	count = result.getInt(1);
		 	}
		 	
		 	if(count < 1){
		    	JOptionPane.showMessageDialog(null, "Invalid Input");
		    	try{
	                connection.close();
				}
				catch(Exception ex){
	                out.println("<hr>" + ex.getMessage() + "<hr>");
				}
	           	response.sendRedirect("upload.jsp");
		 	}      
	 	
		}else{
	    	  doctorid = null;	 
    	}
      
      	if (!prescribedate.isEmpty()){
      
	 		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MMM-yyyy");
	 		sdformat.setLenient(false);

	 		try{
	    		sdformat.parse(prescribedate);
	 		}catch(Exception ex){
	    		JOptionPane.showMessageDialog(null,"Check the date "
	 				+ "format,");
	    		try{
                	connection.close();
				}
				catch(Exception e){
                	out.println("<hr>" + e.getMessage() + "<hr>");
				}
	    		response.sendRedirect("upload.jsp");
	 		}
	 
      	} else {
			prescribedate = null;      
      	}
      
      	if (!testdate.isEmpty()){
      
	 		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MMM-yyyy");
	 		sdformat.setLenient(false);

		 	try{
		    	sdformat.parse(prescribedate);
		 	}catch(Exception ex){
		 	   JOptionPane.showMessageDialog(null,"Date format is dd-MMM-yyyy");
		 	  	try{
	              connection.close();
				}
				catch(Exception e){
	              out.println("<hr>" + e.getMessage() + "<hr>");
				}
	            response.sendRedirect("upload.jsp");
	      	}  
	    }else{
		 	testdate = null;      
	    }
	      
      	PreparedStatement insertrec = null;
      
     	String insertSql = "insert into radiology_record" 
	    	+ " (record_id, patient_id, doctor_id, radiologist_id, test_type, "
	    	+ "prescribing_date, test_date, diagnosis, description) values)";
      	insertrec = connection.prepareStatement(insertSql);
      	insertrec.setInt(1, recordid);
      	insertrec.setString(2, personid);
      	insertrec.setString(3, doctorid);
      	insertrec.setInt(4, id);
      	insertrec.setString(5, testtype);
      	insertrec.setString(6, prescribedate);
      	insertrec.setString(7, testdate);
      	insertrec.setString(8, diagnosis);
      	insertrec.setString(9, description);
      
      	try{
	 		insertrec.executeUpdate();
	 		connection.commit();
	 		session.setAttribute("Saved_Record_Id", recordid);
	 		response.sendRedirect("/proj1/upload.jsp");
      	}catch(Exception ex){
	 		out.println("<hr>" + ex.getMessage() + "<hr>");      
      	}
   
	}else if (request.getParameter("Cancel") != null){
		session.removeAttribute("Saved Records");
	   	try{
           connection.close();
		}
		catch(Exception e){
           out.println("<hr>" + e.getMessage() + "<hr>");
		}
	   response.sendRedirect("/proj1/login.jsp");
		   
   	}else if (session.getAttribute("Saved Records") != null){
        out.println("<p>");
	    out.println("<hr>");
	    out.println("Uploading...... " 
	    	+ session.getAttribute("Saved Records") + ".");
	    out.println("Select path to upload and save images");
	    out.println("<form name='upload-image' method=POST "
	    	+ "enctype='multipart/form-data' action='UploadImage'>");
	    out.println("<table>");
	    out.println("<tr>");
	    out.println("<th>File path: </th>");
	    out.println("<td><input accept='image/jpeg,image/gif,image/png,"
	    	+ " image/bmp, image/jpg' name='file-path' type='file' size='30'"
	    	+ " required multiple/></input></td> ");
	    out.println("</tr>");
	    out.println("<tr>");
	    out.println("<td ALIGN=CENTER COLSPAN='2'><input type='submit' "
	    	+"name='.submit' value='Upload'></td>");
	    out.println("</tr>");
	    out.println("</table>");
	    out.println("</form>") ;  
	    out.println("<hr>");
	      
	    out.println("<form action=upload.jsp>");
	    out.println("<input type=submit name=Cancel value='No Images "
	    	+ "To Upload'><br>");
	    out.println("</form>");
	   
	}else{         
      	out.println("<p>Only radiologists have the authority to create and upload records.</p>");
      	out.println("<form action=upload.jsp method=post>");
      	out.println("Patient Id:<br> <input type=text "
      		+ "name=pid value='" + personid + "' ><br>");
      	out.println("Doctor Id:<br><input type=text "
      		+"name=did value='" + doctorid + "'><br>");
      	out.println("Test Type:<br> <input type=text "
	 		+"name=type maxlenght=24 value=\"" + testtype + "\"><br>");
      	out.println("Prescribing Date:<br><input type=text "
	 		+ "name=pdate value=\"" + prescribedate + "\"><br>");
     	out.println("Test Date:<br> <input type=text "
	 		+ "name=tdate value=\"" + testdate + "\">");
      	out.println("Diagnosis:<br> <input type=text " 
	 		+ "name=diagnosis value=\"" + diagnosis + "\"<br>");
      	out.println("Description:<br><input type=text "
	 		+ "name=description value=\"" + description + "\"maxlength=1024>"
      		+ "<br><br>");
      	out.println("<input type=submit name=Saved_Record_Id value='SAVE'><br>");
      	out.println("</form>");
      	out.println("<hr>");
   	}
  %>
</body>
</html>
