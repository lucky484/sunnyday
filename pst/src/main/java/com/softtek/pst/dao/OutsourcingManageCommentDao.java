package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.OutsourcingManageCommentModel;

public interface OutsourcingManageCommentDao {
   
	      public List<OutsourcingManageCommentModel> queryCommentByOutSourcingManageId(long outsourcingManageId); 
	      
	      public int addOutsourcingManageComment(OutsourcingManageCommentModel outsourcingManageComment);
}
