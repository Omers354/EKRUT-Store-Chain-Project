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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `Order_Number` int NOT NULL AUTO_INCREMENT,
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17  0:05:19
