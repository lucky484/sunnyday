package com.f2b2c.eco.model.platform;

import java.io.Serializable;

public class FMenuModel implements Serializable {
    /**
     * 菜单ID
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父菜单ID
     */
    private FMenuModel parent;

    /**
     * 菜单链接
     */
    private String href;

    /**
     * 菜单图片
     */
    private String icon;
    
    /**
     * 比重
     */
    private Integer weight;
    
    /**
     * 父id
     */
    private Integer parentId;
    
    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FMenuModel getParent() {
		return parent;
	}

	public void setParent(FMenuModel parent) {
		this.parent = parent;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "FMenuModel [id=" + id + ", menuName=" + menuName + ", remark="
				+ remark + ", parent=" + parent + ", href=" + href + ", icon="
				+ icon + ", weight=" + weight + ", parentId=" + parentId + "]";
	}

}