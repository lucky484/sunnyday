package com.softtek.pst.model;

import java.util.Date;

public class OutsourcingManageCommentModel {

	
	      private long outsourcingManageComId;
	      
	      private long outsourcingManageId;
	      
	      private String critic;
	      
	      private String comment;
	      
	      private Date createTime;
	      
	      private Date updateTime;

		public long getOutsourcingManageComId() {
			return outsourcingManageComId;
		}

		public void setOutsourcingManageComId(long outsourcingManageComId) {
			this.outsourcingManageComId = outsourcingManageComId;
		}

		public long getOutsourcingManageId() {
			return outsourcingManageId;
		}

		public void setOutsourcingManageId(long outsourcingManageId) {
			this.outsourcingManageId = outsourcingManageId;
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
	      
	      
}
