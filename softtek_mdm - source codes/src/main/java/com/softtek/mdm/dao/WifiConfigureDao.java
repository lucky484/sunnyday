package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.WifiConfigure;

/**
 * WIFI配置表
 * @author jane.hui
 *
 */
public interface WifiConfigureDao {
	
    /**
     * 根据主键删除WIFI配置表.
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * WIFI配置插入
     * 
     */
    int insert(WifiConfigure record);

    /**
     * WIFI配置表可选字段插入
     * 
     */
    int insertSelective(WifiConfigure record);

    /**
     * 根据主键id获取WIFI配置对象
     * 
     */
    WifiConfigure selectByPrimaryKey(Integer id);

    /**
     * 更新可选的WIFI配置字段
     * 
     */
    int updateByPrimaryKeySelective(WifiConfigure record);

    /**
     * 更新WIFI配置字段
     * 
     */
    int updateByPrimaryKey(WifiConfigure record);
    
    /**
     * 批量插入WIFI配置表数据
     * @return
     */
    int insertBatchWifiConfigure(List<WifiConfigure> list);
    
    /**
     * 根据策略Id删除WIFI配置表数据
     * @param policyId
     * @return
     */
    int updateWifiConfigureByPolicyId(Integer policyId);
    
    /**
     * 根据策略ID获取WIFI配置表
     * @param policyId
     * @return
     */
    List<WifiConfigure> selectByPolicyKey(Integer policyId);
}