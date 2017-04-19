package com.hd.pfirs.service;

import com.hd.pfirs.model.ParamSet;

/**
 * 配置一些预警阀值
 * @author ligang.yang
 *
 */
public interface ParamSetService {
	/**
	 * 查询一个人证比对阀值
	 * 
	 * @return
	 */
	ParamSet getParamSet();
	
	/**
	 * 更新阀值
	 * @param set
	 */
	void updateParamSet(ParamSet set);

	void insertParamSet(ParamSet set);
}
