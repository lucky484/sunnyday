package com.softtek.pst.model;

import java.sql.Date;

public class ImplementManagerModel {
   
	private long implementManagerId;
	 
	 private String name;
	 
	 private String email;
	 
	 private String phone;
	 
	 private Date createTime;
	 
	 private Date updateTime;
	 
	 private Date delete_time;
	 
	 private long creatorId;

	 private long updateBy;

	public long getImplementManagerId() {
		return implementManagerId;
	}

	public void setImplementManagerId(long implementManagerId) {
		this.implementManagerId = implementManagerId;
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

	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
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
