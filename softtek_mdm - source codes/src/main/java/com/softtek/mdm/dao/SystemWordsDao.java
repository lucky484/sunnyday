package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.bean.WordsBean;
import com.softtek.mdm.model.SystemWordsModel;
import com.softtek.mdm.util.DataGridModel;

/**
 * 系统词库dao
 * @author jane.huis
 *
 */
public interface SystemWordsDao {
    /**
     * 根据主键删除系统词库表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @mbggenerated
     */
    int insert(SystemWordsModel record);

    /**
     * 插入
     * @mbggenerated
     */
    int insertSelective(SystemWordsModel record);

    /**
     * 根据主键查询表
     * @mbggenerated
     */
    SystemWordsModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选字段的表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SystemWordsModel record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(SystemWordsModel record);
    
    /**
     * 获取系统词库的大小
     */
    int getSystemWordsSize(DataGridModel params);
    
    /**
     * 获取系统词库列表数据
     */
    List<SystemWordsModel> getSystemWordsList(DataGridModel params);
    
    /**
     * 根据主键删除系统词库
     * @param id:主键id
     * @return 返回操作结果
     */
    int deleteSystemWordsById(Integer id);
    
    /**
     * 通过机构id获取对应的系统词库
     * @param orgId:机构id
     * @return 返回系统词库
     */
    List<WordsBean> getWordsList(Integer orgId);
    
    /**
     * 根据策略名称获取策略
     * @param name
     * @return
     */
    Integer selectWordsyByName(DataGridModel params);
}