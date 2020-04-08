CREATE TABLE `core_guide_file` (
	`GUIDE_ID` CHAR(36) NOT NULL,
	`DOCUMENT` LONGTEXT NULL COMMENT '授权xml文件',
	PRIMARY KEY (`GUIDE_ID`)
)
COMMENT='授权文件'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
INSERT INTO `core_menu_url_info` VALUES ('c6cb93c9-b87f-11e7-96df-64510645b30p', '授权', 'guide', '/model/core/guide/index.jsp', NULL, NULL);
