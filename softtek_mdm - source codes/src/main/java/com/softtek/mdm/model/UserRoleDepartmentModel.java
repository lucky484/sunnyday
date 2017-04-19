package com.softtek.mdm.model;


public class UserRoleDepartmentModel extends BaseEntity {

	private Integer id;
	
	private OrganizationModel organization;
	
	private UserModel user;
	
	private RoleModel role;
	
	private Integer islock;
	
	private String mark;
	
	private String departIds;
	
	private String departNames;


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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDepartIds() {
		return departIds;
	}

	public void setDepartIds(String departIds) {
		this.departIds = departIds;
	}

	public String getDepartNames() {
		return departNames;
	}

	public void setDepartNames(String departNames) {
		this.departNames = departNames;
	}
	
}
