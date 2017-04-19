package com.f2b.security.business.biz.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b.security.business.biz.FarmProduceBiz;
import com.f2b.security.business.dao.FarmProduceDao;
import com.f2b.security.domain.FarmProduce;

@Service("farmProduceBiz")
public class FarmProduceBizImpl implements FarmProduceBiz{

	private Logger logger = LoggerFactory.getLogger(FarmProduceBizImpl.class);

	@Autowired
	private FarmProduceDao farmProduceDao;

	
	/**
	 * 商品列表页
	 * @param request
	 * @return
	 */
	@Override
	public List<FarmProduce> getProduceList(FarmProduce model) {
		return farmProduceDao.getProduceList(model);
	}

	/**
	 * 跳转商品详情页
	 * @param produceId
	 * @return
	 */
	@Override
	public List<FarmProduce> getFarmProducePage(String produceId) {
		// TODO Auto-generated method stub
		return farmProduceDao.getFarmProducePage(produceId);
	}

}
