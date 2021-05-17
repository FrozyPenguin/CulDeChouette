-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: May 17, 2021 at 04:11 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cul_de_chouette`
--

-- --------------------------------------------------------

--
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
CREATE TABLE IF NOT EXISTS `action` (
  `id_partie` int(11) NOT NULL COMMENT 'identifiant de la partie',
  `tour` int(11) NOT NULL COMMENT 'numéro du tour',
  `chouette1` tinyint(4) DEFAULT NULL COMMENT 'valeur du dé chouette 1',
  `chouette2` tinyint(4) DEFAULT NULL COMMENT 'valeur du dé chouette 2',
  `cul` tinyint(4) DEFAULT NULL COMMENT 'valeur du dé cul',
  PRIMARY KEY (`id_partie`,`tour`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `action`
--

INSERT INTO `action` (`id_partie`, `tour`, `chouette1`, `chouette2`, `cul`) VALUES
(2, 1, 2, 3, 1),
(2, 2, 3, 2, 5),
(2, 3, 4, 1, 3),
(2, 4, 1, 1, 5),
(2, 5, 6, 4, 2),
(2, 6, 3, 2, 6),
(2, 7, 3, 1, 2),
(2, 8, 5, 3, 3),
(2, 9, 6, 5, 2),
(2, 10, 5, 5, 4),
(2, 11, 4, 2, 3),
(2, 12, 6, 2, 3),
(2, 13, 2, 2, 6),
(2, 14, 4, 5, 5),
(2, 15, 1, 2, 2),
(2, 16, 4, 1, 2),
(2, 17, 3, 6, 3),
(2, 18, 2, 1, 4),
(2, 19, 5, 2, 3),
(2, 20, 5, 5, 6),
(2, 21, 3, 3, 6),
(2, 22, 3, 6, 4),
(2, 23, 1, 6, 4),
(2, 24, 3, 6, 4),
(2, 25, 2, 3, 5),
(2, 26, 5, 4, 6),
(2, 27, 1, 3, 2),
(2, 28, 6, 2, 1),
(2, 29, 5, 4, 5),
(2, 30, 6, 4, 5),
(2, 31, 2, 1, 3),
(2, 32, 5, 3, 4),
(2, 33, 2, 6, 3),
(2, 34, 2, 5, 1),
(2, 35, 3, 6, 2),
(2, 36, 2, 4, 3),
(2, 37, 3, 3, 6),
(2, 38, 3, 1, 2),
(2, 39, 2, 2, 5),
(2, 40, 1, 1, 2),
(2, 41, 3, 6, 6),
(2, 42, 6, 2, 6),
(2, 43, 5, 1, 5),
(2, 44, 5, 4, 6),
(2, 45, 3, 1, 4),
(2, 46, 6, 3, 5),
(2, 47, 5, 2, 1),
(2, 48, 4, 2, 4),
(2, 49, 5, 5, 3),
(2, 50, 1, 6, 1),
(2, 51, 5, 4, 3),
(2, 52, 1, 4, 3),
(2, 53, 6, 3, 3),
(2, 54, 6, 3, 3),
(2, 55, 2, 5, 5),
(2, 56, 6, 5, 2),
(2, 57, 1, 3, 1),
(2, 58, 4, 3, 1),
(2, 59, 2, 5, 3),
(2, 60, 5, 6, 3),
(2, 61, 4, 6, 6),
(2, 62, 2, 4, 1),
(2, 63, 4, 3, 3),
(2, 64, 3, 2, 2),
(2, 65, 4, 1, 1),
(2, 66, 4, 2, 6),
(2, 67, 6, 1, 1),
(2, 68, 2, 4, 3),
(2, 69, 4, 1, 3),
(2, 70, 1, 6, 5),
(2, 71, 5, 6, 3),
(2, 72, 2, 6, 2),
(2, 73, 6, 2, 6),
(2, 74, 2, 3, 1),
(2, 75, 2, 6, 5),
(2, 76, 6, 4, 4),
(2, 77, 4, 3, 4),
(2, 78, 6, 2, 1),
(2, 79, 1, 1, 3),
(2, 80, 3, 5, 6),
(2, 81, 3, 6, 5),
(2, 82, 4, 3, 1),
(2, 83, 6, 6, 3),
(2, 84, 5, 5, 3),
(2, 85, 4, 4, 6),
(2, 86, 6, 6, 3),
(2, 87, 1, 2, 3),
(2, 88, 4, 5, 4),
(2, 89, 2, 6, 4),
(2, 90, 3, 1, 5),
(2, 91, 4, 5, 5),
(2, 92, 4, 3, 4),
(2, 93, 1, 2, 2),
(2, 94, 2, 5, 3),
(2, 95, 6, 5, 3),
(2, 96, 6, 5, 4),
(2, 97, 1, 3, 5),
(2, 98, 3, 5, 6),
(2, 99, 6, 2, 3),
(2, 100, 3, 6, 6),
(2, 101, 2, 3, 1),
(2, 102, 1, 5, 1),
(2, 103, 3, 4, 4),
(2, 104, 6, 2, 1),
(2, 105, 6, 4, 3),
(2, 106, 5, 4, 2),
(2, 107, 6, 3, 2),
(2, 108, 6, 5, 3),
(2, 109, 2, 3, 4),
(2, 110, 6, 1, 4),
(2, 111, 6, 6, 3),
(2, 112, 1, 4, 4),
(2, 113, 6, 2, 5),
(2, 114, 4, 4, 2),
(2, 115, 6, 5, 2),
(2, 116, 5, 5, 6),
(2, 117, 6, 6, 4),
(2, 118, 1, 4, 4),
(2, 119, 6, 4, 6),
(2, 120, 6, 3, 5),
(2, 121, 1, 1, 5),
(2, 122, 6, 5, 4),
(2, 123, 3, 4, 6),
(2, 124, 3, 4, 5),
(2, 125, 4, 5, 6),
(2, 126, 6, 1, 1),
(2, 127, 4, 1, 2),
(2, 128, 1, 6, 3),
(2, 129, 6, 6, 1),
(2, 130, 5, 6, 3),
(2, 131, 4, 3, 1),
(2, 132, 3, 5, 2),
(2, 133, 2, 3, 2),
(2, 134, 4, 2, 6),
(2, 135, 2, 3, 6),
(2, 136, 4, 3, 4),
(2, 137, 6, 3, 4),
(2, 138, 1, 3, 6),
(2, 139, 1, 3, 1),
(2, 140, 6, 2, 2),
(2, 141, 1, 3, 4),
(2, 142, 5, 6, 2),
(2, 143, 1, 3, 5),
(2, 144, 1, 2, 5),
(2, 145, 3, 3, 3),
(2, 146, 1, 6, 2),
(2, 147, 5, 1, 4),
(2, 148, 1, 2, 4),
(2, 149, 4, 4, 3),
(2, 150, 1, 6, 1),
(2, 151, 2, 5, 1),
(2, 152, 3, 5, 5),
(2, 153, 5, 1, 3),
(2, 154, 3, 3, 5),
(2, 155, 2, 6, 6),
(2, 156, 3, 3, 4),
(2, 157, 1, 6, 3),
(2, 158, 4, 3, 2),
(2, 159, 6, 5, 2),
(2, 160, 2, 3, 5),
(2, 161, 4, 4, 6),
(2, 162, 4, 1, 1),
(2, 163, 5, 1, 6),
(2, 164, 6, 6, 5),
(2, 165, 6, 5, 6),
(2, 166, 4, 5, 6),
(2, 167, 5, 3, 2),
(2, 168, 5, 3, 1),
(2, 169, 6, 5, 1),
(2, 170, 6, 6, 3),
(2, 171, 5, 5, 1),
(2, 172, 1, 2, 3),
(8, 1, 3, 6, 6),
(8, 2, 5, 4, 2),
(8, 3, 1, 4, 3),
(8, 4, 3, 1, 3),
(8, 5, 1, 2, 6),
(8, 6, 2, 1, 4),
(8, 7, 4, 6, 4),
(8, 8, 3, 2, 6),
(8, 9, 2, 1, 3),
(8, 10, 6, 6, 5),
(8, 11, 2, 5, 6),
(8, 12, 2, 4, 3),
(8, 13, 1, 3, 5),
(8, 14, 5, 6, 2),
(8, 15, 3, 4, 3),
(8, 16, 3, 5, 6),
(8, 17, 5, 4, 2),
(8, 18, 5, 4, 6),
(8, 19, 4, 5, 2),
(8, 20, 5, 2, 5),
(8, 21, 2, 1, 2),
(8, 22, 2, 5, 6),
(8, 23, 2, 6, 5),
(8, 24, 6, 3, 3),
(8, 25, 6, 2, 6),
(8, 26, 3, 1, 1),
(8, 27, 1, 1, 6),
(8, 28, 3, 3, 6),
(8, 29, 2, 4, 4),
(8, 30, 3, 2, 5),
(8, 31, 5, 6, 6),
(8, 32, 4, 5, 2),
(8, 33, 5, 3, 3),
(8, 34, 2, 4, 6);

-- --------------------------------------------------------

--
-- Table structure for table `interraction`
--

DROP TABLE IF EXISTS `interraction`;
CREATE TABLE IF NOT EXISTS `interraction` (
  `id_partie` int(11) NOT NULL COMMENT 'identifiant de la partie',
  `tour` int(11) NOT NULL COMMENT 'numéro du tour',
  `effet` char(1) DEFAULT NULL COMMENT 'POSITIF ou NEGATIF',
  `joueur_affecte` varchar(25) DEFAULT NULL COMMENT 'joueur concerné',
  PRIMARY KEY (`id_partie`,`tour`),
  KEY `FK_joueur_TO_interraction` (`joueur_affecte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `interraction`
