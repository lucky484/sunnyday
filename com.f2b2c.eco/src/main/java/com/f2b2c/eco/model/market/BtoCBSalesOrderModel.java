package com.f2b2c.eco.model.market;

import java.util.Date;

/**
 * 订单信息
 * @author jane.hui
 *
 */
public class BtoCBSalesOrderModel {
	
	/**
	 * 消费者订单主键id
	 */
	private String id;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 消费者外键
	 */
	private Integer userId;

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
    private String reveiverTelephone;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 收货人邮箱
     */
    private String receiverPostalCode;
	

    /**
     * 总价
     */
    private Integer total;
    
    /**
     * 订单状态
     * 1：已完成订单
     * 2：待支付订单
     * 3：待发货订单
     * 4.已发货订单
     * 5.待评价
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
	 * 创建时间
	 */
	private Date createdTime;
	
	/**
	 * 删除时间
	 */
	private Date deletedTime;

	
	/**
	 * 精度
	 */
	private String longitude;			
			
	/**
	 * 纬度
	 */
	private String latitude;
	
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreatedTime() {
		return createdTime;
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

	public String getReveiverTelephone() {
		return reveiverTelephone;
	}

	public void setReveiverTelephone(String reveiverTelephone) {
		this.reveiverTelephone = reveiverTelephone;
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
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}