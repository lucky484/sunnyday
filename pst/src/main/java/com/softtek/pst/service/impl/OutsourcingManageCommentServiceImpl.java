package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.OutsourcingManageCommentDao;
import com.softtek.pst.model.OutsourcingManageCommentModel;
import com.softtek.pst.service.OutsourcingManageCommentService;

@Service
public class OutsourcingManageCommentServiceImpl implements OutsourcingManageCommentService{

	
	@Autowired
	private OutsourcingManageCommentDao outsourcingManageCommentDao;
	
	
	@Override
	public List<OutsourcingManageCommentModel> queryCommentByOutSourcingManageId(
			long outsourcingManageId) {
		
		return outsourcingManageCommentDao.queryCommentByOutSourcingManageId(outsourcingManageId);
	}

	@Override
	public int addOutsourcingManageComment(
			OutsourcingManageCommentModel outsourcingManageComment) {
	
		return outsourcingManageCommentDao.addOutsourcingManageComment(outsourcingManageComment);
	}

}
