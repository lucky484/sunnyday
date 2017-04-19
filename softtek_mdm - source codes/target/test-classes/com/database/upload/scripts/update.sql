-- 表并未被使用，属于冗余表，删除
DROP TABLE IF EXISTS `action_operation_dictionary`;

-- 新增app表
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL,
  `release_type` char(1) DEFAULT NULL,
  `apk_file` varchar(100) DEFAULT NULL,
  `app_id` varchar(100) DEFAULT NULL,
  `icon_path` varchar(100) DEFAULT NULL,
  `app_name` varchar(100) DEFAULT NULL,
  `app_version` varchar(20) DEFAULT NULL,
  `signature_certificate` varchar(100) DEFAULT NULL,
  `author` varchar(500) DEFAULT NULL,
  `app_description` text COLLATE utf8_unicode_ci,
  `minimum_version` char(2) DEFAULT NULL,
  `state` char(1) DEFAULT NULL,
  `app_count` int(11) DEFAULT NULL,
  `file_size` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 删除表app_category
DROP TABLE IF EXISTS `app_category`;

-- 新增表app_department_authorization
DROP TABLE IF EXISTS `app_department_authorization`;
CREATE TABLE `app_department_authorization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_structure_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  `is_install` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 删除表app_version_manage
DROP TABLE IF EXISTS `app_version_manage`;

-- 新增表 app_virtual_group_authorization
DROP TABLE IF EXISTS `app_virtual_group_authorization`;
CREATE TABLE `app_virtual_group_authorization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `virtual_group_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  `is_install` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表client_command
DROP TABLE IF EXISTS `client_command`;
CREATE TABLE `client_command` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(200) DEFAULT NULL COMMENT '序列号',
  `remove_device` int(3) DEFAULT NULL COMMENT '解锁终端设备',
  `create_by` int(9) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(9) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表client_config
DROP TABLE IF EXISTS `client_config`;
CREATE TABLE `client_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL,
  `terminal_unbund_enable` int(2) DEFAULT NULL,
  `service_setting_hide` int(2) DEFAULT NULL,
  `device_info_hide` int(2) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表client_manager
DROP TABLE IF EXISTS `client_manager`;
CREATE TABLE `client_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL COMMENT '机构',
  `client_id_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '客户端标识',
  `client_app_name` varchar(50) DEFAULT NULL COMMENT '客户端名称',
  `belong_org` varchar(1000) DEFAULT NULL COMMENT '所属机构',
  `client_version` varchar(20) DEFAULT NULL COMMENT '版本',
  `client_byte` varchar(20) DEFAULT NULL COMMENT '大小',
  `is_upgrade` int(2) DEFAULT NULL COMMENT '是否强制升级(0-否，1-是)',
  `apply_platform` varchar(20) DEFAULT NULL COMMENT '适用平台',
  `support_device` varchar(20) DEFAULT NULL COMMENT '支持设备',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `signature_certificate` varchar(200) DEFAULT NULL,
  `remark` text COLLATE utf8_unicode_ci,
  `file_name` varchar(200) DEFAULT NULL,
  `download_url` varchar(200) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 新增表deleted_device
