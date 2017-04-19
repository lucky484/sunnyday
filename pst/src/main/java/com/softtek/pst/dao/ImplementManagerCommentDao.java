package com.softtek.pst.dao;

import java.util.List;

import com.softtek.pst.model.ImplementManagerCommentModel;

public interface ImplementManagerCommentDao {
   
	   public List<ImplementManagerCommentModel> queryCommentByImplementManagerId(long implementManagerId);
	   
	   public int addImplementManagerComment(ImplementManagerCommentModel implementManagerComment);
}
