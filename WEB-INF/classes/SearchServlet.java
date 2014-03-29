package entitties;
import java.io.*;



import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
/**
 * Servlet implementation class SearchServlet
 */
public class ReportServlet extends HttpServlet {

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
		if (Info_forSearch(request)==1){
			String message = "Error:";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else
			String message = "Enter in the required spaces" + request.getParameter("keywords") + request.getParameter("startdate") + request.getParameter("enddate")";
		    request.setAttribute("message", message);
		    request.getRequestDispatcher("search123.jsp").forward(request, response);	
		}
	public int Info_forSearch(HttpServletRequest request) {

		String user_name = request.getParameter("username");
		String password = request.getParameter("password2");
		String user_class = request.getParameter("class");
		
		Db database = new Db();
		//int person_id = database.getNextPersonID();

		User user = new User(user_name, password, user_class, person_id);
		boolean check = (startdate.equals("") || enddate.equals("") || keywords.equals(""));	
		if(user_name("username") && user_class("a") && !check){
			user.setkeywords(request.getParameter("Keywords"));
			user.setStartDate(request.getParameter("startdate"));
			user.setEndDate(request.getParameter("enddate"));
			user.setOrder(request.getParameter("class"));
                        int result=database.Info_forSearch(user);
		}
		else{
			String message = "You are not an authorized to search this";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}


		return result;
	}
}


