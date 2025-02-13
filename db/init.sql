DROP USER IF EXISTS 'mystudypeer'@'%';
DROP DATABASE IF EXISTS mystudypeer;

CREATE USER 'mystudypeer' @'%' IDENTIFIED BY '123';
CREATE DATABASE mystudypeer;

USE mystudypeer;

CREATE TABLE UniversityProgram(
                                  programId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                                  universityName VARCHAR(64) NOT NULL,
                                  programName VARCHAR(64) NOT NULL,
                                  PRIMARY KEY(programId)
);

-- We assume user cannot change email once registered.
CREATE TABLE Users(
                      userId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      token VARCHAR(255) NOT NULL,
                      name VARCHAR(32) NOT NULL,
                      registerDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                      password VARCHAR(255) NOT NULL,
                      surname VARCHAR(32) NOT NULL,
                      city VARCHAR(32) NOT NULL,
                      class TINYINT NOT NULL DEFAULT 1,
                      telno VARCHAR(16),
                      programId INT UNSIGNED,
                      PRIMARY KEY(userId),
                      FOREIGN KEY(programId) REFERENCES UniversityProgram(programId),
                      CONSTRAINT range_class CHECK (class BETWEEN 1 AND 4),
                      CONSTRAINT unique_email UNIQUE (email),
                      CONSTRAINT unique_telno UNIQUE (telno)
);


CREATE TABLE Post (
                      postId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                      title VARCHAR(32) NOT NULL,
                      course VARCHAR(32) NOT NULL,
                      creationDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                      description VARCHAR(500),
                      postEnabled BOOLEAN NOT NULL,
                      userId INT UNSIGNED,
                      authorName VARCHAR(32) NOT NULL,
                      authorSurname VARCHAR(32) NOT NULL,
                      authorClass TINYINT NOT NULL DEFAULT 1,
                      universityId INT UNSIGNED,
                      PRIMARY KEY (postId),
                      FOREIGN KEY (userId) REFERENCES Users(userId),
                      FOREIGN KEY (universityId) REFERENCES UniversityProgram(programId),
                      INDEX (postEnabled,creationDate),
                      INDEX (postEnabled,title,creationDate)
);



CREATE TABLE PostTag(
                        postId INT UNSIGNED NOT NULL,
                        tag VARCHAR(8) NOT NULL,
                        PRIMARY KEY (postId, tag),
                        FOREIGN KEY (postId) REFERENCES Post(postId),
                        INDEX (tag)
);

CREATE TABLE Comment(
                        commentDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                        commentId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                        commentText VARCHAR(64) NOT NULL,
                        commenterName VARCHAR(32) NOT NULL,
                        commenterSurname VARCHAR(32) NOT NULL,
                        commenterUserId INT UNSIGNED,
                        postId INT UNSIGNED NOT NULL,
                        PRIMARY KEY (commentId),
                        FOREIGN KEY (commenterUserId) REFERENCES Users(userId),
                        FOREIGN KEY (postId) REFERENCES Post(postId)
);

CREATE TABLE Request(
                        applierUserId INT UNSIGNED,
                        postId INT UNSIGNED NOT NULL,
                        status ENUM('rejected', 'ongoing', 'accepted') NOT NULL,
                        requestDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                        PRIMARY KEY (applierUserId, postId),
                        FOREIGN KEY (postId) references Post(postId),
                        FOREIGN KEY (applierUserId) references Users(userId)
);

CREATE TABLE Feedback(
                         feedbackTitle varchar(32) NOT NULL,
                         feedbackText varchar(120) NOT NULL,
                         feedbackPoints int NOT NULL,
                         feedbackDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                         givenTo INT UNSIGNED,
                         givenBy INT UNSIGNED,
                         forPost INT UNSIGNED NOT NULL,
                         PRIMARY KEY (givenTo, givenBy, forPost),
                         FOREIGN KEY (givenBy) REFERENCES Users(userId),
                         FOREIGN KEY (givenTo) REFERENCES Users(userId),
                         FOREIGN KEY (forPost) REFERENCES Post(postId)
);

CREATE TABLE News(
                     newsId int UNSIGNED AUTO_INCREMENT NOT NULL,
                     title varchar(64) NOT NULL,
                     description varchar(500),
                     creationDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                     PRIMARY KEY(newsId)
);

-- Adding University and University Programs
INSERT INTO UniversityProgram (universityName, programName) VALUES ('Other','Other');
INSERT INTO UniversityProgram (universityName, programName) VALUES ('METU NCC','Computer Engineering');
INSERT INTO UniversityProgram (universityName, programName) VALUES ('METU NCC','Electrical-Electronic Engineering');
INSERT INTO UniversityProgram (universityName, programName) VALUES ('METU NCC','Mechanics Engineering');
INSERT INTO UniversityProgram (universityName, programName) VALUES ('METU NCC','Civil Engineering');
INSERT INTO UniversityProgram (universityName, programName) VALUES ('METU NCC','Aerospace Engineering');

