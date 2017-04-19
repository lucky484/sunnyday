package com.f2b2c.eco.service.platform.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FFreightDao;
import com.f2b2c.eco.model.platform.FFreightModel;
import com.f2b2c.eco.service.platform.FFreightService;
/**
 * 
 * @author mozzie.chu
 *
 */
@Service
public class FFreightServiceImpl implements FFreightService{

	@Autowired
	private FFreightDao fFreightDao;
	
	/**
	 * 查询内容至页面
	 */
	@Override
	public FFreightModel select(Integer Id) {
		// TODO Auto-generated method stub
		return fFreightDao.select(Id);
	}

	/**
	 * 添加
	 */
	@Override
	public int insert(FFreightModel model) {
		model.setCreatedTime(new Date());
		return fFreightDao.insert(model);
	}

	/**
	 * 修改
	 */
	@Override
	public int update(FFreightModel model) {
		model.setUpdatedTime(new Date());
		return fFreightDao.update(model);
	}

}
