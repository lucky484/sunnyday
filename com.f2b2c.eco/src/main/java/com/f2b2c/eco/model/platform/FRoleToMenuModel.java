package com.f2b2c.eco.model.platform;

import java.io.Serializable;

public class FRoleToMenuModel implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 角色
     */
    private FRoleModel role;

    /**
     * 菜单
     */
    private FMenuModel menu;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FRoleModel getRole() {
		return role;
	}

	public void setRole(FRoleModel role) {
		this.role = role;
	}

	public FMenuModel getMenu() {
		return menu;
	}

	public void setMenu(FMenuModel menu) {
		this.menu = menu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FRoleToMenuModel [id=");
		builder.append(id);
		builder.append(", role=");
		builder.append(role);
		builder.append(", menu=");
		builder.append(menu);
		builder.append("]");
		return builder.toString();
	}

    
}