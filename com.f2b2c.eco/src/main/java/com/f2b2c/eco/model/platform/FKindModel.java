package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;

public class FKindModel implements Serializable {
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
	private FKindModel parent;

	/**
     * 种类
     */
	private String catalog;

    /**
     * 种类
     */
    private Date setWeightTime;

	/**
     * 权重
     */
	private Integer weight;

    /**
     * 分类icon图标
     */
    private String iconUrl;

	/**
     * 分类icon图标
     */
	private String isEnable;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKindName() {
		return kindName;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

    /**
     * @return setWeightTime
     */

    public Date getSetWeightTime() {
        return setWeightTime;
    }

    /**
     * @param setWeightTime
     *            要设置的 setWeightTime
     */

    public void setSetWeightTime(Date setWeightTime) {
        this.setWeightTime = setWeightTime;
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

	public FKindModel getParent() {
		return parent;
	}

	public void setParent(FKindModel parent) {
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