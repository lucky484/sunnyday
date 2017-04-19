package com.f2b2c.eco.model.market;

import java.util.Date;

public class UserBrowerRecordModel {

	private Integer id;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 商品id
	 */
	private Integer goodsId;

	/**
	 * 创建人
	 */
	private Integer createdUser;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 修改人
	 */
	private Integer updatedUser;

	/**
	 * 修改时间
	 */
	private Date updatedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Integer createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(Integer updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
