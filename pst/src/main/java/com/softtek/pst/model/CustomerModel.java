package com.softtek.pst.model;

import java.util.Date;

/**
 * 
 * @ClassName: CustomerModel
 * @Description: 客户列表
 * @author: cliff.fan
 * @date: Apr 18, 2016 10:57:47 AM
 */
public class CustomerModel {
	/**
	 * 客户ID
	 */
	private long customerId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 客户头衔
	 */
	private String customerTitle;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户电话
	 */
	private String customerPhone;
	/**
	 * 公司电话
	 */
	private String companyPhone;
	/**
	 * 公司地址
	 */
	private String companyAddress;
	/**
	 * 开户银行
	 */
	private String bank;
	/**
	 * 付款账号
	 */
	private String paymentAccount;
	/**
	 * 公司税号
	 */
	private String companyIdNumber;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	private String otherContactWay;
	
	private long creatorId;

	private long updateBy;
	
	/**
	 * 客户简称
	 */
	private String customerNameShort;
	 

	public CustomerModel() {

	}

	public CustomerModel(long customerId, String companyName,
			String customerTitle, String customerName, String customerPhone,
			String companyPhone, String companyAddress, String bank,
			String paymentAccount, String companyIdNumber, Date addTime,
			Date updateTime, String otherContactWay, long creatorId,
			long updateBy) {
		super();
		this.customerId = customerId;
		this.companyName = companyName;
		this.customerTitle = customerTitle;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.companyPhone = companyPhone;
		this.companyAddress = companyAddress;
		this.bank = bank;
		this.paymentAccount = paymentAccount;
		this.companyIdNumber = companyIdNumber;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.otherContactWay = otherContactWay;
		this.creatorId = creatorId;
		this.updateBy = updateBy;
	}



	/**
	 * @return the customerId
	 */
	public long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the customerTitle
	 */
	public String getCustomerTitle() {
		return customerTitle;
	}

	/**
	 * @param customerTitle
	 *            the customerTitle to set
	 */
	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone
	 *            the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}

	/**
	 * @param companyAddress
	 *            the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 *            the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return the paymentAccount
	 */
	public String getPaymentAccount() {
		return paymentAccount;
	}

	/**
	 * @param paymentAccount
	 *            the paymentAccount to set
	 */
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the companyPhone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}

	/**
	 * @param companyPhone
	 *            the companyPhone to set
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyIdNumber() {
		return companyIdNumber;
	}

	public void setCompanyIdNumber(String companyIdNumber) {
		this.companyIdNumber = companyIdNumber;
	}

	public String getOtherContactWay() {
		return otherContactWay;
	}

	public void setOtherContactWay(String otherContactWay) {
		this.otherContactWay = otherContactWay;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(long updateBy) {
		this.updateBy = updateBy;
	}

	public String getCustomerNameShort() {
		return customerNameShort;
	}

	public void setCustomerNameShort(String customerNameShort) {
		this.customerNameShort = customerNameShort;
	}
	
}
