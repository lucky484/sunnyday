package com.f2b2c.eco.model.bean;

import java.util.List;

public class FkindBean {
	/**
	 * 类型ID
	 */
	private Integer id;

	/**
	 * 类型名称
	 */
	private String kindName;

	/**
	 * 权重
	 */
	private Integer weight;

    /**
     * 分类icon图标
     */
    private String iconUrl;

    /**
     * 子分类
     */
    private List<FkindBean> subList;
    
	public Integer getId() {
		return id;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public List<FkindBean> getSubList() {
		return subList;
	}

	public void setSubList(List<FkindBean> subList) {
		this.subList = subList;
	}
}
