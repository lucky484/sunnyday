package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.ChanceManageModel;

public interface ChanceManageDao {
  
	     public List<ChanceManageModel> getChanceManageLists(@Param(value = "start") Integer start,
	 			@Param(value = "length") Integer length, @Param(value = "col") String column,
				@Param(value = "dir") String dir,@Param(value = "sch") String sch);
	     
	     public int getChanceManageCount(String sch);
	     
	     public int addChanceManage(ChanceManageModel chanceManage);
	     
	     public ChanceManageModel queryDetailById(long chanceManageId);
	     
	     public int updateChanceManage(ChanceManageModel chanceManage);
	     
	     public int deleteChanceManage(long chanceManageId);
}
