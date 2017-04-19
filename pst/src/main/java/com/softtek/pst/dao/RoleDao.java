package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.Role;

public interface RoleDao {

	public Role getRoleById(long roleId);

	public Role getRoleById2(long roleId);
	
	public List<Role> getRole();

}
