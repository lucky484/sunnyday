package com.f2b2c.eco.model.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * we suggest most part custom model extend BaseModel
 * @author color.wu
 *
 */
public class BaseModel {

	private Date createdTime;
	private Integer createdUser;
	private Date updatedTime;
	private Integer updatedUser;
	private Date deletedTime;
	private Map<String,Object> extra = new HashMap<String,Object>();
	
	//==================getter and setter start===============
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(Integer createdUser) {
		this.createdUser = createdUser;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Integer getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(Integer updatedUser) {
		this.updatedUser = updatedUser;
	}
	public Date getDeletedTime() {
		return deletedTime;
	}
	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}
	public Map<String, Object> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}
	
	//==================getter and setter end===============
}
