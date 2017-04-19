package com.softtek.mdm.model;

/**
 * 用户类型与权限的对应表
 * @author color.wu
 *
 */
public class AuthModel {

	private Integer id;
	
	private Integer user_type;
	
	private String auth_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getAuth_name() {
		return auth_name;
	}

	public void setAuth_name(String auth_name) {
		this.auth_name = auth_name;
	}

	
	
	
}
