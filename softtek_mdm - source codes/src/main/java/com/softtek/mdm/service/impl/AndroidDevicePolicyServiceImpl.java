package com.softtek.mdm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.AndroidDevicePolicyDao;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.service.AndroidDevicePolicyService;

@Service
public class AndroidDevicePolicyServiceImpl implements AndroidDevicePolicyService {
	
	@Autowired
	private AndroidDevicePolicyDao androidDevicePolicyDao;
	@Override
	public AndroidDevicePolicy findPolicyByMap(Map<String, Object> map) {
		return androidDevicePolicyDao.findPolicyByMap(map);
	}

}
