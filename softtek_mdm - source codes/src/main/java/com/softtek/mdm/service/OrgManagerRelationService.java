package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.OrgManagerRelationModel;

public interface OrgManagerRelationService {

	List<Integer> findManagerIdsByorgId(Integer orgId);
	
	List<OrgManagerRelationModel> findRecordsByManagerId(Integer mid);
}
