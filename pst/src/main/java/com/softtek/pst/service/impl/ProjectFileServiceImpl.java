package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ProjectFileDao;
import com.softtek.pst.model.ProjectFile;
import com.softtek.pst.service.ProjectFileService;

@Service
public class ProjectFileServiceImpl implements ProjectFileService{
	
	@Autowired
	private ProjectFileDao projectFileDao;

	/**
	 * @Title: addProjectFile
	 * @Description: TODO
	 * @param projectFile
	 * @return
	 * @see com.softtek.pst.service.ProjectFileService#addProjectFile(com.softtek.pst.model.ProjectFile)
	 */
	@Override
	public int addProjectFile(ProjectFile projectFile) {
		return projectFileDao.addProjectFile(projectFile);
	}

	/**
	 * @Title: getProjectFile
	 * @Description: TODO
	 * @param projectFileId
	 * @return
	 * @see com.softtek.pst.service.ProjectFileService#getProjectFile(long)
	 */
	@Override
	public ProjectFile getProjectFile(long projectFileId) {
		return projectFileDao.getProjectFile(projectFileId);
	}

	/**
	 * @Title: getProjectFileByProjectId
	 * @Description: TODO
	 * @param projectId
	 * @return
	 * @see com.softtek.pst.service.ProjectFileService#getProjectFileByProjectId(long)
	 */
	@Override
	public List<ProjectFile> getProjectFileByProjectId(long projectId) {
		return projectFileDao.getProjectFileByProjectId(projectId);
	}

	@Override
	public int deleteProjectFileById(long projectFileId) {
		return projectFileDao.deleteProjectFileById(projectFileId);
	}

	@Override
	public int updateProjectFile(ProjectFile projectFile) {
		return projectFileDao.updateProjectFile(projectFile);
	}

}
