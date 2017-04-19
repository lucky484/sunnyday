/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: ProjectCommentServiceImpl.java
 * @Prject: pst
 * @Package: com.softtek.pst.service.impl
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 18, 2016 10:53:23 AM
 * @version: V1.0  
 */
package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ProjectCommentDao;
import com.softtek.pst.model.ProjectComment;
import com.softtek.pst.service.ProjectCommentService;

/**
 * @ClassName: ProjectCommentServiceImpl
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 18, 2016 10:53:23 AM
 */
@Service
public class ProjectCommentServiceImpl implements ProjectCommentService{
	
	@Autowired
	private ProjectCommentDao projectCommentDao;

	/**
	 * @Title: addProjectComment
	 * @Description: TODO
	 * @param pc
	 * @return
	 * @see com.softtek.pst.service.ProjectCommentService#addProjectComment(com.softtek.pst.model.ProjectComment)
	 */
	@Override
	public int addProjectComment(ProjectComment pc) {
		return projectCommentDao.addProjectComment(pc);
	}

	/**
	 * @Title: getProjectCommentByProjectId
	 * @Description: TODO
	 * @param projectId
	 * @return
	 * @see com.softtek.pst.service.ProjectCommentService#getProjectCommentByProjectId(long)
	 */
	@Override
	public List<ProjectComment> getProjectCommentByProjectId(long projectId) {
		return projectCommentDao.getProjectCommentByProjectId(projectId);
	}

}
