package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.OrgManagerRelationDao;
import com.softtek.mdm.dao.OrganizationDao;
import com.softtek.mdm.dao.PolicyDao;
import com.softtek.mdm.dao.StructureDao;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrgManagerRelationModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.service.OrganizationService;
import com.softtek.mdm.util.Constant;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private OrgManagerRelationDao orgManagerRelationDao;
	@Autowired
	private StructureDao structureDao;
	@Autowired
	private PolicyDao policyDao;
	@Autowired
	@Resource(name="messageSourceService")
	private MessageSource messageSource;
	
	@Override
	public OrganizationModel findOne(Integer id) {
		return organizationDao.findOneById(id);
	}
	@Override
	public List<OrganizationModel> findAll() {
		return (List<OrganizationModel>) organizationDao.findAll();
	}
	
	@Override
	public OrganizationModel findOrganizationByMap(Map<String, Object> map) {
		
		return organizationDao.findOrganizationByMap(map);
	}
	@Override
	public Page findOrganizationListsByMap(Map<String, Object> paramMap) {
		
		Page page = new Page();
		Integer count = organizationDao.findOrganizationCountsByMap(paramMap);
		List<OrganizationModel> lists = organizationDao.findOrganizationListsByMap(paramMap);
		page.setData(lists);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return page;
		
	}
	@Override
	public List<OrganizationModel> findOrgManagerListsById(int id) {
		//选中的机构
		List<OrganizationModel> managerList = organizationDao.findManagerListById(id);
		return managerList;
	}
	@Override
	public List<OrganizationModel> findAllOrganization() {
		
		return organizationDao.findAllOrganization();
	}
	@Override
	public void updateLockOrganization(OrganizationModel organization) {
		
		organizationDao.updateOrganizationWithLock(organization);
		
	}
	@Override
	public void deleteOrgization(OrganizationModel organization) {
		
		organizationDao.deleteOrganization(organization);
		
	}
	@Override
	public Integer getUserLicenseByMap(Map<String, Object> paramMap) {
		
		return organizationDao.getUserLicenseByMap(paramMap);
	}
	
	@Override
	public void saveOrganization(OrganizationModel organizationModel, String[] managers) {
		
		//保存机构的信息
		OrganizationModel organization = new OrganizationModel();
		organization.setOrgType(organizationModel.getOrgType());
		organization.setName(organizationModel.getName());
		organization.setLicenseCount(organizationModel.getLicenseCount());
		organization.setMark(organizationModel.getMark());
		organization.setCreateTime(new Date());
		organization.setCreateBy(organizationModel.getCreateBy());
		organization.setStatus(Constant.OrganizationConstant.enabled);
		organizationDao.save(organization);
		//保存organization和org_manager的关系
		ManagerModel manager = null;
		List<OrgManagerRelationModel> lists = new ArrayList<OrgManagerRelationModel>();
		OrgManagerRelationModel orgManagerRelationModel =null;
		if(managers!=null && managers.length>0){
			for(String managerId:managers){
				manager = new ManagerModel();
				orgManagerRelationModel = new OrgManagerRelationModel();
				manager.setId(Integer.parseInt(managerId));
				orgManagerRelationModel.setOrganization(organization);
				orgManagerRelationModel.setManager(manager);
				orgManagerRelationModel.setCreateBy(organizationModel.getCreateBy());
				orgManagerRelationModel.setCreateTime(new Date());
				lists.add(orgManagerRelationModel);
			}
			Map<String, Object> map = new HashMap<String, Object>();  
		    map.put("orgManagerList", lists);  
			orgManagerRelationDao.saveOrgManagerMap(map);
		}
		//2016-06-06 color要求在创建机构的时候同时也新建一条默认策略
		PolicyModel policyModel = new PolicyModel();
		policyModel.setOrganization(organization);
		String policyName = messageSource.getMessage("tiles.institution.policy.default.policy", null, LocaleContextHolder.getLocale());
		policyModel.setName(policyName);
		policyModel.setIsDefault(1);
		policyModel.setVisit_on_worktime(0);
		policyModel.setVisit_time_start("08:30");
		policyModel.setVisit_time_end("17:30");
		policyModel.setAllow_wifi(0);
		policyModel.setOnly_comp_wifi(0);
		policyModel.setLogin_limit(0);
		policyModel.setAuto_login_limit(0);
		policyModel.setLogin_error_limit(3);
		policyModel.setLogin_error_limit_times(1);
		policyModel.setLogin_error_limit_lock(1);
		policyModel.setDevice_limit(1);
		policyModel.setDevice_limit_count(1);
		policyModel.setCreateBy(organizationModel.getCreateBy());
		policyModel.setCreateTime(new Date());
		policyDao.save(policyModel);
		//2016-06-06 color要求在插入机构的时候同时在org_structure表中也插入一条记录
		StructureModel structure = new StructureModel();
		structure.setOrganization(organization);
		structure.setName(organization.getName());
		structure.setMark(organization.getMark());
		structure.setCreateBy(organizationModel.getCreateBy());
		structure.setCreateTime(new Date());
		structure.setPolicy(policyModel);
		structureDao.save(structure);
	}
	@Override
	public List<OrganizationModel> getOrganizationListsByMap(Map<String, Object> paramMap) {
		
		return organizationDao.getOrganizationListsByMap(paramMap);
	}
	@Override
	public void updateOrganization(OrganizationModel organizationModel, String[] managers) {
		
		//保存机构的信息
		OrganizationModel organization = new OrganizationModel();
		organization.setId(organizationModel.getId());
		organization.setOrgType(organizationModel.getOrgType());
		organization.setName(organizationModel.getName());
		organization.setLicenseCount(organizationModel.getLicenseCount());
		organization.setMark(organizationModel.getMark());
		organization.setCreateTime(new Date());
		organization.setCreateBy(organizationModel.getCreateBy());
		organization.setStatus(Constant.OrganizationConstant.enabled);
		organizationDao.update(organization);
		//保存organization和org_manager的关系
		Map<String,Object> delMap = new HashMap<String,Object>();
		delMap.put("org_id",organization.getId());
		delMap.put("create_by", organizationModel.getCreateBy());
		orgManagerRelationDao.deleteManagerOrgMap(delMap);
		
		ManagerModel manager = null;
		List<OrgManagerRelationModel> lists = new ArrayList<OrgManagerRelationModel>();
		OrgManagerRelationModel orgManagerRelationModel =null;
		if(managers!=null && managers.length>0){
			for(String managerId:managers){
				manager = new ManagerModel();
				orgManagerRelationModel = new OrgManagerRelationModel();
				manager.setId(Integer.parseInt(managerId));
				orgManagerRelationModel.setOrganization(organization);
				orgManagerRelationModel.setManager(manager);
				orgManagerRelationModel.setCreateBy(organizationModel.getCreateBy());
				orgManagerRelationModel.setCreateTime(new Date());
				lists.add(orgManagerRelationModel);
			}
			Map<String, Object> map = new HashMap<String, Object>();  
		    map.put("orgManagerList", lists);  
			orgManagerRelationDao.saveOrgManagerMap(map);
		}
		//2016-06-06 color要求在插入机构的时候同时在org_structure表中也插入一条记录
		StructureModel structure = structureDao.findOneByOrgId(organization.getId());
		structure.setOrganization(organization);
		structure.setName(organization.getName());
		structure.setMark(organization.getMark());
		structure.setUpdateBy(organizationModel.getCreateBy());
		structure.setUpdateTime(new Date());
		structureDao.update(structure);
		
	}
	@Override
	public List<OrganizationModel> findOrganizationListByMap(Map<String, Object> paramMap) {
		
		return organizationDao.findOrganizationListByMap(paramMap);
	}
	
	@Override
	public List<OrganizationModel> findEnableOrganizationRecordsByManagerId(Integer id) {
		if(null!=id){
			List<Integer> orgIds=orgManagerRelationDao.findIdListsByManagerId(id);
			if(null!=orgIds&&orgIds.size()>0){
				List<OrganizationModel> orgList=(List<OrganizationModel>) organizationDao.findEnableOrganizationRecordsByIds(orgIds);
				return orgList;
			}
		}
		return null;
	}

}




















