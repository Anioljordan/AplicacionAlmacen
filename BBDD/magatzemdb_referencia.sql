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
-- Table structure for table `referencia`
--

DROP TABLE IF EXISTS `referencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `referencia` (
  `idReferencia` int NOT NULL,
  `nom` varchar(45) NOT NULL,
  `UoM` varchar(10) NOT NULL,
  `quantitatProducte` int NOT NULL,
  `familiaProductes` varchar(30) NOT NULL,
  `proveidor` varchar(45) NOT NULL,
  `dataAlta` date NOT NULL,
  `dataBaixa` date DEFAULT NULL,
  `preuUnitari` float NOT NULL,
  `motiuBaixa` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idReferencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referencia`
--

LOCK TABLES `referencia` WRITE;
/*!40000 ALTER TABLE `referencia` DISABLE KEYS */;
INSERT INTO `referencia` VALUES (1,'Producte A','kg',100,'Família A','Proveïdor A','2022-01-01','2023-01-01',12.5,'Finalització de contracte'),(2,'Producte B','l',200,'Família B','Proveïdor B','2022-02-01','2023-02-01',8.75,'Obsolet'),(3,'Producte C','unit',50,'Família C','Proveïdor C','2022-03-01','2023-03-01',25,'Canvi de proveïdor'),(4,'Producte D','box',150,'Família D','Proveïdor D','2022-04-01','2023-04-01',6.5,'Reducció de demanda'),(5,'Producte E','kg',120,'Família E','Proveïdor E','2022-05-01','2023-05-01',18.3,'Problemes de qualitat'),(6,'Producte F','l',80,'Família F','Proveïdor F','2022-06-01','2023-06-01',10.75,'Renegociació del contracte'),(7,'Producte G','unit',220,'Família G','Proveïdor G','2022-07-01','2023-07-01',50.2,'Baixa rendibilitat'),(8,'Producte H','box',70,'Família H','Proveïdor H','2022-08-01','2023-08-01',5.4,'Reestructuració interna'),(9,'Producte I','kg',30,'Familia','Paco','2022-09-01','2023-09-01',7.9,'Fusió amb altre producte'),(10,'Producte J','l',110,'Família J','Proveïdor J','2022-10-01','2023-10-01',22.1,'Canvi en estratègia de negoci'),(11,'Producte K','unit',95,'Família K','Proveïdor K','2022-11-01','2023-11-01',15.3,'Retirada per incompliment normatiu'),(12,'Producte L','box',45,'Família L','Proveïdor L','2022-12-01','2023-12-01',3.6,'Caiguda de vendes'),(13,'Producte M','kg',140,'Família M','Proveïdor M','2022-01-10','2023-01-10',9.99,'Sustituït per un nou model'),(14,'Producte N','l',65,'Família N','Proveïdor N','2022-02-15','2023-02-15',11.75,'Reducció dinventari'),(15,'Producte O','unit',180,'Família O','Proveïdor O','2022-03-20','2023-03-20',20,'Problema de subministrament'),(16,'Producte P','box',210,'Família P','Proveïdor P','2022-04-25','2023-04-25',4.9,'Falta de demanda'),(17,'Producte Q','kg',160,'Família Q','Proveïdor Q','2022-05-05','2023-05-05',12.3,'Substituït per una nova versió'),(18,'Producte R','l',75,'Família R','Proveïdor R','2022-06-10','2023-06-10',16.1,'Obsolet per innovació tecnològica'),(19,'Producte S','unit',250,'Família S','Proveïdor S','2022-07-15','2023-07-15',30.7,'Problemes amb la normativa de seguretat'),(20,'Producte T','box',130,'Família T','Proveïdor T','2022-08-20','2023-08-20',7,'Canvi de requisits del client'),(21,'Producte U','kg',105,'Família U','Proveïdor U','2022-09-01','2023-09-01',14.6,'Nou proveïdor seleccionat'),(22,'Producte V','l',180,'Família V','Proveïdor V','2022-10-05','2023-10-05',12.3,'Reducció de costos'),(23,'Producte W','unit',140,'Família W','Proveïdor W','2022-11-01','2023-11-01',25.9,'Problemes tècnics'),(24,'Producte X','box',75,'Família X','Proveïdor X','2022-12-10','2023-12-10',5.7,'Descontinuat pel fabricant'),(25,'Producte Y','kg',115,'Família Y','Proveïdor Y','2022-01-15','2023-01-15',9.8,'Problema amb la cadena de subministrament'),(26,'Producte Z','l',200,'Família Z','Proveïdor Z','2022-02-20','2023-02-20',11.1,'Canvi en les especificacions del producte'),(27,'Producte AA','unit',190,'Família AA','Proveïdor AA','2022-03-25','2023-03-25',33.3,'Desviació pressupostària'),(28,'Producte BB','box',135,'Família BB','Proveïdor BB','2022-04-30','2023-04-30',6.8,'Decretat fora destoc'),(29,'Producte CC','kg',100,'Família CC','Proveïdor CC','2022-05-05','2023-05-05',10.5,'Retirat per baixa demanda'),(30,'Producte DD','l',65,'Família DD','Proveïdor DD','2022-06-10','2023-06-10',13.4,'Fusió amb altre producte similar'),(31,'Producte EE','unit',225,'Família EE','Proveïdor EE','2022-07-15','2023-07-15',27.8,'Retirat per queixes dels clients'),(32,'Producte FF','box',90,'Família FF','Proveïdor FF','2022-08-20','2023-08-20',7.6,'Manca de components essencials'),(33,'Producte GG','kg',145,'Família GG','Proveïdor GG','2022-09-25','2023-09-25',14.2,'No compleix els requisits ambientals'),(34,'Producte HH','l',85,'Família HH','Proveïdor HH','2022-10-30','2023-10-30',10.9,'Reemplaçat per una versió millorada'),(35,'Producte II','unit',175,'Família II','Proveïdor II','2022-11-05','2023-11-05',22.6,'Baixa sostenibilitat econòmica'),(36,'Producte JJ','box',60,'Família JJ','Proveïdor JJ','2022-12-10','2023-12-10',6.3,'Error de producció'),(37,'Producte KK','kg',210,'Família KK','Proveïdor KK','2022-01-15','2023-01-15',13,'Reducció de linventari'),(38,'Producte LL','l',125,'Família LL','Proveïdor LL','2022-02-20','2023-02-20',11.5,'Canvi de proveïdor per millora de qualitat'),(39,'Producte MM','unit',135,'Família MM','Proveïdor MM','2022-03-25','2023-03-25',29.1,'Error en la documentació tècnica'),(40,'Producte NN','box',195,'Família NN','Proveïdor NN','2022-04-30','2023-04-30',8.2,'Retirat per motius legals'),(41,'Producte OO','kg',160,'Família OO','Proveïdor OO','2022-05-05','2023-05-05',12.8,'Nou proveïdor seleccionat'),(42,'Producte PP','l',250,'Família PP','Proveïdor PP','2022-06-10','2023-06-10',14.3,'Reducció de costos'),(43,'Producte QQ','unit',140,'Família QQ','Proveïdor QQ','2022-07-15','2023-07-15',26.7,'Problemes tècnics'),(44,'Producte RR','box',75,'Família RR','Proveïdor RR','2022-08-20','2023-08-20',5.9,'Descontinuat pel fabricant'),(45,'Producte SS','kg',115,'Família SS','Proveïdor SS','2022-09-25','2023-09-25',9.5,'Problema amb la cadena de subministrament'),(46,'Producte TT','l',200,'Família TT','Proveïdor TT','2022-10-30','2023-10-30',11.2,'Canvi en les especificacions del producte'),(47,'Producte UU','unit',190,'Família UU','Proveïdor UU','2022-11-05','2023-11-05',33.1,'Desviació pressupostària'),(48,'Producte VV','box',135,'Família VV','Proveïdor VV','2022-12-10','2023-12-10',6.7,'Decretat fora destoc'),(49,'Producte WW','kg',100,'Família WW','Proveïdor WW','2022-01-15','2023-01-15',10.9,'Retirat per baixa demanda'),(50,'Producte XX','l',65,'Família XX','Proveïdor XX','2022-02-20','2023-02-20',13.1,'Fusió amb altre producte similar'),(51,'Producte YY','unit',225,'Família YY','Proveïdor YY','2022-03-25','2023-03-25',27.5,'Retirat per queixes dels clients'),(52,'Producte ZZ','box',90,'Família ZZ','Proveïdor ZZ','2022-04-30','2023-04-30',7.1,'Manca de components essencials'),(53,'Producte AAA','kg',145,'Família AAA','Proveïdor AAA','2022-05-05','2023-05-05',14.4,'No compleix els requisits ambientals'),(54,'Producte BBB','l',85,'Família BBB','Proveïdor BBB','2022-06-10','2023-06-10',10.5,'Reemplaçat per una versió millorada'),(55,'Producte CCC','unit',175,'Família CCC','Proveïdor CCC','2022-07-15','2023-07-15',22.8,'Baixa sostenibilitat econòmica'),(56,'Producte DDD','box',60,'Família DDD','Proveïdor DDD','2022-08-20','2023-08-20',6.2,'Error de producció'),(57,'Producte EEE','kg',210,'Família EEE','Proveïdor EEE','2022-09-25','2023-09-25',13.5,'Reducció de linventari'),(58,'Producte FFF','l',125,'Família FFF','Proveïdor FFF','2022-10-30','2023-10-30',11.8,'Canvi de proveïdor per millora de qualitat'),(59,'Producte GGG','unit',135,'Família GGG','Proveïdor GGG','2022-11-05','2023-11-05',29.2,'Error en la documentació tècnica'),(60,'Producte HHH','box',195,'Família HHH','Proveïdor HHH','2022-12-10','2023-12-10',8.4,'Retirat per motius legals');
/*!40000 ALTER TABLE `referencia` ENABLE KEYS */;
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
