CREATE TABLE `core_dic_info` (
	`DIC_ID` CHAR(36) NOT NULL,
	`DIC_NAME` VARCHAR(100) NOT NULL COMMENT '字典类型',
	`DIC_CODE` VARCHAR(50) NOT NULL COMMENT '字典编码',
	`DIC_DES` VARCHAR(50) NOT NULL COMMENT '字典描述',
	`SYS_TIME` DATETIME NOT NULL COMMENT '编制时间',
	`MEMBER_ID` CHAR(36) NOT NULL COMMENT '编制人，对应core_member_info表主键',
	PRIMARY KEY (`DIC_ID`)
)
COMMENT='数据字典'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `core_dic_info_detail` (
	`DETAIL_ID` CHAR(36) NOT NULL,
	`DIC_ID` CHAR(36) NOT NULL,
	`DETAIL_NAME` VARCHAR(100) NOT NULL COMMENT '详细类型名称',
	`DETAIL_CODE` VARCHAR(50) NOT NULL,
	`DETAIL_VALUE` VARCHAR(50) NOT NULL COMMENT '值',
	`DETAIL_LEVEL` INT(11) NOT NULL COMMENT '顺序',
	`COMMENT` VARCHAR(1000) NULL DEFAULT NULL COMMENT '备注',
	`IS_VALID` BIT(1) NOT NULL COMMENT '是否有效',
	PRIMARY KEY (`DETAIL_ID`)
)
COMMENT='数据字典明细表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


INSERT INTO `core_menu_url_info` VALUES ('d5cb93c9-b87f-11e7-96df-64510645b30p', '数据字典', 'dicinfo', '/model/core/dicinfo/index.jsp', NULL, NULL);
