package com.f2b2c.eco.service.platform.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FMenuDao;
import com.f2b2c.eco.model.platform.FMenuModel;
import com.f2b2c.eco.service.platform.FMenuService;

/**
 * 菜单服务业务实现类
 * 
 * @author brave.chen
 *
 */
@Service
public class FMenuServiceImpl implements FMenuService {

	@Autowired
	private FMenuDao fMenuDao;

	@Override
	public List<FMenuModel> queryFMenuModelList() {
		return fMenuDao.findAll();
	}

	@Override
	public List<FMenuModel> queryMenuByUserId(Integer userId) {

		return fMenuDao.queryMenuByUserId(userId);
	}

	@Override
	public int queryIsExists(String url) {
		
		return fMenuDao.queryIsExists(url);
	}

}
