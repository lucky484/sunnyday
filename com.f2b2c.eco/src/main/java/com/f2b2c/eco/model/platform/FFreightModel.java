package com.f2b2c.eco.model.platform;

import java.util.Date;

/**
 * 
 * @author mozzie.chu
 *
 */
public class FFreightModel {

	/**
	 * 运费ID
	 */
	private Integer id;
	/**
	 * 消费满多少免运费
	 */
	private Integer freemoney;
	/**
	 * 标准费用
	 */
	private Integer money;
	
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

	
	//运费ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	//消费满多少免运费
	public Integer getFreemoney() {
		return freemoney;
	}
	public void setFreemoney(Integer freemoney) {
		this.freemoney = freemoney;
	}
	
	//标准费用
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
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
