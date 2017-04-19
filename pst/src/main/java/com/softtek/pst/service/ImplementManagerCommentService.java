package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ImplementManagerCommentModel;

public interface ImplementManagerCommentService {
	
	
	   public List<ImplementManagerCommentModel> queryCommentByImplementManagerId(long implementManagerId);
	   
	   public int addImplementManagerComment(ImplementManagerCommentModel implementManagerComment);
}
