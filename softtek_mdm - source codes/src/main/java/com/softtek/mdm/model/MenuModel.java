package com.softtek.mdm.model;

/**
 * 对应数据表menu
 * @author color.wu
 *
 */
public class MenuModel extends BaseEntity{

	private Integer id;
	/**
	 * 父级菜单
	 */
	private MenuModel parent;
	/**
	 * 所属机构
	 */
	private OrganizationModel organization;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单url
	 */
	private String address;
	/**
	 * 菜单权重
	 */
	private Integer weight;
	
	private Integer isshow;
	
	public Integer getIsshow() {
		return isshow;
	}
	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public MenuModel getParent() {
		return parent;
	}
	public void setParent(MenuModel parent) {
		this.parent = parent;
	}
	public OrganizationModel getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
	
	

}
