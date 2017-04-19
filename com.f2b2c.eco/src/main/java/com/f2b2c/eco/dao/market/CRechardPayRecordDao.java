package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.CRechardPayRecordModel;

/**
 * C端充值操作记录表
 * @author jane.hui
 *
 */
public interface CRechardPayRecordDao {
    /**
     * 根据主键删除充值操作记录表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入充值操作记录表
     * @mbggenerated
     */
    int insert(CRechardPayRecordModel record);

    /**
     * 插入可选的充值操作记录表字段
     * @mbggenerated
     */
    int insertSelective(CRechardPayRecordModel record);

    /**
     * 根据主键查询充值操作记录表字段
     * @mbggenerated
     */
    CRechardPayRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的充值操作记录表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CRechardPayRecordModel record);

    /**
     *更新可选的充值操作记录表文本字段
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(CRechardPayRecordModel record);

    /**
     * 更新充值操作记录表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(CRechardPayRecordModel record);
}