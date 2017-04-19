package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;

public class FGoodsModel implements Serializable {
	/**
	 * 商品编号
	 */
	private Integer id;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品详情
	 */
	private String detail;

	/**
	 * 商品名称多个|隔开
	 */
	private String logoUrl;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 单价(分)
	 */
	private Integer price;

	/**
	 * 销售量
	 */
	private Integer sell_qty;

	/**
	 * 剩余量
	 */
	private Integer remain;

	/**
	 * 锁定数量
	 */
	private Integer allocate;

	/**
	 * 是否热销0否，1是
	 */
	private Integer isHot;

	/**
	 * 是否新品0否1是
	 */
	private Integer isNew;

	/**
	 * 是否是副本0否1是
	 */
	private Integer isCopy;

	/**
	 * 分类
	 */
	private FKindModel kind;

	/**
	 * 商品简介
	 */
	private String intro;

	/**
	 * 点击率
	 */
	private Long clickTimes;

	/**
	 * 发布人
	 */
	private FUserModel releaseUser;

	/**
	 * 发布时间
	 */
	private Date releaseTime;

	/**
	 * 下架人
	 */
	private FUserModel offShelvesUser;

	/**
	 * 下架时间
	 */
	private Date offShelvesTime;

	/**
	 * 商品版本
	 */
	private Integer version;

	/**
	 * 更新人
	 */
	private FUserModel updatedUser;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 商品产地
	 */
	private String producePlace;

	/**
	 * 商品备注
	 */
	private String remark;

	/**
	 * 商品类型
	 */
	private String type;

	/**
	 * 规格
	 */
	private String spec;

	/**
	 * 特征
	 */
	private String properties;

	/**
	 * 进口/国产 0:国产；1：进口
	 */
	private Integer madeInChina;

	/**
	 * 商品编号
	 */
	private String goodsNo;

	private Integer cityId;

	/**
	 * 权重
	 */
	private Integer weight;
	
	private Integer provinceId;

	private String cityName;

	private String provinceName;

	/**
	 * 状态：0-待审核，1-上架，2-下架，3-审核不通过，4-草稿
	 */
	private Integer status;

	/**
	 * 提成比例64%，填写64即可
	 */
	private Integer sharePercent;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	/**
	 * @return the provinceId
	 */
	public Integer getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMadeInChina() {
		return madeInChina;
	}

	public void setMadeInChina(Integer madeInChina) {
		this.madeInChina = madeInChina;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 *            the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the logoUrl
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSell_qty() {
		return sell_qty;
	}

	public void setSell_qty(Integer sell_qty) {
		this.sell_qty = sell_qty;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Integer getAllocate() {
		return allocate;
	}

	public void setAllocate(Integer allocate) {
		this.allocate = allocate;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Integer isCopy) {
		this.isCopy = isCopy;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public FKindModel getKind() {
		return kind;
	}

	public void setKind(FKindModel kind) {
		this.kind = kind;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Long getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(Long clickTimes) {
		this.clickTimes = clickTimes;
	}

	public FUserModel getReleaseUser() {
		return releaseUser;
	}

	public void setReleaseUser(FUserModel releaseUser) {
		this.releaseUser = releaseUser;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public FUserModel getOffShelvesUser() {
		return offShelvesUser;
	}

	public void setOffShelvesUser(FUserModel offShelvesUser) {
		this.offShelvesUser = offShelvesUser;
	}

	public Date getOffShelvesTime() {
		return offShelvesTime;
	}

	public void setOffShelvesTime(Date offShelvesTime) {
		this.offShelvesTime = offShelvesTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public FUserModel getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(FUserModel updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getProducePlace() {
		return producePlace;
	}

	public void setProducePlace(String producePlace) {
		this.producePlace = producePlace;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSharePercent() {
		return sharePercent;
	}

	public void setSharePercent(Integer sharePercent) {
		this.sharePercent = sharePercent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
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

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FGoodsModel [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", detail=");
		builder.append(detail);
		builder.append(", logoUrl=");
		builder.append(logoUrl);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", price=");
		builder.append(price);
		builder.append(", sell_qty=");
		builder.append(sell_qty);
		builder.append(", remain=");
		builder.append(remain);
		builder.append(", allocate=");
		builder.append(allocate);
		builder.append(", isHot=");
		builder.append(isHot);
		builder.append(", isNew=");
		builder.append(isNew);
		builder.append(", kind=");
		builder.append(kind);
		builder.append(", intro=");
		builder.append(intro);
		builder.append(", clickTimes=");
		builder.append(clickTimes);
		builder.append(", releaseUser=");
		builder.append(releaseUser);
		builder.append(", releaseTime=");
		builder.append(releaseTime);
		builder.append(", offShelvesUser=");
		builder.append(offShelvesUser);
		builder.append(", offShelvesTime=");
		builder.append(offShelvesTime);
		builder.append(", version=");
		builder.append(version);
		builder.append(", updatedUser=");
		builder.append(updatedUser);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", producePlace=");
		builder.append(producePlace);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", sharePercent=");
		builder.append(sharePercent);
		builder.append("]");
		return builder.toString();
	}

}