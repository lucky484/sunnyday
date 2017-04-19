package com.f2b2c.eco.dao.market;

import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.CFavoriteGoods;

public interface CFavoriteGoodsDao extends CrudMapper<CFavoriteGoods, Integer> {

	int insertFavoriteGoods(Map<String, Object> map);

	int deleteFavoriteGoods(Map<String, Object> map);

}