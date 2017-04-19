package com.f2b2c.eco.model.market;

import java.util.Date;

public class ReturnPayModel {

	private int id;

	private String orderDetailId;

	/**
	 * 收货类型
	 */
	private int receiveType;

	/**
	 * 退款类型
	 */
	private int returnType;

	/**
	 * 退款原因
	 */
	private String returnReason;

	/**
	 * 退款金额
	 */
	private int returnAmount;

	/**
	 * 退款金额
	 */
	private String returnRemark;
	
	/**
	 * 创建时间
	 */
	private Date createdTime;
	
	/**
	 * 修改时间
	 */
	private Date updatedTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public int getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(int receiveType) {
		this.receiveType = receiveType;
	}

	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public int getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(int returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
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
