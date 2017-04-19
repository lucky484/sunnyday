package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.DeviceIosBasicInfoModel;
import com.softtek.mdm.model.DeviceLocationModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;

public interface DeviceManagerService {
	
	public Page queryDeviceInfo(Integer id, Integer orgId, Map<String, Object> map,List<StructureModel> stelist);
	
	public int updateVisitStatus(DeviceManagerModel deviceManager);
	
	public int updateLostStatus(DeviceManagerModel deviceManager);
	
	public int updateDeviceStatus(DeviceManagerModel deviceManager);
	
	public int updateIsActive(DeviceManagerModel deviceManager);
	
	public int deleteDevice(DeviceManagerModel deviceManager);
	
	public int findBreakStatusCount(Integer orgid,List<Integer> idlist);
	
	public int findIrrCount(Integer orgid,List<Integer> idlist);

    // 待监控总数
	public int findNotMonitoredCount(int orgid, List<Integer> idlist);

    // 托管中总数
	public int findTrusteeshipCount(int orgid, List<Integer> idlist);

    /*
     * //未设置安全密码总数 public int findNoPasswordCount(int orgid, List<Integer>
     * idlist);
     */
    // 设备丢失总数
	public int findDeviceLostCount(int orgid, List<Integer> idlist);

	public int findCancellationCount(int orgid, List<Integer> idlist);

	public int findMonitoredCount(int orgid, List<Integer> idlist);
	
	AndroidDevicePolicy findPolicyByName(Integer orgId,String name);
	
	public DeviceManagerModel queryDevice(String sn,Integer userId);
	
	public DeviceManagerModel queryDeviceAndUserInfo(Integer id);

	public List<DeviceManagerModel> findAllByOrgID(int orgid, List<Integer> idlist);
	
	public List<DeviceManagerModel> queryIsTakeOff();
	
	public String findUdidById(int uid);

	public Map<String, Object> findLongitudeAndLatitude(int did);
	
	public int deleteDeviceBySn(String sn);
	
	public DeviceManagerModel queryDeviceAllInfo(Integer id);
	
	public DeviceManagerModel queryUserId(Integer deviceId);
	
	public List<DeviceManagerModel> queryAllDeviceList(Integer id, Integer orgId,List<StructureModel> stelist);
	
	public int updateVisitStatusAll(DeviceManagerModel deviceManager);
	
	public int updateDeviceStatusAll(DeviceManagerModel deviceManager);
	
	public int updateIsActiveAll(DeviceManagerModel deviceManager);
	
	public int deleteAllDevice(DeviceManagerModel deviceManager);
	
	public int updateLostStatusAll(DeviceManagerModel deviceManager);
	
	public int updateEnableUnbund(DeviceManagerModel deviceManager);
    
    public int updateEnableUnbundAll(DeviceManagerModel deviceManager);
    
    public List<DeviceManagerModel> queryAllDeviceById(Integer userId);
    
    public int insertDeviceLocation(DeviceLocationModel deviceLocation);
    
    public DeviceManagerModel queryDeviceInfoBySn(String sn);
    
    public DeviceLocationModel queryDeviceLocation(Integer deviceId);
    
    // 导出设备信息
    public List<DeviceManagerModel> queryDeviceExport(Integer id, Integer orgId, Map<String, Object> map,List<StructureModel> stelist);
    
    public DeviceManagerModel queryIosIsExists(String udid);
    
    public DeviceManagerModel queryDeviceInfoById(Integer id);
    
    DeviceIosBasicInfoModel queryIosDeviceBasicInfo(String iosUuid);
    
    int deleteDeviceByUdid(String udid);

    int findAllDeviceCount(int orgid, List<Integer> idlist);

    void insertDevice(DeviceManagerModel model);
    
}
