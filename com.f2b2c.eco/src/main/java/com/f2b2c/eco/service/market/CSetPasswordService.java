package com.f2b2c.eco.service.market;

import com.f2b2c.eco.model.common.ApiResultModel;

/**
 * 设置密码接口
 * @author jane.hui
 *
 */
public interface CSetPasswordService {

	/**
	 * 设置密码
	 * @param userId:用户外键
	 * @param password:密码
	 * @return 返回设置操作结果
	 */
	ApiResultModel<String> setPassword(Integer userId,String password);
    
    /**
     * 保存密码
     * @param userId:用户外键
     * @param password:密码
     * @return 返回保存操作结果
     */
	ApiResultModel<String> savePassword(Integer userId,String password,String mergeOrderPrice);
	
	/**
	 * 修改密码
	 * @param userId;用户外键
	 * @param oldPassword:旧密码
	 * @param password:新密码
	 * @return 返回修改操作结果
	 */
	ApiResultModel<String> modifyPassword(Integer userId,String oldPassword,String password);
}