package com.softtek.pst.model;

import java.util.Date;

public class ProjectFinancialModel {

	   private long projectFinancialId;
	   
	   private long projectId;
	   
	   private int receive;
	   
	   private int payment;
	   
	   private String remark;
	   
	   private Date createTime;
	   
	   private Date updateTime;
	   
	   private String createTimeStr;

	public long getProjectFinancialId() {
		return projectFinancialId;
	}

	public void setProjectFinancialId(long projectFinancialId) {
		this.projectFinancialId = projectFinancialId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public int getReceive() {
		return receive;
	}

	public void setReceive(int receive) {
		this.receive = receive;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	   
}
