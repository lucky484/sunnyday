package com.softtek.mdm.service;

import java.util.Map;

import com.softtek.mdm.model.AndroidDevicePolicy;

public interface AndroidDevicePolicyService {

	 /**
     * 根据orgId，策略名称获取相应的策略
     * @param map
     * @return
     */
    AndroidDevicePolicy findPolicyByMap(Map<String, Object> map);
}
