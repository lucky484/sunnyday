package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ProjectDao;
import com.softtek.pst.model.ProjectModel;
import com.softtek.pst.service.ProjectService;
import com.softtek.pst.util.Page;

/**
 * 
 * @ClassName: ProjectServiceImpl
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 6, 2016 9:53:08 AM
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;

	@Override
	public int addProject(ProjectModel project) {
		return projectDao.addProject(project);
	}

	@Override
	public int deleteProject(long projectId) {
		return projectDao.deleteProject(projectId);
	}

	@Override
	public int updateProject(ProjectModel project) {
		return projectDao.updateProject(project);
	}

	@Override
	public ProjectModel getProjectById(long projectId) {
		return projectDao.getProjectById(projectId);
	}

	@Override
	public Page<ProjectModel> getProjects(Integer start, Integer length, String column, String dir, String search,
			String projectNumber, String projectName,String projectManager,String startTime,String medialTime, String leadTime) {
		Page<ProjectModel> page = new Page<>();
		page.setData(projectDao.getProjects(start, length, column, dir, search, projectNumber, projectName,projectManager,startTime,medialTime,leadTime));
		int total = projectDao.getProjectsNum(search, projectNumber, projectName,projectManager,startTime,medialTime,leadTime);
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public Page<ProjectModel> getProjectsForFinance(Integer start, Integer length, String column, String dir,
			String search) {
		Page<ProjectModel> page = new Page<>();
		page.setData(projectDao.getProjectsForFinance(start, length, column, dir, search));
		int total = projectDao.getProjectsNumForFinance(search);
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public int updateFinance(ProjectModel project) {
		return projectDao.updateFinance(project);
	}

	@Override
	public int queryProjectCodeIsExist(String projectCode) {
		
		return projectDao.queryProjectCodeIsExist(projectCode);
	}

	@Override
	public int checkProjectCode(String projectCode, long projectId) {
		
		return projectDao.checkProjectCode(projectCode, projectId);
	}

	@Override
	public Page<ProjectModel> getProjectsForFinsh(Integer start,
			Integer length, String column, String dir, String search,
			String projectNumber, String projectName, String projectManager) {
		Page<ProjectModel> page = new Page<>();
		page.setData(projectDao.getProjectsForFinsh(start, length, column, dir, search, projectNumber, projectName,projectManager));
		int total = projectDao.getProjectsForFinshNum(search, projectNumber, projectName,projectManager);
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public List<ProjectModel> queryProjectsList() {
		
		return projectDao.queryProjectsList();
	}

}
