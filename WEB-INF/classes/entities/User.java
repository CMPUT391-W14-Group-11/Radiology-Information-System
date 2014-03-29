package entities;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Object;


/**
 * User is a model class representing a user which will use our web 
 * application. A user can be an adminministrator, patient, doctor, 
 * or radiologist. A User has the following properties:
 * 
 * <ul>
 * <li>user_name
 * <li>password  varchar(24),
 * <li>class     char(1),
 * <li>person_id int,
 * <li>date_registered date,
 * </ul>
 * 
 * Additional information can be added to a user in the persons table
 * which includes the following properties:
 *
 * <ul>
 * <li>person_id int,
 * <li>first_name varchar(24),
 * <li>last_name  varchar(24),
 * <li>address    varchar(128),
 * <li>email      varchar(128),
 * <li>phone      char(10),
 * </ul>
 *
 **/

 public class User {

	protected String user_name;
	protected String password;
	protected String user_class;
	protected int person_id;
	protected java.util.Date date_registered;

	protected String first_name;
	protected String last_name;
	protected String address;
	protected String email;
	protected String phone;
	protected int record_int;
	protected int patient_int;
	protected int doctor_id;
	protected int radiologist_id;
	protected String test_type;
	protected String prescribing_date;
	protected String test_date;
	protected String diagnosis;
	protected String description;
	protected String begin;
	protected String end;
	

	/**
	 * Constructor method for new users
	 * 
	 *
	 */
	public User(String user_name, String password, String user_class, int person_id) {
		this.user_name = user_name;
		this.password = password;
		this.user_class = user_class;
		this.person_id = person_id;
	}

	/**
	 * Constructor method without requiring password
	 * 
	 */
	public User(String user_name, String user_class, int person_id) {
		this.user_name = user_name;
		this.user_class = user_class;
		this.person_id = person_id;
	}

	/**
	 * Get the username of the user 
	 * 
	 * @return The username which is used to uniquely identify the user from
	 *         other users.
	 */
	public String getUsername() {
		return user_name;
	}

	/**
	 * Set the username of the user
	 * 
	 * @param username
	 *            The username to set for the user.
	 */
	public void setUsername(String user_name) {
		this.user_name = user_name;
	}

	/**
	 *  Get the password of the user.
	 * 
	 * @return The password which is used by the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password of the user.
	 * 
	 * @param password
	 *            The password to set for the user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the last name of the user.
	 * 
	 * @return The last name of the user.
	 */
	public String getLastName() {
		return last_name;
	}

	/**
	 * Set the last name of the user.
	 * 
	 * @param last_name
	 *            The last name to set for the user.
	 */
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	/**
	 *  Get the first name of the user.
	 * 
	 * @return The first name of the user.
	 */
	public String getFirstName() {
		return first_name;
	}

	/**
	 * Set the first name of the user.
	 * 
	 * @param firstName
	 *            The first name to set for the user.
	 */
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	/**
	 *  Get the user_class of the user.
	 * 
	 * @return The user_class of the user.
	 */
	public String getUserClass() {
		return user_class;
	}

	/**
	 * Set the user_class of the user.
	 * 
	 * @param user_class
	 *            The user_class to set for the user.
	 */
	public void setUserClass(String user_class) {
		this.user_class = user_class;
	}

	/**
	 *  Get the address of the user.
	 * 
	 * @return The address of the user.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the address of the user.
	 * 
	 * @param address
	 *            The address to set for the user.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 *  Get the email of the user.
	 * 
	 * @return The email of the user.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email of the user.
	 * 
	 * @param email
	 *            The email to set for the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *  Get the phone of the user.
	 * 
	 * @return The phone of the user.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set the phone of the user.
	 * 
	 * @param phone
	 *            The phone to set for the user.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 *  Get the person_id of the user.
	 * 
	 * @return The person_id of the user.
	 */
	public int getPersonID() {
		return person_id;
	}

	/**
	 * Set the person_id of the user.
	 * 
	 * @param person_id
	 *            The person_id to set for the user.
	 */
	public void setPersonID(int person_id) {
		this.person_id = person_id;
	}
	public int getRecordID(){
		return record_id;
	}
	public void setRecordID(int record_id){
		this.record_id=record_id;
	}
	public getPateintID(){
		return patient_id;
	}
	public setPatientID(){
		this.patient_id=patient_id;
	}
	//public getDoctorID(){
		return doctor_id;
	}
	public setDoctorID(){
		this.doctor_id=doctor_id;
	}
	public getRadiologistID(){
		return radiologist_id;
	}
	public setRadiologistID(){
		this.radiologist_id=radiologist_id;
	}
	public getTestType(){
		return test_type;
	}
	public setTestType(){
		this.test_type=test_type;
	}
	public getPresDate(){
		return prescribing_date;
	}
	public setPresDate(){
		this.prescribing_date=prescribing_date;
	}
	public getTestDate(){
		return test_date;
	}
	public setTestDate(){
		this.test_date=test_date;
	}
	public getdiagnosis(){
		return diagnosis;
	}
	public setdiagnosis(){
		this.diagnosis=diagnosis;
	}
	public getdescription(){
		return patient_id;
	}
	public setdescription(){
		this.description=description;
	}
	public getStartDate(){
	return begin;
}
	public setStartDate(){
	this.begin=begin;
}

	public EndDate(){
	return end;
}
	public setEndDate(){
	this.end=end;
}

	
	



}
