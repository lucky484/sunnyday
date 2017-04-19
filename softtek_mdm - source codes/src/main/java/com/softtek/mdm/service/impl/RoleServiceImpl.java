package com.softtek.mdm.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.RoleDao;
import com.softtek.mdm.dao.RoleMenuDao;
import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.RoleMenuModel;
import com.softtek.mdm.model.RoleModel;
import com.softtek.mdm.service.RoleService;

import jodd.util.StringUtil;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Override
	public int save(RoleModel entity) {
		return roleDao.save(entity);
	}
	@Override
	public Collection<RoleModel> findAllByOrgId(Integer orgId) {
		return roleDao.findAllByOrgId(orgId);
	}
	
	@Override
	public Page findRecordsByPage(Map<String, Object> map) {
		Page page=new Page();
		List<RoleModel> list=(List<RoleModel>) roleDao.findRecordsByPage(map);
		Integer count = roleDao.queryCountByParamsMap(map);
		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return page;
	}
	
	@Override
	public Integer AllCountByOrgId(Integer orgId) {
		return roleDao.AllCountByOrgId(orgId);
	}
	@Override
	public RoleModel findOne(Integer id) {
		return roleDao.findOne(id);
	}
	@Override
	public int update(RoleModel entity) {
		return roleDao.update(entity);
	}
	@Override
	public int deleteWithPk(Integer id) {
		return roleDao.deleteWithPk(id);
	}
	@Override
	public boolean isExists(String name, Integer orgId) {
		return roleDao.isExists(name, orgId)>0?true:false;
	}
	@Override
	public int create(Map<String, Object> map) {
		OrganizationModel organization = (OrganizationModel) map.get("organization");
		String rids=(String) map.get("rids");
		RoleModel role=(RoleModel) map.get("role");
		roleDao.save(role);
		List<RoleMenuModel> roleMenuList=new ArrayList<RoleMenuModel>();
		if (!StringUtil.isBlank(rids)) {
			String[] idStr = StringUtil.split(rids, ",");
			for (int i = 0; i < idStr.length; i++) {
				RoleMenuModel roleMenu = new RoleMenuModel();
				roleMenu.setOrganization(organization);
				roleMenu.setRole(role);
				MenuModel menu = new MenuModel();
				menu.setId(Integer.parseInt(idStr[i]));
				roleMenu.setMenu(menu);
				roleMenuList.add(roleMenu);
				//roleMenuService.save(roleMenu);
			}
			return roleMenuDao.saveRecordsBatch(roleMenuList);
		}
		return 0;
	}
	@Override
	public int update(Map<String, Object> map) {
		OrganizationModel organization = (OrganizationModel) map.get("organization");
		String rids=(String) map.get("rids");
		RoleModel role=(RoleModel) map.get("role");
		roleDao.update(role);
		int flag = roleMenuDao.deleteByRoleId(role.getId());
		if (flag > 0) {
			if (!StringUtil.isBlank(rids)) {
				List<RoleMenuModel> roleMenuList=new ArrayList<RoleMenuModel>();
				String[] idStr = StringUtil.split(rids, ",");
				for (int i = 0; i < idStr.length; i++) {
					RoleMenuModel roleMenu = new RoleMenuModel();
					//roleMenu.setId(CommUtil.getPrimaryKey() + i);
					roleMenu.setOrganization(organization);
					roleMenu.setRole(role);
					MenuModel menu = new MenuModel();
					menu.setId(Integer.parseInt(idStr[i]));
					roleMenu.setMenu(menu);
					roleMenuList.add(roleMenu);
				}
				return roleMenuDao.saveRecordsBatch(roleMenuList);
			}
		}
		return 0;
	}

}
