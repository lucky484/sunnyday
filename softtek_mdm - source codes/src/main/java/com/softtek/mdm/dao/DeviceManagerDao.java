
package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceIosBasicInfoModel;
import com.softtek.mdm.model.DeviceManagerModel;

public interface DeviceManagerDao {
    
	public List<DeviceManagerModel> queryDeviceInfo(Map<String,Object> map);
	
	public int queryDeviceInfoCount(Map<String,Object> map);
	
	public int updateVisitStatus(DeviceManagerModel deviceManager);
	
	public int updateLostStatus(DeviceManagerModel deviceManager);
	
	public int updateDeviceStatus(DeviceManagerModel deviceManager);
	
	public int updateIsActive(DeviceManagerModel deviceManager);
	
	public int deleteDevice(DeviceManagerModel deviceManager);
	
    /**
     * 总计破解总数
     * 
     * @param map
     * @return
     */
	public int findBreakStatusCount(Map<String,Object> map);

	public int findIrrCount(Map<String, Object> map);

	public int findNotMonitoredCount(Map<String, Object> map);

	public int findTrusteeshipCount(Map<String, Object> map);

	/*public int findNoPasswordCount(Map<String, Object> map);*/

	public int findDeviceLostCount(Map<String, Object> map);

	public int findCancellationCount(Map<String, Object> map);

	public int findMonitoredCount(Map<String, Object> map);
	
	public DeviceManagerModel queryDevice(Map<String,Object> map);
    
	public DeviceManagerModel queryDeviceAndUserInfo(Integer id);

	public List<DeviceManagerModel> findAllByOrgID(Map<String, Object> map);
	
	public List<DeviceManagerModel> queryIsTakeOff();

	public String findUdidById(int uid);
	
	public int deleteDeviceBySn(String sn);
	
	public DeviceManagerModel queryDeviceAllInfo(Integer id);
	
	public DeviceManagerModel queryUserId(Integer deviceId);
	
	public List<DeviceManagerModel> queryAllDeviceList(Map<String,Object> map);
	
	public int updateVisitStatusAll(DeviceManagerModel deviceManager);
	
	public int updateDeviceStatusAll(DeviceManagerModel deviceManager);
	
	public int updateIsActiveAll(DeviceManagerModel deviceManager);
	
	public int deleteAllDevice(DeviceManagerModel deviceManager);
	
    public int updateLostStatusAll(DeviceManagerModel deviceManager);
    
    public int updateEnableUnbund(DeviceManagerModel deviceManager);
    
    public int updateEnableUnbundAll(DeviceManagerModel deviceManager);
    
    public List<DeviceManagerModel> queryAllDeviceById(Integer userId);
    
    public DeviceManagerModel queryDeviceInfoBySn(String sn);
    
    public List<DeviceManagerModel> queryDeviceExport(Map<String,Object> map);
    
    public DeviceManagerModel queryIosIsExists(String udid);
    
    public DeviceManagerModel queryDeviceInfoById(Integer id);
    
    DeviceManagerModel queryDeviceInfoByUdid(String udid);
    
    DeviceIosBasicInfoModel queryIosDeviceBasicInfo(String iosUuid);
    
    int deleteDeviceByUdid(String udid);

    int findAllDeviceCount(Map<String, Object> map);
    
}
