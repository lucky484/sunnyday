-- 设置表android_black_list主键自增长
ALTER TABLE `android_black_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表android_black_list主键自增长
ALTER TABLE `android_device_department` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表android_black_list主键自增长
ALTER TABLE `android_device_policy` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表android_device_users主键自增长
ALTER TABLE `android_device_users` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表android_device_virtual_group主键自增长
ALTER TABLE `android_device_virtual_group` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表android_device_virtual_group主键自增长
ALTER TABLE `android_policy_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表android_device_virtual_group主键自增长
ALTER TABLE `android_policy_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表app主键自增长
ALTER TABLE `app` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表app主键自增长
ALTER TABLE `app_department_authorization` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表app主键自增长
ALTER TABLE `app_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表app_virtual_group_authorization主键自增长
ALTER TABLE `app_virtual_group_authorization` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表application_name_list主键自增长
ALTER TABLE `application_name_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表client_command主键自增长
ALTER TABLE `client_command` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表client_config主键自增长
ALTER TABLE `client_config` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表client_manager主键自增长
ALTER TABLE `client_manager` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表deleted_device主键自增长
ALTER TABLE `deleted_device` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表deleted_users主键自增长
ALTER TABLE `deleted_users` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `device_basic_info` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表department_pushmsg_relation主键自增长
ALTER TABLE `department_pushmsg_relation` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表departmet_netblackwhitelist_relation主键自增长
ALTER TABLE `departmet_netblackwhitelist_relation` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_app_info主键自增长
ALTER TABLE `device_app_info` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表device_config_file主键自增长
ALTER TABLE `device_config_file` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_app_info主键自增长
ALTER TABLE `device_legal_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表device_legal_record主键自增长
ALTER TABLE `device_legal_record` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_location_record主键自增长
ALTER TABLE `device_location_record` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_network_status主键自增长
ALTER TABLE `device_network_status` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule主键自增长
ALTER TABLE `device_rule` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表device_rule_department主键自增长
ALTER TABLE `device_rule_department` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_item_record主键自增长
ALTER TABLE `device_rule_item_record` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_item_relation主键自增长
ALTER TABLE `device_rule_item_relation` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_match主键自增长
ALTER TABLE `device_rule_match` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_operation_item_record主键自增长
ALTER TABLE `device_rule_operation_item_record` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_operation_item_relation主键自增长
ALTER TABLE `device_rule_operation_item_relation` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_user主键自增长
ALTER TABLE `device_rule_user` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_virtual_group主键自增长
ALTER TABLE `device_rule_virtual_group` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_safty主键自增长
ALTER TABLE `device_safty` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表device_rule_general_dictionary主键自增长
-- ALTER TABLE `device_rule_general_dictionary` CHANGE 
-- id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表filter_url主键自增长
ALTER TABLE `filter_url` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表flux主键自增长
ALTER TABLE `flux` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表ios_device_policy主键自增长
ALTER TABLE `ios_device_policy` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表ios_device_push主键自增长
ALTER TABLE `ios_device_push` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表ios_policy_department主键自增长
ALTER TABLE `ios_policy_department` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表ios_policy_user主键自增长
ALTER TABLE `ios_policy_user` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表ios_policy_virtual主键自增长
ALTER TABLE `ios_policy_virtual` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表ios_wifi_configure主键自增长
ALTER TABLE `ios_wifi_configure` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表name_list主键自增长
ALTER TABLE `name_list` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表netbehavior_loginfo主键自增长
ALTER TABLE `netbehavior_loginfo` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表netbehavior_urllimit主键自增长
ALTER TABLE `netbehavior_urllimit` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表netbehavior_whiteblacklist主键自增长
ALTER TABLE `netbehavior_whiteblacklist` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表organization主键自增长
ALTER TABLE `organization` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表org_managers主键自增长
ALTER TABLE `org_managers` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表org_structure主键自增长
ALTER TABLE `org_structure` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表organization_manager_relation主键自增长
ALTER TABLE `organization_manager_relation` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表push_msg主键自增长
ALTER TABLE `push_msg` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表push_msg_history主键自增长
ALTER TABLE `push_msg_history` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表role_menu主键自增长
ALTER TABLE `role_menu` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表security_event_log主键自增长
ALTER TABLE `security_event_log` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表send_message主键自增长
ALTER TABLE `send_message` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 注意send_message的引擎需要改成InnoDB

-- 设置表sysmanage_device_log主键自增长
ALTER TABLE `sysmanage_device_log` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表send_message主键自增长
ALTER TABLE `sysmanage_log` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表sysmanage_org_setting主键自增长
ALTER TABLE `sysmanage_org_setting` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表sysmanage_org_setting主键自增长
ALTER TABLE `sysmanage_org_setting` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表user_app主键自增长
ALTER TABLE `user_app` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表user_auth主键自增长
ALTER TABLE `user_auth` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表user_department主键自增长
ALTER TABLE `user_department` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 设置表user_policy主键自增长
ALTER TABLE `user_policy` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表user_role主键自增长
ALTER TABLE `user_role` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表user_role_department主键自增长
ALTER TABLE `user_role_department` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表users主键自增长
ALTER TABLE `users` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表users主键自增长
ALTER TABLE `users_role` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表users_statistics主键自增长
ALTER TABLE `users_statistics` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表users_virtual_group主键自增长
ALTER TABLE `users_virtual_group` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表virtual_collection主键自增长
ALTER TABLE `virtual_collection` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表virtual_group主键自增长
ALTER TABLE `virtual_group` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表webclip主键自增长
ALTER TABLE `webclip` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

-- 设置表webclip主键自增长
ALTER TABLE `wifi_configure` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `authenticate_info` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `command` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `token_update_info` CHANGE 
id id int(11) NOT NULL AUTO_INCREMENT;


-- 请在此后面添加DB脚本
