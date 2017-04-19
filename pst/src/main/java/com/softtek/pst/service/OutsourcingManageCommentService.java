package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.OutsourcingManageCommentModel;

public interface OutsourcingManageCommentService {
      
	
		public List<OutsourcingManageCommentModel> queryCommentByOutSourcingManageId(long outsourcingManageId); 
	    
	    public int addOutsourcingManageComment(OutsourcingManageCommentModel outsourcingManageComment);
}
