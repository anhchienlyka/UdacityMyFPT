CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  userName VARCHAR(20) UNIQUE,
  salt VARCHAR,
  password VARCHAR,
  firstName VARCHAR(20),
  lastName VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteId INT PRIMARY KEY auto_increment,
    noteTitle VARCHAR(20),
    noteDescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    fileName VARCHAR,
    contentType VARCHAR,
    fileSize VARCHAR,
    userid INT,
    fileData BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialId INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    userName VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userId INT,
    foreign key (userid) references USERS(userid)
);
