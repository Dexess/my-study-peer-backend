DROP USER IF EXISTS 'mystudypeer'@'%';
DROP DATABASE IF EXISTS mystudypeer;

CREATE USER 'mystudypeer' @'%' IDENTIFIED BY '123';
CREATE DATABASE mystudypeer;

USE mystudypeer;

CREATE TABLE universityProgram(
                                  programId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                                  universityName VARCHAR(64) NOT NULL,
                                  programName VARCHAR(64) NOT NULL,
                                  PRIMARY KEY(programId)
);

-- We assume user cannot change email once registered.
CREATE TABLE users(
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
                      PRIMARY KEY(email),
                      FOREIGN KEY(programId) REFERENCES universityProgram(programId),
                      CONSTRAINT range_class CHECK (class BETWEEN 1 AND 4),
                      CONSTRAINT unique_id UNIQUE (userId),
                      CONSTRAINT unique_telno UNIQUE (telno)
);


CREATE TABLE Post (
                      postId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                      title VARCHAR(32) NOT NULL,
                      course VARCHAR(32) NOT NULL,
                      creationDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                      description VARCHAR(500),
                      postEnabled BOOLEAN NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      authorName VARCHAR(32) NOT NULL,
                      authorSurname VARCHAR(32) NOT NULL,
                      authorClass TINYINT NOT NULL DEFAULT 1,
                      universityId INT UNSIGNED,
                      PRIMARY KEY (postId),
                      FOREIGN KEY (email) REFERENCES users(email),
                      FOREIGN KEY (universityId) REFERENCES universityProgram(programId),
                      INDEX (postEnabled,creationDate),
                      INDEX (postEnabled,title,creationDate)
);



CREATE TABLE postTag(
                        postId INT UNSIGNED NOT NULL,
                        tag VARCHAR(8) NOT NULL,
                        PRIMARY KEY (postId, tag),
                        FOREIGN KEY (postId) REFERENCES Post(postId),
                        INDEX (tag)
);

CREATE TABLE comment(
                        commentDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                        commentId INT UNSIGNED AUTO_INCREMENT NOT NULL,
                        commentText VARCHAR(64) NOT NULL,
                        commentorName VARCHAR(32) NOT NULL,
                        commentorSurname VARCHAR(32) NOT NULL,
                        commentorEmail VARCHAR (255) NOT NULL,
                        postId INT UNSIGNED NOT NULL,
                        PRIMARY KEY (commentId),
                        FOREIGN KEY (commentorEmail) REFERENCES users(email),
                        FOREIGN KEY (postId) REFERENCES Post(postId)
);

CREATE TABLE request(
                        applierEmail VARCHAR (255) NOT NULL,
                        postId INT UNSIGNED NOT NULL,
                        status ENUM('rejected', 'ongoing', 'accepted') NOT NULL,
                        requestDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                        PRIMARY KEY (applierEmail, postId),
                        FOREIGN KEY (postId) references Post(postId),
                        FOREIGN KEY (applierEmail) references users(email)
);

