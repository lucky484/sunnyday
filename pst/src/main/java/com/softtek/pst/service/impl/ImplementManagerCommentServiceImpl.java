package com.softtek.pst.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softtek.pst.dao.ImplementManagerCommentDao;
import com.softtek.pst.model.ImplementManagerCommentModel;
import com.softtek.pst.service.ImplementManagerCommentService;

@Service
public class ImplementManagerCommentServiceImpl implements ImplementManagerCommentService{
    
	@Autowired
	private ImplementManagerCommentDao implementManagerCommentDao;
	
	@Override
	public List<ImplementManagerCommentModel> queryCommentByImplementManagerId(
			long implementManagerId) {
		
		return implementManagerCommentDao.queryCommentByImplementManagerId(implementManagerId);
	}

	@Override
	public int addImplementManagerComment(
			ImplementManagerCommentModel implementManagerComment) {
		
		return implementManagerCommentDao.addImplementManagerComment(implementManagerComment);
	}

}
