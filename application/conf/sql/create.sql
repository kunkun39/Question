SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `name` varchar(120) default NULL,
  `contactway` varchar(255) default '',
  `username` varchar(48) default '',
  `password` varchar(48) default '',
  `enabled` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `app_description`;
CREATE TABLE `app_description` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `description` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



DROP TABLE IF EXISTS `examination`;
CREATE TABLE `examination` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `title` varchar(255) default '',
  `description` text,
  `examination_type` varchar(10) default 'TVBOX_ONLY',
  `published` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `sequence` int(11) default 1,
  `title` varchar(255) default '',
  `question_type` varchar(10) default 'MUTI',
  `examination_id` int(11) default NULL,
  FOREIGN KEY (`examination_id`) REFERENCES examination (`id`),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `sequence` varchar(1) default 'A',
  `result` varchar(100) default '',
  `answer_times` int(11) default 0,
  `question_id` int(11) default NULL,
  FOREIGN KEY (`question_id`) REFERENCES question (`id`),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `objective_answer`;
CREATE TABLE `objective_answer` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `result` text,
  `question_id` int(11) default NULL,
  FOREIGN KEY (`question_id`) REFERENCES question (`id`),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



DROP TABLE IF EXISTS `client_info`;
CREATE TABLE `client_info` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `mac_address` varchar(17) default '',
  `experience` int(11) default 0,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `client_result`;
CREATE TABLE `client_result` (
  `id` int(11) NOT NULL auto_increment,
  `timestamp` timestamp default CURRENT_TIMESTAMP,
  `mac_address` varchar(17) default '',
  `result` text,
  `examination_id` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS=1;