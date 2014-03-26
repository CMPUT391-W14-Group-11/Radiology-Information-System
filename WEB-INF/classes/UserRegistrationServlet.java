package main;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

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

		user.user_name = request.getParameterValues("username");
		user.password = request.getParameterValues("password2");
		user.user_class = (request.getParameterValues("class")).charAt(0);

		user.first_name = request.getParameterValues("first-name");
		user.last_name = request.getParameterValues("last-name");
		user.address = request.getParameterValues("address");
		user.email = request.getParameterValues("email");
		user.phone = request.getParameterValues("phone");

		Db database = new Db();

		database.createUserAccount(user);

		return 0;
}
