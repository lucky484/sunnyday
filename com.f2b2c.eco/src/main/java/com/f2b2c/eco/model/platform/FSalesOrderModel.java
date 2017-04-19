package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.f2b2c.eco.model.market.BUserModel;

public class FSalesOrderModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4214934041258604048L;

	/**
	 * 订单详情况列表
	 */
	private List<FSalesOrderDetailsModel> fSalesOrderDetailsModel;

	/**
	 * 平台订单主键
	 */
	private String id;

	/**
	 * 平台订单ID
	 */
	private String orderId;

	/**
	 * 购买人对象
	 */
	private BUserModel user;

	/**
	 * 购买人ID
	 */
	private Integer userId;

	/**
	 * 创建时间（下单时间）
	 */
	private Date createdTime;
	
	/**
	 * 配送日期：定义的规则是下单日期+1
	 */
	private Date sendTime;

	/**
	 * 收货人
	 */
	private String receiverName;

	/**
	 * 收货手机号
	 */
	private String receiverPhone;

	/**
	 * 收货地址
	 */
	private String receiverAddress;

	/**
	 * 收货邮编
	 */
	private String receiverPostalCode;

	/**
	 * 总价(分)
	 */
	private Integer total;

	/**
	 * 支付类型（0现金支付）
	 */
	private Integer payType;

	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 原因
	 */
	private String reason;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 实际付款（分）
	 */
	private Integer realPay;

	/**
	 * 配送地区（格式：江苏省-无锡市-滨湖区 省-市-区）
	 */
	private String distributionArea;

	/**
	 * 收货时间类型
	 */
	private Integer receiveTimeType;

	private String shopName;

	/**
	 * 确认订单的类型，1表示购物车 2表示立即支付
	 */
	private String tag;

	private Integer shopCarId;

	/**
	 * 订单商品的总数量
	 */
	private Integer goodsCount;
	
	/**
	 * 合并订单号
	 */
	private String mergeOrderNo;

	/**
	 * 补差价id
	 */
	private String diffenceId;
	
	/**
	 * 补差价类型
	 */
	private Integer diffenceType;
	
	/**
	 * 补差价金额
	 */
	private Integer diffenceAmount;
	
	/**
	 * 补差价状态
	 */
	private String diffenceStatus;
	
	private Date finshTime;
	
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	public List<FSalesOrderDetailsModel> getfSalesOrderDetailsModel() {
		return fSalesOrderDetailsModel;
	}

	public void setfSalesOrderDetailsModel(
			List<FSalesOrderDetailsModel> fSalesOrderDetailsModel) {
		this.fSalesOrderDetailsModel = fSalesOrderDetailsModel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BUserModel getUser() {
		return user;
	}

	public void setUser(BUserModel user) {
		this.user = user;
	}

	public Integer getUserId() {
		if (null != user) {
			return user.getId();
		}
		return userId;
	}

	public void setUserId(Integer userId) {
		if (null != user) {
			this.userId = user.getId();
		} else {
			this.userId = userId;
		}
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverPostalCode() {
		return receiverPostalCode;
	}

	public void setReceiverPostalCode(String receiverPostalCode) {
		this.receiverPostalCode = receiverPostalCode;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Integer getRealPay() {
		return realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}

	public String getDistributionArea() {
		return distributionArea;
	}

	public void setDistributionArea(String distributionArea) {
		this.distributionArea = distributionArea;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getReceiveTimeType() {
		return receiveTimeType;
	}

	public void setReceiveTimeType(Integer receiveTimeType) {
		this.receiveTimeType = receiveTimeType;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getShopCarId() {
		return shopCarId;
	}

	public void setShopCarId(Integer shopCarId) {
		this.shopCarId = shopCarId;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getMergeOrderNo() {
		return mergeOrderNo;
	}

	public void setMergeOrderNo(String mergeOrderNo) {
		this.mergeOrderNo = mergeOrderNo;
	}
	
	public String getDiffenceId() {
		return diffenceId;
	}

	public void setDiffenceId(String diffenceId) {
		this.diffenceId = diffenceId;
	}

	public Integer getDiffenceType() {
		return diffenceType;
	}

	public void setDiffenceType(Integer diffenceType) {
		this.diffenceType = diffenceType;
	}

	public Integer getDiffenceAmount() {
		return diffenceAmount;
	}

	public void setDiffenceAmount(Integer diffenceAmount) {
		this.diffenceAmount = diffenceAmount;
	}

	public String getDiffenceStatus() {
		return diffenceStatus;
	}

	public void setDiffenceStatus(String diffenceStatus) {
		this.diffenceStatus = diffenceStatus;
	}
	
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getFinshTime() {
		return finshTime;
	}

	public void setFinshTime(Date finshTime) {
		this.finshTime = finshTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FSalesOrderModel [fSalesOrderDetailsModel=");
		builder.append(fSalesOrderDetailsModel);
		builder.append(", id=");
		builder.append(id);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", user=");
		builder.append(user);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append(", sendTime=");
		builder.append(sendTime);
		builder.append(", receiverName=");
		builder.append(receiverName);
		builder.append(", receiverPhone=");
		builder.append(receiverPhone);
		builder.append(", receiverAddress=");
		builder.append(receiverAddress);
		builder.append(", receiverPostalCode=");
		builder.append(receiverPostalCode);
		builder.append(", total=");
		builder.append(total);
		builder.append(", payType=");
		builder.append(payType);
		builder.append(", status=");
		builder.append(status);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", deletedTime=");
		builder.append(deletedTime);
		builder.append(", realPay=");
		builder.append(realPay);
		builder.append(", distributionArea=");
		builder.append(distributionArea);
		builder.append(", receiveTimeType=");
		builder.append(receiveTimeType);
		builder.append(", shopName=");
		builder.append(shopName);
		builder.append(", tag=");
		builder.append(tag);
		builder.append(", shopCarId=");
		builder.append(shopCarId);
		builder.append(", goodsCount=");
		builder.append(goodsCount);
		builder.append(", mergeOrderNo=");
		builder.append(mergeOrderNo);
		builder.append(", diffenceId=");
		builder.append(diffenceId);
		builder.append(", diffenceType=");
		builder.append(diffenceType);
		builder.append(", diffenceAmount=");
		builder.append(diffenceAmount);
		builder.append(", diffenceStatus=");
		builder.append(diffenceStatus);
		builder.append("]");
		return builder.toString();
	}
}