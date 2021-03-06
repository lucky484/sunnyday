/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: Comment.java
 * @Prject: pst
 * @Package: com.softtek.pst.model
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 15, 2016 1:38:30 PM
 * @version: V1.0  
 */
package com.softtek.pst.model;

import java.util.Date;

/**
 * @ClassName: Comment
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 15, 2016 1:38:30 PM
 */
public class CommentModel {
	
	/**
	 * 评论ID
	 */
	private long commentId;
	/**
	 * crID
	 */
	private long crId;
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
	 * @return the commentId
	 */
	public long getCommentId() {
		return commentId;
	}
	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	/**
	 * @return the crId
	 */
	public long getCrId() {
		return crId;
	}
	/**
	 * @param crId the crId to set
	 */
	public void setCrId(long crId) {
		this.crId = crId;
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
