package com.softtek.pst.dao;

import java.util.List;
import com.softtek.pst.model.ProjectManagerCommentModel;

public interface ProjectManagerCommentDao {
   
	   public List<ProjectManagerCommentModel> queryCommentByProjectManagerId(long projectManagerId);
	   
	   public int addProjectManagerComment(ProjectManagerCommentModel projectManagerComment);
}
