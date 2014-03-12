package RIS.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.Db;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
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
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		request.removeAttribute("username");
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
			session.setAttribute("failureMessage", failureMessage);
			//request.setAttribute("failureMessage", failureMessage);
			// set failure message
			//request.getRequestDispatcher("./authenticate/signin.jsp").forward(request, response);
			response.sendRedirect("./login.jsp");
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
