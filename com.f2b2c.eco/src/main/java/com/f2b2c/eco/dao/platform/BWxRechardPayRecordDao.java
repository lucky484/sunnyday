package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.model.platform.BWxRechardPayRecordModel;

/**
 * 微信充值支付记录表
 * @author jane.hui
 *
 */
public interface BWxRechardPayRecordDao {
    /**
     * 根据主键删除记录表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @mbggenerated
     */
    int insert(BWxRechardPayRecordModel record);

    /**
     * 插入可选的微信充值支付记录表
     * @mbggenerated
     */
    int insertSelective(BWxRechardPayRecordModel record);

    /**
     * 根据主键查询微信充值记录表
     * @mbggenerated
     */
    BWxRechardPayRecordModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的微信充值记录表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BWxRechardPayRecordModel record);

    /**
     * 更新微信充值记录表
     * @mbggenerated
     */
    int updateByPrimaryKey(BWxRechardPayRecordModel record);
}