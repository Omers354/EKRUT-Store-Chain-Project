-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: ekrut
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `ID` varchar(50) NOT NULL,
  `Customer_Number` int NOT NULL AUTO_INCREMENT,
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17  0:05:17
