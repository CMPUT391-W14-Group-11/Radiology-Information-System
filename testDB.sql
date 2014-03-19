/*
 *  File name:  testDB.sql
 *  Function:   to populate the initial database schema for the CMPUT 391 project,
 *              Winter Term, 2014
 *  Author:     
 */

INSERT INTO persons VALUES (
	0, 'person0-first_name', 'person0-last_name', 'address', 'person0-email', 'phone'
);

INSERT INTO persons VALUES (
	1, 'person1-first_name', 'person1-last_name', 'address', 'person1-email', 'phone'
);

INSERT INTO persons VALUES (
	2, 'person2-first_name', 'person2-last_name', 'address', 'person2-email', 'phone'
);

INSERT INTO persons VALUES (
	3, 'person3-first_name', 'person3-last_name', 'address', 'person3-email', 'phone'
);

INSERT INTO persons VALUES (
	4, 'person4-first_name', 'person4-last_name', 'address', 'person4-email', 'phone'
);

INSERT INTO persons VALUES (
	5, 'person5-first_name', 'person5-last_name', 'address', 'person5-email', 'phone'
);

INSERT INTO persons VALUES (
	6, 'person6-first_name', 'person6-last_name', 'address', 'person6-email', 'phone'
);

INSERT INTO users VALUES (
	'admin', 'password', 'a', 0, CURRENT_DATE
);

INSERT INTO users VALUES (
	'patient1', 'password', 'p', 1, CURRENT_DATE
);

INSERT INTO users VALUES (
	'patient2', 'password', 'p', 2, CURRENT_DATE
);

INSERT INTO users VALUES (
	'patient3', 'password', 'p', 3, CURRENT_DATE
);

INSERT INTO users VALUES (
	'patient4', 'password', 'p', 4, CURRENT_DATE
);

INSERT INTO users VALUES (
	'patient5', 'password', 'p', 5, CURRENT_DATE
);

INSERT INTO users VALUES (
	'patient6', 'password', 'p', 6, CURRENT_DATE
);

INSERT INTO users VALUES (
	'doctor1', 'password', 'd', 7, CURRENT_DATE
);

INSERT INTO family_doctor VALUES (
	1, 1
);