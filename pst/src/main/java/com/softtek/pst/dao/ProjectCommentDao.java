/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: ProjectCommentDao.java
 * @Prject: pst
 * @Package: com.softtek.pst.dao
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 18, 2016 10:47:32 AM
 * @version: V1.0  
 */
package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.ProjectComment;

/**
 * @ClassName: ProjectCommentDao
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 18, 2016 10:47:32 AM
 */
public interface ProjectCommentDao {
	
	// 新增评论
	public int addProjectComment(ProjectComment pc);
	
	//根据projectId查询评论
	public List<ProjectComment> getProjectCommentByProjectId(long projectId);

}
