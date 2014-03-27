import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.net.*;

/**
 * Servlet implementation class UserLoginServlet
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
		String failureMessage = 
				"Authentication failed for " 
				+ username + ".";
		
		Db db = new Db();
		
		if (db.verifyUser(username, password) == true) {
			// if login succeeds create a session for this user attributes
			createUserSession(username, session);

			String message = "Welcome, " + username;
			request.setAttribute("message", message);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			
			return;
		}
		else {
			// if login is unsuccessful 
			System.err.print("IncorrectLoginException \n");
			
			// set failure message
			session.setAttribute("failureMessage", failureMessage);
			request.setAttribute("failureMessage", failureMessage);
			if (username != null) {
				String message = "Incorrect Username or Password Combination";
				request.setAttribute("message", message);
				response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
			} else {
				String message = "Invalid Login";
				request.setAttribute("message", message);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			return;
		}


	}
	
	/**
	 * 
	 * @param username
	 * @param session
	 */
	private void createUserSession(String username, HttpSession session){
		//Setup Permissions
		Db db = new Db();
		
		//get session variables
		
		
		//set session variables

		
		db.close();
		
	}

}
