package com.softtek.mdm.dao;

import java.util.List;
import com.softtek.mdm.model.AppVirtualGroupAuthorization;
import com.softtek.mdm.util.DataGridModel;

/**
 * 应用虚拟组授权表
 * @author jane.hui
 *
 */
public interface AppVirtualGroupAuthorizationDao {
	
    /**
     * 根据主键删除应用虚拟组授权表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入应用虚拟组授权表
     * @mbggenerated
     */
    int insert(AppVirtualGroupAuthorization record);

    /**
     * 插入可选的应用虚拟组授权表
     * @mbggenerated
     */
    int insertSelective(AppVirtualGroupAuthorization record);

    /**
     * 根据主键查询应用虚拟组授权表
     * @mbggenerated
     */
    AppVirtualGroupAuthorization selectByPrimaryKey(Integer id);

    /**
     * 更新可选的应用虚拟组授权表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AppVirtualGroupAuthorization record);

    /**
     * 更新应用虚拟组授权表
     * @mbggenerated
     */
    int updateByPrimaryKey(AppVirtualGroupAuthorization record);
    
    /**
     * 根据参数获取虚拟组
     * @param params
     * @return
     */
    List<AppVirtualGroupAuthorization> loadVirtualAuth(DataGridModel params);
    
    /**
     * 加载应用未授权的虚拟集合
     * @param params
     * @return
     */
    List<AppVirtualGroupAuthorization> loadFirstVirtualList(DataGridModel params);
    
    /**
     * 加载应用授权的虚拟组
     * @param params
     * @return
     */
    List<AppVirtualGroupAuthorization> loadVirtualGroupList(DataGridModel params); 
    
    /**
     * 批量插入应用授权表
     * @param list
     * @return
     */
    int insertBatchAppVirtualGroup(List<AppVirtualGroupAuthorization> list);
    
    /**
     * 根据应用ID删除数据
     * @param id
     * @return
     */
    int deleteByAppId(Integer id);
}