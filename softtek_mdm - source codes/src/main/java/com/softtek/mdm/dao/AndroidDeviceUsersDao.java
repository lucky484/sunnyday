package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.AndroidDeviceUsers;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.util.DataGridModel;

public interface AndroidDeviceUsersDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table android_device_users
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table android_device_users
     *
     * @mbggenerated
     */
    int insert(AndroidDeviceUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table android_device_users
     *
     * @mbggenerated
     */
    int insertSelective(AndroidDeviceUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table android_device_users
     *
     * @mbggenerated
     */
    AndroidDeviceUsers selectByPrimaryKey(Integer id);
    
    /**
     * 根据用户id查找用户最新的设备策略
     * @param uid
     * @return
     */
    AndroidDeviceUsers findOneByUserIdLast(Integer uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table android_device_users
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AndroidDeviceUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table android_device_users
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AndroidDeviceUsers record);
    
    /**
     * 获取用户列表
     * @return
     */
    List<UserModel> getUserList(String orgId);
    
    /**
     * 批量插入Android设备策略授权用户关联表
     * @param list
     * @return
     */
    int insertBatchPolicyUserIds(List<AndroidDeviceUsers> list);
    
    /**
     * 根据策略id获取用户
     * @param id
     * @return
     */
    List<AndroidDeviceUsers> selectDeviceUserById(Integer id);
    
    /**
     * 根据策略id获取关联的用户
     * @param id
     * @return
     */
    int updateByUsersId(Integer id);
    
    /**
     * 根据策略id获取用户ids
     * @param id
     * @return
     */
    List<Integer> selectUserByIds(Integer id);
    
    /**
     * 根据用户id获取策略
     * @param params
     * @return
     */
    List<AndroidDeviceUsers> selectPolicyIdByUserId(DataGridModel params);
    
    
    AndroidDeviceUsers findOneByEntity(AndroidDeviceUsers entity);
    
    /**
     * 根据用户的编号和机构编号 查询出用户最近的设备策略id
     * @param userId
     * @param orgId
     * @return
     */
    Integer findOne(Integer userId,Integer orgId);
    
    /**
     * 根据策略id获取管来年的授权用户关联表
     * @param list
     * @return
     */
    List<AndroidDeviceUsers> selectUserIdsByPolicyList(List<Integer> list);
}