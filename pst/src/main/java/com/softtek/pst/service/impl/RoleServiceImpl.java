/**  
 * Copyright © 2016公司名字. All rights reserved.
 *
 * @Title: RoleServiceImpl.java
 * @Prject: pst
 * @Package: com.softtek.pst.service.impl
 * @Description: TODO
 * @author: light.chen  
 * @date: Apr 20, 2016 4:10:35 PM
 * @version: V1.0  
 */
package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.RoleDao;
import com.softtek.pst.model.Role;
import com.softtek.pst.service.RoleService;

/**
 * @ClassName: RoleServiceImpl
 * @Description: TODO
 * @author: light.chen
 * @date: Apr 20, 2016 4:10:35 PM
 */
@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getRole() {
		return roleDao.getRole();
	}

}
