import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class UserLoginServlet
 *
 * <url-pattern>/login</url-pattern>
 */
public class UserLoginServlet extends HttpServlet {
       
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = request.getParameter("user_name");
		String password = request.getParameter("password");

		request.removeAttribute("user_name");
		request.removeAttribute("password");
		session.removeAttribute("failureMessage");
		
		Db db = new Db();
		
		if (db.verifyUser(username, password) == true) {
			// if login succeeds create a session for this user attributes
			System.out.println("Login successful");
			db.close();

			// createUserSession(username, session);

			String message = "Welcome, " + username + "!";
			request.setAttribute("message", message);
			/*
			User user = db.getUser(username);
			session.setAttribute("PermissionLevel", user.getUserClass());
			session.setAttribute("Person_Id", user.getPersonID());
			*/
			response.sendRedirect("index.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
			
		}
		
		else {
			// if login is unsuccessful 
			db.close();
			System.err.print("Incorrect Login Exception \n");
			
			// set failure message
			
			if (username != null) {
				String message = "Incorrect Username or Password Combination";
				request.setAttribute("error", message);
				response.sendRedirect("login.jsp?error=" + URLEncoder.encode(message, "UTF-8"));
			} else {
				String message = "Invalid Login";
				request.setAttribute("error", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param session
	 */
	private void createUserSession(String username, HttpSession session) {
		//Setup Permissions
		Db db = new Db();
		
		//get session variables
		
		
		//set session variables
		db.close();
		
	}

}
