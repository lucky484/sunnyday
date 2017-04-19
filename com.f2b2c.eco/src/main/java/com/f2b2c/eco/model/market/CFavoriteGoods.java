package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

public class CFavoriteGoods implements Serializable {
    /**
	 * 主键ID
	 */
    private Integer id;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 商品编号
	 */
	private String goodsNo;


	/**
	 * 创建时间
	 */
	private Date createTime;


    private static final long serialVersionUID = 1L;

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



}