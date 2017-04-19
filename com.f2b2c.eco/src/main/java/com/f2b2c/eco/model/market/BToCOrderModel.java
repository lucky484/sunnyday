package com.f2b2c.eco.model.market;

import java.util.Date;
import java.util.List;

public class BToCOrderModel {

	/**
	 * 订单id
	 */
	private String id;

	/**
	 * 商店名称
	 */
	private String shopName;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品图片
	 */
	private String logoUrl;

	/**
	 * 商品描述
	 */
	private String remark;

	/**
	 * 商品数量
	 */
	private Integer quantity;

	/**
	 * 总价
	 */
	private Integer total;

	/**
	 * 商品商铺id
	 */
	private Integer shopId;

	/**
	 * 商品状态
	 */
	private Integer status;

	/**
	 * 商品价格
	 */
	private Integer price;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单号
	 */
	private String payType;

	/**
	 * 订单生成时间
	 */
	private String createdTime;

	/**
	 * 商品总数量
	 */
	private Integer goodsCount;

	/**
	 * 商品
	 */
	private List<BGoodsToCModel> list;

	/**
	 * 收货人姓名
	 */
	private String receiverName;

	/**
	 * 收货人地址
	 */
	private String receiverAddress;

	/**
	 * 收获人电话
	 */
	private String reveiverMobile;
	/**
	 * 收货时间
	 */
	private Integer receiveTimeType;

	/**
	 * 合并订单号
	 */
	private String mergeOrderNo;

	private Integer realTotalPrice; // 真实金额

	private Integer diffenceAmount; // 差价

	private int diffenceStatus; // 差价状态

	private Integer diffenceType; // 退补状态

	/**
	 * 差价备注
	 */
	private String diffenceRemark;
	/**
	 * 差价原因
	 */
	private int diffenceCause;

	private Integer userId; // 买家id

	/**
	 * 用户余额
	 */
	private Integer accountBalance;

	/**
	 * 0-水果订单 1-非水果订单
	 */
	private int catalog;

	/**
	 * 当前网络时间
	 */
	private String currentTime;

	/**
	 * 接单状态
	 */
	private int receiveOrder;

	/**
	 * 运费
	 */
	private int freight;

	/**
	 * 物流公司代码
	 */
	private String logisticsCode;

	/**
	 * 运单号
	 */
	private String waybillNumber;

	/**
	 * 卖家id
	 */
	private Integer bUserId;

	/**
	 * 付款时间
	 */
	private Date payTime;

	/**
	 * 收货时间
	 */
	private Date receiveTime;

	/**
	 * 真实付款金额
	 */
	private Integer realPay;

	private Integer isEnable;

	private String receiverTelephone;

	/**
	 * 接单时间
	 */
	private String receiveOrderTime;

	public String getShopName() {
		return shopName;
	}

	public Integer getReceiveTimeType() {
		return receiveTimeType;
	}

	public void setReceiveTimeType(Integer receiveTimeType) {
		this.receiveTimeType = receiveTimeType;
	}

	/**
	 * @return the diffenceCause
	 */
	public int getDiffenceCause() {
		return diffenceCause;
	}

	/**
	 * @param diffenceCause
	 *            the diffenceCause to set
	 */
	public void setDiffenceCause(int diffenceCause) {
		this.diffenceCause = diffenceCause;
	}

	public String getDiffenceRemark() {
		return diffenceRemark;
	}

	public void setDiffenceRemark(String diffenceRemark) {
		this.diffenceRemark = diffenceRemark;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public List<BGoodsToCModel> getList() {
		return list;
	}

	public void setList(List<BGoodsToCModel> list) {
		this.list = list;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReveiverMobile() {
		return reveiverMobile;
	}

	public void setReveiverMobile(String reveiverMobile) {
		this.reveiverMobile = reveiverMobile;
	}

	public String getMergeOrderNo() {
		return mergeOrderNo;
	}

	public void setMergeOrderNo(String mergeOrderNo) {
		this.mergeOrderNo = mergeOrderNo;
	}

	public Integer getRealTotalPrice() {
		return realTotalPrice;
	}

	public void setRealTotalPrice(Integer realTotalPrice) {
		this.realTotalPrice = realTotalPrice;
	}

	public Integer getDiffenceAmount() {
		return diffenceAmount;
	}

	public void setDiffenceAmount(Integer diffenceAmount) {
		this.diffenceAmount = diffenceAmount;
	}

	public int getDiffenceStatus() {
		return diffenceStatus;
	}

	public void setDiffenceStatus(int diffenceStatus) {
		this.diffenceStatus = diffenceStatus;
	}

	public Integer getDiffenceType() {
		return diffenceType;
	}

	public void setDiffenceType(Integer diffenceType) {
		this.diffenceType = diffenceType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Integer accountBalance) {
		this.accountBalance = accountBalance;
	}

	public int getCatalog() {
		return catalog;
	}

	public void setCatalog(int catalog) {
		this.catalog = catalog;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public int getReceiveOrder() {
		return receiveOrder;
	}

	public void setReceiveOrder(int receiveOrder) {
		this.receiveOrder = receiveOrder;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
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

	public Integer getbUserId() {
		return bUserId;
	}

	public void setbUserId(Integer bUserId) {
		this.bUserId = bUserId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Integer getRealPay() {
		return realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}

	/**
	 * @return the receiverTelephone
	 */
	public String getReceiverTelephone() {
		return receiverTelephone;
	}

	/**
	 * @param receiverTelephone
	 *            the receiverTelephone to set
	 */
	public void setReceiverTelephone(String receiverTelephone) {
		this.receiverTelephone = receiverTelephone;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getReceiveOrderTime() {
		return receiveOrderTime;
	}

	public void setReceiveOrderTime(String receiveOrderTime) {
		this.receiveOrderTime = receiveOrderTime;
	}
	
	
}
