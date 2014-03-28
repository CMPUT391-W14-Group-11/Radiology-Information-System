import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class UserLoginServlet
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

		if (username != null) {
			System.out.println(username);
			// String message = "Error: Email address is already registered in the database";
			// request.setAttribute("message", message);
			// response.sendRedirect("user_form.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else {
			String error = " User not found.";
			request.setAttribute("error", error);
			response.sendRedirect("user_management.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}

		editUser(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String view = request.getParameter("editUser");

		if (view != null) {
			System.out.println(view);

			Db database = new Db();
			User user = database.getUser(view);
			request.setAttribute("Username", user.getUsername());
			request.setAttribute("Password", user.getPassword());
			request.setAttribute("FirstName", user.getFirstName());
			request.setAttribute("LastName", user.getLastName());
			request.setAttribute("Address", user.getAddress());
			request.setAttribute("Email", user.getEmail());
			request.setAttribute("Phone", user.getPhone());

			request.getRequestDispatcher("user_form.jsp").forward(request, response);	

		}
		
	}
		
}

