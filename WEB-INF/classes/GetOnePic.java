import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *  This servlet sends one picture stored in the table below to the client 
 *  who requested the servlet.
 *
 *   picture( photo_id: integer, title: varchar, place: varchar, 
 *            sm_image: blob,   image: blob )
 *
 *  The request must come with a query string as follows:
 *    GetOnePic?rid5pic12:        sends the picture in sm_image with photo_id = 12
 *    GetOnePic?bigrid5pic12: sends the picture in image  with photo_id = 12
 *
 *  @author  Li-Yan Yuan
 *  	     Alice Wu
 *
 */
public class GetOnePic extends HttpServlet 
    implements SingleThreadModel {

    /**
     *    This method first gets the query string indicating PHOTO_ID,
     *    and then executes the query 
     *          select image from yuan.photos where photo_id = PHOTO_ID   
     *    Finally, it sends the picture to the client
     */
    public void doGet(HttpServletRequest request,
		      HttpServletResponse response)
	throws ServletException, IOException {
	
		//  construct the query  from the client's QueryString
		String picid  = request.getQueryString();
		String query;
	
		// determine which query should be executed
		if ( picid.startsWith("big") )  
		    query = "select full_size from pacs_images where image_id=" 
		    	+ picid.substring(3);
		else
	        query = "select thumbnail from pacs_images where image_id=" 
	        	+ picid ;
	 
	        
		ServletOutputStream out = response.getOutputStream();
	
		Connection conn = null;
		
		try{
			conn = getConnected();
		} catch (Exception ex){
			out.println(ex.getMessage());
		}
		
		if(conn == null){
			return;
		}
		
		try {
		    Statement stmt = conn.createStatement();
		    ResultSet rset = stmt.executeQuery(query);
	
		    if ( rset.next() ) {
		    	response.setContentType("image/jpg");
		    	InputStream input = rset.getBinaryStream(1);	    
		    	int imageByte;
		    	while((imageByte = input.read()) != -1) {
		    		out.write(imageByte);
				}
		    	input.close();
		    }else 
		    	out.println("no picture available");
		} catch ( Exception ex ) {
		    out.println(ex.getMessage() );
		}
		
		// to close the connection
		finally {
		    try {
		    	conn.close();
		    } catch ( SQLException ex) {
		    	out.println( ex.getMessage() );
		    }
		}
    }
    
    /**
     *  Connection returns a connection to database
     */
    private Connection getConnected() throws Exception {

		String username = "jsurya";
		String password = "ark123et";
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String dbstring ="jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	
		/*
		 *  to connect to the database
		 */
		Class drvClass = Class.forName(driverName); 
		DriverManager.registerDriver((Driver) drvClass.newInstance());
		return( DriverManager.getConnection(dbstring,username,password));
    } 
}