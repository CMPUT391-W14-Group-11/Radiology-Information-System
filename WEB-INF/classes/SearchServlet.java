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
		
		String search = request.getParameter("searchRecords");

		if (search != null) {
			String message = "Error:";
			request.setAttribute("message", message);
			response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
		else {
			String message = "Error: No records found";
		    	request.setAttribute("error", error);
		    	response.sendRedirect("advanced_search.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
	}

	public ArrayList<Record> searchRecords() {
		
		ArrayList<RadiologyRecord> reports = new ArrayList<RadiologyRecord>();
		
		try {
			String search = request.getParameter("searchRecords");
			String fDate = request.getParameter("fDate");
			String tDate = request.getParameter("tDate");
			java.util.Date date1 = new SimpleDateFormat("yyy-MM-dd").parse(fDate);
			java.util.Date date2 = new SimpleDateFormat("yyy-MM-dd").parse(tDate);
			
			boolean check = (tDate.equals("") || fDate.equals("") || searchRecords.equals(""));	
			
			if(!check) {
			
				Db database = new Db();
				ArrayList<Record> records = database.searchRecords(search);
				
				for (Record r : records) {
					User patient = database.getUser(r.getPatientID()); 
					reports.add(new RadiologyRecord(patient, r));
				}
				database.close();
			}

		} catch (ParseException e) {  
    			e.printStackTrace();  
		}

		return result;
	}
}


