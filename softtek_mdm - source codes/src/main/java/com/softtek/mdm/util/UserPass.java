package com.softtek.mdm.util;

public class UserPass {

	private Integer id;
	private String username;
	private String password;
	private String newPassword;
	private String  reNewPassword;
	
	public UserPass() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserPass [id=" + id + ", username=" + username + ", password=" + password + ", newPassword="
				+ newPassword + ", reNewPassword=" + reNewPassword + "]";
	}

}
