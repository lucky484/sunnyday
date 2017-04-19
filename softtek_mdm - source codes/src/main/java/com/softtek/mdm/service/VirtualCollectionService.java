package com.softtek.mdm.service;

import java.util.Collection;
import java.util.Map;

import com.softtek.mdm.model.VirtualCollectionModel;
import com.softtek.mdm.model.VirtualGroupModel;

public interface VirtualCollectionService {
	
	String save(VirtualCollectionModel entity,VirtualGroupModel virtualGroup,Integer create_by);
	
	/**
	 * 查询虚拟组是否以及存在
	 * @param virtualName
	 * @param orgID
	 * @return 
	 */
	boolean isExists(VirtualCollectionModel entity);
	/**
	 * 查询虚拟组是否以及存在
	 * @param virtualName
	 * @param orgID
	 * @return 
	 */
	int existsEditName(VirtualCollectionModel entity);
	
	/**
	 * 根据组织架构id获取相应的列表
	 * @param id
	 * @return
	 */
	Collection<VirtualCollectionModel> findAllByOrgID(Integer id);
	/**
	 * 根据组织架构id获取相应的列表
	 * @param id
	 * @return
	 */
	Map<String, Object> findAllByOrgIdMap(Integer orgId);
	/**
	 * 删除虚拟组
	 * @param id
	 */
	void deleteByCid(int id);
	/**
	 * 更新虚拟组集合
	 * 
	 */
	String update(VirtualCollectionModel entity,VirtualGroupModel virtualGroup);
	
}
