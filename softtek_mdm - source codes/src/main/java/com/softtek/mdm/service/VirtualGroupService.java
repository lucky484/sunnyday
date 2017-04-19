package com.softtek.mdm.service;

import java.util.Collection;
import java.util.List;

import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.VirtualGroupModel;

public interface VirtualGroupService {
	/**
	 * 保存虚拟组
	 * @param entity
	 * @return
	 */
	int save(VirtualGroupModel entity);
	
	/**
	 * 根据collection_id删除虚拟组
	 * @param id
	 * @return
	 */
    int delete(int id);
	
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
	 * 根据机构id数组找出符合的记录集合
	 * @param ids
	 * @return
	 */
	Collection<VirtualGroupModel> findByOrgId(Integer orgId);
	/**
	 * 根据机构id数组找出符合的记录集合Member
	 * @param ids
	 * @return
	 */
	Collection<VirtualGroupModel> findByOrgIdMember(Integer orgId,List<Integer> idlist);
	/**
	 * 更新虚拟组排序权值weight
	 * @param entity
	 * @return
	 */
	int updateWeight(VirtualGroupModel entity);
	/**
	 * 更新虚拟组名称
	 * @param entity
	 * @return
	 */
	int updateName(VirtualGroupModel entity);
	/**
	 * 查询虚拟组是否以及存在
	 * @param virtualName
	 * @param orgID
	 * @return 
	 */
	boolean isExists(VirtualGroupModel entity);
	/**
	 * 根据虚拟组ID查询 用户
	 * @param id
	 * @param idlist 
	 * @return
	 */
	Collection<UserModel> queryExistMember(Integer id,String name, List<Integer> idlist);
	/**
	 * 根据虚拟组ID查询不存在的用户(单选)
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryNoExistMemberRadio(Integer orgid,Integer id,String name,List<Integer> idlist);
	/**
	 * 根据虚拟组ID查询不存在的用户
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryNoExistMember(Integer orgid,Integer id,String name,List<Integer> idlist);
	/**
	 * 根据组id和index查询指定的10条记录
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryNoExistMemberPage(Integer orgid,Integer id,Integer index,String name,List<Integer> idlist);
	/**
	 * 根据组id和index查询指定的10条记录
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryNoExistMemberPageRadio(Integer orgid,Integer id,Integer index,String name,List<Integer> idlist);
	/**
	 * 根据组id查询不存在用户页数
	 * @param id
	 * @return
	 */
	int queryNeMemberMaxPage(Integer orgid,Integer id,String name,List<Integer> idlist);
	/**
	 * 根据组id查询不存在用户页数
	 * @param id
	 * @return
	 */
	int queryNeMemberMaxPageRadio(Integer orgid,Integer id,String name,List<Integer> idlist);
	/**
	 * 根据组id查询存在用户页数
	 * @param id
	 * @return
	 */
	int queryEMemberMaxPage(Integer id,String name,List<Integer> idlist);
	/**
	 * 根据组id查询存在用户的总页数
	 * @param id
	 * @return
	 */
	Collection<UserModel> queryExistMemberPage(Integer id,Integer index,String name,List<Integer> idlist);

	Integer existsEditName(VirtualGroupModel virtualGroup);

	VirtualGroupModel findOne(int parseInt);
	
}
