package com.softtek.mdm.dao;

import java.util.Collection;

import com.softtek.mdm.model.VirtualCollectionModel;

public interface VirtualCollectionDao extends CrudMapper<VirtualCollectionModel, Integer> {
	/**
	 * 查询虚拟组是否以及存在
	 * @param virtualName
	 * @param orgID
	 * @return 
	 */
	boolean isExists(VirtualCollectionModel entity);
	
	/**
	 * 删除虚拟组集合
	 */
	int deleteCollection(int id);
	/**
	 * 删除虚拟组
	 */
	int deleteVirtualGroup(int id);
	/**
	 * 删除虚拟组集合用户
	 */
	int deleteMember(int id);
	
	/**
	 * 根据组织架构id获取相应的列表
	 * @param id
	 * @return
	 */
	Collection<VirtualCollectionModel> findAllByOrgID(Integer id);

	int existsEditName(VirtualCollectionModel entity);
	
}