CREATE TABLE feedback(
                         feedbackTitle varchar(32) NOT NULL,
                         feedbackText varchar(120) NOT NULL,
                         feedbackPoints int NOT NULL,
                         feedbackDate DATE NOT NULL DEFAULT (CURRENT_DATE),
                         givenTo VARCHAR(255) NOT NULL,
                         givenBy VARCHAR(255) NOT NULL,
                         forPost INT UNSIGNED NOT NULL,
                         PRIMARY KEY (givenTo, givenBy),
                         FOREIGN KEY (givenBy) REFERENCES users(email),
                         FOREIGN KEY (givenTo) REFERENCES users(email),
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
INSERT INTO universityProgram (universityName, programName) VALUES ('METU NCC','Computer Engineering');
INSERT INTO universityProgram (universityName, programName) VALUES ('METU NCC','Electrical-Electronic Engineering');
INSERT INTO universityProgram (universityName, programName) VALUES ('METU NCC','Mechanics Engineering');
INSERT INTO universityProgram (universityName, programName) VALUES ('METU NCC','Civil Engineering');
INSERT INTO universityProgram (universityName, programName) VALUES ('METU NCC','Aerospace Engineering');

-- Creating Users
INSERT INTO users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('necdet.efe@metu.edu.tr', '', 'Necdet',CURDATE(), SHA2('iloveyelizhoca', 512), 'EFE', 'Antalya', 3, '533 845 83 23', 1);
INSERT INTO users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('aygun.mustafa@metu.edu.tr', '', 'Mustafa',CURDATE(), SHA2('pass123',512), 'Aygün', 'Ankara', 3, '538 975 45 35', 1);
INSERT INTO users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('john.doe@metu.edu.tr', '', 'John',CURDATE(), SHA2('ilovemoney', 512), 'Doe', 'Washington', 3, '500 500 00 00', 1);
INSERT INTO users (email, token, name, registerDate, password, surname, city, class, telno, programId) VALUES ('keanu.reeves@metu.edu.tr', '', 'Keanu',CURDATE(), SHA2('ilovehollywood', 512), 'Reeves', 'Hollywood', 4, '500 000 00 00', 2);

-- Creating Posts
INSERT INTO
    Post (title, course, description, postEnabled, email, authorName, authorSurname, authorClass, universityId)
SELECT
    'Calisma arkadası arıyorum', 'CNG350', '',  1,  email, name, surname, class, programId
FROM
    users
WHERE email = 'necdet.efe@metu.edu.tr';

INSERT INTO
    Post (title, course, description, postEnabled, email, authorName, authorSurname, authorClass, universityId)
SELECT
    'Bu 2. post', 'CNG355', '',  1,  email, name, surname, class, programId
FROM
    users
WHERE email = 'necdet.efe@metu.edu.tr';

INSERT INTO
    Post (title, course, creationDate, description, postEnabled, email, authorName, authorSurname, authorClass, universityId)
SELECT
    'CNG 350 Working Group 2', 'CNG350','2021-02-20','Looking for group mates', 1, email, name, surname, class, programId
FROM
    users
WHERE
        email = 'aygun.mustafa@metu.edu.tr';

-- Inserting Feedbacks

INSERT INTO feedback (feedbackTitle, feedbackText, feedbackPoints, givenTo,givenBy, forPost) VALUES ('CNG350','good', 5, 'necdet.efe@metu.edu.tr', 'aygun.mustafa@metu.edu.tr', 1);

INSERT INTO feedback (feedbackTitle, feedbackText, feedbackPoints, givenTo,givenBy, forPost) VALUES ('CNG350','good',4,'necdet.efe@metu.edu.tr','john.doe@metu.edu.tr', 1);

-- Inserting requets
INSERT INTO request (applierEmail,postId,status) VALUES ('aygun.mustafa@metu.edu.tr', 1, 'ongoing');
INSERT INTO request (applierEmail,postId,status) VALUES ('john.doe@metu.edu.tr', 1, 'accepted');
INSERT INTO request (applierEmail,postId,status) VALUES ('john.doe@metu.edu.tr', 2, 'ongoing');
INSERT INTO request (applierEmail,postId,status) VALUES ('keanu.reeves@metu.edu.tr', 1, 'accepted');
INSERT INTO request (applierEmail,postId,status) VALUES ('keanu.reeves@metu.edu.tr', 3, 'ongoing');

-- Inserting comments
INSERT INTO
    comment (commentText, commentorName, commentorSurname, commentorEmail, postId)
SELECT
    'We are 2 now.', name, surname, email, 1
FROM
    users
WHERE email = 'john.doe@metu.edu.tr';

INSERT INTO
    comment (commentText, commentorName, commentorSurname, commentorEmail, postId)
SELECT
    'We are looking for 3.', name, surname, email, 1
FROM
    users
WHERE
        email = 'necdet.efe@metu.edu.tr';

INSERT INTO
    comment (commentText, commentorName, commentorSurname, commentorEmail, postId)
SELECT
    'We are 3 now.', name, surname,email, 1
FROM
    users
WHERE
        email = 'keanu.reeves@metu.edu.tr';

-- Inserting tags
INSERT INTO postTag VALUES(1, 'METU');
INSERT INTO postTag VALUES(1, 'CNG 351');
INSERT INTO postTag VALUES(2, 'CNG');
INSERT INTO postTag VALUES(2, '2');

-- Inserting news
INSERT INTO News (newsId, title, description) VALUES(1,'Feedback verme sistemi yenilendi!','Şimdi user feedback sistemini sorunsuzca görüntüleyebilir ve feedbacklerinizi verebilirsiniz!');
INSERT INTO News VALUES(2,'Comment bugu fixlendi!','Comment atarken oluşan bug fixlendi. Commentlerinizi sorunsuzca atmaya devam edebilirsiniz!','2020-10-10');


GRANT ALL PRIVILEGES ON mystudypeer.* TO 'mystudypeer' @'%';

