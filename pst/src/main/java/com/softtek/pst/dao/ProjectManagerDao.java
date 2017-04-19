package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.ProjectManagerModel;

public interface ProjectManagerDao {
     
	public List<ProjectManagerModel> getProjectManagerLists(@Param(value = "start") Integer start,
			@Param(value = "length") Integer length, @Param(value = "col") String column,
			@Param(value = "dir") String dir,@Param(value = "sch") String sch);
	
	public int getProjectManagerCount(String sch);
	
	public int addProjectManager(ProjectManagerModel projectManager);
	
	public ProjectManagerModel queryDetailById(@Param(value="id") long projectManagerId);
	
	public int updateProjectManager(ProjectManagerModel projectManager);
	
	public int deleteProjectManager(long projectManagerId);
	
	public List<ProjectManagerModel> getProjectManagerListsExport(String search);
	
	public List<ProjectManagerModel> queryAllProjectManager();
}
