package com.softtek.mdm.model;

/**
 * 用户和部门以及角色的关联表对应数据表users_role
 * @author color.wu
 *
 */
public class UserRoleModel extends BaseEntity{

	private Integer id;
	/**
	 * 所属机构
	 */
	private OrganizationModel organization;
	/**
	 * 用户对象
	 */
	private UserModel user;
	/**
	 * 用户角色
	 */
	private RoleModel role;
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
	
}
