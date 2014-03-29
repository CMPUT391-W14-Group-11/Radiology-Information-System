package entitties;
import java.io.*;


import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
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
		if (Info_forUpload(request)==1){
			String message = "Error:";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else
			String message = "Enter in the required spaces" + request.getParameter("keywords") + request.getParameter("startdate") + request.getParameter("enddate")";
		    request.setAttribute("message", message);
		    request.getRequestDispatcher("uploadrecords123.jsp").forward(request, response);	
		}
	public int Info_forSearch(HttpServletRequest request) {

		String user_name = request.getParameter("username");
		String password = request.getParameter("password2");
		String user_class = request.getParameter("class");
		
		Db database = new Db();
		//int person_id = database.getNextPersonID();

		User user = new User(user_name, password, user_class, person_id);
		if(user_name("username") && user_class("r")){
			user.setPatient_Name(request.getParameter("patient name"));
			user.setDoctor_Name(request.getParameter("doctor name"));
			user.setTestType(request.getParameter("testtype"));
			user.setPresDate(request.getParameter("prescription date"));
			user.setTestDate(request.getParameter("testdate"));
			user.setdiagnosis(request.getParameter("diagnosis"));
			user.setdescription(request.getParameter("description"));
                        int result=Upload_Record(user);
		}
		else{
			String message = "You are not an authorized to upload this";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}


		return result;
	}
}

