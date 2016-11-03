CREATE SCHEMA IF NOT EXISTS `springdemo`;
USE `springdemo`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `date_of_birth` date NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `account` VALUES (1,'minhhuan','@huanvip@','Huan','Phan','1996-11-24',1),(2,'quangnnd','lazziness','Quang','Nguyen','1996-01-01',0),(3,'baoht','123456','Bao','Huynh','1996-01-01',0),(4,'danhlt','123456','Danh','Le','1996-01-01',1);
