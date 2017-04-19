package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.IosPolicyUser;
import com.softtek.mdm.model.IospolicyDepartment;
import com.softtek.mdm.util.DataGridModel;

/**
 * Ios策略授权部门表
 * @author jane.hui
 *
 */
public interface IospolicyDepartmentDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(IospolicyDepartment record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(IospolicyDepartment record);

    /**
     * 根据主键返回IospolicyDepartment
     * @mbggenerated
     */
    IospolicyDepartment selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IospolicyDepartment record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(IospolicyDepartment record);
    
    /**
     * 根据策略id获取部门架构
     * @param id
     * @return
     */
    List<Integer> selectDepartmentById(Integer id);
    
    /**
     * 根据策略id相关参数获取策略
     * @param params
     * @return
     */
    List<IospolicyDepartment> selectPolicyByDepartId(DataGridModel params);
    
    /**
     * 根据策略删除授权相关的部门
     * @param params
     * @return
     */
    int deleteByDepartId(Integer id);
    
    /**
     * 批量插入ios设备策略部门关联表
     * @param list
     * @return
     */
    int insertBatchIosDeviceDepartment(List<IospolicyDepartment> list);
    
    /**
     * 根据策略id获取授权部门id
     * @param id
     * @return
     */
    List<IospolicyDepartment> selectIosPolicyDepartById(Integer id);
    
    /**
     * 根据参数获取授权用户
     * @param params
     * @return
     */
    List<IosPolicyUser> selectDepartUserList(DataGridModel params);
    
    /**
     * 根据部门id获取用户id
     * @param list
     * @return
     */
    List<Integer> selectDepartUserIds(DataGridModel params);
 
    /**
     * 根据策略id删除关联的部门id
     * @param id
     * @return
     */
    int updateByDepartId(Integer id);
}