package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.MenuDictModel;
import com.hd.pfirs.model.RoleAuthModel;
import com.hd.pfirs.model.RoleModel;

/**
 * 
 * @author brave.chen
 * @since 2016-01-26
 */
public interface RoleService
{
	/**
	 * 获取角色列表
	 * @param page 第几页
	 * @param roleName 角色名称
	 * @return 角色列表
	 */
	List<RoleModel> getRole(int page, String roleName);

	/**
	 * 获取满足条件的角色条数
	 * @param roleName 角色名称
	 * @return 满足条件的角色总条数
	 */
	int getRoleCount(String roleName);
	
	/**
	 * 保存角色
	 * @param model 角色对象
	 */
	void saveRole(RoleModel model);

	/**
	 * 删除角色对象
	 * @param roleId 角色ID
	 * @return 删除标志
	 */
	int deleteRole(Long roleID);
	
	/**
	 * 根据角色名称获取角色id
	 * @param roleName 角色名称
	 * @return
	 */
	RoleModel getRoleByRoleName(String roleName);
	
	
	/**
	 * 根据角色ID获取角色对象
	 * @param roleID 角色ID
	 * @return 角色对象
	 */
	RoleModel getRoleByRoleID(Long roleID);
	
	/**
	 * 根据角色id获取角色权限ID
	 * @param roleId
	 * @return
	 */
	List<RoleAuthModel> getRoleAuthModelByRoleId(Long roleID);

	/**
	 * 获取所有的菜单信息
	 * @return 菜单列表
	 */
	List<MenuDictModel> getAllMenus();

	/**
	 * 获取有权限的菜单
	 * @param id 菜单ID
	 * @return 有权限的菜单列表
	 */
	List<MenuDictModel> getAuthMenusByRoleId(Long id);
	
	/**
	 * 保存角色权限对象
	 * @param roleAuthModel 角色权限对象
	 * @return 保存结果
	 */
	int saveRoleAuthModel(RoleAuthModel roleAuthModel);

	/**
	 * 更新角色对象
	 * @param roleModel 角色对象
	 */
	int updateRole(RoleModel roleModel);
	
	/**
	 * 批量保存角色权限对象
	 * @param roleAuthModels 角色权限对象列表
	 */
	void batchAddRoleAuth(List<RoleAuthModel> roleAuthModels);

	/**
	 * 获取角色id序列
	 * @return 角色id序列
	 */
	Long getRoleID();

	/**
	 * 删除角色的菜单权限
	 * @param delIds 需要删除的菜单权限
	 * @param roleID 角色ID
	 */
	void deleteAuthMenus(List<Long> delIds, Long roleID);

	/**
	 * 获取角色对象列表
	 * @ return 角色对象列表
	 */
	List<RoleModel> getRoles();
}
