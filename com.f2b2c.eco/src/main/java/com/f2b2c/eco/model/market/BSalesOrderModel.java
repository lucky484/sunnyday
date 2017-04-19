package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BSalesOrderModel implements Serializable {
	/**
	 * 消费者订单主键ID
	 */
	private String id;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 合并订单号
	 */
	private String mergeOrderNo;

	/**
	 * 消费者
	 */
	private CUserModel user;

	/**
	 * 消费者外键
	 */
	private Integer userId;

	/**
	 * 卖家外键
	 */
	private Integer bUserId;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 收货人姓名
	 */
	private String receiverName;

	/**
	 * 收货人手机号
	 */
	private String receiverPhone;

	/**
	 * 收货人电话
	 */
	private String receiverTelephone;

	/**
	 * 收货地址
	 */
	private String receiverAddress;

	/**
	 * 收货人邮箱
	 */
	private String receiverPostalCode;

	/**
	 * 总价(分)
	 */
	private Integer total;

	/**
	 * 支付类型（0：微信支付 1.支付宝支付）
	 */
	private Integer payType;

	/**
	 * 订单状态 0：待支付订单 1：待发货订单 2：已完成订单 3：已取消订单 4：已发货订单
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 商品数量
	 */
	private Integer goodsCount;

	/**
	 * 交易记录的内容
	 */
	private String tradMark;

	/**
	 * 是否水果分类(0.水果分类 1.非水果分类)
	 */
	private String catalog;

	/**
	 * 运费
	 */
	private Integer freight;

	/**
	 * 真实付款金额
	 */
	private Integer realPay;

	private List<BSalesOrderDetailsModel> details;

	private double price;

	/**
	 * 物流公司代码
	 */
	private String logisticsCode;

	/**
	 * 物流公司运单号
	 */
	private String waybillNumber;

	/**
	 * 支付时间
	 */
	private Date payTime;

	/**
	 * 收貨人電話
	 */
	private String reveiverMobile;

	/**
	 * 是否接单
	 */
	private Integer receiveOrder;

	/**
	 * 发货时间
	 */
	private Date deliverTime;

	/**
	 * 确认收货时间
	 */
	private Date receiveTime;

	/**
	 * 取消订单时间
	 */
	private Date cancelTime;

	/**
	 * 退款状态
	 */
	private Integer returnStatus;

	/**
	 * 退款时间
	 */
	private Date returnTime;

	/**
	 * 接单时间
	 */
	private Date receiveOrderTime;

	private static final long serialVersionUID = 1L;

	public List<BSalesOrderDetailsModel> getDetails() {
		return details;
	}

	public void setDetails(List<BSalesOrderDetailsModel> details) {
		this.details = details;
	}

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

	public String getMergeOrderNo() {
		return mergeOrderNo;
	}

	public void setMergeOrderNo(String mergeOrderNo) {
		this.mergeOrderNo = mergeOrderNo;
	}

	public CUserModel getUser() {
		return user;
	}

	public void setUser(CUserModel user) {
		this.user = user;
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

	public String getReceiverTelephone() {
		return receiverTelephone;
	}

	public void setReceiverTelephone(String receiverTelephone) {
		this.receiverTelephone = receiverTelephone;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getbUserId() {
		return bUserId;
	}

	public void setbUserId(Integer bUserId) {
		this.bUserId = bUserId;
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

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTradMark() {
		return tradMark;
	}

	public void setTradMark(String tradMark) {
		this.tradMark = tradMark;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getReveiverMobile() {
		return reveiverMobile;
	}

	public void setReveiverMobile(String reveiverMobile) {
		this.reveiverMobile = reveiverMobile;
	}

	public Integer getReceiveOrder() {
		return receiveOrder;
	}

	public void setReceiveOrder(Integer receiveOrder) {
		this.receiveOrder = receiveOrder;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Integer getRealPay() {
		return realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}

	public Date getReceiveOrderTime() {
		return receiveOrderTime;
	}

	public void setReceiveOrderTime(Date receiveOrderTime) {
		this.receiveOrderTime = receiveOrderTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BSalesOrderModel [id=");
		builder.append(id);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", user=");
		builder.append(user);
		builder.append(", createdTime=");
		builder.append(createdTime);
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
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", deletedTime=");
		builder.append(deletedTime);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getOrderNo()=");
		builder.append(getOrderNo());
		builder.append(", getUser()=");
		builder.append(getUser());
		builder.append(", getCreatedTime()=");
		builder.append(getCreatedTime());
		builder.append(", getReceiverName()=");
		builder.append(getReceiverName());
		builder.append(", getReceiverPhone()=");
		builder.append(getReceiverPhone());
		builder.append(", getReceiverAddress()=");
		builder.append(getReceiverAddress());
		builder.append(", getReceiverPostalCode()=");
		builder.append(getReceiverPostalCode());
		builder.append(", getTotal()=");
		builder.append(getTotal());
		builder.append(", getPayType()=");
		builder.append(getPayType());
		builder.append(", getStatus()=");
		builder.append(getStatus());
		builder.append(", getRemark()=");
		builder.append(getRemark());
		builder.append(", getUpdatedTime()=");
		builder.append(getUpdatedTime());
		builder.append(", getDeletedTime()=");
		builder.append(getDeletedTime());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}