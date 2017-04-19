package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;

public interface ManagerDao extends CrudMapper<ManagerModel, Integer> {
	
	/**
	 * 鏍规嵁鐢ㄦ埛鍚嶆煡鎵剧敤鎴蜂俊鎭�
	 * @param name
	 * @return
	 */
	ManagerModel findOneByName(String username);

	/**
	 * 鐗╃悊鍒犻櫎鐢ㄦ埛
	 * @param id
	 * @return
	 */
	int truncateWithPk(Integer id);
	
	int truncateWithUserId(Integer userId);
	
	int truncateWithUserIds(List<Integer> userIds);
	
	ManagerModel findOneInstitution(String username);
	
	ManagerModel findOneByOrgAndName(Integer orgId,String username);
	/**
	 * 鏍规嵁鏈烘瀯id鍜屾墍鏈夎兘澶熺鐞嗗綋鍓嶆満鏋勭殑鏈烘瀯绠＄悊鍛樼殑id鐨勯泦鍚�
	 * @param map
	 * @return
	 */
	List<Integer> findIdsByOrgIdAndCreatedBys(Map<String, Object> map);

	ManagerModel getManagerByMap(Map<String, Object> map);

	List<ManagerModel> findManagerListsByMap(Map<String, Object> paramMap);

	Integer findManagerCountsByMap(Map<String, Object> paramMap);

	void updateManagerWithLock(ManagerModel managerModel);

	void deleteManager(ManagerModel manager);

	List<ManagerModel> getManagerListsByMap(Map<String, Object> paramMap);

	List<OrganizationModel> getManagerListsById(int id);

	/**鏇存柊鍒楄〃鏉冮檺
	 * @param auth
	 */
	void updateAuth(Map<String, Object> map);
    /**
     * @param entity
     * @return
     */
    int updatePerson(ManagerModel entity);

}



