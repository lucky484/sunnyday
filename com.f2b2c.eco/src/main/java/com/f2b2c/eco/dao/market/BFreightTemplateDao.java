package com.f2b2c.eco.dao.market;

import java.util.List;

import com.f2b2c.eco.model.market.BFreightTemplate;

/**
 * 运费模板dao
 * @author jane.hui
 *
 */
public interface BFreightTemplateDao {
    /**
     * 根据主键删除运费模板
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入运费模板
     * @mbggenerated
     */
    int insert(BFreightTemplate record);

    /**
     * 插入可选字段的运费模板
     * @mbggenerated
     */
    int insertSelective(BFreightTemplate record);

    /**
     * 根据主键查询运费模板
     * @mbggenerated
     */
    BFreightTemplate selectByPrimaryKey(Integer id);

    /**
     * 更新可选字段的运费模板
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BFreightTemplate record);

    /**
     * 更新运费模板
     * @mbggenerated
     */
    int updateByPrimaryKey(BFreightTemplate record);
    
    /**
     * 根据用户外键查询运费模板
     * @param userId:用户
     * @return 返回运费模板List
     */
    List<BFreightTemplate> selectFreightTemplateByUserId(Integer userId);
}