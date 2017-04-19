insert into authority values(56,'add_customer_button','添加客户按钮',null,'客户管理');
insert into role_authority(role_id,authority_id) values(1,56),(3,56),(3,17),(3,19),(3,30),(3,31);
alter table project modify project_type varchar(45) default null;
alter table project add delete_time datetime default null;
alter table CR add delete_time datetime default null;
alter table customer add delete_time datetime default null;
alter table user add delete_time datetime default null;
alter table project_file add delete_time datetime default null;
alter table cr_file add delete_time datetime default null;
