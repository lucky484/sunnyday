package com.f2b.security.business.biz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.f2b.security.domain.FarmOrder;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FarmOrderBiz {

	// ******************************************************************************
	// ********************************* CRUD START
	// *********************************
	// ******************************************************************************

	/**
	 * 添加或修改（确认）订单
	 * 
	 * @author mozzie.chu
	 * @param model
	 */
	public void addFarmOrder(FarmOrder model);

	/**
	 * 根据手机号查询订单
	 * 
	 * @param phone
	 * @return
	 */
	public List<FarmOrder> getOrderListByPhone(String phone);

	public List<FarmOrder> getOrderList(String openId);
	public FarmOrder getOrderBySku(String sku);

	/**
	 * 根据openid查询所有订单
	 * 
	 * @param phone
	 * @return
	 */
	public List<FarmOrder> getOrderListByOpenId(String openid);
	/**
	 * 获取所有成功的促销订单
	 * @param openid
	 * @return
	 */
	public List<FarmOrder> getAllSuccessPromoteOrder(String openid);
	public List<FarmOrder> getAllOrderListByOpenId(String openid);
	/**
	 * 获取未支付订单
	 * @param openid
	 * @return
	 */
	public FarmOrder getUnPayOrderByOpenid(String openid);

	public FarmOrder getOrderByNo(String orderNo, String openId);

	/**
	 * 获取总记录数
	 */
	public Long totalGrapeVolume();

	/**
	 * 根据sku查询订单
	 * 
	 * @param sku
	 * @return
	 */
	public List<FarmOrder> getDetailMap(String sku);

	/**
	 * 根据sku查询当前所下的订单
	 * 
	 * @param sku
	 * @return
	 */
	public List<FarmOrder> getOrderListBySku(String sku);
	
	/**
	 * 带着produceName商品名跳转
	 * 
	 * @param produceName
	 * @return
	 */
	public List<FarmOrder> getFarmOrderPage(String produceName);

	/**
	 * 分页列表
	 */
	public List<FarmOrder> findFarmOrder();

	public List<FarmOrder> findFarmOrder(Map<String, String> queryHash);

	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize);

	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize, Map<String, String> queryHash);

	/**
	 * 订单管理 - 分页列表
	 */
	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize, String sqlOrder,
			Map<String, String> queryHash);

	/**
	 * 导出未发货
	 * 
	 * @param response
	 */
	public void getFarmOrderExport1(HttpServletResponse response);

	/**
	 * 导出全部
	 * 
	 * @param response
	 */
	public void getFarmOrderExport(HttpServletResponse response);

	public void updateOrder(FarmOrder farmOrder);
	public void updateOrder(FarmOrder farmOrder, String openid, Object nickname, Object shareOpenid,
			AccessToken accessToken);
	
	public FarmOrder getById(long id);
}
