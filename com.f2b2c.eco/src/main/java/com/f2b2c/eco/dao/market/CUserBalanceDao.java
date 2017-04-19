package com.f2b2c.eco.dao.market;

import java.util.List;

import com.f2b2c.eco.model.market.CUserBalanceModel;

/**
 * C端用户帐户余额
 * @author jane.hui
 *
 */
public interface CUserBalanceDao {
    
    /**
     * 删除表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入表
     * @mbggenerated
     */
    int insert(CUserBalanceModel record);

    /**
     * 插入可选的字段
     * @mbggenerated
     */
    int insertSelective(CUserBalanceModel record);

    /**
     * 根据主键查询表
     * @mbggenerated
     */
    CUserBalanceModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的表字段
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CUserBalanceModel record);

    /**
     * 根据版本号和主键id更新用户帐户金额表
     * @mbggenerated
     */
    int updateByPrimaryKeyAndVersion(CUserBalanceModel record);
    
    /**
     * 更新表字段
     * @mbggenerated
     */
    int updateByPrimaryKey(CUserBalanceModel record);
    
    /**
     * 根据用户id获取账户信息
     * @param userId:用户id
     * @return 返回C端账户信息
     */
    CUserBalanceModel selectByCUserId(Integer userId);
    
    /**
     * 将退款金额返回到余额中去
     * @param cuser
     * @return
     */
    int updateAccountBalanceByUserId(CUserBalanceModel cuser);
    
    /**
     * 批量更新用户获得的佣金
     * @param list:C端用户余额表
     * @return 返回更新用户佣金操作结果
     */
    int updateCUserCommission(List<CUserBalanceModel> list);
}