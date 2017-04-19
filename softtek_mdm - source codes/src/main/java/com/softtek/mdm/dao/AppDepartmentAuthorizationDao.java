package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.AppDepartmentAuthorization;
import com.softtek.mdm.util.DataGridModel;

/**
 * 应用授权表
 * @author jane.hui
 *
 */
public interface AppDepartmentAuthorizationDao {
	
    /**
     * 根据主键删除应用部门授权表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入应用部门授权表 
     * @mbggenerated
     */
    int insert(AppDepartmentAuthorization record);

    /**
     * 插入可选的应用部门授权表数据
     * @mbggenerated
     */
    int insertSelective(AppDepartmentAuthorization record);

    /**
     * 根据主键查询应用部门授权表
     * @mbggenerated
     */
    AppDepartmentAuthorization selectByPrimaryKey(Integer id);

    /**
     * 更新可选的应用部门授权表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AppDepartmentAuthorization record);

    /**
     * 更新应用部门授权表
     * @mbggenerated
     */
    int updateByPrimaryKey(AppDepartmentAuthorization record);
    
    /**
     * 根据参数检索部门
     * @param params
     * @return
     */
    List<AppDepartmentAuthorization> loadDepartAuth(DataGridModel params);
    
    /**
     * 批量插入应用授权表s
     * @param list
     * @return
     */
    int insertBatchAppDepartment(List<AppDepartmentAuthorization> list);
    
    /**
     * 根据应用ID删除
     * @param id
     * @return
     */
    int deleteByAppId(Integer id);
    
    /**
     * 根据应用ID获取用户
     * @param appId
     * @return
     */
    List<Integer> selectUserByAppIds(Integer appId);
}