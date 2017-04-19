package com.f2b2c.eco.service.market.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b2c.eco.dao.market.CFavoriteShopDao;
import com.f2b2c.eco.service.market.CFavoriteShopService;

/**
 * 订单实现类
 * 
 * @author jane.hui
 *
 */
@Service("cFavoriteShopService")
@Transactional
public class CFavoriteShopServiceImpl implements CFavoriteShopService {
	@Autowired
	private CFavoriteShopDao cFavoriteShopDao;

	@Override
	public int insertFavoriteShop(Map<String, Object> map) {
		return cFavoriteShopDao.insertFavoriteShop(map);
	}

	@Override
	public int deleteFavoriteShop(Map<String, Object> map) {
		return cFavoriteShopDao.deleteFavoriteShop(map);
	}

}