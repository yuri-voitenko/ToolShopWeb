DROP DATABASE IF EXISTS toolshopweb;
CREATE DATABASE toolshopweb DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE toolshopweb;

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  fullName varchar(255) NOT NULL,
  phoneNumber varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  avatar varchar(255) DEFAULT NULL,
  PRIMARY KEY (id), UNIQUE (id), UNIQUE (email)
);

CREATE TABLE tools (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  category varchar(255) NOT NULL,
  manufacturer varchar(255) NOT NULL,
  power int NOT NULL,
  maxRotationSpeed int NOT NULL,
  weight DECIMAL(5, 2) NOT NULL,
  cost DECIMAL(5, 2) NOT NULL,
  mainImage varchar(255) DEFAULT NULL,
  additionalImage varchar(255) DEFAULT NULL,
  PRIMARY KEY (id), UNIQUE (id), UNIQUE (manufacturer), UNIQUE (category)
);

INSERT INTO users
    (email, password, fullName, phoneNumber, address, avatar)
VALUES
    ("voit@gmail.com", MD5("Voitenko!335"), "Yuri Voitenko", "+380505730182", "Kharkiv, Puskinska st., 79", "avatar_01.png"),
    ("vlad@gmail.com", MD5("Bullsh1t007$"), "Vlad Bykov", "+38050-789-0182", "Lughansk, Kirova st., 12", "avatar_02.jpg" ),
	("pupok@yandex.ru", MD5("BigBoss999*"), "Vasya Pupkin", "+38050-789-0182", "Donetsk, Lenina st., 2", NULL),
	("small_johnny@ukr.net", MD5("AmericanBoy#1970"), "John Lo", "780955730292", "Kiev, Belyaeva st., 45", "avatar_04.jpg"),
	("rusich@mail.ru", MD5("NotRaci%st789"), "Niha Petrov", "+380663344666", "Poltava, Puskinska st., 1/A", "avatar_05.jpg");

INSERT INTO tools
    (name, category, manufacturer, power, maxRotationSpeed, weight, cost, mainImage, additionalImage)
VALUES
    ("tovar1", "Drill", "Bosch", 2500, 10000, 2.1, 25.3, "image1.jpg", "image2.jpg"),
    ("tovar2", "Angle Grinder", "Craft", 3200, 12000, 3.3, 45.8, "image1.jpg", "image2.jpg"),
    ("tovar3", "Perforator", "Einhell", 4000, 97000, 2.2, 77.2, "image1.jpg", "image2.jpg"),
    ("tovar4", "Screwdriver", "Forte", 3500, 85000, 4.5, 18.3, "image1.jpg", "image2.jpg"),
    ("tovar5", "Electric screwdriver", "Hyundai", 2900, 10000, 2.4, 22.4, "image1.jpg", "image2.jpg"),
    ("tovar6", "Generator", "Makita", 2500, 15000, 2.7, 20.2, "image1.jpg", "image2.jpg"),
    ("tovar7", "Building dryer", "DWT", 3200, 11000, 2.9, 31.3, "image1.jpg", "image2.jpg"),
    ("tovar8", "Milling machine", "Stern", 2600, 14000, 4.0, 29.6, "image1.jpg", "image2.jpg"),
    ("tovar9", "Electric saw", "Vorskla", 4000, 85000, 3.8, 35.3, "image1.jpg", "image2.jpg"),
    ("tovar10", "Feed chopper", "Росмаш", 2500, 10000, 4.2, 44.3, "image1.jpg", "image2.jpg"),
    ("tovar11", "Sharpen machine", "Titan", 3200, 97000, 2.1, 47.5, "image1.jpg", "image2.jpg"),
    ("tovar12", "Welding machine", "Зенит", 3500, 80000, 2.5, 23.6, "image1.jpg", "image2.jpg"),
    ("tovar13", "Compressor", "Miol", 2900, 12000, 2.3, 33.2, "image1.jpg", "image2.jpg"),
    ("tovar14", "Car compressor", "ХарТех", 2500, 10000, 3.0, 39.3, "image1.jpg", "image2.jpg"),
    ("tovar15", "Electric mower", "Кедр", 4000, 80000, 2.2, 37.8, "image1.jpg", "image2.jpg"),
    ("tovar16", "Electric fretsaw", "Интерскол", 2600, 97000, 3.0, 25.3, "image1.jpg", "image2.jpg"),
    ("tovar17", "Electric stapler", "Искра", 2900, 10000, 2.7, 25.2, "image1.jpg", "image2.jpg"),
    ("tovar18", "Electric airbrush", "Дніпро-М", 3500, 80000, 4.2, 36.1, "image1.jpg", "image2.jpg"),
    ("tovar19", "Vibrating grinder", "Фиолент", 4000, 15000, 2.0, 48.0, "image1.jpg", "image2.jpg"),
    ("tovar20", "Wall chaser", "Энергомаш", 3200, 85000, 4.9, 25.9, "image1.jpg", "image2.jpg");