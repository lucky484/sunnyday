package com.hd.pfirs.dao;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.ParamSet;

/**
 * 时时监控页面基础配置查询
 * 
 * @author ligang.yang
 *
 */
public interface ParamSetDao {

	/**
	 * 查询一个人证比对阀值
	 * 
	 * @return
	 */
	public ParamSet getParamSet();
	
	/**
	 * 用来更新数据库的阀值配置信息
	 * @param set
	 */
	void updateParamSet(ParamSet set);

	/**
	 * 新增一条阀值配置信息
	 * @param set
	 */
	public void insertParamSet(ParamSet set);
}
