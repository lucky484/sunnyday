/**
* @ClassName: Authority
* @Description: 用户权限
* @author Jacob Shen
* @date Apr 9, 2016 11:09:16 AM
*/
package com.softtek.pst.model;

import java.util.List;

public class Authority {
	private long authorityId;
	private String authorityName;
	private String authorityDescription;
	private String authorityUrl;
	private String authorityType;
	private List<RoleAuthority> roles;

	public Authority() {
		super();
	}

	public Authority(long authorityId, String authorityName, String authorityDescription, String authorityUrl,
			String authorityType, List<RoleAuthority> roles) {
		this.authorityId = authorityId;
		this.authorityName = authorityName;
		this.authorityDescription = authorityDescription;
		this.authorityUrl = authorityUrl;
		this.roles = roles;
		this.authorityType = authorityType;
	}

	public long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(long authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityDescription() {
		return authorityDescription;
	}

	public void setAuthorityDescription(String authorityDescription) {
		this.authorityDescription = authorityDescription;
	}

	public List<RoleAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleAuthority> roles) {
		this.roles = roles;
	}

	public String getAuthorityUrl() {
		return authorityUrl;
	}

	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}

	public String getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}

}
