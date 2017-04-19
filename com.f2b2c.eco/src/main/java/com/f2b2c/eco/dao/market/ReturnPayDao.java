package com.f2b2c.eco.dao.market;

import com.f2b2c.eco.model.market.ReturnPayModel;

public interface ReturnPayDao {
   
	   int insertReturnPay(ReturnPayModel returnPayModel);
	   
	   /**
	    * 根据主键查询退款金额model
	    * @param id
	    * @return
	    */
	   Integer selectByDetailId(String id);
}
