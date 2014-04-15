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
<%!
/* getConnection function returns a connection to Database*/
private Connection getConnection(){
Connection conn = null;
String driverName = "oracle.jdbc.driver.OracleDriver";
String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";

try {
Class drvClass = Class.forName(driverName);
DriverManager.registerDriver((Driver) drvClass.newInstance());
}catch(Exception ex){
return null;
}

try {
conn = DriverManager.getConnection(dbstring,"jsurya",
"ark123et");
conn.setAutoCommit(false);
return conn;
}catch(Exception ex){
return null;
}

}
%>
<%@ page
import="java.sql.*,javax.portlet.ActionResponse.*,
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
/**********************************************************
* User Interface Section
***********************************************************/
/*out.println("<form action=newrecord.jsp>");
out.println("<input type=submit name=Back value='Go Back'><br>");
out.println("</form>");*/
out.println("<b>Find out more help information by clicking "
+"<a href='help.html#record' target='blank'>Help</a></b><br><br>");
 
Integer person_id = (Integer) session.getAttribute("Person_Id");
String role = (String) session.getAttribute("PermissionLevel");

/* in case the session expires, system will redirect the user to log in*/
    /*if(person_id == null || !role.equals("r")){
response.sendRedirect("login.jsp");
    }*/

    /* Initialize variables*/
    String pid = "";
    String did = "";
    String type = "";
    String pDate = "";
    String tDate = "";
    String diag = "";
    String description = "";

    /**********************************************************
* Request Handle Section
***********************************************************/
    if(request.getParameter("SaveRecord") != null){

     /* The user is creating a record*/
     Connection conn = getConnection();
    
     if(conn == null){
JOptionPane.showMessageDialog(null, "Can't get a connection."
+" Please try again.");
response.sendRedirect("newrecord.jsp");
}
    
     /* Initialize statement and result set*/
        Statement stmt = null;
        try{
         stmt = conn.createStatement();
        }catch (Exception ex){
         out.println("<hr>" + ex.getMessage() + "<hr>");
         return;
        }
        ResultSet rset = null;
        String sql = "";
        
/* creating the record id for the current id*/
        sql = "SELECT MAX(RECORD_ID) AS NEXT_RID FROM RADIOLOGY_RECORD";
        rset = stmt.executeQuery(sql);
        int rid = 0;
        
        while(rset != null && rset.next()){
            rid = rset.getInt("NEXT_RID") + 1;
        }
      
        /* get the values from all input fields*/
     pid = request.getParameter("pid");
     did = request.getParameter("did");
        type = request.getParameter("type");
        pDate = request.getParameter("pdate");
        tDate = request.getParameter("tdate");
        diag = request.getParameter("diagnosis");
        description = request.getParameter("description");
      
        /* query to validate patient id and doctor id*/
        String validPid = "select count(*) from users where person_id = '"
         + pid + "' and class = 'p'";
        String validDid = "select count(*) from users where person_id = '"
+ did + "' and class = 'd'";

        /* check patient id and docotor id if they are not empty
* a valid id is an integer and exisits in database with
* a proper class.
* If empty, set both ids to null.
*/
        if (!pid.isEmpty()){
      
try{
int patient_id = Integer.parseInt(pid);	
}catch (Exception ex){
JOptionPane.showMessageDialog(null,"Patient id should be an "
+"integer");
try{
conn.close();
}
catch(Exception ex1){
out.println("<hr>" + ex1.getMessage() + "<hr>");
}
                response.sendRedirect("newrecord.jsp");
                return;
}

rset = stmt.executeQuery(validPid);

int count = 0;
while(rset != null && rset.next()){
count = rset.getInt(1);
}

if(count < 1){
JOptionPane.showMessageDialog(null, "The patient id is "
+ "invalid.");

try{
conn.close();
}
catch(Exception ex){
out.println("<hr>" + ex.getMessage() + "<hr>");
}

response.sendRedirect("newrecord.jsp");
return;
}
        }else{
pid = null;
       }
      
       if (!did.isEmpty()){
       try{
int doctor_id = Integer.parseInt(did);	
}catch (Exception ex){
JOptionPane.showMessageDialog(null,"Doctor id should be an "
+ "integer");
try{
conn.close();
}
catch(Exception ex1){
out.println("<hr>" + ex1.getMessage() + "<hr>");
}
             response.sendRedirect("newrecord.jsp");	
             return;
}
      
try{	
rset = stmt.executeQuery(validDid);	
}catch (Exception ex){
out.println("<hr>" + ex.getMessage() + "<hr>");
}

int count = 0;
while(rset != null && rset.next()){
count = rset.getInt(1);
}

if(count < 1){
JOptionPane.showMessageDialog(null, "The doctor id is "
+ "invalid.");
try{
conn.close();
}
catch(Exception ex){
out.println("<hr>" + ex.getMessage() + "<hr>");
}
response.sendRedirect("newrecord.jsp");
return;
}

}else{
did = null;	
     }
      
       /* check the prescribing and test date if they are not null.
* Or else make them null.
* A correct date should not only be a valid date but also
* strictly follows the given format
*/
       if (!pDate.isEmpty()){
      
SimpleDateFormat sdformat = new SimpleDateFormat("dd-MMM-yyyy");
sdformat.setLenient(false);

try{
sdformat.parse(pDate);
}catch(Exception ex){
JOptionPane.showMessageDialog(null,"Please check the date "
+ "format,"
+ " make sure it's in dd-MMM-yyyy");
try{
                 conn.close();
}
catch(Exception ex1){
                 out.println("<hr>" + ex1.getMessage() + "<hr>");
}
response.sendRedirect("newrecord.jsp");
return;
}



       } else {
pDate = null;
       }
      
       if (!tDate.isEmpty()){
      
SimpleDateFormat sdformat = new SimpleDateFormat("dd-MMM-yyyy");
sdformat.setLenient(false);

try{
sdformat.parse(pDate);
}catch(Exception ex){
JOptionPane.showMessageDialog(null,"Please check the date "
+"format,"
+ " make sure it's in dd-MMM-yyyy");
try{
conn.close();
}
catch(Exception ex1){
out.println("<hr>" + ex1.getMessage() + "<hr>");
}
response.sendRedirect("newrecord.jsp");
return;
}
}else{
tDate = null;
}

       if(conn == null){
       return;
       }
      
       /* if values pass all tests, then do the update */
       PreparedStatement insertRecord = null;
      
      String insertSql = "insert into radiology_record"
+ " (record_id, patient_id, doctor_id, radiologist_id, test_type, "
+ "prescribing_date, test_date, diagnosis, description) values "
+ " (?, ?, ?, ?, ?, ?, ?, ?, ?)";
       insertRecord = conn.prepareStatement(insertSql);
       insertRecord.setInt(1, rid);
       insertRecord.setString(2, pid);
       insertRecord.setString(3, did);
       insertRecord.setInt(4, person_id);
       insertRecord.setString(5, type);
       insertRecord.setString(6, pDate);
       insertRecord.setString(7, tDate);
       insertRecord.setString(8, diag);
       insertRecord.setString(9, description);
      
       try{
insertRecord.executeUpdate();
conn.commit();
session.setAttribute("Saved_Record_Id", rid);
conn.close();
response.sendRedirect("/proj1/newrecord.jsp");
       }catch(Exception ex){
out.println("<hr>" + ex.getMessage() + "<hr>");
       }
      
      
   
}else if (request.getParameter("Cancel") != null){

/* if the user decides to end uplaoding image process*/
session.removeAttribute("Saved_Record_Id");
response.sendRedirect("/proj1/homepage.jsp");

    }else if (session.getAttribute("Saved_Record_Id") != null){
   
    /* if the record is saved, then we direct the user to
* upload images. And the uploading process will be
* handled by UploadImage.class
*/
        out.println("<p>");
out.println("<hr>");
out.println("You are uploading for record "
+ session.getAttribute("Saved_Record_Id") + ".");
out.println("Please input or select the path of the image!");
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

out.println("<form action=newrecord.jsp>");
out.println("<input type=submit name=Cancel value='No Images "
+ "To Upload'><br>");
out.println("</form>");	
}else{

/* without any request, the UI section is printed. */
       out.println("<p> As a radiologsit, you could create a new radiology "
+ "record by entering the information first and add pacs.</p>");
       out.println("<form action=newrecord.jsp method=post>");
       out.println("Please Enter Patient Id Here:<br> <input type=text "
       + "name=pid value='" + pid + "' ><br>");
       out.println("Please Enter Doctor Id Here:<br><input type=text "
       +"name=did value='" + did + "'><br>");
       out.println("Please Enter Test Type Here:<br> <input type=text "
+"name=type maxlenght=24 value=\"" + type + "\"><br>");
      out.println("Please Enter Prescribing Date Here:<br><input type=text "
+ "name=pdate value=\"" + pDate + "\">(eg. 02-FEB-2011)<br>");
       out.println(" Please Enter Test Date Here:<br> <input type=text "
+ "name=tdate value=\"" + tDate + "\">(eg. 03-AUG-2012)<br>");
       out.println("Please Enter Diagnosis Here:<br> <input type=text "
+ "name=diagnosis value=\"" + diag + "\"maxlength=128><br>");
       out.println(" Please Enter Description Here:<br><input type=text "
+ "name=description value=\"" + description + "\"maxlength=1024>"
       + "<br><br>");
       out.println("<input type=submit name=SaveRecord value='Save New "
       + "Record'><br>");
       out.println("</form>");
       out.println("<hr>");
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
