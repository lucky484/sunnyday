package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;

public interface ManagerService {
	
	ManagerModel findOne(Integer id);
	
	/**
	 * 鏍规嵁鐢ㄦ埛鍚嶆煡鎵剧敤鎴蜂俊鎭�
	 * @param name
	 * @return
	 */
	ManagerModel findOneByName(String username);
	
	int update(ManagerModel entity);
	
	/**
	 * 鐗╃悊鍒犻櫎鐢ㄦ埛
	 * @param id
	 * @return
	 */
	int truncateWithPk(Integer id);
	
	int save(ManagerModel entity);
	
	int truncateWithUserId(Integer userId);
	
	int truncateWithUserIds(List<Integer> userIds);
	
	ManagerModel findOneInstitution(String username);
	
	ManagerModel findOneByOrgAndName(Integer orgId,String username);

	ManagerModel getManagerByMap(Map<String, Object> map);

	Page findManagerListsByMap(Map<String, Object> paramMap);

	void saveManager(ManagerModel managerModel, String[] orgs);

	void updateManager(ManagerModel managerModel, String[] orgs);

	void updateManagerWithLock(ManagerModel managerModel);

	void deleteManager(ManagerModel manager);

	List<ManagerModel> getManagerListsByMap(Map<String, Object> paramMap);

	List<OrganizationModel> getManagerListsById(int id);
	
    int updatePerson(ManagerModel entity);


}
