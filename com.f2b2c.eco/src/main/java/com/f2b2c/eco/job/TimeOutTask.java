package com.f2b2c.eco.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.service.market.BSalesOrderService;

/**
 * 订单的定时任务
 * @author jing.liu
 *
 */
@Service("timeOutTask")
public class TimeOutTask {
       
	   @Autowired
       private BSalesOrderService bSalesOrderService;
	   
	   /**
	    * 处理查过一天未付款的非水果订单
	    */
	   public void deleteFeFruitTimeOutOrder(){
		   List<BSalesOrderModel> list = bSalesOrderService.queryFeFruitUnPayOrder(new Date(), 24*60);
		   if(list != null && list.size() > 0){
			   bSalesOrderService.deleteTimeoutOrder(list,6);
		   }
	   }
	   
	   /**
	    * 处理发货超过两小时的水果订单
	    */
	   public void deleteFruitPayOrder(){
		   List<BSalesOrderModel> list = bSalesOrderService.queryFruitPayOrder(new Date(), 2*60);
		   if(list != null && list.size() > 0){
			   bSalesOrderService.deleteTimeoutOrder(list,1);
		   }
	   }
	   
	   /**
	    * 处理发货超过7天的非水果订单
	    */
	   public void deleteFeFruitPayOrder(){
		   List<BSalesOrderModel> list = bSalesOrderService.queryFeFruitPayOrder(new Date(), 240*60);
		   if(list != null && list.size() > 0){
			   bSalesOrderService.deleteTimeoutOrder(list,1);
		   }
	   }
	   
	   /**
	    * 处理有退货流程审核后超过1小时的水果订单
	    */
	   public void deleteCheckFruitOrder(){
		   List<BSalesOrderModel> list = bSalesOrderService.queryCheckOrder(new Date(),60);
		   if(list != null && list.size() > 0){
			   bSalesOrderService.deleteTimeoutOrder(list,1);
		   }
	   }
	   
	   /**
	    * 处理有退货流程审核后超过2天的非水果订单
	    */
	   public void deleteCheckFeFruitOrder(){
		   List<BSalesOrderModel> list = bSalesOrderService.queryCheckFeFruitOrder(new Date(),48*60);
		   if(list != null && list.size() > 0){
			   bSalesOrderService.deleteTimeoutOrder(list,1);
		   }
	   }
	   
}
