package com.f2b2c.eco.service.market;

import java.util.List;

import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.CUserModel;

/**
 * 账户管理
 * @author jane.hui
 *
 */
public interface AccountService {

	/**
	 * 钱包支付
	 * @param userId:用户id
	 * @return 返回是扣款成功
	 */
	ApiResultModel<String> walletPay(Integer userId,Integer totalFee,String outTradeNo);
	
	/**
	 * 余额操作
	 * @param userId:用户操作
	 * @param totalFee:金额
	 * @param outTradeNo:交易流水号
	 * @return 返回操作余额结果
	 */
	int saveBalance(Integer userId,Integer totalFee,String outTradeNo,String operateType,String operateContent,String tag);
	
	/**
	 * 根据用户id获取账户余额
	 * @param userId:用户外键
	 * @return 返回账户余额
	 */
    Integer getAccountBalance(Integer userId);
    
    /**
     * 钱包支付
     * @param userId
     * @param totalFee
     * @param outTradeNo
     * @return
     */
    int walletPayTo(Integer userId,Integer totalFee,String outTradeNo);
    
    /**
     * 计算佣金
     */
    int settleCommission(List<BSalesOrderDetailsModel> list,Integer userId,String orderNo);
    
    /**
     * 保存用户余额
     * @param version:版本号
     * @param totalFee:金额
     * @param userId:用户外键
     * @param id:用户主键
     * @return 返回操作结果
     */
    int saveUserBalance(Integer version,Integer totalFee,Integer userId,Integer id);
    
    /**
     * 保存用户分佣相关记录
     * @param total:佣金
     * @param userId:用户id
     * @return 返回分佣操作结果
     */
    int saveUserCommission(Integer total,CUserModel user);
    
    /**
     * 保存商家分佣相关记录
     * @param total:佣金
     * @param userId:用户id
     * @return 返回分佣操作结果
     */
    int saveMerchantCommission(Integer total,CUserModel user);
    
    /**
     * 根据用户id判断是否设置支付密码
     * @param userId:用户id
     * @return 返回支付密码是否存在(1.存在  0.不存在)
     */
    Integer isPayPasswordExists(Integer userId);
}