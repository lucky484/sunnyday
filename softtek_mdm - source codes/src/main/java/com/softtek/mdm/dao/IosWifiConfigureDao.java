package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.IosWifiConfigure;

/**
 * IosWIFI配置
 * @author jane.hui
 *
 */
public interface IosWifiConfigureDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(IosWifiConfigure record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(IosWifiConfigure record);

    /**
     * 根据主键返回IosWifiConfigure
     * @mbggenerated
     */
    IosWifiConfigure selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IosWifiConfigure record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(IosWifiConfigure record);
    
    /**
     * 批量插入wifi配置
     * @param list
     * @return
     */
    int insertBatchIosWifiConfigure(List<IosWifiConfigure> list);
    
    /**
     * 根据策略id获取WIFI配置
     * @param policyId
     * @return
     */
    List<IosWifiConfigure> selectIosWifiByPolicyId(Integer policyId);
    
    /**
     * 根据策略id删除WIFI配置
     * @param policyId
     * @return
     */
    int delIosWifiConfigureByPolicyId(Integer policyId);
}