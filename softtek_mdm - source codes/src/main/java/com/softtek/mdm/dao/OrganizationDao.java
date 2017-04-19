package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.OrganizationModel;

public interface OrganizationDao extends CrudMapper<OrganizationModel, Integer>{

	OrganizationModel findOrganizationByMap(Map<String, Object> map);

	List<OrganizationModel> findOrganizationListsByMap(Map<String, Object> paramMap);

	Integer findOrganizationCountsByMap(Map<String, Object> paramMap);

	void lock(OrganizationModel organization);

	List<OrganizationModel> findOrgManagerListsById(int id);

	List<OrganizationModel> findManagerListById(int id);

	List<OrganizationModel> findUnSelectManagerListById(int id);

	List<OrganizationModel> findAllOrganization();
    
	Collection<OrganizationModel> findEnableOrganizationRecordsByIds(List<Integer> ids);
	
	void updateOrganizationWithLock(OrganizationModel organization);

	void deleteOrganzationById(int id);

	void deleteOrganization(OrganizationModel organization);

	Integer getUserLicenseByMap(Map<String, Object> paramMap);

	List<OrganizationModel> getOrganizationListsByMap(Map<String, Object> paramMap);

	OrganizationModel findOneById(Integer id);

	public List<OrganizationModel> queryAllOrg();

	List<OrganizationModel> findOrganizationListByMap(Map<String, Object> paramMap);
	
	public String queryOrgCode(Integer id);
	
	public List<String> queryAllOrgId();
	
	public List<OrganizationModel> queryOrgByOrgId(Integer id);
}
