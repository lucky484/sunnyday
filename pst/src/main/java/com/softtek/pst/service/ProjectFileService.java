package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ProjectFile;

public interface ProjectFileService {

	public int addProjectFile(ProjectFile projectFile);

	public ProjectFile getProjectFile(long projectFileId);

	public int updateProjectFile(ProjectFile projectFile);

	public int deleteProjectFileById(long projectFileId);

	public List<ProjectFile> getProjectFileByProjectId(long projectId);

}
