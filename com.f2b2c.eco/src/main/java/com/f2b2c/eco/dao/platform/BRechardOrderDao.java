package com.f2b2c.eco.dao.platform;

import java.util.List;

import com.f2b2c.eco.model.platform.BRechardOrderModel;

/**
 * 充值订单表
 * @author jane.hui
 *
 */
public interface BRechardOrderDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(BRechardOrderModel record);

    /**
     * 查询可选字段的充值订单表
     * @mbggenerated
     */
    int insertSelective(BRechardOrderModel record);

    /**
     * 根据主键查询充值订单表
     * @mbggenerated
     */
    BRechardOrderModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的查询充值订单表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BRechardOrderModel record);

    /**
     * 更新充值订单表
     * @mbggenerated
     */
    int updateByPrimaryKey(BRechardOrderModel record);
    
    /**
     * 根据商户订单号查询充值订单表信息
     * @param outTradeNo：商户订单号
     * @return 返回充值订单信息
     */
    BRechardOrderModel selectByOutTradeNo(String outTradeNo);
    
    /**
     * 根据充值订单号更新充值订单信息表
     * @param record：充值订单信息
     * @return 返回是否操作成功
     */
    int updateByOrderNo(BRechardOrderModel record);
    
    /**
     * 根据用户id获取充值订单记录信息
     * @param userId:用户外键
     * @return 返回充值记录信息
     */
    List<BRechardOrderModel> selectRechardRecordByUserId(Page page);
}