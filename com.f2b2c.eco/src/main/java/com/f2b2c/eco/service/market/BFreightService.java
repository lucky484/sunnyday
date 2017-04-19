package com.f2b2c.eco.service.market;

import java.util.List;

import com.f2b2c.eco.model.bean.BFreightBean;
import com.f2b2c.eco.model.market.BFreightTemplate;

/**
 * 运费设置service类
 * @author jane.hui
 * 
 */
public interface BFreightService {

	/**
	 * 根据店铺id获取运费Bean
	 * @param shopId
	 * @return
	 */
    List<BFreightBean> queryFreight(Integer shopId);
    
    /**
     * 获取用户模板
     * @param bUserId:用户外键
     * @return 返回模板
     */
    List<BFreightTemplate> queryFreightTemplate(Integer bUserId);
    
    /**
     * 修改运费设置
     * @param freightStr:运费字符串
     * @param shopId:店铺id
     * @return 返回修改结果
     */
    int modify(String freightStr,Integer shopId);
 }
