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
    ("WMB-99506", "Drill", "Einhell", 2500, 10000, 2.1, 25.3, "pr1_1.jpg", "pr1_2.jpg"),
    ("DLA-50744", "Angle Grinder", "DWT", 3200, 12000, 3.3, 45.8, "pr2_1.jpg", "pr2_2.jpg"),
    ("XVF-15874", "Perforator", "Craftec", 4000, 9700, 2.2, 77.2, "pr3_1.jpg", "pr3_2.jpg"),
    ("TBV-99606", "Battery Screwdriver", "Storm", 3500, 85000, 4.5, 18.3, "pr4_1.jpg", "pr4_2.jpg"),
    ("DYO-31019", "Electric screwdriver", "Vorskla", 2900, 10000, 2.4, 22.4, "pr5_1.jpg", "pr5_2.jpg"),
    ("WSJ-60890", "Generator", "Forte", 2500, 15000, 2.7, 20.2, "pr6_1.jpg", "pr6_2.jpg"),
    ("KXF-94097", "Building dryer", "Makita", 3200, 11000, 2.9, 31.3, "pr7_1.jpg", "pr7_2.jpg"),
    ("FJK-57229", "Milling machine", "Phiolent", 2600, 14000, 4.0, 29.6, "pr8_1.jpg", "pr8_2.jpg"),
    ("TJL-59445", "Electric saw", "Bosch", 4000, 8500, 3.8, 35.3, "pr9_1.jpg", "pr9_2.jpg"),
    ("ZJM-64244", "Feed chopper", "VEGIS", 2500, 1000, 4.2, 44.3, "pr10_1.jpg", "pr10_2.jpg"),
    ("BKB-68153", "Sharpen machine", "Ритм", 3200, 9700, 2.1, 47.5, "pr11_1.jpg", "pr11_2.jpg"),
    ("CDI-16073", "Welding machine", "Edon", 3500, 8000, 2.5, 23.6, "pr12_1.jpg", "pr12_2.jpg"),
    ("IEK-57231", "Compressor", "Miol", 2900, 12000, 2.3, 33.2, "pr13_1.jpg", "pr13_2.jpg"),
    ("OJN-29762", "Car compressor", "URAGAN", 2500, 10000, 3.0, 39.3, "pr14_1.jpg", "pr14_2.jpg"),
    ("HVQ-50558", "Electric mower", "Expert", 4000, 8000, 2.2, 37.8, "pr15_1.jpg", "pr15_2.jpg"),
    ("AEY-08705", "Electric fretsaw", "Bravo", 2600, 9700, 3.0, 25.3, "pr16_1.jpg", "pr16_2.jpg"),
    ("ABQ-85934", "Electric stapler", "Titan", 2900, 10000, 2.7, 25.2, "pr17_1.jpg", "pr17_2.jpg"),
    ("YTG-85933", "Electric airbrush", "Stern", 3500, 8000, 4.2, 36.1, "pr18_1.jpg", "pr18_2.jpg"),
    ("FAJ-56317", "Vibrating grinder", "Дніпро-М", 4000, 15000, 2.0, 48.0, "pr19_1.jpg", "pr19_2.jpg"),
    ("XSZ-12517", "Wall chaser", "Hitachi", 3200, 8500, 4.9, 25.9, "pr20_1.jpg", "pr20_2.jpg");