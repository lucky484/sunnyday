package com.f2b2c.eco.model.platform;

import java.util.Date;

public class FLogisticsModel {

	/**
	 * 物流编号
	 */
	private Integer id;
	
	/**
	 * 物流名称
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 创建人
	 */
	private FUserModel userId;
	/**
	 * 修改时间
	 */
	private Date updatedTime;
	/**
	 * 修改人
	 */
	private FUserModel updatedId;

	//物流编号
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//物流名称
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public FUserModel getUserId() {
		return userId;
	}

	public void setUserId(FUserModel userId) {
		this.userId = userId;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public FUserModel getUpdatedId() {
		return updatedId;
	}

	public void setUpdatedId(FUserModel updatedId) {
		this.updatedId = updatedId;
	}
	
	
}
