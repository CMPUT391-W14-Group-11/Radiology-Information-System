/**
* A sample program to demonstrate how to use servlet to
* load an image file from the client disk via a web browser
* and insert the image into a table in Oracle DB.
* 
* 
* Modified from UploadImage.java 
* Copyright 2007 COMPUT 391 Team, CS, UofA
* Author: Fan Deng
*
*
**/

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class UploadImage extends HttpServlet {
        public String response_message = "start";
        public void doPost(HttpServletRequest request,HttpServletResponse response)
                throws ServletException, IOException {
// change the following parameters to connect to the oracle database
                PrintWriter out = response.getWriter();
                HttpSession session = request.getSession();
                Integer rid = (Integer) session.getAttribute(".....");
                
                try {
                    
//Parse the HTTP request to get the image stream
                    DiskFileUpload fu = new DiskFileUpload();
                    List FileItems = fu.parseRequest(request);
                    out.println("size:" + FileItems.size() + "<br>");
// Process the uploaded items, assuming only 1 image file uploaded
                    Iterator i = FileItems.iterator();
                    FileItem item = (FileItem) i.next();
                    for(int j = 1; j < FileItems.size(); j++){
                        insertPacRecord(item, rid, response);
                        item = (FileItem) i.next();	
                    }
                    
                } catch( Exception ex ) {
//System.out.println( ex.getMessage());
                    response_message = ex.getMessage();
                }
                
                int reply = JOptionPane.showConfirmDialog(null,"Your images have been uploaded successfully. Would you like to upload more images?", "Continue?", JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.NO_OPTION){
                    session.removeAttribute("Saved_Record_Id");
                    response.sendRedirect("/proj1/homepage.jsp");
                }else{
                    response.sendRedirect("upload_records.jsp");
                }
        }
  
}


