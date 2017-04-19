package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.OrgManagerRelationModel;

public interface OrgManagerRelationDao {

	/**
	 * 根据orgId查找所有属于的记录
	 * @param orgId
	 * @return
	 */
	Collection<OrgManagerRelationModel> findRecordsByOrgId(Integer orgId);
	
	/**
	 * 根据管理员id查找所有属于的记录
	 * @param mid
	 * @return
	 */
	Collection<OrgManagerRelationModel> findRecordsByManagerId(Integer mid);

	void saveOrgManagerMap(Map<String, Object> map);

	List<Integer> findIdListsByManagerId(Integer id);

	void deleteOrgManagerMap(Map<String, Object> delMap);

	void deleteManagerOrgMap(Map<String, Object> delMap);

}
