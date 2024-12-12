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
-- Table structure for table `familia`
--

DROP TABLE IF EXISTS `familia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `familia` (
  `idFamilia` int NOT NULL,
  `nom` varchar(50) NOT NULL,
  `descripcio` varchar(500) DEFAULT NULL,
  `dataAlta` date NOT NULL,
  `dataBaixa` date DEFAULT NULL,
  `proveidorDefault` varchar(100) NOT NULL,
  `observacions` varchar(500) NOT NULL,
  `descompteGeneral` float DEFAULT NULL,
  PRIMARY KEY (`idFamilia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `familia`
--

LOCK TABLES `familia` WRITE;
/*!40000 ALTER TABLE `familia` DISABLE KEYS */;
INSERT INTO `familia` VALUES (1,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(2,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(3,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(4,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(5,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(6,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(7,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(8,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(9,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(10,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(11,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(12,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(13,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(14,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(15,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(16,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(17,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(18,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(19,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(20,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(21,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(22,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(23,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(24,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(25,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(26,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(27,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(28,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(29,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(30,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(31,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(32,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(33,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(34,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(35,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(36,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(37,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(38,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(39,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(40,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(41,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(42,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(43,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(44,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(45,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(46,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(47,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(48,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(49,'Familia','no','2019-09-08','2019-09-10','hola','no',10),(56,'q','a','1999-09-09','1999-09-09','a','q',1.1),(69,'qwe','qwer','1999-09-09',NULL,'Paco','qwer',0),(332,'q','q','1999-09-09',NULL,'a','1',1),(4789,'vom',NULL,'1999-09-09','1999-09-09','Paco','123312',9);
/*!40000 ALTER TABLE `familia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-14 10:39:49
