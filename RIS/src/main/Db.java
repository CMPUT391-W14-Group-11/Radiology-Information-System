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
	private String url = "jdbc:mysql://localhost:";
	private String user = "root";
	private String password = "";

	/**
	 * Instantiating a Db object will perform connection
	 */
	public Db() {
		try {
			Class drvClass = Class.forName("com.mysql.jdbc.Driver");
			DriverManager.registerDriver((Driver)
					drvClass.newInstance());
			this.con = DriverManager.getConnection(this.url, this.user,
					this.password);
		} catch (Exception e) {
			e.printStackTrace();
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