package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceLegalListDao;
import com.softtek.mdm.model.DeviceLegalListModel;
import com.softtek.mdm.model.DeviceRuleOperationItemRelationModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceLegalListService;
import com.softtek.mdm.service.DeviceRuleOperationItemRelationService;
import com.softtek.mdm.service.DeviceRuleService;

@Service
public class DeviceLegalListServiceImpl implements DeviceLegalListService {

	@Autowired
	private DeviceLegalListDao deviceLegalListDao;
	@Autowired
	private DeviceRuleOperationItemRelationService deviceRuleOperationItemRelationService;
	@Autowired
	private DeviceRuleService deviceRuleService;
	
	@Override
	public Page findWithPagation(Integer rid, Integer start, Integer length) {
		start=start==null?0:start;
		length=length==null?10:length;
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("rid", rid);
		map.put("pageNum", start);
		map.put("pageSize", length);
		List<DeviceRuleOperationItemRelationModel> operationList=deviceRuleOperationItemRelationService.findAllByRuleId(rid);
		List<DeviceLegalListModel> list= deviceLegalListDao.findWithMap(map);
		if(list!=null&&list.size()>0){
			for(int k=0;k<list.size();k++){
				list.get(k).setOperation(operationList);
			}
		}
		Page page=new Page();
		int totals=deviceLegalListDao.findCountByRuleId(rid);
		page.setData(list);
		page.setRecordsFiltered(totals);
		page.setRecordsTotal(totals);
		return page;
	}

	@Override
	public int save(DeviceLegalListModel entity) {
		return deviceLegalListDao.save(entity);
	}

	@Override
	public Page findCompliantWithDeviceId(Integer did, Integer start, Integer length) {
		
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("did", did);
		map.put("pageNum", start);
		map.put("pageSize", length);
		List<DeviceLegalListModel> list= deviceLegalListDao.findCompliantListWithDeviceId(map);
		List<DeviceRuleOperationItemRelationModel> operationList = null;
		if(list!=null && list.size()>0){
			for(DeviceLegalListModel deviceLegalList:list){
				if(deviceLegalList.getDeviceRule()!=null){
					operationList=deviceRuleOperationItemRelationService.findAllByRuleId(deviceLegalList.getDeviceRule().getId());
					deviceLegalList.setOperation(operationList);
				}
			}
		}
		Page page=new Page();
		int totals=deviceLegalListDao.findCompliantCountByDeviceId(did);
		page.setData(list);
		page.setRecordsFiltered(totals);
		page.setRecordsTotal(totals);
		return page;
		
	}

	@Override
	public Page findIllegalListWithDeviceId(Integer did, Integer start, Integer length) {
		Map<String, Object> map=new HashMap<String,Object>();
		if(did!=null){
			List<Integer> rids=deviceRuleService.findRuleIdsByDeviceId(did);
			if(rids!=null&&rids.size()>0){
				map.put("did", did);
				map.put("rids", rids);
				map.put("pageNum", start);
				map.put("pageSize", length);
				List<DeviceLegalListModel> legals=deviceLegalListDao.findOneWithMap(map);
				if(legals!=null&&legals.size()>0){
					for (int i=0;i<legals.size();i++) {
						List<DeviceRuleOperationItemRelationModel> operationList=deviceRuleOperationItemRelationService.findAllByRuleId(legals.get(i).getDeviceRule().getId());
						if(operationList!=null){
							legals.get(i).setOperation(operationList);
						}
					}
				}
				Page page=new Page();
				page.setData(legals);
				int total=deviceLegalListDao.findOneCountWithMap(map);
				page.setRecordsFiltered(total);
				page.setRecordsTotal(total);
				return page;
			}else{
				Page page=new Page();
				page.setData(new ArrayList<DeviceLegalListModel>());
				page.setRecordsFiltered(0);
				page.setRecordsTotal(0);
				return page;
			}
			
		}else{
			Page page=new Page();
			page.setData(new ArrayList<DeviceLegalListModel>());
			page.setRecordsFiltered(0);
			page.setRecordsTotal(0);
			return page;
		}
		
	}

	@Override
	public int deleteRecordsByRuleIdAndDeviceId(Integer rid, Integer did) {
		if(rid!=null&&did!=null){
			return deviceLegalListDao.deleteRecordsByRuleIdAndDeviceId(rid, did);
		}
		return 0;
	}

}
