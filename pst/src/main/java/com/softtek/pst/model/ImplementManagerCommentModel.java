package com.softtek.pst.model;

import java.util.Date;

public class ImplementManagerCommentModel {
    
	 private long implementManagerComId;
	 
	 private long implementManagerId;
	 
	 private String critic;
	 
	 private String comment;
	 
	 private Date createTime;
     
     private Date updateTime;
     
     private Date deleteTime;

	public long getImplementManagerComId() {
		return implementManagerComId;
	}

	public void setImplementManagerComId(long implementManagerComId) {
		this.implementManagerComId = implementManagerComId;
	}

	public long getImplementManagerId() {
		return implementManagerId;
	}

	public void setImplementManagerId(long implementManagerId) {
		this.implementManagerId = implementManagerId;
	}

	public String getCritic() {
		return critic;
	}

	public void setCritic(String critic) {
		this.critic = critic;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
     
     
}
