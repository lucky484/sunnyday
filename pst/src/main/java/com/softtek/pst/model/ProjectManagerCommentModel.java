package com.softtek.pst.model;

import java.util.Date;

public class ProjectManagerCommentModel {
     
	     private long projectManagerComId;
	     
	     private long projectManagerId;
	     
	     private String critic;
	     
	     private String comment;
	     
	     private Date createTime;
	     
	     private Date updateTime;
	     
	     private Date deleteTime;

		public long getProjectManagerComId() {
			return projectManagerComId;
		}

		public void setProjectManagerComId(long projectManagerComId) {
			this.projectManagerComId = projectManagerComId;
		}

		public long getProjectManagerId() {
			return projectManagerId;
		}

		public void setProjectManagerId(long projectManagerId) {
			this.projectManagerId = projectManagerId;
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

		@Override
		public String toString() {
			return "ProjectManagerCommentModel [projectManagerComId="
					+ projectManagerComId + ", projectManagerId="
					+ projectManagerId + ", critic=" + critic + ", comment="
					+ comment + ", createTime=" + createTime + ", updateTime="
					+ updateTime + ", deleteTime=" + deleteTime + "]";
		}
	     
	     
}
