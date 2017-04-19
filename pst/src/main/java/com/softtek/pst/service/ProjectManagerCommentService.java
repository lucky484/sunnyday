package com.softtek.pst.service;

import java.util.List;
import com.softtek.pst.model.ProjectManagerCommentModel;

public interface ProjectManagerCommentService {
   
	public List<ProjectManagerCommentModel> queryCommentByProjectManagerId(long projectManagerId);
	
	public int addProjectManagerComment(ProjectManagerCommentModel projectManagerComment);
}
