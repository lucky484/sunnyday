package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.PayRecordModel;

/**
 * 支付记录dao
 * @author jane.hui
 *
 */
public interface PayRecordDao {
	
    /**
     * 根据主键删除支付记录表数据
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入支付记录表数据
     * @mbggenerated
     */
    int insert(PayRecordModel record);

    /**
     * 插入可选的支付记录表数据
     * @mbggenerated
     */
    int insertSelective(PayRecordModel record);

    /**
     * 查询支付记录表数据
     * @mbggenerated
     */
    PayRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的支付记录表数据
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PayRecordModel record);

    /**
     * 更新可选的支付记录表数据
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(PayRecordModel record);

    /**
     * 更新支付记录表字段数据
     * @mbggenerated
     */
    int updateByPrimaryKey(PayRecordModel record);
}