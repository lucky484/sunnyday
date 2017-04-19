package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.TokenUpdateInfoDao;
import com.softtek.mdm.model.TokenUpdateInfo;
import com.softtek.mdm.service.TokenUpdateInfoService;

@Service
public class TokenUpdateInfoServiceImpl implements TokenUpdateInfoService{
    
	@Autowired
	private TokenUpdateInfoDao tokenUpdateInfoDao;
	
	@Override
	public TokenUpdateInfo queryIsProfileByIosUuid(String iosUuid) {
		
		return tokenUpdateInfoDao.queryIsProfileByIosUuid(iosUuid);
	}

	@Override
	public int deleteTokenUpdateInfoByUuid(String udid) {
		
		return tokenUpdateInfoDao.deleteTokenUpdateInfoByUuid(udid);
	}

}
