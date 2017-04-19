package com.softtek.mdm.service;


import java.util.Map;

import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;

public interface UserRoleDepartmentService {

	int save(UserRoleDepartmentModel entity);
	
	int truncateWithUserId(Integer id);
	
	/**
	 * 根据机构id和管理员id查询记录
	 * @param map
	 * @return
	 */
	UserRoleDepartmentModel findOneByMaps(Integer orgId,Integer userId);
	
	int truncateWithRoleId(Integer roleId);
	
	int truncateWithPk(Integer roleId);
	
	int findOrdIdByUid(Integer id);
	
	Map<String, Object> update(UserRoleDepartmentModel userRoleDepartment, String departmentIds,OrganizationModel organization,Integer auth);
}
