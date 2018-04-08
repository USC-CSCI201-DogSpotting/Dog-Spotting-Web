DROP DATABASE if exists DogSpotting;

CREATE DATABASE DogSpotting;

USE DogSpotting;

CREATE TABLE GeneralInfo (
	infoID int(5) primary key not null auto_increment,
    infoname varchar(15) not null,
    infonum int(10) not null
);

INSERT INTO GeneralInfo (infoname, infonum)
	VALUES	('numpost', 0),
			('numuser', 0),
            ('weekrenew', 1),
            ('monthrenew', 1),
            ('yearrenew', 1);

CREATE TABLE User (
	userID int(10) primary key not null auto_increment,
    username varchar(15) not null,
    password varchar(15) not null,
    picture varchar(50) not null
);

CREATE TABLE Follow (
	followID int(20) primary key not null auto_increment,
    followingID int(10) not null,
    followerID int(10) not null,
    FOREIGN KEY fk1(followingID) REFERENCES User(userID),
    FOREIGN KEY fk2(followerID) REFERENCES User(userID)
);

CREATE TABLE Post (
	postID int(10) primary key not null auto_increment,
    userID int(10) not null,
    image varchar(50) not null,
    description varchar(500) not null,
    timestamp timestamp not null,
    tag1 varchar(10) not null,
    tag2 varchar(10),
    tag3 varchar(10),
    tag4 varchar(10),
    tag5 varchar(10),
    weeklike int(10) not null,
    monthlike int(10) not null,
    yearlike int(10) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID)
);

CREATE TABLE Favorite (
	favoriteID int(10) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

CREATE TABLE Comment (
	commentID int(10) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10) not null,
    content varchar(100) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

CREATE TABLE WeekRank (
	rankID int(4) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

CREATE TABLE MonthRank (
	rankID int(4) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);

CREATE TABLE YearRank (
	rankID int(4) primary key not null auto_increment,
    userID int(10) not null,
    postID int(10) not null,
    FOREIGN KEY fk1(userID) REFERENCES User(userID),
    FOREIGN KEY fk2(postID) REFERENCES Post(postID)
);