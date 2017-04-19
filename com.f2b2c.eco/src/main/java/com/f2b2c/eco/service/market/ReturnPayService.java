package com.f2b2c.eco.service.market;

import com.f2b2c.eco.model.market.ReturnPayModel;

public interface ReturnPayService {
    
	int insertReturnPay(ReturnPayModel returnPayModel,String orderId);
}
