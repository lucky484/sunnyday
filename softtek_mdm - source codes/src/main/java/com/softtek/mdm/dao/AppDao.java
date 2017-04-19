package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.bean.AppBean;
import com.softtek.mdm.model.App;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.util.DataGridModel;

/**
 * 应用表
 * @author jane.hui
 *
 */
public interface AppDao {
	
    /**
     * 根据主键删除应用表
     */
    int deleteByPrimaryKey(Integer id);
    
    /**
     * 插入应用表
     */
    int insert(App record);

    /**
     * 插入可选的应用表数据
     */
    int insertSelective(App record);

    /**
     * 根据主键查询应用表
     * @mbggenerated
     */
    App selectByPrimaryKey(Integer id);

    /**
     * 更新可选的应用表字段
     */
    int updateByPrimaryKeySelective(App record);

    /**
     * 更新应用表
     */
    int updateByPrimaryKey(App record);
    
    /**
     * 根据参数获取应用数
     * @param params
     * @return
     */
    int getApplicationCountByParams(DataGridModel params);
    
    /**
     * 根据参数获取应用列表
     * @param params
     * @return
     */
    List<App> getApplicationListByParams(DataGridModel params);
    
    /**
     * 根据id删除应用
     * @param id
     * @return
     */
    int updateByPrimaryId(Integer id);
    
    /**
     * 根据id更新应用状态
     * @param id
     * @return
     */
    int updateStateByPrimaryId(DataGridModel params);
    
    /**
     * 根据id更新应用发布应用说明字段
     * @param app
     * @return
     */
    int updateAppDescriptionById(App app);
    
    /**
     * 根据机构id获取对应机构下的应用
     * @param params
     * @return
     */
    List<App> getApplicationByOrgId(DataGridModel params);
    
    /**
     * 检索设备信息
     * @param params
     * @return
     */
    List<DeviceBasicInfoModel> queryDevice(DataGridModel params);
    
    /**
     * 检索设备信息数
     * @param params
     * @return
     */
    int queryDeviceCount(DataGridModel params);
    
    /**
     * 根据授权部门应用列表
     * @return
     */
    List<AppBean> getAppListByDepartIds(DataGridModel params);
    
    /**
     * 根据应用Id获取应用是否存在
     * @param params
     * @return
     */
    Integer selectNameByAppId(DataGridModel params);
    
    /**
     * 根据应用Id获取应用
     * @param id
     * @return
     */
    App selectAppByAppId(String id);
    
    /**
     * 根据应用ID获取用户ids
     * @param appId
     * @return
     */
    List<Integer> selectUserIdsByAppId(DataGridModel params);
}