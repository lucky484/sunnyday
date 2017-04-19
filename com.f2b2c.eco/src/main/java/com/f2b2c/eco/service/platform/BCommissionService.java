package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.common.ApiResultModel;

/**
 * 佣金Service
 * @author jane.hui
 *
 */
public interface BCommissionService {

    /**
     * 转出功能
     * @param userId:佣金功能
     * @param money:转出金额
     * @return 返回转账结果
     */
    ApiResultModel<String> transfer(Integer userId,Integer money); 
}
