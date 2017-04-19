package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceRuleItemRecordDao;
import com.softtek.mdm.dao.DeviceRuleItemRelationDao;
import com.softtek.mdm.model.DeviceRuleItemRelationModel;
import com.softtek.mdm.service.DeviceRuleItemRelationService;

@Service
public class DeviceRuleItemRelationServiceImpl implements DeviceRuleItemRelationService {

	@Autowired
	private DeviceRuleItemRelationDao deviceRuleItemRelationDao;
	@Autowired
	private DeviceRuleItemRecordDao deviceRuleItemRecordDao;
	
	
	@Override
	public int save(DeviceRuleItemRelationModel entity) {
		return deviceRuleItemRelationDao.save(entity);
	}
	@Override
	public List<DeviceRuleItemRelationModel> findAllByRuleId(Integer rid) {
		if(rid!=null){
			return deviceRuleItemRelationDao.findAllByRuleId(rid);
		}
		return null;
	}
	@Override
	public int truncateWithRuleId(Integer rid) {
		if(rid!=null){
			List<DeviceRuleItemRelationModel> list=this.findAllByRuleId(rid);
			//删除items
			List<Integer> ids=new ArrayList<Integer>();
			if(list!=null&&list.size()>0){
				for (DeviceRuleItemRelationModel drir : list) {
					ids.add(drir.getDeviceRuleItemRecord().getId());
				}
				deviceRuleItemRecordDao.truncateWithIds(ids);
			}
			return deviceRuleItemRelationDao.truncateWithRuleId(rid);
		}
		return 0;
	}

}
