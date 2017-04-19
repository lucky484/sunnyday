package com.softtek.mdm.dao;

import java.util.Collection;

import com.softtek.mdm.model.AuthModel;

public interface AuthDao extends CrudMapper<AuthModel, Integer> {

	/**
	 * 根据用户类型id进行查询用户权限
	 * @param id
	 * @return
	 */
	Collection<AuthModel> findOneByUserType(Integer id);
}