DROP TABLE IF EXISTS `deleted_device`;
CREATE TABLE `deleted_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL,
  `device_name` varchar(64) DEFAULT NULL COMMENT '设备名',
  `device_type` varchar(50) DEFAULT NULL COMMENT '设备类型android,ios',
  `sn` varchar(60) DEFAULT NULL COMMENT '序列号',
  `esnorimei` varchar(60) DEFAULT NULL COMMENT 'esn/imei号',
  `belong` varchar(40) DEFAULT NULL COMMENT '归属',
  `device_status` varchar(40) DEFAULT NULL COMMENT '设备状态',
  `user_id` varchar(30) DEFAULT NULL COMMENT '用户',
  `os_version` varchar(30) DEFAULT NULL COMMENT '系统版本',
  `phone_number` varchar(64) DEFAULT NULL COMMENT '手机号',
  `app_versoin` varchar(64) DEFAULT NULL COMMENT '客户端版本',
  `ip` varchar(64) DEFAULT NULL COMMENT '最后登录ip',
  `power` varchar(64) DEFAULT NULL COMMENT '电源状态',
  `resolution` varchar(50) DEFAULT NULL COMMENT '分辨率',
  `udid` varchar(64) DEFAULT NULL COMMENT 'UDID',
  `capacity` varchar(64) DEFAULT NULL COMMENT '存储容量',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_collection_time` datetime DEFAULT NULL COMMENT '最后采集时间',
  `create_time` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `irr_status` int(2) DEFAULT NULL,
  `lost_status` int(2) DEFAULT NULL,
  `break_status` int(2) DEFAULT NULL,
  `visit_status` int(2) DEFAULT NULL,
  `is_active` int(2) DEFAULT NULL,
  `sd_card` varchar(50) DEFAULT NULL,
  `device_unique_id` varchar(1000) DEFAULT NULL COMMENT '设备唯一标识',
  `longitude` varchar(60) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(60) DEFAULT NULL COMMENT '纬度',
  `flux` varchar(60) DEFAULT NULL COMMENT '设备流量',
  `statistical_time` datetime DEFAULT NULL COMMENT '统计时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表deleted_users
DROP TABLE IF EXISTS `deleted_users`;
CREATE TABLE `deleted_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(256) DEFAULT NULL COMMENT '用户名',
  `org_id` int(11) DEFAULT NULL COMMENT '机构编号 ',
  `group_id` int(11) DEFAULT NULL COMMENT '部门id',
  `policy_id` int(11) DEFAULT NULL COMMENT '策略id',
  `real_name` varchar(256) DEFAULT NULL COMMENT '用户姓名',
  `create_time` datetime DEFAULT NULL COMMENT '删除时间',
  `create_by` int(11) DEFAULT NULL COMMENT '删除人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表department_pushmsg_relation
DROP TABLE IF EXISTS `department_pushmsg_relation`;
CREATE TABLE `department_pushmsg_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orgmanagerid` int(9) DEFAULT NULL COMMENT '创建者所属部门ID（没有部门id则是机构id）',
  `picmsgid` int(11) DEFAULT NULL COMMENT '图文消息ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 新增departmet_netblackwhitelist_relation
DROP TABLE IF EXISTS `departmet_netblackwhitelist_relation`;
CREATE TABLE `departmet_netblackwhitelist_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orgmanagerid` int(9) DEFAULT NULL COMMENT '创建者所属部门ID（没有部门id则是机构id）',
  `netbalckwhitelistid` int(9) DEFAULT NULL COMMENT '上网行为黑白名单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- 新增device_location_record
DROP TABLE IF EXISTS `device_location_record`;
CREATE TABLE `device_location_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(9) DEFAULT NULL COMMENT '设备id',
  `longitude` varchar(50) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(50) DEFAULT NULL COMMENT '纬度',
  `loc_dec` varchar(50) DEFAULT NULL COMMENT '位置描述',
  `create_by` int(9) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(9) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- 新增device_statistics
