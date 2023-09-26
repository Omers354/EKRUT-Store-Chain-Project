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

-- Dump completed on 2023-01-17  0:05:18
