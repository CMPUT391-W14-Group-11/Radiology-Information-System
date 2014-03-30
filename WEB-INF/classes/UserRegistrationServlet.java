import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class UserRegistrationServlet
 */
public class UserRegistrationServlet extends HttpServlet {

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
		int result = -1;

		if (request.getParameter("person_id") == null) {

			Db database = new Db();
			int person_id = database.getNextID("person_id", "persons");
			User user = createUser(request, person_id);

			result = createUserAccount(user);
		}

		else {
			result = updateUserAccount(request);
		}

		System.out.println("Result code for user transactions" + result);

		// Feedback from user account transactions
		if (result == 0) {
				String message = "Accout for user '" + request.getParameter("username") + "' has been successfully created/updated.";
				request.setAttribute("message", message);
				response.sendRedirect("user_list.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		if (result == 1) {
		
				String error1 = "Error: Email address is already registered in the database";
				request.setAttribute("error1", error1);
				response.sendRedirect("user_form.jsp?error1=" + URLEncoder.encode(error1, "UTF-8"));
		}

		if (result == 2) {
				String error2 = "Error: Username is already registered in the database";
				request.setAttribute("error2", error2);
				response.sendRedirect("user_form.jsp?error2=" + URLEncoder.encode(error2, "UTF-8"));
		}

	}

	/**
	 * Inserts a User into the database
	 * 
	 * @see User
	 * @see Db.insertUserAccount()
	 * @param User
	 *            
	 * @return int 0 on success
	 * @return int 1 on invalid email
	 * @return int 2 on invalid username
	 */
	public int createUserAccount(User user) {

		Db database = new Db();
		int result = database.insertUserAccount(user);
		database.close();
		System.out.println("Creating new user...");

		return result;
	}

	/**
	 * Updates an existing User in the database
	 * 
	 * @see User
	 * @see Db.updateUserAccount()
	 * @param User
	 *            
	 * @return int 0 on success
	 * @return int 1 on invalid email
	 * @return int 2 on invalid username
	 */
	public int updateUserAccount(HttpServletRequest request) {

		Db database = new Db();
		int person_id = Integer.valueOf(request.getParameter("person_id"));
		User user = createUser(request, person_id); 
		int result = database.updateUserAccount(user);
		System.out.println("Updating user with person_id: " + person_id);

		return result;
	}

	/**
	 * Create User object
	 * 
	 * @see User
	 * @param User
	 *            
	 * @return User
	 */
	public User createUser(HttpServletRequest request, int person_id) {

		String user_name = request.getParameter("username");
		String password = request.getParameter("password2");
		String user_class = request.getParameter("class");
		

		User user = new User(user_name, password, user_class, person_id);

		user.setFirstName(request.getParameter("first-name"));
		user.setLastName(request.getParameter("last-name"));
		
		String addr =  request.getParameter("address");
		if (addr == null) {
			user.setAddress("");
		}
		else {
			user.setAddress(addr);
		}
		
		user.setEmail(request.getParameter("email"));
		
		String ph =  request.getParameter("phone");
		if (addr == null) {
			user.setPhone("");
		}
		else {
			user.setPhone(ph);
		}

		return user;
	}

}
