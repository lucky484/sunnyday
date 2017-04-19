package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FMenuModel;

public interface FMenuDao extends CrudMapper<FMenuModel, Serializable>{
     
	    public List<FMenuModel> queryMenuByUserId(Integer userId);
	    
	    public int queryIsExists(String url);
}