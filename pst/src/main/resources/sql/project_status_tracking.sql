/*
Navicat MySQL Data Transfer

Source Server         : 211.149.144.205(MYSQL)
Source Server Version : 50626
Source Host           : 211.149.144.205:3306
Source Database       : project_status_tracking

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-05-21 16:14:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(45) DEFAULT NULL COMMENT '权限名称',
  `authority_description` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `authority_url` varchar(100) DEFAULT NULL COMMENT 'url链接',
  `authority_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='用户权限';

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

-- ----------------------------
-- Table structure for cr
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
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`cr_id`),
  KEY `cr_project_fk_idx` (`project_id`),
  CONSTRAINT `cr_project_fk` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='change request(新增需求)';

-- ----------------------------
-- Records of cr
-- ----------------------------
INSERT INTO `cr` VALUES ('129', '76', '修改', '24', '局长彭', '冉涛', '100', '100', null, '2016-05-05 15:40:47', '2016-05-05 15:40:47', '1');
INSERT INTO `cr` VALUES ('130', '76', '答复答复', '111', '大沙发', '大沙发大沙发', '1111', '1111', null, '2016-05-05 15:41:03', '2016-05-05 15:41:03', '1');

-- ----------------------------
-- Table structure for cr_comment
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cr_comment
-- ----------------------------

-- ----------------------------
-- Table structure for cr_file
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cr附件';

-- ----------------------------
-- Records of cr_file
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `customer_title` varchar(60) DEFAULT NULL COMMENT '客户头衔',
  `customer_name` varchar(60) NOT NULL,
  `customer_phone` varchar(45) NOT NULL COMMENT '客户电话',
  `company_address` varchar(100) DEFAULT NULL COMMENT '公司地址',
  `company_phone` varchar(45) DEFAULT NULL COMMENT '公司电话',
  `bank` varchar(200) DEFAULT NULL COMMENT '开户银行',
  `payment_account` varchar(60) DEFAULT NULL COMMENT '付款账号',
  `company_id_number` varchar(60) DEFAULT NULL COMMENT '公司税号',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `other_contact_way` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('18', '上海全网', '1111', '程皓明', '1111111111', '1111111111111111', '1111111111', '111111111', '111111', '1111111', '2016-05-05 15:39:43', '2016-05-08 21:36:36', null);

-- ----------------------------
-- Table structure for logging
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
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logging
-- ----------------------------
INSERT INTO `logging` VALUES ('1', '2016-04-29 17:53:02', '管理员于2016-04-29 05:50:38登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('2', '2016-04-29 20:20:57', '管理员于2016-04-29 08:18:33登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('3', '2016-04-29 20:23:56', '管理员于2016-04-29 08:21:32新增项目:【asdfasfda】', '管理员', '1', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('4', '2016-04-29 20:24:48', '管理员于2016-04-29 08:22:25对【null】进行了评论', '管理员', '1', '5', '评论', 'project_comment');
INSERT INTO `logging` VALUES ('5', '2016-04-29 20:25:41', '管理员于 2016-04-29 08:23:17 为项目【asdfasfda】增加了一条CR(撒旦法司法)', '管理员', '1', '1', '增加', 'cr');
INSERT INTO `logging` VALUES ('6', '2016-04-29 20:25:52', '管理员于 2016-04-29 08:23:29 对项目【asdfasfda】中的CR(撒旦法司法)进行了评论', '管理员', '1', '5', '评论', 'cr_comment');
INSERT INTO `logging` VALUES ('7', '2016-04-29 20:26:00', '管理员于2016-04-29 08:23:37导出了所有项目', '管理员', '1', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('8', '2016-04-29 20:27:10', '管理员于2016-04-29 08:24:47导出了所有项目', '管理员', '1', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('9', '2016-05-03 13:41:12', '管理员于2016-05-03 01:38:56登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('10', '2016-05-03 13:42:29', '普通用户于2016-05-03 01:40:13登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('11', '2016-05-03 15:06:51', '管理员于2016-05-03 03:04:35登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('12', '2016-05-03 15:07:48', '管理员于2016-05-03 03:05:32登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('13', '2016-05-03 15:09:05', '管理员于2016-05-03 03:06:50新增了用户【admin】', '管理员', '1', '1', '新增', 'user');
INSERT INTO `logging` VALUES ('14', '2016-05-03 15:10:39', '丁万凯于2016-05-03 03:08:24登录系统', '丁万凯', '10', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('15', '2016-05-03 15:12:38', '丁万凯于2016-05-03 03:10:23新增项目:【TuFaEHR】', '丁万凯', '10', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('16', '2016-05-03 15:24:44', '丁万凯于 2016-05-03 03:22:28 为项目【TuFaEHR】增加了一条CR(Always-UP和MD调薪确认询问)', '丁万凯', '10', '1', '增加', 'cr');
INSERT INTO `logging` VALUES ('17', '2016-05-03 15:25:15', '丁万凯于 2016-05-03 03:22:59 为项目【TuFaEHR】增加了一条CR(职位基本工资和所有职位)', '丁万凯', '10', '1', '增加', 'cr');
INSERT INTO `logging` VALUES ('18', '2016-05-03 16:41:46', '丁万凯于2016-05-03 04:39:31登录系统', '丁万凯', '10', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('19', '2016-05-04 17:58:04', '普通用户于2016-05-04 05:55:51登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('20', '2016-05-04 22:15:27', '管理员于2016-05-04 10:13:14登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('21', '2016-05-04 22:15:32', '管理员于2016-05-04 10:13:19登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('22', '2016-05-04 22:15:43', '管理员于2016-05-04 10:13:30删除项目【TuFaEHR】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('23', '2016-05-04 22:15:46', '管理员于2016-05-04 10:13:33删除项目【asdfasfda】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('24', '2016-05-04 22:15:49', '管理员于2016-05-04 10:13:36删除项目【1】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('25', '2016-05-04 22:16:20', '管理员于2016-05-04 10:14:07删除项目【水上项目】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('26', '2016-05-04 22:16:25', '管理员于2016-05-04 10:14:12删除项目【abc】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('27', '2016-05-05 09:21:15', '管理员于2016-05-05 09:19:03登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('28', '2016-05-05 09:22:55', '管理员于2016-05-05 09:20:43新增项目:【1】', '管理员', '1', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('29', '2016-05-05 09:22:59', '管理员于2016-05-05 09:20:47删除项目【1】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('30', '2016-05-05 14:02:17', '管理员于2016-05-05 02:00:06登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('31', '2016-05-05 14:02:45', '管理员于2016-05-05 02:00:33新增项目:【safasdfas】', '管理员', '1', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('32', '2016-05-05 14:02:50', '管理员于2016-05-05 02:00:38删除项目【safasdfas】', '管理员', '1', '2', '删除', 'project');
INSERT INTO `logging` VALUES ('33', '2016-05-05 15:38:28', '管理员于2016-05-05 03:36:17登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('34', '2016-05-05 15:39:43', '管理员于2016-05-05 03:37:32新增了名为【程皓明】的客户', '管理员', '1', '1', '增加', 'customer');
INSERT INTO `logging` VALUES ('35', '2016-05-05 15:40:15', '管理员于2016-05-05 03:38:03新增项目:【啊啊士大夫】', '管理员', '1', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('36', '2016-05-05 15:40:47', '管理员于 2016-05-05 03:38:36 为项目【啊啊士大夫】增加了一条CR(修改)', '管理员', '1', '1', '增加', 'cr');
INSERT INTO `logging` VALUES ('37', '2016-05-05 15:41:03', '管理员于 2016-05-05 03:38:52 为项目【啊啊士大夫】增加了一条CR(答复答复)', '管理员', '1', '1', '增加', 'cr');
INSERT INTO `logging` VALUES ('38', '2016-05-05 15:41:17', '管理员于2016-05-05 03:39:06导出了所有项目', '管理员', '1', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('39', '2016-05-05 15:42:01', '管理员于2016-05-05 03:39:50登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('40', '2016-05-05 15:42:04', '管理员于2016-05-05 03:39:53导出了所有项目', '管理员', '1', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('41', '2016-05-08 21:36:01', '管理员于2016-05-08 09:33:56登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('42', '2016-05-08 21:36:19', '管理员于2016-05-08 09:34:14删除了一条客户【张建平】', '管理员', '1', '2', '删除', 'customer');
INSERT INTO `logging` VALUES ('43', '2016-05-08 21:36:36', '管理员于2016-05-08 09:34:31修改了名为【程皓明】的客户', '管理员', '1', '3', '修改', 'customer');
INSERT INTO `logging` VALUES ('44', '2016-05-09 13:29:09', '管理员于2016-05-09 01:27:06登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('45', '2016-05-09 14:11:47', '管理员于2016-05-09 02:09:43登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('46', '2016-05-10 10:07:43', '管理员于2016-05-10 10:05:42登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('47', '2016-05-10 15:01:35', '管理员于2016-05-10 02:59:34登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('48', '2016-05-11 15:02:44', '管理员于2016-05-11 03:00:44登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('49', '2016-05-11 17:05:22', '管理员于2016-05-11 05:03:22登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('50', '2016-05-11 17:05:37', '管理员于2016-05-11 05:03:37修改项目【啊啊士大夫】', '管理员', '1', '3', '修改', 'project');
INSERT INTO `logging` VALUES ('51', '2016-05-13 15:02:07', '管理员于2016-05-13 03:00:11登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('52', '2016-05-13 15:02:07', '管理员于2016-05-13 03:00:12登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('53', '2016-05-13 15:02:44', '管理员于2016-05-13 03:00:49新增了用户【admin】', '管理员', '1', '1', '新增', 'user');
INSERT INTO `logging` VALUES ('54', '2016-05-13 15:03:05', '周军于2016-05-13 03:01:10登录系统', '周军', '11', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('55', '2016-05-13 15:06:20', '周军于2016-05-13 03:04:25新增项目:【旭贝尔CRM维护】', '周军', '11', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('56', '2016-05-13 15:06:58', '周军于2016-05-13 03:05:03登录系统', '周军', '11', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('57', '2016-05-13 15:07:11', '普通用户于2016-05-13 03:05:16登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('58', '2016-05-13 15:07:21', '管理员于2016-05-13 03:05:26登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('59', '2016-05-13 15:30:59', '普通用户于2016-05-13 03:29:04登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('60', '2016-05-14 18:46:56', '管理员于2016-05-14 06:44:59登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('61', '2016-05-14 20:36:54', '管理员于2016-05-14 08:34:58登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('62', '2016-05-14 20:40:47', '管理员于2016-05-14 08:38:51登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('63', '2016-05-14 20:42:22', '管理员于2016-05-14 08:40:26修改项目【旭贝尔CRM维护】', '管理员', '1', '3', '修改', 'project');
INSERT INTO `logging` VALUES ('64', '2016-05-16 09:23:53', '管理员于2016-05-16 09:22:03登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('65', '2016-05-16 10:52:23', '管理员于2016-05-16 10:50:33登录系统', '管理员', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('66', '2016-05-16 10:54:34', '周军于2016-05-16 10:52:44登录系统', '周军', '11', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('67', '2016-05-16 10:54:43', '周军于2016-05-16 10:52:54登录系统', '周军', '11', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('68', '2016-05-21 12:28:59', '普通用户于2016-05-21 12:26:20登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('69', '2016-05-21 12:55:01', '普通用户于2016-05-21 12:52:22登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('70', '2016-05-21 14:07:28', '总经理于2016-05-21 02:04:49登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('71', '2016-05-21 14:15:21', '总经理于2016-05-21 02:12:42登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('72', '2016-05-21 14:26:30', '总经理于2016-05-21 02:23:51登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('73', '2016-05-21 14:41:41', '总经理于2016-05-21 02:39:02登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('74', '2016-05-21 14:42:44', '总经理于2016-05-21 02:40:05删除了用户【manager】', '总经理', '1', '2', '删除', 'user');
INSERT INTO `logging` VALUES ('75', '2016-05-21 14:45:38', '总经理于2016-05-21 02:42:59删除了用户【manager】', '总经理', '1', '2', '删除', 'user');
INSERT INTO `logging` VALUES ('76', '2016-05-21 14:46:45', '总经理于2016-05-21 02:44:06登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('77', '2016-05-21 15:06:18', '总经理于2016-05-21 03:03:39更新项目【啊啊士大夫】财务信息', '总经理', '1', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('78', '2016-05-21 15:07:06', '总经理于2016-05-21 03:04:27更新项目【旭贝尔CRM维护】财务信息', '总经理', '1', '1', '增加', 'project');
INSERT INTO `logging` VALUES ('79', '2016-05-21 15:17:51', '总经理于2016-05-21 03:15:12登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('80', '2016-05-21 15:22:33', '总经理于2016-05-21 03:19:54登录系统', '总经理', '1', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('81', '2016-05-21 15:24:56', '普通用户于2016-05-21 03:22:17登录系统', '普通用户', '2', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('82', '2016-05-21 16:01:26', '管理员于2016-05-21 03:58:47登录系统', '管理员', '3', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('83', '2016-05-21 16:04:01', '管理员于2016-05-21 04:01:22登录系统', '管理员', '3', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('84', '2016-05-21 16:04:51', '管理员于2016-05-21 04:02:12登录系统', '管理员', '3', '6', '登录', 'user');
INSERT INTO `logging` VALUES ('85', '2016-05-21 16:05:12', '管理员于2016-05-21 04:02:33对【null】进行了评论', '管理员', '3', '5', '评论', 'project_comment');
INSERT INTO `logging` VALUES ('86', '2016-05-21 16:05:17', '管理员于2016-05-21 04:02:38修改项目【旭贝尔CRM维护】', '管理员', '3', '3', '修改', 'project');
INSERT INTO `logging` VALUES ('87', '2016-05-21 16:05:39', '管理员于2016-05-21 04:03:00导出了所有项目', '管理员', '3', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('88', '2016-05-21 16:05:39', '管理员于2016-05-21 04:03:00导出了所有项目', '管理员', '3', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('89', '2016-05-21 16:05:45', '管理员于2016-05-21 04:03:06导出了所有项目', '管理员', '3', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('90', '2016-05-21 16:05:45', '管理员于2016-05-21 04:03:06导出了所有项目', '管理员', '3', '4', '导出', 'project');
INSERT INTO `logging` VALUES ('91', '2016-05-21 16:05:51', '管理员于2016-05-21 04:03:12导出了所有项目', '管理员', '3', '4', '导出', 'project');

-- ----------------------------
-- Table structure for project
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
  `customer_name` varchar(100) NOT NULL COMMENT '客户名称',
  `remark` text COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL COMMENT '创建者',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `project_status` varchar(45) NOT NULL COMMENT '项目状态：1商务、2制作、3修改、4完成、5流标',
  `project_type` varchar(45) NOT NULL COMMENT '项目类型：1视频制作、2平面设计、3活动会务、4环境布置、5多媒体布展、6新媒体开发、7云发布\n',
  `project_status_number` int(11) NOT NULL COMMENT '项目状态对应的数字',
  `project_type_number` int(11) NOT NULL COMMENT '项目类型对应的数字',
  `cost` double(10,2) DEFAULT '0.00' COMMENT '成本支出',
  `returned_status` varchar(255) DEFAULT NULL COMMENT '回款状态',
  PRIMARY KEY (`project_id`),
  KEY `project_user_fk_idx` (`creator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='项目';

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('76', '阿斯顿发放', '啊啊士大夫', '阿三发生', '2016-05-05', '2016-05-06', '2016-05-07', '1.00', '程皓明', '', '2016-05-05 15:40:15', '2016-05-21 15:06:18', '1', '18', '制作', '环境布置', '2', '4', '3.00', 'asdf');
INSERT INTO `project` VALUES ('77', 'P1QWXBER03', '旭贝尔CRM维护', '周军', '2016-01-01', '2016-04-01', '2016-05-01', '0.00', '上海全网', '', '2016-05-13 15:06:20', '2016-05-21 16:05:17', '11', '18', '修改', '平面设计', '3', '2', '1.00', '1');

-- ----------------------------
-- Table structure for project_comment
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_comment
-- ----------------------------
INSERT INTO `project_comment` VALUES ('1', '77', '管理员', '11', '2016-05-21 16:05:12', null);

-- ----------------------------
-- Table structure for project_file
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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='项目附件';

-- ----------------------------
-- Records of project_file
-- ----------------------------
INSERT INTO `project_file` VALUES ('3', 'Spring Mvc.txt', 'project/39/20160418091748_Spring Mvc.txt', 'text/plain', '2422', '2016-04-18 08:51:30', '2016-04-18 09:17:48', '39');
INSERT INTO `project_file` VALUES ('4', 'Penguins.jpg', 'project/39/20160418085130_Penguins.jpg', 'image/jpeg', '777835', '2016-04-18 08:51:30', null, '39');
INSERT INTO `project_file` VALUES ('5', 'Spring Mvc.txt', 'project/53/20160418093017_Spring Mvc.txt', 'text/plain', '2422', '2016-04-18 09:30:17', null, '53');
INSERT INTO `project_file` VALUES ('6', 'annotation_temp.xml', 'project/53/20160418093017_annotation_temp.xml', 'text/xml', '3021', '2016-04-18 09:30:17', null, '53');
INSERT INTO `project_file` VALUES ('7', 'Spring Mvc.txt', 'project/56/20160418093309_Spring Mvc.txt', 'text/plain', '2422', '2016-04-18 09:33:09', null, '56');
INSERT INTO `project_file` VALUES ('8', 'annotation_temp.xml', 'project/56/20160418093342_annotation_temp.xml', 'text/xml', '3021', '2016-04-18 09:33:42', null, '56');
INSERT INTO `project_file` VALUES ('9', 'Cliff Expense report2.xlsx', 'project/57/20160418094941_Cliff Expense report2.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '71797', '2016-04-18 09:49:41', null, '57');
INSERT INTO `project_file` VALUES ('12', 'Cliff Expense report2.xlsx', 'project/58/20160418102108_Cliff Expense report2.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '71797', '2016-04-18 10:21:08', null, '58');
INSERT INTO `project_file` VALUES ('13', 'Expense (capsugel)_Cliff_20160222.xlsx', 'project/58/20160418102108_Expense (capsugel)_Cliff_20160222.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '62742', '2016-04-18 10:21:08', null, '58');
INSERT INTO `project_file` VALUES ('16', 'spring-framework-4.0.3.RELEASE-dist.zip', 'project/60/20160419095201_spring-framework-4.0.3.RELEASE-dist.zip', 'application/x-zip-compressed', '54885041', '2016-04-19 09:53:45', null, '60');
INSERT INTO `project_file` VALUES ('31', 'Koala.jpg', 'project/76/20160511170337_Koala.jpg', 'image/jpeg', '780831', '2016-05-11 17:05:37', null, '76');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL COMMENT '角色名称',
  `role_description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'general_manager', '总经理');
INSERT INTO `role` VALUES ('2', 'user', '普通用户');
INSERT INTO `role` VALUES ('3', 'admin', '管理员');

-- ----------------------------
-- Table structure for role_authority
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
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='角色权限';

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
INSERT INTO `role_authority` VALUES ('75', '1', '52');
INSERT INTO `role_authority` VALUES ('76', '1', '53');
INSERT INTO `role_authority` VALUES ('77', '1', '54');
INSERT INTO `role_authority` VALUES ('78', '1', '55');
INSERT INTO `role_authority` VALUES ('79', '3', '1');
INSERT INTO `role_authority` VALUES ('80', '3', '2');
INSERT INTO `role_authority` VALUES ('81', '3', '3');
INSERT INTO `role_authority` VALUES ('82', '3', '4');
INSERT INTO `role_authority` VALUES ('83', '3', '5');
INSERT INTO `role_authority` VALUES ('84', '3', '6');
INSERT INTO `role_authority` VALUES ('85', '3', '7');
INSERT INTO `role_authority` VALUES ('86', '3', '8');
INSERT INTO `role_authority` VALUES ('87', '3', '9');
INSERT INTO `role_authority` VALUES ('88', '3', '10');
INSERT INTO `role_authority` VALUES ('89', '3', '11');
INSERT INTO `role_authority` VALUES ('90', '3', '12');
INSERT INTO `role_authority` VALUES ('91', '3', '14');
INSERT INTO `role_authority` VALUES ('92', '3', '16');
INSERT INTO `role_authority` VALUES ('93', '3', '22');
INSERT INTO `role_authority` VALUES ('94', '3', '24');
INSERT INTO `role_authority` VALUES ('95', '3', '25');
INSERT INTO `role_authority` VALUES ('96', '3', '26');
INSERT INTO `role_authority` VALUES ('97', '3', '27');
INSERT INTO `role_authority` VALUES ('98', '3', '28');
INSERT INTO `role_authority` VALUES ('99', '3', '33');
INSERT INTO `role_authority` VALUES ('100', '3', '34');
INSERT INTO `role_authority` VALUES ('101', '3', '35');
INSERT INTO `role_authority` VALUES ('102', '3', '36');
INSERT INTO `role_authority` VALUES ('103', '3', '37');
INSERT INTO `role_authority` VALUES ('104', '3', '38');
INSERT INTO `role_authority` VALUES ('105', '3', '41');
INSERT INTO `role_authority` VALUES ('106', '3', '44');
INSERT INTO `role_authority` VALUES ('107', '3', '50');
INSERT INTO `role_authority` VALUES ('108', '3', '51');

-- ----------------------------
-- Table structure for user
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'manager', 'manager', '2016-05-21 15:19:55', null, '2016-04-28 10:57:13', '1', 'manager', '总经理');
INSERT INTO `user` VALUES ('2', 'guest', 'guest', '2016-05-21 15:22:17', null, '2016-04-20 17:55:40', '2', 'guest', '普通用户');
INSERT INTO `user` VALUES ('3', 'admin', 'admin', '2016-05-21 16:02:13', null, null, '3', 'admin', '管理员');
