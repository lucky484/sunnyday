package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleOperationItemRecordDao;
import com.softtek.mdm.dao.DeviceRuleOperationItemRelationDao;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.service.DeviceRuleOperationItemRelationService;
@Service
public class DeviceRuleOperationItemRelationServiceImpl implements DeviceRuleOperationItemRelationService {

	@Autowired
	private DeviceRuleOperationItemRelationDao  deviceRuleOperationItemRelationDao;
	@Autowired
	private DeviceRuleOperationItemRecordDao deviceRuleOperationItemRecordDao;
	
	@Override
	public int save(DeviceRuleOperationItemRelationModel entity) {
		return deviceRuleOperationItemRelationDao.save(entity);
	}
	@Override
	public List<DeviceRuleOperationItemRelationModel> findAllByRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleOperationItemRelationDao.findAllByRuleId(rid);
		}
		return null;
	}
	@Override
	public int truncateWithRuleId(Integer rid) {
		if(rid!=null){
			List<DeviceRuleOperationItemRelationModel> list=this.findAllByRuleId(rid);
			List<Integer> ids=new ArrayList<Integer>();
			if(list!=null&&list.size()>0){
				for (DeviceRuleOperationItemRelationModel droir : list) {
					ids.add(droir.getDeviceRuleOperationRecord().getId());
				}
				deviceRuleOperationItemRecordDao.truncateWithIds(ids);
			}
			return deviceRuleOperationItemRelationDao.truncateWithRuleId(rid);
		}
		return 0;
	}

}
