package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.PolicyModel;
/**
 * UserPolicyDAO
 * @author color.wu
 *
 */
public interface PolicyDao extends CrudMapper<PolicyModel,Integer>{
    
	public List<PolicyModel> queryAll(Integer begin,Integer num,Integer orgId);
	
	public int queryAllCount(Integer orgId); 
	
	public int deletePolicy(Integer id);
	
	public PolicyModel queryInfoById(Integer id);
	
	public int updatePolicyInfo(PolicyModel policy);
	
	/**
	 * 鏌ヨ鎵?鏈夌殑绛栫暐
	 * @param orgId
	 * @return
	 */
	public List<PolicyModel> findAllByOrgId(Integer orgId);
	
	public void updateDefaultPolicy(Integer orgId);
	
	/**
	 * 查询出默认策略
	 * @return
	 */
	public int queryDefaultPolicy(Integer orgId);
	
	public String queryNameById(Integer policyId);
	
	public List<PolicyModel> findAllPolicy(Map<String,Object> map);
	
	public int findAllPolicyCount(Map<String,Object> map);
	
	/**
	 * 根据名称进行模糊查询
	 * @param name
	 * @return
	 */
	List<PolicyModel> findLikeName(Map<String,Object> map);
	
	/**
	 * 根据策略id查询出策略的详细信息
	 */
	public PolicyModel queryAllInfoById(Integer id);
	
	/**
	 * 校验策略名称是否存在
	 * @param name
	 * @return
	 */
	public int queryCountByName(String name);

	/**
	 * 查询满足条件的策略数量
	 * @author brave.chen
	 * @param paramsMap 条件map
	 * @return 数量
	 */
	public int queryCountByParams(Map<String, Object> paramsMap);

	/**
	 * 查询满足条件的策略列表
	 *
	 * @author brave.chen
	 * @param paramsMap 条件map
	 * @return 策略列表
	 */
	public List<PolicyModel> queryByParams(Map<String, Object> paramsMap);

	public List<PolicyModel> findAllByMap(Map<String, Object> map);
	
	public List<PolicyModel> queryUserIdbyDepartmentId(Map<String,Object> map);
	
    public PolicyModel queryPolicyByUserId(Integer userId);
}
