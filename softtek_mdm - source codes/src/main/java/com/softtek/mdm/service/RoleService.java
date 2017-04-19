package com.softtek.mdm.service;


import java.util.Collection;
import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.RoleModel;


public interface RoleService {

	/**
	 * 保存实体信息
	 * @param entity
	 * @return
	 */
	int save(RoleModel entity);
	
	int update(RoleModel entity);
	
	/**
	 * 根据组织机构id查询所有的用户角色信息
	 * @param orgId
	 * @return
	 */
	Collection<RoleModel> findAllByOrgId(Integer orgId);
	
	/**
	 * 通过机构id，起始记录index和记录长度查询对应的记录
	 * @param map 参数
	 * @return 分页对象
	 */
	Page findRecordsByPage(Map<String, Object> map);
	
	/**
	 * 所有的数据记录根据机构id
	 * @param orgId
	 * @return
	 */
	Integer AllCountByOrgId(Integer orgId);
	
	/**
	 * 根据id查询一个Role对象
	 * @param id
	 * @return
	 */
	RoleModel findOne(Integer id);
	
	int deleteWithPk(Integer id);
	
	boolean isExists(String name,Integer orgId);
	
	/**
	 * 创建新的角色包含管理的menus
	 * @param map
	 * @return
	 */
	int create(Map<String, Object> map);
	
	int update(Map<String, Object> map);
}