DROP TABLE IF EXISTS `device_statistics`;
CREATE TABLE `device_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_id` int(11) DEFAULT NULL COMMENT '机构Id',
  `device_name` varchar(64) DEFAULT NULL COMMENT '设备名称',
  `device_type` varchar(50) DEFAULT NULL COMMENT '设备类型android,ios',
  `sn` varchar(60) DEFAULT NULL COMMENT '序列号',
  `esnorimei` varchar(60) DEFAULT NULL COMMENT 'esn/imei号',
  `belong` varchar(40) DEFAULT NULL COMMENT '归属',
  `device_status` varchar(40) DEFAULT NULL COMMENT '设备状态',
  `user_id` varchar(30) DEFAULT NULL COMMENT '用户',
  `os_version` varchar(30) DEFAULT NULL COMMENT '系统版本',
  `phone_number` varchar(64) DEFAULT NULL COMMENT '手机号',
  `app_versoin` varchar(64) DEFAULT NULL COMMENT '客户端版本',
  `ip` varchar(64) DEFAULT NULL COMMENT '最后登录ip',
  `power` varchar(64) DEFAULT NULL COMMENT '电源状态',
  `resolution` varchar(50) DEFAULT NULL COMMENT '分辨率',
  `udid` varchar(64) DEFAULT NULL COMMENT 'UDID',
  `capacity` varchar(64) DEFAULT NULL COMMENT '存储容量',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_collection_time` datetime DEFAULT NULL COMMENT '最后采集时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `irr_status` int(2) DEFAULT NULL COMMENT '违规状态',
  `lost_status` int(2) DEFAULT NULL COMMENT '丢失状态',
  `break_status` int(2) DEFAULT NULL COMMENT '破解状态',
  `visit_status` int(2) DEFAULT NULL,
  `is_active` int(2) DEFAULT NULL COMMENT '激活状态',
  `sd_card` varchar(50) DEFAULT NULL,
  `device_unique_id` varchar(1000) DEFAULT NULL COMMENT '设备唯一标识',
  `longitude` varchar(60) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(60) DEFAULT NULL COMMENT '纬度',
  `flux` varchar(60) DEFAULT NULL COMMENT '设备流量',
  `statistical_time` datetime DEFAULT NULL COMMENT '统计日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2048 DEFAULT CHARSET=utf8;

-- 新增email_model_setting
DROP TABLE IF EXISTS `email_model_setting`;
CREATE TABLE `email_model_setting` (
  `id` int(9) NOT NULL COMMENT '邮件模板id',
  `adviceType` int(9) DEFAULT NULL COMMENT '邮件模板类型',
  `theme` varchar(1000) DEFAULT NULL COMMENT '邮件主题',
  `content` varchar(1500) DEFAULT NULL COMMENT '邮件内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增email_param_setting
DROP TABLE IF EXISTS `email_param_setting`;
CREATE TABLE `email_param_setting` (
  `id` int(9) NOT NULL COMMENT '邮件参数设置ID',
  `host` varchar(512) DEFAULT NULL COMMENT '邮件服务器地址',
  `sender` varchar(512) DEFAULT NULL COMMENT '邮件发送者邮箱',
  `port` int(6) DEFAULT NULL COMMENT '邮件服务端口号',
  `username` varchar(512) DEFAULT NULL COMMENT '邮件发送者邮箱',
  `password` varchar(512) DEFAULT NULL COMMENT '邮件发送者邮箱',
  `isSSL` int(1) DEFAULT NULL COMMENT '是否开启ssl安全',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表filter_url
DROP TABLE IF EXISTS `filter_url`;
CREATE TABLE `filter_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) DEFAULT NULL,
  `is_allow` char(1) DEFAULT NULL,
  `ios_device_policy_id` int(11) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表flux
DROP TABLE IF EXISTS `flux`;
CREATE TABLE `flux` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `device_name` varchar(64) DEFAULT NULL COMMENT '设备名称',
  `device_type` varchar(50) DEFAULT NULL COMMENT '设备类型',
  `sn` varchar(60) DEFAULT NULL COMMENT 'sn号',
  `esnorimei` varchar(60) DEFAULT NULL COMMENT 'imei号',
  `device_status` varchar(40) DEFAULT NULL COMMENT '设备状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `flux` varchar(60) DEFAULT NULL COMMENT '设备流量',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id 对应users表',
  `device_basic_info_id` int(11) DEFAULT NULL COMMENT '设备信息表主键索引',
  `statistical_time` datetime DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 新增表
