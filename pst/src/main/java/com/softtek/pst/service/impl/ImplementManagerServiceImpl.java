package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ImplementManagerDao;
import com.softtek.pst.model.ImplementManagerModel;
import com.softtek.pst.service.ImplementManagerService;
import com.softtek.pst.util.Page;

@Service
public class ImplementManagerServiceImpl implements ImplementManagerService{
    
	
	@Autowired
	private ImplementManagerDao implementManagerDao;
	
	@Override
	public Page<ImplementManagerModel> getImplementManager(Integer start,
			Integer length, String column, String dir, String sch) {
		Page<ImplementManagerModel> page = new Page<ImplementManagerModel>();
		int total = implementManagerDao.getImplementManagerCount(sch);
		page.setData(implementManagerDao.getImplementManagerLists(start, length, column, dir, sch));
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public int addImplementManager(ImplementManagerModel implementManager) {
		
		return implementManagerDao.addImplementManager(implementManager);
	}

	@Override
	public ImplementManagerModel queryDetailById(long id) {
	    
		return implementManagerDao.queryDetailById(id);
	}

	@Override
	public int updateImplementManager(ImplementManagerModel implementManager) {
		
		return implementManagerDao.updateImplementManager(implementManager);
	}

	@Override
	public int deleteImplementManager(long implementManagerId) {
		
		return implementManagerDao.deleteImplementManager(implementManagerId);
	}

	@Override
	public List<ImplementManagerModel> queryAllImplementManager() {
		
		return implementManagerDao.queryAllImplementManager();
	}

}
