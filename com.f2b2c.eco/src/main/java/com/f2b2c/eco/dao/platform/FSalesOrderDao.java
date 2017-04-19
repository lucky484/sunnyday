package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.bean.platform.FDifferentOrderBean;
import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;

public interface FSalesOrderDao extends CrudMapper<FSalesOrderModel, Serializable>{
	int queryCountByCondition(Map<String, Object> paramMap);

	/**
	 * 根据订单ID删除订单
	 * 
	 * @param orderId
	 *            订单ID
	 */
	void deleteByOrderId(String orderId);
	/**
	 * 
	 * @param orderId
	 */
	void truncateByOrderId(String orderId);

	/**
	 * 根据订单ID删除订单详情
	 * 
	 * @param orderId
	 */
	void deleteOrderDetailsByOrderId(String orderId);

	/**
	 * 添加订单详情
	 * 
	 * @param getfSalesOrderDetailsModel
	 */
	void addOrderDetails(List<FSalesOrderDetailsModel> getfSalesOrderDetailsModel);

	/**
	 * 根据ID查询订单对象
	 * 
	 * @param id
	 *            订单ID
	 * @return 订单对象
	 */
	FSalesOrderModel queryOrderById(String id);

	/**
	 * 
	 * @param deleteIds
	 */
	void deleteDetails(List<String> deleteIds);

	/**
	 * 
	 * @param detailsModels
	 */
	void updateDetailsModel(List<FSalesOrderDetailsModel> detailsModels);
    
	/**
	 * 查询订单记录信息
	 * 
	 * @param map
	 * @return
	 */
	List<FSalesOrderModel> findOrderRecord(Map<String, Object> map);
	
	/**
	 * 查询订单记录信息
	 * 
	 * @param map
	 * @return
	 */
	List<FSalesOrderModel> findOrderListWithStatus(Map<String, Object> map);
	
	/**
	 * 查询订单记录信息总记录
	 * 
	 * @return
	 */
	int getOrderCountWithStatus(Map<String, Object> map);
	
	/**
	 * 查询订单记录信息总记录
	 * 
	 * @return
	 */
	int orderRecordCount(Map<String, Object> map);

	void deleteOrderDetails(List<String> deleteIds); 
	
	/**
	 * 根据orderId，查询订单的基本信息
	 * 
	 * @param orderId
	 * @return
	 */
	FSalesOrderModel findOne(String orderId);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<BToCOrderModel> findBusinessPurchesOrderRecord(Map<String, Object> map);
	
	/**
	 * 根据orderId，查询订单的基本信息
	 * 
	 * @param orderId
	 * @return
	 */
	int findBusinessPurchesOrderCount(Map<String, Object> map);

	/**
	 * 更新订单状态
	 * 
	 * @param map
	 *            参数map
	 */
	void updateOrderStatus(Map<String, Object> map);

	/**
	 * 查询商户 某天的订单信息
	 * 
	 * @return
	 */
	FSalesOrderModel findOneOrder(Map<String, Object> map);
	
	/**
	 * 根据订单号查询订单状态
	 * 
	 * @param orderNo
	 * @return
	 */
	List<FSalesOrderModel> queryStatusByOrderNo(String mergeOrderNo);
	
	int updateStatusByOrderNo(String mergeOrderNo);
	
	/**
	 * 删除订单，假删除
	 * 
	 * @param orderId
	 */
	void deleteDetailsByOrderId(String orderId);
	
	/**
	 * 采购单详情
	 * 
	 * @param orderId
	 * @return
	 */
	BToCOrderModel findBusinessPurchesOrderDetail(String orderId);
	
	List<FSalesOrderModel> findExpiredOrder();
	
	int deleteBatchOrders(List<FSalesOrderModel> list);
	
	int updateTotalById(FSalesOrderModel fSalesOrder);
	
	void updateStatusByNo(FSalesOrderModel fSalesOrder);
	
	/**
	 * 根据订单id更新实际金额
	 * 
	 * @param fDifferentOrderBean
	 * @return
	 */
	int updateOrderPriceByOrderId(FDifferentOrderBean fDifferentOrderBean);
	
	/**
	 * 根据订单id去获取所有的区域id
	 * 
	 * @param orderId
	 * @return
	 */
	Integer getAreaIdByOrderId(String orderId);

	BToCOrderModel findOrderByOrderId(String orderId);

	String findOrderIdByMergeOrderNo(String out_trade_no);

	FSalesOrderModel getTotalOrders(Map<Object, Object> paramMap);
	
	/**
	 * 根据用户id获取已发货的状态
	 * @param userId:用户id
	 * @return 返回是否存在已发货商品
	 */
	FSalesOrderModel queryUnPayOrder(Integer userId);
	
	/**
	 * 删除订单  不做物理删除
	 * @param orderId
	 * @return
	 */
	int updateOrderDeleteTimeById(String orderId);
	
	/**
	 * 根据订单编号修改实际要付款的金额
	 * @param fSalesOrder
	 * @return
	 */
	int updateRealPayByOrderNo(FSalesOrderModel fSalesOrder);
}