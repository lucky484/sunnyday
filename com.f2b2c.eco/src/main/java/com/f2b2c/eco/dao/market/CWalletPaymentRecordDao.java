package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.CWalletPaymentRecordModel;

/**
 * 钱包支付操作记录表
 * @author jane.hui
 *
 */
public interface CWalletPaymentRecordDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(CWalletPaymentRecordModel record);

    /**
     * 插入可选的字段
     * @mbggenerated
     */
    int insertSelective(CWalletPaymentRecordModel record);

    /**
     * 根据主键查询表
     * @mbggenerated
     */
    CWalletPaymentRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CWalletPaymentRecordModel record);

    /**
     * 根据主键更新表
     * @mbggenerated
     */
    int updateByPrimaryKey(CWalletPaymentRecordModel record);
}