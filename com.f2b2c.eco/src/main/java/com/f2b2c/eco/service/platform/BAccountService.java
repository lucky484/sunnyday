package com.f2b2c.eco.service.platform;

/**
 * 账户管理
 * @author jane.hui
 *
 */
public interface BAccountService {
    
    /**
     * 余额操作
     * @param userId:用户操作
     * @param totalFee:金额
     * @param outTradeNo:交易流水号
     * @return 返回操作余额结果
     */
    int saveBalance(Integer userId,Integer totalFee,String outTradeNo,String operateType,String operateContent,String tag);

    /**
     * 保存用户余额
     * @param version:版本号
     * @param totalFee:金额
     * @param userId:用户外键
     * @param id:用户主键
     * @return 返回操作结果
     */
    int saveUserBalance(Integer version,Integer totalFee,Integer userId,Integer id);
}