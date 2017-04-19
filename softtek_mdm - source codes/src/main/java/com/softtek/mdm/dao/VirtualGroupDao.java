package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.Map;

import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.VirtualGroupModel;

public interface VirtualGroupDao extends CrudMapper<VirtualGroupModel, Integer>{
	/**
	 * 根据collection_id找出符合的记录集合
	 * @param id
	 * @return
	 */
	Collection<VirtualGroupModel> findByCid(Integer id);
	/**
	 * 根据collection_id数组找出符合的记录集合
	 * @param ids
	 * @return
	 */
	Collection<VirtualGroupModel> findByCids(Integer[] ids);
	/**
	 * 根据机构ID查到所有的虚拟组
	 * @param id
	 * @return
	 */
	Collection<VirtualGroupModel> findByOrgId(Integer id);
	/**
	 * 根据机构ID查到所有的虚拟组
	 * @param id
	 * @return
	 */
	Collection<VirtualGroupModel> findByOrgIdMember(Map<String,Object> map);
	/**
	 * 根据虚拟组的id集合，查询符合的记录
	 * @param ids
	 * @return
	 */
	Collection<VirtualGroupModel> findByids(Integer[] ids);
	/**
	 * 删除虚拟组所有用户
	 * @param id
	 * @return
	 */
	int deleteAllUsers(int id);
	/**
	 * 根据name更新权值排序
	 * @param entity
	 * @return
	 */
	int updateWeightByName(VirtualGroupModel virtualGroup);
	/**
	 * 根据ID更新权值排序
	 * @param entity
	 * @return
	 */
	int updateNameById(VirtualGroupModel virtualGroup);
	/**
	 * 是否有虚拟组
	 * @param entity
	 * @return
	 */
	boolean isExists(VirtualGroupModel virtualGroup);
	/**
	 * 根据虚拟组ID查询 用户
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryExistMember(Integer id);
	/**
	 * 根据虚拟组ID查询不存在的用户
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryNoExistMember(Integer id);
	
	Integer existsEditName(VirtualGroupModel virtualGroup);
}
