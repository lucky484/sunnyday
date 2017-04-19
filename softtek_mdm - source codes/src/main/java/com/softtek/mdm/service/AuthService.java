package com.softtek.mdm.service;

import java.util.Collection;

import com.softtek.mdm.model.AuthModel;

public interface AuthService {

	/**
	 * 根据用户类型id进行查询用户权限
	 * @param id
	 * @return
	 */
	Collection<AuthModel> findOneByUserType(Integer id);
}
