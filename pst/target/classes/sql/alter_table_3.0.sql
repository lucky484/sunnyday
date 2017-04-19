-- 项目表添加字段
alter table `project_status_tracking`.`project` add COLUMN cost double(10,2) default 0 COMMENT '成本支出';
alter table `project_status_tracking`.`project` add COLUMN returned_status varchar(255) default 0 COMMENT '回款状态';
-- 删除外键
ALTER TABLE `project_status_tracking`.`project` DROP FOREIGN KEY `project_user_fk`;
ALTER TABLE `project_status_tracking`.`project` DROP INDEX `project_user_fk_idx` ;
-- 添加权限
insert into `project_status_tracking`.`authority` values(52,'finance_list','财务管理页面','/projectsManagement/finance/financeList.do','项目管理'),
(53,'getFinances','财务管理查询','/projectsManagement/finance/getFinances.do','项目管理'),
(54,'upateFinance','更新财务信息页面','/projectsManagement/finance/updateFinance.do','项目管理'),
(55,'update_finance','更新财务','/projectsManagement/finance/update.do','项目管理');
insert into `project_status_tracking`.`role_authority`(role_id,authority_id) values(1,52),(1,53),(1,54),(1,55);
insert into `project_status_tracking`.`role_authority`(role_id,authority_id) values
  (3,1),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),(3,9),(3,10),(3,11),(3,12),(3,14),(3,16),
  (3,22),(3,24),(3,25),(3,26),(3,27),(3,28),(3,33),(3,34),(3,35),(3,36),(3,37),(3,38),(3,41),
  (3,44),(3,50),(3,51),(3,15),(3,20),(3,21),(3,29),(3,32),(3,39),(3,43);