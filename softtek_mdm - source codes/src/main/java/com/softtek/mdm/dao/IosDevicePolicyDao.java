package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.softtek.mdm.model.IosDevicePolicy;
import com.softtek.mdm.util.DataGridModel;

/**
 * Ios设备策略表
 * @author jane.hui
 *
 */
public interface IosDevicePolicyDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(IosDevicePolicy record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(IosDevicePolicy record);

    /**根据主键返回IosDevicePolicy
     * 
     * @mbggenerated
     */
    IosDevicePolicy selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IosDevicePolicy record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(IosDevicePolicy record);
    
    /**
     * 获取该机构下的所有ios策略
     * @param maps
     * @return
     */
	List<IosDevicePolicy> findAllByMap(Map<String, Object> maps);
	
    /**
     * 查询该机构下所有的策略
     * @param org_id
     * @return
     */
    Collection<IosDevicePolicy> findAll(Integer orgId);
    
    /**
     * 根据策略名称获取策略
     * @param name
     * @return
     */
    Integer selectDevicePolicyByName(DataGridModel params);
    
    /**
     * 根据策略id获取用户id
     * @param policyId:策略id
     * @return 返回用户列表
     */
    List<Integer> selectUserListByPoicyId(Integer policyId);
}