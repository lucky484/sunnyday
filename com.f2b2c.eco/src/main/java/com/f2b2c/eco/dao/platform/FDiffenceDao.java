package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.bean.platform.FDifferentOrderBean;
import com.f2b2c.eco.model.platform.FDiffenceModel;

public interface FDiffenceDao {
  
	 int insert(FDiffenceModel fdiffence);
	 
	 /**
	  * 根据差价主键获取补差价订单详情
	  * @param id：差价主键
	  * @return 返回补差价订单详情
	  */
	 FDifferentOrderBean selectOrderDiffenceById(String id);
	 
	 /**
	  * 更新补差价订单信息
	  * @param fDifferentOrderBean
	  * @return
	  */
	 int updateDiffOrderStatus(FDifferentOrderBean fDifferentOrderBean);
}