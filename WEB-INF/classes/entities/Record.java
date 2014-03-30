package entities;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Object;

/**
 * Report is a model class representing a radiology record in this web application
 * A report is associated with patients and contains the following information:
 * 
 * <ul>
 * <li>record_id   int,
 * <li>patient_id  int,
 * <li>doctor_id   int,
 * <li>radiologist_id int,
 * <li>test_type   varchar(24),
 * <li>prescribing_date date,
 * <li>test_date    date,
 * <li>diagnosis    varchar(128),
 * <li>description   varchar(1024),
 * </ul>
 *
 **/

 public class Record {
	private int record_id;
	private int patient_id;
	private int doctor_id;
	private int radiologist_id;
	private String test_type;
	private java.util.Date  prescribing_date;
	private java.util.Date test_date;
	private String diagnosis;
	private String description;
		
	/**
	 * Constructor method for new reports
	 * 
	 *
	 */
	public Record(int record_id, int patient_id, int doctor_id, int radiologist_id, String test_type) {
		this.record_id = record_id;
		this.patient_id = patient_id;
		this.doctor_id = doctor_id;
		this.radiologist_id = radiologist_id;
		this.test_type = test_type;
	}

	/**
	 * Alternate constructor method
	 * 
	 */
	public Record(int patient_id, int doctor_id, java.util.Date test_date) {
		this.patient_id = patient_id;
		this.doctor_id = doctor_id;
		this.test_date = test_date;
	}

	/**
	 * Get the record_id in the report
	 * 
	 * @return The record_id which is used to uniquely identify the user from
	 *         other users.
	 */
	public int getRecordID() {
		return record_id;
	}

	/**
	 * Set the record_id in the report
	 * 
	 * @param record_id
	 *            The record_id to set for the report.
	 */
	public void setRecordID(int record_id) {
		this.record_id = record_id;
	}

	/**
	 * Get the patient_id in the report
	 * 
	 * @return The patient_id which is used to uniquely identify the user from
	 *         other users.
	 */
	public int getPatientID() {
		return patient_id;
	}

	/**
	 * Set the patient_id in the report
	 * 
	 * @param patient_id
	 *            The patient_id to set for the report.
	 */
	public void setPatientID(int patient_id) {
		this.patient_id = patient_id;
	}

	/**
	 *  Get the doctor_id in the report.
	 * 
	 * @return The doctor_id which is used by the report.
	 */
	public int getDoctorID() {
		return doctor_id;
	}

	/**
	 * Set the doctor_id in the report.
	 * 
	 * @param doctor_id
	 *            The doctor_id to set for the report.
	 */
	public void setDoctorID(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	/**
	 * Get the radiologist_id in the report.
	 * 
	 * @return The radiologist_id in the report.
	 */
	public int getRadiologistID() {
		return radiologist_id;
	}

	/**
	 * Set the radiologist_id in the report.
	 * 
	 * @param radiologist_id
	 *            The radiologist_id to set for the report.
	 */
	public void setRadiologistID(int radiologist_id) {
		this.radiologist_id = radiologist_id;
	}

	/**
	 *  Get the test type of the report.
	 * 
	 * @return The test type of the report.
	 */
	public String getTestType() {
		return test_type;
	}

	/**
	 * Set the test type of the report.
	 * 
	 * @param test_type
	 *            The test type to set for the report.
	 */
	public void setTestType(String test_type) {
		this.test_type = test_type;
	}

	/**
	 *  Get the prescribing_date of the report.
	 * 
	 * @return The prescribing_date of the report.
	 */
	public java.util.Date getPrescribingDate() {
		return prescribing_date;
	}

	/**
	 * Set the prescribing_date of the report.
	 * 
	 * @param prescribing_date
	 *            The prescribing_date to set for the report.
	 */
	public void setPrescribingDate(java.util.Date prescribing_date) {
		this.prescribing_date = prescribing_date;
	}

	/**
	 *  Get the test_date of the report.
	 * 
	 * @return The test_date of the report.
	 */
	public java.util.Date getTestDate() {
		return test_date;
	}

	/**
	 * Set the test_date of the report.
	 * 
	 * @param test_date
	 *            The test_date to set for the report.
	 */
	public void setTestDate(java.util.Date test_date) {
		this.test_date = test_date;
	}

	/**
	 *  Get the diagnosis in the report.
	 * 
	 * @return The diagnosis in the report.
	 */
	public String getDiagnosis() {
		return diagnosis;
	}

	/**
	 * Set the diagnosis in the report.
	 * 
	 * @param diagnosis
	 *            The diagnosis to set for the report.
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 *  Get the description in the report.
	 * 
	 * @return The description in the report.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description in the report.
	 * 
	 * @param description
	 *            The description to set for the report.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
