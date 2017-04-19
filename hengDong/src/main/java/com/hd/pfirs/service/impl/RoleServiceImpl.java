package com.hd.pfirs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.RoleDao;
import com.hd.pfirs.model.MenuDictModel;
import com.hd.pfirs.model.RoleAuthModel;
import com.hd.pfirs.model.RoleModel;
import com.hd.pfirs.service.RoleService;

/**
 * 角色管理实现类
 * 
 * @author brave.chen
 * @since 2016-01-26
 */
@Service
public class RoleServiceImpl implements RoleService
{

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<RoleModel> getRole(int page, String roleName)
	{
		return roleDao.getRole(page, roleName);
	}

	@Override
	public int getRoleCount(String roleName)
	{
		return roleDao.getRoleCount(roleName);
	}

	@Override
	public void saveRole(RoleModel model)
	{
		roleDao.saveRole(model);
	}

	@Override
	public int deleteRole(Long roleId)
	{
		return roleDao.deleteRole(roleId);
	}

	@Override
	public RoleModel getRoleByRoleName(String roleName)
	{
		return roleDao.getRoleByRoleName(roleName);
	}

	@Override
	public List<RoleAuthModel> getRoleAuthModelByRoleId(Long roleID)
	{
		return roleDao.getRoleAuthModelByRoleId(roleID);
	}

	@Override
	public RoleModel getRoleByRoleID(Long roleID)
	{
		return roleDao.getRoleByRoleID(roleID);
	}

	@Override
	public List<MenuDictModel> getAllMenus()
	{
		return roleDao.getAllMenus();
	}

	@Override
	public List<MenuDictModel> getAuthMenusByRoleId(Long id)
	{
		return roleDao.getAuthMenusByRoleId(id);
	}

	@Override
	public int saveRoleAuthModel(RoleAuthModel roleAuthModel)
	{
		return roleDao.saveRoleAuthModel(roleAuthModel);
	}

	@Override
	public int updateRole(RoleModel roleModel)
	{
		return roleDao.updateRole(roleModel);
	}

	@Override
	public void batchAddRoleAuth(List<RoleAuthModel> roleAuthModels)
	{
		roleDao.batchAddRoleAuth(roleAuthModels);
	}

	@Override
	public Long getRoleID()
	{
		return roleDao.getRoleID();
	}

	@Override
	public void deleteAuthMenus(List<Long> delIds, Long roleID)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delIds", delIds);
		map.put("roleID", roleID);
		roleDao.deleteAuthMenus(map);
	}

	@Override
	public List<RoleModel> getRoles()
	{
		return roleDao.getRoles();
	}

}
