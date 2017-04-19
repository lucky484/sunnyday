package com.softtek.pst.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.softtek.pst.model.OutsourcingManageModel;

public interface OutsourcingManageDao {
      
	   public List<OutsourcingManageModel> getOutsourcingManageLists(@Param(value = "start") Integer start,
				@Param(value = "length") Integer length, @Param(value = "col") String column,
				@Param(value = "dir") String dir,@Param(value = "sch") String sch);
	   
	   public int getOutsourcingManageCount(String sch);
	   
	   public int addOutsourcingManage(OutsourcingManageModel outsourcingMange);
	   
	   public OutsourcingManageModel queryDetailById(long id);
	   
	   public int updateOutsourcingManage(OutsourcingManageModel outsourcingMange);
	   
	   public int deleteOutsourcingManage(long outsourcingManageId);
}
