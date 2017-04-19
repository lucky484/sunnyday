alter table project_status_tracking.customer add COLUMN other_contact_way varchar(100) NULL COMMENT '其他联系方式';
alter table project_status_tracking.customer CHANGE COLUMN customer_title customer_title varchar(60) null COMMENT '客户头衔';
alter table project_status_tracking.customer CHANGE COLUMN company_address company_address varchar(100) null COMMENT '公司地址';
alter table project_status_tracking.customer CHANGE COLUMN company_phone company_phone varchar(45) null COMMENT '公司电话';
alter table project_status_tracking.customer CHANGE COLUMN bank bank varchar(200) null COMMENT '开户银行';
alter table project_status_tracking.customer CHANGE COLUMN payment_account payment_account varchar(60) null COMMENT '付款账号';
alter table project_status_tracking.customer CHANGE COLUMN company_id_number company_id_number varchar(60) null COMMENT '公司税号';
alter table project_status_tracking.project CHANGE COLUMN remark remark text null COMMENT '备注';
alter table project_status_tracking.cr CHANGE COLUMN remark remark text null COMMENT '备注';