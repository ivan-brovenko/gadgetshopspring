-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: gadget_shop
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_id` int(11) NOT NULL DEFAULT '2',
  `details` varchar(100) NOT NULL DEFAULT 'ordinary',
  `date` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `ship_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `total_price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_order_status_id_fk` (`status_id`),
  KEY `order_user_id_fk` (`user_id`),
  KEY `order_ship_id_fk` (`ship_id`),
  KEY `order_bill_id_fk` (`bill_id`),
  CONSTRAINT `order_bill_id_fk` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_order_status_id_fk` FOREIGN KEY (`status_id`) REFERENCES `order_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_ship_id_fk` FOREIGN KEY (`ship_id`) REFERENCES `ship` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (26,2,'ordinary','2018-06-06 13:13:34',2,1,1,'',5200),(27,2,'ordinary','2018-06-06 13:19:29',2,2,1,'homw',1900),(28,2,'ordinary','2018-06-06 13:22:20',2,1,2,'',1350);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-06 13:27:50
