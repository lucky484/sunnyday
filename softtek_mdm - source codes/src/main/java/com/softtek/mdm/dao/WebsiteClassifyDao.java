package com.softtek.mdm.dao;

import java.util.List;

import com.softtek.mdm.model.WebsiteClassifyModel;

/**
 * 网站分类dao
 * @author jane.hui
 *
 */
public interface WebsiteClassifyDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @mbggenerated
     */
    int insert(WebsiteClassifyModel record);

    /**
     * 插入可选字段的网站分类表
     * @mbggenerated
     */
    int insertSelective(WebsiteClassifyModel record);

    /**
     * 根据主键查询网站分类表
     * @mbggenerated
     */
    WebsiteClassifyModel selectByPrimaryKey(Integer id);

    /**
     * 更新
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WebsiteClassifyModel record);

    /**
     * 更新网站分类表
     * @mbggenerated
     */
    int updateByPrimaryKey(WebsiteClassifyModel record);
    
    /**
     * 获取网站分类列表
     * @return
     */
    List<WebsiteClassifyModel> selectClassifyList();
}