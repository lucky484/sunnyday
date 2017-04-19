package com.f2b2c.eco.job;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.service.market.BSalesOrderService;

/**
 * 超过15分钟未付款的任务
 * @author jing.liu
 *
 */
public class OrderTask extends TimerTask {
    
	private BSalesOrderService bSalesOrderService;
	
	public OrderTask(BSalesOrderService bSalesOrderService) {
		this.bSalesOrderService = bSalesOrderService;
	}

	@Override
	public void run() {
		   List<BSalesOrderModel> list = bSalesOrderService.queryUnPayOrder(new Date(), 15);
		   if(list != null && list.size() > 0){
			   bSalesOrderService.deleteTimeoutOrder(list,6);
		   }
	}

}
