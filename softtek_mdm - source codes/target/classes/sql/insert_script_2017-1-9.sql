
-- ====================================mdm version 4.0 update========================================================
-- add by jing.liu  add date 2017-1-9
CREATE TABLE t_user_time (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT NULL COMMENT '用户id',
  device_id int(11) DEFAULT NULL COMMENT '设备Id',
  org_id int(11) DEFAULT NULL COMMENT '机构Id',
  zero int(2),
  one int(2),
  two int(2),
  three int(2),
  four int(2),
  five int(2),
  six int(2),
  seven int(2),
  eight int(2),
  nine int(2),
  ten int(2),
  eleven int(2),
  twelve int(2),
  thirteen int(2),
  fourteen int(2),
  fifteen int(2),
  sixteen int(2),
  seventeen int(2),
  eighteen int(2),
  nineteen int(2),
  twenty int(2),
  twenty_one int(2),
  twenty_two int(2),
  twenty_three int(2),
  create_time datetime DEFAULT NULL,
  create_by int(11) DEFAULT NULL,
  update_time datetime DEFAULT NULL,
  update_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);
insert into menu(id,parent_id,name,address,weight,update_time,create_time,isshow)values(857412380,857412352,'tiles.aside.menu.userTime','',52,now(),now(),1);

insert into menu(id,parent_id,name,address,weight,update_time,create_time,isshow)values(857412381,857412352,'tiles.aside.menu.device.flux.abnormal','',53,now(),now(),1);
-- 2017-1-9  josen.yang 
-- add 用户个人流量表
DROP TABLE IF EXISTS `user_flux`;
CREATE TABLE `user_flux` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `device_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备名称',
  `device_type` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备类型',
  `sn` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT 'sn号',
  `esnorimei` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT 'imei号',
  `device_status` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `flux` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备流量',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id 对应users表',
  `device_basic_info_id` int(11) DEFAULT NULL COMMENT '设备信息表主键索引',
  `statistical_time` datetime DEFAULT NULL COMMENT '采集时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_abnormal` int(2) DEFAULT '0' COMMENT '是否异常 0:否  1:异常',
  `is_report` int(2) DEFAULT '0' COMMENT '是否上报 0： 没有上报  1：已经上报应用信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- 2017-1-9  josen.yang 
-- add 用户个人流量应用详情表

