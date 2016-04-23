DELETE FROM subject_student;
DELETE FROM subject_teacher;
DELETE FROM team_person;
DELETE FROM team;
DELETE FROM project;
DELETE FROM person;
DELETE FROM subject;

ALTER TABLE person AUTO_INCREMENT = 1;
ALTER TABLE subject AUTO_INCREMENT = 1;
ALTER TABLE project AUTO_INCREMENT = 1;
ALTER TABLE team AUTO_INCREMENT = 1;

INSERT INTO person (login, password, name, surname, mail, acc_type) VALUES
("teacher", "teacher", "Default", "Teacher", "teacher@uni.edu", "TEACHER"),
("student", "student", "Default", "Student", "student@uni.edu", "STUDENT");
INSERT INTO subject (name) VALUES
("Advanced Information Systems"),
("Theoretical Computer Science"),
("Advanced Operating Systems");
INSERT INTO project (name, subject_id, teamsize, capacity, regBegin, regEnd, deadline) VALUES
("Java EE", 1, 3, 50, '2016-03-01 20:00:00', '2016-03-31 20:00:00', '2016-05-03 21:00:00'),
("Cache", 1, 1, 50, '2016-04-01 20:00:00', '2016-04-30 20:00:00', '2016-05-03 21:00:00'),
("Assignment 1", 2, 1, 250, '2015-10-01 15:00:00', '2015-10-30 22:00:00', '2015-11-30 22:00:00'),
("Producer-consumer problem", 3, 1, 150, '2016-05-20 20:00:00', '2016-05-21 20:00:00', '2016-08-01 21:00:00');
INSERT INTO team (name, capacity, project_id) VALUES
("Team Forsen", 1, 1),
("Team Reckful", 1, 2);
INSERT INTO team_person (team_id, members_id) VALUES
(1, 2),
(2, 2);
INSERT INTO subject_student (enrolled_id, students_id) VALUES
(1, 2),
(2, 2),
(3, 2);
INSERT INTO subject_teacher (teachedsubjects_id, teachers_id) VALUES
(1, 1),
(2, 1),
(3, 1);