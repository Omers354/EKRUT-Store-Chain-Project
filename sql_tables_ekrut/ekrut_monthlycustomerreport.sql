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
-- Table structure for table `monthlycustomerreport`
--

DROP TABLE IF EXISTS `monthlycustomerreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthlycustomerreport` (
  `Report_ID` int NOT NULL AUTO_INCREMENT,
  `CustomerID` int NOT NULL,
  `Number_Of_Orders` int DEFAULT NULL,
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17  0:05:19
