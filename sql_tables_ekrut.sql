-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `Branch` varchar(50) NOT NULL,
  `Region` varchar(50) NOT NULL,
  PRIMARY KEY (`Branch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('Ashkelon','South'),('Dubai','UAE'),('Eilat','South'),('Haifa','North'),('Nahariya','North'),('Sharjah','UAE');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `ID` varchar(50) NOT NULL,
  `Customer_Number` int(11) NOT NULL AUTO_INCREMENT,
  `Credit_Card_Number` varchar(50) DEFAULT NULL,
  `Customers_Status` enum('Active','Frozen','Blocked') DEFAULT 'Active',
  `Is_Subscriber` tinyint(1) DEFAULT '0',
  `Branch` enum('Ashkelon','Dubai','Eilat','Haifa','Nahariya','Sharjah','Online') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Customer_Number_UNIQUE` (`Customer_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES ('223837198',3,'5555-6666-9993','Active',1,'Online'),('227848569',10,'5555-6666-9997','Active',0,'Online'),('285529100',5,'5555-6666-9995','Active',1,'Ashkelon'),('298325273',4,'5555-6666-9994','Active',0,'Dubai'),('303146766',9,'5555-6666-9999','Frozen',0,'Online'),('304842442',2,'5555-6666-9992','Active',1,'Eilat'),('314761065',12,'5555-6666-9997','Active',0,'Haifa'),('322379646',8,'5555-6666-9998','Blocked',0,'Online'),('327345568',7,'5555-6666-9999','Active',0,'Nahariya'),('343092928',1,'5555-6666-9991','Active',0,'Online'),('354053895',6,'5555-6666-9996','Active',0,'Sharjah'),('378522836',11,'5555-6666-9998','Active',0,'Online');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries`
--

DROP TABLE IF EXISTS `deliveries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries` (
  `Order_Number` int(11) NOT NULL AUTO_INCREMENT,
  `Address` varchar(45) NOT NULL,
  `Receiver_Name` varchar(45) NOT NULL,
  `Receiver_Email` varchar(45) NOT NULL,
  `Receiver_Phone_Number` varchar(45) NOT NULL,
  `Confirmation_Date` date DEFAULT NULL,
  `Delivery_Date` date DEFAULT NULL,
  `Status` enum('Pending','Active','Delivered','Done') DEFAULT 'Active',
  PRIMARY KEY (`Order_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries`
--

LOCK TABLES `deliveries` WRITE;
/*!40000 ALTER TABLE `deliveries` DISABLE KEYS */;
INSERT INTO `deliveries` VALUES (7,'UncleNextDoor,12,1','Rick','fopone3928@dmonies.com','050476235','2023-01-05','2023-01-09','Active'),(11,'fdgdfgdfg,,','Joe','woreyo7857@edinel.com','054011054','2023-01-05','2023-01-09','Active'),(25,'adadadaa,,','Geff','noleviw255@dmonies.com','053055220','2023-01-08','2023-01-10','Done'),(56,'sdfsdfsdfsdf,,','Joe','woreyo7857@edinel.com','054011054','2023-01-11','2023-01-11','Active'),(86,'hifaaaa,,','Joe','woreyo7857@edinel.com','054011054','2023-01-16','2023-01-19','Done');
/*!40000 ALTER TABLE `deliveries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ekrutstaff`
--

DROP TABLE IF EXISTS `ekrutstaff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ekrutstaff` (
  `ID` int(11) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Region` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `username_store_employee` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ekrutstaff`
--

LOCK TABLES `ekrutstaff` WRITE;
/*!40000 ALTER TABLE `ekrutstaff` DISABLE KEYS */;
INSERT INTO `ekrutstaff` VALUES (202323947,'DeliverySouth','South'),(214637336,'Ceo',NULL),(225855373,'MWorkerNorth','North'),(227128543,'OpWorker3',''),(238124725,'MManager',NULL),(250777580,'MWorkerUAE','UAE'),(265758007,'DeliveryUAE','UAE'),(270776581,'RManagerUAE','UAE'),(282053881,'CServiceEm',NULL),(312329680,'OpWorker2',''),(321148356,'MWorkerSouth','South'),(325478999,'OpWorker4',NULL),(336697030,'RManagerSouth','South'),(362062621,'RManagerNorth','North'),(362774561,'OpWorker1',''),(369123929,'DeliveryNorth','North');
/*!40000 ALTER TABLE `ekrutstaff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `Branch` varchar(100) NOT NULL,
  `Product_ID` varchar(50) NOT NULL,
  `Product_Name` varchar(45) DEFAULT NULL,
  `Product_Quantity` int(11) DEFAULT NULL,
  `Threshold_Point` int(11) DEFAULT NULL,
  `Active_Sale` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`Branch`,`Product_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES ('Ashkelon','1','Bamba',18,NULL,0),('Ashkelon','10','Doritos nachos',18,NULL,0),('Ashkelon','11','Doritos Cool Ranch',18,NULL,0),('Ashkelon','12','Mars',15,NULL,0),('Ashkelon','13','Snickers',17,NULL,0),('Ashkelon','14','Cheese flavored Popcorns',19,NULL,0),('Ashkelon','15','Coca Cola Zero can',18,NULL,0),('Ashkelon','16','Coca Cola can',19,NULL,0),('Ashkelon','17','Neviot grape flavor',19,NULL,0),('Ashkelon','18','Coca Cola Zero bottle',19,NULL,0),('Ashkelon','19','Coca Cola bottle',19,NULL,0),('Ashkelon','2','Apropo Bisley onion flavor',19,NULL,0),('Ashkelon','20','Coke Zero Watermelon',19,NULL,0),('Ashkelon','21','Cheetos XO Pizza Circle',19,NULL,0),('Ashkelon','22','Cheetos cheese',19,NULL,0),('Ashkelon','23','Kinder Bueno',19,NULL,0),('Ashkelon','24','Click Biscuit',19,NULL,0),('Ashkelon','25','Cranchos Onion',19,NULL,0),('Ashkelon','26','Crunchos barbecue',19,NULL,0),('Ashkelon','27','Crunchos Pizza',19,NULL,0),('Ashkelon','28','Schweppes Bitter Lemon',19,NULL,0),('Ashkelon','29','Schweppes Lemon soda',19,NULL,0),('Ashkelon','3','Apropo corn snack',19,NULL,0),('Ashkelon','30','Tapochips onion cream',19,NULL,0),('Ashkelon','31','Tapochips pops',19,NULL,0),('Ashkelon','32','Tapochips Mexican Crunch',19,NULL,0),('Ashkelon','4','Beasley BBQ',19,NULL,0),('Ashkelon','5','Beasley Grill',19,NULL,0),('Ashkelon','6','Beasley Pizza',19,NULL,0),('Ashkelon','7','Beasley Cheesy Sticks',19,NULL,0),('Ashkelon','8','Neviot apple flavor',19,NULL,0),('Ashkelon','9','Bamba filled with brownies',19,NULL,0),('Dubai','1','Bamba',18,6,0),('Dubai','10','Doritos nachos',18,6,0),('Dubai','11','Doritos Cool Ranch',19,6,0),('Dubai','12','Mars',19,6,0),('Dubai','13','Snickers',19,6,0),('Dubai','14','Cheese flavored Popcorns',19,6,0),('Dubai','15','Coca Cola Zero can',19,6,0),('Dubai','16','Coca Cola can',19,6,0),('Dubai','17','Neviot grape flavor',19,6,0),('Dubai','18','Coca Cola Zero bottle',19,6,0),('Dubai','19','Coca Cola bottle',19,6,0),('Dubai','2','Apropo Bisley onion flavor',19,6,0),('Dubai','20','Coke Zero Watermelon',19,6,0),('Dubai','21','Cheetos XO Pizza Circle',19,6,0),('Dubai','22','Cheetos cheese',19,6,0),('Dubai','23','Kinder Bueno',19,6,0),('Dubai','24','Click Biscuit',19,6,0),('Dubai','25','Cranchos Onion',19,6,0),('Dubai','26','Crunchos barbecue',19,6,0),('Dubai','27','Crunchos Pizza',19,6,0),('Dubai','28','Schweppes Bitter Lemon',19,6,0),('Dubai','29','Schweppes Lemon soda',19,6,0),('Dubai','3','Apropo corn snack',19,6,0),('Dubai','30','Tapochips onion cream',19,6,0),('Dubai','31','Tapochips pops',19,6,0),('Dubai','32','Tapochips Mexican Crunch',19,6,0),('Dubai','4','Beasley BBQ',19,6,0),('Dubai','5','Beasley Grill',19,6,0),('Dubai','6','Beasley Pizza',19,6,0),('Dubai','7','Beasley Cheesy Sticks',19,6,0),('Dubai','8','Neviot apple flavor',19,6,0),('Dubai','9','Bamba filled with brownies',19,6,0),('Eilat','1','Bamba',9,NULL,0),('Eilat','10','Doritos nachos',10,NULL,0),('Eilat','11','Doritos Cool Ranch',11,NULL,0),('Eilat','12','Mars',17,NULL,0),('Eilat','13','Snickers',19,NULL,0),('Eilat','14','Cheese flavored Popcorns',17,NULL,0),('Eilat','15','Coca Cola Zero can',17,NULL,0),('Eilat','16','Coca Cola can',19,NULL,0),('Eilat','17','Neviot grape flavor',18,NULL,0),('Eilat','18','Coca Cola Zero bottle',19,NULL,0),('Eilat','19','Coca Cola bottle',19,NULL,0),('Eilat','2','Apropo Bisley onion flavor',18,NULL,0),('Eilat','20','Coke Zero Watermelon',19,NULL,0),('Eilat','21','Cheetos XO Pizza Circle',19,NULL,0),('Eilat','22','Cheetos cheese',19,NULL,0),('Eilat','23','Kinder Bueno',19,NULL,0),('Eilat','24','Click Biscuit',19,NULL,0),('Eilat','25','Cranchos Onion',19,NULL,0),('Eilat','26','Crunchos barbecue',19,NULL,0),('Eilat','27','Crunchos Pizza',19,NULL,0),('Eilat','28','Schweppes Bitter Lemon',19,NULL,0),('Eilat','29','Schweppes Lemon soda',19,NULL,0),('Eilat','3','Apropo corn snack',19,NULL,0),('Eilat','30','Tapochips onion cream',19,NULL,0),('Eilat','31','Tapochips pops',19,NULL,0),('Eilat','32','Tapochips Mexican Crunch',19,NULL,0),('Eilat','4','Beasley BBQ',19,NULL,0),('Eilat','5','Beasley Grill',19,NULL,0),('Eilat','6','Beasley Pizza',19,NULL,0),('Eilat','7','Beasley Cheesy Sticks',19,NULL,0),('Eilat','8','Neviot apple flavor',19,NULL,0),('Eilat','9','Bamba filled with brownies',19,NULL,0),('Haifa','1','Bamba',6,40,1),('Haifa','10','Doritos nachos',16,40,1),('Haifa','11','Doritos Cool Ranch',17,40,1),('Haifa','12','Mars',19,40,1),('Haifa','13','Snickers',17,40,1),('Haifa','14','Cheese flavored Popcorns',15,40,1),('Haifa','15','Coca Cola Zero can',19,40,1),('Haifa','16','Coca Cola can',17,40,1),('Haifa','17','Neviot grape flavor',19,40,1),('Haifa','18','Coca Cola Zero bottle',19,40,1),('Haifa','19','Coca Cola bottle',19,40,1),('Haifa','2','Apropo Bisley onion flavor',19,40,1),('Haifa','20','Coke Zero Watermelon',19,40,1),('Haifa','21','Cheetos XO Pizza Circle',18,40,1),('Haifa','22','Cheetos cheese',19,40,1),('Haifa','23','Kinder Bueno',19,40,1),('Haifa','24','Click Biscuit',19,40,1),('Haifa','25','Cranchos Onion',19,40,1),('Haifa','26','Crunchos barbecue',19,40,1),('Haifa','27','Crunchos Pizza',19,40,1),('Haifa','28','Schweppes Bitter Lemon',19,40,1),('Haifa','29','Schweppes Lemon soda',19,40,1),('Haifa','3','Apropo corn snack',19,40,1),('Haifa','30','Tapochips onion cream',19,40,1),('Haifa','31','Tapochips pops',19,40,1),('Haifa','32','Tapochips Mexican Crunch',19,40,1),('Haifa','4','Beasley BBQ',19,40,1),('Haifa','5','Beasley Grill',19,40,1),('Haifa','6','Beasley Pizza',19,40,1),('Haifa','7','Beasley Cheesy Sticks',19,40,1),('Haifa','8','Neviot apple flavor',19,40,1),('Haifa','9','Bamba filled with brownies',19,40,1),('Nahariya','1','Bamba',19,40,0),('Nahariya','10','Doritos nachos',19,40,0),('Nahariya','11','Doritos Cool Ranch',19,40,0),('Nahariya','12','Mars',19,40,0),('Nahariya','13','Snickers',19,40,0),('Nahariya','14','Cheese flavored Popcorns',19,40,0),('Nahariya','15','Coca Cola Zero can',19,40,0),('Nahariya','16','Coca Cola can',19,40,0),('Nahariya','17','Neviot grape flavor',19,40,0),('Nahariya','18','Coca Cola Zero bottle',19,40,0),('Nahariya','19','Coca Cola bottle',19,40,1),('Nahariya','2','Apropo Bisley onion flavor',41,40,0),('Nahariya','20','Coke Zero Watermelon',19,40,0),('Nahariya','21','Cheetos XO Pizza Circle',19,40,0),('Nahariya','22','Cheetos cheese',19,40,0),('Nahariya','23','Kinder Bueno',19,40,0),('Nahariya','24','Click Biscuit',19,40,0),('Nahariya','25','Cranchos Onion',19,40,0),('Nahariya','26','Crunchos barbecue',19,40,0),('Nahariya','27','Crunchos Pizza',19,40,0),('Nahariya','28','Schweppes Bitter Lemon',19,40,0),('Nahariya','29','Schweppes Lemon soda',19,40,0),('Nahariya','3','Apropo corn snack',19,40,0),('Nahariya','30','Tapochips onion cream',19,40,0),('Nahariya','31','Tapochips pops',19,40,0),('Nahariya','32','Tapochips Mexican Crunch',19,40,0),('Nahariya','4','Beasley BBQ',19,40,0),('Nahariya','5','Beasley Grill',19,40,0),('Nahariya','6','Beasley Pizza',19,40,0),('Nahariya','7','Beasley Cheesy Sticks',19,40,0),('Nahariya','8','Neviot apple flavor',19,40,0),('Nahariya','9','Bamba filled with brownies',19,40,0),('Sharjah','1','Bamba',19,2,0),('Sharjah','10','Doritos nachos',19,2,0),('Sharjah','11','Doritos Cool Ranch',19,2,0),('Sharjah','12','Mars',19,2,0),('Sharjah','13','Snickers',19,2,0),('Sharjah','14','Cheese flavored Popcorns',19,2,0),('Sharjah','15','Coca Cola Zero can',19,2,0),('Sharjah','16','Coca Cola can',19,2,0),('Sharjah','17','Neviot grape flavor',19,2,0),('Sharjah','18','Coca Cola Zero bottle',19,2,0),('Sharjah','19','Coca Cola bottle',19,2,0),('Sharjah','2','Apropo Bisley onion flavor',19,2,0),('Sharjah','20','Coke Zero Watermelon',19,2,0),('Sharjah','21','Cheetos XO Pizza Circle',19,2,0),('Sharjah','22','Cheetos cheese',19,2,0),('Sharjah','23','Kinder Bueno',19,2,0),('Sharjah','24','Click Biscuit',19,2,0),('Sharjah','25','Cranchos Onion',19,2,0),('Sharjah','26','Crunchos barbecue',19,2,0),('Sharjah','27','Crunchos Pizza',19,2,0),('Sharjah','28','Schweppes Bitter Lemon',19,2,0),('Sharjah','29','Schweppes Lemon soda',19,2,0),('Sharjah','3','Apropo corn snack',19,2,0),('Sharjah','30','Tapochips onion cream',19,2,0),('Sharjah','31','Tapochips pops',19,2,0),('Sharjah','32','Tapochips Mexican Crunch',19,2,0),('Sharjah','4','Beasley BBQ',19,2,0),('Sharjah','5','Beasley Grill',19,2,0),('Sharjah','6','Beasley Pizza',19,2,0),('Sharjah','7','Beasley Cheesy Sticks',19,2,0),('Sharjah','8','Neviot apple flavor',19,2,0),('Sharjah','9','Bamba filled with brownies',19,2,0);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthlycustomerreport`
--

DROP TABLE IF EXISTS `monthlycustomerreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthlycustomerreport` (
  `Report_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CustomerID` int(11) NOT NULL,
  `Number_Of_Orders` int(11) DEFAULT NULL,
  `Region` varchar(45) DEFAULT NULL,
  `Branch` varchar(45) DEFAULT NULL,
  `Month` varchar(100) DEFAULT NULL,
  `Year` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Report_ID`,`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthlycustomerreport`
--

LOCK TABLES `monthlycustomerreport` WRITE;
/*!40000 ALTER TABLE `monthlycustomerreport` DISABLE KEYS */;
INSERT INTO `monthlycustomerreport` VALUES (1,223837198,2,'North',NULL,'12','2022'),(1,304842442,1,'North',NULL,'12','2022'),(1,314761065,3,'North',NULL,'12','2022'),(1,343092928,7,'North',NULL,'12','2022'),(2,223837198,5,'South',NULL,'12','2022'),(2,304842442,15,'South',NULL,'12','2022'),(2,343092928,6,'South',NULL,'12','2022'),(3,223837198,1,'UAE',NULL,'12','2022');
/*!40000 ALTER TABLE `monthlycustomerreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthlyinventoryreports`
--

DROP TABLE IF EXISTS `monthlyinventoryreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthlyinventoryreports` (
  `Report_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Product_Name` varchar(50) DEFAULT NULL,
  `Region` varchar(45) DEFAULT NULL,
  `Branch` varchar(45) DEFAULT NULL,
  `Year` varchar(45) DEFAULT NULL,
  `Month` varchar(45) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Treshold_Point` int(11) DEFAULT NULL,
  `Treshold_PointCounter` int(11) DEFAULT NULL,
  PRIMARY KEY (`Report_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthlyinventoryreports`
--

LOCK TABLES `monthlyinventoryreports` WRITE;
/*!40000 ALTER TABLE `monthlyinventoryreports` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthlyinventoryreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthlyordersreports`
--

DROP TABLE IF EXISTS `monthlyordersreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthlyordersreports` (
  `Report_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Region` varchar(45) NOT NULL,
  `Branch` varchar(45) NOT NULL,
  `OrdersAmount` int(11) DEFAULT NULL,
  `Month` varchar(45) DEFAULT NULL,
  `Year` varchar(45) DEFAULT NULL,
  `SupplyMethod` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Report_ID`,`Region`,`Branch`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthlyordersreports`
--

LOCK TABLES `monthlyordersreports` WRITE;
/*!40000 ALTER TABLE `monthlyordersreports` DISABLE KEYS */;
INSERT INTO `monthlyordersreports` VALUES (1,'North','Haifa',4,'12','2022','Store_PickUp'),(1,'North','Nahariya',6,'12','2022','Store_PickUp'),(2,'UAE','Dubai',1,'12','2022','Store_PickUp'),(3,'South','Ashkelon',6,'12','2022','Store_PickUp'),(3,'South','Eilat',16,'12','2022','Store_PickUp'),(4,'North','Haifa',4,'12','2022',NULL);
/*!40000 ALTER TABLE `monthlyordersreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthlyupdateinventory`
--

DROP TABLE IF EXISTS `monthlyupdateinventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthlyupdateinventory` (
  `ReportID` int(11) NOT NULL,
  `Branch` varchar(100) NOT NULL,
  `Product_ID` varchar(50) NOT NULL,
  `Product_Name` varchar(45) DEFAULT NULL,
  `Product_Quantity` int(11) DEFAULT NULL,
  `Threshold_Point` int(11) DEFAULT NULL,
  `Date` varchar(45) DEFAULT NULL,
  `Threshold_PointCounter` int(11) DEFAULT NULL,
  PRIMARY KEY (`Branch`,`Product_ID`,`ReportID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthlyupdateinventory`
--

LOCK TABLES `monthlyupdateinventory` WRITE;
/*!40000 ALTER TABLE `monthlyupdateinventory` DISABLE KEYS */;
INSERT INTO `monthlyupdateinventory` VALUES (1,'Ashkelon','1','Bamba',18,0,'2022-12-01',0),(1,'Ashkelon','10','Doritos nachos',18,0,'2022-12-01',0),(1,'Ashkelon','11','Doritos Cool Ranch',18,0,'2022-12-01',0),(1,'Ashkelon','12','Mars',15,0,'2022-12-01',0),(1,'Ashkelon','13','Snickers',17,0,'2022-12-01',0),(1,'Ashkelon','14','Cheese flavored Popcorns',19,0,'2022-12-01',0),(1,'Ashkelon','15','Coca Cola Zero can',18,0,'2022-12-01',0),(1,'Ashkelon','16','Coca Cola can',19,0,'2022-12-01',0),(1,'Ashkelon','17','Neviot grape flavor',19,0,'2022-12-01',0),(1,'Ashkelon','18','Coca Cola Zero bottle',19,0,'2022-12-01',0),(1,'Ashkelon','19','Coca Cola bottle',19,0,'2022-12-01',0),(1,'Ashkelon','2','Apropo Bisley onion flavor',19,0,'2022-12-01',0),(1,'Ashkelon','20','Coke Zero Watermelon',19,0,'2022-12-01',0),(1,'Ashkelon','21','Cheetos XO Pizza Circle',19,0,'2022-12-01',0),(1,'Ashkelon','22','Cheetos cheese',19,0,'2022-12-01',0),(1,'Ashkelon','23','Kinder Bueno',19,0,'2022-12-01',0),(1,'Ashkelon','24','Click Biscuit',19,0,'2022-12-01',0),(1,'Ashkelon','25','Cranchos Onion',19,0,'2022-12-01',0),(1,'Ashkelon','26','Crunchos barbecue',19,0,'2022-12-01',0),(1,'Ashkelon','27','Crunchos Pizza',19,0,'2022-12-01',0),(1,'Ashkelon','28','Schweppes Bitter Lemon',19,0,'2022-12-01',0),(1,'Ashkelon','29','Schweppes Lemon soda',19,0,'2022-12-01',0),(1,'Ashkelon','3','Apropo corn snack',19,0,'2022-12-01',0),(1,'Ashkelon','30','Tapochips onion cream',19,0,'2022-12-01',0),(1,'Ashkelon','31','Tapochips pops',19,0,'2022-12-01',0),(1,'Ashkelon','32','Tapochips Mexican Crunch',19,0,'2022-12-01',0),(1,'Ashkelon','4','Beasley BBQ',19,0,'2022-12-01',0),(1,'Ashkelon','5','Beasley Grill',19,0,'2022-12-01',0),(1,'Ashkelon','6','Beasley Pizza',19,0,'2022-12-01',0),(1,'Ashkelon','7','Beasley Cheesy Sticks',19,0,'2022-12-01',0),(1,'Ashkelon','8','Neviot apple flavor',19,0,'2022-12-01',0),(1,'Ashkelon','9','Bamba filled with brownies',19,0,'2022-12-01',0),(1,'Dubai','1','Bamba',18,6,'2022-12-01',NULL),(1,'Dubai','10','Doritos nachos',18,6,'2022-12-01',NULL),(1,'Dubai','11','Doritos Cool Ranch',19,6,'2022-12-01',NULL),(1,'Dubai','12','Mars',19,6,'2022-12-01',NULL),(1,'Dubai','13','Snickers',19,6,'2022-12-01',NULL),(1,'Dubai','14','Cheese flavored Popcorns',19,6,'2022-12-01',NULL),(1,'Dubai','15','Coca Cola Zero can',19,6,'2022-12-01',NULL),(1,'Dubai','16','Coca Cola can',19,6,'2022-12-01',NULL),(1,'Dubai','17','Neviot grape flavor',19,6,'2022-12-01',NULL),(1,'Dubai','18','Coca Cola Zero bottle',19,6,'2022-12-01',NULL),(1,'Dubai','19','Coca Cola bottle',19,6,'2022-12-01',NULL),(1,'Dubai','2','Apropo Bisley onion flavor',19,6,'2022-12-01',NULL),(1,'Dubai','20','Coke Zero Watermelon',19,6,'2022-12-01',NULL),(1,'Dubai','21','Cheetos XO Pizza Circle',19,6,'2022-12-01',NULL),(1,'Dubai','22','Cheetos cheese',19,6,'2022-12-01',NULL),(1,'Dubai','23','Kinder Bueno',19,6,'2022-12-01',NULL),(1,'Dubai','24','Click Biscuit',19,6,'2022-12-01',NULL),(1,'Dubai','25','Cranchos Onion',19,6,'2022-12-01',NULL),(1,'Dubai','26','Crunchos barbecue',19,6,'2022-12-01',NULL),(1,'Dubai','27','Crunchos Pizza',19,6,'2022-12-01',NULL),(1,'Dubai','28','Schweppes Bitter Lemon',19,6,'2022-12-01',NULL),(1,'Dubai','29','Schweppes Lemon soda',19,6,'2022-12-01',NULL),(1,'Dubai','3','Apropo corn snack',19,6,'2022-12-01',NULL),(1,'Dubai','30','Tapochips onion cream',19,6,'2022-12-01',NULL),(1,'Dubai','31','Tapochips pops',19,6,'2022-12-01',NULL),(1,'Dubai','32','Tapochips Mexican Crunch',19,6,'2022-12-01',NULL),(1,'Dubai','4','Beasley BBQ',19,6,'2022-12-01',NULL),(1,'Dubai','5','Beasley Grill',19,6,'2022-12-01',NULL),(1,'Dubai','6','Beasley Pizza',19,6,'2022-12-01',NULL),(1,'Dubai','7','Beasley Cheesy Sticks',19,6,'2022-12-01',NULL),(1,'Dubai','8','Neviot apple flavor',19,6,'2022-12-01',NULL),(1,'Dubai','9','Bamba filled with brownies',19,6,'2022-12-01',NULL),(1,'Eilat','1','Bamba',13,0,'2022-12-01',0),(1,'Eilat','10','Doritos nachos',10,0,'2022-12-01',0),(1,'Eilat','11','Doritos Cool Ranch',11,0,'2022-12-01',0),(1,'Eilat','12','Mars',17,0,'2022-12-01',0),(1,'Eilat','13','Snickers',19,0,'2022-12-01',0),(1,'Eilat','14','Cheese flavored Popcorns',17,0,'2022-12-01',0),(1,'Eilat','15','Coca Cola Zero can',17,0,'2022-12-01',0),(1,'Eilat','16','Coca Cola can',19,0,'2022-12-01',0),(1,'Eilat','17','Neviot grape flavor',18,0,'2022-12-01',0),(1,'Eilat','18','Coca Cola Zero bottle',19,0,'2022-12-01',0),(1,'Eilat','19','Coca Cola bottle',19,0,'2022-12-01',0),(1,'Eilat','2','Apropo Bisley onion flavor',18,0,'2022-12-01',0),(1,'Eilat','20','Coke Zero Watermelon',19,0,'2022-12-01',0),(1,'Eilat','21','Cheetos XO Pizza Circle',19,0,'2022-12-01',0),(1,'Eilat','22','Cheetos cheese',19,0,'2022-12-01',0),(1,'Eilat','23','Kinder Bueno',19,0,'2022-12-01',0),(1,'Eilat','24','Click Biscuit',19,0,'2022-12-01',0),(1,'Eilat','25','Cranchos Onion',19,0,'2022-12-01',0),(1,'Eilat','26','Crunchos barbecue',19,0,'2022-12-01',0),(1,'Eilat','27','Crunchos Pizza',19,0,'2022-12-01',0),(1,'Eilat','28','Schweppes Bitter Lemon',19,0,'2022-12-01',0),(1,'Eilat','29','Schweppes Lemon soda',19,0,'2022-12-01',0),(1,'Eilat','3','Apropo corn snack',19,0,'2022-12-01',0),(1,'Eilat','30','Tapochips onion cream',19,0,'2022-12-01',0),(1,'Eilat','31','Tapochips pops',19,0,'2022-12-01',0),(1,'Eilat','32','Tapochips Mexican Crunch',19,0,'2022-12-01',0),(1,'Eilat','4','Beasley BBQ',19,0,'2022-12-01',0),(1,'Eilat','5','Beasley Grill',19,0,'2022-12-01',0),(1,'Eilat','6','Beasley Pizza',19,0,'2022-12-01',0),(1,'Eilat','7','Beasley Cheesy Sticks',19,0,'2022-12-01',0),(1,'Eilat','8','Neviot apple flavor',19,0,'2022-12-01',0),(1,'Eilat','9','Bamba filled with brownies',19,0,'2022-12-01',0),(1,'Haifa','1','Bamba',14,40,'2022-12-01',2),(1,'Haifa','10','Doritos nachos',16,40,'2022-12-01',2),(1,'Haifa','11','Doritos Cool Ranch',17,40,'2022-12-01',1),(1,'Haifa','12','Mars',19,40,'2022-12-01',1),(1,'Haifa','13','Snickers',17,40,'2022-12-01',1),(1,'Haifa','14','Cheese flavored Popcorns',15,40,'2022-12-01',1),(1,'Haifa','15','Coca Cola Zero can',19,40,'2022-12-01',1),(1,'Haifa','16','Coca Cola can',17,40,'2022-12-01',1),(1,'Haifa','17','Neviot grape flavor',19,40,'2022-12-01',1),(1,'Haifa','18','Coca Cola Zero bottle',19,40,'2022-12-01',1),(1,'Haifa','19','Coca Cola bottle',19,40,'2022-12-01',1),(1,'Haifa','2','Apropo Bisley onion flavor',19,40,'2022-12-01',2),(1,'Haifa','20','Coke Zero Watermelon',19,40,'2022-12-01',1),(1,'Haifa','21','Cheetos XO Pizza Circle',18,40,'2022-12-01',1),(1,'Haifa','22','Cheetos cheese',19,40,'2022-12-01',1),(1,'Haifa','23','Kinder Bueno',19,40,'2022-12-01',1),(1,'Haifa','24','Click Biscuit',19,40,'2022-12-01',1),(1,'Haifa','25','Cranchos Onion',19,40,'2022-12-01',1),(1,'Haifa','26','Crunchos barbecue',19,40,'2022-12-01',1),(1,'Haifa','27','Crunchos Pizza',19,40,'2022-12-01',1),(1,'Haifa','28','Schweppes Bitter Lemon',19,40,'2022-12-01',1),(1,'Haifa','29','Schweppes Lemon soda',19,40,'2022-12-01',1),(1,'Haifa','3','Apropo corn snack',19,40,'2022-12-01',2),(1,'Haifa','30','Tapochips onion cream',19,40,'2022-12-01',1),(1,'Haifa','31','Tapochips pops',19,40,'2022-12-01',1),(1,'Haifa','32','Tapochips Mexican Crunch',19,40,'2022-12-01',1),(1,'Haifa','4','Beasley BBQ',19,40,'2022-12-01',2),(1,'Haifa','5','Beasley Grill',19,40,'2022-12-01',2),(1,'Haifa','6','Beasley Pizza',19,40,'2022-12-01',2),(1,'Haifa','7','Beasley Cheesy Sticks',19,40,'2022-12-01',2),(1,'Haifa','8','Neviot apple flavor',19,40,'2022-12-01',1),(1,'Haifa','9','Bamba filled with brownies',19,40,'2022-12-01',2),(1,'Nahariya','1','Bamba',19,40,'2022-12-01',1),(1,'Nahariya','10','Doritos nachos',19,40,'2022-12-01',1),(1,'Nahariya','11','Doritos Cool Ranch',19,40,'2022-12-01',1),(1,'Nahariya','12','Mars',19,40,'2022-12-01',1),(1,'Nahariya','13','Snickers',19,40,'2022-12-01',1),(1,'Nahariya','14','Cheese flavored Popcorns',19,40,'2022-12-01',1),(1,'Nahariya','15','Coca Cola Zero can',19,40,'2022-12-01',1),(1,'Nahariya','16','Coca Cola can',19,40,'2022-12-01',1),(1,'Nahariya','17','Neviot grape flavor',19,40,'2022-12-01',1),(1,'Nahariya','18','Coca Cola Zero bottle',19,40,'2022-12-01',1),(1,'Nahariya','19','Coca Cola bottle',19,40,'2022-12-01',1),(1,'Nahariya','2','Apropo Bisley onion flavor',41,40,'2022-12-01',1),(1,'Nahariya','20','Coke Zero Watermelon',19,40,'2022-12-01',1),(1,'Nahariya','21','Cheetos XO Pizza Circle',19,40,'2022-12-01',1),(1,'Nahariya','22','Cheetos cheese',19,40,'2022-12-01',1),(1,'Nahariya','23','Kinder Bueno',19,40,'2022-12-01',1),(1,'Nahariya','24','Click Biscuit',19,40,'2022-12-01',1),(1,'Nahariya','25','Cranchos Onion',19,40,'2022-12-01',1),(1,'Nahariya','26','Crunchos barbecue',19,40,'2022-12-01',1),(1,'Nahariya','27','Crunchos Pizza',19,40,'2022-12-01',1),(1,'Nahariya','28','Schweppes Bitter Lemon',19,40,'2022-12-01',1),(1,'Nahariya','29','Schweppes Lemon soda',19,40,'2022-12-01',1),(1,'Nahariya','3','Apropo corn snack',19,40,'2022-12-01',1),(1,'Nahariya','30','Tapochips onion cream',19,40,'2022-12-01',1),(1,'Nahariya','31','Tapochips pops',19,40,'2022-12-01',1),(1,'Nahariya','32','Tapochips Mexican Crunch',19,40,'2022-12-01',1),(1,'Nahariya','4','Beasley BBQ',19,40,'2022-12-01',1),(1,'Nahariya','5','Beasley Grill',19,40,'2022-12-01',1),(1,'Nahariya','6','Beasley Pizza',19,40,'2022-12-01',1),(1,'Nahariya','7','Beasley Cheesy Sticks',19,40,'2022-12-01',1),(1,'Nahariya','8','Neviot apple flavor',19,40,'2022-12-01',1),(1,'Nahariya','9','Bamba filled with brownies',19,40,'2022-12-01',1),(1,'Sharjah','1','Bamba',19,2,'2022-12-01',NULL),(1,'Sharjah','10','Doritos nachos',19,2,'2022-12-01',NULL),(1,'Sharjah','11','Doritos Cool Ranch',19,2,'2022-12-01',NULL),(1,'Sharjah','12','Mars',19,2,'2022-12-01',NULL),(1,'Sharjah','13','Snickers',19,2,'2022-12-01',NULL),(1,'Sharjah','14','Cheese flavored Popcorns',19,2,'2022-12-01',NULL),(1,'Sharjah','15','Coca Cola Zero can',19,2,'2022-12-01',NULL),(1,'Sharjah','16','Coca Cola can',19,2,'2022-12-01',NULL),(1,'Sharjah','17','Neviot grape flavor',19,2,'2022-12-01',NULL),(1,'Sharjah','18','Coca Cola Zero bottle',19,2,'2022-12-01',NULL),(1,'Sharjah','19','Coca Cola bottle',19,2,'2022-12-01',NULL),(1,'Sharjah','2','Apropo Bisley onion flavor',19,2,'2022-12-01',NULL),(1,'Sharjah','20','Coke Zero Watermelon',19,2,'2022-12-01',NULL),(1,'Sharjah','21','Cheetos XO Pizza Circle',19,2,'2022-12-01',NULL),(1,'Sharjah','22','Cheetos cheese',19,2,'2022-12-01',NULL),(1,'Sharjah','23','Kinder Bueno',19,2,'2022-12-01',NULL),(1,'Sharjah','24','Click Biscuit',19,2,'2022-12-01',NULL),(1,'Sharjah','25','Cranchos Onion',19,2,'2022-12-01',NULL),(1,'Sharjah','26','Crunchos barbecue',19,2,'2022-12-01',NULL),(1,'Sharjah','27','Crunchos Pizza',19,2,'2022-12-01',NULL),(1,'Sharjah','28','Schweppes Bitter Lemon',19,2,'2022-12-01',NULL),(1,'Sharjah','29','Schweppes Lemon soda',19,2,'2022-12-01',NULL),(1,'Sharjah','3','Apropo corn snack',19,2,'2022-12-01',NULL),(1,'Sharjah','30','Tapochips onion cream',19,2,'2022-12-01',NULL),(1,'Sharjah','31','Tapochips pops',19,2,'2022-12-01',NULL),(1,'Sharjah','32','Tapochips Mexican Crunch',19,2,'2022-12-01',NULL),(1,'Sharjah','4','Beasley BBQ',19,2,'2022-12-01',NULL),(1,'Sharjah','5','Beasley Grill',19,2,'2022-12-01',NULL),(1,'Sharjah','6','Beasley Pizza',19,2,'2022-12-01',NULL),(1,'Sharjah','7','Beasley Cheesy Sticks',19,2,'2022-12-01',NULL),(1,'Sharjah','8','Neviot apple flavor',19,2,'2022-12-01',NULL),(1,'Sharjah','9','Bamba filled with brownies',19,2,'2022-12-01',NULL);
/*!40000 ALTER TABLE `monthlyupdateinventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `Order_Number` int(11) NOT NULL AUTO_INCREMENT,
  `PickUp_Code` varchar(6) DEFAULT NULL,
  `ID` varchar(50) NOT NULL,
  `Price` float NOT NULL,
  `Branch` varchar(100) DEFAULT NULL,
  `Order_Date` varchar(45) DEFAULT NULL,
  `Supply_Method` enum('Store_PickUp','Delivery') DEFAULT 'Store_PickUp',
  `Payment_Method` enum('credit_card','EKT') DEFAULT 'credit_card',
  `Order_Status` enum('Pending','Active','Canceled','Delivered') DEFAULT 'Pending',
  `Region` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Order_Number`),
  UNIQUE KEY `PickUp_Code_UNIQUE` (`PickUp_Code`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'1111','314761065',3.5,'Haifa','2022-12-12','Store_PickUp','credit_card','Active','North'),(2,'9691','223837198',5.05,'Haifa','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(3,'7164','223837198',7.15,'Nahariya','2022-12-12','Store_PickUp','credit_card','Active','North'),(4,'7889','223837198',16.8,'Nahariya','2022-12-12','Store_PickUp','credit_card','Delivered','North'),(5,'7789','304842442',0.84,'Eilat','2022-12-12','Store_PickUp','EKT','Active','South'),(6,'6357','304842442',1.9,'Nahariya','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(7,NULL,'304842442',52.5,'Eilat','2022-12-12','Delivery','EKT','Active','South'),(8,NULL,'304842442',52.5,'Nahariya','2022-12-12','Delivery','credit_card','Canceled','North'),(9,NULL,'304842442',52.5,'Nahariya','2023-01-05','Delivery','credit_card','Canceled','North'),(10,'7992','304842442',7.6,'Nahariya','2023-01-05','Store_PickUp','EKT','Active','North'),(11,NULL,'343092928',3.04,'Haifa','2023-01-05','Delivery','credit_card','Active','North'),(12,'4410','343092928',1.05,'Haifa','2023-01-05','Store_PickUp','EKT','Active','North'),(13,'4784','304842442',1.9,'Eilat','2023-01-05','Store_PickUp','EKT','Active','South'),(14,NULL,'304842442',1.05,'Eilat','2023-01-08','Store_PickUp','credit_card','Canceled','South'),(15,NULL,'304842442',1.05,'Eilat','2023-01-08','Store_PickUp','EKT','Active','South'),(16,NULL,'304842442',1.05,'Eilat','2023-01-08','Store_PickUp','EKT','Active','South'),(17,NULL,'304842442',1.05,'Eilat','2023-01-08','Store_PickUp','EKT','Canceled','South'),(18,NULL,'304842442',1.05,'Eilat','2023-01-08','Store_PickUp','credit_card','Active','South'),(19,NULL,'304842442',1.05,'Eilat','2023-01-08','Store_PickUp','credit_card','Active','South'),(22,NULL,'343092928',1.9,'Haifa','2023-01-08','Delivery','credit_card','Canceled','North'),(23,NULL,'343092928',1.9,'Haifa','2023-01-08','Delivery','credit_card','Canceled','North'),(24,'2826','223837198',1.9,'Haifa','2023-01-08','Store_PickUp','credit_card','Active','North'),(25,NULL,'223837198',1.52,'Haifa','2023-01-08','Delivery','EKT','Active','North'),(26,'1536','223837198',6.77,'Haifa','2023-01-08','Store_PickUp','EKT','Active','North'),(27,'4982','223837198',0,'Haifa','2023-01-08','Store_PickUp','EKT','Active','North'),(28,'6885','223837198',0,'Haifa','2023-01-08','Store_PickUp','credit_card','Canceled','North'),(29,'9371','223837198',0,'Haifa','2023-01-08','Store_PickUp','credit_card','Canceled','North'),(30,'5744','223837198',0,'Haifa','2023-01-08','Store_PickUp','credit_card','Canceled','North'),(31,'6651','223837198',1.9,'Haifa','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(32,NULL,'304842442',1.9,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(33,NULL,'304842442',1.9,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(37,NULL,'304842442',9.25,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(39,'2325','343092928',7.6,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(41,'5228','343092928',12.42,'Ashkelon','2022-12-12','Store_PickUp','credit_card','Active','South'),(43,'6784','223837198',5.65,'Haifa','2022-12-12','Store_PickUp','EKT','Active','North'),(44,'7218','223837198',6.39,'Haifa','2022-12-12','Store_PickUp','EKT','Active','North'),(45,NULL,'223837198',1.33,'Nahariya','2022-12-12','Delivery','credit_card','Canceled','North'),(46,NULL,'223837198',5.9,'Ashkelon','2022-12-12','Delivery','credit_card','Canceled','South'),(47,'8624','223837198',1.46,'Nahariya','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(48,'2521','223837198',1.05,'Ashkelon','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(49,'6199','223837198',2.95,'Ashkelon','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(50,'8572','223837198',2.95,'Dubai','2022-12-12','Store_PickUp','EKT','Active','UAE'),(52,NULL,'304842442',1.9,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(53,NULL,'304842442',1.9,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(54,NULL,'304842442',1.9,'Eilat','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(55,NULL,'343092928',1.05,'Nahariya','2022-12-12','Delivery','credit_card','Canceled','North'),(57,NULL,'343092928',1.05,'Ashkelon','2022-12-12','Delivery','credit_card','Canceled','South'),(58,NULL,'343092928',1.05,'Ashkelon','2022-12-12','Delivery','credit_card','Canceled','South'),(59,NULL,'343092928',1.9,'Nahariya','2022-12-12','Delivery','credit_card','Canceled','North'),(60,'5543','223837198',1.9,'Eilat','2022-12-12','Store_PickUp','EKT','Active','South'),(61,'7398','223837198',1.9,'Ashkelon','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(62,'9839','343092928',1.9,'Ashkelon','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(63,'6985','343092928',1.9,'Ashkelon','2022-12-12','Store_PickUp','credit_card','Canceled','South'),(64,NULL,'314761065',1.05,'Haifa','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(65,NULL,'314761065',1.9,'Haifa','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(66,NULL,'314761065',1.9,'Haifa','2022-12-12','Store_PickUp','credit_card','Canceled','North'),(70,NULL,'343092928',1.05,'Nahariya','2022-12-12','Delivery','credit_card','Active','North'),(71,NULL,'343092928',1.9,'Nahariya','2022-12-12','Delivery','credit_card','Active','North'),(72,NULL,'343092928',1.05,'Haifa','2022-12-12','Delivery','credit_card','Active','North'),(73,NULL,'343092928',1.05,'Haifa','2022-12-12','Delivery','credit_card','Active','North'),(75,'5757','223837198',1.9,'Nahariya','2022-12-12','Store_PickUp','EKT','Active','North'),(76,'5555','223837198',1.9,'Nahariya','2022-12-12','Store_PickUp','credit_card','Active','North'),(77,'4444','343092928',1.9,'Nahariya','2022-12-12','Store_PickUp','credit_card','Active','North'),(78,'6666','343092928',1.9,'Nahariya','2022-12-12','Store_PickUp','credit_card','Active','North'),(82,'6602','343092928',3.8,'Haifa','2023-01-15','Store_PickUp','credit_card','Active','North'),(83,'3791','343092928',3.8,'Haifa','2023-01-15','Store_PickUp','credit_card','Active','North'),(84,'8314','223837198',3.8,'Haifa','2023-01-15','Store_PickUp','EKT','Active','North'),(85,'5303','343092928',3.8,'Haifa','2023-01-15','Store_PickUp','credit_card','Delivered','North'),(86,NULL,'343092928',1.9,'Haifa','2023-01-16','Delivery','credit_card','Delivered','North');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `ImageSource` varchar(100) DEFAULT NULL,
  `Product_ID` int(11) NOT NULL,
  `Product_Name` varchar(45) NOT NULL,
  `Product_Price` float NOT NULL,
  `Discount_Price` float NOT NULL,
  `Product_Type` varchar(45) NOT NULL,
  PRIMARY KEY (`Product_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('/Bamba.png',1,'Bamba',1.9,1.9,'Snack'),('/Apropo Bisley onion flavor.png',2,'Apropo Bisley onion flavor',1.46,1.46,'Snack'),('/Apropo corn snack.png',3,'Apropo corn snack',1.46,1.46,'Snack'),('/Beasley BBQ.png',4,'Beasley BBQ	',1.29,1.29,'Snack'),('/Beasley Grill.png',5,'Beasley Grill',1.29,1.29,'Snack'),('/Beasley Pizza.png',6,'Beasley Pizza',1.29,1.29,'Snack'),('/Beasley Cheesy Sticks.png',7,'Beasley Cheesy Sticks',1.35,1.35,'Snack'),('/Neviot apple flavor.png',8,'Neviot apple flavor',1.58,1.58,'Beverage'),('/Bamba filled with brownies.png',9,'Bamba filled with brownies',1.9,1.9,'Snack'),('/Doritos nachos.png',10,'Doritos nachos',1.05,1.05,'Snack'),('/Doritos Cool Ranch.png',11,'Doritos Cool Ranch',1.05,1.05,'Snack'),('/Mars.png',12,'Mars',1.14,1.14,'Snack'),('/Snickers.png',13,'Snickers',1.14,1.14,'Snack'),('/Cheese flavored Popcorns.png',14,'Cheese flavored Popcorns',1.43,1.43,'Snack'),('/Coca Cola Zero can.png',15,'Coca Cola Zero can',1.58,1.58,'Beverage'),('/Coca Cola can.png',16,'Coca Cola can',1.58,1.58,'Beverage'),('/Neviot grape flavor.png',17,'Neviot grape flavor',1.58,1.58,'Beverage'),('/Coca Cola Zero bottle.png',18,'Coca Cola Zero bottle',2.2,2.2,'Beverage'),('/Coca Cola bottle.png',19,'Coca Cola bottle',2.2,1.1,'Beverage'),('/Coke Zero Watermelon Strawberry.png',20,'Coke Zero Watermelon Strawberry',1.58,1.58,'Beverage'),('/Cheetos XO Pizza Circle.png',21,'Cheetos XO Pizza Circle',1.02,1.02,'Snack'),('/Cheetos cheese.png',22,'Cheetos cheese',1.02,1.02,'Snack'),('/Kinder Bueno.png',23,'Kinder Bueno',1.14,1.14,'Snack'),('/Click Biscuit.png',24,'Click Biscuit',1.73,1.73,'Snack'),('/Cranchos Onion .png',25,'Cranchos Onion ',1.02,1.02,'Snack'),('/Crunchos barbecue.png',26,'Crunchos barbecue',1.02,1.02,'Snack'),('/Crunchos Pizza.png',27,'Crunchos Pizza',1.02,1.02,'Snack'),('/Schweppes Bitter Lemon.png',28,'Schweppes Bitter Lemon',2.11,2.11,'Beverage'),('/Schweppes Lemon soda.png',29,'Schweppes Lemon soda',2.11,2.11,'Beverage'),('/Tapochips onion cream.png',30,'Tapochips onion cream',1.17,1.17,'Snack'),('/Tapochips pops with natural flavor.png',31,'Tapochips pops ',1.17,1.17,'Snack'),('/Tapochips Mexican Crunch.png',32,'Tapochips Mexican Crunch',1.17,1.17,'Snack');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productsinorder`
--

DROP TABLE IF EXISTS `productsinorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productsinorder` (
  `Order_Number` int(11) NOT NULL,
  `Product_ID` int(11) NOT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Product_Name` varchar(45) DEFAULT NULL,
  `Product_Price` float DEFAULT NULL,
  PRIMARY KEY (`Order_Number`,`Product_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsinorder`
--

LOCK TABLES `productsinorder` WRITE;
/*!40000 ALTER TABLE `productsinorder` DISABLE KEYS */;
INSERT INTO `productsinorder` VALUES (3,1,1,'Bamba',1.9),(3,10,2,'Doritos nachos',2.1),(3,11,3,'Doritos Cool Ranch',3.15),(4,11,16,'Doritos Cool Ranch',16.8),(11,1,2,'Bamba',3.8),(14,10,1,'Doritos nachos',1.05),(15,10,1,'Doritos nachos',1.05),(16,10,1,'Doritos nachos',1.05),(17,10,1,'Doritos nachos',1.05),(18,10,1,'Doritos nachos',1.05),(19,10,1,'Doritos nachos',1.05),(20,10,1,'Doritos nachos',1.05),(21,10,1,'Doritos nachos',1.05),(24,1,1,'Bamba',1.9),(25,1,1,'Bamba',1.9),(26,11,1,'Doritos Cool Ranch',1.05),(26,14,4,'Cheese flavored Popcorns',5.72),(34,1,1,'Bamba',1.9),(34,10,1,'Doritos nachos',1.05),(34,11,1,'Doritos Cool Ranch',1.05),(35,1,1,'Bamba',1.9),(36,1,1,'Bamba',1.9),(38,1,1,'Bamba',1.9),(38,10,1,'Doritos nachos',1.05),(38,11,6,'Doritos Cool Ranch',6.3),(40,2,1,'Apropo Bisley onion flavor',1.46),(40,14,2,'Cheese flavored Popcorns',2.86),(40,15,2,'Coca Cola Zero can',3.16),(40,17,1,'Neviot grape flavor',1.58),(41,1,1,'Bamba',1.9),(41,10,1,'Doritos nachos',1.05),(41,11,1,'Doritos Cool Ranch',1.05),(41,12,4,'Mars',4.56),(41,13,2,'Snickers',2.28),(41,15,1,'Coca Cola Zero can',1.58),(42,10,1,'Doritos nachos',1.05),(42,11,1,'Doritos Cool Ranch',1.05),(42,12,1,'Mars',1.14),(43,1,2,'Bamba',3.8),(43,10,2,'Doritos nachos',2.1),(43,11,1,'Doritos Cool Ranch',1.05),(43,13,1,'Snickers',1.14),(44,1,2,'Bamba',3.8),(44,13,1,'Snickers',1.14),(44,16,2,'Coca Cola can',3.16),(44,21,1,'Cheetos XO Pizza Circle',1.02),(50,1,1,'Bamba',1.9),(50,10,1,'Doritos nachos',1.05),(51,12,1,'Mars',1.14),(56,1,1,'Bamba',1.9),(60,1,1,'Bamba',1.9),(69,1,2,'Bamba',3.8),(70,1,2,'Bamba',3.8),(82,1,2,'Bamba',3.8),(83,1,2,'Bamba',3.8),(84,1,2,'Bamba',3.8),(85,1,2,'Bamba',3.8),(86,1,1,'Bamba',1.9);
/*!40000 ALTER TABLE `productsinorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restock`
--

DROP TABLE IF EXISTS `restock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restock` (
  `Branch` varchar(100) NOT NULL,
  `Product_ID` varchar(50) NOT NULL,
  `Desired_Quantity` int(11) DEFAULT NULL,
  `Status` enum('Pending','Done') DEFAULT NULL,
  `Date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Branch`,`Product_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restock`
--

LOCK TABLES `restock` WRITE;
/*!40000 ALTER TABLE `restock` DISABLE KEYS */;
INSERT INTO `restock` VALUES ('Haifa','1',10,'Done','2022-12-14'),('Haifa','10',10,'Done','2022-12-14'),('Haifa','2',10,'Done','2022-12-14'),('Haifa','3',30,'Done','2022-12-14'),('Haifa','4',30,'Done','2022-12-14'),('Haifa','5',10,'Done','2022-12-14'),('Haifa','6',30,'Done','2022-12-14'),('Haifa','7',10,'Done','2022-12-14'),('Haifa','9',30,'Done','2022-12-14'),('Haifa','All',30,'Done','2022-12-14'),('Nahariya','2',41,'Done','2023-01-13'),('Nahariya','All',19,'Done','2022-12-17');
/*!40000 ALTER TABLE `restock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `Sale_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Product_ID` varchar(45) NOT NULL,
  `Start_Sale` varchar(45) DEFAULT NULL,
  `End_Sale` varchar(45) DEFAULT NULL,
  `Original_Price` float DEFAULT NULL,
  `Discount_Percentage` float NOT NULL,
  PRIMARY KEY (`Product_ID`),
  UNIQUE KEY `Sale_ID_UNIQUE` (`Sale_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (28,'1',NULL,NULL,1.9,45),(33,'19',NULL,NULL,2.2,50),(34,'23',NULL,NULL,1.14,10),(35,'24',NULL,NULL,1.73,100),(32,'6',NULL,NULL,1.29,15),(39,'All',NULL,NULL,0,30);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribers`
--

DROP TABLE IF EXISTS `subscribers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscribers` (
  `ID` varchar(50) NOT NULL,
  `Subscriber_Number` int(11) NOT NULL,
  `Is_First_Purchase` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Subscriber_Number_UNIQUE` (`Subscriber_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribers`
--

LOCK TABLES `subscribers` WRITE;
/*!40000 ALTER TABLE `subscribers` DISABLE KEYS */;
INSERT INTO `subscribers` VALUES ('223837198',171515,1),('285529100',476689,0),('304842442',601923,1);
/*!40000 ALTER TABLE `subscribers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) DEFAULT NULL,
  `ID` varchar(50) NOT NULL,
  `First_Name` varchar(100) DEFAULT NULL,
  `Last_Name` varchar(100) DEFAULT NULL,
  `Role` enum('CEO','Regional_Manager','Operation_Worker','None','Service_Representative','Customer','Marketing_Manager','Marketing_Worker','Pending','Delivery_Coordinator') DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Phone_Number` varchar(100) DEFAULT NULL,
  `Is_Logged_In` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Ceo','123456','214637336','Elizabeth','II','CEO','waceya1484@dmonies.com','052433951',0),('CServiceEm','123456','282053881','Lark','Muzerberg','Service_Representative','civofoy832@eilnews.com','052934576',0),('Customer1','123456','343092928','Joe','Mama','Customer','woreyo7857@edinel.com','054011054',0),('Customer2','123456','304842442','Rick','Sanchez','Customer','fopone3928@dmonies.com','050476235',0),('Customer3','123456','223837198','Geff','bozos','Customer','noleviw255@dmonies.com','053055220',0),('Customer4','123456','298325273','Kim ','Wexler','Customer','layijev401@edinel.com','050395384',0),('Customer5','123456','285529100','Mary','Jane','Customer','faweyen717@dmonies.com','052236852',0),('Customer6','123456','354053895','Gill','Bates','Customer','rasori9949@diratu.com','050950823',0),('Customer69','69420','314761065','Hugh','Hefner','Customer','hughefner11@diratu.com','050224913',0),('Customer7','123456','327345568','Roger','Less','Customer','layeej5661@edinel.com','054808544',0),('Customer90','123456','200082568','Arnold','Schwarzenegger','None','baxix24333@octovie.com','052996661',0),('Customer91','123456','227848569','Bendydick','Crackerdong','Pending','zoix2431495@diratu.com','052690568',0),('Customer92','123456','378522836','Vin','Patrol','Pending','vafotop448@diratu.com','054155403',0),('Customer93','123456','277812528','Barck ','Obamba','None','gocenaw461@diratu.com','054600665',0),('CustomerBlock','123456','322379646','Salsa ','Stark','Customer','xatiye7325@eilnews.com','050366735',0),('CustomerFrozen','123456','303146766','Ellen ','Replay','Customer','sayopip387@edinel.com','054099361',0),('DeliveryNorth','123456','369123929','Natalie ','Postman','Delivery_Coordinator','wenixis981@dmonies.com','053031344',0),('DeliverySouth','123456','202323947','Wile','Coyote ','Delivery_Coordinator','zenixxwwe481@dmonies.com','054210237',0),('DeliveryUAE','123456','265758007','Bond ','James Bond','Delivery_Coordinator','xsitvab007@eilnews.com','050070007',0),('MManager','123456','238124725','Nicole','Kidme','Marketing_Manager','biyabok400@dmonies.com','050574379',0),('MWorkerNorth','123456','225855373','Gal','Gadol','Marketing_Worker','pegeti2263@diratu.com','050013447',0),('MWorkerSouth','123456','321148356','Max','Pain','Marketing_Worker','driklo193@dmonies.com','052154507',0),('MWorkerUAE','123456','250777580','Friday','Addams','Marketing_Worker','wndayfrei10@diratu.com','053766433',0),('OpWorker1','123456','362774561','Mr','T','Operation_Worker','abrt2335@eilnews.com','053753378',0),('OpWorker2','123456','312329680','The','Rock','Operation_Worker','saertor448@diratu.com','054580336',0),('OpWorker3','123456','227128543','Lady ','Jenga','Operation_Worker','flwey67517@dmonies.com','053245683',0),('OpWorker4','123456','325478999','Ben','Klock','Operation_Worker','benk@drift.com','055760439',0),('RManagerNorth','123456','362062621','Donald','Duck','Regional_Manager','wodipe1495@diratu.com','054299218',0),('RManagerSouth','123456','336697030','Eli','Gaga','Regional_Manager','vogeho8040@edinel.com','052047257',0),('RManagerUAE','123456','270776581','Elion','Task','Regional_Manager','yilak31581@edinel.com','053240437',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17 12:45:43
