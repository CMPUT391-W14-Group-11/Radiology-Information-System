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
			editUser(request, response);
		}
		else {
			String error = " User not found.";
			request.setAttribute("error", error);
			response.sendRedirect("user_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
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
		
}

