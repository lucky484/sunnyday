package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.WxPayRecordModel;

/**
 * 微信支付记录
 * @author jane.hui
 *
 */
public interface WxPayRecordDao {
    /**
     * 根据主键删除微信支付记录
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入微信支付记录
     * @mbggenerated
     */
    int insert(WxPayRecordModel record);

    /**
     * 插入可选微信支付记录
     * @mbggenerated
     */
    int insertSelective(WxPayRecordModel record);

    /**
     * 根据主键查询微信支付记录
     * @mbggenerated
     */
    WxPayRecordModel selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选微信支付记录
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WxPayRecordModel record);

    /**
     * 根据主键更新微信支付记录
     * @mbggenerated
     */
    int updateByPrimaryKey(WxPayRecordModel record);
}