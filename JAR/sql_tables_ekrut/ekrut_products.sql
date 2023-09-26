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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `ImageSource` varchar(100) DEFAULT NULL,
  `Product_ID` int NOT NULL,
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17  0:05:17
