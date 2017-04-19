
package com.softtek.mdm.dao;

import java.util.List;
import com.softtek.mdm.model.IosPolicyUser;
import com.softtek.mdm.util.DataGridModel;

/**
 * Ios策略授权用户
 * @author jane.hui
 *
 */
public interface IosPolicyUserDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(IosPolicyUser record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(IosPolicyUser record);

    /**
     * 根据主键返回IosPolicyUser
     * @mbggenerated
     */
    IosPolicyUser selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IosPolicyUser record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(IosPolicyUser record);
    
    /**
     * 根据策略id获取用户id列表
     * @param id
     * @return
     */
    List<Integer> selectUserIdByPolicyId(Integer id);
    
    /**
     * 获取当前用户以前的策略id
     * @param params
     * @return
     */
    List<IosPolicyUser> selectPolicyIdByUserId(DataGridModel params);
    
    /**
     * 根据策略id删除策略用户关联表
     * @param id
     * @return
     */
    int deleteByPolicyId(Integer id);
    
    /**
     * 批量插入用户策略关联表
     * @param list
     * @return
     */
    int insertBatchIosPolicyUser(List<IosPolicyUser> list);
    
    /**
     * 根据策略id获取授权的用户
     * @param id
     * @return
     */
    List<IosPolicyUser> selectDeviceUserById(Integer id);
    
    /**
     * 根据策略id获取关联的用户
     * @param id
     * @return
     */
    int updateByUsersId(Integer id);
    
    /**
     * 根据用户的编号和机构编号 查询出用户最近的设备策略id
     * @param userId
     * @param orgId
     * @return
     */
    Integer findOne(Integer userId,Integer orgId);
    
    /**
     * 根据参数获取Ios策略和用户关联表
     * @param entity
     * @return
     */
    IosPolicyUser findOneByEntity(IosPolicyUser entity);
}