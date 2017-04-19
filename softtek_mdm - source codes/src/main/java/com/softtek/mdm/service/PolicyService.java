package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;

public interface PolicyService {
     
	public int save(PolicyModel policy,String id,List<StructureModel> strList);
	
	public Page queryAll(Integer begin, Integer num,Integer orgId); 
	
	public int deletePolicy(Integer id,List<StructureModel> strList,Integer orgId);
	

	/**
	 * ????id????????user_policy
	 * @param id
	 * @return
	 */
	PolicyModel findOne(Integer id);

    public PolicyModel queryInfoById(Integer id);
    
    public int updatePolicyInfo(PolicyModel policy,String ids,String departmentIds,List<StructureModel> strList);
    
    /**
	 * ??????е????
	 * @param orgId
	 * @return
	 */
	public List<PolicyModel> findAllByOrgId(Integer orgId);
	
	public List<PolicyModel> findAllByMap(Map<String, Object> map);
	
	int update(PolicyModel entity);
	
	/**
	 * 查询默认策略
	 * @return
	 */
	public int queryDefaultPolicy(Integer orgId);
	
	public String queryNameById(Integer policyId);
	
	public List<PolicyModel> findAllPolicy(int start,int pageSize,int id,UserModel user);
	
	public int findAllPolicyCount(Integer id,UserModel user);
	
	List<PolicyModel> findLikeName(Integer orgId,String name);
	
	/**
	 * 校验策略名称是否存在
	 * @param name
	 * @return
	 */
	public int queryCountByName(String name);
	/**
	 * 查询所有数量
	 * @param orgid
	 * @return
	 */
	public int queryPolicyCount(Integer orgid);
	public PolicyModel queryAllInfoById(Integer id);

	/**
	 * 按条件查询分页对象
	 * @author brave.chen
	 * @param paramsMap 参数map
	 * @return 分页对象
	 */
	public Page queryByParams(Map<String, Object> paramsMap);
	
	public List<PolicyModel> queryUserIdbyDepartmentId(Map<String,Object> map);
	
	public PolicyModel queryPolicyByUserId(Integer userId);

}
