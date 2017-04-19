package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.model.platform.FFreightModel;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FFreightDao {

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
