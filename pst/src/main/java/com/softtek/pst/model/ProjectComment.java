/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: ProjectComment.java
 * @Prject: pst
 * @Package: com.softtek.pst.model
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 18, 2016 10:44:31 AM
 * @version: V1.0  
 */
package com.softtek.pst.model;

import java.util.Date;

/**
 * @ClassName: ProjectComment
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 18, 2016 10:44:31 AM
 */
public class ProjectComment {
	
	/**
	 * 项目评论ID
	 */
	private long projectcomId;
	/**
	 * 项目ID
	 */
	private long projectId;
	/**
	 * 评论人
	 */
	private String critic;
	/**
	 * 评论
	 */
	private String comment;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * @return the projectcomId
	 */
	public long getProjectcomId() {
		return projectcomId;
	}
	/**
	 * @param projectcomId the projectcomId to set
	 */
	public void setProjectcomId(long projectcomId) {
		this.projectcomId = projectcomId;
	}
	/**
	 * @return the projectId
	 */
	public long getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the critic
	 */
	public String getCritic() {
		return critic;
	}
	/**
	 * @param critic the critic to set
	 */
	public void setCritic(String critic) {
		this.critic = critic;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
