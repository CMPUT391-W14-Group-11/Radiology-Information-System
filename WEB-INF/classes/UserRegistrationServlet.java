import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;

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

		String message = "OK " + createUserAccount(request);;
		PrintWriter out = response.getWriter();
      		out.println("<h1>" + message + "</h1>");
      		out.println("<body><ul>");
      		out.println("<li>" + request.getParameter("username") + "</li>");
      		out.println("<li>" + request.getParameter("password2") + "</li>");
      		out.println("<li>" + (request.getParameter("class")).charAt(0) + "</li>");
      		out.println("<li>" + request.getParameter("first-name") + "</li>");
      		out.println("<li>" + request.getParameter("last-name") + "</li>");
      		String addr =  request.getParameter("address") + "";
      		out.println("<li>" + addr  + "</li>");
      		out.println("<li>" + request.getParameter("email") + "</li>");
      		out.println("<li>" + request.getParameter("phone") + "</li>");
      		out.println("</body>");

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
		Character user_class = (request.getParameter("class")).charAt(0);
		
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
