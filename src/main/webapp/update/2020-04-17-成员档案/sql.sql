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
