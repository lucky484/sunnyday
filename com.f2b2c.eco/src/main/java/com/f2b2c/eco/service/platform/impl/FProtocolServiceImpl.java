package com.f2b2c.eco.service.platform.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FProtocolDao;
import com.f2b2c.eco.model.platform.FProtocolModel;
import com.f2b2c.eco.service.platform.FProtocolService;
/**
 * 注册+担保协议
 * @author mozzie.chu
 *
 */
@Service
public class FProtocolServiceImpl implements FProtocolService {

	@Autowired
	private FProtocolDao fProtocolDao;

	/**
	 * 显示协议
	 */
	@Override
	public FProtocolModel queryFProtocolByType(String type) {
		// TODO Auto-generated method stub
		return fProtocolDao.queryFProtocolByType(type);
	}
	
	/**
	 * 填写协议
	 */
	@Override
	public int insert(FProtocolModel proModel) {
        proModel.setCreatedTime(new Date());
		return fProtocolDao.insert(proModel);
	}
	
	/**
	 * 修改协议
	 */
	@Override
	public int updateFProtocolContent(FProtocolModel proModel) {
		// TODO Auto-generated method stub
		return fProtocolDao.updateFProtocolContent(proModel);
	}
	
}
