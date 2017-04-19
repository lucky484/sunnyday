/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CommentDao.java
 * @Prject: pst
 * @Package: com.softtek.pst.dao
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 15, 2016 1:49:16 PM
 * @version: V1.0  
 */
package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.CommentModel;

/**
 * @ClassName: CommentDao
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 15, 2016 1:49:16 PM
 */
public interface CommentDao {
	
	// 新增评论
	public int addComment(CommentModel cm);
	
	//根据crId查询评论
	public List<CommentModel> getCommentByCrId(long crId);

}
