package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ProjectModel;
import com.softtek.pst.util.Page;

/**
 * 
 * @ClassName: ProjectService
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 6, 2016 9:52:04 AM
 */
public interface ProjectService {

	public int addProject(ProjectModel project);

	public int deleteProject(long projectId);

	public int updateProject(ProjectModel project);

	public ProjectModel getProjectById(long projectId);

	public int updateFinance(ProjectModel project);

	public Page<ProjectModel> getProjects(Integer start, Integer length, String column, String dir, String search,
			String projectNumber, String projectName,String projectManager,String startTime,String medialTime, String leadTime);

	public Page<ProjectModel> getProjectsForFinance(Integer start, Integer length, String column, String dir,
			String search);
	
	public int queryProjectCodeIsExist(String projectCode);
	
    public int checkProjectCode(String projectCode,long projectId);
    
    public Page<ProjectModel> getProjectsForFinsh(Integer start, Integer length, String column, String dir, String search,
			String projectNumber, String projectName,String projectManager);
    
    public List<ProjectModel> queryProjectsList();

}
