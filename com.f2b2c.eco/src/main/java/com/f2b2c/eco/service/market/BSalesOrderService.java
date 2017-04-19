package com.f2b2c.eco.service.market;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.CLogisticsCodeModel;

public interface BSalesOrderService {
    
	/**
	 * 查询所有订单，分页
	 * @param num
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public List<BToCOrderModel> queryAllOrder(Integer num,Integer pageSize,Integer userId,Integer status,Integer returnStatus);
	
	public int queryAllOrderCount(Integer userId,Integer status,Integer returnStatus);
	
	/**
	 * 删除订单
	 * @param id
	 * @return
	 */
	public int deleteOrderById(String id,Integer status);
	
	/**
	 * 修改订单状态
	 * @param bSalesOrder
	 * @return
	 */
	public int updateStatusById(BSalesOrderModel bSalesOrder);
	
	/**
	 * 根据id查询订单详情
	 * @param id
	 * @return
	 */
	public BSalesOrderModel queryOrderDetailById(String id);
	
	/**
	 * 查询订单详情
	 * @param orderId
	 * @param status
	 * @return
	 */
	public BToCOrderModel queryAllOrderDetail(String orderId);
	
	List<BSalesOrderModel> queryTradInfo(Integer userId,Integer page,Integer pageSize);
	
	int queryTradInfoCount(Integer userId);
	
	List<BToCOrderModel> queryAllSalesOrder(Integer num,Integer pageSize,Integer userId,Integer status);
	
	int queryAllSalesOrderCount(Integer userId,Integer status);
	
	int deleteSalesOrderById(String id);
	
	 /**
     * 查询超过半小时未付款的订单
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryUnPayOrder(Date date,Integer time);
    
    /**
     * 删除超过半小时未付款的订单
     * @param date
     * @param time
     * @return
     */
    void deleteTimeoutOrder(List<BSalesOrderModel> list,Integer status);
    
    /**
     * 查询当前店铺下所有的订单
     * @param map
     * @return
     */
	Page<BSalesOrderModel> querySaleOrderByBUserId(Pageable pageable,Map<String, Object> map);
	
	/**
	 * 获取物流信息
	 * @param pageable
	 * @param map
	 * @return
	 */
	Page<CLogisticsCodeModel> getLogisticsInfo(Pageable pageable,Map<String, Object> map);
	
	/**
	 * 根据订单号相关参数获取物流信息
	 * @param orderNo:订单号
	 * @param code:快递公司编码
	 * @param number:运单号
	 * @return 返回操作结果
	 */
	int saveLogisticsInfo(String orderNo,String code,String number);
	
	/**
	 * 查询所有需要审核的订单
	 * @param pageable
	 * @param map
	 * @return
	 */
	Page<BSalesOrderDetailsModel> queryReturnOrder(Pageable pageable,Map<String,Object> map);
	
	/**
	 * 店家同意退款
	 * @param orderId
	 * @param orderDetailId
	 * @param returnType
	 * @param userId
	 * @param returnAmount
	 * @return
	 */
	int agreeReturn(String orderId,String orderDetailId,Integer returnType,Integer userId,Integer returnAmount);
	
	/**
	 * 店家拒绝接单
	 * @param bSalesOrder
	 * @return
	 */
	int refuseOrder(BSalesOrderModel bSalesOrder);
	
	/**
	 * 用户退款的订单
	 * @param orderDetailId
	 * @param returnType
	 * @return
	 */
	int refuseReturn(String orderId,String orderDetailId,Integer returnType);
	
	/**
	 * 用户取消的订单
	 */
    int cancelOrder(Integer type,String orderId,Integer status);
    
    /**
     * 一天之内未付款的所有的非水果订单
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryFeFruitUnPayOrder(Date date,Integer time);
    
    /**
     * 2小时之外的已发货的水果订单
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryFruitPayOrder(Date date,Integer time);
    
    /**
     * 查询所有发货时间超过10天的非水果订单
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryFeFruitPayOrder(Date date,Integer time);
    
    /**
     * 水果订单的发货
     * @param bSalesOrder
     * @return
     */
    int updateStatusTo(BSalesOrderModel bSalesOrder);
    
    /**
     * 15分钟付款店家未接单的订单，自动取消
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryPayUnReceiveOrder(Date date,Integer time);
    
    /**
     * 查询审核后超过1小时的水果订单
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryCheckOrder(Date date,Integer time);
    
    /**
     * 查询审核后超过2天的非水果订单
     * @param date
     * @param time
     * @return
     */
    List<BSalesOrderModel> queryCheckFeFruitOrder(Date date,Integer time);
    
    /**
     * 15分钟未接单
     * @param list
     * @param status
     */
    void deleteTimeoutUnReceiverOrder(List<BSalesOrderModel> list,Integer status);
    
	/**
	 * 所有可以评价的商品列表
	 * @param map
	 * @return
	 */
	List<BGoodsToCModel> queryEvaluateOrderDetails(Map<String,Object> map);
	
	/**
	 * 可以评价订单的总数量
	 * @param map
	 * @return
	 */
	int queryEvaluateOrderDetailsCount(Map<String,Object> map);
	
	/**
	 * 查询商家未处理的订单
	 * @param userId
	 * @return
	 */
	int queryUnDealOrderByUserId(Integer userId);
	
	/**
	 * 查询还在退款中或者退货中的订单详情
	 * @param orderId
	 * @return
	 */
	int queryReturnCount(String orderId);
	
	/**
	 *  根据id查询订单详情
	 * @param orderId
	 * @return
	 */
	BSalesOrderModel queryOrderById(String orderId);
	
}