DROP TABLE IF EXISTS `user_flux_detail`;
CREATE TABLE `user_flux_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_flux` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备流量',
  `sn` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '设备sn号唯一标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id 对应users表',
  `statistical_time` datetime DEFAULT NULL COMMENT '采集时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `appid` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT 'appid',
  `app_version` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'app版本',
  `app_status` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '应用状态',
  `app_belong` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '归属',
  `device_id` int(11) DEFAULT NULL COMMENT '所属设备id',
  `app_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用名称',
  `user_flux_id` int(11) DEFAULT NULL COMMENT '流量信息异常主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



INSERT INTO menu (
	id,
	parent_id,
	name,
	weight,
	update_time,
	create_time,
	isshow
);
VALUES
	(
		857412381,
		857412352,
		'tiles.aside.menu.device.flux.abnormal',
		53,
		'2017-01-10',
		'2017-01-10',
		1
	);
	
		
-- 2017/1/18 netbehavior_loginfo table add keywords(关键字)、description(描述)、 website_type(网站类型) by jane.hui
alter table netbehavior_loginfo add keywords text comment '关键字';
alter table netbehavior_loginfo add description text comment '描述';
alter table netbehavior_loginfo add website_type text comment '网站类型';

-- 2017/1/18 add website_classify(网站分类表) by jane.hui
DROP TABLE IF EXISTS `website_classify`;
CREATE TABLE `website_classify` (
  `id` int(11) NOT NULL COMMENT '主键id',
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '网站名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `website_classify` VALUES ('0', '其它');
INSERT INTO `website_classify` VALUES ('1', '资讯');
INSERT INTO `website_classify` VALUES ('2', '新闻');
INSERT INTO `website_classify` VALUES ('3', '视频');
INSERT INTO `website_classify` VALUES ('4', '门户');
INSERT INTO `website_classify` VALUES ('5', '游戏');
INSERT INTO `website_classify` VALUES ('6', '娱乐');
INSERT INTO `website_classify` VALUES ('7', '购物');
INSERT INTO `website_classify` VALUES ('8', '音乐');
INSERT INTO `website_classify` VALUES ('9', '旅游');
INSERT INTO `website_classify` VALUES ('10', '房产');
INSERT INTO `website_classify` VALUES ('11', '汽车');
INSERT INTO `website_classify` VALUES ('12', '酒店');
INSERT INTO `website_classify` VALUES ('13', '团购');
INSERT INTO `website_classify` VALUES ('14', '股票');
INSERT INTO `website_classify` VALUES ('15', '教育');
INSERT INTO `website_classify` VALUES ('16', '政府');
INSERT INTO `website_classify` VALUES ('17', '招聘');
INSERT INTO `website_classify` VALUES ('18', '博客');
-- 2017/1/18 add system_words(系统词库表) by jane.hui
DROP TABLE IF EXISTS `system_words`;
CREATE TABLE `system_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '词库名称',
  `website_classify_id` int(11) NOT NULL COMMENT '网站分类外键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) DEFAULT NULL COMMENT '创建者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` int(11) DEFAULT NULL COMMENT '更新者',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `org_id` int(11) DEFAULT NULL COMMENT '机构外键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2017/1/18 add menu(菜单表) by jane.hui
INSERT INTO menu (
    id,
    parent_id,
    name,
    address,
    weight,
    update_time,
    create_time,
    isshow
);
VALUES
(
    857412382,
    857412370,
    'tiles.aside.menu.systemwords',
    '/words',
    54,
    '2017-01-10',
    '2017-01-10',
    1
);
INSERT INTO menu (
    id,
    parent_id,
    name,
    address,
    weight,
    update_time,
    create_time,
    isshow
)
VALUES
(
    857412383,
    857412352,
    'tiles.aside.menu.net',
    '/net',
    55,
    '2017-01-10',
    '2017-01-10',
    1
);

-- add by jing.liu add date 2017-01-22 remark:save mobileconfig file's path
alter table users add config_file varchar(500) comment '未签名描述文件的路径';
alter table users add sign_config_file varchar(500) comment '已签名描述文件的路径';

-- 新增ios_policy_list表
DROP TABLE IF EXISTS `ios_policy_list`;
CREATE TABLE `ios_policy_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT comment '主键',
  `ios_device_policy_id` int(11) DEFAULT NULL comment 'ios设备策略外键',
  `name_list_id` int(11) DEFAULT NULL comment '名单列表id',
  `create_date` datetime DEFAULT NULL comment '创建时间',
  `create_user` int(11) DEFAULT NULL comment '创建者',
  `update_date` datetime DEFAULT NULL comment '更新时间',
  `update_user` int(11) DEFAULT NULL comment '更新者',
  `delete_time` datetime DEFAULT NULL comment '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 新增ios设备策略与黑白名单关联表
