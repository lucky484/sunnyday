package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.Map;

import com.softtek.mdm.model.RoleModel;

/**
 * 用户角色管理
 * @author color.wu
 *
 */
public interface RoleDao extends CrudMapper<RoleModel, Integer>{
	
	/**
	 * 根据组织机构id查询所有的用户角色信息
	 * @param orgId
	 * @return
	 */
	Collection<RoleModel> findAllByOrgId(Integer orgId);
	
	/**
	 * 通过分页查询记录
	 * @param map
	 * @return
	 */
	Collection<RoleModel> findRecordsByPage(Map<String, Object> map);
	
	/**
	 * 所有记录数
	 * @param orgId
	 * @return
	 */
	Integer AllCountByOrgId(Integer orgId);
	
	Integer isExists(String name,Integer orgId);

	/**
	 * 获取符合条件的角色对象
	 *
	 * @author brave.chen
	 * @param map 查询参数map
	 * @return 数量
	 */
	Integer queryCountByParamsMap(Map<String, Object> map);
	
}
