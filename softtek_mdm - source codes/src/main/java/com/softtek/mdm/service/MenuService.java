package com.softtek.mdm.service;


import java.util.List;

import com.softtek.mdm.model.MenuModel;

public interface MenuService {

	/**
	 * 根据机构id查询所属菜单，并按权重排序
	 * @param orgId
	 * @return
	 */
	List<MenuModel> findAllByOrgId(Integer orgId);
	
	List<MenuModel> findAllMenusByOrgId(Integer orgId);
	
	List<MenuModel> findAllShowMenu();
	
	List<MenuModel> findAllMenus();
	/**
	 * 获取当前节点的一级子女节点
	 * @param parentId
	 * @return
	 */
	List<MenuModel> getChildren(String parentId);
}
