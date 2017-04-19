package com.softtek.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleUserDao;
import com.softtek.mdm.model.DeviceRuleUserModel;
import com.softtek.mdm.service.DeviceRuleUserService;
@Service
public class DeviceRuleUserServiceImpl implements DeviceRuleUserService {

	@Autowired
	private DeviceRuleUserDao deviceRuleUserDao;
	@Override
	public int save(DeviceRuleUserModel entity) {
		return deviceRuleUserDao.save(entity);
	}
	@Override
	public List<DeviceRuleUserModel> findAllByRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleUserDao.findAllByRuleId(rid);
		}
		return null;
	}
	@Override
	public int truncateWithRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleUserDao.truncateWithRuleId(rid);
		}
		return 0;
	}

}
