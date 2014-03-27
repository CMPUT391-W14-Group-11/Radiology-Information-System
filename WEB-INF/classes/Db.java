// package main;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Object;

// import main.*;

/**
 * Class used to handle database connections and queries
 * 
 *  @author	Jessica Surya
 *  @author	
 */

public class Db {

	protected Connection con = null;
	private String url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private String user = "jsurya";
	private String password = "ark123et";

	public Db() {

		/**
		 * Instantiating a Db object will perform connection
		 */
	       try
	       {
	              Class drvClass = Class.forName(driverName);
	              DriverManager.registerDriver((Driver)
	              drvClass.newInstance());
	              this.con = DriverManager.getConnection(this.url, this.user,
			this.password);

	       } catch(Exception e) {
			e.printStackTrace();
	        	System.err.println(e.getMessage());
	       }
	}

	/**
	 * Performs a database query
	 * 
	 * @param String stmt
	 * @return ResultSet rs
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
	 * @return int succcess
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
		ResultSet rs = performQuery("SELECT MAX(person_id) FROM persons");
		
		try {
			if(rs.next()) {
				int num = rs.getInt(1) + 1;
				while(checkValidity(num) == false) {
					num++;
				}
				return num;
			}
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public boolean checkValidity(int num) {
		ResultSet check = performQuery("SELECT * FROM persons WHERE person_id =" + num);
		
		try {
			if(check.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean verifyUser(String username, String password) {

		try {
			PreparedStatement stmt = 
				con.prepareStatement("SELECT * FROM users WHERE user_name = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			boolean empty = true;
			while( rs.next() ) {
			    // ResultSet processing here
			    empty = false;
			}

			if( empty ) {
			    // Empty result set
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public int insertUserAccount(User user) {
		int personID = user.getPersonID();
		try {
			PreparedStatement stmt = 
				con.prepareStatement("INSERT INTO persons (person_id, first_name, last_name, address, email, phone) "
				+ "VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, personID); 
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getAddress());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getPhone());

			stmt.executeUpdate();

			PreparedStatement stmt2 = 
				con.prepareStatement("INSERT INTO users (user_name, password, class, person_id, date_registered)" 
				+ "VALUES (?, ?, ?, ?, CURRENT_DATE)");
			stmt2.setString(1, user.getUsername()); 
			stmt2.setString(2, user.getPassword());
			stmt2.setString(3, (user.getUserClass()));
			stmt2.setInt(4, personID);
				
			stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return 0;
	}
}
