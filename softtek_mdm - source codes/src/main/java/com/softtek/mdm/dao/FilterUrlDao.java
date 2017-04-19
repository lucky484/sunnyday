package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.FilterUrl;

/**
 * web内容过滤
 * @author jane.hui
 *
 */
public interface FilterUrlDao {
	
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(FilterUrl record);

    /**
     * 插入可选的字段表
     * @mbggenerated
     */
    int insertSelective(FilterUrl record);

    /**
     * 根据主键返回FilterUrl
     * @mbggenerated
     */
    FilterUrl selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FilterUrl record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(FilterUrl record);
    
    /**
     * 批量插入url
     * @param list
     * @return
     */
    int insertBatchFilterUrl(List<FilterUrl> list);
}