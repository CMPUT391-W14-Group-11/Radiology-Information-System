import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class ReportServlet
 * Handles HTTPServletRequest from reports.jsp
 *
 * <url-pattern>/reports</url-pattern>
 * 
 *  @author	Jessica Surya
 */
public class ReportServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       	HttpSession session = request.getSession();
	       	response.setContentType("text/html");

	       	String diagnosis = request.getParameter("diagnosis");
		String fDate = request.getParameter("fDate");
		String tDate = request.getParameter("tDate");

		ArrayList<RadiologyRecord> reports = getDiagnosisReports(diagnosis, fDate, tDate);

		if (!reports.isEmpty()) {
			System.out.println("Reports found!\n");
			request.setAttribute("reports", reports);
			response.sendRedirect("reports.jsp");
		}
		else {
			System.out.println("No reports found...\n");
			reports = getDiagnosisReports();
			request.setAttribute("reports", reports);
			String message = "No reports found.";
			request.setAttribute("message", message);
			response.sendRedirect("reports.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected ArrayList<RadiologyRecord> getDiagnosisReports(String diagnosis, String fDate, String tDate) {

		ArrayList<RadiologyRecord> reports = new ArrayList<RadiologyRecord>();
		
		boolean check = (tDate.equals("") || fDate.equals("") || diagnosis.equals(""));	
	

		try {
			java.util.Date date1 = new SimpleDateFormat("yyy-MM-dd").parse(fDate);
			java.util.Date date2 = new SimpleDateFormat("yyy-MM-dd").parse(tDate);
		
			if(!check) {
			
				Db database = new Db();
				ArrayList<Record> records = database.getDiagnosisReports(diagnosis, date1, date2);
				
				for (Record r : records) {
					User patient = database.getUser(r.getPatientID()); 
					reports.add(new RadiologyRecord(patient, r));
				}
				database.close();
			}

		} catch (ParseException e) {  
    			e.printStackTrace();  
		}

		return reports;
	}

	protected ArrayList<RadiologyRecord> getDiagnosisReports() {

		ArrayList<RadiologyRecord> reports = new ArrayList<RadiologyRecord>();
		Db database = new Db();
		ArrayList<Record> records = database.getAllDiagnosisReports();
		for (Record r : records) {
			User patient = database.getUser(r.getPatientID()); 
			reports.add(new RadiologyRecord(patient, r));
		}
		database.close();

		return reports;
	}

}


