/*
Navicat MySQL Data Transfer

Source Server         : This
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : project_status_tracking

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-04-29 09:41:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(45) DEFAULT NULL COMMENT '权限名称',
  `authority_description` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `authority_url` varchar(100) DEFAULT NULL COMMENT 'url链接',
  `authority_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='用户权限';

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', 'projects_read', '查看项目', '/projectsManagement/projects/projectsList.do', null);
INSERT INTO `authority` VALUES ('2', 'projects_create', '创建项目', '/projectsManagement/projects/addProject.do', null);
INSERT INTO `authority` VALUES ('3', 'projects_update', '修改项目', '/projectsManagement/projects/edit.do', null);
INSERT INTO `authority` VALUES ('4', 'projects_delete', '删除项目', '/projectsManagement/projects/deleteProject.do', null);
INSERT INTO `authority` VALUES ('5', 'projects_price_read', '查看项目报价', null, '项目管理');
INSERT INTO `authority` VALUES ('6', 'projects_export', '导出项目', '/projectsManagement/projects/export.do', null);
INSERT INTO `authority` VALUES ('7', 'project_detail_read', '查看项目详情', '/projectsManagement/projects/viewProject.do', null);
INSERT INTO `authority` VALUES ('8', 'cr_read', 'CR列表页面', '/projectsManagement/projects/Requirement/crList.do', 'CR');
INSERT INTO `authority` VALUES ('9', 'cr_create', '添加CR页面', '/projectsManagement/projects/Requirement/addCRList.do', 'CR');
INSERT INTO `authority` VALUES ('10', 'cr_update', '修改CR页面', '/projectsManagement/projects/Requirement/editCRList.do', 'CR');
INSERT INTO `authority` VALUES ('11', 'cr_delete', '删除CR', '/projectsManagement/projects/Requirement/deleteCR.do', 'CR');
INSERT INTO `authority` VALUES ('12', 'cr_price_read', '查看CR报价', '', 'CR');
INSERT INTO `authority` VALUES ('14', 'cr_detail_read', '查看CR详情', '/projectsManagement/projects/Requirement/viewCRList.do', 'CR');
INSERT INTO `authority` VALUES ('15', 'user_management', '用户列表', '/usersManagement/users/userList.do', '用户管理');
INSERT INTO `authority` VALUES ('16', 'logging_read', '查看日志', '/logsManagement/logging/loggingList.do', '日志管理');
INSERT INTO `authority` VALUES ('17', 'customer_create', '增加客户', '/projectsManagement/customers/addCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('18', 'customer_delete', '删除客户', '/projectsManagement/customers/deleteCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('19', 'customer_update', '修改客户', '/projectsManagement/customers/editCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('20', 'customer_view', '查看客户', '/projectsManagement/customers/viewCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('21', 'customer_read', '进入客户列表', '/projectsManagement/customers/customerList.do', '客户管理');
INSERT INTO `authority` VALUES ('22', 'get_projects', '查询项目列表', '/projectsManagement/projects/getProjects.do', '项目管理');
INSERT INTO `authority` VALUES ('24', 'add_comment', '增加CR评论', '/projectsManagement/projects/Requirement/addComment.do', 'CR');
INSERT INTO `authority` VALUES ('25', 'cr_download', 'CR附件下载', '/projectsManagement/projects/Requirement/downloadCR.do', 'CR');
INSERT INTO `authority` VALUES ('26', 'cr_add', '增加CR', '/projectsManagement/projects/Requirement/addCR.do', 'CR');
INSERT INTO `authority` VALUES ('27', 'cr_edit', '修改CR', '/projectsManagement/projects/Requirement/updateCR.do', 'CR');
INSERT INTO `authority` VALUES ('28', 'cr_view', '查询项目所有CR', '/projectsManagement/projects/Requirement/getAllCR.do', 'CR');
INSERT INTO `authority` VALUES ('29', 'get_customerInfo', '获取客户', '/projectsManagement/customers/getCustomerInfo.do', '客户管理');
INSERT INTO `authority` VALUES ('30', 'insert_customer', '增加客户功能', '/projectsManagement/customers/insertCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('31', 'update_customer', '修改客户功能', '/projectsManagement/customers/updateCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('32', 'get_customer', '查看客户详情', '/projectsManagement/customers/getCustomer.do', '客户管理');
INSERT INTO `authority` VALUES ('33', 'get_loggings', '获取日志详情', '/logsManagement/logging/getLoggings.do', '日志管理');
INSERT INTO `authority` VALUES ('34', 'download', '项目附件下载', '/projectsManagement/projects/download.do', '项目管理');
INSERT INTO `authority` VALUES ('35', 'add_projectComment', '项目增加评论', '/projectsManagement/projects/addProjectComment.do', '项目管理');
INSERT INTO `authority` VALUES ('36', 'get_projectInfo', '查询项目详情', '/projectsManagement/projects/getProjectInfo.do', '项目管理');
INSERT INTO `authority` VALUES ('37', 'update_project', '修改项目功能', '/projectsManagement/projects/updateProject.do', '项目管理');
INSERT INTO `authority` VALUES ('38', 'insert', '增加项目功能', '/projectsManagement/projects/insert.do', '项目管理');
INSERT INTO `authority` VALUES ('39', 'userList', '用户列表', '/usersManagement/users/userList.do', '用户管理');
INSERT INTO `authority` VALUES ('40', 'add_userList', '增加用户页面', '/usersManagement/users/addUserList.do', '用户管理');
INSERT INTO `authority` VALUES ('41', 'edit_psd', '修改密码', '/usersManagement/users/editPsd.do', '用户管理');
INSERT INTO `authority` VALUES ('42', 'edit_userList', '修改用户页面', '/usersManagement/users/editUserList.do', '用户管理');
INSERT INTO `authority` VALUES ('43', 'get_users', '查询用户功能', '/usersManagement/users/getUsers.do', '用户管理');
INSERT INTO `authority` VALUES ('44', 'check_psd', '密码验证', '/usersManagement/users/checkPsd.do', '用户管理');
INSERT INTO `authority` VALUES ('45', 'check_userName', '用户名验证', '/usersManagement/users/checkUserName.do', '用户管理');
INSERT INTO `authority` VALUES ('46', 'check_editUserName', '修改用户名验证', '/usersManagement/users/checkEditUserName.do', '用户管理');
INSERT INTO `authority` VALUES ('47', 'add_user', '增加用户功能', '/usersManagement/users/addUser.do', '用户管理');
INSERT INTO `authority` VALUES ('48', 'delete_user', '删除用户功能', '/usersManagement/users/deleteUser.do', '用户管理');
INSERT INTO `authority` VALUES ('49', 'update_user', '修改用户功能', '/usersManagement/users/updateUser.do', '用户管理');
INSERT INTO `authority` VALUES ('50', 'update_psd', '修改密码功能', '/usersManagement/users/updatePsd.do', '用户管理');
INSERT INTO `authority` VALUES ('51', 'index', '首页', '/index.do', null);
INSERT INTO `authority` VALUES ('52', 'finance_list', '财务管理页面', '/projectsManagement/finance/financeList.do', '项目管理');
INSERT INTO `authority` VALUES ('53', 'getFinances', '财务管理查询', '/projectsManagement/finance/getFinances.do', '项目管理');
INSERT INTO `authority` VALUES ('54', 'upateFinance', '更新财务信息页面', '/projectsManagement/finance/updateFinance.do', '项目管理');
INSERT INTO `authority` VALUES ('55', 'update_finance', '更新财务', '/projectsManagement/finance/update.do', '项目管理');
INSERT INTO `authority` VALUES ('56', 'projectManagerList', '项目经理列表', '/projectsManagement/projectManager/projectManagerList.do', '项目管理');
INSERT INTO `authority` VALUES ('57', 'get_projectManagerList', '查询项目经理列表', '/projectsManagement/projectManager/getProjectManagerLists.do', '项目管理');
INSERT INTO `authority` VALUES ('58', 'add_projectManager', '新增项目经理页面', '/projectsManagement/projectManager/addProjectManager.do', '项目管理');
INSERT INTO `authority` VALUES ('59', 'insert_projectManager', '新增项目经理', '/projectsManagement/projectManager/insertProjectManager.do', '项目管理');
INSERT INTO `authority` VALUES ('60', 'edit_projectManager', '修改项目经理页面', '/projectsManagement/projectManager/edit.do', '项目管理');
INSERT INTO `authority` VALUES ('61', 'update_projectManager', '修改项目经理', '/projectsManagement/projectManager/updateProjectManager.do', '项目管理');
INSERT INTO `authority` VALUES ('62', 'view_projectManager', '查看项目经理详情', '/projectsManagement/projectManager/view.do', '项目管理');
INSERT INTO `authority` VALUES ('63', 'add_projectManagerComment', '增加项目经理评论', '/projectsManagement/projectManager/addComment.do', '项目管理');
INSERT INTO `authority` VALUES ('64', 'delete_projectManager', '删除项目经理', '/projectsManagement/projectManager/deleteProjectManager.do', '项目管理');
INSERT INTO `authority` VALUES ('65', 'export_projectManager', '导出项目经理列表', '/projectsManagement/projectManager/export.do', '项目管理');
INSERT INTO `authority` VALUES ('66', 'implementManager', '实施经理页面', '/projectsManagement/implementManager/implementManagerList.do', '项目管理');
INSERT INTO `authority` VALUES ('67', 'get_implementManager', '实施经理列表', '/projectsManagement/implementManager/getImplementManagerLists.do', '项目管理');
INSERT INTO `authority` VALUES ('68', 'add_implementManager', '新增实施经理页面', '/projectsManagement/implementManager/addImplementManager.do', '项目管理');
INSERT INTO `authority` VALUES ('69', 'insert_implementManager', '新增实施经理', '/projectsManagement/implementManager/insertImplementManager.do', '项目管理');
INSERT INTO `authority` VALUES ('70', 'edit_implementManager', '编辑实施经理', '/projectsManagement/implementManager/edit.do', '项目管理');
INSERT INTO `authority` VALUES ('71', 'update_implementManager', '修改实施经理', '/projectsManagement/implementManager/updateImplementManager.do', '项目管理');
INSERT INTO `authority` VALUES ('72', 'delete_implementManager', '删除实施经理', '/projectsManagement/implementManager/deleteImplementManager.do', '项目管理');
INSERT INTO `authority` VALUES ('73', 'view_implementManager', '查看实施经理', '/projectsManagement/implementManager/view.do', '项目管理');
INSERT INTO `authority` VALUES ('74', 'add_implementManagerComment', '添加评论', '/projectsManagement/implementManager/addComment.do', '项目管理');
INSERT INTO `authority` VALUES ('75', 'outsourcingManage', '外包管理', '/projectsManagement/outsourcingManage/outsourcingManage.do', '项目管理');
INSERT INTO `authority` VALUES ('76', 'get_outsourcingManage', '外包管理列表', '/projectsManagement/outsourcingManage/getOutsourcingManageLists.do', '项目管理');
INSERT INTO `authority` VALUES ('77', 'add_outsourcingManage', '外包管理新增页面', '/projectsManagement/outsourcingManage/addOutsourcingManage.do', '项目管理');
INSERT INTO `authority` VALUES ('78', 'insert_outsourcingManage', '外包管理新增', '/projectsManagement/outsourcingManage/insertOutsourcingManage.do', '项目管理');
INSERT INTO `authority` VALUES ('79', 'edit_outsourcingManage', '外包管理编辑页面', '/projectsManagement/outsourcingManage/edit.do', '项目管理');
INSERT INTO `authority` VALUES ('80', 'update_outsourcingManage', '外包管理修改', '/projectsManagement/outsourcingManage/updateOutsourcingManage.do', '项目管理');
INSERT INTO `authority` VALUES ('81', 'view_outsourcingManage', '外包管理详情', '/projectsManagement/outsourcingManage/view.do', '项目管理');
INSERT INTO `authority` VALUES ('82', 'add_outsourcingManageComment', '新增外包管理评论', '/projectsManagement/outsourcingManage/addComment.do', '项目管理');
INSERT INTO `authority` VALUES ('83', 'delete_outsourcingManage', '删除外包管理', '/projectsManagement/outsourcingManage/deleteOutsourcingManage.do', '项目管理');
INSERT INTO `authority` VALUES ('84', 'chanceManageList', '机会管理', '/projectsManagement/chanceManage/chanceManage.do', '项目管理');
INSERT INTO `authority` VALUES ('85', 'get_chanceManageList', '机会管理列表', '/projectsManagement/chanceManage/getChanceManageLists.do', '项目管理');
INSERT INTO `authority` VALUES ('86', 'add_chanceManage', '新增机会管理页面', '/projectsManagement/chanceManage/addChanceManage.do', '项目管理');
INSERT INTO `authority` VALUES ('87', 'insert_chanceManage', '新增机会管理', '/projectsManagement/chanceManage/insertChanceManage.do', '项目管理');
INSERT INTO `authority` VALUES ('88', 'edit_chanceManage', '编辑机会管理页面', '/projectsManagement/chanceManage/edit.do', '项目管理');
INSERT INTO `authority` VALUES ('89', 'update_chanceManage', '修改机会管理', '/projectsManagement/chanceManage/updateChanceManage.do', '项目管理');
INSERT INTO `authority` VALUES ('90', 'view_chanceManage', '机会管理详情', '/projectsManagement/chanceManage/view.do', '项目管理');
INSERT INTO `authority` VALUES ('91', 'delete_chanceManage', '删除机会管理', '/projectsManagement/chanceManage/deleteChanceManage.do', '项目管理');
INSERT INTO `authority` VALUES ('92', 'add_financeReceive', '添加付款', '/projectsManagement/finance/addFinanceReceive.do', '项目管理');
INSERT INTO `authority` VALUES ('93', 'insert_financeReceive', '添加付款', '/projectsManagement/finance/addProjectFinancial.do', '项目管理');
INSERT INTO `authority` VALUES ('94', 'insert_financePayment', '添加收款', '/projectsManagement/finance/addFinancePayment.do', '项目管理');
INSERT INTO `authority` VALUES ('95', 'view_financereceive', '收款详情', '/projectsManagement/finance/viewFinanceReceive.do', '项目管理');
INSERT INTO `authority` VALUES ('96', 'view_financepayment', '付款详情', '/projectsManagement/finance/viewFinancePayment.do', '项目管理');
INSERT INTO `authority` VALUES ('97', 'customer_export', '客户列表导出', '/projectsManagement/customers/export.do', '项目管理');
INSERT INTO `authority` VALUES ('98', 'implementManager_export', '实施经理列表导出', '/projectsManagement/implementManager/export.do', '项目管理');
INSERT INTO `authority` VALUES ('99', 'outsourcingManage_export', '外包管理列表导出', '/projectsManagement/outsourcingManage/export.do', '项目管理');
INSERT INTO `authority` VALUES ('100', 'chanceManage_export', '机会管理列表导出', '/projectsManagement/chanceManage/export.do', '项目管理');
INSERT INTO `authority` VALUES ('101', 'finance_export', '财务管理列表导出', '/projectsManagement/finance/export.do', '项目管理');
INSERT INTO `authority` VALUES ('102', 'user_export', '用户列表导出', '/projectsManagement/users/export.do', '用户管理');
insert into authority values('103','add_customer_button','添加客户按钮',null,'客户管理');
INSERT INTO `authority` VALUES ('104', 'check_projectCode', '验证项目编号', '/projectsManagement/projects/checkProjectCode.do', '项目管理');
INSERT INTO `authority` VALUES ('105', 'check_projectCode', '验证项目编号', '/projectsManagement/projects/checkEditProjectCode.do', '项目管理');
INSERT INTO `authority` VALUES ('106', 'finsh_projectCode', '已完成的项目', '/projectsManagement/projects/getProjectsForFinsh.do', '项目管理');
INSERT INTO `authority` VALUES ('107', 'finsh_projectCodeList', '已完成的项目页面', '/projectsManagement/projects/getprojectForFinshList.do', '项目管理');
INSERT INTO `authority` VALUES ('108', 'export_projectCodeList', '已完成的项目导出', '/projectsManagement/projects/exportForFinsh.do', '项目管理');
-- ----------------------------
-- Table structure for `cr`
-- ----------------------------
DROP TABLE IF EXISTS `cr`;
CREATE TABLE `cr` (
  `cr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `cr_describe` varchar(200) NOT NULL COMMENT '需求描述',
  `estimated_workload` int(11) NOT NULL COMMENT '预估工作量（小时）',
  `presenter` varchar(45) NOT NULL COMMENT '提交人',
  `confirmor` varchar(45) NOT NULL COMMENT '确认人',
  `estimate_quotation` int(11) NOT NULL COMMENT '预估报价',
  `confirm_quotation` int(11) NOT NULL COMMENT '确认报价',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`cr_id`),
  KEY `cr_project_fk_idx` (`project_id`),
  CONSTRAINT `cr_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='change request(新增需求)';

-- ----------------------------
-- Records of cr
-- ----------------------------

-- ----------------------------
-- Table structure for `cr_comment`
-- ----------------------------
DROP TABLE IF EXISTS `cr_comment`;
CREATE TABLE `cr_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `cr_id` int(11) NOT NULL COMMENT 'crID',
  `critic` varchar(45) NOT NULL COMMENT '评论人',
  `comment` varchar(500) NOT NULL COMMENT '评论',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`comment_id`),
  KEY `cr_comment_cr_fk_idx` (`cr_id`),
  CONSTRAINT `cr_comment_cr_fk` FOREIGN KEY (`cr_id`) REFERENCES `cr` (`cr_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cr_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `cr_file`
-- ----------------------------
DROP TABLE IF EXISTS `cr_file`;
CREATE TABLE `cr_file` (
  `cr_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_path` varchar(200) NOT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file_size` int(11) NOT NULL,
  `content_type` varchar(200) NOT NULL,
  `file_name` varchar(200) NOT NULL,
  `cr_id` int(11) NOT NULL,
  PRIMARY KEY (`cr_file_id`),
  KEY `cr_file_cr_fk_idx` (`cr_id`),
  CONSTRAINT `cr_file_cr_fk` FOREIGN KEY (`cr_id`) REFERENCES `cr` (`cr_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='cr附件';

-- ----------------------------
-- Records of cr_file
-- ----------------------------

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `customer_title` varchar(60) NOT NULL COMMENT '客户头衔',
  `customer_name` varchar(60) NOT NULL,
  `customer_phone` varchar(45) NOT NULL COMMENT '客户电话',
  `company_address` varchar(100) NOT NULL COMMENT '公司地址',
  `company_phone` varchar(45) NOT NULL COMMENT '公司电话',
  `bank` varchar(200) NOT NULL COMMENT '开户银行',
  `payment_account` varchar(60) NOT NULL COMMENT '付款账号',
  `company_id_number` varchar(60) NOT NULL COMMENT '公司税号',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `logging`
-- ----------------------------
DROP TABLE IF EXISTS `logging`;
CREATE TABLE `logging` (
  `logging_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '操作时间',
  `action` varchar(200) NOT NULL COMMENT '操作记录',
  `operator` varchar(45) NOT NULL COMMENT '操作人',
  `user_id` int(11) NOT NULL,
  `event_type` int(11) NOT NULL COMMENT '事件类型(1:增加,2:删除,3:修改,4:导出,5:增加评论,6:登录,7修改密码)',
  `event_name` varchar(45) NOT NULL COMMENT '事件名称(增加,删除,修改,导出,查看)',
  `tables` varchar(45) NOT NULL COMMENT '记录操作的数据库表名',
  PRIMARY KEY (`logging_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_code` varchar(50) NOT NULL COMMENT '项目 编号',
  `project_name` varchar(60) NOT NULL COMMENT '项目名称',
  `project_manager` varchar(45) NOT NULL COMMENT '项目经理',
  `start_time` date NOT NULL COMMENT '开始时间',
  `medial_time` date NOT NULL COMMENT '内测时间',
  `lead_time` date NOT NULL COMMENT ' 交付时间',
  `project_quotation` double(10,2) NOT NULL COMMENT '项目报价',
  `customer_name` varchar(45) NOT NULL COMMENT '客户名称',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL COMMENT '创建者',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `project_status` varchar(45) NOT NULL COMMENT '项目状态：1商务、2制作、3修改、4完成、5流标',
  `project_type` varchar(45) NOT NULL COMMENT '项目类型：1视频制作、2平面设计、3活动会务、4环境布置、5多媒体布展、6新媒体开发、7云发布\n',
  `project_status_number` int(3) NOT NULL COMMENT '项目状态对应的数字',
  `project_type_number` int(3) NOT NULL COMMENT '项目类型对应的数字',
  PRIMARY KEY (`project_id`),
  KEY `project_user_fk_idx` (`creator_id`),
  CONSTRAINT `project_user_fk` FOREIGN KEY (`creator_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='项目';

-- ----------------------------
-- Table structure for `project_comment`
-- ----------------------------
DROP TABLE IF EXISTS `project_comment`;
CREATE TABLE `project_comment` (
  `projectcom_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `critic` varchar(45) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`projectcom_id`),
  KEY `project_comment_project_fk_idx` (`project_id`),
  CONSTRAINT `project_comment_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `project_file`
-- ----------------------------
DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file` (
  `project_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(200) NOT NULL,
  `file_path` varchar(200) NOT NULL,
  `content_type` varchar(200) NOT NULL,
  `file_size` int(11) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_file_id`),
  KEY `project_file_project_fk_idx` (`project_id`),
  CONSTRAINT `project_file_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='项目附件';

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL COMMENT '角色名称',
  `role_description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '管理员');
INSERT INTO `role` VALUES ('2', 'user', '普通用户');

-- ----------------------------
-- Table structure for `role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority` (
  `role_authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `authority_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_authority_id`),
  KEY `role_authority_role_fk_idx` (`role_id`),
  KEY `role_authority_authority_fk_idx` (`authority_id`),
  CONSTRAINT `role_authority_authority_fk` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`authority_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_authority_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- ----------------------------
-- Records of role_authority
-- ----------------------------
INSERT INTO `role_authority` VALUES ('1', '1', '1');
INSERT INTO `role_authority` VALUES ('2', '1', '2');
INSERT INTO `role_authority` VALUES ('3', '1', '3');
INSERT INTO `role_authority` VALUES ('4', '1', '4');
INSERT INTO `role_authority` VALUES ('5', '1', '5');
INSERT INTO `role_authority` VALUES ('6', '1', '6');
INSERT INTO `role_authority` VALUES ('7', '1', '7');
INSERT INTO `role_authority` VALUES ('8', '1', '8');
INSERT INTO `role_authority` VALUES ('9', '1', '9');
INSERT INTO `role_authority` VALUES ('10', '1', '10');
INSERT INTO `role_authority` VALUES ('11', '1', '11');
INSERT INTO `role_authority` VALUES ('13', '2', '1');
INSERT INTO `role_authority` VALUES ('14', '2', '7');
INSERT INTO `role_authority` VALUES ('15', '1', '16');
INSERT INTO `role_authority` VALUES ('17', '1', '18');
INSERT INTO `role_authority` VALUES ('18', '1', '19');
INSERT INTO `role_authority` VALUES ('19', '1', '20');
INSERT INTO `role_authority` VALUES ('20', '1', '14');
INSERT INTO `role_authority` VALUES ('21', '1', '15');
INSERT INTO `role_authority` VALUES ('22', '2', '3');
INSERT INTO `role_authority` VALUES ('23', '2', '4');
INSERT INTO `role_authority` VALUES ('24', '1', '12');
INSERT INTO `role_authority` VALUES ('25', '1', '17');
INSERT INTO `role_authority` VALUES ('26', '1', '21');
INSERT INTO `role_authority` VALUES ('27', '1', '22');
INSERT INTO `role_authority` VALUES ('29', '2', '8');
INSERT INTO `role_authority` VALUES ('30', '1', '24');
INSERT INTO `role_authority` VALUES ('31', '2', '24');
INSERT INTO `role_authority` VALUES ('32', '1', '25');
INSERT INTO `role_authority` VALUES ('33', '2', '25');
INSERT INTO `role_authority` VALUES ('34', '1', '26');
INSERT INTO `role_authority` VALUES ('35', '2', '26');
INSERT INTO `role_authority` VALUES ('36', '1', '27');
INSERT INTO `role_authority` VALUES ('37', '2', '27');
INSERT INTO `role_authority` VALUES ('38', '1', '28');
INSERT INTO `role_authority` VALUES ('39', '2', '28');
INSERT INTO `role_authority` VALUES ('40', '1', '29');
INSERT INTO `role_authority` VALUES ('41', '1', '30');
INSERT INTO `role_authority` VALUES ('42', '1', '31');
INSERT INTO `role_authority` VALUES ('43', '1', '32');
INSERT INTO `role_authority` VALUES ('44', '1', '33');
INSERT INTO `role_authority` VALUES ('45', '1', '34');
INSERT INTO `role_authority` VALUES ('46', '2', '34');
INSERT INTO `role_authority` VALUES ('47', '1', '35');
INSERT INTO `role_authority` VALUES ('48', '2', '35');
INSERT INTO `role_authority` VALUES ('49', '1', '36');
INSERT INTO `role_authority` VALUES ('50', '2', '36');
INSERT INTO `role_authority` VALUES ('51', '1', '37');
INSERT INTO `role_authority` VALUES ('52', '2', '37');
INSERT INTO `role_authority` VALUES ('53', '1', '38');
INSERT INTO `role_authority` VALUES ('54', '2', '38');
INSERT INTO `role_authority` VALUES ('55', '1', '39');
INSERT INTO `role_authority` VALUES ('56', '1', '40');
INSERT INTO `role_authority` VALUES ('57', '1', '41');
INSERT INTO `role_authority` VALUES ('58', '1', '42');
INSERT INTO `role_authority` VALUES ('59', '1', '43');
INSERT INTO `role_authority` VALUES ('60', '1', '44');
INSERT INTO `role_authority` VALUES ('61', '1', '45');
INSERT INTO `role_authority` VALUES ('62', '1', '46');
INSERT INTO `role_authority` VALUES ('63', '1', '47');
INSERT INTO `role_authority` VALUES ('64', '1', '48');
INSERT INTO `role_authority` VALUES ('65', '1', '49');
INSERT INTO `role_authority` VALUES ('66', '1', '50');
INSERT INTO `role_authority` VALUES ('67', '2', '22');
INSERT INTO `role_authority` VALUES ('68', '2', '2');
INSERT INTO `role_authority` VALUES ('69', '2', '14');
INSERT INTO `role_authority` VALUES ('70', '2', '9');
INSERT INTO `role_authority` VALUES ('71', '2', '10');
INSERT INTO `role_authority` VALUES ('72', '2', '11');
INSERT INTO `role_authority` VALUES ('73', '1', '51');
INSERT INTO `role_authority` VALUES ('74', '2', '51');
INSERT INTO `role_authority` VALUES ('120', '1', '56');
INSERT INTO `role_authority` VALUES ('121', '1', '57');
INSERT INTO `role_authority` VALUES ('122', '1', '58');
INSERT INTO `role_authority` VALUES ('123', '1', '59');
INSERT INTO `role_authority` VALUES ('124', '1', '60');
INSERT INTO `role_authority` VALUES ('125', '1', '61');
INSERT INTO `role_authority` VALUES ('126', '1', '62');
INSERT INTO `role_authority` VALUES ('127', '1', '63');
INSERT INTO `role_authority` VALUES ('128', '1', '64');
INSERT INTO `role_authority` VALUES ('129', '1', '65');
INSERT INTO `role_authority` VALUES ('130', '1', '66');
INSERT INTO `role_authority` VALUES ('131', '1', '67');
INSERT INTO `role_authority` VALUES ('132', '1', '68');
INSERT INTO `role_authority` VALUES ('133', '1', '69');
INSERT INTO `role_authority` VALUES ('134', '1', '70');
INSERT INTO `role_authority` VALUES ('135', '1', '71');
INSERT INTO `role_authority` VALUES ('136', '1', '72');
INSERT INTO `role_authority` VALUES ('137', '1', '73');
INSERT INTO `role_authority` VALUES ('138', '1', '74');
INSERT INTO `role_authority` VALUES ('139', '1', '75');
INSERT INTO `role_authority` VALUES ('140', '1', '76');
INSERT INTO `role_authority` VALUES ('141', '1', '77');
INSERT INTO `role_authority` VALUES ('142', '1', '78');
INSERT INTO `role_authority` VALUES ('143', '1', '79');
INSERT INTO `role_authority` VALUES ('144', '1', '80');
INSERT INTO `role_authority` VALUES ('145', '1', '81');
INSERT INTO `role_authority` VALUES ('146', '1', '82');
INSERT INTO `role_authority` VALUES ('147', '1', '83');
INSERT INTO `role_authority` VALUES ('148', '1', '84');
INSERT INTO `role_authority` VALUES ('149', '1', '85');
INSERT INTO `role_authority` VALUES ('150', '1', '86');
INSERT INTO `role_authority` VALUES ('151', '1', '87');
INSERT INTO `role_authority` VALUES ('152', '1', '88');
INSERT INTO `role_authority` VALUES ('153', '1', '89');
INSERT INTO `role_authority` VALUES ('154', '1', '90');
INSERT INTO `role_authority` VALUES ('155', '1', '91');
INSERT INTO `role_authority` VALUES ('156', '1', '92');
INSERT INTO `role_authority` VALUES ('157', '1', '93');
INSERT INTO `role_authority` VALUES ('158', '1', '94');
INSERT INTO `role_authority` VALUES ('159', '1', '95');
INSERT INTO `role_authority` VALUES ('160', '1', '96');
INSERT INTO `role_authority` VALUES ('161', '1', '97');
INSERT INTO `role_authority` VALUES ('162', '1', '98');
INSERT INTO `role_authority` VALUES ('163', '1', '99');
INSERT INTO `role_authority` VALUES ('164', '1', '100');
INSERT INTO `role_authority` VALUES ('165', '1', '101');
INSERT INTO `role_authority` VALUES ('166', '1', '102');
INSERT INTO `role_authority` VALUES ('167', '2', '56');
INSERT INTO `role_authority` VALUES ('168', '2', '57');
INSERT INTO `role_authority` VALUES ('169', '2', '58');
INSERT INTO `role_authority` VALUES ('170', '2', '59');
INSERT INTO `role_authority` VALUES ('171', '2', '60');
INSERT INTO `role_authority` VALUES ('172', '2', '61');
INSERT INTO `role_authority` VALUES ('173', '2', '62');
INSERT INTO `role_authority` VALUES ('174', '2', '63');
INSERT INTO `role_authority` VALUES ('175', '2', '64');
INSERT INTO `role_authority` VALUES ('177', '2', '66');
INSERT INTO `role_authority` VALUES ('178', '2', '67');
INSERT INTO `role_authority` VALUES ('179', '2', '68');
INSERT INTO `role_authority` VALUES ('180', '2', '69');
INSERT INTO `role_authority` VALUES ('181', '2', '70');
INSERT INTO `role_authority` VALUES ('182', '2', '71');
INSERT INTO `role_authority` VALUES ('183', '2', '72');
INSERT INTO `role_authority` VALUES ('184', '2', '73');
INSERT INTO `role_authority` VALUES ('185', '2', '74');
INSERT INTO `role_authority` VALUES ('186', '2', '75');
INSERT INTO `role_authority` VALUES ('187', '2', '76');
INSERT INTO `role_authority` VALUES ('188', '2', '77');
INSERT INTO `role_authority` VALUES ('189', '2', '78');
INSERT INTO `role_authority` VALUES ('190', '2', '79');
INSERT INTO `role_authority` VALUES ('191', '2', '80');
INSERT INTO `role_authority` VALUES ('192', '2', '81');
INSERT INTO `role_authority` VALUES ('193', '2', '82');
INSERT INTO `role_authority` VALUES ('194', '2', '83');
INSERT INTO `role_authority` VALUES ('195', '2', '84');
INSERT INTO `role_authority` VALUES ('196', '2', '85');
INSERT INTO `role_authority` VALUES ('197', '2', '86');
INSERT INTO `role_authority` VALUES ('198', '2', '87');
INSERT INTO `role_authority` VALUES ('199', '2', '88');
INSERT INTO `role_authority` VALUES ('200', '2', '89');
INSERT INTO `role_authority` VALUES ('201', '2', '90');
INSERT INTO `role_authority` VALUES ('202', '2', '91');
INSERT INTO `role_authority` VALUES ('214', '2', '29');
INSERT INTO `role_authority` VALUES ('215', '2', '32');
INSERT INTO `role_authority` VALUES ('216', '2', '21');
INSERT INTO `role_authority` VALUES ('217', '2', '20');
INSERT INTO `role_authority` VALUES ('218', '1', '52');
INSERT INTO `role_authority` VALUES ('219', '1', '53');
INSERT INTO `role_authority` VALUES ('220', '1', '54');
INSERT INTO `role_authority` VALUES ('221', '1', '55');
INSERT INTO `role_authority` VALUES ('222', '2', '41');
INSERT INTO `role_authority` VALUES ('223', '2', '44');
INSERT INTO `role_authority` VALUES ('224', '2', '50');
INSERT INTO `role_authority` VALUES ('235', '2', '17');
INSERT INTO `role_authority` VALUES ('236', '2', '30');
INSERT INTO `role_authority` VALUES ('237', '1', '104');
INSERT INTO `role_authority` VALUES ('238', '2', '104');
INSERT INTO `role_authority` VALUES ('239', '1', '105');
INSERT INTO `role_authority` VALUES ('240', '2', '105');
INSERT INTO `role_authority` VALUES ('241', '1', '106');
INSERT INTO `role_authority` VALUES ('242', '2', '106');
INSERT INTO `role_authority` VALUES ('243', '1', '107');
INSERT INTO `role_authority` VALUES ('244', '2', '107');
INSERT INTO `role_authority` VALUES ('245', '1', '108');
-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) NOT NULL COMMENT '用户名',
  `user_password` varchar(60) NOT NULL COMMENT '密码',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '所属角色',
  `english_name` varchar(45) DEFAULT NULL COMMENT '英文名',
  `chinese_name` varchar(45) DEFAULT NULL COMMENT '中文名',
  PRIMARY KEY (`user_id`),
  KEY `user_role_fk_idx` (`role_id`),
  CONSTRAINT `user_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '1dT0258c244T0a8d19e716292b231e3190', '2016-04-29 09:30:05', null, '2016-04-28 10:57:13', '1', 'admin', '管理员');
INSERT INTO `user` VALUES ('2', 'guest', 'T084eT0343a0486ff05530df6c705c8bb4', '2016-04-28 15:07:38', null, '2016-04-20 17:55:40', '2', 'guest', '普通用户');


-- ----------------------------------------
-- Table structure for 'project_manager'
-- ----------------------------------------
DROP TABLE IF EXISTS `project_manager`;
CREATE TABLE `project_manager` (
  `project_manager_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`project_manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='项目经理';


-- ---------------------------------------------
-- Table structure for 'implement_manager'
-- ---------------------------------------------
DROP TABLE IF EXISTS `implement_manager`;
CREATE TABLE `implement_manager` (
  `implement_manager_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`implement_manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='实施经理';

-- ---------------------------------------------
-- Table structure for 'project_manager_comment'
-- ---------------------------------------------

DROP TABLE IF EXISTS `project_manager_comment`;
CREATE TABLE `project_manager_comment` (
  `project_manager_com_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_manager_id` int(11) NOT NULL,
  `critic` varchar(45) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`project_manager_com_id`),
  KEY `project_manager_comment_project_fk_idx` (`project_manager_id`),
  CONSTRAINT `project_manager_comment_project_fk` FOREIGN KEY (`project_manager_id`) REFERENCES `project_manager` (`project_manager_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------
-- Table structure for 'implement_manager_comment'
-- ---------------------------------------------
DROP TABLE IF EXISTS `implement_manager_comment`;
CREATE TABLE `implement_manager_comment` (
  `implement_manager_com_id` int(11) NOT NULL AUTO_INCREMENT,
  `implement_manager_id` int(11) NOT NULL,
  `critic` varchar(45) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`implement_manager_com_id`),
  KEY `implement_manager_comment_project_fk_idx` (`implement_manager_id`),
  CONSTRAINT `implement_manager_comment_project_fk` FOREIGN KEY (`implement_manager_id`) REFERENCES `implement_manager` (`implement_manager_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------
-- Table structure for 'outsourcing_manage'
-- ---------------------------------------------
DROP TABLE IF EXISTS `outsourcing_manage`;
CREATE TABLE `outsourcing_manage` (
  `outsourcing_manage_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`outsourcing_manage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='外包管理';


-- ---------------------------------------------
-- Table structure for 'outsourcing_manage_comment'
-- ---------------------------------------------
DROP TABLE IF EXISTS `outsourcing_manage_comment`;
CREATE TABLE `outsourcing_manage_comment` (
  `outsourcing_manage_com_id` int(11) NOT NULL AUTO_INCREMENT,
  `outsourcing_manage_id` int(11) NOT NULL,
  `critic` varchar(45) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`outsourcing_manage_com_id`),
  KEY `outsourcing_manage_comment_project_fk_idx` (`outsourcing_manage_id`),
  CONSTRAINT `outsourcing_manage_comment_project_fk` FOREIGN KEY (`outsourcing_manage_id`) REFERENCES `outsourcing_manage` (`outsourcing_manage_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------
-- Table structure for 'chance_manage'
-- ---------------------------------------------
DROP TABLE IF EXISTS `chance_manage`;
CREATE TABLE `chance_manage` (
  `chance_manage_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(45) DEFAULT NULL COMMENT '项目名称',
  `project_manager_name` varchar(45) DEFAULT NULL COMMENT '项目经理',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `lead_time` datetime DEFAULT NULL COMMENT '交付日期',
  `forecast_quotation` double(10,2) DEFAULT NULL COMMENT '预估报价',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`chance_manage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='机会管理';

-- ---------------------------------------------
-- Table structure for 'project_financial'
-- ---------------------------------------------
DROP TABLE IF EXISTS `project_financial`;
CREATE TABLE `project_financial` (
  `project_financial_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `receive` double(10,2) DEFAULT NULL COMMENT '已收款',
  `payment` double(10,2) DEFAULT NULL COMMENT '已付款',
  `remark` text DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`project_financial_id`),
  KEY `project_financial_fk_idx` (`project_id`),
  CONSTRAINT `project_financial_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='收款付款';


alter table project_status_tracking.customer add COLUMN other_contact_way varchar(100) NULL COMMENT '其他联系方式';
alter table project_status_tracking.customer CHANGE COLUMN customer_title customer_title varchar(60) null COMMENT '客户头衔';
alter table project_status_tracking.customer CHANGE COLUMN company_address company_address varchar(100) null COMMENT '公司地址';
alter table project_status_tracking.customer CHANGE COLUMN company_phone company_phone varchar(45) null COMMENT '公司电话';
alter table project_status_tracking.customer CHANGE COLUMN bank bank varchar(200) null COMMENT '开户银行';
alter table project_status_tracking.customer CHANGE COLUMN payment_account payment_account varchar(60) null COMMENT '付款账号';
alter table project_status_tracking.customer CHANGE COLUMN company_id_number company_id_number varchar(60) null COMMENT '公司税号';
alter table project_status_tracking.project CHANGE COLUMN remark remark text null COMMENT '备注';
alter table project_status_tracking.cr CHANGE COLUMN remark remark text null COMMENT '备注';

-- 项目表添加字段
 alter table `project_status_tracking`.`project` add COLUMN cost double(10,2) default 0 COMMENT '成本支出';
 alter table `project_status_tracking`.`project` add COLUMN returned_status varchar(255) default 0 COMMENT '回款状态';
 alter table `project_status_tracking`.`project` add COLUMN project_nature int(2) default null COMMENT '项目性质';
 alter table `project_status_tracking`.`project` add COLUMN outsourcing_quotation double(10,2) default 0 COMMENT '外包价格';
-- 删除外键
 ALTER TABLE `project_status_tracking`.`project` DROP FOREIGN KEY `project_user_fk`;
 ALTER TABLE `project_status_tracking`.`project` DROP INDEX `project_user_fk_idx` ;
alter table `project_status_tracking`.`project` add COLUMN implement_manager varchar(50) default null COMMENT '实施经理';
insert into role_authority(role_id,authority_id) values(1,103);
alter table project modify project_type varchar(45) default null;
alter table project add delete_time datetime default null;
alter table CR add delete_time datetime default null;
alter table customer add delete_time datetime default null;
alter table user add delete_time datetime default null;
alter table project_file add delete_time datetime default null;
alter table cr_file add delete_time datetime default null;

update role set role_name = 'general_manager' where role_id = 1;
alter table project_status_tracking.project_manager add COLUMN creator_id int(11) NULL COMMENT '创建人';
alter table project_status_tracking.project_manager add COLUMN update_by int(11) NULL COMMENT '修改人';
alter table project_status_tracking.implement_manager add COLUMN creator_id int(11) NULL COMMENT '创建人';
alter table project_status_tracking.implement_manager add COLUMN update_by int(11) NULL COMMENT '修改人';
alter table project_status_tracking.outsourcing_manage add COLUMN creator_id int(11) NULL COMMENT '创建人';
alter table project_status_tracking.outsourcing_manage add COLUMN update_by int(11) NULL COMMENT '修改人';
alter table project_status_tracking.chance_manage add COLUMN creator_id int(11) NULL COMMENT '创建人';
alter table project_status_tracking.chance_manage add COLUMN update_by int(11) NULL COMMENT '修改人';

update authority set authority_url = '/usersManagement/users/export.do' where authority_id = 102;
alter table project_status_tracking.customer add COLUMN creator_id int(11) NULL COMMENT '创建人';
alter table project_status_tracking.customer add COLUMN update_by int(11) NULL COMMENT '修改人';
alter table project_status_tracking.cr modify COLUMN estimate_quotation int(11) default null COMMENT '报价';
alter table project_status_tracking.customer add COLUMN customer_name_short varchar(60) NULL COMMENT '客户简称';
alter table project_status_tracking1.user add COLUMN email varchar(50) NULL COMMENT '电子邮箱';
alter table project_status_tracking1.user add COLUMN is_notification int(1) NULL COMMENT '是否邮件通知';


insert into role_authority (role_id,authority_id)values(1,38);