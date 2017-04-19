package com.softtek.pst.service;

import com.softtek.pst.model.ChanceManageModel;
import com.softtek.pst.util.Page;

public interface ChanceManageService {
        
	public Page<ChanceManageModel> getChanceManage(Integer start, Integer length, String column, String dir,String sch);
	
	public int addChanceManage(ChanceManageModel chanceManage);
	
	public ChanceManageModel queryDetailById(long chanceManageId);
	
	public int updateChanceManage(ChanceManageModel chanceManage);
	
	public int deleteChanceManage(long chanceManageId);
}
