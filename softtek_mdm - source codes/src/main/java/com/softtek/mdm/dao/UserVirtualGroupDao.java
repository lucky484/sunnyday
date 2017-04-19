package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.ExcelVirModel;
import com.softtek.mdm.model.UserVirtualGroupModel;

public interface UserVirtualGroupDao extends CrudMapper<UserVirtualGroupModel, Integer> {

	Collection<UserVirtualGroupModel> findByUser(Integer orgId,Integer id);
	
	int deleteByIds(List<Integer> ids);
    
	int insertMember(UserVirtualGroupModel entity);
	
	int deleteByid(Integer userid,Integer groupid,Integer colid);

	int truncateWithUserId(Integer userId);

	int truncateWithUserIds(List<Integer> ids);

	int insertMembers(List<UserVirtualGroupModel> modelList);
	
	/**
	 * 批量保存
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<UserVirtualGroupModel> list);
	
	/**
	 * 查询存在虚拟组用户
	 * @param map
	 * @return
	 */
	List<String> findVirExistMember(Map<String, Integer> map);
	/**
	 * 查询所有用户
	 * @param map
	 * @return
	 */
	List<String> findAllMember(Map<String, Integer> map);
	/**
	 * 批量插入用户
	 * @param list
	 * @return
	 */
	int insertMembersByIdList(List<ExcelVirModel> list);

	/**
	 * 查询集合下存在的用户名
	 * @param map
	 * @return
	 */
	List<String> findAllColMember(Map<String, Integer> map);
	
	List<Integer> getUserIdsByVirtualIdList(Map<String, Object> map);

}
