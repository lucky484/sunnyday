package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.RoleMenuModel;
import com.softtek.mdm.model.RoleModel;

public interface RoleMenuService {
	
	int save(RoleMenuModel entity);
	
	/**
	 * 根据角色查询角色对应的菜单
	 * @param entity
	 * @return
	 */
	List<RoleMenuModel> findListByRoleId(RoleModel entity);
	
	/**
	 * 根据roleid删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(Integer roleId);
}
