DROP DATABASE IF EXISTS toolshopweb;
CREATE DATABASE toolshopweb;
USE toolshopweb;

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  fullName varchar(255) NOT NULL,
  phoneNumber varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  PRIMARY KEY (id), UNIQUE (id), UNIQUE (email)
);

INSERT INTO users
    (email, password, fullName, phoneNumber, address)
VALUES
    ("voit@gmail.com", MD5("Voitenko!335"), "Yuri Voitenko", "+380505730182", "Kharkiv, Puskinska st., 79"),
    ("vlad@gmail.com", MD5("Bullsh1t007$"), "Vlad Bykov", "+38050-789-0182", "Lughansk, Kirova st., 12"),
	("pupok@yandex.ru", MD5("BigBoss999*"), "Vasya Pupkin", "+38050-789-0182", "Donetsk, Lenina st., 2"),
	("small_johnny@ukr.net", MD5("AmericanBoy#1970"), "John Lo", "780955730292", "Kiev, Belyaeva st., 45"),
	("rusich@mail.ru", MD5("NotRaci%st789"), "Niha Petrov", "+380663344666", "Poltava, Puskinska st., 1/A");