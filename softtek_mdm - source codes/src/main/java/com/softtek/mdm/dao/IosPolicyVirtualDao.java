package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.IosPolicyUser;
import com.softtek.mdm.model.IosPolicyVirtual;
import com.softtek.mdm.util.DataGridModel;

/**
 * Ios策略授权虚拟组
 * @author jane.hui
 *
 */
public interface IosPolicyVirtualDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(IosPolicyVirtual record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(IosPolicyVirtual record);

    /**
     * 根据主键返回IosPolicyVirtual
     * @mbggenerated
     */
    IosPolicyVirtual selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IosPolicyVirtual record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(IosPolicyVirtual record);
    
    /**
     * 根据策略id获取虚拟组
     * @param intId
     * @return
     */
    List<Integer> selectVirtualById(Integer intId);
    
    /**
     * 根据参数获取ios策略虚拟组
     * @param id
     * @return
     */
    List<IosPolicyVirtual> selectIosPolicyIdByVirtualId(DataGridModel params);
    
    /**
     * 根据策略id删除授权策略虚拟组关联表
     * @param id
     * @return
     */
    int deleteByVirtualGroupId(Integer id);
    
    /**
     * 批量插入授权虚拟组关联表
     * @param list
     * @return
     */
    int insertBatchIosPolicyVirtual(List<IosPolicyVirtual> list);
    
    /**
     * 根据策略id获取授权虚拟组
     * @param id
     * @return
     */
    List<IosPolicyVirtual> selectIosVirtualGroupById(Integer id);
    
    /**
     * 根据参数获取授权虚拟组
     * @param params
     * @return
     */
    List<IosPolicyUser> selectVirtualUserList(DataGridModel params);
    
    /**
     * 根据虚拟组id获取所在的用户id
     * @param list
     * @return
     */
    List<Integer> selectVirtualUserIds(DataGridModel params);
    
    /**
     * 根据策略id删除关联的虚拟组id
     * @param id
     * @return
     */
    int updateByVirtualGroupId(Integer id);
}