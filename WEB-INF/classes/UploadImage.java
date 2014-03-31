



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
*/

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

	/**
	 * doPost handles uploading images process
	 */
	public void doPost(HttpServletRequest request,HttpServletResponse response)
                throws ServletException, IOException {

		// change the following parameters to connect to the oracle database
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Integer rid = (Integer) session.getAttribute("saved_record");

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
			response_message = ex.getMessage();
		}

		int reply = JOptionPane.showConfirmDialog(null,
					"Your images have been uploaded successfully. Would you "
					+"like to upload more images?", "Continue?",
					JOptionPane.YES_NO_OPTION);

		if(reply == JOptionPane.NO_OPTION){
			session.removeAttribute("saved_record");
			response.sendRedirect("/proj1/login.jsp");
		}else{
			response.sendRedirect("/proj1/upload_records.jsp");
		}
	}
}
