package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;

public interface FShopCarDao extends CrudMapper<FSalesOrderDetailsModel, Serializable>{

	
	int deleteShopCarById(Integer id);
	/**
	 * 
	 * @param map
	 * @return
	 */
	int deleteBatch(Map<String, Object> map);
}
