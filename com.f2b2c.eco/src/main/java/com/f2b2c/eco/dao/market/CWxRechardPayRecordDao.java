package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.CWxRechardPayRecordModel;

/**
 * C端微信支付充值操作记录表
 * @author jane.hui
 *
 */
public interface CWxRechardPayRecordDao {
    /**
     * 根据主键删除表记录
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(CWxRechardPayRecordModel record);

    /**
     * 插入可选字段的表
     * @mbggenerated
     */
    int insertSelective(CWxRechardPayRecordModel record);

    /**
     * 根据主键查询操作记录表
     * @mbggenerated
     */
    CWxRechardPayRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选字段的微信支付充值操作记录表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CWxRechardPayRecordModel record);

    /**
     * 更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(CWxRechardPayRecordModel record);
}