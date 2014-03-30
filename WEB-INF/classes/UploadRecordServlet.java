import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import oracle.sql.*;
import oracle.jdbc.*;
import java.net.*;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import entities.*;
/**
 * Servlet implementation class UserRegistrationServlet
 */
public class UploadRecordServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       	HttpSession session = request.getSession();
	       	response.setContentType("text/html");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();


		FileItem file = null;
		DiskFileUpload fu = new DiskFileUpload();

		String save_record = request.getParameter("save_record");
		int result = -1;

		if (save_record != null) {
			result = saveRecord(request);
		}

		if (result == 0 ) {
			String message = "Record saved successfully";
			request.setAttribute("message", message);
			response.sendRedirect("upload_records.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else if (result < 0 ) {
			String error = "An error occured while saving";
			request.setAttribute("error", error);
			response.sendRedirect("upload_records.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
		else {
			String error = "Record has already been saved in the database";
			request.setAttribute("error", error);
			response.sendRedirect("upload_records.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
	}

	public int saveRecord(HttpServletRequest request) {

		try {
			
			String p_username = request.getParameter("p_username");
			String d_username = request.getParameter("d_username");

			// Obtain radiologist person_id from sessions
			String r_username = "admin";

			String test_type = request.getParameter("test_type");
			String prescription_date = request.getParameter("prescription_date");
			String test_date = request.getParameter("test_date");

			java.util.Date date1 = new SimpleDateFormat("yyy-MM-dd").parse(prescription_date);
			java.util.Date date2 = new SimpleDateFormat("yyy-MM-dd").parse(test_date);
		
			String diagnosis = request.getParameter("diagnosis");
			String description = request.getParameter("description");
			
			Db database = new Db();
			int patient_id = database.getUser(p_username).getPersonID();
			int doctor_id = database.getUser(d_username).getPersonID();
			int radiologist_id = database.getUser(r_username).getPersonID();

			int record_id = database.getNextID("record_id", "radiology_record");

			Record record = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);
			
			record.setPrescribingDate(date1);
			record.setTestDate(date2);
			record.setDiagnosis(diagnosis);
			record.setDescription(description);

			return database.insertRadiologyRecord(record);
		} catch (ParseException e) {  
    			e.printStackTrace();  
		}

		return -1;
	}	
}