DROP TABLE IF EXISTS `ios_black_list`;
CREATE TABLE `ios_black_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT comment '主键',
  `ios_device_policy_id` int(11) DEFAULT NULL comment 'ios设备策略外键',
  `name_list_id` int(11) DEFAULT NULL comment '名单列表id',
  `create_date` datetime DEFAULT NULL comment '创建时间',
  `create_user` int(11) DEFAULT NULL comment '创建者',
  `update_date` datetime DEFAULT NULL comment '更新时间',
  `update_user` int(11) DEFAULT NULL comment '更新者',
  `delete_time` datetime DEFAULT NULL comment '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 新增ios_device_policy->ios设备策略表 by jane.hui 2017/2/6
DROP TABLE IF EXISTS `ios_device_policy`;
CREATE TABLE `ios_device_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_enable` char(1) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
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
  `allow_open_from_managed_to_unmanaged` char(1) DEFAULT NULL COMMENT '允许受管控的应用打开不受管控的应用的文档',
  `allow_open_from_unmanaged_to_managed` char(1) DEFAULT NULL COMMENT '允许不受管控的应用打开受管控的应用的文档',
  `allow_global_background_fetch_when_roaming` char(1) DEFAULT NULL COMMENT '允许在漫游时自动同步',
  `allow_assistant_while_locked` char(1) DEFAULT NULL COMMENT '设备锁定时允许使用Siri',
  `allow_lock_screen_today_view` char(1) DEFAULT NULL COMMENT '允许锁屏时显示TodayView的消息',
  `allow_cloud_keychain_sync` char(1) DEFAULT NULL COMMENT 'icloud同步钥匙串',
  `allow_lock_screen_control_center` char(1) DEFAULT NULL COMMENT '允许锁屏时显示控制中心的消息',
  `allow_finger_print_for_unlock` char(1) DEFAULT NULL COMMENT '允许指纹解锁',
  `allow_lock_screen_notifications_view` char(1) DEFAULT NULL COMMENT '允许锁屏时显示通知消息',
  `allow_managed_apps_cloud_sync` char(1) DEFAULT NULL COMMENT '允许被管理的应用将数据存储到iCloud',
  `allow_cloud_photo_library` char(1) DEFAULT NULL COMMENT '允许iCloud照片图库',
  `allow_shared_stream` char(2) DEFAULT NULL COMMENT '允许iCloud照片共享',
  `allow_activity_continuation` char(1) DEFAULT NULL COMMENT '允许使用handoff',
  `allow_adding_game_center_friends` char(1) DEFAULT NULL COMMENT '允许添加 Game Center好友',
  `allow_multiplayer_gaming` char(1) DEFAULT NULL COMMENT '允许多人游戏',
  `is_enable_password` char(1) DEFAULT NULL COMMENT '是否启用密码(1.启用 0.不启用)',
  `is_net_limit` char(1) DEFAULT NULL COMMENT '是否上网限制标识(1.限制上网 0.不限制上网)',
  `visit_time_str` char(255) DEFAULT NULL COMMENT '上网时间段字符串',
  `enable_blacklist` char(1) DEFAULT NULL COMMENT '是否启用网页黑名单(1.启用白名单 0.启用黑名单)',
  `enable_app_name_list` char(1) DEFAULT NULL COMMENT '是否启用应用黑名单(0.启用白名单  1.启用黑名单)',
  `version` varchar(64) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- 新增设备策略推送表(ios_device_push) by jane.hui 2017/2/6
DROP TABLE IF EXISTS `ios_device_push`;
CREATE TABLE `ios_device_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `udid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `topic` varchar(100) COLLATE utf8_unicode_ci DEFAULT '',
  `control` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `push_magic` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unlock_token` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- 新增设备策略与部门关联表 (ios_policy_department) by jane.hui 2017/2/6
DROP TABLE IF EXISTS `ios_policy_department`;
CREATE TABLE `ios_policy_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_policy_id` varchar(60) DEFAULT NULL COMMENT '策略id',
  `depart_id` varchar(60) DEFAULT NULL COMMENT '部门id',
  `ios_device_policy_id` int(9) DEFAULT NULL,
  `org_structure_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 新增设备策略与用户关联表 (ios_policy_user) by jane.hui 2017/2/6
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 新增设备策略与虚拟组关联表 (ios_policy_virtual) by jane.hui 2017/2/6
DROP TABLE IF EXISTS `ios_policy_virtual`;
CREATE TABLE `ios_policy_virtual` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ios_device_policy_id` int(11) DEFAULT NULL,
  `virtual_group_id` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 新增ios设备策略配置表 (ios_wifi_configure) by jane.hui 2017/2/6
