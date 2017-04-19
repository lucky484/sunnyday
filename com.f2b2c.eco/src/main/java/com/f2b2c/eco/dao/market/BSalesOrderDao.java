
package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.Page;

/**
 * 订单
 * 
 * @author jing.liu
 *
 */
public interface BSalesOrderDao extends CrudMapper<BSalesOrderModel, Serializable> {
    
	/**
	 * 查询所有订单，根据不同的状态
	 * 
	 * @param num
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public List<BToCOrderModel> queryAllOrder(@Param(value="num")Integer num,@Param(value="pageSize")Integer pageSize,@Param(value="userId")Integer userId,@Param(value="status")Integer status,@Param(value="returnStatus")Integer returnStatus);
	
	public int queryAllOrderCount(@Param(value="userId")Integer userId,@Param(value="status")Integer status,@Param(value="returnStatus")Integer returnStatus);
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	public int deleteOrderById(String id);
	
	/**
	 * 修改订单状态
	 * 
	 * @param bSalesOrder
	 * @return
	 */
	public int updateStatusById(BSalesOrderModel bSalesOrder);
	
	/**
	 * 根据id查询订单详情
	 * 
	 * @param id
	 * @return
	 */
	public BSalesOrderModel queryOrderDetailById(String id);
	
	/**
	 * 批量插入订单
	 * 
	 * @param list
	 * @return
	 */
	int insertBatchOrder(List<BSalesOrderModel> list);
	
	/**
	 * 查询订单详情
	 * 
	 * @param orderId
	 * @param status
	 * @return
	 */
	public BToCOrderModel queryAllOrderDetail(@Param(value="orderId")String orderId);
	
	/**
	 * 根据订单号获取订单
	 * 
	 * @param mergeOrderNo
	 * @return
	 */
	List<BSalesOrderModel> selectSalesOrderByMergeNo(Page page);
    
	/**
	 * 根据合并订单号更新订单状态
	 * 
	 * @param mergeOrderNo
	 * @return
	 */
	int updateOrderStatusByMergeNo(String mergeOrderNo);
	
	List<BSalesOrderModel> queryTradInfo(@Param(value="userId")Integer userId,@Param(value="num")Integer num,@Param(value="pageSize")Integer pageSize);
	
	int queryTradInfoCount(Integer userId);
	
	/**
	 * 卖家销售单
	 * 
	 * @param num
	 * @param pageSize
	 * @param userId
	 * @param status
	 * @return
	 */
	List<BToCOrderModel> queryAllSalesOrder(@Param(value="num")Integer num,@Param(value="pageSize")Integer pageSize,@Param(value="userId")Integer userId,@Param(value="status")Integer status);
	
	/**
	 * 所有销售单的总记录数
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
    int queryAllSalesOrderCount(@Param(value="userId")Integer userId,@Param(value="status")Integer status);
    
    /**
	 * 删除销售单
	 * 
	 * @param id
	 * @return
	 */
    int deleteSalesOrderById(String id);
    
    List<BSalesOrderModel> findExpiredOrder();
    
    int deleteBatchOrders(List<BSalesOrderModel> list);
    
    void updateOrderStatusByOrderNo(String orderNo);
    
    /**
	 * 查询超过半小时未付款的订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryUnPayOrder(@Param(value="date") Date date,@Param(value="time")Integer time);
    
    /**
	 * 删除超过半小时未付款的订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    int deleteTimeoutOrder(Map<String,Object> map);
    
    /**
	 * 删除超过半小时未付款的订单详情
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    int deleteTimeoutOrderDetails(List<BSalesOrderModel> list);
    
    /**
	 * 根据订单号获取物流信息
	 * 
	 * @param orderNo:订单号
	 * @return 返回物流信息
	 */
    BSalesOrderModel selectLogisticsByOrderNo(String orderNo);
    
    /**
	 * 查询当前店铺下所有的订单
	 * 
	 * @param map
	 * @return
	 */
    List<BSalesOrderModel> querySaleOrderByBUserId(Map<String,Object> map);
    
    int querySaleOrderByBUserIdCount(Map<String,Object> map);
    
    /**
	 * 根据订单号更新物流信息
	 * 
	 * @param bSalesOrder:销售订单
	 * @return 返回物流信息
	 */
    int updateLogisticByOrderNo(BSalesOrderModel bSalesOrder);
    
    /**
	 * 修改退款退货状态
	 */
    void updateOrderReturnStatus(@Param(value="returnStatus") Integer returnStatus,@Param(value="orderId") String orderId);
    
    /**
	 * 更新订单的总价
	 */
    void updateTotalById(BSalesOrderModel bSalesOrder);
    
    /**
	 * 根据订单id获取订单信息
	 * 
	 * @param id:订单id
	 * @return 返回订单信息
	 */
    BSalesOrderModel selectOrderById(Integer id);
    
    /**
	 * 一天之内未付款的所有的非水果订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryFeFruitUnPayOrder(@Param(value="date") Date date,@Param(value="time")Integer time);
    
    /**
	 * 2小时之外的已发货的水果订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryFruitPayOrder(@Param(value="date") Date date,@Param(value="time")Integer time);
    
    /**
	 * 查询所有发货时间超过10天的非水果订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryFeFruitPayOrder(@Param(value="date") Date date,@Param(value="time")Integer time);
    
    /**
	 * 15分钟付款店家未接单的订单，自动取消
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryPayUnReceiveOrder(@Param(value="date") Date date,@Param(value="time")Integer time);
    
    /**
	 * 查询审核后超过1小时的水果订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryCheckOrder(@Param(value="date") Date date,@Param(value="time")Integer time);
    
    /**
	 * 查询审核后超过2天的非水果订单
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
    List<BSalesOrderModel> queryCheckFeFruitOrder(@Param(value="date") Date date,@Param(value="time")Integer time);

	int updateComment(String orderDetailsId);
	
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