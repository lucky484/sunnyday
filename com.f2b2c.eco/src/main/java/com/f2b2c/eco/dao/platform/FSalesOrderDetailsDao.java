package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;

public interface FSalesOrderDetailsDao extends CrudMapper<FSalesOrderDetailsModel, Serializable> {
   
	int insertBatchOrderDetail(List<FSalesOrderDetailsModel> list);
	
	int updateBatchGoodsQuantity(List<FSalesOrderDetailsModel> list);
	
	List<FSalesOrderDetailsModel> queryAllOrderDetailsByOrderId(String orderId);
	
	void updateBatchGoodsQuantityPlus(List<FSalesOrderDetailsModel> list);
	
	int queryOrderDetailByGoodsId(@Param(value="goodsId")Integer goodsId,@Param(value="orderId")String orderId);
	
	int updateGoodsQtyByGoodsId(FSalesOrderDetailsModel fSalesOrderDetails);
	
	int insertOrderDetail(FSalesOrderDetailsModel fSalesOrderDetails);
}