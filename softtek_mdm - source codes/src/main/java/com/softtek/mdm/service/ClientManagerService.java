package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.ClientManagerModel;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.Page;

public interface ClientManagerService {
   
	public List<OrganizationModel> queryAllOrg();
	
	public int insertClientManager(ClientManagerModel clientManger); 
	
	public String queryOrgCode(Integer id);
	
	public Page queryAll(Integer begin, Integer num,ManagerModel manager);
	
	public List<String> queryAllOrgId();
	
	public ClientManagerModel queryClientInfoById(Integer id);
	
	public List<ClientManagerModel> queryImgByOrgId();
	
	public List<OrganizationModel> queryOrgByOrgId(Integer id);
	
	public int deleteClient(ClientManagerModel clientManger);
	
	public int deleteAllOrgClient(Integer updateBy);
	
	public ClientManagerModel queryQrCode();
	
	/**
	 * 获取发布客户端中包含orgId，并且平台是platForm的版本最新的并且是未删除的那一条记录
	 * @param orgId
	 * @param platForm
	 * @return
	 */
	public ClientManagerModel findLastOne(String orgId,String platForm);
}
