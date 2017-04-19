package com.softtek.mdm.dao;

import java.util.List;
import com.softtek.mdm.model.IosBlackList;

/**
 * ios设备策略外键与名单外键关联表
 * @author jane.hui
 *
 */
public interface IosBlackListDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(IosBlackList record);

    /**
     * 插入可选字段的表
     * @mbggenerated
     */
    int insertSelective(IosBlackList record);

    /**根据主键查询表
     * @mbggenerated
     */
    IosBlackList selectByPrimaryKey(Integer id);

    /**
     * 更新可选字段的表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IosBlackList record);

    /**
     * 更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(IosBlackList record);
    
    /**
     * 插入策略应用名单表
     * @return
     */
    int insertBatchBlackPolicyList(List<IosBlackList> list);
}