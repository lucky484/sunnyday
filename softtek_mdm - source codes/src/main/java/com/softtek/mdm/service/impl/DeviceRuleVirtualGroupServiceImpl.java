package com.softtek.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleVirtualGroupDao;
import com.softtek.mdm.model.DeviceRuleVirtualGroupModel;
import com.softtek.mdm.service.DeviceRuleVirtualGroupService;
@Service
public class DeviceRuleVirtualGroupServiceImpl implements DeviceRuleVirtualGroupService {

	@Autowired
	private DeviceRuleVirtualGroupDao deviceRuleVirtualGroupDao;
	@Override
	public int save(DeviceRuleVirtualGroupModel entity) {
		return deviceRuleVirtualGroupDao.save(entity);
	}
	
	@Override
	public List<DeviceRuleVirtualGroupModel> findAllByRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleVirtualGroupDao.findAllByRuleId(rid);
		}
		return null;
		
	}

	@Override
	public int truncateWithRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleVirtualGroupDao.truncateWithRuleId(rid);
		}
		return 0;
	}

}
