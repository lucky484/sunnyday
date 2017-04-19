package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceAppInfoDao;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceAppInfoService;

@Service
public class DeviceAppInfoServiceImpl implements DeviceAppInfoService{

	@Autowired
	private DeviceAppInfoDao deviceAppInfoDao;

	@Override
	public Page findByPage(Integer did, Integer start, Integer length) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("did", did);
		map.put("pageNum", start);
		map.put("pageSize", length);
		Page page=new Page();
		Integer count=deviceAppInfoDao.findCountByDid(did);
		page.setData((List<DeviceAppInfoModel>) deviceAppInfoDao.findByPage(map));
		page.setRecordsTotal(count);
		page.setRecordsFiltered(count);
		return page;
	}

	@Override
	public int save(DeviceAppInfoModel entity) {
		return deviceAppInfoDao.save(entity);
	}

	@Override
	public int truncateWithDeviceId(Integer did) {
		if(did!=null)
		{
			return deviceAppInfoDao.truncateWithDeviceId(did);
		}
		return 0;
	}
	

}
