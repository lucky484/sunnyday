/**
* @ClassName: RoleAuthority
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Apr 9, 2016 4:00:55 PM
*/
package com.softtek.pst.model;

/**
 * @author jacob.shen
 *
 */
public class RoleAuthority {
	private long roleAuthorityId;
	private long roleId;
	private long authorityId;
	private Role role;
	private Authority authority;

	public RoleAuthority() {
	}

	public RoleAuthority(long roleAuthorityId, long roleId, long authorityId, Role role, Authority authority) {
		this.roleAuthorityId = roleAuthorityId;
		this.roleId = roleId;
		this.authorityId = authorityId;
		this.role = role;
		this.authority = authority;
	}

	public long getRoleAuthorityId() {
		return roleAuthorityId;
	}

	public void setRoleAuthorityId(long roleAuthorityId) {
		this.roleAuthorityId = roleAuthorityId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(long authorityId) {
		this.authorityId = authorityId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

}