-- Creating Users
INSERT INTO Users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('necdet.efe@metu.edu.tr', '', 'Necdet',CURDATE(), SHA2('iloveyelizhoca', 512), 'EFE', 'Antalya', 3, '533 845 83 23', 2);
INSERT INTO Users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('aygun.mustafa@metu.edu.tr', '', 'Mustafa',CURDATE(), SHA2('pass123',512), 'Aygün', 'Ankara', 3, '538 975 45 35', 2);
INSERT INTO Users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('john.doe@metu.edu.tr', '', 'John',CURDATE(), SHA2('ilovemoney', 512), 'Doe', 'Washington', 3, '500 500 00 00', 2);
INSERT INTO Users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('keanu.reeves@metu.edu.tr', '', 'Keanu',CURDATE(), SHA2('ilovehollywood', 512), 'Reeves', 'Hollywood', 4, '500 000 00 00', 3);

-- Creating Posts
INSERT INTO
    Post (title, course, description, postEnabled, userId, authorName, authorSurname, authorClass, universityId)
SELECT
    'Calisma arkadası arıyorum', 'CNG350', '',  1,  userId, name, surname, class, programId
FROM
    Users
WHERE email = 'necdet.efe@metu.edu.tr';

INSERT INTO
    Post (title, course, description, postEnabled, userId, authorName, authorSurname, authorClass, universityId)
SELECT
    'Bu 2. post', 'CNG355', '',  1,  userId, name, surname, class, programId
FROM
    Users
WHERE email = 'necdet.efe@metu.edu.tr';

INSERT INTO
    Post (title, course, creationDate, description, postEnabled, userId, authorName, authorSurname, authorClass, universityId)
SELECT
    'CNG 350 Working Group 2', 'CNG350','2021-02-20','Looking for group mates', 1, userId, name, surname, class, programId
FROM
    Users
WHERE
        email = 'aygun.mustafa@metu.edu.tr';

-- Inserting Feedbacks

INSERT INTO Feedback (feedbackTitle, feedbackText, feedbackPoints, givenTo,givenBy, forPost) VALUES ('CNG350','good', 5, 1, 2, 1);

INSERT INTO Feedback (feedbackTitle, feedbackText, feedbackPoints, givenTo,givenBy, forPost) VALUES ('CNG350','good',4,1,3, 1);

-- Inserting requets
INSERT INTO Request (applierUserId,postId,status) VALUES (2, 1, 'ongoing');
INSERT INTO Request (applierUserId,postId,status) VALUES (3, 1, 'accepted');
INSERT INTO Request (applierUserId,postId,status) VALUES (4, 2, 'ongoing');
INSERT INTO Request (applierUserId,postId,status) VALUES (4, 1, 'accepted');
INSERT INTO Request (applierUserId,postId,status) VALUES (4, 3, 'ongoing');

-- Inserting comments
INSERT INTO
    Comment (commentText, commenterName, commenterSurname, commenterUserId, postId)
SELECT
    'We are 2 now.', name, surname, userId, 1
FROM
    Users
WHERE email = 'john.doe@metu.edu.tr';

INSERT INTO
    Comment (commentText, commenterName, commenterSurname, commenterUserId, postId)
SELECT
    'We are looking for 3.', name, surname, userId, 1
FROM
    Users
WHERE
        email = 'necdet.efe@metu.edu.tr';

INSERT INTO
    Comment (commentText, commenterName, commenterSurname, commenterUserId, postId)
SELECT
    'We are 3 now.', name, surname, userId, 1
FROM
    Users
WHERE
        email = 'keanu.reeves@metu.edu.tr';

-- Inserting tags
INSERT INTO PostTag VALUES(1, 'METU');
INSERT INTO PostTag VALUES(1, 'CNG 351');
INSERT INTO PostTag VALUES(2, 'CNG');
INSERT INTO PostTag VALUES(2, '2');

-- Inserting news
INSERT INTO News (newsId, title, description) VALUES(1,'Feedback verme sistemi yenilendi!','Şimdi user feedback sistemini sorunsuzca görüntüleyebilir ve feedbacklerinizi verebilirsiniz!');
INSERT INTO News VALUES(2,'Comment bugu fixlendi!','Comment atarken oluşan bug fixlendi. Commentlerinizi sorunsuzca atmaya devam edebilirsiniz!','2020-10-10');


GRANT ALL PRIVILEGES ON mystudypeer.* TO 'mystudypeer' @'%';

