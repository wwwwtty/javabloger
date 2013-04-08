
/*Table structure for table `novel_article` */

DROP TABLE IF EXISTS `novel_article`;

CREATE TABLE `novel_article` (
  `art_id` 			int		NOT NULL 	PRIMARY KEY AUTO_INCREMENT,
  `art_author` 				varchar(255) 	DEFAULT NULL,
  `click_totle` 			int				DEFAULT NULL,
  `click_week` 				int				DEFAULT NULL,
  `click_month` 			int				DEFAULT NULL,
  `collected` 				int				DEFAULT NULL,
  `create_time` 			datetime 		DEFAULT NULL,
  `art_desc` 				text		 	DEFAULT NULL,
  `last_update_time` 		datetime 		DEFAULT NULL,
  `art_name` 				varchar(255) 	DEFAULT NULL,
  `recommend_point_month` 	int				DEFAULT NULL,
  `recommend_point_totle` 	int				DEFAULT NULL,
  `recommend_point_week`	int				DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `novel_article` */

/*Table structure for table `novel_category` */

DROP TABLE IF EXISTS `novel_category`;

CREATE TABLE `novel_category` (
  `cat_id` 		int(11) NOT NULL  PRIMARY KEY AUTO_INCREMENT,
  `cat_name` 	varchar(255) NOT NULL,
  UNIQUE KEY `uc_novel_category_2` (`cat_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `novel_category` */

/*Table structure for table `novel_chapter` */

DROP TABLE IF EXISTS `novel_chapter`;

CREATE TABLE `novel_chapter` (
  `ch_id` 		int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `art_id` 		int DEFAULT NULL,
  `content` 	text,
  `create_time` datetime DEFAULT NULL,
  `seq_no` 		int DEFAULT NULL,
  `reference` 	varchar(255) DEFAULT NULL,
  `section` 	varchar(200) DEFAULT NULL,
  `title` 		varchar(200) DEFAULT NULL,
  `ch_url` 		varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
