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
public class ReportServlet extends HttpServlet {

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
		if (Info_forReport(request)==1){
			String message = "Error:";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else
			String message = "Enter in the required spaces" + request.getParameter("diagnosis") + request.getParameter("From") + request.getParameter("To")";
		    request.setAttribute("message", message);
		    request.getRequestDispatcher("index.jsp").forward(request, response);	
		}
	public int Info_forReport(HttpServletRequest request) {

		String user_name = request.getParameter("username");
		String password = request.getParameter("password2");
		String user_class = request.getParameter("class");
		
		Db database = new Db();
		//int person_id = database.getNextPersonID();

		User user = new User(user_name, password, user_class, person_id);
		boolean check = (begin.equals("") || end.equals("") || diagnosis.equals(""));	
		if(user_name("username") && user_class("a") && !check){
			user.setdiagnosis(request.getParameter("Diagnosis"));
			user.setStartDate(request.getParameter("begin"));
			user.setEndDate(request.getParameter("end"));
			int result = database.insertInfo_Report(user);
		}
		else {
			String message = "You are not an admin";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}


		database.close();
		return result;
	}
}


