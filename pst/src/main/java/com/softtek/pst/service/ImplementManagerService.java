package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ImplementManagerModel;
import com.softtek.pst.util.Page;

public interface ImplementManagerService {
     
	public Page<ImplementManagerModel> getImplementManager(Integer start, Integer length, String column, String dir,String sch);
	
	public int addImplementManager(ImplementManagerModel implementManager);
	
	public ImplementManagerModel queryDetailById(long id);
	
	public int updateImplementManager(ImplementManagerModel implementManager);
	
	public int deleteImplementManager(long implementManagerId);
	
    public List<ImplementManagerModel> queryAllImplementManager();
}
