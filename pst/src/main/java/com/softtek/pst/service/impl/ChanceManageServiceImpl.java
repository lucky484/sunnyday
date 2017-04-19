package com.softtek.pst.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.pst.dao.ChanceManageDao;
import com.softtek.pst.model.ChanceManageModel;
import com.softtek.pst.service.ChanceManageService;
import com.softtek.pst.util.Page;

@Service
public class ChanceManageServiceImpl implements ChanceManageService{
   
	@Autowired
	private ChanceManageDao chanceManageDao;
	
	@Override
	public Page<ChanceManageModel> getChanceManage(Integer start,
			Integer length, String column, String dir, String sch) {
	    Page<ChanceManageModel> page = new Page<ChanceManageModel>();
	    int total = chanceManageDao.getChanceManageCount(sch);
	    page.setData(chanceManageDao.getChanceManageLists(start, length, column, dir, sch));
	    page.setRecordsTotal(total);
	    page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public int addChanceManage(ChanceManageModel chanceManage) {
		
		return chanceManageDao.addChanceManage(chanceManage);
	}

	@Override
	public ChanceManageModel queryDetailById(long chanceManageId) {
		
		return chanceManageDao.queryDetailById(chanceManageId);
	}

	@Override
	public int updateChanceManage(ChanceManageModel chanceManage) {
		
		return chanceManageDao.updateChanceManage(chanceManage);
	}

	@Override
	public int deleteChanceManage(long chanceManageId) {
		
		return chanceManageDao.deleteChanceManage(chanceManageId);
	}

}
