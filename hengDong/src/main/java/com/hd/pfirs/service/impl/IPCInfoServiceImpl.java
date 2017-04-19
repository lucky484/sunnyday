package com.hd.pfirs.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao.IPCInfoDao;
import com.hd.pfirs.model.DeviceModel;
import com.hd.pfirs.model.IPCModel;
import com.hd.pfirs.service.IPCInfoService;
import com.hd.pfirs.util.CommUtil;

/**
 * 前端采集类的实现
 * 
 * @author ligang.yang
 *
 */
@Service
public class IPCInfoServiceImpl implements IPCInfoService {

	@Autowired
	private IPCInfoDao dao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveIPCInfo(Map<String, Object> paramsMap) {
		if (paramsMap != null && !paramsMap.isEmpty()) {
			paramsMap.put("MonitID", CommUtil.getPrimaryKey());
			// String deviceCode = (String) paramsMap.get("DeviceCode");
			// int num = dao.queryIPCInfoByDeviceCode(deviceCode);
			// if (num != 0) {
			// dao.updateIPCInfo(paramsMap);
			// } else {
			dao.saveIPCInfo(paramsMap);
			// }
		}
	}

	@Override
	public List<IPCModel> queryIPCInfo(String deviceCode, String collectSite) {
		return dao.queryIPCInfo(deviceCode, collectSite);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DeviceModel> queryDeviceInfo(String deviceCode, String collectSite) {
		return dao.queryDeviceInfo(deviceCode, collectSite);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void insertEquip(DeviceModel model) {
		model.setDeviceId(CommUtil.getPrimaryKey());
		String deviceCode = model.getDeviceCode();
		dao.insertEquip(model);
		// 如果有设备号新增一条运行状态监控
		dao.insertIPCBaseInfo(CommUtil.getPrimaryKey(), model.getDeviceCode());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEquip(DeviceModel model) {
		dao.updateEquip(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEquip(String deviceId, String deleteStatus, String updateName) {
		dao.deleteEquip(deviceId, deleteStatus, updateName);
	}

	/**
	  * {@inheritDoc} 
	  */
	@Override
	public List<String> queryDeviceCodes() {
		return dao.queryDeviceCodes();
	}

	/**
	  * {@inheritDoc} 
	  */
	@Override
	public String queryDeviceCodeById(String deviceId) {
		return dao.queryDeviceCodeById(deviceId);
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public List<Map<String, String>> queryRegions() {
		return dao.queryRegions();
	}

	@Override
	public int queryDevicesByRegionCode(String regionCode)
	{
		return dao.queryDevicesByRegionCode(regionCode);
	};
}
