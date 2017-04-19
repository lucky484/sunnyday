package com.f2b2c.eco.dao.market;

import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.CFavoriteShop;

public interface CFavoriteShopDao extends CrudMapper<CFavoriteShop, Integer> {

	int insertFavoriteShop(Map<String, Object> map);

	int deleteFavoriteShop(Map<String, Object> map);



}