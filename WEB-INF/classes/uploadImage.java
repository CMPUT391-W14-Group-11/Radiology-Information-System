/***
* A sample program to demonstrate how to use servlet to
* load an image file from the client disk via a web browser
* and insert the image into a table in Oracle DB.
*
* Copyright 2007 COMPUT 391 Team, CS, UofA
* Author: Fan Deng
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Shrink function from
* http://www.java-tips.org/java-se-tips/java.awt.image/shrinking-an-image-by-skipping-pixels.html
*
*
* the table shall be created using the following
CREATE TABLE pictures (
pic_id int,
pic_desc varchar(100),
pic BLOB,
primary key(pic_id)
);
*
* One may also need to create a sequence using the following
* SQL statement to automatically generate a unique pic_id:
*
* CREATE SEQUENCE pic_id_sequence;
*
***/

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

/**
* The package commons-fileupload-1.0.jar is downloaded from
* http://jakarta.apache.org/commons/fileupload/
* and it has to be put under WEB-INF/lib/ directory in your servlet context.
* One shall also modify the CLASSPATH to include this jar file.
*/
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
                    response.sendRedirect("/proj1/newrecord.jsp");
                }
        }

    /*
/* To connect to the specified database
*/
    private static Connection getConnected( String drivername,
                                            String dbstring,
                                            String username,
                                            String password )
            throws Exception {
            Class drvClass = Class.forName(drivername);
            DriverManager.registerDriver((Driver) drvClass.newInstance());
            return( DriverManager.getConnection(dbstring,username,password));
    }
        
        //shrink image by a factor of n, and return the shrinked image
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
    
        private static void insertPacRecord(FileItem item, Integer rid, HttpServletResponse response){
                String username = "mingxun";
                String password = "hellxbox_4801";
                String drivername = "oracle.jdbc.driver.OracleDriver";
                String dbstring ="jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
                
                try{
                    InputStream instream = item.getInputStream();
                    BufferedImage img = ImageIO.read(instream);
                    BufferedImage thumbNail = shrink(img, 10);
                    PrintWriter out = response.getWriter();
                    
                    Connection conn = getConnected(drivername,dbstring, username,password);
// Connect to the database and create a statement
                    Statement stmt = conn.createStatement();
                    
/*
* First, to generate a unique pic_id using an SQL sequence
*/
                    ResultSet rset1 = stmt.executeQuery("SELECT max(image_id) from pacs_images");
                    rset1.next();
                    int pic_id = rset1.getInt(1) + 1;
                    out.println("<p> in while "+ pic_id + "</p>");
                    
                    stmt.execute("INSERT INTO pacs_images VALUES("+ rid + ", " + pic_id+",empty_blob(), empty_blob(), empty_blob())");
                    
// to retrieve the lob_locator
// Note that you must use "FOR UPDATE" in the select statement
                    String cmd = "SELECT * FROM pacs_images WHERE record_id = "+ rid + "and image_id=" +pic_id +" FOR UPDATE";
                    ResultSet rset = stmt.executeQuery(cmd);
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
                    out.println(" Upload OK! " + pic_id + "<br>");
                    conn.close();
                    
                }catch(Exception ex){
                    System.out.println( ex.getMessage());
                }
                
        }
}


