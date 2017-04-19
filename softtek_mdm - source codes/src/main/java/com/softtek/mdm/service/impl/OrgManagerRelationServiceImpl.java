package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.ManagerDao;
import com.softtek.mdm.dao.OrgManagerRelationDao;
import com.softtek.mdm.model.OrgManagerRelationModel;
import com.softtek.mdm.service.OrgManagerRelationService;

@Service
public class OrgManagerRelationServiceImpl implements OrgManagerRelationService {

	@Autowired
	private OrgManagerRelationDao orgManagerRelationDao;
	@Autowired
	private ManagerDao managerDao;
	
	
	@Override
	public List<Integer> findManagerIdsByorgId(Integer orgId) {
		List<Integer> arrList=new ArrayList<>();
		if(orgId!=null){
			List<OrgManagerRelationModel> list=(List<OrgManagerRelationModel>) orgManagerRelationDao.findRecordsByOrgId(orgId);
			if(list!=null){
				for (OrgManagerRelationModel orgNR : list) {
					if(!arrList.contains(orgNR.getManager().getId())){
						arrList.add(orgNR.getManager().getId());
					}
				}
			}
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("orgId", orgId);
			if(arrList.size()>0){
				map.put("idList", arrList);
				List<Integer> mids=managerDao.findIdsByOrgIdAndCreatedBys(map);
				for (Integer id : mids) {
					if(!arrList.contains(id)){
						arrList.add(id);
					}
				}
			}
		}
		return arrList;
	}


	@Override
	public List<OrgManagerRelationModel> findRecordsByManagerId(Integer mid) {
		return (List<OrgManagerRelationModel>) orgManagerRelationDao.findRecordsByManagerId(mid);
	}

}
