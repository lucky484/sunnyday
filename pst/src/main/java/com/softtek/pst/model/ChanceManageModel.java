package com.softtek.pst.model;

import java.util.Date;

public class ChanceManageModel {
        
	       private long chanceManageId;
	       
	       private String projectName;
	       
	       private Date startTime;
	       
	       private Date leadTime;
	       
	       private int forecastQuotation;
	       
	       private Date createTime;
	       
	       private Date updateTime;
	       
	       private String projectManagerName;
	       
	       private long creatorId;

		   private long updateBy;

		public long getChanceManageId() {
			return chanceManageId;
		}

		public void setChanceManageId(long chanceManageId) {
			this.chanceManageId = chanceManageId;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getLeadTime() {
			return leadTime;
		}

		public void setLeadTime(Date leadTime) {
			this.leadTime = leadTime;
		}

		public int getForecastQuotation() {
			return forecastQuotation;
		}

		public void setForecastQuotation(int forecastQuotation) {
			this.forecastQuotation = forecastQuotation;
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

		public String getProjectManagerName() {
			return projectManagerName;
		}

		public void setProjectManagerName(String projectManagerName) {
			this.projectManagerName = projectManagerName;
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
