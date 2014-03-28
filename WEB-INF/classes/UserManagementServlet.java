import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.net.*;

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
		Db db = new Db();
		Template template = new Template();
		ArrayList<User> users = db.getUserAccounts();

		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<String> firstnames = new ArrayList<String>();
		ArrayList<String> lastnames = new ArrayList<String>();
		ArrayList<String> classes = new ArrayList<String>();
		ArrayList<String> addresses = new ArrayList<String>();
		ArrayList<String> emails = new ArrayList<String>();
		ArrayList<String> phones = new ArrayList<String>();
		
		for (User u : users) {
			usernames.add(u.getUsername());
			firstnames.add(u.getFirstName());
			lastnames.add(u.getLastName());
			classes.add(u.getUserClass());
			addresses.add(u.getAddress());
			emails.add(u.getEmail());
			phones.add(u.getPhone()); 
		}

		request.setAttribute("userAccounts_usernames", usernames);
		request.setAttribute("userAccounts_firstnames", firstnames);
		request.setAttribute("userAccounts_lastnames", lastnames);
		request.setAttribute("userAccounts_classes", classes);
		request.setAttribute("userAccounts_addresses", addresses);
		request.setAttribute("userAccounts_emails", emails);
		request.setAttribute("userAccounts_phones", phones);
		request.getRequestDispatcher("user_list.jsp").forward(request, response);
	}
		
}