--

INSERT INTO `interraction` (`id_partie`, `tour`, `effet`, `joueur_affecte`) VALUES
(2, 1, 'N', 'NeiNei'),
(2, 7, 'N', 'Allyta'),
(2, 11, 'N', 'Allyta'),
(2, 26, 'N', 'Allyta'),
(2, 27, 'N', 'NeiNei'),
(2, 30, 'N', 'Allyta'),
(2, 32, 'N', 'Allyta'),
(2, 36, 'N', 'Allyta'),
(2, 38, 'N', 'NeiNei'),
(2, 44, 'N', 'Allyta'),
(2, 51, 'N', 'Allyta'),
(2, 68, 'N', 'Allyta'),
(2, 74, 'N', 'Allyta'),
(2, 96, 'N', 'Allyta'),
(2, 101, 'N', 'Allyta'),
(2, 109, 'N', 'Allyta'),
(2, 122, 'N', 'Allyta'),
(2, 124, 'N', 'Allyta'),
(2, 125, 'N', 'Allyta'),
(2, 158, 'N', 'Allyta'),
(2, 166, 'N', 'Allyta'),
(8, 12, 'N', 'Karadoc'),
(8, 18, 'N', 'Karadoc');

-- --------------------------------------------------------

--
-- Table structure for table `joueur`
--

