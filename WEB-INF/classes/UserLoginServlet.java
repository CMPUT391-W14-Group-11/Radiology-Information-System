package RIS.user;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

import main.Db;

/**
 * Servlet implementation class UserLoginServlet
 */
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		System.err.print("InCorrectLoginException: ");
		HttpSession session = request.getSession();
		
		String username = request.getParameter("user_name");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		out.println(username);
		request.removeAttribute("user_name");
		request.removeAttribute("password");
		session.removeAttribute("failureMessage");
		String failureMessage = 
				"Authentication failed for " 
				+ username + ".";
		
		Db db = new Db();
		
		if (db.verifyUser(username, password) ) {
			// if login succeeds create a session for this user attributes
			createUserSession(username, session);
			
			return;
		}
		else {
			// if login is unsuccessful 
			System.err.print("InCorrectLoginException: ");
			response.setContentType("text/html");
			// set failure message
			session.setAttribute("failureMessage", failureMessage);
			request.setAttribute("failureMessage", failureMessage);
	       		out = response.getWriter();
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
				"Transitional//EN\">\n" +
				"<HTML>\n" +
				"<HEAD><TITLE>Asn2Sample</TITLE></HEAD>\n" +
				"<BODY>\n" +
				"<H1>WELCOME\n</H1>" + 
				username);
			out.println("</BODY></HTML>");
			
			//request.getRequestDispatcher("./authenticate/signin.jsp").forward(request, response);
			//response.sendRedirect("./login.jsp");
			return;
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
