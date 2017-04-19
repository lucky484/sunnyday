package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.AndroidDevicePolicyDao;
import com.softtek.mdm.dao.DeletedDeviceDao;
import com.softtek.mdm.dao.DeviceLocationRecordDao;
import com.softtek.mdm.dao.DeviceManagerDao;
import com.softtek.mdm.dao.ParamSettingDao;
import com.softtek.mdm.model.AndroidDevicePolicy;
import com.softtek.mdm.model.DeviceIosBasicInfoModel;
import com.softtek.mdm.model.DeviceLocationModel;
import com.softtek.mdm.model.DeviceManagerModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.service.DeviceManagerService;


@Service
public class DeviceManagerServiceImpl implements DeviceManagerService{
   
	@Autowired
	private DeviceManagerDao deviceManagerDao;
	@Autowired
	private AndroidDevicePolicyDao androidDevicePolicyDao;
	
	@Autowired
	private ParamSettingDao paramSettingDao;

    @Autowired
    private DeletedDeviceDao deletedDeviceDao;
	
	@Autowired
	private DeviceLocationRecordDao deviceLocationRecoedDao;
	
	
	@Override
	public Page queryDeviceInfo(Integer id, Integer orgId, Map<String, Object> map,List<StructureModel> stelist) {
		List<Integer> idsList = new ArrayList<Integer>();
//		List<StructureModel> stelist=(List<StructureModel>) structureDao.findAllByOrgID(orgId);
		if(id == null){
			for(StructureModel s : stelist){
				if(s.getParent() == null){
					id = s.getId();
				}
			}
		}
		List<Integer> idList = queryChild(stelist,id,idsList);
		if(id != null){
			idList.add(id);
		}
        // 动态获得托管时间
		SystemParamSetModel param=paramSettingDao.querySysParamSetting();
        // 分钟*60=时间戳
        Integer outtime=Integer.parseInt(param.getOutManagerTime().toString())*60;
		map.put("idList",idList);
		map.put("orgId", orgId);
		map.put("outtime", outtime);
		Page p = new Page();
		int total = deviceManagerDao.queryDeviceInfoCount(map);
		List<DeviceManagerModel> list = deviceManagerDao.queryDeviceInfo(map);
		for(DeviceManagerModel deviceManager : list){
				if(deviceManager.getDevice_type().equals("android")){
					if(deviceManager.getDevice_status() == 1){
						deviceManager.setDeviceStatus("1");
					}else{
						if(((float)(new Date().getTime() - deviceManager.getLast_collection_time().getTime())/60000) > (float)param.getOutManagerTime()){
                        // 1表示注销中
                        deviceManager.setDeviceStatus("2"); // 托管中
						}else{
							if(deviceManager.getIsActive().equals("0")){
                            deviceManager.setDeviceStatus("3"); // 待监控
							}else{
                            deviceManager.setDeviceStatus("4"); // 监控中
							}
						}
					}
				}else{
					deviceManager.setDeviceStatus(deviceManager.getDevice_status().toString());
				}
			}
		p.setData(list);
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p; 
	}
   
	public List<Integer> queryChild(List<StructureModel> stelist,Integer id,List<Integer> idsList){
		for(StructureModel str : stelist){
		if(str.getParent() != null){
			if(id.equals(str.getParent().getId())){
				idsList.add(str.getId());  
				queryChild(stelist,str.getId(),idsList);
			}
		}
		}
		return idsList;
	}

	@Override
	public int updateVisitStatus(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateVisitStatus(deviceManager);
	}

	@Override
	public int updateLostStatus(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateLostStatus(deviceManager);
	}
    
	@Override
	public int findBreakStatusCount(Integer orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findBreakStatusCount(map);
	}
	public int findIrrCount(Integer orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findIrrCount(map);
	}

	@Override
	public int findNotMonitoredCount(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findNotMonitoredCount(map);
	}

	@Override
	public int findTrusteeshipCount(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findTrusteeshipCount(map);
	}

	/*@Override
	public int findNoPasswordCount(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findNoPasswordCount(map);
	}*/

	@Override
	public int findDeviceLostCount(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findDeviceLostCount(map);
	}

	@Override
	public int findCancellationCount(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findCancellationCount(map);
	}

	@Override
	public int findMonitoredCount(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findMonitoredCount(map);
	}

	@Override
	public int updateDeviceStatus(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateDeviceStatus(deviceManager);
	}

	@Override
	public int updateIsActive(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateIsActive(deviceManager);
	}

	@Override
	public int deleteDevice(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.deleteDevice(deviceManager);
	}

