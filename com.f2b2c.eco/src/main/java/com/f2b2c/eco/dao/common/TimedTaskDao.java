package com.f2b2c.eco.dao.common;

import com.f2b2c.eco.model.common.ProvinceModel;

/**
 * @author josen.yang
 *
 */
public interface TimedTaskDao extends CrudMapper<ProvinceModel, Integer>
{

	void autoBakUpFSalesOrder();

	void autoBakUpFSalesOrderDetails();
	
}
