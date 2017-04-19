package com.softtek.mdm.dao;

import java.util.Collection;

import com.softtek.mdm.model.MenuModel;

public interface MenuDao extends CrudMapper<MenuModel, Integer>{
	
	/**
	 * 根据机构id查询所属菜单，并按权重排序
	 * @param orgId
	 * @return
	 */
	Collection<MenuModel> findAllByOrgId(Integer orgId);
	
	Collection<MenuModel> findAllMenusByOrgId(Integer orgId);
	
	Integer findAllMenusByOrgIdCount(Integer orgId);
	
	Collection<MenuModel> findAllShowMenu();
	
	Collection<MenuModel> findAllMenus();
	/**
	 * 查询当前节点的一级子女节点
	 * @param parentId
	 * @return
	 */
	Collection<MenuModel> getChildren(Integer parentId);
}
