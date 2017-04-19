package com.f2b2c.eco.dao.market;

import java.util.List;

import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.market.CRechardOrderModel;

/**
 * 充值订单表
 * @author jane.hui
 *
 */
public interface CRechardOrderDao {
    
    /**
     * 根据主键id删除充值订单表记录
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入充值订单表字段
     * @mbggenerated
     */
    int insert(CRechardOrderModel record);

    /**
     * 插入可选字段的充值订单表字段
     * @mbggenerated
     */
    int insertSelective(CRechardOrderModel record);

    /**
     * 根据主键查询充值订单表记录
     * @mbggenerated
     */
    CRechardOrderModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的充值订单表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CRechardOrderModel record);

    /**
     * 更新充值订单表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(CRechardOrderModel record);
    
    /**
     * 根据商户订单号查询充值订单表信息
     * @param outTradeNo：商户订单号
     * @return 返回充值订单信息
     */
    CRechardOrderModel selectByOutTradeNo(String outTradeNo);
    
    /**
     * 根据充值订单号更新充值订单信息表
     * @param record：充值订单信息
     * @return 返回是否操作成功
     */
    int updateByOrderNo(CRechardOrderModel record);
    
    /**
     * 根据用户id获取充值订单记录信息
     * @param userId:用户外键
     * @return 返回充值记录信息
     */
    List<CRechardOrderModel> selectRechardRecordByUserId(Page page);
}