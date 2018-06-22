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
-- Table structure for table `gadget`
--

DROP TABLE IF EXISTS `gadget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gadget` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memory_size` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `price` double NOT NULL,
  `category_id` int(11) NOT NULL,
  `producer_country_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gadget_category_id_fk` (`category_id`),
  KEY `gadget_producer_country_id_fk` (`producer_country_id`),
  CONSTRAINT `gadget_category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gadget_producer_country_id_fk` FOREIGN KEY (`producer_country_id`) REFERENCES `producer_country` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gadget`
--

LOCK TABLES `gadget` WRITE;
/*!40000 ALTER TABLE `gadget` DISABLE KEYS */;
INSERT INTO `gadget` VALUES (1,256,'iphone X',1000.99,1,1),(2,256,'iphone 8 plus',800,1,1),(3,250,'iphone 8',700,1,1),(4,32,'iphone 7 plus',600,1,2),(5,32,'iphone 5s',200,1,2),(6,512,'iphone 6s',550,1,2),(7,512,'iphone X',1200,1,1),(8,128,'iphone X',950,1,1),(9,32,'iphone X',900,1,1),(10,512,'iphone 8 plus',900,1,2),(11,128,'iphone 8 plus',750,1,2),(12,32,'iphone 8 plus',700,1,2),(13,512,'iphone 8',750,1,1),(14,128,'iphone 8',650,1,1),(15,32,'iphone 8',600,1,1),(16,512,'iphone 7 plus',700,1,2),(17,256,'iphone 7 plus',650,1,2),(18,128,'iphone 7 plus',630,1,2),(19,128,'iphone 6s',450,1,1);
/*!40000 ALTER TABLE `gadget` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-06 13:27:48
