package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.ManagerDao;
import com.softtek.mdm.dao.OrgManagerRelationDao;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrgManagerRelationModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.ManagerService;
import com.softtek.mdm.status.AuthStatus;
import com.softtek.mdm.util.Constant;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerDao managerDao;
	@Autowired
	private OrgManagerRelationDao orgManagerRelationDao;
	
	@Override
	public ManagerModel findOne(Integer id) {
		
		return managerDao.findOne(id);
	}

	@Override
	public ManagerModel findOneByName(String username) {
		return managerDao.findOneByName(username);
	}

	@Override
	public int update(ManagerModel entity) {
		return managerDao.update(entity);
	}

	@Override
	public int truncateWithPk(Integer id) {
		return managerDao.truncateWithPk(id);
	}

	@Override
	public int save(ManagerModel entity) {
		return managerDao.save(entity);
	}

	@Override
	public int truncateWithUserId(Integer userId) {
		if(userId!=null){
			return managerDao.truncateWithUserId(userId);
		}
		return 0;
	}

	@Override
	public int truncateWithUserIds(List<Integer> userIds) {
		if(userIds!=null&&userIds.size()>0){
			return managerDao.truncateWithUserIds(userIds);
		}
		return 0;
	}

	@Override
	public ManagerModel findOneInstitution(String username) {
		return managerDao.findOneInstitution(username);
	}

	@Override
	public ManagerModel findOneByOrgAndName(Integer orgId, String username) {
		return managerDao.findOneByOrgAndName(orgId, username);
	}

	@Override
	public ManagerModel getManagerByMap(Map<String, Object> map) {
		
		return managerDao.getManagerByMap(map);
	}

	@Override
	public Page findManagerListsByMap(Map<String, Object> paramMap) {
		
		Page page = new Page();
		
		Integer count = managerDao.findManagerCountsByMap(paramMap);
		List<ManagerModel> list = managerDao.findManagerListsByMap(paramMap);
		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return page;
	}

	@Override
	public void saveManager(ManagerModel managerModel, String[] orgs) {
		
		//淇濆瓨org_manager鐨勫熀鏈俊鎭�
		ManagerModel manager = new ManagerModel();
		manager.setUsername(managerModel.getUsername());
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		manager.setPassword(md5PasswordEncoder.encodePassword(managerModel.getPassword(), null));
		manager.setName(managerModel.getName());
		manager.setPhone(managerModel.getPhone());
		manager.setEmail(managerModel.getEmail());
		manager.setMark(managerModel.getMark());
		manager.setCreateTime(new Date());
		manager.setCreateBy(managerModel.getCreateBy());
		manager.setLogin_count(0);
		manager.setStatus(Constant.OrganizationConstant.enabled);
		manager.setUser_type(Integer.parseInt(AuthStatus.SOFTTEK_MANAGER.toString()));
		managerDao.save(manager);
		//淇濆瓨org_manager鍜宱rganization涔嬮棿鐨勫叧绯�
		OrganizationModel organization = null;
		List<OrgManagerRelationModel> lists = new ArrayList<OrgManagerRelationModel>();
		OrgManagerRelationModel orgManagerRelationModel =null;
		if(orgs!=null && orgs.length>0){
			for(String orgId:orgs){
				organization = new OrganizationModel();
				orgManagerRelationModel = new OrgManagerRelationModel();
				organization.setId(Integer.parseInt(orgId));
				orgManagerRelationModel.setManager(manager);
				orgManagerRelationModel.setOrganization(organization);
				orgManagerRelationModel.setCreateBy(managerModel.getCreateBy());
				orgManagerRelationModel.setCreateTime(new Date());
				lists.add(orgManagerRelationModel);
			}
			Map<String, Object> map = new HashMap<String, Object>();  
		    map.put("orgManagerList", lists);  
			orgManagerRelationDao.saveOrgManagerMap(map);
		}
		
	}

	@Override
	public void updateManager(ManagerModel managerModel, String[] orgs) {
		
		//淇濆瓨org_manager鐨勫熀鏈俊鎭�
		ManagerModel manager = new ManagerModel();
		manager.setId(managerModel.getId());
		manager.setUsername(managerModel.getUsername());
		if(StringUtils.isNotBlank(managerModel.getPassword())){
			Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
			manager.setPassword(md5PasswordEncoder.encodePassword(managerModel.getPassword(), null));
		}
		manager.setName(managerModel.getName());
		manager.setPhone(managerModel.getPhone());
		manager.setEmail(managerModel.getEmail());
		manager.setMark(managerModel.getMark());
		manager.setUpdateBy(managerModel.getCreateBy());
		manager.setStatus(Constant.OrganizationConstant.enabled);
		manager.setUser_type(Integer.parseInt(AuthStatus.SOFTTEK_MANAGER.toString()));
		managerDao.update(manager);
		//淇濆瓨org_manager鍜宱rganization涔嬮棿鐨勫叧绯�
		//閫氳繃manager_id鍘绘煡鎵惧搴旂殑鏈烘瀯
		
		Map<String,Object> delMap = new HashMap<String,Object>();
		delMap.put("manager_id",manager.getId());
		delMap.put("create_by", managerModel.getCreateBy());
		orgManagerRelationDao.deleteOrgManagerMap(delMap);
		OrganizationModel organization = null;
		List<OrgManagerRelationModel> lists = new ArrayList<OrgManagerRelationModel>();
		OrgManagerRelationModel orgManagerRelationModel =null;
		if(orgs!=null && orgs.length>0){
			for(String id:orgs){
				organization = new OrganizationModel();
				orgManagerRelationModel = new OrgManagerRelationModel();
				organization.setId(Integer.parseInt(id));
				orgManagerRelationModel.setManager(manager);
				orgManagerRelationModel.setOrganization(organization);
				orgManagerRelationModel.setCreateBy(managerModel.getCreateBy());
				orgManagerRelationModel.setCreateTime(new Date());
				lists.add(orgManagerRelationModel);
			}
			//鍋氭柊澧炴彃鍏ョ殑鎿嶄綔
			Map<String, Object> map = new HashMap<String, Object>();  
		    map.put("orgManagerList", lists);  
			orgManagerRelationDao.saveOrgManagerMap(map);
		}
	}

	@Override
	public void updateManagerWithLock(ManagerModel managerModel) {
		
		managerDao.updateManagerWithLock(managerModel);	
	}

	@Override
	public void deleteManager(ManagerModel manager) {
		
		managerDao.deleteManager(manager);
	}

	@Override
	public List<ManagerModel> getManagerListsByMap(Map<String, Object> paramMap) {
		
		return managerDao.getManagerListsByMap(paramMap);
	}

	@Override
	public List<OrganizationModel> getManagerListsById(int id) {
		
		return managerDao.getManagerListsById(id);
	}

    /* (non-Javadoc)
     * @see com.softtek.mdm.service.ManagerService#updatePerson(com.softtek.mdm.model.ManagerModel)
     */
    @Override
    public int updatePerson(ManagerModel entity) {
        managerDao.updatePerson(entity);
        return  managerDao.update(entity);
    }



}
