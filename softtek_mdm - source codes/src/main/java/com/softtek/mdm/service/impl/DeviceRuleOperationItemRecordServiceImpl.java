package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleOperationItemRecordDao;
import com.softtek.mdm.model.DeviceRuleOperationItemRecordModel;
import com.softtek.mdm.service.DeviceRuleOperationItemRecordService;

@Service
public class DeviceRuleOperationItemRecordServiceImpl implements DeviceRuleOperationItemRecordService {

	@Autowired
	private DeviceRuleOperationItemRecordDao deviceRuleOperationItemRecordDao;
	@Override
	public int save(DeviceRuleOperationItemRecordModel entity) {
		return deviceRuleOperationItemRecordDao.save(entity);
	}

}
