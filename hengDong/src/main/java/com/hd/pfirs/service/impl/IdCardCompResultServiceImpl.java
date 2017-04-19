package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.hd.pfirs.dao.IdCardCompResultDao;
import com.hd.pfirs.model.IdCardCompResult;
import com.hd.pfirs.service.IdCardCompResultService;

public class IdCardCompResultServiceImpl implements IdCardCompResultService {
	@Autowired
	public IdCardCompResultDao ICCompResultDao;

	@Override
	public void insertIdCardCompResult(IdCardCompResult idCardCompResult) {
		ICCompResultDao.insertIdCardCompResult(idCardCompResult);
	}
}
