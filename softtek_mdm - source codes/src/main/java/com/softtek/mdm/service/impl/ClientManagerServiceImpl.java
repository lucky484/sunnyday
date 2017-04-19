package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.ClientManagerDao;
import com.softtek.mdm.dao.OrganizationDao;
import com.softtek.mdm.model.ClientManagerModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.ClientManagerService;

@Service
public class ClientManagerServiceImpl implements ClientManagerService {
    
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private ClientManagerDao clientManagerDao;
	
    @Autowired
	private MessageSource messageSourceService;
	
	@Override
	public List<OrganizationModel> queryAllOrg() {
	    
		return organizationDao.queryAllOrg();
	}

	@Override
	public int insertClientManager(ClientManagerModel clientManger) {
		
		return clientManagerDao.insertClientManager(clientManger);
	}
    
	@Override
	public String queryOrgCode(Integer id) {
		return organizationDao.queryOrgCode(id);
	}

	@Override
	public Page queryAll(Integer begin, Integer num,ManagerModel manager) {
		Page p = new Page();
		Map<String,Object> map = new HashMap<String,Object>();
		List<OrganizationModel> orgList = organizationDao.queryAllOrg();
		map.put("managerId", manager.getId());
		map.put("userType", manager.getUser_type());
		map.put("start", begin);
		map.put("pageSize", num);
		int total = clientManagerDao.queryAllClientConfigCount(map);
		List<ClientManagerModel> list = clientManagerDao.queryAllClientConfig(map);
		for(ClientManagerModel clientManager : list){
			String orgName = "";
			String orgIds = clientManager.getBelongOrg();
			String[] orgIdArr = orgIds.split(",");
			if(orgIds.equals("") || orgIds == null){
				String allOrg = messageSourceService.getMessage("tiles.institution.client.manager.all.org", null, LocaleContextHolder.getLocale());
				clientManager.setBelongOrg(allOrg);
			}else{
				for(OrganizationModel organization : orgList){
					for(int i=0;i<orgIdArr.length;i++){
						if(Integer.valueOf(orgIdArr[i]).equals(organization.getId())){
							orgName += organization.getName() + ",";
						}
					}
				}
				clientManager.setBelongOrg(orgName.substring(0,orgName.length()-1));
			}
		}
		p.setData(list); 
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	@Override
	public List<String> queryAllOrgId() {
		
		return organizationDao.queryAllOrgId();
	}

	@Override
	public ClientManagerModel queryClientInfoById(Integer id) {
		
		return clientManagerDao.queryClientInfoById(id);
	}

	@Override
	public List<ClientManagerModel> queryImgByOrgId() {
		
		return clientManagerDao.queryImgByOrgId();
	}

	@Override
	public List<OrganizationModel> queryOrgByOrgId(Integer id) {

		return organizationDao.queryOrgByOrgId(id);
	}

	@Override
	public int deleteClient(ClientManagerModel clientManger) {
		
		return clientManagerDao.deleteClient(clientManger);
	}

	@Override
	public int deleteAllOrgClient(Integer updateBy) {
		
		return clientManagerDao.deleteAllOrgClient(updateBy);
	}

	@Override
	public ClientManagerModel queryQrCode() {
		
		return clientManagerDao.queryQrCode();
	}

	/**
	 * 获取发布客户端中包含orgId，并且平台是platForm的版本最新的并且是未删除的那一条记录
	 * @param orgId
	 * @param platForm
	 * @return
	 */
	@Override
	public ClientManagerModel findLastOne(String orgId, String platForm) {
		try {
			if(StringUtils.isNotEmpty(orgId)&&StringUtils.isNotEmpty(platForm)){
				Map<String, Object> map=new HashMap<String,Object>();
				map.put("orgId", orgId);
				map.put("platForm",platForm);
				return clientManagerDao.findLastOne(map);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
   
}
