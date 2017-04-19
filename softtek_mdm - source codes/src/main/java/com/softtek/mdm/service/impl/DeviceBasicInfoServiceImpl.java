package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceLegalListDao;
import com.softtek.mdm.dao.ParamSettingDao;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.DeviceNetworkStatusModel;
import com.softtek.mdm.model.SystemParamSetModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.DeviceAppInfoService;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.DeviceNetworkStatusService;

@Service
public class DeviceBasicInfoServiceImpl implements DeviceBasicInfoService {

	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	@Autowired
	private DeviceLegalListDao deviceLegalListDao;
	@Autowired
	private DeviceAppInfoService deviceAppInfoService;
	@Autowired
	private DeviceNetworkStatusService deviceNetworkStatusService;
	
	@Autowired
	private ParamSettingDao paramSettingDao;
	
	@Override
	public Integer findCountByUser(UserModel entity) {
		return deviceBasicInfoDao.findCountByUser(entity);
	}
	
	/**
	 * 保存用户设备基本信息
	 * @param entity
	 * @return
	 */
	@Override
	public Integer save(DeviceBasicInfoModel entity){
		return deviceBasicInfoDao.save(entity);
	}

	@Override
	public List<DeviceBasicInfoModel> findByUserId(Integer uid) {
		SystemParamSetModel systemParamSet = paramSettingDao.querySysParamSetting();
		List<DeviceBasicInfoModel> list = deviceBasicInfoDao.findByUserId(uid);
		for(DeviceBasicInfoModel deviceBasicInfoModel : list){
			if(deviceBasicInfoModel.getDevice_status()!=null){
				if(deviceBasicInfoModel.getDevice_type().equals("android")){
					if(deviceBasicInfoModel.getDevice_status().equals("1")){
						deviceBasicInfoModel.setDevice_status("1");
					}else{
						if(deviceBasicInfoModel.getTime() > Float.valueOf(systemParamSet.getOutManagerTime())){
							//1表示注销中 
							deviceBasicInfoModel.setDevice_status("2"); //托管中
						}else{
							if(deviceBasicInfoModel.getIsActive().equals("0")){
								deviceBasicInfoModel.setDevice_status("3");   //待监控
							}else{
								deviceBasicInfoModel.setDevice_status("4");   //监控中
							}
							//判断设备的丢失状态,只有丢失中的状态，无找回中的状态
							if(deviceBasicInfoModel.getLost_status()!=null){
								if(deviceBasicInfoModel.getLost_status()==1){
									deviceBasicInfoModel.setDevice_status("5");
								}else{
									deviceBasicInfoModel.setDevice_status("6");
								}
							}
						}
					}
				}else{
					//若为ios设备，则判断是否丢失状态，其他不做判断
					if(deviceBasicInfoModel.getLost_status()!=null){
						if(deviceBasicInfoModel.getLost_status()==1){
							deviceBasicInfoModel.setDevice_status("5");
						}else{
							deviceBasicInfoModel.setDevice_status("6");
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public DeviceBasicInfoModel findOne(Integer id) {
		DeviceBasicInfoModel deviceBasicInfoModel = deviceBasicInfoDao.findOneWithNetInfo(id);
		DeviceNetworkStatusModel deviceNetworkStatus = deviceBasicInfoModel.getDeviceNetworkStatus();
		SystemParamSetModel systemParamSet = paramSettingDao.querySysParamSetting();
		//if("关闭".equals(deviceNetworkStatus.getData_roam())){
			//deviceNetworkStatus.setData_roam("0");
		//}else{
			//deviceNetworkStatus.setData_roam("1");
		//}
		deviceBasicInfoModel.setDeviceNetworkStatus(deviceNetworkStatus);
		
		//这边是判断设备的状态，主要是用了数据库中的 device_status，is_active,lost_status 字段
		/**
		 * 判断过程是 如果设备的device_status == 1,那么设备的状态就是注销中，否则如果设备的is_active字段值为0就是待监控，如果为1就是监控中，
		 */
		if(deviceBasicInfoModel.getDevice_status()!=null){
			if(deviceBasicInfoModel.getDevice_type().equals("android")){
				if(deviceBasicInfoModel.getDevice_status().equals("1")){
					deviceBasicInfoModel.setDevice_status("1");
				}else{
					if(deviceBasicInfoModel.getTime() > Float.valueOf(systemParamSet.getOutManagerTime())){
						//1表示注销中 
						deviceBasicInfoModel.setDevice_status("2"); //托管中
					}else{
						if(deviceBasicInfoModel.getIsActive().equals("0")){
							deviceBasicInfoModel.setDevice_status("3");   //待监控
						}else{
							deviceBasicInfoModel.setDevice_status("4");   //监控中
						}
						//判断设备的丢失状态,只有丢失中的状态，无找回中的状态
						if(deviceBasicInfoModel.getLost_status()!=null){
							if(deviceBasicInfoModel.getLost_status()==1){
								deviceBasicInfoModel.setDevice_status("5");
							}else{
								deviceBasicInfoModel.setDevice_status("6");
							}
						}
					}
				}
			}else{
				//若为ios设备，则判断是否丢失状态，其他不做判断
				if(deviceBasicInfoModel.getLost_status()!=null){
					if(deviceBasicInfoModel.getLost_status()==1){
						deviceBasicInfoModel.setDevice_status("5");
					}else{
						deviceBasicInfoModel.setDevice_status("6");
					}
				}
			}
		}
		
		DeviceLegalListModel deviceLegalList = deviceLegalListDao.getDeviceLegalListById(deviceBasicInfoModel.getId());
		deviceBasicInfoModel.setDeviceLegalListModel(deviceLegalList);
		return deviceBasicInfoModel;
	}

	@Override
	public int findCountBySteId(List<Integer> idList,Integer orgId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("orgId", orgId);
		int count = deviceBasicInfoDao.findCountByUsers(map);
		return count;
	}

//	@Override
//	public int findCountByUsers(List<Integer> ids) {
//		if(ids!=null&&ids.size()>0){
//			return deviceBasicInfoDao.findCountByUsers(ids);
//		}
//		return 0;
//	}

	@Override
	public DeviceBasicInfoModel findByUdid(String udid) {
		return deviceBasicInfoDao.findByUdid(udid);
	}

	@Override
	public int update(DeviceBasicInfoModel deviceBasicInfo) {
		return deviceBasicInfoDao.update(deviceBasicInfo);
	}

	@Override
	public int trucateWithUserId(Integer userId) {
		
		if(userId!=null){
			List<DeviceBasicInfoModel> basicInfoList=deviceBasicInfoDao.findByUserId(userId);
			if(basicInfoList!=null&&basicInfoList.size()>0){
				for (DeviceBasicInfoModel basic : basicInfoList) {
					//=============================
					//删除设备应用
					deviceAppInfoService.truncateWithDeviceId(basic.getId());
					//删除网络信息
					deviceNetworkStatusService.truncateWithDeviceId(basic.getId());
				}
			}
			
			//删除基本信息
			return deviceBasicInfoDao.trucateWithUserId(userId);
		}
		return 0;
		
	}

	@Override
	public int trucateWithUserIds(List<Integer> userIds) {
		if(userIds!=null&&userIds.size()>0){
			for (Integer uid : userIds) {
				if(uid!=null){
					this.trucateWithUserId(uid);
				}
				
			}
			return userIds.size();
		}
		return 0;
	}

	@Override
	public int findCountByOrgId(Integer orgId, Integer isAndroid,List<Integer> idlist) {
		if(orgId!=null&&isAndroid!=null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orgId", orgId);
			map.put("isAndroid", isAndroid);
			map.put("idlist",idlist); 
			return deviceBasicInfoDao.findCountByOrgId(map);
		}
		return 0;
	}

	@Override
	public int findCountByDepartIds(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<Integer> dpids=(List<Integer>) map.get("ids");
		if(dpids!=null&&dpids.size()>0){
			return deviceBasicInfoDao.findCountByDepartIds(map);
		}
		return 0;
	}

	@Override
	public void updateLongiAndLati(String longitude, String latitude,String sn) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		map.put("sn", sn);
	    deviceBasicInfoDao.updateLongiAndLati(map);
	}

	@Override
	public DeviceBasicInfoModel queryDeviceLocation(Integer id) {
		
		return deviceBasicInfoDao.queryDeviceLocation(id);
	}

	@Override
	public DeviceBasicInfoModel findBySN(String sn) {
		if(null!=sn){
			return deviceBasicInfoDao.findBySN(sn);
		}
		return null;
	}

	@Override
	public List<DeviceBasicInfoModel> findListsByOrgId(Integer org_id) {
		
		return deviceBasicInfoDao.findListsByOrgId(org_id);
	}

	@Override
	public Integer findCountWithoutCurrentByUser(UserModel entity) {
		return deviceBasicInfoDao.findCountWithoutCurrentByUser(entity);
	}

	@Override
	public int updateIosDevice(DeviceBasicInfoModel deviceBasicInfo) {
		
		return deviceBasicInfoDao.updateIosDevice(deviceBasicInfo);
	}

	@Override
	public DeviceBasicInfoModel findByIosUuid(String iosUuid) {
		
		return deviceBasicInfoDao.findByIosUuid(iosUuid);
	}

	@Override
	public int findCountWithoutCurrentByUserAndIosUuid(UserModel user) {
		
		return deviceBasicInfoDao.findCountWithoutCurrentByUserAndIosUuid(user);
	}
	
	
}
