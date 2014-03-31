import java.io.*;

import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

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
	       	Db database = new Db();
	       	String[] keywords = request.getParameter("keywords");
	       	String fDate = request.getParameter("fdate");
	       	String tDate = request.getParameter("tdate");
	       	String order = request.getParameter("order");
        
        /*
* Changing format from yyyy-MM-dd to dd-MMM-yy for sql
*/
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");*/
        
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fDate);
            java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(tDate);
        //PrintWriter out = response.getWriter();

        /*
* Get how query will be sorted
*/
        String sort = null;
        if (order.equals("mrf")) {
        	sort= "order by DESC";
        } else if (order.equals("mfl")) {
            sort = "order by ASC";
        } else {
            sort = "order by DESC";
        }
        
        /*
* The user has to input from and to dates otherwise
* only keyword search to get resultset of query
*/
        if (!(keywords.equals(""))) {
            if((fDate.equals("")) || (tDate.equals(""))) {
                return database.searchRecords(keywords);
                String message="Resutls for keywords";
            	request.setAttribute("message",message);
            	request.sendRedirect("advanced_search.jsp?message=" + URLEncoder.encode(message, "UTF-8");
                } else {
                return database.getResultsByDateAndKeywords(date,date1, keywords, sort);
                String message="Your Results for keywords and Dates";
            	request.setAttribute("message",message);
            	request.sendRedirect("advanced_search.jsp?message=" + URLEncoder.encode(message, "UTF-8");
                                      
            }
        } else if (!((fDate.equals("")) || (tDate.equals("")))) {
            if (!(sort.equals("order by DESC"))) {
                return database.getResultsByDate(fDate, tDate, sort);
                String message="Your results for Dates=";
            	request.setAttribute("message",message);
            	request.sendRedirect("advanced_search.jsp?message=" + URLEncoder.encode(message, "UTF-8");
            } else {
            	String message="Cannot sort by rank with just time, please sort differently or add keywords";
            	request.setAttribute("message",message);
            	request.sendRedirect("advanced_search.jsp?message=" + URLEncoder.encode(message, "UTF-8");
            }
        } else {
        	String message="Please enter a keyword";
            	request.setAttribute("message",message);
            	request.sendRedirect("advanced_search.jsp?message=" + URLEncoder.encode(message, "UTF-8");
        }
        
        } catch (Exception e) {
            e.getMessage();
        }
        

        
    }
}

