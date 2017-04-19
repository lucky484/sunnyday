package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.Page;

public interface BSalesOrderDetailsDao extends CrudMapper<BSalesOrderDetailsModel, Serializable> {
    
	public int updateOrderDetailsByOrderId(String orderId);
	
	int insertBatchOrderDetail(List<BSalesOrderDetailsModel> list);
	
	public List<BSalesOrderDetailsModel> queryGoodsByOrderId(String orderId);
	
	int updateSalesOrderDetailsByOrderId(String orderId);
	
	/**
	 * 修改商品的退货状态
	 * 
	 * @param bSalesOrderDetailsModel
	 */
	int updateGoodsStatus(BSalesOrderDetailsModel bSalesOrderDetailsModel);
	
	/**
	 * 查询所有的退款退货的商品
	 * 
	 * @param map
	 * @return
	 */
	List<BSalesOrderDetailsModel> queryReturnOrder(Map<String,Object> map);
	
	int queryReturnOrderCount(Map<String,Object> map);
	
	/**
	 * 根据订单号查询订单详情List
	 * 
	 * @param page:
	 * @return 返回订单详情list
	 */
	List<BSalesOrderDetailsModel> selectOrderDetailListByMergeNo(Page page);

	int updateComment(String orderDetailsId);
	
}