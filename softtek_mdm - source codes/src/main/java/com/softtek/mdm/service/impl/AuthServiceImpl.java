package com.softtek.mdm.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.AuthDao;
import com.softtek.mdm.model.AuthModel;
import com.softtek.mdm.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthDao authDao;
	
	
	@Override
	public Collection<AuthModel> findOneByUserType(Integer id) {
		return authDao.findOneByUserType(id);
	}

}
