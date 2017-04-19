package com.f2b2c.eco.model.platform;

import java.io.Serializable;

public class FUserToRoleModel implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户
     */
    private FUserModel user;

    /**
     * 角色
     */
    private FRoleModel role;
    
    private String roleName;
    
    private Integer roleId;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FUserModel getUser() {
		return user;
	}

	public void setUser(FUserModel user) {
		this.user = user;
	}

	public FRoleModel getRole() {
		return role;
	}

	public void setRole(FRoleModel role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FUserToRoleModel [id=");
		builder.append(id);
		builder.append(", user=");
		builder.append(user);
		builder.append(", role=");
		builder.append(role);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append("]");
		return builder.toString();
	}
    
}