package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.ImplementManagerModel;

public interface ImplementManagerDao {
   
	 public List<ImplementManagerModel> getImplementManagerLists(@Param(value = "start") Integer start,
				@Param(value = "length") Integer length, @Param(value = "col") String column,
				@Param(value = "dir") String dir,@Param(value = "sch") String sch);
	 
	 public int getImplementManagerCount(String sch);
	 
	 public int addImplementManager(ImplementManagerModel implementManager);
	 
	 public ImplementManagerModel queryDetailById(long id);
	 
	 public int updateImplementManager(ImplementManagerModel implementManager);
	 
	 public int deleteImplementManager(long implementManagerId);
	 
	 public List<ImplementManagerModel> queryAllImplementManager();
}
