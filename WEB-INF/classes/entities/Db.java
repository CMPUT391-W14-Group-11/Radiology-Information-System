package entities;

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

	/**
	 * Creates an existing User in the database
	 * 
	 * @see User
	 * @param User
	 *            
	 * @return int 0 on success
	 * @return int 1 on invalid email
	 * @return int 2 on invalid username
	 */
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

			
			if (stmt.executeUpdate() == 1) {
				return 1;
			}

			PreparedStatement stmt2 = 
				con.prepareStatement("INSERT INTO users (user_name, password, class, person_id, date_registered)" 
				+ "VALUES (?, ?, ?, ?, CURRENT_DATE)");
			stmt2.setString(1, user.getUsername()); 
			stmt2.setString(2, user.getPassword());
			stmt2.setString(3, (user.getUserClass()));
			stmt2.setInt(4, personID);
				
			if (stmt2.executeUpdate() == 2) {
				return 2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return 0;
	}

	/**
	 * Get all users from the database by username  
	 * 
	 * 
	 * @return ArrayList<User> objects
	 */
	public ArrayList<User> getUserAccounts() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			PreparedStatement stmt = 
				con.prepareStatement("SELECT p.person_id, u.user_name, p.first_name, p.last_name, u.class, p.address, p.email, p.phone" +
				" FROM persons p, users u WHERE p.person_id = u.person_id ORDER BY last_name DESC");
			
			ResultSet rs = stmt.executeQuery();

			while( rs.next() ) {
			    // ResultSet processing here
				int person_id = rs.getInt("person_id");
			    	String user_name = rs.getString("user_name");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String user_class = rs.getString("class");
				String address =  rs.getString("address");
				String email =  rs.getString("email");
				String phone =  rs.getString("phone");
				
				User user = new User(user_name, user_class, person_id);

				user.setFirstName(first_name);
				user.setLastName(last_name);
				user.setAddress(address);
				user.setEmail(email);
				user.setPhone(phone);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * Get a user from the database by person_id 
	 * 
	 * @param int person_id
	 * @return User object
	 */
	public User getUser(int person_id) {
		User user;

		try {
			PreparedStatement stmt = 
				con.prepareStatement("SELECT p.person_id, u.user_name, p.first_name, p.last_name, u.class, u.password, p.address, p.email, p.phone "
				+ "FROM persons p, users u WHERE p.person_id = u.person_id AND p.person_id = ?");
			stmt.setInt(1, person_id); 

			ResultSet rs = stmt.executeQuery();

			if ( rs.next() ) {
			    // ResultSet processing here
				String username = rs.getString("user_name");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String user_class = rs.getString("class");
				String password = rs.getString("password");
				String address =  rs.getString("address");
				String email =  rs.getString("email");
				String phone =  rs.getString("phone");
				
				user = new User(username, user_class, person_id);

				user.setPassword(password);
				user.setFirstName(first_name);
				user.setLastName(last_name);
				user.setAddress(address);
				user.setEmail(email);
				user.setPhone(phone);

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Updates an existing User in the database
	 * 
	 * @see User
	 * @param User
	 *            
	 * @return int 0 on success
	 * @return int 1 on invalid email
	 * @return int 2 on invalid username
	 */
	public int updateUserAccount(User user) {
		int personID = user.getPersonID();
		try {
			PreparedStatement stmt = 
				con.prepareStatement("UPDATE persons SET first_name = ?, last_name = ?, address = ?, email = ?, phone = ?"
				+ " WHERE person_id = ?");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getAddress());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getPhone());
			stmt.setInt(6, personID); 

			if (stmt.executeUpdate() < 1) {
				return 1;
			}

			PreparedStatement stmt2 = 
				con.prepareStatement("UPDATE users SET user_name = ?, password = ?, class = ?" 
				+ " WHERE person_id = ?");
			stmt2.setString(1, user.getUsername()); 
			stmt2.setString(2, user.getPassword());
			stmt2.setString(3, (user.getUserClass()));
			stmt2.setInt(4, personID);

			if (stmt2.executeUpdate() < 1) {
				return 2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return 0;
	}

	/**
	 * Get all patients from the database by doctor  
	 * 
	 * 
	 * @return ArrayList<User> objects
	 */
	public ArrayList<User> getPatients(int doctor_id) {
		ArrayList<User> patients = new ArrayList<User>();

		try {
			PreparedStatement stmt = 
				con.prepareStatement("SELECT p.person_id, u.user_name, p.first_name, p.last_name, u.class, u.password, p.address, p.email, p.phone " +
				" FROM persons p, family_doctor f , users u WHERE p.person_id = f.patient_id AND  u.person_id = f.patient_id AND f.doctor_id = ? ORDER BY p.last_name DESC");
			stmt.setInt(1, doctor_id);

			ResultSet rs = stmt.executeQuery();

			while( rs.next() ) {
			    // ResultSet processing here
				int person_id = rs.getInt("person_id");
			    	String user_name = rs.getString("user_name");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String user_class = rs.getString("class");
				String address =  rs.getString("address");
				String email =  rs.getString("email");
				String phone =  rs.getString("phone");
				
				User user = new User(user_name, user_class, person_id);

				user.setFirstName(first_name);
				user.setLastName(last_name);
				user.setAddress(address);
				user.setEmail(email);
				user.setPhone(phone);

				patients.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	/**
	 * Get all users of in a class from the database
	 * 
	 * 
	 * @return ArrayList<Integer> person_id
	 */
	public ArrayList<Integer> getClassMembers(String userClass) {
		ArrayList<Integer> members = new ArrayList<Integer>();

		try {
			PreparedStatement stmt = 
				con.prepareStatement("SELECT p.person_id "
				+ "FROM persons p, users u WHERE p.person_id = u.person_id AND u.class = ? ORDER BY p.last_name DESC");
			stmt.setString(1, userClass);
			ResultSet rs = stmt.executeQuery();

			while( rs.next() ) {
			    // ResultSet processing here
				Integer person_id = Integer.valueOf(rs.getInt("person_id"));
				members.add(person_id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return members;
	}


	/**
	 * Remove patients from family_doctor table
	 * 
	 * 
	 * @return int 0 on success
	 */
	public int removePatient(int doctor_id, int patient_id) {
		int result = -1;
		try {
			PreparedStatement stmt = 
				con.prepareStatement("DELETE FROM family_doctor " +
				" WHERE patient_id = ? AND  doctor_id = ? ");
			stmt.setInt(1, patient_id);
			stmt.setInt(2, doctor_id);

			result = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public int insertInfo_Report(User user){
		int PersonID=user.getPersonID;
	 	try{
		PreparedStatement stmt = null;
	    ResultSet rset = null;
	/*String sql = "select p.user_name, p.address, p.phone, r.record_id, to_char(r.test_date, 'DD-MON-YYYY') as test_date " +
	"from radiology_record r,persons p where r.patient_name = p.user_name and " +
	"r.diagnosis = ? and r.test_date BETWEEN ? and ? ORDER BY r.test_date asc";*/
	     stmt.setdiagnosis(1, diagnosis);
	     stmt.setStartDate(2, begin);
	     stmt.setEndDate(3, end);
	     rset = stmt.executeUpdate();
	     }
	        catch(SQLException e){
	        	e.printStackTrace();
	        	
	        }
	 	return 0;
	 	
	}
	public ArrayList<User> getReport()
	{
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<String> id = new ArrayList<String>();
	 	ArrayList<String> user_name = new ArrayList<String>();
	 	ArrayList<String> phone = new ArrayList<String>();
	 	ArrayList<String> address = new ArrayList<String>();
	 	ArrayList<String> test_date = new ArrayList<String>();
		try{
			PreparedStatement stmt = con.prepareStatement("Select p.useif(id.sir_name, p.address, p.phone, r.record_id, to_char(r.test_date, 'DD-MON-YYYY') AS test_date " +
		    "From radiology_record r,persons p Where r.patient_name = p.user_name and " +
		     "r.diagnosis = ? and r.test_date BETWEEN ? and ? ORDER BY r.test_date asc");
		     ResultSet rset=stmt.executeQuery();
		     while(rset != null && rset.next()){
		    	 user_name = (rset.getUsername("user_name"));
		    	 address = (rset.getAddress("address"));
		    	 phone = (rset.getPhone("phone"));
		    	 test_date = (rset.getTest_Date("test_date"));
	     }

				User user = new User(user_name, user_class, person_id);
				if(id.size()==0){}
				else{
					for(int i=0;i<id.size();i++)
					{
						user.get(i);
					}
				}

	 	}catch(SQLException e){
			e.printStackTrace();
		}
		return users;
	}
	public int Info_forSearch(User user){
		int RecordID=user.getRecordID;
		String sql="select";
	 	try{
	 		PreparedStatement stmt = null;
	 		ResultSet rset = null;
	 		stmt = conn.prepareStatement(sql);
	 		int i = 1;
	 		if(!startDate.equals("")){
	 			stmt.setString(i++, startDate);
	 			}
	 		if(!endDate.equals("")){
	 			stmt.setString(i++, endDate);
	 			}
	 		if(!keywords.equals("")){
	 			stmt.setString(i++, keywords);
	 			stmt.setString(i++, keywords);
	 			stmt.setString(i++, keywords);
	 			}
	 		if(!user_class.equals("a")){
	 			stmt.setUserName(i++, user_name);
	 		}
	 	}catach(SQLException e)
	 	{
	 		e.printStackTrace();

	 	return 0;
	}
	public ArrayList<User> getSearch_results()
	{
		ArrayList<String> clauses = new ArrayList<String>();
	     //Add start and end date clauses
	     if(!startDate.equals("")){
	    	 clauses.add("test_date >= ? ");
	     }
	     
	     if(!endDate.equals("")){
	    	 clauses.add("test_date <= ? ");
	     }
	     if(!keywords.equals("")){
	     //Add CONTAINS and SCRORE for rankings
	    	 clauses.add("contains(r.patient_name, ?, 1) >= 0 and contains(r.diagnosis, ?, 2) >= 0 "+
	     "and contains(r.description, ?, 3) >= 0 ");
	     String score = "score(1)*6 + score(2)*3 + score(3) as rank, ";
	     sql = sql.concat(score);
	     }

	     //Add extra clause depending on user class
	     /*String userName = (String) session.getAttribute("name");
	String classtype = (String) session.getAttribute("classtype");*/
	if(user_class.equals("r")){
		clauses.add("r.radiologist_name = ? ");
	}
	else if(user_class.equals("d")){
		clauses.add("? IN (SELECT d.doctor_name from family_doctor d where d.patient_name = r.patient_name) ");
	}
	else if(user_class.equals("p")){
		clauses.add("r.patient_name = ? ");
	}
	    
	//Add ordering
	     String orderBy = "";
	     if(request.getParameter("order").equals("Rank")){
	    	 orderBy = "rank DESC";
	     }
	     else if(request.getParameter("order").equals("Most-Recent-First")){
	    	 orderBy = "r.test_date DESC";
	     }
	     else if(request.getParameter("order").equals("Most-Recent-Last")){
	    	 orderBy = "r.test_date ASC";
	     }
	    
	     //Columns for table
	     String start = "r.record_id, r.patient_name, r.doctor_name, r.radiologist_name, r.test_type, "+
	     "to_char(r.prescribing_date, 'DD-MON-YYYY') as prescribing_date, to_char(r.test_date, 'DD-MON-YYYY') as test_date, "+
	     "r.diagnosis, r.description from radiology_record r ";
	sql = sql.concat(start);

	//Add in clauses to query
	boolean first = true;
	for (String value : clauses){
	if(first){
	first = false;
	sql = sql.concat("WHERE "+ value);
	}
	else
	sql = sql.concat(" AND "+ value);
	}	
	sql = sql.concat("ORDER BY "+orderBy);
	}
	//Get table column titles
	ResultSet rset = stmt.executeQuery();
	ResultSetMetaData rsmd = rset.getMetaData();
	int colCount = rsmd.getColumnCount();
	int loop = 1;
	if(!keywords.equals("")){
		loop = 2;
	for (int j=loop; j<= colCount; j++) {
		rsmd.getColumnName(j);
	}
	}
	//Print rows
	while(rset != null && rset.next()){
		if(!(!keywords.equals("") && rset.getInt(1) == 0)){
			int recid = 2;
			if(keywords.equals(""))
				recid = 1;
			for(int k=loop;k<=colCount; k++) {
				rset.getString(k);
	}
	}
		//Print pictures corresponding to row
		Statement picStmt = conn.createStatement();
		String pquery = "select image_id from pacs_images where record_id = "+rset.getString(recid);
		ResultSet picset = picStmt.executeQuery(pquery);
		if(picset != null && picset.next()){
			System.out.println("<tr><td COLSPAN="+colCount+">Images for record_id "+rset.getString(recid)+":");
			String end = "rec="+rset.getString(recid)+"&pic="+picset.getString(1);
	            out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
	            out.println("<img src=\"GetOnePic?"+end+"\" height=\"45\" width=\"60\"></a>");
	            while(picset.next()){
	            	end = "rec="+rset.getString(recid)+"&pic="+picset.getString(1);
	            	System.out.println("<a href=\"GetOnePic?size=full&"+end+"\" target=\"_blank\">");
	            	System.out.println("<img src=\"GetOnePic?"+end+"\" height=\"45\" width=\"60\"></a>");
	}
	}
	}
	}

	}
	     catch(Exception ex){
	    	 ex.printStackTrace();
	}
	     public int Upload_Record(User user){
	 		int RadiologistID=user.getRadiologistID;
	 		int RecordID=user.getRecordID;
	 		Statement recstmt = conn.createStatement();
	 		ResultSet recrset = recstmt.executeQuery("SELECT record_seq.nextval from dual");
	 		recrset.next();
	 		int recid = recrset.getInt(1);

	 		//Insert record
	 		PreparedStatement stmt = null;
	 		     ResultSet rset = null;
	 		String sql = "insert into radiology_record values("+recid+",?,?,?,?,?,?,?,?)";
	 		try{
	 		stmt = conn.prepareStatement(sql);
	 		     stmt.setFirstname(1, first_name);
	 		     stmt.setLastName(1, last_name);
	 		     stmt.setDoctor_Name(2, doc_name);
	 		     stmt.setUsername(3, user_ame);
	 		     stmt.setTest_Type(4, testtype);
	 		     stmt.setPres_Date(5, presdate);
	 		     stmt.setTest_Date(6, testdate);
	 		     stmt.setdiganosis(7, diag);
	 		     stmt.setdescription(8, desc);
	 		     stmt.executeUpdate();

	 		conn.commit();
	 		response.sendRedirect("uploadImage.jsp?recid="+recid+"&result=recok");
	 		}
	 		     catch(Exception ex){
	 		         error = ex.getMessage();
	 		}

	 		try{
	 		         conn.close();
	 		      }
	 		        catch(Exception ex){
	 		        error = ex.getMessage();
	 		        }
	 		}
