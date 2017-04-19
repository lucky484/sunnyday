package com.f2b.security.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 农场订单表
 * 
 * @author mozzie.chu
 * @version 16/08/16, 15:08
 */
@Entity
@Table(name = "farm_oder")
@GenericGenerator(name = "FARM_ORDER_GEN", strategy = "enhanced-table", parameters = {
		@org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
		@org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
		@org.hibernate.annotations.Parameter(name = "segment_value", value = "order_id"),
		@org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
		@org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
		@org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
		@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
public class FarmOrder {
	/**
	 * 主键
	 */
	private Long orderId;

	/**
	 * 商品名
	 */
	private String produceName;

	/**
	 * SKU-库存量单位（订单货号）
	 */
	private String sku;

	/**
	 * 店主-买家
	 */
	private String merchant;

	/**
	 * 店主（买家）电话
	 */
	private String phone;

	/**
	 * 总价
	 */
	private BigDecimal total;

	/**
	 * 重量-数量
	 */
	private String weight;

	/**
	 * 收获地址
	 */
	private String address;

	/**
	 * 运费
	 */
	private BigDecimal freight;

	/**
	 * 单价
	 */
	private BigDecimal unitPrice;

	/**
	 * 下单时间
	 */
	private Date createDate;

	/**
	 * 订单状态 0：已发货; -1: 未发货，-2订单失败未支付成功
	 */
	private Integer status;

	/**
	 * 买家备注
	 */
	private String comments;

	/**
	 * 加密后的微信号
	 */
	private String openId;

	/**
	 * 购买人昵称
	 */
	private String nickname;

	/**
	 * 批发商
	 */
	private String wholesaler;

	/**
	 * 收货时间 finshDate
	 */

	private Date updateDate;

	/**
	 * 分享人的openid
	 */
	private String shareOpenId;
	
	/**
	 * 分享人的昵称
	 */
	private String shareNickname;

	// 主键
	@Id
	@GeneratedValue(generator = "FARM_ORDER_GEN")
	@Column(name = "order_id")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	// 加密后的微信号
	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	// sku
	@Column(name = "sku")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	// 批发商 - 卖家
	@Column(name = "wholesaler")
	public String getWholesaler() {
		return wholesaler;
	}

	public void setWholesaler(String wholesaler) {
		this.wholesaler = wholesaler;
	}

	// 店主
	@Column(name = "merchant")
	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	// 单价
	@Column(name = "unit_price")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	// 重量
	@Column(name = "weight")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	// 总价
	@Column(name = "total")
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	// 商品名
	@Column(name = "produce_name")
	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	// 下单时间
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	// 状态
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	// 地址
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// 电话
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// 备注
	@Column(name = "comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	// 运费
	@Column(name = "freight")
	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getShareOpenId() {
		return shareOpenId;
	}

	public void setShareOpenId(String shareOpenId) {
		this.shareOpenId = shareOpenId;
	}

	public String getShareNickname() {
		return shareNickname;
	}

	public void setShareNickname(String shareNickname) {
		this.shareNickname = shareNickname;
	}

}
