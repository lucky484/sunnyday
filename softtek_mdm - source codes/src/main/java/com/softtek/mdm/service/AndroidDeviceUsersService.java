package com.softtek.mdm.service;

import com.softtek.mdm.model.AndroidDeviceUsers;

public interface AndroidDeviceUsersService {

	/**
     * 根据用户id查找用户最新的设备策略
     * @param uid
     * @return
     */
    AndroidDeviceUsers findOneByUserIdLast(Integer uid);

    int updateByPrimaryKeySelective(AndroidDeviceUsers record);
    
    int insert(AndroidDeviceUsers record);
}
