package com.softtek.mdm.dao;

import java.util.List;
import com.softtek.mdm.model.IosPolicyList;
import com.softtek.mdm.model.NetBehaviorBlackWhiteList;

/**
 * ios设备策略与名单关联表
 * @author jane.hui
 *
 */
public interface IosPolicyListDao {
	
    /**
     * 根据主键删除ios策略与名单关联表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入ios策略与名单关联表
     * @mbggenerated
     */
    int insert(IosPolicyList record);

    /**
     * 插入可选字段的ios策略与名单关联表
     * @mbggenerated
     */
    int insertSelective(IosPolicyList record);

    /**
     * 根据主键查询ios策略与名单关联表
     * @mbggenerated
     */
    IosPolicyList selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选字段的ios策略与名单关联表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IosPolicyList record);

    /**
     * 根据主键更新ios策略与名单关联表
     * @mbggenerated
     */
    int updateByPrimaryKey(IosPolicyList record);
    
    /**
     * 批量插入ios策略名单表 
     * @param list:ios设备策略外键与名单外键的关联表
     * @return 返回插入操作结果
     */
    int insertBatchPolicyList(List<IosPolicyList> list);
    
    /**
     * 根据策略id删除关联的名单id
     * @param id
     * @return
     */
    int updateByNamelistId(Integer id);
    
    /**
     * 根据策略id获取黑白名单
     * @param id
     * @return
     */
    List<NetBehaviorBlackWhiteList> selectNameListByPolicyId(Integer id);
}