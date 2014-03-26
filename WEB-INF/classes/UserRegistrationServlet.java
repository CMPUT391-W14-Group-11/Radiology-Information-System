package main;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

import main.*;

/**
 * Servlet implementation class UserRegistrationServlet
 */
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	       	
	       	response.setContentType("text/html");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createUserAccount(request);

	}

	/**
	 * Inserts a User into the database
	 * 
	 * @see User
	 * @param User
	 *            
	 * @return int success
	 */
	public int createUserAccount(HttpServletRequest request) {
		User user = new User();

		user.setUserID(request.getParameterValues("username"));
		user.setPassword(request.getParameterValues("password2"));
		user.setUserClass((request.getParameterValues("class")).charAt(0));

		user.setFirstName(request.getParameterValues("first-name"));
		user.setLastName(request.getParameterValues("last-name"));
		user.setAddress(request.getParameterValues("address"));
		user.setEmail(request.getParameterValues("email"));
		user.setPhone(request.getParameterValues("phone"));

		Db database = new Db();
		user.setPersonID(database.getNextPeronID()); 
		database.createUserAccount(user);

		return 0;
	}
}
