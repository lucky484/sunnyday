package com.softtek.pst.service;

import com.softtek.pst.model.OutsourcingManageModel;
import com.softtek.pst.util.Page;

public interface OutsourcingManageService {
    
	public Page<OutsourcingManageModel> getOutsourcingManage(Integer start, Integer length, String column, String dir,String sch);
	
	public int addOutsourcingManage(OutsourcingManageModel outsourcingMange);
	
    public OutsourcingManageModel queryDetailById(long id);
   
    public int updateOutsourcingManage(OutsourcingManageModel outsourcingMange);
    
    public int deleteOutsourcingManage(long outsourcingManageId);

}
