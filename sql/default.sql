DELETE FROM subject_student;
DELETE FROM subject_teacher;
DELETE FROM team_student;
DELETE FROM team;
DELETE FROM project;
DELETE FROM person;
DELETE FROM subject;

ALTER TABLE person AUTO_INCREMENT = 1;
ALTER TABLE subject AUTO_INCREMENT = 1;
ALTER TABLE project AUTO_INCREMENT = 1;
ALTER TABLE team AUTO_INCREMENT = 1;

INSERT INTO person (login, password, name, surname, mail, acc_type) VALUES
("admin", "admin", "Default", "Admin", "admin@uni.edu", "ADMIN"),
("teacher", "teacher", "Default", "Teacher", "teacher@uni.edu", "TEACHER"),
("student", "student", "Default", "Student", "student@uni.edu", "STUDENT");
INSERT INTO subject (name) VALUES
("Advanced Information Systems"),
("Theoretical Computer Science"),
("Advanced Operating Systems");
INSERT INTO project (name, subject_id, teamsize, capacity, deadline) VALUES
("Java EE", 1, 3, 50, '2016-05-03 21:00:00'),
("Cache", 1, 1, 50, '2016-05-03 21:00:00'),
("Assignment 1", 2, 1, 250, '2015-11-30 22:00:00'),
("Producer-consumer problem", 3, 1, 150, '2016-08-01 21:00:00');
INSERT INTO team (name, capacity, project_id) VALUES
("Team Forsen", 1, 1),
("Team Reckful", 1, 2),
("Team Mitch", 1, 4);
INSERT INTO team_student (team_id, student_id, points) VALUES
(1, 3, 10),
(2, 3, 20);
INSERT INTO subject_student (enrolled_id, students_id) VALUES
(1, 3),
(2, 3),
(3, 3);
INSERT INTO subject_teacher (teachedsubjects_id, teachers_id) VALUES
(1, 2),
(2, 2),
(3, 2);