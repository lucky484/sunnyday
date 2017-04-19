package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.ClientManagerModel;

public interface ClientManagerDao {
    
	public int insertClientManager(ClientManagerModel clientManger);
		
	public int queryAllClientConfigCount(Map<String,Object> map);
	
	public List<ClientManagerModel> queryAllClientConfig(Map<String,Object> map);
	
	public ClientManagerModel queryClientInfoById(Integer id);
	
	public List<ClientManagerModel> queryImgByOrgId();
	
	public int deleteClient(ClientManagerModel clientManger);
	
	public int deleteAllOrgClient(Integer updateBy);
	
	public ClientManagerModel queryQrCode();
	
	/**
	 * 获取发布客户端中包含orgId，并且平台是platForm的版本最新的并且是未删除的那一条记录
	 * @param orgId
	 * @param platForm
	 * @return
	 */
	public ClientManagerModel findLastOne(Map<String, Object> map);
}
