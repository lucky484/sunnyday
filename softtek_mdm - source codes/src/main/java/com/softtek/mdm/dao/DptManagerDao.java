package com.softtek.mdm.dao;


import java.util.Collection;
import java.util.Map;

import com.softtek.mdm.model.UserRoleDepartmentModel;
/**
 * @author josen.yang
 *
 */
public interface DptManagerDao extends CrudMapper<UserRoleDepartmentModel, Integer> {

	/**
	 * 根据机构ID查询所有的部门管理员
	 * @param id
	 * @return
	 */
	Collection<UserRoleDepartmentModel> findRecordsByPage(Map<String, Object> map);
	
	Integer findRecordsByPageCount(Map<String, Object> map);
	
	Integer AllCountByOrgId(Integer orgId);

	int deleteUrdByUrd(Integer id);
	int deleteOmByUserId(Integer id);
	int deleteUdByUrd(Integer id);
	
	public UserRoleDepartmentModel queryRoleByUid(Integer id);

	String findRoleNameById(Integer id);

	/**
	 * 查询满足条件的结果数
	 *
	 * @author brave.chen
	 * @param map 参数map
	 * @return 数量
	 */
	Integer queryCountByParamsMap(Map<String, Object> map);

	/**
	 * @param id
	 * @return
	 */
	Integer queryAuthByUid(Integer id);
}
