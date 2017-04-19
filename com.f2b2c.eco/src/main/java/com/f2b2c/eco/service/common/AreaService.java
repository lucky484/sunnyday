package com.f2b2c.eco.service.common;

import java.util.List;

import com.f2b2c.eco.model.common.ProvinceModel;

public interface AreaService {

	/**
	 * 查询所有的省份
	 * 
	 * @return
	 */
	List<ProvinceModel> queryAll();
	
	/**
	 * 根据areaCode找到省市区
	 * @param areaCode
	 * @return
	 */
	String findArea(String areaCode);
	
	List<Integer> queryCityIdByProvinceId(Integer provinceId);
	
	List<Integer> queryAreaIdByCityId(List<Integer> list);
}
