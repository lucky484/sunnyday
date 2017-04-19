package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleItemRecordDao;
import com.softtek.mdm.model.DeviceRuleItemRecordModel;
import com.softtek.mdm.service.DeviceRuleItemRecordService;

@Service
public class DeviceRuleItemRecordServiceImpl implements DeviceRuleItemRecordService {

	@Autowired
	private DeviceRuleItemRecordDao deviceRuleItemRecordDao; 
	@Override
	public int save(DeviceRuleItemRecordModel entity) {
		return deviceRuleItemRecordDao.save(entity);
	}

}
