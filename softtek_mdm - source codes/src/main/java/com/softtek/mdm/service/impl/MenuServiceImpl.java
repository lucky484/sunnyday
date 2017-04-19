package com.softtek.mdm.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.MenuDao;
import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Override
	public List<MenuModel> findAllByOrgId(Integer orgId) {
		return (List<MenuModel>) menuDao.findAllByOrgId(orgId);
	}
	@Override
	public List<MenuModel> findAllMenusByOrgId(Integer orgId) {
		return (List<MenuModel>) menuDao.findAllMenusByOrgId(orgId);
	}
	@Override
	public List<MenuModel> findAllShowMenu() {
		return (List<MenuModel>) menuDao.findAllShowMenu();
	}
	@Override
	public List<MenuModel> findAllMenus() {
		return (List<MenuModel>) menuDao.findAllMenus();
	}
	@Override
	public List<MenuModel> getChildren(String parentId) {
		if (StringUtils.isEmpty(parentId)) {
			return null;
		}
		List<MenuModel> list=(List<MenuModel>) menuDao.getChildren(Integer.valueOf(parentId));
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list;
	}

}
	