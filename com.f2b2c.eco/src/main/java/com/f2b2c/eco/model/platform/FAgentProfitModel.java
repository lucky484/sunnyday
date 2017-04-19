package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BShopInfoModel;

public class FAgentProfitModel implements Serializable {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 订单
	 */
	private FSalesOrderModel order;

	/**
	 * 订单
	 */
	private BSalesOrderModel bOrder;

	/**
	 * 佣金
	 */
	private BigDecimal commissionAmount;

	/**
	 * 分润所属的店铺，方便后期统计分析时使用
	 */
	private BShopInfoModel shop;

	public BSalesOrderModel getBorder() {
		return bOrder;
	}

	public void setBorder(BSalesOrderModel bOrder) {
		this.bOrder = bOrder;
	}

	/**
	 * 创建时间
	 */
	private Date createdtime;

	private String orderId;// 订单id

	private Integer orderAmount;// 订单实际金额，以分为单位

	private BigDecimal profitPercent;// 分润比例

	private Integer roleId;

	private String roleName;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public FSalesOrderModel getOrder() {
		return order;
	}

	public void setOrder(FSalesOrderModel order) {
		this.order = order;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public Date getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BShopInfoModel getShop() {
		return shop;
	}

	public void setShop(BShopInfoModel shop) {
		this.shop = shop;
	}

	public BSalesOrderModel getbOrder() {
		return bOrder;
	}

	public void setbOrder(BSalesOrderModel bOrder) {
		this.bOrder = bOrder;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getProfitPercent() {
		return profitPercent;
	}

	public void setProfitPercent(BigDecimal profitPercent) {
		this.profitPercent = profitPercent;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FAgentProfitModel [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", order=");
		builder.append(order);
		builder.append(", bOrder=");
		builder.append(bOrder);
		builder.append(", commissionAmount=");
		builder.append(commissionAmount);
		builder.append(", shop=");
		builder.append(shop);
		builder.append(", createdtime=");
		builder.append(createdtime);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", orderAmount=");
		builder.append(orderAmount);
		builder.append(", profitPercent=");
		builder.append(profitPercent);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append("]");
		return builder.toString();
	}
}