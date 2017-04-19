package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceStatisticsModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.util.DataGridModel;

public interface DeviceBasicInfoDao extends CrudMapper<DeviceBasicInfoModel, Integer> {
	
	/**
     * 根据用户的id查询用户所拥有的设备的数目
     * 
     * @param entity
     * @return
     */
	Integer findCountByUser(UserModel entity);
	
	/**
     * 根据用户的id和sn号查询用户所拥有的非当前sn的设备数目
     * 
     * @param entity
     * @return
     */
	Integer  findCountWithoutCurrentByUser(UserModel entity);
	
	/**
     * 根据用户id查询该用户所拥有的设备基本信息列表
     * 
     * @param uid
     * @return
     */
	List<DeviceBasicInfoModel> findByUserId(Integer uid);
	
	/**
     * 根据策略id获取已分配的设备类表
     * 
     * @param params
     * @return
     */
	List<DeviceBasicInfoModel> getDeviceListByPolicy(DataGridModel params);
	
	/**
     * 根据策略id获取已分配的设备类表
     * 
     * @param params
     * @return
     */
	List<DeviceBasicInfoModel> getIosDeviceListByPolicy(DataGridModel params);
	
	/**
     * 根据策略id获取已分配的设备数
     * 
     * @param params
     * @return
     */
	int getDeviceSizeByPolicy(DataGridModel params);
	
	/**
     * 根据策略id获取已分配的Ios设备数
     * 
     * @param params
     * @return
     */
	int getDeviceIosSizeByPolicy(DataGridModel params);
	
	/**
     * 根据多用户的id集合查询所属的设备数目
     * 
     * @param ids
     * @return
     */
	int findCountByUsers(Map<String,Object> map);
	
	/**
     * 根据多用户id查找所属的设备id
     * 
     * @param ids
     * @return
     */
	List<Integer> findDeviceIdsByUserIds(List<Integer> ids);

	/**
	 * 根据用户列表id查询device token
	 * @param ids:用户id列表
	 * @return 返回device token
	 */
	List<String> findDeviceTokenListByUserIds(List<Integer> ids);
	/**
	 * 根据用户的id集合，查询用户所拥有的udids
	 * @param ids
	 * @param deviceType "ios","android",null(表示全部)
	 * @return
	 */
	List<String> findUdidsByUserIds(@Param(value="list") List<Integer> list,@Param(value="deviceType") String deviceType);
	
	/**
     * 查询该记录是否已经存在
     * 
     * @param id
     * @param device_name
     * @return
     */
	int isExists(Integer id, String device_name);
	
	DeviceBasicInfoModel findByUdid(String udid);
	
	int trucateWithUserId(Integer userId);
	
	int trucateWithUserIds(List<Integer> userIds);
	
	/**
	 * 
	 * @param orgId
	 * @param isAndroid 1 android 0 ios
	 * @return
	 */
	int findCountByOrgId(Map<String,Object> map);

	List<DeviceBasicInfoModel> findAllByOrgID(Integer id);

	DeviceBasicInfoModel findOneWithNetInfo(Integer id);
	
	int findCountByDepartIds(Map<String, Object> map);

	void updateLongiAndLati(Map<String, Object> map);
	
	DeviceBasicInfoModel queryDeviceLocation(Integer id);
	
	DeviceBasicInfoModel findBySN(String sn);

	List<DeviceBasicInfoModel> findListsByOrgId(Integer org_id);

	Integer getDeviceCountByMap(Map<String, Object> paramMap);

	List<DeviceStatisticsModel> getDeviceListsByMap(Map<String, Object> paramMap);

	/**
     * 根据其实结束时间map获得对应设备统计数据
     * 
     * @param paramMap
     * @return
     */
    List<DeviceStatisticsModel> getExportDeviceListsByMap(Map<String, Object> paramMap);

	void autoBackupDevice();

	List<DeviceBasicInfoModel> getDevicesByUserId(Integer userId);
	
	int updateIosDevice(DeviceBasicInfoModel deviceBasicInfo);
	
	DeviceBasicInfoModel queryDeviceInfoByUdid(String udid);
	
	int updateDeviceStatusByUdid(DeviceBasicInfoModel deviceBasicInfo);
	
	DeviceBasicInfoModel findByIosUuid(String iosUuid);

    List<DeviceStatisticsModel> getChartDeviceListsByMap(Map<String, Object> paramMap);
    
    DeviceStatisticsModel getTodayDeviceByMap(Map<String, Object> paramMap);

	/**
     * 根据用户id和iosuuid查询其他绑定的设备
     * 
     * @param user
     * @return
     */
    int findCountWithoutCurrentByUserAndIosUuid(UserModel user);

    void deleteDeviceStatisJob();
    
    /**
     * 查找需要更新的设备信息
     * @param map
     * |-version 当前的版本
     * |-orgIds List集合
     * |-platform 可以为null,表示不区分ios或者安卓
     * @return
     */
    List<DeviceBasicInfoModel> findNeedUpdate(Map<String, Object> map);
    
    /**
     * 检测用户的描述文件是否属于当前用户
     * @param userId 用户编号
     * @param udid MDM server 分配的udid
     * @return >0合法，0 不合法
     */
    int checkIosRight(Integer userId, String udid);
}


