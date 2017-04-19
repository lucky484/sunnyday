package com.f2b2c.eco.model.platform;

import java.io.Serializable;

public class BKindModel implements Serializable {
	/**
     * 类型ID
     */
	private Integer id;

	/**
     * 类型名称
     */
	private String kindName;

	/**
     * 备注
     */
	private String remark;

	/**
     * 父类型
     */
	private BKindModel parent;

	/**
     * 种类
     */
	private String catalog;

	/**
     * 权重
     */
	private Integer weight;

    /**
     * 分类icon图标
     */
    private String iconUrl;

	/**
     * 分类广告图
     */
	private String picUrl;

    /**
     * 是否参与佣金 0:不参与 1：参与
     */
    private Integer isCommission;

	private static final long serialVersionUID = 1L;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getId() {
		return id;
	}

    public Integer getIsCommission() {
        return isCommission;
    }

    public void setIsCommission(Integer isCommission) {
        this.isCommission = isCommission;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BKindModel getParent() {
		return parent;
	}

	public void setParent(BKindModel parent) {
		this.parent = parent;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

    public String getIconUrl()
    {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    @Override
	public String toString() {
		return "FKindModel [id=" + id + ", kindName=" + kindName + ", remark="
				+ remark + ", parent=" + parent + ", catalog=" + catalog
				+ ", weight=" + weight + "]";
	}

}