package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.ProjectFile;

public interface ProjectFileDao {

	public int addProjectFile(ProjectFile projectFile);

	public int updateProjectFile(ProjectFile projectFile);

	public ProjectFile getProjectFile(long projectFileId);

	public int deleteProjectFileById(long projectFileId);

	public List<ProjectFile> getProjectFileByProjectId(long projectId);

}
