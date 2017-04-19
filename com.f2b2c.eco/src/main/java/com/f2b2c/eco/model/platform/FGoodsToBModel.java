package com.f2b2c.eco.model.platform;

/**
 * 搜索商品Model
 * @author jane.hui
 *
 */
public class FGoodsToBModel {

	/**
	 * 商品主键
	 */
	private Integer id;
	
	/**
	 * 商品编号
	 */
	private String goodsNo;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品详情
	 */
	private String detail;
	
	/**
	 * 图片
	 */
	private String logoUrl;
	
	/**
	 * 单位
	 */
	private Integer unit;
	
	/**
	 * 单价
	 */
	private Integer price;
	
	/**
	 * 销量
	 */
	private Integer sellQty;
	
	/**
	 * 库存
	 */
	private Integer remain;
	
	/**
	 * 简介
	 */
	private String intro;
	
	/**
	 * 产地 
	 */
	private String producePlace;
	
	/**
	 * 类别
	 */
	private String type;
	
	/**
	 * 属性
	 */
	private String properties;
	
	/**
	 * 规格
	 */
	private String spec;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSellQty() {
		return sellQty;
	}

	public void setSellQty(Integer sellQty) {
		this.sellQty = sellQty;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getProducePlace() {
		return producePlace;
	}

	public void setProducePlace(String producePlace) {
		this.producePlace = producePlace;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

}
