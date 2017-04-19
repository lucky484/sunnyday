package com.f2b2c.eco.service.market;

import com.f2b2c.eco.model.market.SmsValidCodeModel;

public interface SmsValidCodeService {

	public int addSmsValidCode(SmsValidCodeModel smsValidCode);
	
	public int updateCodeByPhone(SmsValidCodeModel smsValidCode);
	
	public SmsValidCodeModel queryIsExits(String mobilePhone);
}
