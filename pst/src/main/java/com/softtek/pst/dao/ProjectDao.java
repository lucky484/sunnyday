package com.softtek.pst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.ProjectModel;

/**
 * 
 * @ClassName: projectDao
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 6, 2016 9:43:36 AM
 */
public interface ProjectDao {

	public int addProject(ProjectModel project);

	public int deleteProject(long projectId);

	public int updateProject(ProjectModel project);

	public ProjectModel getProjectById(long projectId);

	public int getProjectsNum(@Param(value = "search") String search, @Param(value = "projectNumber") String projectNumber,
			@Param(value = "projectName") String projectName,@Param(value="projectManager") String projectManager,@Param(value="startTime") String startTime,
			@Param(value="medialTime") String medialTime,@Param(value="leadTime") String leadTime);

	public List<ProjectModel> getProjects(@Param(value = "start") Integer start,
			@Param(value = "length") Integer length, @Param(value = "col") String column,
			@Param(value = "dir") String dir, @Param(value = "search") String search,
			@Param(value = "projectNumber") String projectNumber, @Param(value = "projectName") String projectName,
			@Param(value="projectManager") String projectManager,@Param(value="startTime") String startTime,@Param(value="medialTime") String medialTime,
			@Param(value="leadTime") String leadTime);

	public int updateFinance(ProjectModel project);
	
	public int getProjectsNumForFinance(@Param(value = "search") String search);

	public List<ProjectModel> getProjectsForFinance(@Param(value = "start") Integer start,
			@Param(value = "length") Integer length, @Param(value = "col") String column,
			@Param(value = "dir") String dir, @Param(value = "search") String search);
	
	public int queryProjectCodeIsExist(String projectCode);
	
	public int checkProjectCode(@Param(value = "projectCode")String projectCode,@Param(value = "projectId")long projectId);
	
	public int getProjectsForFinshNum(@Param(value = "search") String search, @Param(value = "projectNumber") String projectNumber,
			@Param(value = "projectName") String projectName,@Param(value="projectManager") String projectManager);
	
	public List<ProjectModel> getProjectsForFinsh(@Param(value = "start") Integer start,
			@Param(value = "length") Integer length, @Param(value = "col") String column,
			@Param(value = "dir") String dir, @Param(value = "search") String search,
			@Param(value = "projectNumber") String projectNumber, @Param(value = "projectName") String projectName,
			@Param(value="projectManager") String projectManager);

	public List<ProjectModel> queryProjectsList();
}
