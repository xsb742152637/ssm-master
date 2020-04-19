CREATE TABLE `core_member_archives` (
	`MEMBER_ID` CHAR(36) NOT NULL,
	`PHOTO` LONGBLOB NULL COMMENT '头像',
	PRIMARY KEY (`MEMBER_ID`)
)
COMMENT='人员档案'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

ALTER TABLE `core_member_info`
	DROP COLUMN `PHOTO`;

INSERT INTO `core_menu_url_info` VALUES ('260c7fad-94fa-4e41-a389-89a6b0946054', '个人信息', 'memberarchives', '/model/core/memberarchives/index.jsp', NULL, '2020-04-19 20:47:01');
