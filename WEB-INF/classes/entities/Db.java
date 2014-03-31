package entities;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Object;
import oracle.sql.*;
import oracle.jdbc.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * Class used to handle all database connections and queries
 *  	
 *  @author	Jessica Surya
 * 	
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
	       try {
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

	/**
	 * Gets the next ID from the database for a specified field
	 * 
	 * @param String fieldID
	 * @param String table
	 * @return int nextID
	 * @return int 0 on failure
	 */
	public int getNextID(String fieldID, String table) {
		
		try {
			String query = "SELECT MAX(" + fieldID + ") FROM " + table ;
			
			ResultSet rs = performQuery(query);
			if(rs.next()) {
				int num = rs.getInt(1) + 1;
				while(checkValidity(num, fieldID, table) == false) {
					num++;
				}
				rs.close();
				return num;
			}
			rs.close();
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public boolean checkValidity(int num, String fieldID, String table) {
		
		try {
			String query = "SELECT COUNT(*) FROM " + table + " WHERE " 
			+ fieldID + " = " + num;

			ResultSet check = performQuery(query);

			if(check.next()) {
				check.close();
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
				rs.close();
			    empty = false;
			}

			if( empty ) {
			    // Empty result set
				stmt.close();
				rs.close();
				return false;
			} else {
				stmt.close();
				rs.close();
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
				con.prepareStatement("INSERT INTO persons "
				+ "(person_id, first_name, last_name, address, email, phone) "
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
				con.prepareStatement("INSERT INTO users "
				+ "(user_name, password, class, person_id, date_registered)" 
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

			rs.close();

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
	 * Get a user from the database by username 
	 * 
	 * @param String username
	 * @return User object
	 */
	public User getUser(String username) {
		User user;

		try {
			PreparedStatement stmt = 
				con.prepareStatement("SELECT p.person_id, u.user_name, p.first_name, p.last_name, u.class, u.password, p.address, p.email, p.phone "
				+ "FROM persons p, users u WHERE p.person_id = u.person_id AND u.user_name = ?");
			stmt.setString(1, username); 

			ResultSet rs = stmt.executeQuery();

			if ( rs.next() ) {
			    // ResultSet processing here
				int person_id = rs.getInt("person_id");
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
			stmt.close();
			rs.close();

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
				stmt.close();
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
				stmt.close();
				return 2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return 0;
	}

	/**
	 * 
	 * User Management Module
	 *
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

			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	/**
	 * 
	 * User Management Module
	 *
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
				+ "FROM persons p, users u WHERE p.person_id = u.person_id "
				+ "AND u.class = ? ORDER BY p.last_name DESC");
			stmt.setString(1, userClass);
			ResultSet rs = stmt.executeQuery();

			while( rs.next() ) {
			    // ResultSet processing here
				Integer person_id = Integer.valueOf(rs.getInt("person_id"));
				members.add(person_id);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return members;
	}


	/**
	 * 
	 * User Management Module 
	 *
	 * Remove patients from family_doctor table
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
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * User Management Module 
	 *
	 * Add patients to family_doctor table
	 * 
	 * @return int 0 on success
	 */
	public int addPatient(int doctor_id, int patient_id) {
		int result = -1;
		try {
			PreparedStatement stmt = 
				con.prepareStatement("INSERT INTO family_doctor " +
				"(doctor_id, patient_id) VALUES (?, ?)");
			stmt.setInt(1, doctor_id);
			stmt.setInt(2, patient_id);

			result = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 
	 * Report Generation Module
	 *
	 * Gets all radiology records associated with a user (patient)
	 * @param int person_id
	 *
	**/
	public ArrayList<Record> getRecords(int person_id) {
		ArrayList<Record> recs = new ArrayList<Record>();

		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM radiology_record " 
			+ "WHERE patient_id = ? ");
			stmt.setInt(1, person_id);
			ResultSet rset = stmt.executeQuery();

			while(rset != null && rset.next()) {
				int record_id = (rset.getInt("record_id"));
				int patient_id = (rset.getInt("patient_id"));
				int doctor_id = (rset.getInt("doctor_id"));
				int radiologist_id = (rset.getInt("radiologist_id"));
				String test_type = (rset.getString("test_type"));
				java.util.Date prescribing_date = (rset.getDate("prescribing_date"));
				java.util.Date test_date = (rset.getDate("test_date"));
				String diagnosis = (rset.getString("diagnosis"));
				String description = (rset.getString("description"));
				
				Record rec = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);

				rec.setPrescribingDate(prescribing_date);
				rec.setTestDate(test_date);
				rec.setDiagnosis(diagnosis);
				rec.setDescription(description);

				recs.add(rec);
			}
			stmt.close();
			rset.close();

	     } catch(SQLException e) {
			e.printStackTrace();
			
		}
		return recs;
	}


	/**
	 * 
	 * Report Generation Module
	 *
	 * Gets radiology records by record_id
	 * @param int record_id
	 *
	**/
	public Record getRecord(int record_id) {
		Record rec;

		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM radiology_record " 
			+ "WHERE record_id = ? ");
			stmt.setInt(1, record_id);

			ResultSet rset = stmt.executeQuery();
				
			int patient_id = (rset.getInt("patient_id"));
			int doctor_id = (rset.getInt("doctor_id"));
			int radiologist_id = (rset.getInt("radiologist_id"));
			String test_type = (rset.getString("test_type"));
			java.util.Date prescribing_date = (rset.getDate("prescribing_date"));
			java.util.Date test_date = (rset.getDate("test_date"));
			String diagnosis = (rset.getString("diagnosis"));
			String description = (rset.getString("description"));
			
			rec = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);

			rec.setPrescribingDate(prescribing_date);
			rec.setTestDate(test_date);
			rec.setDiagnosis(diagnosis);
			rec.setDescription(description);

			stmt.close();
			rset.close();

			return rec;

	     } catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Report Generation Module
	 *
	 * Gets the list of all patients with a specified diagnosis for a given time period. 
	 * 
	 * TODO: Return only the first record for patients with qualifying multiple records
	 *
	**/
	public ArrayList<Record> getDiagnosisReports(String diagnosis, java.util.Date fDate, java.util.Date tDate) {
		ArrayList<Record> records = new ArrayList<Record>();
		
		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * " 
				+ "FROM radiology_record r"
				+ "WHERE LOWER(r.diagnosis) LIKE LOWER(?) "
				+ "AND r.test_date BETWEEN ? AND ? "
				+ "ORDER BY r.test_date ASC");

			stmt.setString(1, "%" + diagnosis + "%");
			stmt.setDate(2, new java.sql.Date(fDate.getTime()));
			stmt.setDate(3, new java.sql.Date(tDate.getTime()));

			ResultSet rset = stmt.executeQuery();

			while(rset != null && rset.next()) {
				int record_id = (rset.getInt("record_id"));
				int patient_id = (rset.getInt("patient_id"));
				int doctor_id = (rset.getInt("doctor_id"));
				int radiologist_id = (rset.getInt("radiologist_id"));
				String test_type = (rset.getString("test_type"));
				java.util.Date prescribing_date = (rset.getDate("prescribing_date"));
				java.util.Date test_date = (rset.getDate("test_date"));
				String r_diagnosis = (rset.getString("diagnosis"));
				String description = (rset.getString("description"));
				
				Record rec = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);

				rec.setPrescribingDate(prescribing_date);
				rec.setTestDate(test_date);
				rec.setDiagnosis(r_diagnosis);
				rec.setDescription(description);

				records.add(rec);
			}
			stmt.close();
			rset.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return records;
	}

	/**
	 * 
	 * Upload Module
	 *
	 * Add a new radiology record to the database 
	 * 
	 * Records are identified by a unquie record_id
	 *
	 * @param Record object	
	 * @return int 0 on success
	 *
	 **/
	public int insertRadiologyRecord(Record record) {

		//Insert record
		try {
			PreparedStatement stmt = 
				con.prepareStatement("INSERT INTO radiology_record "
				+ "(record_id, patient_id, doctor_id, radiologist_id, test_type, "
				+ "prescribing_date, test_date, diagnosis, description) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)");
			
			System.out.println("record_id: " + record.getRecordID());
			stmt.setInt(1, record.getRecordID()); 
			stmt.setInt(2, record.getPatientID());
			stmt.setInt(3, record.getDoctorID());
			stmt.setInt(4, record.getRadiologistID());
			stmt.setString(5, record.getTestType());
			stmt.setDate(6, new java.sql.Date(record.getPrescribingDate().getTime()));
			stmt.setDate(7, new java.sql.Date(record.getTestDate().getTime()));
			stmt.setString(8, record.getDiagnosis());
			stmt.setString(9, record.getDescription());

			
			if (stmt.executeUpdate() == 1) {
				stmt.close();
				return 0;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}
		return 0;
	}

	/**
	 * 
	 * Upload Module
	 *
	 * Upload an image stored in the user's local file system to the database 
	 * 
	 * Images are associated with a radiology record
	 *
	 *
	 * Modified from UploadImage.java 
	 * Copyright 2007 COMPUT 391 Team, CS, UofA
	 * Author: Fan Deng
	 *
	 * Shrink function from
	 * http://www.java-tips.org/java-se-tips/java.awt.image/shrinking-an-image-by-skipping-pixels.html
	 *
	 *
	 **/
	public void insertPacRecord(FileItem item, Integer rid) {
		
		try{

			InputStream instream = item.getInputStream();
			BufferedImage img = ImageIO.read(instream);
			BufferedImage thumbNail = shrink(img, 10);
		    
			/*
			* First, to generate a unique pic_id using an SQL sequence
			*/
			int pic_id = getNextID("image_id", "pacs_images");
			    
			PreparedStatement stmt = con.prepareStatement("INSERT INTO pacs_images"
			    + " VALUES(record_id = ? . image_id = ? , empty_blob(), empty_blob(), empty_blob())");
				stmt.setInt(1, rid);
				stmt.setInt(2, pic_id);

			ResultSet rset = stmt.executeQuery();

			// to retrieve the lob_locator
			// Note that you must use "FOR UPDATE" in the select statement
			PreparedStatement cmd = con.prepareStatement("SELECT * FROM pacs_images WHERE record_id = ? "
				+ "AND image_id = ? FOR UPDATE");
			stmt.setInt(1, rid);
			stmt.setInt(2, pic_id);

			rset = cmd.executeQuery();
			rset.next();
			BLOB thumbblob = ((OracleResultSet)rset).getBLOB(3);

			//Write the image to the blob object
			OutputStream outstream = thumbblob.getBinaryOutputStream();
			ImageIO.write(thumbNail, "jpg", outstream);
			instream.close();
			outstream.close();	   

			BLOB regblob = ((OracleResultSet)rset).getBLOB(4);
			//Write the image to the blob object
			outstream = regblob.getBinaryOutputStream();
			ImageIO.write(img, "jpg", outstream);           
			instream.close();
			outstream.close();
			BLOB fullblob = ((OracleResultSet)rset).getBLOB(5);
			//Write the image to the blob object
			outstream = fullblob.getBinaryOutputStream();
			ImageIO.write(img, "jpg", outstream);
			instream.close();
			outstream.close();
			stmt.executeUpdate("commit");

			stmt.close();
			rset.close();
		    
		} catch(Exception ex) {
		    System.out.println( ex.getMessage());
		}
		
	}

	//shrink image by a factor of n, and return the shrinked image
	//
	public static BufferedImage shrink(BufferedImage image, int n) {
		
		int w = image.getWidth() / n;
		int h = image.getHeight() / n;
		
		BufferedImage shrunkImage =
			new BufferedImage(w, h, image.getType());
		
		for (int y=0; y < h; ++y)
			for (int x=0; x < w; ++x)
				shrunkImage.setRGB(x, y, image.getRGB(x*n, y*n));
		
		return shrunkImage;
	}

	/**
	 * 
	 * Search Module
	 *
	 * Retrieve an image stored in the database 
	 * 
	 * Images are associated with a radiology record
	 * 
	 * @param String thumbnail/regular_size/full_size
	 *
	 **/
	public Blob getImage(int image_id, String size) {
		ResultSet pac_image;
		Blob image = null;

		try {

			PreparedStatement stmt = con.prepareStatement("SELECT * FROM pacs_images WHERE record_id = ? "
				+ "AND image_id = ?");
			stmt.setInt(1, image_id);
			pac_image = stmt.executeQuery();
			pac_image.next();
			image = ((OracleResultSet)pac_image).getBlob(size);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return image;
	}

	/**
	* Returns an ArrayList of Records of the search by specified keywords and date
	*
	* Rank(record_id) = 6*frequency(patient_name) + 3*frequency(diagnosis) + frequency(description)
	*
	* @param String fromdate, String todate, String keywords, String order
	* @return ArrayList<Record>
	*/
  	public ArrayList<Record> getResultsByDateAndKeywords(java.util.Date fDate, java.util.Date tDate, String[] keywords,
                                                 String order) {
  		ArrayList<Record> records = new ArrayList<Record>();
  		for(int i = 0; i < keywords.length; i++) {

		        String query = "SELECT score(1)*6 + score(2)*3 + score(3) AS score, "
		                        + "record_id FROM radiology_record r, persons p WHERE "
		                        + "p.person_id = r.patient_id AND "
		                        + "((test_date BETWEEN '" + fDate + "' AND '" + tDate + " ') "
		                        + "AND (contains(p.first_name, '" + keywords[i] + "', 1) > 0) OR "
		                        + "(contains(p.last_name, '" + keywords[i] + "', 1) > 0) OR "
		                        + "(contains(r.diagnosis, '" + keywords[i] + "', 2) > 0) OR "
		                        + "(contains(r.description, '" + keywords[i] + "', 3) > 0)) " 
					+ order;
		        ResultSet rset = performQuery(query);
		        try {
			        while(rset != null && rset.next()) {
					int record_id = (rset.getInt("record_id"));
					int patient_id = (rset.getInt("patient_id"));
					int doctor_id = (rset.getInt("doctor_id"));
					int radiologist_id = (rset.getInt("radiologist_id"));
					String test_type = (rset.getString("test_type"));
					java.util.Date prescribing_date = (rset.getDate("prescribing_date"));
					java.util.Date test_date = (rset.getDate("test_date"));
					String r_diagnosis = (rset.getString("diagnosis"));
					String description = (rset.getString("description"));
					
					Record rec = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);

					rec.setPrescribingDate(prescribing_date);
					rec.setTestDate(test_date);
					rec.setDiagnosis(r_diagnosis);
					rec.setDescription(description);

					records.add(rec);
				}
				rset.close();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return records;
	}

	public ArrayList<Record> getResultsByDate(java.util.Date fDate, java.util.Date tDate, String order){
                 ArrayList<Record> records = new ArrayList<Record>();

	        String query = "SELECT score(1)*6 + score(2)*3 + score(3) AS score, "
	                        + "record_id FROM radiology_record r, persons p WHERE "
	                        + "p.person_id = r.patient_id AND "
	                        + "((test_date BETWEEN '" + fDate + "' AND '" + tDate + " ') "
				+ order;
	        ResultSet rset = performQuery(query);
	        
	        try {
		        while(rset != null && rset.next()) {
				int record_id = (rset.getInt("record_id"));
				int patient_id = (rset.getInt("patient_id"));
				int doctor_id = (rset.getInt("doctor_id"));
				int radiologist_id = (rset.getInt("radiologist_id"));
				String test_type = (rset.getString("test_type"));
				java.util.Date prescribing_date = (rset.getDate("prescribing_date"));
				java.util.Date test_date = (rset.getDate("test_date"));
				String r_diagnosis = (rset.getString("diagnosis"));
				String description = (rset.getString("description"));
				
				Record rec = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);

				rec.setPrescribingDate(prescribing_date);
				rec.setTestDate(test_date);
				rec.setDiagnosis(r_diagnosis);
				rec.setDescription(description);

				records.add(rec);
			}
			rset.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	
    	/**
	* Returns an ArrayList of Records of the search by specified keywords
	*
	* Rank(record_id) = 6*frequency(patient_name) + 3*frequency(diagnosis) + frequency(description)
	*
	* @param String[] keywords
	* @return ArrayList<Record>
	*/
    	public ArrayList<Record> searchRecords(String[] keywords) {
		ArrayList<Record> records = new ArrayList<Record>();
	 	
	 	for(int i = 0; i < keywords.length; i++) {

			String query = "SELECT score(1)*6 + score(2)*3 + score(3) AS score, "
	                        + "record_id FROM radiology_record r, persons p WHERE "
	                        + "p.person_id = r.patient_id AND "
	                        + "((contains(p.first_name, '" + keywords[i] + "', 1) > 0) OR "
	                        + "(contains(p.last_name, '" + keywords[i] + "', 1) > 0) OR "
	                        + "(contains(r.diagnosis, '" + keywords[i] +"', 2) > 0) OR "
	                        + "(contains(r.description, '" + keywords[i] + "', 3) > 0)) "; 

			ResultSet rset = performQuery(query);
			try {
				while(rset != null && rset.next()) {
					int record_id = (rset.getInt("record_id"));
					int patient_id = (rset.getInt("patient_id"));
					int doctor_id = (rset.getInt("doctor_id"));
					int radiologist_id = (rset.getInt("radiologist_id"));
					String test_type = (rset.getString("test_type"));
					java.util.Date prescribing_date = (rset.getDate("prescribing_date"));
					java.util.Date test_date = (rset.getDate("test_date"));
					String r_diagnosis = (rset.getString("diagnosis"));
					String description = (rset.getString("description"));
					
					Record rec = new Record(record_id, patient_id, doctor_id, radiologist_id, test_type);

					rec.setPrescribingDate(prescribing_date);
					rec.setTestDate(test_date);
					rec.setDiagnosis(r_diagnosis);
					rec.setDescription(description);

					records.add(rec);
				}

				rset.close();
			} catch (Exception e ) {
				e.printStackTrace();
			}
		}

		return records;
	}

}
