/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: CommentServiceImpl.java
 * @Prject: pst
 * @Package: com.softtek.pst.service.impl
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 15, 2016 2:08:39 PM
 * @version: V1.0  
 */
package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.CommentDao;
import com.softtek.pst.model.CommentModel;
import com.softtek.pst.service.CommentService;

/**
 * @ClassName: CommentServiceImpl
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 15, 2016 2:08:39 PM
 */
@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;

	/**
	 * @Title: addComment
	 * @Description: TODO
	 * @param cm
	 * @return
	 * @see com.softtek.pst.service.CommentService#addComment(com.softtek.pst.model.CommentModel)
	 */
	@Override
	public int addComment(CommentModel cm) {
		return commentDao.addComment(cm);
	}

	/**
	 * @Title: getCommentByCrId
	 * @Description: TODO
	 * @param crId
	 * @return
	 * @see com.softtek.pst.service.CommentService#getCommentByCrId(long)
	 */
	@Override
	public List<CommentModel> getCommentByCrId(long crId) {
		return commentDao.getCommentByCrId(crId);
	}

}
