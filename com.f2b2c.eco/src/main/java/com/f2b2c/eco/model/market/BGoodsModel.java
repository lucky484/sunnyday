package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.model.platform.FUserModel;

public class BGoodsModel implements Serializable {
	/**
	 * 商铺商品ID
	 */
	private Integer id;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品编号
	 */
	private String goodsNo;
	
	/**
	 * 商品详情
	 */
	private String detail;

	/**
	 * 商品图片多个|分割
	 */
	private String logoUrl;

	/**
	 * 商品单位
	 */
	private String unit;

	/**
	 * 重量
	 */
	private String unitOfWeight;
	/**
	 * 商品单价(分)
	 */
	private Integer price;

	/**
	 * 商品销量
	 */
	private Integer sales;

	/**
	 * 商品库存
	 */
	private Integer remain;

	/**
	 * 商品锁定数量
	 */
	private Integer allocate;

	/**
	 * 是否热销商品
	 */
	private Integer isHot;

	/**
	 * 是否新品
	 */
	private Integer isNew;

	/**
	 * 商品类别
	 */
	private FKindModel kind;

	/**
	 * 商品类型
	 */
	private String type;
	/**
	 * 商品简介
	 */
	private String intro;

	/**
	 * 商品点击率
	 */
	private Integer clickTimes;

	/**
	 * 商店
	 */
	private BShopInfoModel shop;

	/**
	 * 发布时间
	 */
	private Date releaseTime;

	/**
	 * 商品下架人id
	 */
	private BUserModel offShelvesUser;

	/**
	 * 商品下架时间
	 */
	private Date offShelvesTime;

	/**
	 * 商品版本号
	 */
	private Integer version;

	/**
	 * 商品更新人
	 */
	private BUserModel updatedUser;

	/**
	 * 商品更新时间
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
	 * 审批人id
	 */
	private FUserModel approveUser;

	/**
	 * 审批时间
	 */
	private Date approveTime;
	/**
	 * 规格
	 */
	private String spec;
	/**
	 * 是否通过审核
	 */
	private Integer status;

	/**
	 * 审批不通过或者通过原因
	 */
	private String reason;
	
	/**
	 * 佣金比例如果是60%，则只需要输入60即可，不允许小数点
	 */
	private String sharePercent;

	/**
	 * 产品参数
	 */
	private String parameter;
	/**
	 * 特征
	 */
	private String properties;

	/**
	 * 进口/国产 0:国产；1：进口
	 */
	private Integer madeInChina;

	/**
	 * 是否是水果分类(0.水果分类 1.非水果分类)
	 */
	private String catalog;

	/**
	 * 是否参与佣金 0:不参与 1：参与
	 */
	private String isCommission;
	
	public String getProperties() {
		return properties;
	}

	public String getType() {
		return type;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Integer getMadeInChina() {
		return madeInChina;
	}

	public String getUnitOfWeight() {
		return unitOfWeight;
	}

	public void setUnitOfWeight(String unitOfWeight) {
		this.unitOfWeight = unitOfWeight;
	}

	public void setMadeInChina(Integer madeInChina) {
		this.madeInChina = madeInChina;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
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
	
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
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

	public Integer getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}

	public BShopInfoModel getShop() {
		return shop;
	}

	public void setShop(BShopInfoModel shop) {
		this.shop = shop;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public BUserModel getOffShelvesUser() {
		return offShelvesUser;
	}

	public void setOffShelvesUser(BUserModel offShelvesUser) {
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

	public BUserModel getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(BUserModel updatedUser) {
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

	public FUserModel getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(FUserModel approveUser) {
		this.approveUser = approveUser;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * @param spec
	 *            the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getSharePercent() {
		return sharePercent;
	}

	public void setSharePercent(String sharePercent) {
		this.sharePercent = sharePercent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getIsCommission() {
		return isCommission;
	}

	public void setIsCommission(String isCommission) {
		this.isCommission = isCommission;
	}

	@Override
	public String toString() {
		return "BGoodsModel [id=" + id + ", name=" + name + ", detail="
				+ detail + ", logoUrl=" + logoUrl + ", unit=" + unit
				+ ", price=" + price + ", sales=" + sales + ", remain="
				+ remain + ", allocate=" + allocate + ", isHot=" + isHot
				+ ", isNew=" + isNew + ", kind=" + kind + ", intro=" + intro
				+ ", clickTimes=" + clickTimes + ", shop=" + shop
				+ ", releaseTime=" + releaseTime + ", offShelvesUser="
				+ offShelvesUser + ", offShelvesTime=" + offShelvesTime
				+ ", version=" + version + ", updatedUser=" + updatedUser
				+ ", updatedTime=" + updatedTime + ", producePlace="
				+ producePlace + ", remark=" + remark + ", approveUser="
				+ approveUser + ", approveTime=" + approveTime + ", status="
				+ status + ", reason=" + reason + ", sharePercent="
				+ sharePercent + "]";
	}
}