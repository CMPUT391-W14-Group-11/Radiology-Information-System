import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class UserManagmentServlet
 * Handles HTTPServletRequest from user_management.jsp
 *
 * <url-pattern>/users</url-pattern>
 *
 *  @author	Jessica Surya
 *
 */
public class UserManagementServlet extends HttpServlet {
       
    	/**
    	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     	*      response)
     	*/
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");

		if (request.getParameter("editUser") != null) {
			editUser(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String username = request.getParameter("searchUser");
		String deletion = request.getParameter("submitRemoval");
		String[] savePatientList = request.getParameterValues("removePatient");

		if (username != null) {
			System.out.println(username);
			editUser(request, response);
		}
		else {
			String error = " User not found.";
			request.setAttribute("error", error);
			response.sendRedirect("user_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}

		if (deletion != null && savePatientList.length > 0) {
			if (savePatientList(request, response) == 0) {
				String message = "Patient(s) has been successfully removed.";
				request.setAttribute("message", message);
				response.sendRedirect("patient_list.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
			}
		}
		else {
			String error = "Failed to remove patient(s)";
			request.setAttribute("error", error);
			response.sendRedirect("patient_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String view = request.getParameter("editUser");

		if (view != null) {

			int person_id = Integer.valueOf(request.getParameter("editUser"));
			System.out.println(person_id);

			Db database = new Db();
			User user = database.getUser(person_id);
			request.setAttribute("Username", user.getUsername());
			request.setAttribute("Password", user.getPassword());
			request.setAttribute("FirstName", user.getFirstName());
			request.setAttribute("LastName", user.getLastName());
			request.setAttribute("Address", user.getAddress());
			request.setAttribute("Email", user.getEmail());
			request.setAttribute("Phone", user.getPhone());
			request.setAttribute("PersonID", user.getPersonID());

			if(user.getUserClass().equals("a")) {
		           	request.setAttribute("SelectA", "selected");
		        }
		        else if(user.getUserClass().equals("p")) {
		            	request.setAttribute("SelectP", "selected");
		        }
		        else if(user.getUserClass().equals("d")) {
		            	request.setAttribute("SelectD", "selected");
		        }
		        else if(user.getUserClass().equals("r")) {
		            	request.setAttribute("SelectR", "selected");
		        }
		        else {
		        	System.out.println("failed");
		        }

			request.getRequestDispatcher("user_form.jsp").forward(request, response);	

		}
		
	}

	/**
	 *
	 * Handles HTTPServletRequest from patient_list.jsp
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected int savePatientList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] savePatientList = request.getParameterValues("patients");
		int doc = Integer.valueOf(request.getParameter("save"));
		System.out.println("doctor_id: " + doc);
		Db database = new Db();
		for (int i = 0; i < savePatientList.length ; i++) {
			System.out.println(Integer.valueOf(savePatientList[i]));
			database.removePatient(doc, Integer.valueOf(savePatientList[i]));	
		}

		database.close();
		return 0;
	}
		
}

