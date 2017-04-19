package com.softtek.mdm.dao;


import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.UserRoleDepartmentModel;

public interface UserRoleDepartmentDao extends CrudMapper<UserRoleDepartmentModel, Integer> {
	/**
	 * 根据id删除（物理删除）
	 * @param id
	 * @return
	 */
	int truncateWithPk(Integer id);
	
	/**
	 * 根据用户id，查询主键id结果
	 * @param id
	 * @return
	 */
	UserRoleDepartmentModel findByUserId(Integer id);
	
	/**
	 * 根据机构id和管理员id查询记录
	 * @param map
	 * @return
	 */
	UserRoleDepartmentModel findOneByMaps(Map<String, Integer> map);
	
	/**
	 * 根据角色id查找
	 * @param roleId
	 * @return
	 */
	List<UserRoleDepartmentModel> findWithRoleId(Integer roleId);
	
	int findOrdIdByUid(Integer id);
}
