/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: ProjectCommentService.java
 * @Prject: pst
 * @Package: com.softtek.pst.service
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 18, 2016 10:52:43 AM
 * @version: V1.0  
 */
package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ProjectComment;

/**
 * @ClassName: ProjectCommentService
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 18, 2016 10:52:43 AM
 */
public interface ProjectCommentService {
	
	// 新增评论
	public int addProjectComment(ProjectComment pc);
	
	//根据projectId查询评论
	public List<ProjectComment> getProjectCommentByProjectId(long projectId);

}
