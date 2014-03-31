<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Radiology Information System</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Radiology Information System Home">
<meta name="author" content="">
</head>

<%@ include file="/layout/headlib.jsp"%>
<body>
    <%@ include file="/layout/nav_bar.jsp"%>
    <div class="container">

    <div class="container">
        <!-- Main hero unit for a primary marketing message or call to action -->
        <div class="hero-unit" style="text-align:left">

        <h1>Table of Contents</h1>
                <a href="#install">Installation Instructions</a><br>
                <a href="#user">User Management</a><br>
                <a href="#upload">Uploading Radiology Records</a><br>
                <a href="#reports">Diagnosis Reports</a><br>
                <a href="#search">Searching for Radiology Records</a><br>

            <h1 id="install">Installation Instructions</h1>
                <ol><li>Download the program's tar file.</li>
                    <li>Untar the file in the /catalina/webapps directory. A directory named "Radiology-Information-System" should have been created in the process.</li>
                <li>$ cd /path/to/catalina/webapps/Radiology-Information-System/WEB-INF/classes</li>
                <li>$ make all</li>
                <li>$ starttomcat</li>
                <li>Open a browser and
                    navigate to
                    http://ui???.cs.ualberta.ca:<your-first-port-number>/Radiology-Information-System/</li>
                </ol>
            
            <h1 id="user">User Management</h1>
            <h3>Login</h3>
            <p>To login, you must first be registered. You must
            also enter a valid username and password combination. If an
            incorrect combination is entered, you will not be allowed to access the system.</p>

            <h3>Register Users</h3>
            <p>As an administrator, register new users to the system by entering a unique username and email.
            If the entered username and/or email is already in use, you
            will not be allowed to proceed. The new user must be assigned a password and a user class (administator, doctor, patient, or radiologist). You must also enter the user's first name, last name, address, email, and phone number. </p>
            <h3>Logout</h3>
            <p>You may logout of the system by clicking the "logout"
            link in the navigation bar.</p>

            <h3>Manage Patients</h3>
            <p>As an administrator, you can modify which patients are under a specified doctor. Click on the "User Management" link in the navigation bar to manage the patients in the system.</p>
            <p>All doctors are listed at the top of a table which lists their patients.
            To add a patient to their list, enter the username of a patient and click the "Add Patient" button to add him/her to the list</p>
            <p>To delete patient from a table, click the checkbox next to his/her
            name.</p>
            <p>Click the "Submit" button for the changes to take effect.</p>

            <h1 id="upload">Uploading Files</h1>
            <h3>Radiology Images</h3>
            <p>As a radiologist, to upload a radiology image to include with the record, 
            you must select a file in your director. You must
            enter the username of the patient and the doctor associated with the record.</p>

            <p>Radiology records are submitted to the
            database when the "Submit Record" button at the bottom of the page
            is clicked.</p>

            <h1 id="reports">Diagnosis Reports</h1>
            <h3>Search for Diagnosis Reports</h3>

            <p>As an administrator, you may generate radiology reports by clicking the
            "Reports" link in the navigation bar.</p>
            <p>Generate reports by specifying a diagnosis and a date range</p>
            
            <p>Results will be displayed in a table which contains the following fields:</p>
                <ul>             
                <li>First Name</li>
                <li>Last Name</li>
                <li>Address</li>
                <li>Phone Number</li>
                <li>Testing Date</li>
                </ul>


            <h1 id="search">Searching for Records</h1>

                    <p>You may search through Radiology Records by clicking the "Search" link
                    in the navigation bar. A search is performed by typing
                    in any number of keywords and/or providing a date range.</p>

            <h3>Keywords</h3>

            <p>Searching with keyword(s) will limit the search results to
            records containing the Patient Name, or the keyword(s) in the Diagnosis, or
            Description.</p>

            <h3>Date</h3>

            <p>Selecting a date range will limit the search results to
            radiology records with a test date in the selected range.</p>

    <%@ include file="/layout/footer.jsp"%>
</body>
</html>