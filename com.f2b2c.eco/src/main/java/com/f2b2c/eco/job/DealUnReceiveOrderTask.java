package com.f2b2c.eco.job;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.service.market.BSalesOrderService;


/**
 * 15分钟未接单的任务
 * @author jing.liu
 *
 */
public class DealUnReceiveOrderTask extends TimerTask {
    
	
    private BSalesOrderService bSalesOrderService;
	
	public DealUnReceiveOrderTask(BSalesOrderService bSalesOrderService) {
		this.bSalesOrderService = bSalesOrderService;
	}

	@Override
	public void run() {
		// BSalesOrderService bSalesOrderService = (BSalesOrderService) SpringContextUtils.getBean("BSalesOrderServiceImpl");
	     List<BSalesOrderModel> list = bSalesOrderService.queryPayUnReceiveOrder(new Date(), 15);
	     if(list != null && list.size() > 0){
		      bSalesOrderService.deleteTimeoutUnReceiverOrder(list,6);
	     }
	}

}
