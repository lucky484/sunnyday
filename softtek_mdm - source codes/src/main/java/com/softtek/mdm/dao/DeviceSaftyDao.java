package com.softtek.mdm.dao;

import com.softtek.mdm.model.DeviceSaftyModel;

public interface DeviceSaftyDao extends CrudMapper<DeviceSaftyModel, Integer> {
	
	/**
	 * 根据设备id删除设备安全信息
	 * @param did
	 * @return
	 */
	int truncateWithDeviceId(Integer did);
	
	DeviceSaftyModel findOneWithDeviceId(Integer did);
	
	int updateSaftyByDeviceId(DeviceSaftyModel deviceSafty);
	
	int updateAll(DeviceSaftyModel deviceSafty);
}
