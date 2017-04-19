package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.f2b2c.eco.bean.platform.FDifferentOrderBean;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.model.platform.FUserModel;

/**
 * 平台订单服务类
 * 
 * @author brave.chen
 *
 */
public interface FSalesOrderService {

	/**
	 * 查询所有订单
	 * 
	 * @return
	 */
	List<FSalesOrderModel> queryAllOrders();
	
	/**
	 * 更新平台订单
	 * 
	 * @param fSalesOrderModel
	 */
	void updateFSalesOrderModel(FSalesOrderModel fSalesOrderModel);
	
	/**
	 * 根据代理用户ID查询其有权限的订单订单列表
	 * 
	 * @param fUserModel
	 *            代理用户对象
	 * @return
	 */
	List<FSalesOrderModel> queryOrdersByAgentUserId(FUserModel fUserModel);
	
	/**
	 * 保存平台订单
	 * 
	 * @param fSalesOrderModel
	 *            平台订单
	 */
	void saveOrderModels(FSalesOrderModel fSalesOrderModel);
	
	/**
	 * 
	 * @param pageable
	 *            分页对象
	 * @param userModel
	 *            用户对象
	 * @return 订单分页对象
	 */
	Page<FSalesOrderModel> findWithPaginationByUser(Map<String, Object> map);

	/**
	 * 根据订单ID删除订单
	 * 
	 * @param orderId
	 *            订单ID
	 */
	void deleteOrderByOrderId(FSalesOrderModel fSalesOrder);

	/**
	 * 
	 * @param id
	 *            订单序列号
	 * @return 订单对象
	 */
	FSalesOrderModel queryById(String id);
	
	int createOrder(FSalesOrderModel fSalesOrder);
	
	/**
	 * 用于出货单菜单列表的记录
	 * 
	 * @param map
	 * @return
	 */
	Page<FSalesOrderModel> findWithPagination(Map<String, Object> map);

	void deleteOrderDetails(List<String> deleteIds);

	void updateDetailsModel(List<FSalesOrderDetailsModel> detailsModels);
	
	/**
	 * 根据订单编号，获取订单的信息内容，包括具体的商品信息
	 * 
	 * @param orderId
	 * @return
	 */
	Map<String, Object> getOrderInfo(String orderId);

	/**
	 * 更改订单状态
	 * 
	 * @param map
	 *            map参数对象
	 */
	void updateOrderStatus(Map<String, Object> map);
	
	/**
	 * b端店家购买的采货记录
	 * 
	 * @param map
	 * @return
	 */
	List<BToCOrderModel> findBusinessPurchesOrder(Map<String, Object> map);
	
	/**
	 * 查询商户 某天的订单信息
	 * 
	 * @param bUserId
	 * @param date
	 * @return
	 */
	FSalesOrderModel findOneOrder(Integer bUserId,String date);
	
	/**
	 * 
	 * @param entity
	 * @param updateOrInsert
	 * @return
	 */
	int save(FSalesOrderModel entity,Integer updateOrInsert);
	
	void notifyUrl(String app_id,String out_trade_no,String body,String buyer_id,String seller_id,String subject,String total_amount,String trade_no,String tradeContent);
	
	/**
	 * 查询采购单的数量
	 * 
	 * @return
	 */
	int findBusinessPurchesOrderCount(Map<String,Object> map);
	
	/**
	 * 采购单详情
	 * 
	 * @param orderId
	 * @return
	 */
	BToCOrderModel findBusinessPurchesOrderDetail(String orderId);

	void wxNotifyUrl(Map<String, String> map);
	
	/**
	 * 审核补差价类型
	 * 
	 * @param id：补差价订单id
	 * @return 返回补差价订单信息
	 */
	FDifferentOrderBean showDifferentOrder(String id);
	
	/**
	 * 审核差价订单信息
	 * 
	 * @param id:差价id
	 * @param type:审核状态(1.审核通过
	 *            2.审核未通过)
	 * @return 返回审核状态
	 */
	Integer checkDiffOrder(String id,String type, String orderId,Integer realPay,Integer money);

	/**
	 * 通过订单id去获取该订单所属的区域id
	 * 
	 * @param orderId
	 * @return
	 */
	Integer getAreaIdByOrderId(String orderId);

	BToCOrderModel findOrderByOrderId(String orderId);

	FSalesOrderModel getTotalOrders(Map<Object, Object> paramMap);
	
	/**
	 * 查询未付款的订单
	 * @param userId
	 * @return
	 */
	FSalesOrderModel queryUnPayOrder(Integer userId);
	
	
	/**
	 * 删除订单  不做物理删除
	 * @param orderId
	 * @return
	 */
	int updateOrderDeleteTimeById(String orderId);
}
