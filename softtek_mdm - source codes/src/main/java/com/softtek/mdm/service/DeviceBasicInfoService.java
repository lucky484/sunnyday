package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.UserModel;

public interface DeviceBasicInfoService {

	/**
	 * 根据用户的id查询用户所拥有的设备的数目
	 * @param entity
	 * @return
	 */
	Integer findCountByUser(UserModel entity);
	
	/**
	 * 根据用户的id和sn号查询用户所拥有的非当前sn的设备数目
	 * @param entity
	 * @return
	 */
	Integer  findCountWithoutCurrentByUser(UserModel entity);
	/**
	 * 保存用户设备基本信息
	 * @param entity
	 * @return
	 */
	Integer save(DeviceBasicInfoModel entity);
	
	/**
	 * 根据用户id查询该用户所拥有的设备基本信息列表
	 * @param uid
	 * @return
	 */
	List<DeviceBasicInfoModel> findByUserId(Integer uid);
	
	/**
	 * 查找具体的某个设备信息
	 * @param id
	 * @return
	 */
	DeviceBasicInfoModel findOne(Integer id);
	
	/**
	 * 根据部门id集合查询所属的设备数目
	 * @param ids
	 * @return
	 */
	int findCountBySteId(List<Integer> idList,Integer orgId);
	
	/**
	 * 根据用户编号集合查询所拥有的设备数目
	 * @param ids
	 * @return
	 */
//	int findCountByUsers(List<Integer> ids);
	
	/**
	 * 根据department id 查询所属设备数目
	 * @param dpids
	 * @return
	 */
	int findCountByDepartIds(Map<String, Object> map);
	
	DeviceBasicInfoModel findByUdid(String udid);
	
	int update(DeviceBasicInfoModel deviceBasicInfo);
	
	int trucateWithUserId(Integer userId);
	
	int trucateWithUserIds(List<Integer> userIds);
	
	int findCountByOrgId(Integer orgId,Integer isAndroid,List<Integer> idlist);
	
	void updateLongiAndLati(String longitude, String latitude,String sn);
	
	DeviceBasicInfoModel queryDeviceLocation(Integer id);
	
	DeviceBasicInfoModel findBySN(String sn);
	
	List<DeviceBasicInfoModel> findListsByOrgId(Integer org_id);
	
	int updateIosDevice(DeviceBasicInfoModel deviceBasicInfo);
	
	DeviceBasicInfoModel findByIosUuid(String iosUuid);
	
	int findCountWithoutCurrentByUserAndIosUuid(UserModel user);
}
