# Script för att skapa NewtonTestTool databas

DROP SCHEMA IF EXISTS NewtonTestTool;
CREATE SCHEMA NewtonTestTool CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL ON NewtonTestTool.* TO root@localhost IDENTIFIED BY 'root';

USE NewtonTestTool;

CREATE TABLE ManyToMany (
	uId INT UNSIGNED NOT NULL,
	tId INT UNSIGNED NOT NULL,
	FOREIGN KEY (uId) REFERENCES User(uId) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (tId) REFERENCES Test(tId) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;

CREATE TABLE User (
	uId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	pwd VARCHAR(8) NOT NULL,
	email VARCHAR(30) NOT NULL,
	category ENUM('STUDENT', 'TEACHER', 'ADMIN') NOT NULL,
	PRIMARY KEY(uId)
) ENGINE=INNODB;

CREATE TABLE Test (
		tId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		title VARCHAR(100) NOT NULL,
		openDate VARCHAR(10) NOT NULL,
		closeDate VARCHAR(10) NOT NULL,
		timer VARCHAR(8),
		showAnswer BOOLEAN NOT NULL DEFAULT FALSE,
		done BOOLEAN DEFAULT FALSE,
		totalScore TINYINT UNSIGNED DEFAULT 0,
		grade TINYINT UNSIGNED DEFAULT 0,
		feedback VARCHAR(500),
		uId INT UNSIGNED NOT NULL,
		PRIMARY KEY(tId)
) ENGINE=INNODB;

CREATE TABLE Question (
		qId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		title VARCHAR(255) NOT NULL,
		category ENUM('SELECTION', 'CHOICE', 'REORDER', 'ANSWER') NOT NULL,
		score TINYINT UNSIGNED NOT NULL,
		tId INT UNSIGNED NOT NULL,
		PRIMARY KEY(qId),
	 	FOREIGN KEY (tId) REFERENCES Test(tId) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;

CREATE TABLE Answer (
		aId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		title VARCHAR(255) NOT NULL,
		ansStudent VARCHAR(1024),
		ansTeacher VARCHAR(1024),
		qId INT UNSIGNED NOT NULL,
		PRIMARY KEY(aId),
	 	FOREIGN KEY (qId) REFERENCES Question(qId) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;

CREATE TABLE Resource (
		rcId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		url VARCHAR(300) NOT NULL,
		qId INT UNSIGNED NOT NULL,
		PRIMARY KEY(rcId),
	 	FOREIGN KEY (qId) REFERENCES Question(qId) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;

-- CREATE TABLE Result (
-- 		rsId INT UNSIGNED NOT NULL AUTO_INCREMENT,
-- 		tId INT UNSIGNED NOT NULL,
-- 		score TINYINT UNSIGNED DEFAULT 0,
-- 		grade TINYINT UNSIGNED DEFAULT 0,
-- 		feedback VARCHAR(500),
-- 		PRIMARY KEY(rsId),
-- 	 	FOREIGN KEY (tId) REFERENCES Test(tId) ON UPDATE CASCADE ON DELETE CASCADE
-- ) ENGINE=INNODB;

# Lägga till dummy data i NewtonTestTool databas
INSERT INTO `user` VALUES (1, 'Hampus', '123', 'ham@hotmail.com', 'STUDENT');
INSERT INTO `user` VALUES (2, 'Viktor', '123', 'vikt@hotmail.com', 'STUDENT');
INSERT INTO `user` VALUES (3, 'Namir', '123', 'namir@hotmail.com', 'STUDENT');
INSERT INTO `user` VALUES (4, 'Cam', '123', 'cam@hotmail.com', 'STUDENT');
INSERT INTO `user` VALUES (5, 'Tom', '123', 'tom@hotmail.com', 'TEACHER');
INSERT INTO `user` VALUES (6, 'Michaela', '123', 'michaela@hotmail.com', 'ADMIN');

INSERT INTO `test` VALUES (1, 'Java grund', '2016-01-01', '2016-12-30', NULL, 0, 0, 0, 0, NULL, 6);
INSERT INTO `test` VALUES (2, 'Java desktop programmering', '2016-01-01', '2016-12-30', NULL, 0, 0, 0, 0, NULL, 6);
INSERT INTO `test` VALUES (3, 'Java web programmering', '2016-01-01', '2016-12-30', NULL, 0, 0, 0, 0, NULL, 6);

INSERT INTO `question` VALUES (1, 'Vad är SQL förkortning för', 'SELECTION', 0, 1);
INSERT INTO `question` VALUES (2, 'Vilken av följande är frukter', 'CHOICE', 0, 1);
INSERT INTO `question` VALUES (3, 'Vilka land har högst befolkning', 'REORDER', 0, 1);
INSERT INTO `question` VALUES (4, 'Vad är skillnad mellan static och instans variabel', 'ANSWER', 0, 1);

INSERT INTO `answer` VALUES (1, 'Single selection language', NULL, NULL, 1);
INSERT INTO `answer` VALUES (2, 'Sequal query language', NULL, NULL, 1);
INSERT INTO `answer` VALUES (3, 'Selection query language', NULL, NULL, 1);
INSERT INTO `answer` VALUES (4, 'Singular query language', NULL, NULL, 1);
INSERT INTO `answer` VALUES (5, 'Äpple', NULL, NULL, 2);
INSERT INTO `answer` VALUES (6, 'Byggnad', NULL, NULL, 2);
INSERT INTO `answer` VALUES (7, 'Banan', NULL, NULL, 2);
INSERT INTO `answer` VALUES (8, 'Panda', NULL, NULL, 2);
INSERT INTO `answer` VALUES (9, 'Sverige', NULL, NULL, 3);
INSERT INTO `answer` VALUES (10, 'Danmark', NULL, NULL, 3);
INSERT INTO `answer` VALUES (11, 'Island',  NULL, NULL, 3);
INSERT INTO `answer` VALUES (12, 'Finland', NULL, NULL, 3);
INSERT INTO `answer` VALUES (13, 'Static på class nivå och Instans per instans', NULL, NULL, 4);
