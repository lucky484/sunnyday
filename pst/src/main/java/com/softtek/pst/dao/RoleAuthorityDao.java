package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.RoleAuthority;

public interface RoleAuthorityDao {

	public List<RoleAuthority> getAuthoritiesByRole(long roleId);

	public List<RoleAuthority> getRolesByAuthority(long roleId);
}
