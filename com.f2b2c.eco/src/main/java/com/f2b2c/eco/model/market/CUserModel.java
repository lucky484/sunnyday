package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CUserModel implements Serializable {
	/**
	 * 普通用户ID
	 */
	private Integer id;

	/**
	 * 用户名称
	 */
	private String accountName;

	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 支付密码
	 */
	private String payPassword;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 注册时间
	 */
	private Date registerTime;

	/**
	 * 更新人(暂时没有对顾客管理，暂时无用)
	 */
	private Integer updatedUser;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 推荐类型
	 */
	private Integer recommendType;

	/**
	 * 推荐链接
	 */
	private String recommendUrl;

	/**
	 * 推荐人
	 */
	private Integer recommendUser;

	/**
	 * 头像路径
	 */
	private String picUrl;

	/**
	 * 余额
	 */
	private Integer accountBalance;

	/**
	 * 订单状态，待付款
	 */
	private Integer waitPay;

	/**
	 * 订单状态，待发货
	 */
	private Integer waitSend;

	/**
	 * 订单状态，待收获ss
	 */
	private Integer waitReceive;

	/**
	 * 订单状态，待评价
	 */
	private Integer waitEvaluate;
	
	/**
	 * 退款订单总数
	 */
	private Integer returnStatusCount;
	
	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	private List<CDeliveryAddressModel> list;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getRecommendUser() {
		return recommendUser;
	}

	public void setRecommendUser(Integer recommendUser) {
		this.recommendUser = recommendUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(Integer updatedUser) {
		this.updatedUser = updatedUser;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	public String getRecommendUrl() {
		return recommendUrl;
	}

	public void setRecommendUrl(String recommendUrl) {
		this.recommendUrl = recommendUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Integer accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Integer getWaitPay() {
		return waitPay;
	}

	public void setWaitPay(Integer waitPay) {
		this.waitPay = waitPay;
	}

	public Integer getWaitSend() {
		return waitSend;
	}

	public void setWaitSend(Integer waitSend) {
		this.waitSend = waitSend;
	}

	public Integer getWaitReceive() {
		return waitReceive;
	}

	public void setWaitReceive(Integer waitReceive) {
		this.waitReceive = waitReceive;
	}

	public Integer getWaitEvaluate() {
		return waitEvaluate;
	}

	public void setWaitEvaluate(Integer waitEvaluate) {
		this.waitEvaluate = waitEvaluate;
	}

	public List<CDeliveryAddressModel> getList() {
		return list;
	}

	public void setList(List<CDeliveryAddressModel> list) {
		this.list = list;
	}

	public Integer getReturnStatusCount() {
		return returnStatusCount;
	}

	public void setReturnStatusCount(Integer returnStatusCount) {
		this.returnStatusCount = returnStatusCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CUserModel [id=");
		builder.append(id);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", nickName=");
		builder.append(nickName);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", registerTime=");
		builder.append(registerTime);
		builder.append(", updatedUser=");
		builder.append(updatedUser);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", deletedTime=");
		builder.append(deletedTime);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", recommendType=");
		builder.append(recommendType);
		builder.append(", recommendUrl=");
		builder.append(recommendUrl);
		builder.append(", picUrl=");
		builder.append(picUrl);
		builder.append("]");
		return builder.toString();
	}

}