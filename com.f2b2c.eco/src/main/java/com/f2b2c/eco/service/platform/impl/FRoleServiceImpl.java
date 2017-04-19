package com.f2b2c.eco.service.platform.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.annotation.LogAnnotation;
import com.f2b2c.eco.dao.platform.FRoleDao;
import com.f2b2c.eco.model.platform.FRoleModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FRoleService;

/**
 * 角色服务实现类
 * @author brave.chen
 *
 */
@Service
public class FRoleServiceImpl implements FRoleService
{

	@Autowired
	private FRoleDao fRoleDao;
	
	@Override
	public List<FRoleModel> queryAll()
	{
		return fRoleDao.findAll();
	}

	@Override
	public Page<FRoleModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
		int total = fRoleDao.countWithMap(paramMap);
		paramMap.put("pageable", pageable);
		List<FRoleModel> lists = fRoleDao.findWithPagination(paramMap);	
		Page<FRoleModel> pages = new Pagination<>(lists,pageable,total);
		return pages;
	}

	@Override
	public FRoleModel findOneByUserId(String userId) {
		return fRoleDao.findOneByUserId(userId);
	}
	
	@Override
	@LogAnnotation(operateContent = "登录成功", operateType = "用户登录")
	public List<String> queryAllUrlByUserId(Integer userId) {
		
		return fRoleDao.queryAllUrlByUserId(userId);
	}

	@Override
	public List<String> queryAllUrl() {
		
		return fRoleDao.queryAllUrl();
	}
	
}
