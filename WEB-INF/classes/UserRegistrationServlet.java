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

		if (createUserAccount(request) == 1) {
			String message = "Error: Email address is already registered in the database";
			request.setAttribute("message", message);
			response.sendRedirect("user_form.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else {
			String message = "Accout for user '" + request.getParameter("username") + "' has been successfully created.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("index.jsp").forward(request, response);	
		}
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

		String user_name = request.getParameter("username");
		String password = request.getParameter("password2");
		String user_class = request.getParameter("class");
		
		Db database = new Db();
		int person_id = database.getNextPersonID();

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

		int result = database.insertUserAccount(user);

		database.close();
		return result;
	}
}
