package main;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Db
 */
/**
 * Class used to handle database connections and queries
 * 
 *  @author	Jessica Surya
 *	@author	
 */

public class Db {
	private Connection con = null;
	private String url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	private String user = "jsurya";
	private String password = "";

	public static void connect() {

		/**
		 * Instantiating a Db object will perform connection
		 */
		 Statement stmt;

	       try
	       {

	              Class drvClass = Class.forName(m_driverName);
	              DriverManager.registerDriver((Driver)
	              drvClass.newInstance());

	       } catch(Exception e)
	       {

	              System.err.print("ClassNotFoundException: ");
	              System.err.println(e.getMessage());

	       }

	       try
	       {

	              m_con = DriverManager.getConnection(m_url, m_userName,
	              m_password);

	              stmt = m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	              ResultSet rset = stmt.executeQuery(queryString);
		}


		
	}

/**
	 * Performs a database query
	 * 
	 * @param String stmt
	 * @returns ResultSet rs
	 */
	public ResultSet performQuery(String stmt) {
		Statement st = null;
		try {
			st = this.con.createStatement();
			ResultSet rs= st.executeQuery(stmt);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Executes a INSERT, UPDATE, or DELETE statement  
	 * 
	 * @param String stmt
	 * @returns int succcess
	 */
	public int performUpdate(String stmt) {
		Statement st = null;
		try {
			st = this.con.createStatement();
			int success = st.executeUpdate(stmt);
			st.close();
			return success;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean verifyUser(String username, String password2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
