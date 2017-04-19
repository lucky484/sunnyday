/*
 * @Title: IPCInfoDao.java
 * @Package com.hd.pfirs.dao
 * @Description: 采集设备信息收集和展示的相关dao
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年1月15日 下午4:43:39
 * @version V1.0
 */
package com.hd.pfirs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.DeviceModel;
import com.hd.pfirs.model.IPCModel;

/**
  * @ClassName: IPCInfoDao
  * @Description: 采集设备信息收集和展示的相关dao
  * @author ligang.yang@softtek.com
  * @date 2016年1月15日 下午4:43:39
  *
  */
public interface IPCInfoDao {

	/**
	 * 保存采集设备信息
	 * 
	 * @param paramsMap
	 */
	public void saveIPCInfo(Map<String, Object> paramsMap);

	public List<IPCModel> queryIPCInfo(@Param(value = "deviceCode") String deviceCode,
			@Param(value = "collectSite") String collectSite);

	public int queryIPCInfoByDeviceCode(@Param(value = "deviceCode") String deviceCode);

	public void updateIPCInfo(Map<String, Object> paramsMap);

	/**
	  * 
	  * @Description: 采集设备信息查询dao
	  * @param (value = "deviceCode") String deviceCode, (value = "collectSite") String collectSite
	  * @return List<DeviceModel>
	  * @author: ligang.yang@softtek.com  
	  * @version: 2016年1月15日 下午4:40:51
	  */
	List<DeviceModel> queryDeviceInfo(@Param(value = "deviceCode") String deviceCode,
			@Param(value = "collectSite") String collectSite);

	/**
	 * 
	  * @Description: 插入一条设备信息记录
	  * @param      : DeviceModel model
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午4:14:23   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	void insertEquip(DeviceModel model);

	/**
	  * @Description: 插入一条运行状态监控基础信息
	  * @param      : 
	  * @return     : void
	  * @throws  
	  * @data       : 2016年2月3日 上午10:33:01   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	void insertIPCBaseInfo(@Param(value = "monitID") long monitID, @Param(value = "deviceCode") String deviceCode);

	/**
	 * 
	  * @Description: 更新一条已有的设备信息记录
	  * @param      : DeviceModel model
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午4:14:57   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	void updateEquip(DeviceModel model);

	/**
	 * 
	  * @Description: 更新一条设备信息为删除状态
	  * @param      : String deviceId,  String deleteStatus, String updateName
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午4:15:28   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	void deleteEquip(@Param(value = "deviceId") String deviceId, @Param(value = "deleteStatus") String deleteStatus,
			@Param(value = "updateName") String updateName);

	/**
	 * 
	  * @Description: 查询所有设备号
	  * @param      : 
	  * @return     : List<String>
	  * @throws  
	  * @data       : 2016年1月20日 下午6:01:54   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	List<String> queryDeviceCodes();

	/**
	 * 
	  * @Description: 根据设备id查设备号
	  * @param      : 
	  * @return     : String
	  * @throws  
	  * @data       : 2016年1月21日 上午11:01:16   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	String queryDeviceCodeById(@Param(value = "deviceId") String deviceId);

	/**
	  * @Description: 查询所有的辖区编码和辖区域名
	  * @param      : 
	  * @return     : Map<String,Object>
	  * @throws  
	  * @data       : 2016年2月1日 上午9:44:48   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	List<Map<String, String>> queryRegions();

	/**
	  * @Description: 根据辖区编号查询设备总数
	  * @param      : 
	  * @return     : int
	  * @throws  
	  * @data       : 2016年2月1日 上午9:46:17   
	  * @version:   : v1.0   
	  * @author     : brave.chen
	 */
	public int queryDevicesByRegionCode(@Param(value = "regionCode") String regionCode);
}
