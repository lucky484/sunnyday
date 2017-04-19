package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;

import com.softtek.mdm.model.UserDepartmentModel;

public interface UserDepartmentDao extends CrudMapper<UserDepartmentModel, Integer> {

	/**
	 * 根据外键id删除（物理删除）
	 * @param id
	 * @return
	 */
	int truncateWithFId(Integer fid);
	
	/**
	 * 根据关联表查询记录集
	 * @param fid
	 * @return
	 */
	Collection<UserDepartmentModel> findByFId(Integer fid);

	int truncateWithUrdId(Integer urdid);

	String findDptNameById(int id);
	
	/**
	 * 批量保存记录
	 * @param list
	 * @return 受影响的行数
	 */
	int saveRecordsBatch(List<UserDepartmentModel> list);

}