DROP TABLE IF EXISTS `joueur`;
CREATE TABLE IF NOT EXISTS `joueur` (
  `pseudonyme` varchar(25) NOT NULL COMMENT 'pseudonyme du joueur',
  `email` varchar(254) NOT NULL COMMENT 'email du joueur',
  `mot_de_passe` varchar(25) NOT NULL COMMENT 'mot de passe du joueur',
  `date_naissance` date DEFAULT NULL COMMENT 'date de naissance pour calcul dâge',
  `sexe` char(3) DEFAULT NULL COMMENT 'sexe du joueur',
  `ville` varchar(25) DEFAULT NULL COMMENT 'ville du joueur',
  PRIMARY KEY (`pseudonyme`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `joueur`
--

INSERT INTO `joueur` (`pseudonyme`, `email`, `mot_de_passe`, `date_naissance`, `sexe`, `ville`) VALUES
('Allyta', 'belemail@email.fr', 'abcd', '2021-05-03', 'F', 'Agen'),
('Herve', 'mail@mail.fr', 'abcd', '2021-05-05', 'H', 'Dux'),
('Karadoc', 'email@email.fr', 'abcd', '2021-05-07', 'H', 'Vannes'),
('Le tavernier', 'email@email.fr', 'abcd', '2021-05-06', 'H', 'Ville'),
('NeiNei', 'nathan.bechtel@outlook.com', 'abcd', '2021-04-28', 'H', 'Pau'),
('Nonna', 'email@email.fr', 'abcd', '2021-05-05', 'F', 'Dux'),
('Perceval', 'email@email.fr', 'abcd', '2021-05-05', 'H', 'Galles');

-- --------------------------------------------------------

--
-- Table structure for table `partie`
--

DROP TABLE IF EXISTS `partie`;
CREATE TABLE IF NOT EXISTS `partie` (
  `id_partie` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identifiant de la partie',
  `objectif` int(11) NOT NULL DEFAULT 343 COMMENT 'score à atteindre',
  `date_debut` datetime DEFAULT NULL COMMENT 'date de début de la partie',
  `duree` float DEFAULT NULL COMMENT 'durée de la partie (minutes)',
  `hote` varchar(25) DEFAULT NULL COMMENT 'pseudonyme du joueur hote de la partie',
  `vainqueur` varchar(25) DEFAULT NULL COMMENT 'pseudonyme du vainqueur',
  PRIMARY KEY (`id_partie`),
  KEY `FK_joueur_TO_partie` (`vainqueur`),
  KEY `FK_joueur_TO_partie1` (`hote`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `partie`
--

INSERT INTO `partie` (`id_partie`, `objectif`, `date_debut`, `duree`, `hote`, `vainqueur`) VALUES
(1, 343, '2021-05-17 14:27:24', NULL, 'Allyta', NULL),
(2, 343, '2021-05-17 14:34:51', 263.935, 'Allyta', 'NeiNei'),
(8, 100, '2021-05-17 15:42:08', 43.702, 'Karadoc', 'Perceval');

-- --------------------------------------------------------

--
-- Table structure for table `resultats`
--

DROP TABLE IF EXISTS `resultats`;
CREATE TABLE IF NOT EXISTS `resultats` (
  `id_partie` int(11) NOT NULL COMMENT 'identifiant de la partie',
  `pseudonyme` varchar(25) NOT NULL COMMENT 'pseudonyme du joueur',
  `ordre` int(11) DEFAULT NULL COMMENT 'ordre du joueur dans la partie',
  `score` int(11) NOT NULL DEFAULT 0 COMMENT 'score du joueur à la partie',
  `suites_gagnees` int(11) DEFAULT NULL COMMENT 'nombre de suites remportées',
  `chouettes_velues_perdues` int(11) DEFAULT NULL COMMENT 'nombre de chouettes velues perdues',
  PRIMARY KEY (`id_partie`,`pseudonyme`),
  KEY `FK_joueur_TO_resultats` (`pseudonyme`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `resultats`
--

INSERT INTO `resultats` (`id_partie`, `pseudonyme`, `ordre`, `score`, `suites_gagnees`, `chouettes_velues_perdues`) VALUES
(2, 'Allyta', 1, 325, 0, 18),
(2, 'Le tavernier', 3, 50, 0, 0),
(2, 'NeiNei', 2, 348, 0, 3),
(8, 'Karadoc', 1, -10, 0, 2),
(8, 'Perceval', 2, 133, 0, 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `action`
--
ALTER TABLE `action`
  ADD CONSTRAINT `FK_partie_TO_action` FOREIGN KEY (`id_partie`) REFERENCES `partie` (`id_partie`);

--
-- Constraints for table `interraction`
--
ALTER TABLE `interraction`
  ADD CONSTRAINT `FK_action_TO_interraction` FOREIGN KEY (`id_partie`,`tour`) REFERENCES `action` (`id_partie`, `tour`),
  ADD CONSTRAINT `FK_joueur_TO_interraction` FOREIGN KEY (`joueur_affecte`) REFERENCES `joueur` (`pseudonyme`);

--
-- Constraints for table `partie`
--
ALTER TABLE `partie`
  ADD CONSTRAINT `FK_joueur_TO_partie` FOREIGN KEY (`vainqueur`) REFERENCES `joueur` (`pseudonyme`),
  ADD CONSTRAINT `FK_joueur_TO_partie1` FOREIGN KEY (`hote`) REFERENCES `joueur` (`pseudonyme`);

--
-- Constraints for table `resultats`
--
ALTER TABLE `resultats`
  ADD CONSTRAINT `FK_joueur_TO_resultats` FOREIGN KEY (`pseudonyme`) REFERENCES `joueur` (`pseudonyme`),
  ADD CONSTRAINT `FK_partie_TO_resultats` FOREIGN KEY (`id_partie`) REFERENCES `partie` (`id_partie`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
