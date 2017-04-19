package com.softtek.mdm.model;

public class RoleMenuModel extends BaseEntity{

	private Integer id;
	/**
	 * 所属机构
	 */
	private OrganizationModel organization;
	/**
	 * 所属角色
	 */
	private RoleModel role;
	/**
	 * 所属菜单
	 */
	private MenuModel menu;
	/**
	 * 菜单url
	 */
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrganizationModel getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationModel organization) {
		this.organization = organization;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
