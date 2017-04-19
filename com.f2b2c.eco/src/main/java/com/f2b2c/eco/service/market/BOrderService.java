package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.f2b2c.eco.model.bean.BShopCarBean;
import com.f2b2c.eco.model.bean.CLogisticBean;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BGoodsToPurchaseOrderModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BShopToOrderModel;
import com.f2b2c.eco.model.market.BtoCBSalesOrderModel;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.CShopCarModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.InsertCDeliveryAddressModel;

/**
 * 订单接口
 * 
 * @author jane.hui
 *
 */
public interface BOrderService {
    
    /**
	 * 获取购物车表数据
	 * 
	 * @param userId:消费者外键
	 * @return 返回购物车中所有的商品
	 */
    List<CShopCarModel> getShopCarByUserId(Integer userId,List<Integer> list);
    
    /**
	 * 删除购物车中的商品
	 * 
	 * @param 购物车表主键
	 * @param 返回删除购物车中的商品是否成功标
	 */
    int deleteShopCartById(String id);
    
    /**
	 * 修改购物车中的商品
	 * 
	 * @param 购物车中表主键
	 * @param 修改某个商品的数量
	 * @return 返回修改购物车中的商品是否成功
	 */
    ApiResultModel<Integer> modifyShopCart(String strQuantity);
    
    /**
	 * 检验购物车中的商品
	 * 
	 * @param cartList:购物车中的要结算的商品
	 * @return 返回购物车中版本升级的商品
	 */
    ApiResultModel<Map<String, Object>> checkShopCart(String shopCartStr,Integer userId,String addressId);
    
    /**
	 * 根据用户外键获取收货地址
	 * 
	 * @param userId:用户外键
	 * @return:返回收货地址
	 */
    CDeliveryAddressModel getCDeliveryAddressList(Integer userId);

    /**
	 * 加入购物车
	 * 
	 * @param integer
	 * @param integer2
	 * @param integer3
	 * @return
	 */
    DataToCResultModel<Object> insertBGoodsShopCar(String goodsNo, Integer integer2, Integer integer3);
    
    /**
	 * 将购物车商品转换成json
	 * 
	 * @param list
	 * @return 获取购物车商品转换成json格式
	 */
    BShopCarBean parseShopCartToJson(List<CShopCarModel> list);
    
    /**
	 * 将购物车商品转换成json
	 * 
	 * @param list
	 * @return 获取购物车商品转换成json格式
	 */
    Map<String,Object> parseShopCartToJson(List<CShopCarModel> list,Integer provinceId,Integer allPrice);
    
    /**
	 * 获取订单页面中的商品和收货地址
	 * 
	 * @param request
	 * @return 返回订单页面商品以及收货地址
	 */
    Map<String,Object> getOrderGoodsAndAddresss(Integer userId,List<Integer> list,String addressId,Integer allPrice);
    
    /**
	 * 立即购买
	 * 
	 * @param id:商品主键
	 * @param userId:用户外键
	 * @return 返回立即购买跳转后确认订单页面的商品和收货地址
	 */
    ApiResultModel<Map<String, Object>> purchase(Integer id,Integer userId,Integer quantity,String addressId);    
    
    /**
	 * 根据用户id获取收货地址
	 * 
	 * @param userId
	 * @return
	 */
    ApiResultModel<String> getDeliveryAddress(Integer userId);
    
    /**
	 * 插入收货地址表
	 * 
	 * @param address:收货地址表对象
	 * @return 返回是否插入成功
	 */
    ApiResultModel<String> insertDeliveryAddress(InsertCDeliveryAddressModel address,Integer userId);
    
    /**
	 * 提交订单
	 * 
	 * @param btoCBSalesOrder:订单信息
	 * @param list:订单详情
	 * @param userId:用户外键
	 * @return
	 */
    ApiResultModel<String> submitOrder(String payType,HttpServletRequest request,HttpServletResponse response,BtoCBSalesOrderModel btoCBSalesOrder,List<BShopToOrderModel> list,Integer userId);
    
    /**
	 * 提交订单
	 * 
	 * @param btoCBSalesOrder:订单信息
	 * @param list:订单详情
	 * @param userId:用户外键
	 * @return
	 */
    ApiResultModel<String> submitOneOrder(String payType,HttpServletRequest request,HttpServletResponse response,BtoCBSalesOrderModel btoCBSalesOrder,BGoodsToPurchaseOrderModel shopObj,Integer userId);
    
    /**
	 * 修改订单状态
	 * 
	 * @param app_id:应用id
	 * @param out_trade_no:商品订单号
	 * @param body:商品描述
	 * @param buyer_id:买家id
	 * @param seller_id:卖家id
	 * @param subject:商品名称
	 * @param total_amount:支付金额
	 * @param trade_no:交易流水号
	 * @param traceContent:交易内容
	 */
    List<BSalesOrderModel> notifyUrl(String app_id,String out_trade_no,String body,String buyer_id,String seller_id,String subject,String total_amount,String trade_no,String tradeContent);
    
    /**
	 * 检测是否超出范围
	 * 
	 * @param list:商品id列表
	 * @param oldLocationX:收货地址x轴
	 * @param oldLocationY:收货地址y轴
	 * @return 返回是否超出范围
	 */
    ApiResultModel<String> isOutOfRange(List<Integer> list,String oldLocationX,String oldLocationY);
    
    /**
	 * 微信回调URL
	 * 
	 * @param record
	 */
    List<BSalesOrderModel> wxNotifyUrl(Map<String, String> map);
    
    /**
	 * 根据店铺id和收货地址id获取收货地址
	 * 
	 * @param shopId:店铺id
	 * @param provinceId:省id
	 * @return 返回运费
	 */
    int getFreight(Integer shopId, CDeliveryAddressModel deliveryAddress, String catalog);
    
    /**
	 * 查询购物车的总数和种类
	 * 
	 * @param userId
	 * @return 返回购物车总数量
	 */
    Map<String,Object> queryCarCountAndKindByUserId(Integer userId);
    
    /**
	 * 物流公司
	 * 
	 * @param orderNo:订单号
	 * @return
	 */
    CLogisticBean getLogisticsInfo(String orderNo);
    
    List<BSalesOrderModel> selectSalesOrderByMergeNo(String orderNo);

}