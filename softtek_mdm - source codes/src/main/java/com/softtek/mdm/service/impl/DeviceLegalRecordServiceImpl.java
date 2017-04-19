package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceLegalRecordDao;
import com.softtek.mdm.model.DeviceLegalRecordModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceLegalRecordService;

@Service
public class DeviceLegalRecordServiceImpl implements DeviceLegalRecordService {

	@Autowired
	private DeviceLegalRecordDao deviceLegalRecordDao;
	
	@Override
	public int save(DeviceLegalRecordModel entity) {
		return deviceLegalRecordDao.save(entity);
	}

	@Override
	public Page findLegalRecordByRidAndDid(Integer rid, Integer did, Integer start, Integer length) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("rid", rid);
		map.put("did", did);
		map.put("pageNum", start==null?0:start);
		map.put("pageSize", length==null?10:length);
		List<DeviceLegalRecordModel> data=deviceLegalRecordDao.findByMap(map);
		Page page=new Page();
		int totals=deviceLegalRecordDao.findCountByRidAndDid(rid, did);
		page.setData(data);
		page.setRecordsFiltered(totals);
		page.setRecordsTotal(totals);
		return page;
	}

	@Override
	public int deleteWithPk(Integer id) {
		return deviceLegalRecordDao.deleteWithPk(id);
	}

	@Override
	public Page findLegalRecordByOrgId(Integer orgId, Integer start, Integer length) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("pageNum", start==null?0:start);
		map.put("pageSize", length==null?10:length);
		List<DeviceLegalRecordModel> data=deviceLegalRecordDao.findRecordByOrgIdMap(map);
		Page page=new Page();
		int totals=deviceLegalRecordDao.findCountByOrgId(orgId);
		page.setData(data);
		page.setRecordsFiltered(totals);
		page.setRecordsTotal(totals);
		return page;
	}

	@Override
	public Page findLegalRecordByDeviceId(Integer did, Integer start, Integer length) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(did!=null){
			map.put("did", did);
			map.put("pageNum", start==null?0:start);
			map.put("pageSize", length==null?10:length);
			
			List<DeviceLegalRecordModel> data=deviceLegalRecordDao.findHistoryByDeviceId(map);
			Page page=new Page();
			int totals=deviceLegalRecordDao.findLegalRecordCountByDeviceId(did);
			page.setData(data);
			page.setRecordsFiltered(totals);
			page.setRecordsTotal(totals);
			return page;
		}
		return null;
	}

	@Override
	public Page queryLegalRecordByParams(Map<String, Object> map)
	{
		List<DeviceLegalRecordModel> data=deviceLegalRecordDao.queryLegalRecordByParams(map);
		Page page=new Page();
		int totals=deviceLegalRecordDao.queryLegalRecCountByParams(map);
		page.setData(data);
		page.setRecordsFiltered(totals);
		page.setRecordsTotal(totals);
		return page;
	}

	@Override
	public int deleteRecordsByOrgId(Integer id) {
		return deviceLegalRecordDao.deleteRecordsByOrgId(id);
	}

}
