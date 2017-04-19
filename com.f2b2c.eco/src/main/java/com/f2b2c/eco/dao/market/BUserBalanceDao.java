package com.f2b2c.eco.dao.market;

import java.util.List;

import com.f2b2c.eco.model.market.BUserBalanceModel;

public interface BUserBalanceDao {
    /**
     * 根据主键删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(BUserBalanceModel record);

    /**
     *  插入可选的字段
     * @mbggenerated
     */
    int insertSelective(BUserBalanceModel record);

    /**
     * 根据主键查询表
     * @mbggenerated
     */
    BUserBalanceModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的 表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BUserBalanceModel record);

    /**
     * 更新表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(BUserBalanceModel record);
    
    
    /**
     * 根据用户id获取账户信息
     * @param userId:用户id
     * @return 返回B端账户信息
     */
    BUserBalanceModel selectByBUserId(Integer userId);
    
    /**
     * 根据版本号和主键id更新用户帐户金额表
     * @mbggenerated
     */
    int updateByPrimaryKeyAndVersion(BUserBalanceModel record);
    
    /**
     * 批量更新用户佣金
     * @param list:B端用户佣金列表
     * @return 返回更新用户佣金操作结果
     */
    int updateBUserCommission(List<BUserBalanceModel> list);
}