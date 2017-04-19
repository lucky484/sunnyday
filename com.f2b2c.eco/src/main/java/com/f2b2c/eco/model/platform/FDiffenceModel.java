package com.f2b2c.eco.model.platform;

import java.util.Date;

public class FDiffenceModel {

	private String id;

	private String orderNo; // 订单号

	private int diffenceType; // 差价类型

	private int diffenceCause; // 差价原因

	private int diffenceAmount; // 差价金额

	private int status; // 审核状态

	private String remark; // 描述

	private Date createdTime; // 创建时间

	private Date updatedTime; // 修改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getDiffenceType() {
		return diffenceType;
	}

	public void setDiffenceType(int diffenceType) {
		this.diffenceType = diffenceType;
	}

	public int getDiffenceCause() {
		return diffenceCause;
	}

	public void setDiffenceCause(int diffenceCause) {
		this.diffenceCause = diffenceCause;
	}

	public int getDiffenceAmount() {
		return diffenceAmount;
	}

	public void setDiffenceAmount(int diffenceAmount) {
		this.diffenceAmount = diffenceAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
