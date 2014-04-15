package entities;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

/**
 * RadiologyRecord is a model class representing a complete radiology record and 
 * information on the patient
 * 
 * 
 * @see User
 * @see Record
 *
 **/

public class RadiologyRecord {
	
	private User user;
	private Record record;

	public RadiologyRecord(User user, Record record) {
		this.user = user;
		this.record = record;
	}

	public User getUser() {
		return this.user;
	}

	public Record getRecord() {
		return this.record;
	}
}