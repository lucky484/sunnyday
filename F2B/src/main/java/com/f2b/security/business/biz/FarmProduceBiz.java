package com.f2b.security.business.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.f2b.security.domain.FarmProduce;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FarmProduceBiz {

	// ******************************************************************************
	// ********************************* CRUD START *********************************
	// ******************************************************************************

	/**
	 * 商品列表页
	 * @param request
	 * @return
	 */
	public List<FarmProduce> getProduceList(FarmProduce model);
	
	/**
	 * 跳转商品详情页
	 * @param produceId
	 * @return
	 */
	public List<FarmProduce> getFarmProducePage(String produceId);
}
