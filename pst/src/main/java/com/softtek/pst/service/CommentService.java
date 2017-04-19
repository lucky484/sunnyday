/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CommentService.java
 * @Prject: pst
 * @Package: com.softtek.pst.service
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 15, 2016 2:07:57 PM
 * @version: V1.0  
 */
package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.CommentModel;

/**
 * @ClassName: CommentService
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 15, 2016 2:07:57 PM
 */
public interface CommentService {
	
	// 新增评论
	public int addComment(CommentModel cm);
	
	//根据crId查询评论
	public List<CommentModel> getCommentByCrId(long crId);

}
