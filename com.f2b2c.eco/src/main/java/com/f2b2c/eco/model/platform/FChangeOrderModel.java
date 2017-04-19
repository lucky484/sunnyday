package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;

public class FChangeOrderModel implements Serializable {
    /**
     * 增补单ID
     */
    private Integer id;

    /**
     * 原订单
     */
    private FSalesOrderModel orignalOrder;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 收货人姓名
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
     * 支付类型
     */
    private Integer payType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 原因
     */
    private String reason;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FSalesOrderModel getOrignalOrder() {
		return orignalOrder;
	}

	public void setOrignalOrder(FSalesOrderModel orignalOrder) {
		this.orignalOrder = orignalOrder;
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

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FChangeOrderModel [id=");
		builder.append(id);
		builder.append(", orignalOrder=");
		builder.append(orignalOrder);
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
		builder.append(", payType=");
		builder.append(payType);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", reason=");
		builder.append(reason);
		builder.append("]");
		return builder.toString();
	}

    
}