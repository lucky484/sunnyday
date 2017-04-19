package com.softtek.mdm.dao;

import com.softtek.mdm.model.DeviceLocationModel;

public interface DeviceLocationRecordDao {
   
	  public int insertDeviceLocation(DeviceLocationModel deviceLocation);
	  
	  public DeviceLocationModel queryDeviceLocation(Integer deviceId);
}
