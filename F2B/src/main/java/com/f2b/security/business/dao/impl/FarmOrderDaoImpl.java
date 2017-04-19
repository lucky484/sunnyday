package com.f2b.security.business.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.f2b.security.business.dao.FarmOrderDao;
import com.f2b.security.domain.FarmOrder;
import com.f2b.sugar.data.QueryUtils;

@Repository
public class FarmOrderDaoImpl implements FarmOrderDao {

	private final static Logger logger = LoggerFactory.getLogger(FarmOrderDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	public Map<String, Object> getSearchCondition(Map<String, String> queryHash) {

		Map<String, Object> conditionHash = new HashMap<>();
		conditionHash.put("status != ?{paramIndex} ", -2);
		if (queryHash == null) {
			return conditionHash;
		}

		return conditionHash;
	}

	// ******************************************************************************
	// ********************************* CRUD START
	// *********************************
	// ******************************************************************************

	/**
	 * 添加订单信息
	 * 
	 * @author mozzie.chu
	 */
	@Override
	@Transactional
	public Integer add(FarmOrder farmOrder) {
		farmOrder.setCreateDate(new Date());
		farmOrder.setStatus(-2);
		// farmOrder.setSku(OrderCofig.obtionOrderNo());
		this.em.persist(farmOrder);
		logger.info("FarmOrder添加farmOrder成功！");
		return 1;
	}

	/**
	 * 修改（确认）订单
	 * 
	 * @@author mozzie.chu
	 */
	@Override
	@Transactional
	public Integer update(FarmOrder farmOrder) {
		FarmOrder existFarmOrder = em.find(FarmOrder.class, farmOrder.getOrderId());
		existFarmOrder.setProduceName(farmOrder.getProduceName());
		existFarmOrder.setMerchant(farmOrder.getMerchant());
		existFarmOrder.setWholesaler(farmOrder.getWholesaler());
		existFarmOrder.setSku(farmOrder.getSku());
		existFarmOrder.setUnitPrice(farmOrder.getUnitPrice());
		existFarmOrder.setWeight(farmOrder.getWeight());
		existFarmOrder.setTotal(farmOrder.getTotal());
		existFarmOrder.setStatus(farmOrder.getStatus());
		existFarmOrder.setAddress(farmOrder.getAddress());
		existFarmOrder.setNickname(farmOrder.getNickname());
		existFarmOrder.setPhone(farmOrder.getPhone());
		existFarmOrder.setOpenId(farmOrder.getOpenId());
		existFarmOrder.setComments(farmOrder.getComments());
		existFarmOrder.setFreight(farmOrder.getFreight());
		existFarmOrder.setShareOpenId(farmOrder.getShareOpenId());
		existFarmOrder.setShareNickname(farmOrder.getShareNickname());
		existFarmOrder.setUpdateDate(new Date());
		logger.info("FarmOrder修改farmOrder成功！");
		return 1;
	}

	/**
	 * 根据手机号查询订单
	 */
	@Override
	public List<FarmOrder> getOrderListByPhone(String phone) {

		TypedQuery<FarmOrder> typedQuery = em.createQuery(
				"select r from FarmOrder r where r.phone=?1 and r.status != -2 order by r.status desc, createDate desc",
				FarmOrder.class).setParameter(1, phone);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	public List<FarmOrder> getOrderList(String openId) {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.openId=?1 order by r.status desc, createDate desc",
						FarmOrder.class)
				.setParameter(1, openId);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	@Override
	public List<FarmOrder> getSuccessList(String openId) {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.openId=?1 and r.status != -2 order by r.orderId desc ",
						FarmOrder.class)
				.setParameter(1, openId);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}
	public List<FarmOrder> getAllSuccessPromoteOrder(String openId) {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.openId=?1 and r.status != -2 r.unitPrice=28.8 order by r.orderId desc ",
						FarmOrder.class)
				.setParameter(1, openId);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	public FarmOrder getOrderByNo(String orderNo, String openId) {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.sku=?1 and r.openId=?2 order by r.orderId desc ",
						FarmOrder.class)
				.setParameter(1, orderNo).setParameter(2, openId);
		List<FarmOrder> list = typedQuery.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取总记录数 获取提子的销量
	 */
	@Override
	public Long totalGrapeVolume(Map<String, String> queryHash) {
		Map<String, Object> conditions = getSearchCondition(queryHash);
		TypedQuery<Long> typedQuery = QueryUtils.getTypedQueryByCondition("select count(a) from FarmOrder a",
				conditions, "", em, Long.class);
		return typedQuery.getSingleResult();
	}

	/**
	 * 根据sku查询订单
	 */
	@Override
	public List<FarmOrder> getDetailMap(String sku) {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.sku=?1 and r.status != -2 ", FarmOrder.class)
				.setParameter(1, sku);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 根据sku查询当前所下的订单
	 * 
	 * @param sku
	 * @return
	 */
	public List<FarmOrder> getOrderListBySku(String sku) {
		TypedQuery<FarmOrder> typedQuery = em.createQuery("select r from FarmOrder r where r.sku=?1", FarmOrder.class)
				.setParameter(1, sku);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 带着produceName商品名跳转
	 */
	@Override
	public List<FarmOrder> getFarmOrderPage(String produceName) {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.produceName=?1 and r.status != -2", FarmOrder.class)
				.setParameter(1, produceName);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 订单管理 - 列表
	 */
	@Override
	public List<FarmOrder> findFarmOrder() {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.status != -2 order by r.orderId desc", FarmOrder.class);
		typedQuery.setFirstResult(0).setFirstResult(20);
		return typedQuery.getResultList();
	}

	/**
	 * 订单管理 - 分页列表
	 */
	@Override
	public List<FarmOrder> findFarmOrder(Integer pageNow, Integer pageSize, String sqlOrder,
			Map<String, String> queryHash) {
		if (StringUtils.isEmpty(sqlOrder)) {
			sqlOrder = "order by r.orderId desc ";
		}

		Map<String, Object> conditions = getSearchCondition(queryHash);
		TypedQuery<FarmOrder> typedQuery = QueryUtils.getTypedQueryByCondition("select r from FarmOrder r", conditions,
				sqlOrder, em, FarmOrder.class);
		// 判断是否需要分页，并提交分页方法
		if (pageSize > 0 && pageNow > 0) {
			logger.debug("提交了分页查询信息，pageNow为[" + pageNow + "]，pageSize为[" + pageSize + "]");
			int minLimit = pageSize * (pageNow - 1);
			int maxLimit = pageSize;
			typedQuery.setFirstResult(minLimit).setMaxResults(maxLimit);
		}
		// 返回查询结果
		return typedQuery.getResultList();
	}

	/**
	 * 导出未发货
	 */
	@Override
	public List<FarmOrder> findFarmOrderExcl1() {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.status = -1 order by r.orderId desc", FarmOrder.class);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	/**
	 * 导出全部
	 * 
	 * @return
	 */
	public List<FarmOrder> findFarmOrderExcl() {
		TypedQuery<FarmOrder> typedQuery = em
				.createQuery("select r from FarmOrder r where r.status != -2 order by r.orderId desc", FarmOrder.class);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	@Override
	public List<FarmOrder> getOrderListByOpenId(String openid) {
		TypedQuery<FarmOrder> typedQuery = em.createQuery(
				"select r from FarmOrder r where r.openId=:openId and sku is not null order by r.orderId desc",
				FarmOrder.class);
		typedQuery.setParameter("openId", openid);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}
	
	public List<FarmOrder> getAllOrderListByOpenId(String openid) {
		TypedQuery<FarmOrder> typedQuery = em.createQuery(
				"select r from FarmOrder r where r.openId=:openId order by r.orderId desc",
				FarmOrder.class);
		typedQuery.setParameter("openId", openid);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	public FarmOrder getUnPayOrderByOpenid(String openid) {
		TypedQuery<FarmOrder> typedQuery = em.createQuery(
				"select r from FarmOrder r where r.openId=:openId and r.status=-2 order by r.createDate desc",
				FarmOrder.class);
		typedQuery.setParameter("openId", openid);
		List<FarmOrder> list = typedQuery.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public List<FarmOrder> getAllUnPayOrder() {
		TypedQuery<FarmOrder> typedQuery = em.createQuery(
				"select r from FarmOrder r where r.status=-2 and r.sku is not null order by r.createDate desc",
				FarmOrder.class);
		List<FarmOrder> list = typedQuery.getResultList();
		return list;
	}

	public FarmOrder getById(long id) {
		return em.find(FarmOrder.class, id);
	}

	@Override
	public FarmOrder getOrderBySku(String sku) {
		TypedQuery<FarmOrder> typedQuery = em.createQuery(
				"select r from FarmOrder r where r.sku=:sku and r.status!=-2 order by r.createDate desc",
				FarmOrder.class);
		typedQuery.setParameter("sku", sku);
		List<FarmOrder> list = typedQuery.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
