/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.26 : Database - scnu_salary
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`scnu_salary` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `scnu_salary`;

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `depId` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门Id',
  `depName` varchar(32) NOT NULL COMMENT '部门名称',
  `memberCount` int(11) NOT NULL DEFAULT '0' COMMENT '部门成员人数',
  PRIMARY KEY (`depId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `department` */

insert  into `department`(`depId`,`depName`,`memberCount`) values (1,'学院领导',1),(2,'软件工程系',1),(3,'数学教学部',3),(4,'学院办公室',0),(5,'学院实验中心',0),(6,'离职退休人员',0);

/*Table structure for table `pay` */

DROP TABLE IF EXISTS `pay`;

CREATE TABLE `pay` (
  `payId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(32) NOT NULL,
  `payTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `postWage` decimal(10,3) unsigned DEFAULT '0.000',
  `performWage` decimal(10,3) unsigned DEFAULT '0.000',
  `tempWage` decimal(10,3) unsigned DEFAULT '0.000',
  `extra` decimal(10,3) unsigned DEFAULT '0.000',
  `lessonWage` decimal(10,3) unsigned DEFAULT '0.000',
  `manageWage` decimal(10,3) unsigned DEFAULT '0.000',
  `manageSubsidy` decimal(10,3) unsigned DEFAULT '0.000',
  `postscript` varchar(64) DEFAULT NULL,
  `result` decimal(10,3) DEFAULT '0.000' COMMENT '发放的总的工资',
  PRIMARY KEY (`payId`),
  KEY `userId` (`userId`),
  CONSTRAINT `pay_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `pay` */

insert  into `pay`(`payId`,`userId`,`payTime`,`postWage`,`performWage`,`tempWage`,`extra`,`lessonWage`,`manageWage`,`manageSubsidy`,`postscript`,`result`) values (2,'1','2020-03-01 22:40:16','2.000','0.000','0.000','0.000','0.000','0.000','0.000','迟到扣工资','0.000'),(3,'1','2020-01-01 22:40:48','3.000','0.000','0.000','0.000','0.000','0.000','0.000',NULL,'0.000'),(4,'1','2019-12-01 22:41:22','4.000','0.000','0.000','0.000','0.000','0.000','0.000',NULL,'0.000'),(5,'1','2019-06-01 22:41:38','5.000','0.000','0.000','0.000','0.000','0.000','0.000',NULL,'0.000'),(6,'1','2019-05-01 22:42:03','6.000','0.000','0.000','0.000','0.000','0.000','0.000',NULL,'0.000'),(7,'1','2019-04-01 22:42:18','6.000','0.000','0.000','0.000','0.000','0.000','0.000',NULL,'0.000'),(13,'3','2020-04-04 13:56:55','1500.000','600.000','0.000','0.000','8400.000','0.000','0.000','高数','10500.000'),(14,'1','2020-04-04 13:57:11','1500.000','600.000','0.000','0.000','4200.000','0.000','0.000','加油哦！！！come on','6300.000'),(15,'2','2020-04-04 13:58:29','2000.000','1100.000','100.500','100.660','0.000','12600.000','0.000','领导','15901.160'),(16,'4','2020-04-04 17:16:59','1500.000','600.000','0.900','0.000','4200.000',NULL,NULL,'','6300.000');

/*Table structure for table `salary_args` */

DROP TABLE IF EXISTS `salary_args`;

CREATE TABLE `salary_args` (
  `title` varchar(32) NOT NULL,
  `postWage` decimal(10,3) DEFAULT NULL,
  `performWage` decimal(10,3) DEFAULT NULL,
  `titleFactor` decimal(10,3) DEFAULT NULL,
  `perLessonWage` decimal(10,3) DEFAULT NULL,
  PRIMARY KEY (`title`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `salary_args` */

insert  into `salary_args`(`title`,`postWage`,`performWage`,`titleFactor`,`perLessonWage`) values ('其他','1000.000','500.000','1.000','35.000'),('副教授','1800.000','800.000','1.300','35.000'),('副院长/副书记','1800.000','900.000','1.800','35.000'),('助教','1200.000','500.000','1.000','35.000'),('教授','2000.000','1000.000','1.500','35.000'),('系/部/中心主任','1500.000','700.000','1.500','35.000'),('系/部/中心副主任','1200.000','600.000','1.200','35.000'),('讲师','1500.000','600.000','1.200','35.000'),('院长/书记','2000.000','1100.000','2.000','35.000');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` varchar(32) NOT NULL COMMENT '工号（尚未添加唯一约束）',
  `password` varchar(32) NOT NULL DEFAULT '123456',
  `userName` varchar(16) NOT NULL,
  `sex` char(2) NOT NULL DEFAULT '男',
  `depId` int(11) unsigned NOT NULL COMMENT '部门的Id',
  `loginAuth` varchar(16) NOT NULL DEFAULT '教师' COMMENT '登录权限',
  `proTitle` varchar(16) DEFAULT NULL COMMENT '职称',
  `post` varchar(16) DEFAULT NULL COMMENT '职务（管理岗）',
  `salaryType` varchar(16) NOT NULL DEFAULT '专任教师' COMMENT '发放工资的类型',
  `email` varchar(32) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`),
  KEY `depId` (`depId`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`userId`,`password`,`userName`,`sex`,`depId`,`loginAuth`,`proTitle`,`post`,`salaryType`,`email`,`phone`) values ('1','123','a','男',2,'财务员','讲师','其他','专任教师','1127664027@qq.com','18111111111'),('2','2','b','女',1,'教师','教授','院长/书记','非专任教师','',''),('3','123456','翁文','男',3,'教师','讲师','其他','专任教师','',''),('4','123456','王小明','男',4,'专任教师','副教授','其他','专任教师','1127664027@qq.com',''),('5','123456','李华','男',3,'教师','讲师','其他','专任教师','',''),('6','123456','黎语冰','男',6,'教师','教授','院长/书记','非专任教师','',''),('7','123456','测试','男',1,'教师','讲师','其他','专任教师','',''),('8','123456','测试2','男',1,'教师','讲师','其他','专任教师','','');

/*Table structure for table `workload` */

DROP TABLE IF EXISTS `workload`;

CREATE TABLE `workload` (
  `workloadId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(32) NOT NULL,
  `term` char(16) NOT NULL,
  `lessonHour` int(10) unsigned zerofill DEFAULT NULL,
  `attendCnt` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`workloadId`),
  KEY `userId` (`userId`),
  CONSTRAINT `workload_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `workload` */

insert  into `workload`(`workloadId`,`userId`,`term`,`lessonHour`,`attendCnt`) values (1,'1','2019-2020（2）',0000000100,0000000050),(2,'1','2019-2020（1）',0000000200,0000000200),(4,'3','2019-2020（2）',0000000100,0000000120),(5,'2','2019-2020（2）',0000000000,0000000000),(6,'4','2019-2020（2）',0000000100,0000000060),(7,'7','2019-2020（2）',0000000100,0000000060);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
