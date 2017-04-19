package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

public class CComment implements Serializable {
    /**
	 * 主键ID
	 */
    private Integer id;

    /**
	 * 帐号
	 */
	private String userAccountName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 综合分数
	 */
	private Integer syntheticalScore;
	/**
	 * 质量分数
	 */
	private Integer qualityScore;

	/**
	 * 快递分数
	 */
	private Integer speedScore;
	/**
	 * 服务分数
	 */
	private Integer serviceScore;
	/**
	 * 商品编号
	 */
	private String goodsNo;

	/**
	 * 订单详情Id
	 */
	private String orderDetailsId;

	/**
	 * 图片地址
	 */
	private String imgUrl;

	/**
	 * 用户头像
	 */
	private String userPic;

	/**
	 * 评价内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建时间字符串
	 */
	private String createTimeStr;

    /**
	 * 创建时间
	 */
	private Date deletedTime;

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

	/**
	 * @return the createTimeStr
	 */
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	/**
	 * @param createTimeStr
	 *            the createTimeStr to set
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	/**
	 * @return the userPic
	 */
	public String getUserPic() {
		return userPic;
	}

	/**
	 * @param userPic
	 *            the userPic to set
	 */
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	/**
	 * @return the userAccountName
	 */
	public String getUserAccountName() {
		return userAccountName;
	}

	/**
	 * @param userAccountName
	 *            the userAccountName to set
	 */
	public void setUserAccountName(String userAccountName) {
		this.userAccountName = userAccountName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
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


	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderDetailsId() {
		return orderDetailsId;
	}

	public void setOrderDetailsId(String orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}

	/**
	 * @return the syntheticalScore
	 */
	public Integer getSyntheticalScore() {
		return syntheticalScore;
	}

	/**
	 * @param syntheticalScore
	 *            the syntheticalScore to set
	 */
	public void setSyntheticalScore(Integer syntheticalScore) {
		this.syntheticalScore = syntheticalScore;
	}

	/**
	 * @return the qualityScore
	 */
	public Integer getQualityScore() {
		return qualityScore;
	}

	/**
	 * @param qualityScore
	 *            the qualityScore to set
	 */
	public void setQualityScore(Integer qualityScore) {
		this.qualityScore = qualityScore;
	}


	/**
	 * @return the speedScore
	 */
	public Integer getSpeedScore() {
		return speedScore;
	}

	/**
	 * @param speedScore
	 *            the speedScore to set
	 */
	public void setSpeedScore(Integer speedScore) {
		this.speedScore = speedScore;
	}

	/**
	 * @return the serviceScore
	 */
	public Integer getServiceScore() {
		return serviceScore;
	}

	/**
	 * @param serviceScore
	 *            the serviceScore to set
	 */
	public void setServiceScore(Integer serviceScore) {
		this.serviceScore = serviceScore;
	}

}