DROP TABLE IF EXISTS `ios_device_policy`;
CREATE TABLE `ios_device_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_enable` char(1) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `is_enable_password` char(1) DEFAULT NULL,
  `allow_simple_value` char(1) DEFAULT NULL,
  `letter_digit_value` char(1) DEFAULT NULL,
  `password_length` char(2) DEFAULT NULL,
  `password_complexity` char(1) DEFAULT NULL,
  `lock_longest_time` char(1) DEFAULT NULL,
  `grace_time` char(1) DEFAULT NULL,
  `failure_times` char(1) DEFAULT NULL,
  `validity_period` int(11) DEFAULT NULL,
  `password_history` int(11) DEFAULT NULL,
  `allow_install_app` char(1) DEFAULT NULL,
  `allow_remove_app` char(1) DEFAULT NULL,
  `allow_use_camera` char(1) DEFAULT NULL,
  `allow_face_time` char(1) DEFAULT NULL,
  `allow_screen_catch` char(1) DEFAULT NULL,
  `allow_siri` char(1) DEFAULT NULL,
  `allow_voice_dialing` char(1) DEFAULT NULL,
  `allow_app_purchase` char(1) DEFAULT NULL,
  `force_iTunes_store` char(1) DEFAULT NULL,
  `limit_advert_tracking` char(1) DEFAULT NULL,
  `allow_modify_user` char(1) DEFAULT NULL,
  `allow_find_friends` char(1) DEFAULT NULL,
  `allow_host_pair` char(1) DEFAULT NULL,
  `allow_display_news` char(1) DEFAULT NULL,
  `allow_fingerprint_unlock` char(1) DEFAULT NULL,
  `allow_notice_news` char(1) DEFAULT NULL,
  `allow_display_passbook` char(1) DEFAULT NULL,
  `allow_use_youtube` char(1) DEFAULT NULL,
  `allow_use_iTunes` char(1) DEFAULT NULL,
  `allow_airDrop` char(1) DEFAULT NULL,
  `allow_iMessage` char(1) DEFAULT NULL,
  `allow_iBook_store` char(1) DEFAULT NULL,
  `allow_game_center` char(1) DEFAULT NULL,
  `allow_use_safari` char(1) DEFAULT NULL,
  `allow_backup` char(1) DEFAULT NULL,
  `allow_document_synchronization` char(1) DEFAULT NULL,
  `allow_photo_stream` char(1) DEFAULT NULL,
  `safe_type` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表ios_device_push
DROP TABLE IF EXISTS `ios_device_push`;
CREATE TABLE `ios_device_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `udid` varchar(100) DEFAULT NULL,
  `topic` varchar(100) DEFAULT '',
  `control` varchar(10) DEFAULT NULL,
  `push_magic` varchar(100) DEFAULT NULL,
  `token` varchar(100) COLLATE DEFAULT NULL,
  `unlock_token` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表ios_policy_user
DROP TABLE IF EXISTS `ios_policy_user`;
CREATE TABLE `ios_policy_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `ios_device_policy_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表ios_wifi_configure
DROP TABLE IF EXISTS `ios_wifi_configure`;
CREATE TABLE `ios_wifi_configure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ssid` varchar(100) DEFAULT NULL,
  `is_autojoin` char(1) DEFAULT NULL,
  `is_hiddennetwork` char(1) DEFAULT NULL,
  `security_type` char(1) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `eap_method` char(1) DEFAULT NULL,
  `stage_authentication` char(1) DEFAULT NULL,
  `ca_certificate` char(1) DEFAULT NULL,
  `user_certificate` char(1) DEFAULT NULL,
  `identity` varchar(100) DEFAULT NULL,
  `anonymous_identity` varchar(100) DEFAULT NULL,
  `agent` char(1) COLLATE DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `agent_server` varchar(255) DEFAULT NULL,
  `agent_port` varchar(100) DEFAULT NULL,
  `agent_appraisal` varchar(255) DEFAULT NULL,
  `agent_password` varchar(100) DEFAULT NULL,
  `agent_url` varchar(255) DEFAULT NULL,
  `ios_device_policy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表license_info
