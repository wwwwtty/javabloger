/*
SQLyog Enterprise - MySQL GUI v8.14 
MySQL - 5.0.67-community-nt : Database - cms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cms`;

/*Table structure for table `cms_category` */

DROP TABLE IF EXISTS `cms_category`;

CREATE TABLE `cms_category` (
  `categoryId` int(11) NOT NULL auto_increment,
  `createUserID` int(11) default NULL COMMENT '创建人ID',
  `Categoryname` varchar(20) default NULL COMMENT '栏目名称',
  `categoryDesc` varchar(100) default NULL COMMENT '栏目描述',
  `code` varchar(20) default NULL COMMENT '栏目编码',
  `invalid` char(1) default '0' COMMENT '是否已被关闭',
  `grade` varchar(20) default NULL COMMENT '栏目分级(通过编码的形式)',
  PRIMARY KEY  (`categoryId`),
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='新闻分类(栏目)';

/*Data for the table `cms_category` */

insert  into `cms_category`(`categoryId`,`createUserID`,`Categoryname`,`categoryDesc`,`code`,`invalid`,`grade`) values (9,NULL,'每一天','第二张页面','second','1',NULL),(7,NULL,'首页','第一张页面','index','0',NULL),(14,NULL,'测试三','第三张页面','third','1',NULL);

/*Table structure for table `cms_category_news` */

DROP TABLE IF EXISTS `cms_category_news`;

CREATE TABLE `cms_category_news` (
  `categoryId` int(11) default NULL,
  `newsID` int(11) default NULL,
  KEY `FK_Reference_4` (`categoryId`),
  KEY `FK_Reference_5` (`newsID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新闻与栏目的中间表';

/*Data for the table `cms_category_news` */

/*Table structure for table `cms_comment` */

DROP TABLE IF EXISTS `cms_comment`;

CREATE TABLE `cms_comment` (
  `comment_id` int(11) NOT NULL auto_increment COMMENT 'ID',
  `newsID` int(11) default NULL COMMENT '评论新闻IDeas',
  `createUserID` int(11) default NULL COMMENT '创建人IDeas',
  `title` varchar(100) default NULL COMMENT '标题',
  `comment` text COMMENT '评论内容',
  `createTime` datetime default NULL COMMENT '创建时间',
  `showName` varchar(20) default NULL COMMENT '显示名称',
  PRIMARY KEY  (`comment_id`),
  KEY `FK_Reference_11` (`createUserID`),
  KEY `FK_Reference_8` (`newsID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新闻评论';

/*Data for the table `cms_comment` */

/*Table structure for table `cms_news` */

DROP TABLE IF EXISTS `cms_news`;

CREATE TABLE `cms_news` (
  `newsID` int(11) NOT NULL auto_increment,
  `createUserID` int(11) default NULL COMMENT '创建人ID',
  `title` varchar(100) default NULL COMMENT '标题',
  `subTitle` varchar(300) default NULL COMMENT '副标题',
  `createTime` datetime default NULL COMMENT '创建时间',
  `publishTime` datetime default NULL COMMENT '发布时间',
  `isDrag` char(1) default NULL COMMENT '是否是草稿',
  `source` varchar(50) default NULL COMMENT '消息来源',
  `publishAuthor` varchar(50) default NULL COMMENT '发布时显示的作者',
  PRIMARY KEY  (`newsID`),
  KEY `FK_Reference_9` (`createUserID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新闻表';

/*Data for the table `cms_news` */

/*Table structure for table `cms_news_content` */

DROP TABLE IF EXISTS `cms_news_content`;

CREATE TABLE `cms_news_content` (
  `contentId` int(11) NOT NULL,
  `newsID` int(11) default NULL,
  `page_Index` int(11) default NULL,
  `content` tinytext,
  PRIMARY KEY  (`contentId`),
  KEY `FK_Reference_1` (`newsID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新闻详细内容';

/*Data for the table `cms_news_content` */

/*Table structure for table `cms_property` */

DROP TABLE IF EXISTS `cms_property`;

CREATE TABLE `cms_property` (
  `key` varchar(20) default NULL COMMENT '扩展列名',
  `value` varchar(50) default NULL COMMENT '扩展值',
  `keydesc` varchar(100) default NULL COMMENT '扩展点描述',
  `foriegnID` int(11) NOT NULL COMMENT '扩展表ID',
  `extend` varchar(40) NOT NULL COMMENT '扩展来源'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='(新闻,栏目)扩展属性表';

/*Data for the table `cms_property` */

/*Table structure for table `site_page` */

DROP TABLE IF EXISTS `site_page`;

CREATE TABLE `site_page` (
  `siteId` int(11) NOT NULL,
  `pageId` int(11) NOT NULL,
  `name` varchar(20) default NULL,
  `namespace` varchar(50) default NULL,
  `type` varchar(20) default NULL,
  `code` varchar(20) default NULL,
  PRIMARY KEY  (`pageId`),
  KEY `FK_Reference_2` (`siteId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='站里面的一个页面';

/*Data for the table `site_page` */

/*Table structure for table `site_website` */

DROP TABLE IF EXISTS `site_website`;

CREATE TABLE `site_website` (
  `siteId` int(11) NOT NULL,
  `siteName` varchar(20) default NULL,
  `siteDesc` varchar(100) default NULL,
  `code` varchar(20) default NULL,
  PRIMARY KEY  (`siteId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='代表一个WEB应用站点';

/*Data for the table `site_website` */

/*Table structure for table `user_user` */

DROP TABLE IF EXISTS `user_user`;

CREATE TABLE `user_user` (
  `userID` int(11) NOT NULL,
  `username` varchar(20) default NULL,
  `password` varchar(20) default NULL,
  `realName` varchar(20) default NULL,
  `sex` char(1) default NULL,
  `showName` varchar(50) default NULL,
  PRIMARY KEY  (`userID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户管理';

/*Data for the table `user_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
