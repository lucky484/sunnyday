package com.f2b2c.eco.dao.common;

import java.util.List;

import com.f2b2c.eco.apimodel.Area;
import com.f2b2c.eco.model.common.ProvinceModel;

public interface AreaDao extends CrudMapper<ProvinceModel, Integer>
{

	Area getAreaByCode(Integer areaId);
	
	/**
	 * 根据areaCode扎到省市区
	 * @param areaCode
	 * @return
	 */
	String findArea(String areaCode);
	
	List<Integer> queryCityIdByProvinceId(Integer provinceId);
	
	List<Integer> queryAreaIdByCityId(List<Integer> list);
}
