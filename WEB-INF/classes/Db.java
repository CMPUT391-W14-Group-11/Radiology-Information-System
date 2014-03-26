package main;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import main.User;

/**
 * Servlet implementation class Db
 */
/**
 * Class used to handle database connections and queries
 * 
 *  @author	Jessica Surya
 *  @author	
 */

public class Db {

	private Connection con = null;
	private static String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	private static String m_driverName = "oracle.jdbc.driver.OracleDriver";
	private static String user = "jsurya";
	private static String password = "";
	
	static Connection m_con;
    	static String queryString = "";

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

	              m_con = DriverManager.getConnection(m_url, user,
	              password);

	              stmt = m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	              ResultSet rset = stmt.executeQuery(queryString);
	       } catch(SQLException ex) {

	              System.err.println("SQLException: " +
	              ex.getMessage());

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

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNextPersonID() {
		ResultSet rs = performQuery("SELECT users, MAX(person_id)");

		try {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public boolean verifyUser(String username, String password2) {

		return false;
	}

	public int createUserAccount(User user) {
		Date dateRegistered = new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(rs.getTimestamp("date_registered"));

		String stmt = "INSERT INTO persons (person_id, first_name, last_name, address, email, phone) "
			+ "VALUES ('" + user.getPersonID() 
			+ "', '" + user.getFirstName()
			+ "', '" + user.getLastName()
			+ "', '" + user.address()
			+ "', '" + user.email()
			+ "', '" + user.phone()
			+ "');";

		String stmt = "INSERT INTO users (user_name, password, class, person_id, date_registered) "
			+ "VALUES ('" + user.getUsername() 
			+ "', '" + user.getPassword()
			+ "', '" + user.getClass()
			+ "', '" + user.getPersonID()
			+ "', '" + dateRegistered
			+ "');";
		return performUpdate(stmt);
	}
}
