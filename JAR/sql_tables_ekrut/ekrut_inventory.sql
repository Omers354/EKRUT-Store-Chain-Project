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
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `Branch` varchar(100) NOT NULL,
  `Product_ID` varchar(50) NOT NULL,
  `Product_Name` varchar(45) DEFAULT NULL,
  `Product_Quantity` int DEFAULT NULL,
  `Threshold_Point` int DEFAULT NULL,
  `Active_Sale` tinyint DEFAULT '0',
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17  0:05:18
