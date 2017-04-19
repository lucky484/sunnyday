package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ProjectManagerCommentDao;
import com.softtek.pst.model.ProjectManagerCommentModel;
import com.softtek.pst.service.ProjectManagerCommentService;

@Service
public class ProjectManagerCommentServiceImpl implements ProjectManagerCommentService{
   
	@Autowired 
	private ProjectManagerCommentDao projectManagerCommentDao;
	
	@Override
	public List<ProjectManagerCommentModel> queryCommentByProjectManagerId(
			long projectManagerId) {
		
		return projectManagerCommentDao.queryCommentByProjectManagerId(projectManagerId);
	}

	@Override
	public int addProjectManagerComment(
			ProjectManagerCommentModel projectManagerComment) {
		
		return projectManagerCommentDao.addProjectManagerComment(projectManagerComment);
	}

}
