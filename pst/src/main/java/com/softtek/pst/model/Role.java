/**
* @ClassName: Role
* @Description: 用户角色
* @author Jacob Shen
* @date Apr 9, 2016 11:07:53 AM
*/
package com.softtek.pst.model;

import java.util.List;

public class Role {
	private long roleId;
	private String roleName;
	private String roleDescription;
	private List<RoleAuthority> roleAuthorities;
	private List<UserModel> users;

	public Role() {
	}

	public Role(long roleId, String roleName, String roleDescription, List<RoleAuthority> roleAuthorities, List<UserModel> users) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roleAuthorities = roleAuthorities;
		this.users = users;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public List<RoleAuthority> getRoleAuthorities() {
		return roleAuthorities;
	}

	public void setRoleAuthorities(List<RoleAuthority> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}

	public List<UserModel> getUsers() {
		return users;
	}

	public void setUsers(List<UserModel> users) {
		this.users = users;
	}

}
