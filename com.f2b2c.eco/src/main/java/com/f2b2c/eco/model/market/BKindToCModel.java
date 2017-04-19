package com.f2b2c.eco.model.market;

import java.util.List;

public class BKindToCModel {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 类型名称
	 */
	private String kindName;

	/**
	 * 总类
	 */
	private String catalog;
	
	/**
	 * 父类
	 */
	private Integer parentId;

	/**
	 * 权重（排序）
	 */
	private Integer weight;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 商品分类图片
	 */
	private String iconUrl;

	/**
	 * 商品分类图片
	 */
	private String picUrl;

	/**
	 * 子集分类
	 */
	private List<BKindToCModel> subList;

	public Integer getId() {
		return id;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BKindToCModel> getSubList() {
		return subList;
	}

	public void setSubList(List<BKindToCModel> subList) {
		this.subList = subList;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
