package com.softtek.mdm.service;

import com.softtek.mdm.model.TokenUpdateInfo;

public interface TokenUpdateInfoService {
    
	TokenUpdateInfo queryIsProfileByIosUuid(String iosUuid);
	
	int deleteTokenUpdateInfoByUuid(String udid);
}
