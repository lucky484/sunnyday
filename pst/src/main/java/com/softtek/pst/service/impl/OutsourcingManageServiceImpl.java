package com.softtek.pst.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.pst.dao.OutsourcingManageDao;
import com.softtek.pst.model.OutsourcingManageModel;
import com.softtek.pst.service.OutsourcingManageService;
import com.softtek.pst.util.Page;

@Service
public class OutsourcingManageServiceImpl implements OutsourcingManageService{
	
	@Autowired 
	private OutsourcingManageDao outsourcingManageDao;

	@Override
	public Page<OutsourcingManageModel> getOutsourcingManage(Integer start,
			Integer length, String column, String dir, String sch) {
		Page<OutsourcingManageModel> page = new Page<OutsourcingManageModel>();
		int total = outsourcingManageDao.getOutsourcingManageCount(sch);
		page.setData(outsourcingManageDao.getOutsourcingManageLists(start, length, column, dir, sch));
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public int addOutsourcingManage(OutsourcingManageModel outsourcingMange) {
		
		return outsourcingManageDao.addOutsourcingManage(outsourcingMange);
	}

	@Override
	public OutsourcingManageModel queryDetailById(long id) {
		
		return outsourcingManageDao.queryDetailById(id);
	}

	@Override
	public int updateOutsourcingManage(OutsourcingManageModel outsourcingMange) {
		
		return outsourcingManageDao.updateOutsourcingManage(outsourcingMange);
	}

	@Override
	public int deleteOutsourcingManage(long outsourcingManageId) {
		
		return outsourcingManageDao.deleteOutsourcingManage(outsourcingManageId);
	}

}
