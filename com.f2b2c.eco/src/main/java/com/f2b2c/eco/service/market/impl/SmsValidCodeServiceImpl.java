package com.f2b2c.eco.service.market.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.SmsValidCodeDao;
import com.f2b2c.eco.model.market.SmsValidCodeModel;
import com.f2b2c.eco.service.market.SmsValidCodeService;

@Service
public class SmsValidCodeServiceImpl implements SmsValidCodeService {

	@Autowired
	private SmsValidCodeDao smsValidCodeDao;

	@Override
	public int addSmsValidCode(SmsValidCodeModel smsValidCode) {

		return smsValidCodeDao.addSmsValidCode(smsValidCode);
	}

	@Override
	public int updateCodeByPhone(SmsValidCodeModel smsValidCode) {
		
		return smsValidCodeDao.updateCodeByPhone(smsValidCode);
	}

	@Override
	public SmsValidCodeModel queryIsExits(String mobilePhone) {
		
		return smsValidCodeDao.queryIsExits(mobilePhone);
	}

}
