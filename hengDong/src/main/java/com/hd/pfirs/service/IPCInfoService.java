package com.hd.pfirs.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.DeviceModel;
import com.hd.pfirs.model.IPCModel;

/**
 * 前端采集信息类
 * 
 * @author ligang.yang
 *
 */
public interface IPCInfoService {
	/**
	 * 保存前端采集信息
	 * 
	 * @param paramsMap
	 */
	void saveIPCInfo(Map<String, Object> paramsMap);

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	List<IPCModel> queryIPCInfo(String deviceCode, String collectSite);

	/**
	 * 
	  * @Description: 查询采集设备信息列表
	  * @param String deviceCode, String collectSite
	  * @return List<DeviceModel>
	  * @throws 
	  * @author: ligang.yang@softtek.com  
	  * @version: 2016年1月15日 下午4:37:34
	 */
	List<DeviceModel> queryDeviceInfo(String deviceCode, String collectSite);

	/**
	  * 
	  * @Description: 插入一条设备信息
	  * @param      : DeviceModel model
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午2:31:55   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	void insertEquip(DeviceModel model);

	/**
	  * 
	  * @Description: 更新一条已有的设备信息记录
	  * @param      : DeviceModel model
	  * @return     : void
	  * @throws  
	  * @data       : 2016年1月18日 下午2:31:55   
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
	  * @Description: 查询设备号
	  * @param      : 
	  * @return     : List<String>
	  * @throws  
	  * @data       : 2016年1月20日 下午5:57:53   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	List<String> queryDeviceCodes();

	/**
	 * 
	  * @Description: 根据设备id来查询设备号
	  * @param      : 
	  * @return     : String
	  * @throws  
	  * @data       : 2016年1月21日 上午10:59:19   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	String queryDeviceCodeById(@Param(value = "deviceId") String deviceId);

	/**
	  * @Description: 查询所有的辖区编码和辖区域名
	  * @param      : 
	  * @return     : Map<String,Object>
	  * @throws  
	  * @data       : 2016年2月1日 上午9:46:17   
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
	int queryDevicesByRegionCode(@Param(value = "regionCode") String regionCode);
}
