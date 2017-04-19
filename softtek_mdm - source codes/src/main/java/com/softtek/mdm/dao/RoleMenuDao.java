package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;

import com.softtek.mdm.model.RoleMenuModel;
import com.softtek.mdm.model.RoleModel;

public interface RoleMenuDao extends CrudMapper<RoleMenuModel, Integer>{

	/**
	 * 根据角色查询角色对应的菜单
	 * @param entity
	 * @return
	 */
	Collection<RoleMenuModel> findListByRoleId(RoleModel entity);
	
	/**
	 * 根据roleid删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(Integer roleId);
	
	/**
	 * 批量保存
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<RoleMenuModel> list);
}
