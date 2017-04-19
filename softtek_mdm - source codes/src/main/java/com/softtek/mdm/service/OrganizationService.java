package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;

public interface OrganizationService {
	
	OrganizationModel findOne(Integer id);
	
	List<OrganizationModel> findAll();

	void saveOrganization(OrganizationModel organization, String[] managers);

	OrganizationModel findOrganizationByMap(Map<String, Object> map);

	Page findOrganizationListsByMap(Map<String, Object> paramMap);

	void updateOrganization(OrganizationModel organization, String[] managers);

	List<OrganizationModel> findOrgManagerListsById(int id);

	List<OrganizationModel> findAllOrganization();

	void updateLockOrganization(OrganizationModel organization);

	void deleteOrgization(OrganizationModel organization);

	Integer getUserLicenseByMap(Map<String, Object> paramMap);

	List<OrganizationModel> getOrganizationListsByMap(Map<String, Object> paramMap);

	List<OrganizationModel> findOrganizationListByMap(Map<String, Object> paramMap);
	
	List<OrganizationModel> findEnableOrganizationRecordsByManagerId(Integer id);
}
