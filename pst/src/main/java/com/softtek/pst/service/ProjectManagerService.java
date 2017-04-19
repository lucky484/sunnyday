package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ProjectManagerModel;
import com.softtek.pst.util.Page;

public interface ProjectManagerService {
	
	public Page<ProjectManagerModel> getProjectManager(Integer start, Integer length, String column, String dir,String sch);
	
	public int addProjectManager(ProjectManagerModel projectManager);
	
	public ProjectManagerModel queryDetailById(long projectManagerId);
	
	public int updateProjectManager(ProjectManagerModel projectManager);
	
	public int deleteProjectManager(long projectManagerId);
	
	public List<ProjectManagerModel> getProjectManagerListsExport(String search);
	
	public List<ProjectManagerModel> queryAllProjectManager();
}
