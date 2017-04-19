package com.f2b2c.eco.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.common.AreaDao;
import com.f2b2c.eco.model.common.ProvinceModel;
import com.f2b2c.eco.service.common.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;

	@Override
	public List<ProvinceModel> queryAll() {
		return areaDao.findAll();
	}

	@Override
	public String findArea(String areaCode) {
		if(null!=areaCode){
			return areaDao.findArea(areaCode);
		}
		return null;
	}

	@Override
	public List<Integer> queryCityIdByProvinceId(Integer provinceId) {
	
		return areaDao.queryCityIdByProvinceId(provinceId);
	}

	@Override
	public List<Integer> queryAreaIdByCityId(List<Integer> list) {
		
		return areaDao.queryAreaIdByCityId(list);
	}
}
