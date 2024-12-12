-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: magatzemdb
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `magatzem`
--

DROP TABLE IF EXISTS `magatzem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `magatzem` (
  `idEntrada` int NOT NULL,
  `nomEntrada` varchar(50) NOT NULL,
  `dataEntrada` date NOT NULL,
  `dataSortida` date DEFAULT NULL,
  `motiuSortida` varchar(100) DEFAULT NULL,
  `valorTotal` float NOT NULL,
  `tipus` enum('FAMILIA','REFERENCIA') NOT NULL,
  PRIMARY KEY (`idEntrada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magatzem`
--

LOCK TABLES `magatzem` WRITE;
/*!40000 ALTER TABLE `magatzem` DISABLE KEYS */;
INSERT INTO `magatzem` VALUES (1,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(2,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(3,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(4,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(5,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(6,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(7,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(8,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(9,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(10,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(11,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(12,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(13,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(14,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(15,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(16,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(17,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(18,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(19,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(20,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(21,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(22,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(23,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(24,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(25,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(26,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(27,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(28,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(29,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(30,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(31,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(32,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(33,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(34,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(35,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(36,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(37,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(38,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(39,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(40,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(41,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(42,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(43,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(44,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(45,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(46,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(47,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(48,'entrada','2019-09-08','2019-09-10','hola',10,'REFERENCIA'),(49,'entrada','2019-09-08','2019-09-10','hola',10,'FAMILIA'),(800,'adsf','1999-09-09',NULL,NULL,100,'FAMILIA');
/*!40000 ALTER TABLE `magatzem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-14 10:39:48
