package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ProjectManagerDao;
import com.softtek.pst.model.ProjectManagerModel;
import com.softtek.pst.service.ProjectManagerService;
import com.softtek.pst.util.Page;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService{
    
	@Autowired
	private ProjectManagerDao projectManagerDao;
	
	@Override
	public Page<ProjectManagerModel> getProjectManager(Integer start, Integer length,
			String column, String dir,String sch) {
		Page<ProjectManagerModel> page = new Page<ProjectManagerModel>();
		int total = projectManagerDao.getProjectManagerCount(sch);
		page.setData(projectManagerDao.getProjectManagerLists(start, length, column, dir, sch));
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public int addProjectManager(ProjectManagerModel projectManager) {
		
		return projectManagerDao.addProjectManager(projectManager);
	}

	@Override
	public ProjectManagerModel queryDetailById(long projectManagerId) {
		
		return projectManagerDao.queryDetailById(projectManagerId);
	}

	@Override
	public int updateProjectManager(ProjectManagerModel projectManager) {

		return projectManagerDao.updateProjectManager(projectManager);
	}

	@Override
	public int deleteProjectManager(long projectManagerId) {
		
		return projectManagerDao.deleteProjectManager(projectManagerId);
	}

	@Override
	public List<ProjectManagerModel> getProjectManagerListsExport(String search) {
		
		return projectManagerDao.getProjectManagerListsExport(search);
	}

	@Override
	public List<ProjectManagerModel> queryAllProjectManager() {
		
		return projectManagerDao.queryAllProjectManager();
	}
}
