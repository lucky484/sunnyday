package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.model.platform.BRechardPayRecordModel;

/**
 * 支付宝支付充值记录表
 * @author jane.hui
 *
 */
public interface BRechardPayRecordDao {
    /**
     * 根据直接删除支付宝支付充值记录表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(BRechardPayRecordModel record);

    /**
     * 插入可选字段的支付宝支付记录表
     * @mbggenerated
     */
    int insertSelective(BRechardPayRecordModel record);

    /**
     * 根据主键查询支付宝充值记录表
     * @mbggenerated
     */
    BRechardPayRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选字段的支付宝充值记录表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BRechardPayRecordModel record);

    /**
     * 更新可选的支付宝充值记录表
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(BRechardPayRecordModel record);

    /**
     * 更新支付宝充值记录表
     * @mbggenerated
     */
    int updateByPrimaryKey(BRechardPayRecordModel record);
}