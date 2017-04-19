package com.softtek.mdm.dao;

import java.util.List;
import com.softtek.mdm.model.AndroidBlackList;
import com.softtek.mdm.model.AppList;
import com.softtek.mdm.model.NameList;
import com.softtek.mdm.util.DataGridModel;

public interface AndroidBlackListDao {
	
	/**
	 * 根据主键删除数据
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入Android黑白名单关联表数据
     */
    int insert(AndroidBlackList record);

    /**
     * 插入Android黑白名单关联表数据
     */
    int insertSelective(AndroidBlackList record);

    /**
     * 根据主键id获取Android黑白名单关联表数据
     */
    AndroidBlackList selectByPrimaryKey(Integer id);

    /**
     * 更新Android黑白名单关联表数据
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AndroidBlackList record);

    /**
     * 更新Android黑白名单关联表数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(AndroidBlackList record);
    
    /**
     * 根据策略id删除关联的应用名单
     * @param id
     * @return
     */
    int updateByAppNamelistId(Integer id);
    
    /**
     * 插入策略应用名单表
     * @return
     */
    int insertBatchBlackPolicyList(List<AndroidBlackList> list);
    
    /**
     * 根据策略id获取策略应用名称
     * @return
     */
    List<AppList> selectAppListByIdList(DataGridModel params);
    
    /**
     * 根据策略id获取关联的黑白名单
     * @param id
     * @return
     */
    List<NameList> selectNameListByPolicyId(Integer id);
}