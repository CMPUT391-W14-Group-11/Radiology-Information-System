import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

import entities.*;

/**
 * Servlet implementation class UserManagmentServlet
 * Handles HTTPServletRequest from user_management.jsp
 *
 * <url-pattern>/users</url-pattern>
 *
 *  @author	Jessica Surya
 *
 */
public class UserManagementServlet extends HttpServlet {
       
    	/**
    	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     	*      response)
     	*/
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		String username = request.getParameter("searchUser");
		String view = request.getParameter("editUser");
		if (username != null || view != null) {
			editUser(request, response);
		}
		else {
			String error = " User not found.";
			request.setAttribute("error", error);
			response.sendRedirect("user_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Db database = new Db();
	
		ArrayList<Integer> doctors = database.getClassMembers("d"); 
		database.close();

		int result = -1;
		if(doctors != null) {
			
			for(int doctor_id : doctors) {
				database = new Db();
				String tableDoctor = request.getParameter("deletePatient-" + doctor_id);
				String addPatient = request.getParameter("addTo-" + doctor_id);
				String addPatientID = request.getParameter("addPatient-" + doctor_id);
				User patient = database.getUser(addPatientID);
				String[] doctorPatientList = request.getParameterValues("removePatient");
				database.close();
				
				if (tableDoctor != null && doctorPatientList != null) {
					result = removePatientList(doctor_id, doctorPatientList);
				}

				if (addPatient != null && addPatientID != null && patient != null) {
					result = addPatient(doctor_id, patient.getPersonID());
				}
			
				if (tableDoctor != null && doctorPatientList != null) {
					if (result == 0) {
						String message = "Patient(s) has been successfully removed.";
						request.setAttribute("message", message);
						response.sendRedirect("patient_list.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
					}
					else{
						String error = "Failed to add patient(s)";
						request.setAttribute("error", error);
						response.sendRedirect("patient_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
					}
				}
				if (addPatient != null && addPatientID != null) {
					if (result == 0) {
						String message = "Patient(s) has been successfully added.";
						request.setAttribute("message", message);
						response.sendRedirect("patient_list.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
					}
				
					else {
						String error = "Failed to remove patient(s)";
						request.setAttribute("error", error);
						response.sendRedirect("patient_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
					}
				}
			}

		}
		else {
			String error = "Failed to edit patient list";
			request.setAttribute("error", error);
			response.sendRedirect("patient_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer person_id = -1;
		try {
			person_id = Integer.valueOf(request.getParameter("editUser"));
		} catch (NumberFormatException e) {
		 	e.printStackTrace();
		}
		
		String username = request.getParameter("searchUser");
		System.out.println(person_id);
		System.out.println(username);

		Db database = new Db();
		User user = null;
		if (username != null) {
			user = database.getUser(username);

		}
		else {
			user = database.getUser(person_id);
		}
		
		if (user != null) {
			request.setAttribute("Username", user.getUsername());
			request.setAttribute("Password", user.getPassword());
			request.setAttribute("FirstName", user.getFirstName());
			request.setAttribute("LastName", user.getLastName());
			request.setAttribute("Address", user.getAddress());
			request.setAttribute("Email", user.getEmail());
			request.setAttribute("Phone", user.getPhone());
			request.setAttribute("PersonID", user.getPersonID());

			if(user.getUserClass().equals("a")) {
		           	request.setAttribute("SelectA", "selected");
		        }
		        else if(user.getUserClass().equals("p")) {
		            	request.setAttribute("SelectP", "selected");
		        }
		        else if(user.getUserClass().equals("d")) {
		            	request.setAttribute("SelectD", "selected");
		        }
		        else if(user.getUserClass().equals("r")) {
		            	request.setAttribute("SelectR", "selected");
		        }
		        else {
		        	System.out.println("failed");
		        }

			request.getRequestDispatcher("user_form.jsp").forward(request, response);
		}
		else {
			String error = "Failed to edit patient";
			request.setAttribute("error", error);
			response.sendRedirect("patient_list.jsp?error=" + URLEncoder.encode(error, "UTF-8"));
		}
	}

	/**
	 *
	 * Remove patient from family_doctor table
	 *
	 */
	protected int removePatientList(int doctor_id, String[] patient_id) {
		
		Db database = new Db();
		for (int i = 0; i < patient_id.length ; i++) {
			System.out.println("Patient to be removed: " + Integer.valueOf(patient_id[i]));
			database.removePatient(doctor_id, Integer.valueOf(patient_id[i]));	
		}

		database.close();
		return 0;
	}
	
	/**
	 *
	 * Add patient to family_doctor table
	 *
	 */
	protected int addPatient(int doctor_id, int patient_id) {
		
		Db database = new Db();
		System.out.println(patient_id);
		database.addPatient(doctor_id, patient_id);	

		database.close();
		return 0;
	}	
}