	@Override
	public AndroidDevicePolicy findPolicyByName(Integer orgId, String name) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("name", name);
		return androidDevicePolicyDao.findPolicyByMap(map);
	}
	@Override
	public DeviceManagerModel queryDevice(String sn,Integer userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sn",sn);
		map.put("userId",userId);
		return deviceManagerDao.queryDevice(map);
	}
	
	@Override
	public DeviceManagerModel queryDeviceAndUserInfo(Integer id) {
		
		return deviceManagerDao.queryDeviceAndUserInfo(id);
	}

	@Override
	public List<DeviceManagerModel> findAllByOrgID(int orgid, List<Integer> idlist) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgid);
		map.put("idlist", idlist);
		return deviceManagerDao.findAllByOrgID(map);
	}

	@Override
	public List<DeviceManagerModel> queryIsTakeOff() {
		
		return deviceManagerDao.queryIsTakeOff();
	}

	@Override
	public String findUdidById(int uid) {
		return deviceManagerDao.findUdidById(uid);
	}

	@Override
	public Map<String, Object> findLongitudeAndLatitude(int did) {
		deviceManagerDao.findUdidById(did);
		return null;
	}

	@Override
	public int deleteDeviceBySn(String sn) {
        DeviceManagerModel model = deviceManagerDao.queryDeviceInfoBySn(sn);
        DeviceManagerModel device = deviceManagerDao.queryDeviceAllInfo(model.getId());
        deletedDeviceDao.insertDevice(device);
		return deviceManagerDao.deleteDeviceBySn(sn);
	}

	@Override
	public DeviceManagerModel queryDeviceAllInfo(Integer id) {
		
		return deviceManagerDao.queryDeviceAllInfo(id);
	}

	@Override
	public DeviceManagerModel queryUserId(Integer deviceId) {
		
		return deviceManagerDao.queryUserId(deviceId);
	}

	@Override
	public List<DeviceManagerModel> queryAllDeviceList(Integer id, Integer orgId, List<StructureModel> stelist) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Integer> idsList = new ArrayList<Integer>();
		if(id == null){
			for(StructureModel s : stelist){
				if(s.getParent() == null){
					id = s.getId();
				}
			}
		}
		List<Integer> idList = queryChild(stelist,id,idsList);
		if(id != null){
			idList.add(id);
		}
		map.put("idList",idList);
		map.put("orgId", orgId);
		List<DeviceManagerModel> list = deviceManagerDao.queryAllDeviceList(map);
		return list;
	}

	@Override
	public int updateVisitStatusAll(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateVisitStatusAll(deviceManager);
	}

	@Override
	public int updateDeviceStatusAll(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateDeviceStatusAll(deviceManager);
	}

	@Override
	public int updateIsActiveAll(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateIsActiveAll(deviceManager);
	}

	@Override
	public int deleteAllDevice(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.deleteAllDevice(deviceManager);
	}

	@Override
	public int updateLostStatusAll(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateLostStatusAll(deviceManager);
	}

	@Override
	public int updateEnableUnbund(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateEnableUnbund(deviceManager);
	}

	@Override
	public int updateEnableUnbundAll(DeviceManagerModel deviceManager) {
		
		return deviceManagerDao.updateEnableUnbundAll(deviceManager);
	}

	@Override
	public List<DeviceManagerModel> queryAllDeviceById(Integer userId) {
		
		return deviceManagerDao.queryAllDeviceById(userId);
	}

	@Override
	public int insertDeviceLocation(DeviceLocationModel deviceLocation) {
		
		return deviceLocationRecoedDao.insertDeviceLocation(deviceLocation);
	}

	@Override
	public DeviceManagerModel queryDeviceInfoBySn(String sn) {
		
		return deviceManagerDao.queryDeviceInfoBySn(sn);
	}

	@Override
	public DeviceLocationModel queryDeviceLocation(Integer deviceId) {
	
		return deviceLocationRecoedDao.queryDeviceLocation(deviceId);
	}

	@Override
	public List<DeviceManagerModel> queryDeviceExport(Integer id, Integer orgId, Map<String, Object> map,List<StructureModel> stelist) {
		List<Integer> idsList = new ArrayList<Integer>();
//		List<StructureModel> stelist=(List<StructureModel>) structureDao.findAllByOrgID(orgId);
		if(id == null){
			for(StructureModel s : stelist){
				if(s.getParent() == null){
					id = s.getId();
				}
			}
		}
		List<Integer> idList = queryChild(stelist,id,idsList);
		if(id != null){
			idList.add(id);
		}
        // 动态获得托管时间
		SystemParamSetModel param=paramSettingDao.querySysParamSetting();
        // 分钟*60=时间戳
        Integer outtime=Integer.parseInt(param.getOutManagerTime().toString())*60;
        map.put("idList",idList);
		map.put("orgId", orgId);
		map.put("outtime", outtime);
		List<DeviceManagerModel> list = deviceManagerDao.queryDeviceExport(map);
		for(DeviceManagerModel deviceManager : list){
			if(deviceManager.getDevice_status() == 1){
				deviceManager.setDeviceStatus("1");
			}else{
				if(((float)(new Date().getTime() - deviceManager.getLast_collection_time().getTime())/60000) > (float)param.getOutManagerTime()){
                    // 1表示注销中
                    deviceManager.setDeviceStatus("2"); // 托管中
				}else{
					if(deviceManager.getIsActive().equals("0")){
                        deviceManager.setDeviceStatus("3"); // 待监控
					}else{
                        deviceManager.setDeviceStatus("4"); // 监控中
					}
				}
			}
			}
		return list;
	}

	@Override
	public DeviceManagerModel queryIosIsExists(String udid) {
		
		return deviceManagerDao.queryIosIsExists(udid);
	}

	@Override
	public DeviceManagerModel queryDeviceInfoById(Integer id) {
		
		return deviceManagerDao.queryDeviceInfoById(id);
	}

	@Override
	public DeviceIosBasicInfoModel queryIosDeviceBasicInfo(String iosUuid) {
		
		return deviceManagerDao.queryIosDeviceBasicInfo(iosUuid);
	}

	@Override
	public int deleteDeviceByUdid(String udid) {
		
		return deviceManagerDao.deleteDeviceByUdid(udid);
	}

    @Override
    public int findAllDeviceCount(int orgid, List<Integer> idlist) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", orgid);
        map.put("idlist", idlist);
        return deviceManagerDao.findAllDeviceCount(map);
    }

    @Override
    public void insertDevice(DeviceManagerModel model) {
        deletedDeviceDao.insertDevice(model);
    }
 
}
