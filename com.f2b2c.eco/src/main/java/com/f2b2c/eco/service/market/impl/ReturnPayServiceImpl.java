package com.f2b2c.eco.service.market.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BSalesOrderDao;
import com.f2b2c.eco.dao.market.BSalesOrderDetailsDao;
import com.f2b2c.eco.dao.market.ReturnPayDao;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.ReturnPayModel;
import com.f2b2c.eco.service.market.ReturnPayService;

@Service
public class ReturnPayServiceImpl implements ReturnPayService{
     
	
	@Autowired
	private ReturnPayDao returnPayDao;
	
	@Autowired
	private BSalesOrderDetailsDao bSalesOrderDetailsDao;
	
	@Autowired
	private BSalesOrderDao bSalesOrderDao;
	
	
	@Override
	public int insertReturnPay(ReturnPayModel returnPayModel,String orderId) {
		returnPayModel.setCreatedTime(new Date());
		returnPayModel.setUpdatedTime(new Date());
		int result = returnPayDao.insertReturnPay(returnPayModel);
		if(result == 1){
			BSalesOrderDetailsModel bSalesOrderDetailsModel = new BSalesOrderDetailsModel();
			bSalesOrderDetailsModel.setId(returnPayModel.getOrderDetailId());
			if(returnPayModel.getReturnType() == 1){
				bSalesOrderDetailsModel.setGoodsStatus(1);
			}else{
				bSalesOrderDetailsModel.setGoodsStatus(2);
			}
			bSalesOrderDetailsModel.setUpdatedTime(new Date());
			bSalesOrderDetailsDao.updateGoodsStatus(bSalesOrderDetailsModel);
			bSalesOrderDao.updateOrderReturnStatus(1,orderId);
		}
		return result;
	}

}
