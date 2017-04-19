package com.softtek.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.RoleMenuDao;
import com.softtek.mdm.model.RoleMenuModel;
import com.softtek.mdm.model.RoleModel;
import com.softtek.mdm.service.RoleMenuService;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Override
	public int save(RoleMenuModel entity) {
		return roleMenuDao.save(entity);
	}

	@Override
	public List<RoleMenuModel> findListByRoleId(RoleModel entity) {
		return (List<RoleMenuModel>) roleMenuDao.findListByRoleId(entity);
	}

	@Override
	public int deleteByRoleId(Integer roleId) {
		return roleMenuDao.deleteByRoleId(roleId);
	}

}
