DROP DATABASE if exists DogSpotting;

CREATE DATABASE DogSpotting;

USE DogSpotting;

CREATE TABLE GeneralInfo (
	infoID int(5) primary key not null auto_increment,
    infoname varchar(15) not null,
    infonum int(10) not null
);

INSERT INTO GeneralInfo (infoname, infonum)
	VALUES  ('numpost', 0),
			('numuser', 0),
            ('dailyrenew', 20000000),
            ('monthlyrenew', 20000000),
            ('yearlyrenew', 20000000);

CREATE TABLE User (
	userID int(10) primary key not null auto_increment,
    username varchar(15) not null,
    password varchar(15) not null,
    picture varchar(300) not null
);

INSERT INTO User (username, password, picture)
	VALUES  ('a', 'a', 'a'),
            ('b', 'b', 'b'),
            ('c', 'c', 'c');

CREATE TABLE Follow (
	followID int(20) primary key not null auto_increment,
    followingID int(10) not null,
    followerID int(10) not null,
    valid int(1) not null,
    FOREIGN KEY fk1(followingID) REFERENCES User(userID),
    FOREIGN KEY fk2(followerID) REFERENCES User(userID)
);

INSERT INTO Follow (followingID, followerID, valid)
	VALUES  (1, 2, 1),
            (2, 1, 1),
            (1, 3, 1);

UPDATE Follow SET valid = 0 WHERE followID = 3;

CREATE TABLE Post (
	postID int(10) primary key not null auto_increment,
    userID int(10) not null,
    image varchar(200) not null,
    description varchar(500) not null,
    timestamp timestamp not null DEFAULT CURRENT_TIMESTAMP,
    tag1 varchar(10) not null,
    tag2 varchar(10),
    tag3 varchar(10),
    tag4 varchar(10),
    tag5 varchar(10),
    dailylike int(10) not null,
    monthlylike int(10) not null,
    yearlylike int(10) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID)
);

INSERT INTO Post (userID, image, description, tag1, tag2, dailylike, monthlylike, yearlylike)
	VALUES  (1, 'https://images.pexels.com/photos/460823/pexels-photo-460823.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940', 'a', 'a', NULL, 1, 1, 1);
INSERT INTO Post (userID, image, description, tag1, tag2, dailylike, monthlylike, yearlylike)
	VALUES  (2, 'https://images.pexels.com/photos/356378/pexels-photo-356378.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940', 'a', 'a', NULL, 1, 1, 1);

CREATE TABLE Likes (
	likesID int(10) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10) not null,
    valid int(1) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

INSERT INTO Likes (userID, postID, valid) VALUES (1, 1, 1);

SELECT u.username, p.postID, p.image, p.description, p.tag1, p.tag2, p.tag3, p.tag4, p.tag5 FROM User u, Post p, Likes l WHERE p.postID = l.postID AND u.userID = p.userID AND l.userID = 1 LIMIT 100;

CREATE TABLE Comment (
	commentID int(10) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10),
    refcommentID int(10),
    content varchar(100) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID),
    FOREIGN KEY fk3(refcommentID) REFERENCES Comment(commentID)
);

INSERT INTO Comment (userID, postID, content)
	VALUES  (1, 1, '1'),
            (3, 1, '2');

INSERT INTO Comment (userID, refcommentID, content)
	VALUES  (1, 1, '11'),
            (3, 1, '12');

INSERT INTO Comment (userID, refcommentID, content)
	VALUES  (1, 3, '111'),
            (1, 3, '112');

INSERT INTO Comment (userID, refcommentID, content)
	VALUES  (1, 5, '1111'),
            (1, 4, '121');


CREATE TABLE DailyRank (
	rankID int(4) primary key not null auto_increment,
    postID int(10) not null,
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

CREATE TABLE MonthlyRank (
	rankID int(4) primary key not null auto_increment,
    postID int(10) not null,
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

CREATE TABLE YearlyRank (
	rankID int(4) primary key not null auto_increment,
    postID int(10) not null,
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

INSERT INTO DailyRank (postID)
	VALUES  (1);
    
INSERT INTO MOnthlyRank (postID)
	VALUES  (2);

INSERT INTO YearlyRank (postID)
	VALUES  (1);