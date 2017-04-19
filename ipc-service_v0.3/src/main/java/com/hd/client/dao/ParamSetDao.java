package com.hd.client.dao;

import java.util.Map;

import com.hd.client.model.ParamSet;

public interface ParamSetDao {

	public ParamSet getParamSet();

	/**
	 * 查询数据库大小
	 * 
	 * @return
	 */
	public Map<String, Object> countDBSize();
}
