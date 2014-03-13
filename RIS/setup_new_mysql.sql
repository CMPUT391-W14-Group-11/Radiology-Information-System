/*
 *  File name:  setup.sql
 *  Function:   to create the initial database schema for the CMPUT 391 project,
 *              Winter Term, 2014
 *  Author:     Prof. Li-Yan Yuan
 */
DROP TABLE IF EXISTS family_doctor;
DROP TABLE IF EXISTS pacs_images;
DROP TABLE IF EXISTS radiology_record;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS persons;

/*
 *  To store the personal information
 */
CREATE TABLE persons (
   `person_id` int(7),
   `first_name` varchar(24),
   `last_name`  varchar(24),
   `address`   varchar(128),
   `email`      varchar(128),
   `phone`      char(10),
   PRIMARY KEY(`person_id`),
   UNIQUE (`email`)
);

/*
 *  To store the log-in information
 *  Note that a person may have been assigned different user_name(s), depending
 *  on his/her role in the log-in  
 */
CREATE TABLE users (
   `user_name` varchar(24),
   `password`  varchar(24),
   `class`     char(1),
   `person_id` int(7),
   `date_registered` date,
   CHECK (class in ('a','p','d','r')),
   PRIMARY KEY(`user_name`),
   FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`)
);

/*
 *  to indicate who is whose family doctor.
 */
CREATE TABLE family_doctor (
   `doctor_id`    int(7),
   `patient_id`   int(7),
   FOREIGN KEY(`doctor_id`) REFERENCES `persons` (`person_id`),
   FOREIGN KEY(`patient_id`) REFERENCES `persons`(`person_id`),
   PRIMARY KEY(`doctor_id`,`patient_id`)
);

/*
 *  to store the radiology records
 */
CREATE TABLE radiology_record (
   `record_id`   int(5),
   `patient_id`  int(7),
   `doctor_id`   int(7),
   `radiologist_id` int(7),
   `test_type`   varchar(24),
   `prescribing_date` date,
   `test_date`    date,
   `diagnosis`    varchar(128),
   `description`   varchar(1024),
   PRIMARY KEY(`record_id`),
   FOREIGN KEY(`patient_id`) REFERENCES `persons` (`person_id`),
   FOREIGN KEY(`doctor_id`) REFERENCES  `persons` (`person_id`),
   FOREIGN KEY(`radiologist_id`) REFERENCES `persons` (`person_id`)
);

/*
 *  to store the pacs images
 */
CREATE TABLE pacs_images (
   `record_id`   int(5),
   `image_id`    int(5),
   `thumbnail`   blob,
   `regular_size` blob,
   `full_size`    blob,
   PRIMARY KEY(`record_id`,`image_id`),
   FOREIGN KEY(`record_id`) REFERENCES `radiology_record` (`record_id`)
);
