package com.f2b.security.business.dao;

import java.util.List;
import java.util.Map;

import com.f2b.security.domain.FarmOrder;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FarmOrderDao {

	// ******************************************************************************
	// ********************************* CRUD START
	// *********************************
	// ******************************************************************************

	/**
	 * 添加订单信息
	 * 
	 * @author mozzie.chu
	 * @param model
	 */
	public Integer add(FarmOrder model);

	/**
	 * 修改（确认）订单
	 * 
	 * @author mozzie.chu
	 */
	public Integer update(FarmOrder model);

	/**
	 * Description:根据手机号查询订单
	 * 
	 * @author curry.su
	 * @param phone
	 * @return
	 */
	public List<FarmOrder> getOrderListByPhone(String phone);

	public List<FarmOrder> getOrderList(String openId);

	/**
	 * 根据手机号查询收货人、地址
	 * 
	 * @param phone
	 * @return
	 */
	public List<FarmOrder> getSuccessList(String openId);

	public List<FarmOrder> getOrderListByOpenId(String openid);
	/**
	 * 获取所有成功的促销订单
	 * @param openid
	 * @return
	 */
	public List<FarmOrder> getAllSuccessPromoteOrder(String openid);
	public List<FarmOrder> getAllOrderListByOpenId(String openid);

	public FarmOrder getOrderByNo(String orderNo, String openId);

	public FarmOrder getOrderBySku(String sku);

	/**
	 * 获取总记录数
	 */
	public Long totalGrapeVolume(Map<String, String> queryHash);

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
	 * 订单管理 - 分页列表
	 */
	public List<FarmOrder> findFarmOrder();

	/**
	 * 订单管理 - 分页列表
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @param sqlOrder
	 * @param queryHash
	 * @return
	 */
	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize, String sqlOrder,
			Map<String, String> queryHash);

	/**
	 * 导出未发货
	 * 
	 * @return
	 */
	public List<FarmOrder> findFarmOrderExcl1();

	/**
	 * 导出全部
	 * 
	 * @return
	 */
	public List<FarmOrder> findFarmOrderExcl();

	public FarmOrder getUnPayOrderByOpenid(String openid);

	public List<FarmOrder> getAllUnPayOrder();

	public FarmOrder getById(long id);

}
