package com.softtek.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleDepartmentDao;
import com.softtek.mdm.model.DeviceRuleDepartmentModel;
import com.softtek.mdm.service.DeviceRuleDepartmentService;

@Service
public class DeviceRuleDepartmentServiceImpl implements DeviceRuleDepartmentService {

	@Autowired
	private DeviceRuleDepartmentDao deviceRuleDepartmentDao;
	@Override
	public int save(DeviceRuleDepartmentModel entity) {
		return deviceRuleDepartmentDao.save(entity);
	}
	
	@Override
	public List<DeviceRuleDepartmentModel> findAllByRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleDepartmentDao.findAllByRuleId(rid);
		}
		return null;
	}

	@Override
	public int truncateWithRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleDepartmentDao.truncateWithRuleId(rid);
		}
		return 0;
	}

}
