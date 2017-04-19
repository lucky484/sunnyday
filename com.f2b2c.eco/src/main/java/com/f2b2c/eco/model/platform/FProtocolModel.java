package com.f2b2c.eco.model.platform;

import java.util.Date;

public class FProtocolModel {

	private Integer id;
	
	private String content;
	
	private Date createdTime;
	
	private FUserModel createdUser;
	
	private Date updatedTime;
	
	private FUserModel updatedUser;
	
	private FUserModel deletedUser;
	
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public FUserModel getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(FUserModel createdUser) {
		this.createdUser = createdUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public FUserModel getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(FUserModel updatedUser) {
		this.updatedUser = updatedUser;
	}

	public FUserModel getDeletedUser() {
		return deletedUser;
	}

	public void setDeletedUser(FUserModel deletedUser) {
		this.deletedUser = deletedUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