DROP TABLE IF EXISTS `license_info`;
CREATE TABLE `license_info` (
  `id` int(18) NOT NULL COMMENT '许可证id',
  `adviceDays` int(11) DEFAULT NULL COMMENT '上网人姓名',
  `activeCode` varchar(1000) DEFAULT NULL COMMENT '上网人ID',
  `machineFilePath` varchar(1000) DEFAULT NULL COMMENT '用户机器码文件路径',
  `privateKey` varchar(1000) DEFAULT NULL COMMENT '本地私钥',
  `licenceFileBytes` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表organization_manager_relation
DROP TABLE IF EXISTS `organization_manager_relation`;
CREATE TABLE `organization_manager_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 新增表security_event_log
DROP TABLE IF EXISTS `security_event_log`;
CREATE TABLE `security_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_type` varchar(100) DEFAULT NULL COMMENT '事件类型',
  `level` varchar(10) DEFAULT NULL,
  `operate_content` text COLLATE utf8_unicode_ci,
  `create_time` datetime DEFAULT NULL,
  `create_by` int(9) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` int(9) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- 新增表system_param_set
DROP TABLE IF EXISTS `system_param_set`;
CREATE TABLE `system_param_set` (
  `id` int(18) NOT NULL COMMENT 'id',
  `mdmAddress` varchar(1000) DEFAULT NULL COMMENT 'MDM管理平台地址',
  `outmanagertime` int(9) DEFAULT NULL COMMENT '设备脱管时间限制(分)',
  `deviceInfoCollectTime` int(9) DEFAULT NULL COMMENT '终端设备信息采集周期(分)',
  `illegalInfoCollectTime` int(9) DEFAULT NULL COMMENT '终端违规采集周期(分)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表system_style_set
DROP TABLE IF EXISTS `system_style_set`;
CREATE TABLE `system_style_set` (
  `id` int(9) NOT NULL DEFAULT '0',
  `logo_isdefault` int(2) DEFAULT '0' COMMENT 'logo是否使用默认',
  `copyright_isdefault` int(2) DEFAULT '0' COMMENT '版权是否使用默认',
  `copyright_default` varchar(500) DEFAULT NULL COMMENT '默认的版权',
  `copyright_new` varchar(500) DEFAULT NULL COMMENT '自定义的版权',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表user_app
DROP TABLE IF EXISTS `user_app`;
CREATE TABLE `user_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  `state` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 新增表users_statistics
DROP TABLE IF EXISTS `users_statistics`;
CREATE TABLE `users_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `user_name` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `group_name` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '部门名称',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `org_name` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '机构名称',
  `statistical_time` datetime DEFAULT NULL COMMENT '统计日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `last_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8191 DEFAULT CHARSET=utf8;

-- 新增表webclip
DROP TABLE IF EXISTS `webclip`;
CREATE TABLE `webclip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lable` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `icon_path` varchar(100) DEFAULT NULL,
  `is_remove` char(1) DEFAULT NULL,
  `precompose_icon` char(1) DEFAULT NULL,
  `full_screen` char(1) DEFAULT NULL,
  `ios_device_policy_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表wifi_configure
DROP TABLE IF EXISTS `wifi_configure`;
CREATE TABLE `wifi_configure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ssid` varchar(100) NOT NULL,
  `is_autojoin` char(1) DEFAULT NULL,
  `is_hiddennetwork` char(1) DEFAULT NULL,
  `security_type` char(1) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `eap_method` char(1) DEFAULT NULL,
  `stage_authentication` char(1) DEFAULT NULL,
  `ca_certificate` char(1) DEFAULT NULL,
  `user_certificate` char(1) DEFAULT NULL,
  `identity` varchar(50) DEFAULT NULL,
  `anonymous_identity` char(50) DEFAULT NULL,
  `android_device_policy_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `aa` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表authenticate_info
DROP TABLE IF EXISTS `authenticate_info`;
CREATE TABLE `authenticate_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(250) NOT NULL,
  `udid` varchar(250) NOT NULL,
  `control` char(10) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表 command
DROP TABLE IF EXISTS `command`;
CREATE TABLE `command` (
  `id` varchar(255) NOT NULL,
  `device_id` varchar(255) DEFAULT NULL,
  `command` varchar(100) DEFAULT NULL,
  `command_id` int(11) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `doIt` varchar(30) DEFAULT NULL,
  `result` varchar(100) DEFAULT NULL,
  `class_name` varchar(200) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表ios_policy_department
DROP TABLE IF EXISTS `ios_policy_department`;
CREATE TABLE `ios_policy_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_structure_id` int(11) DEFAULT NULL COMMENT '策略id',
  `ios_device_policy_id` int(11) DEFAULT NULL COMMENT '部门id',
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表 ios_policy_virtual
DROP TABLE IF EXISTS `ios_policy_virtual`;
CREATE TABLE `ios_policy_virtual` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `virtual_group_id` int(11) DEFAULT NULL COMMENT '策略id',
  `ios_device_policy_id` int(11) DEFAULT NULL COMMENT '部门id',
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增表 token_update_info
DROP TABLE IF EXISTS `token_update_info`;
CREATE TABLE `token_update_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `push_magic` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `topic` varchar(255) NOT NULL,
  `udid` varchar(255) NOT NULL,
  `unlock_token` text NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ios_uuid` varchar(100) DEFAULT NULL,
  `is_profile` int(2) DEFAULT NULL COMMENT '是否安装描述文件',
  `payload_identifier` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 删除表backup
DROP TABLE IF EXISTS `backup`;

-- 删除表device_location
DROP TABLE IF EXISTS `device_location`;

-- 删除表my_test
DROP TABLE IF EXISTS `my_test`;

-- 删除表device_log
DROP TABLE IF EXISTS `device_log`;

-- 删除表device_notify
DROP TABLE IF EXISTS `device_notify`;

-- 删除表device_policy
DROP TABLE IF EXISTS `device_policy`;

-- 删除表device_rule_dictionary
DROP TABLE IF EXISTS `device_rule_dictionary`;

-- 删除表device_rule_general_dictionary
DROP TABLE IF EXISTS `device_rule_general_dictionary`;

-- 删除表org_app_authorize
DROP TABLE IF EXISTS `org_app_authorize`;

DROP TABLE IF EXISTS `ios_policy_users`;

-- 删除表push_msg.sql
DROP TABLE IF EXISTS `push_msg.sql`;

-- 删除表sysmanage_feedback
DROP TABLE IF EXISTS `sysmanage_feedback`;

-- 删除表sysmanage_orgmanager_org_relation
DROP TABLE IF EXISTS `sysmanage_orgmanager_org_relation`;

-- 删除表sys_message_notify
DROP TABLE IF EXISTS `sys_message_notify`;

-- 删除表virtual_app_authorize
DROP TABLE IF EXISTS `virtual_app_authorize`;

-- android_black_list id 自增长
alter table android_black_list modify column id int(9) NOT NULL AUTO_INCREMENT;

-- android_device_department id 自增长
alter table android_device_department modify column id int(11) NOT NULL AUTO_INCREMENT;


-- android_device_policy
alter table android_device_policy modify column id int(11) NOT NULL AUTO_INCREMENT;
alter table android_device_policy add column visit_time_str text;

-- android_device_users
alter table android_device_users modify column id int(11) NOT NULL AUTO_INCREMENT;


-- android_device_virtual_group
alter table android_device_virtual_group modify column id int(11) NOT NULL AUTO_INCREMENT;

-- android_policy_list
alter table android_policy_list modify column id int (11) NOT NULL AUTO_INCREMENT;

-- app_list
alter table app_list modify column id int(11) NOT NULL AUTO_INCREMENT;

-- application_name_list
alter table application_name_list modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_app_info
alter table device_app_info modify column id int(11) NOT NULL AUTO_INCREMENT;
alter table device_app_info modify column appid varchar(200) DEFAULT NULL COMMENT 'appid';

-- device_basic_info
alter table device_basic_info modify column id int(11) NOT NULL AUTO_INCREMENT;
alter table device_basic_info modify column user_id int(11) DEFAULT NULL COMMENT '用户';

ALTER TABLE `device_basic_info`
ADD COLUMN `terminal_unbund_enable` int(2) DEFAULT NULL,
ADD COLUMN `device_unique_id` varchar(1000) DEFAULT NULL,
ADD COLUMN `longitude` varchar(100) DEFAULT NULL,
ADD COLUMN `latitude` varchar(60) DEFAULT NULL,
ADD COLUMN `flux` varchar(60) DEFAULT NULL,
ADD COLUMN `imsi` varchar(60) DEFAULT NULL;


-- device_config_file
alter table device_config_file modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_legal_list
alter table device_legal_list modify column id int(11) NOT NULL AUTO_INCREMENT;
alter table device_legal_list modify column delete_time datetime DEFAULT NULL COMMENT '删除时间';

-- device_legal_record
alter table device_legal_record modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_network_status
alter table device_network_status modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule
alter table device_rule modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_department
alter table device_rule_department modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_item_record
alter table device_rule_item_record modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_item_relation
alter table device_rule_item_relation modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_operation_item_record
alter table device_rule_operation_item_record modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_operation_item_relation
alter table device_rule_operation_item_relation modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_user
alter table device_rule_user modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_rule_virtual_group
alter table device_rule_virtual_group modify column id int(11) NOT NULL AUTO_INCREMENT;

-- device_safty
alter table device_safty modify column id int(11) NOT NULL AUTO_INCREMENT;

-- name_list
alter table name_list modify column id int(11) NOT NULL AUTO_INCREMENT;

-- org_structure
alter table org_structure modify column id int(9) NOT NULL AUTO_INCREMENT;

-- netbehavior_loginfo
drop PROCEDURE if exists netbehavior_loginfo_gj;
alter table netbehavior_loginfo add column department_id int(9) DEFAULT NULL COMMENT '部门ID';
CREATE PROCEDURE netbehavior_loginfo_gj()
BEGIN
-- 需要定义接收游标数据的变量 
  DECLARE deptName CHAR(255);
  -- 遍历数据结束标志
  DECLARE done INT DEFAULT FALSE;
-- 游标
  DECLARE cur CURSOR FOR select distinct(department_name) department_name from netbehavior_loginfo;
  -- 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  -- 打开游标
  OPEN cur;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据，这里只有一个，多个的话也一样；
    FETCH cur INTO deptName;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件
    UPDATE netbehavior_loginfo SET department_id = (SELECT ID FROM org_structure WHERE NAME =deptName) where department_name=deptName;
  END LOOP;
  -- 关闭游标
  CLOSE cur;
END;
call netbehavior_loginfo_gj();

-- netbehavior_whiteblacklist bak
create table netbehavior_whiteblacklist_bak as select * from netbehavior_whiteblacklist;
-- netbehavior_whiteblacklist update
alter table netbehavior_whiteblacklist modify column id int(9) NOT NULL AUTO_INCREMENT COMMENT '黑白名id';
alter table netbehavior_whiteblacklist add column create_user int(9) DEFAULT NULL COMMENT '创建人ID';
alter table netbehavior_whiteblacklist add column update_user int(9) DEFAULT NULL COMMENT '更新ID';
alter table netbehavior_whiteblacklist add column create_realname varchar(255) DEFAULT NULL COMMENT '创建人真实姓名';
alter table netbehavior_whiteblacklist add column update_realname varchar(255) DEFAULT NULL COMMENT '更新真实姓名';
drop PROCEDURE if exists netbehavior_whiteblacklist_gj;
CREATE PROCEDURE netbehavior_whiteblacklist_gj()
BEGIN
  -- 需要定义接收游标数据的变量 
  DECLARE userID int(11);
  DECLARE userName varchar(255);
  DECLARE realName varchar(255);
  -- 遍历数据结束标志
  DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE user_cur CURSOR FOR select id,user_name from users;

  -- 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  -- 打开游标
  OPEN user_cur;
  
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据，这里只有一个，多个的话也一样；
    FETCH user_cur INTO userID,userName;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件
    update netbehavior_whiteblacklist set create_user=id,create_realname=userName where create_username=userName;
	  update netbehavior_whiteblacklist set update_user=id,update_realname=userName where update_username=userName;
  END LOOP;
  -- 关闭游标
  CLOSE user_cur;
END;
call netbehavior_whiteblacklist_gj();
alter table netbehavior_whiteblacklist drop column create_username;
alter table netbehavior_whiteblacklist drop column update_username;


-- org_managers
alter table org_managers modify column id int(9) NOT NULL AUTO_INCREMENT;
alter table org_managers add column status char(1) DEFAULT '0' COMMENT '机构管理员状态';
alter table org_managers add column last_login_time datetime DEFAULT NULL COMMENT '用户最后登录时间';
alter table org_managers add column auth int(11) DEFAULT '1' COMMENT '操作权限';

-- organization
alter table organization modify column id int(9) NOT NULL AUTO_INCREMENT;
alter table organization modify column org_type varchar(255) DEFAULT '0' COMMENT '机构编码';
alter table organization modify column name varchar(255) DEFAULT NULL COMMENT '机构名称';
alter table organization modify column update_time datetime DEFAULT NULL COMMENT '最后更新时间';
alter table organization modify column update_time datetime DEFAULT NULL COMMENT '最后更新时间';
alter table organization modify column delete_time datetime DEFAULT NULL COMMENT '删除时间';
alter table organization add column status char(1) DEFAULT NULL COMMENT '机构状态';

-- push_msg
alter table push_msg modify column pic_url varchar(1000) DEFAULT NULL COMMENT '图片路径';
alter table push_msg drop column create_user;
alter table push_msg drop column update_user;
alter table push_msg add column departIds varchar(2000) DEFAULT NULL COMMENT '部门IDs';
alter table push_msg add column virtualIds varchar(2000) DEFAULT NULL COMMENT '虚拟组IDs';
alter table push_msg add column userIds varchar(2000) DEFAULT NULL COMMENT '用户IDs';
alter table push_msg add column create_by int(9) DEFAULT NULL COMMENT '创建人id';
alter table push_msg add column update_by int(9) DEFAULT NULL COMMENT '修改人id';
alter table push_msg add column create_realname varchar(255) DEFAULT NULL COMMENT '创建人姓名';
alter table push_msg add column update_realname varchar(255) DEFAULT NULL COMMENT '修改人姓名';

-- push_msg_history
alter table push_msg_history modify column udid varchar(255) DEFAULT NULL COMMENT '推送设备唯一编号';
alter table push_msg_history modify column id int(11) NOT NULL AUTO_INCREMENT;

-- role_menu
alter table role_menu modify column id int(9) NOT NULL AUTO_INCREMENT COMMENT 'id';

-- send_message
alter table send_message modify column id int(11) NOT NULL AUTO_INCREMENT;

-- sysmanage_device_log
alter table sysmanage_device_log modify column id int(9) NOT NULL AUTO_INCREMENT;
alter table sysmanage_device_log add column user_name varchar(100) DEFAULT NULL;
alter table sysmanage_device_log modify column event_type varchar(20) DEFAULT NULL;
alter table sysmanage_device_log add column device_name varchar(50) DEFAULT NULL;

-- sysmanage_log
alter table sysmanage_log modify column id int(11) NOT NULL AUTO_INCREMENT;
alter table sysmanage_log add column user_name varchar(100) DEFAULT NULL;
alter table sysmanage_log modify column user_type varchar(100) DEFAULT NULL;

-- sysmanage_org_setting
alter table sysmanage_org_setting modify column id int(11) NOT NULL AUTO_INCREMENT;

-- user_department
alter table user_department modify column id int(9) NOT NULL AUTO_INCREMENT;

-- user_policy
alter table user_policy modify column id int(9) NOT NULL AUTO_INCREMENT;

-- user_role
alter table user_role modify column  id int(9) NOT NULL AUTO_INCREMENT COMMENT '角色id';

-- user_role_department
alter table user_role_department modify column id int(11) NOT NULL AUTO_INCREMENT;


-- userdevice_policy

-- users
alter table users modify column id int(9) NOT NULL AUTO_INCREMENT;
alter table users modify column office_phone varchar(20) DEFAULT NULL COMMENT '公司电话';
alter table users modify column position varchar(20) DEFAULT NULL COMMENT '公司电话';
alter table users add column create_type int(2) DEFAULT NULL COMMENT '创建方式(0为管理员手工创建，1为管理员excel导入)';
alter table users add column device_login_count int(11) DEFAULT NULL COMMENT '设备登录次数';

-- users_role
alter table users_role modify column id int(9) NOT NULL AUTO_INCREMENT;

-- users_virtual_group
alter table users_virtual_group modify column id int(9) NOT NULL AUTO_INCREMENT;

-- virtual_collection
alter table virtual_collection modify column id int(9) NOT NULL AUTO_INCREMENT;

-- virtual_group
alter table virtual_group modify column id int(9) NOT NULL AUTO_INCREMENT;

UPDATE menu set `name`='流量统计' ,address='/systemStatistics/flux' where id=857412355 ;
UPDATE menu set address='/systemStatistics/device' where id=857412354 ;
UPDATE menu set address='/systemStatistics/user' where id=857412353 ;


-- 请在此后面添加DB脚本


