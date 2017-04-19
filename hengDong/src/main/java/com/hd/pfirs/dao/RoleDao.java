package com.hd.pfirs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.MenuDictModel;
import com.hd.pfirs.model.RoleAuthModel;
import com.hd.pfirs.model.RoleModel;

/**
 * 权限dao
 * @author brave.chen
 * @since 2016-01-26
 */
public interface RoleDao
{
	/**
	 * 获取选中页的角色列表
	 * @param page 当前页
	 * @param roleName 角色名称
	 * @return 角色列表
	 */
	public List<RoleModel> getRole(
			@Param(value = "page")int page,
			@Param(value = "roleName") String roleName);
	
	/**
	 * 获取指定名称的角色列表
	 * @param roleName 角色名称，为空查询全部
	 * @return 指定名称的角色条数
	 */
	public int getRoleCount(
			@Param(value = "roleName") String roleName);

	/**
	 * 保存角色对象
	 * @param model 角色对象
	 */
	public void saveRole(RoleModel model);

	/**
	 * 保存角色权限对象
	 * @param roleAuthModel 角色权限对象
	 */
	public int saveRoleAuthModel(RoleAuthModel roleAuthModel);

	/**
	 * 删除角色对象
	 * @param roleID 角色id
	 * @return 删除标志
	 */
	public int deleteRole(Long roleID);

	/**
	 * 根据角色名称获取角色id
	 * @param roleName 角色名称
	 * @return 角色id
	 */
	public RoleModel getRoleByRoleName(String roleName);

	/**
	 * 角色权限列表
	 * @param roleID 角色id
	 * @return 角色权限列表
	 */
	public List<RoleAuthModel> getRoleAuthModelByRoleId(Long roleID);

	/**
	 * 根据角色id获取角色对象
	 * @param roleID 角色ID
	 * @return 角色对象
	 */
	public RoleModel getRoleByRoleID(Long roleID);

	/**
	 * 获取所有菜单信息
	 * @return 菜单列表
	 */
	public List<MenuDictModel> getAllMenus();

	/**
	 * 获取有权限的菜单
	 * @param id 角色ID
	 * @return 有权限的菜单列表
	 */
	public List<MenuDictModel> getAuthMenusByRoleId(Long id);

	/**
	 * 更新角色对象
	 * @param roleModel 角色对象
	 * @return 更新结果
	 */
	public int updateRole(RoleModel roleModel);

	/**
	 * 批量保存角色权限对象
	 * @param roleAuthModels 角色权限对象列表
	 */
	public void batchAddRoleAuth(List<RoleAuthModel> roleAuthModels);


	/**
	 * 获取角色id序列
	 * @return 角色id序列
	 */
	public Long getRoleID();

	/**
	 * 删除角色的菜单权限
	 * @param map 删除条件
	 */
	public void deleteAuthMenus(Map<String, Object> map);

	/**
	 * 获取角色对象列表
	 * @ return 角色对象列表
	 */
	public List<RoleModel> getRoles();
}
