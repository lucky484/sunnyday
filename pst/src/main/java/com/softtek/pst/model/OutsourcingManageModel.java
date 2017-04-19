package com.softtek.pst.model;

import java.util.Date;

public class OutsourcingManageModel {

	     private long outsourcingManageId;
	     
	     private String name;
	     
	     private String email;
	     
	     private String phone;
	     
	     private Date createTime;
	     
	     private Date updateTime;
	     
	     private long creatorId;

		 private long updateBy;

		public long getOutsourcingManageId() {
			return outsourcingManageId;
		}

		public void setOutsourcingManageId(long outsourcingManageId) {
			this.outsourcingManageId = outsourcingManageId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public long getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(long creatorId) {
			this.creatorId = creatorId;
		}

		public long getUpdateBy() {
			return updateBy;
		}

		public void setUpdateBy(long updateBy) {
			this.updateBy = updateBy;
		}
	     
	     
}