DROP TABLE IF EXISTS `ios_wifi_configure`;
CREATE TABLE `ios_wifi_configure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ssid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_autojoin` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_hiddennetwork` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `security_type` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `eap_method` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `stage_authentication` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ca_certificate` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_certificate` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `identity` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `anonymous_identity` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `agent_server` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_port` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_appraisal` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agent_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ios_device_policy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- modify app icon_path length by jane.hui 
alter table app modify column icon_path varchar(255);

-- insert v_device_policy view by jane.hui 2017/2/20
CREATE view v_device_policy   
as 
select 
      t1.id,
      t1.name,
      0 as type,
      t1.description,
      t1.is_enable as isEnable,
      t1.org_id as orgId,
      t1.create_user as createBy,
      date_format(t1.create_date,'%Y-%m-%d %H:%i:%S') as createDate,
      date_format(t1.update_date,'%Y-%m-%d %H:%i:%S') as updateDate,   
      t1.update_user as updateBy,
      (select count(t8.id) from device_basic_info t8 where t8.user_id in (
         select 
         DISTINCT(t2.id) 
        from 
          users t2 
        where 
         (t2.id in (select t3.users_id from android_device_users t3 where t3.android_device_policy_id = t1.id and t3.delete_time is null)
        or t2.group_id in (select t4.org_structure_id from android_device_department t4 where t4.android_device_policy_id = t1.id and t4.delete_time is null) 
        or t2.id in (
        SELECT t5.user_id from users_virtual_group t5 
        inner join android_device_virtual_group t6 
        on t5.virtual_id = t6.virtual_group_id 
         where t6.android_device_policy_id = t1.id and t5.delete_time is null and t6.delete_time is null and t5.org_id =t1.org_id)) 
         and t2.org_id = t1.org_id and t2.delete_time is null 
        ) and t8.delete_time is null and t8.device_type = 'android') as assignedCount  
    from 
      android_device_policy t1 
    where 
      t1.delete_time is null 
    UNION 
    select 
      t11.id,
      t11.name,
      1 as type,
      t11.description,
      t11.is_enable as isEnable,
      t11.org_id as orgId,
      t11.create_user as createBy,
      date_format(t11.create_date,'%Y-%m-%d %H:%i:%S') as createDate,
      date_format(t11.update_date,'%Y-%m-%d %H:%i:%S') as updateDate,   
      t11.update_user as updateBy,
      (select count(t18.id) from device_basic_info t18 where t18.user_id in (
         select 
         DISTINCT(t12.id) 
        from 
          users t12 
        where 
         (t12.id in (select t13.user_id from ios_policy_user t13 where t13.ios_device_policy_id = t11.id and t13.delete_time is null)
        or t12.group_id in (select t14.org_structure_id from ios_policy_department t14 where t14.ios_device_policy_id = t11.id and t14.delete_time is null) 
        or t12.id in (
        SELECT t15.user_id from users_virtual_group t15 
        inner join ios_policy_virtual t16 
        on t15.virtual_id = t16.virtual_group_id 
         where t16.ios_device_policy_id = t11.id and t15.delete_time is null and t16.delete_time is null and t15.org_id =t11.org_id)) 
         and t12.org_id = t11.org_id and t12.delete_time is null 
        ) and t18.delete_time is null and t18.device_type = 'ios') as assignedCount  
    from 
      ios_device_policy t11  
    where 
      t11.delete_time is null 

-- add index by jane.hui 2017/2/22 start 
   ALTER TABLE device_basic_info ADD INDEX user_id (`user_id`);
   ALTER TABLE users ADD Unique (`id`);
   ALTER TABLE users ADD INDEX group_id(`group_id`);
   ALTER TABLE android_device_department ADD INDEX android_device_policy_id(android_device_policy_id);
   ALTER TABLE android_device_virtual_group ADD INDEX android_device_policy_id(android_device_policy_id);
   ALTER TABLE android_device_users ADD INDEX android_device_policy_id(android_device_policy_id);
   ALTER TABLE ios_policy_department ADD INDEX ios_device_policy_id(ios_device_policy_id);
   ALTER TABLE ios_policy_virtual ADD INDEX ios_device_policy_id(ios_device_policy_id);
   ALTER TABLE ios_policy_user ADD INDEX ios_device_policy_id(ios_device_policy_id);
-- add index by jane.hui 2017/2/22 end 
      
-- add trackId,formattedPrice by jane.hui 2017/2/24 start
   alter table users add config_file varchar(500) comment '未签名描述文件的路径';
   
-- add trackId,formattedPrice by jane.hui 2017/2/24 end
   alter table app add track_id varchar(100) comment 'iTunesStoreID';
   alter table app add formatted_price varchar(100) comment '格式化后的价格';
   
   
-- add by jing.liu add date 2017-03-02  add column and delete column
   alter table send_message add column device_id int(11) comment '设备id';
   
   alter table push_msg add column push_time datetime comment '推送时间';
   
    -- add device_id BY jane.hui 2017/3/10 
   alter table netbehavior_loginfo add column device_id int(11) comment '设备id';
   
    -- add device_id BY josen.yang 2017/3/13 
   alter table users_statistics add column group_id int(11) comment '部门id';
   
   -- alter table android_device_policy add allowUseGPS by jane.hui 2017/3/14
   alter table android_device_policy add column allow_use_gps char(1) comment '允许使用GPS';
   
   -- alter table token_update_info add uuid by jane.hui 2017/3/17
   alter table token_update_info add column uuid varchar(100) comment 'uuid';
   -- alter table deleted_device add device_id by josen.yang 2017/3/27
   alter table deleted_device add column device_id int(11) comment '设备主键Id';
   
   