package com.f2b2c.eco.service.market.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b2c.eco.dao.market.CFavoriteGoodsDao;
import com.f2b2c.eco.service.market.CFavoriteGoodsService;

/**
 * 订单实现类
 * 
 * @author jane.hui
 *
 */
@Service("cFavoriteService")
@Transactional
public class CFavoriteGoodsServiceImpl implements CFavoriteGoodsService {
	@Autowired
	private CFavoriteGoodsDao cFavoriteDao;

	@Override
	public int insertFavoriteGoods(Map<String, Object> map) {
		return cFavoriteDao.insertFavoriteGoods(map);
	}

	@Override
	public int deleteFavoriteGoods(Map<String, Object> map) {
		return cFavoriteDao.deleteFavoriteGoods(map);
	}

}