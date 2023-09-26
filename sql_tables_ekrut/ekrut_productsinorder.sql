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
-- Table structure for table `productsinorder`
--

DROP TABLE IF EXISTS `productsinorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productsinorder` (
  `Order_Number` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Quantity` int DEFAULT NULL,
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17  0:05:18
