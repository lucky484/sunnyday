package com.f2b2c.eco.service.platform;

import com.f2b2c.eco.model.platform.FFreightModel;

public interface FFreightService {

	/**
	 * 查询
	 * @param Id
	 * @return
	 */
	FFreightModel select(Integer Id);
	
	/**
	 * 添加
	 * @param model
	 * @return
	 */
	int insert(FFreightModel model);
	
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	int update(FFreightModel model);
}
