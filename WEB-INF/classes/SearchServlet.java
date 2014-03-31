import java.io.*;

import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {

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
		String keywords = request.getParameter("keywords");
		String fDate = request.getParameter("fDate");
		String tDate = request.getParameter("tDate");

		ArrayList<String> search = searchRecords(keywords, fDate, tDate);

		

		if (search != null) {
			ArrayList<String> searchRecords = searchRecords(request, response);
		}
		else {
			String message = "Error: No records found";
		    	request.setAttribute("error", error);
		    	response.sendRedirect("advanced_search.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
	}

	protected ArrayList<String> searchRecords(String keywords, String fDate, String tDate) {
		ArrayList<String> clauses = new ArrayList<String>();
		String sql = "";
		//Add start and end date clauses
		if(!fDate.equals("")) {
		 	clauses.add("test_date >= ? ");
		}
		if(!tDate.equals("")) {
		 	clauses.add("test_date <= ? ");
		}
		if(!keywords.equals("")) {
		//Add CONTAINS and SCRORE for rankings
			clauses.add("contains(r.patient_name, ?, 1) >= 0 and contains(r.diagnosis, ?, 2) >= 0 " +
			"and contains(r.description, ?, 3) >= 0 ");
			String score = "score(1)*6 + score(2)*3 + score(3) as rank, ";
			sql = sql.concat(score);
		}

	    	//Add extra clause depending on user class
	    	/*String userName = (String) session.getAttribute("name");
		String classtype = (String) session.getAttribute("classtype");*/
		if(user_class.equals("r")) {
			clauses.add("r.radiologist_name = ? ");
		}
		else if(user_class.equals("d")) {
			clauses.add("? IN (SELECT d.doctor_name from family_doctor d where d.patient_name = r.patient_name) ");
		}
		else if(user_class.equals("p")) {
			clauses.add("r.patient_name = ? ");
		}
		//Add ordering
	     	String orderBy = "";
		if (request.getParameter("order").equals("Rank")) {
			orderBy = "rank DESC";
		}
		else if(request.getParameter("order").equals("Most-Recent-First")) {
			orderBy = "r.test_date DESC";
		}
		else if(request.getParameter("order").equals("Most-Recent-Last")) {
			orderBy = "r.test_date ASC";
		}
	    
	     	//Columns for table
	     	String start = "r.record_id, r.patient_name, r.doctor_name, r.radiologist_name, r.test_type, "+
	     	"to_char(r.prescribing_date, 'DD-MON-YYYY') as prescribing_date, to_char(r.test_date, 'DD-MON-YYYY') as test_date, "+
	     	"r.diagnosis, r.description from radiology_record r ";
		sql = sql.concat(start);

		//Add in clauses to query
		boolean first = true;
		for (String value : clauses) {
			if (first) {
				first = false;
				sql = sql.concat("WHERE "+ value);
			}
			else {
				sql = sql.concat(" AND "+ value);
			}	
			sql = sql.concat("ORDER BY "+orderBy);
		}

		//Get table column titles
		ResultSet rset = stmt.executeQuery();
		ResultSetMetaData rsmd = rset.getMetaData();
		int colCount = rsmd.getColumnCount();
		int loop = 1;
		if(!keywords.equals("")) {
			loop = 2;
			for (int j = loop; j<= colCount; j++) {
				rsmd.getColumnName(j);
			}
		}
		//Print rows
		while(rset != null && rset.next()) {
			if(!(!keywords.equals("") && rset.getInt(1) == 0)) {
				int recid = 2;
				if(keywords.equals("")) {
					recid = 1;
				}
				for(int k = loop;k <= colCount; k++) {
					rset.getString(k);
				}
			}
		}

		//Print pictures corresponding to row

		String pquery = "select image_id from pacs_images where record_id = "+ rset.getString(recid);
		ResultSet picset = database.executeQuery(pquery);
		if(picset != null && picset.next()) {
			System.out.println("<tr><td COLSPAN="+ colCount +">Images for record_id "+ rset.getString(recid)+":");
			String end = "rec="+rset.getString(recid)+"&pic="+picset.getString(1);
	            	out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
	           	out.println("<img src=\"GetOnePic?"+ end +"\" height=\"45\" width=\"60\"></a>");

	           	while (picset.next()) {
	            		end = "rec="+rset.getString(recid)+"&pic="+picset.getString(1);
	            		System.out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
	            		System.out.println("<img src=\"GetOnePic?"+end+"\" height=\"45\" width=\"60\"></a>");
			}
		}
		Db database=new Db();
		ArrayList<search>=getResultsByDateAndKeywords(keywords,fDate,tDate,OrderBy);
		database.close();
	}
}


