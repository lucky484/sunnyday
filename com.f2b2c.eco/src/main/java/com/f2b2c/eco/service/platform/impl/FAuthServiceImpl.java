package com.f2b2c.eco.service.platform.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FAuthCodeDao;
import com.f2b2c.eco.dao.platform.FUserDao;
import com.f2b2c.eco.model.platform.FAuthCodeModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FAuthService;
import com.f2b2c.eco.util.KeygenUtil;

@Service
public class FAuthServiceImpl implements FAuthService {
	@Autowired
	private FAuthCodeDao dao;

	@Autowired
	private FUserDao userDao;

	@Override
	public int insertAuthCodes(Integer num, Integer userId) {
		List<String> keys = KeygenUtil.generateKeys(num);
		FUserModel user = new FUserModel();
		user.setId(userId);
		FAuthCodeModel model = null;
		for (int i = 0; i < keys.size(); i++) {
			model = new FAuthCodeModel();
			model.setCreatedUser(user);
			model.setCreatedTime(new Date());
			model.setIsUsed(0);// 未被注册
			model.setCode(keys.get(i));
			dao.insert(model);
		}
		return 1;
	}

	@Override
	public List<FAuthCodeModel> queryAuthCodesByFUserId(Integer userId) {
		if (userId == null) {
			return generateDefaultFAuthList();
		} else {
			return dao.findAllByFUserId(userId);
		}
	}

	@Override
	public int updateAuthCode(String code) {
		return dao.updateByCode(code);
	}

	private List<FAuthCodeModel> generateDefaultFAuthList() {
		return new ArrayList<FAuthCodeModel>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<FAuthCodeModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
        paramMap.put("pageable", pageable);
		List<FAuthCodeModel> authCodes = dao.findWithPagination(paramMap);
		int total = dao.queryCountByCondition(paramMap);
        Page<FAuthCodeModel> pages = new Pagination<FAuthCodeModel>(authCodes,
				pageable, total);
		return pages;
	}

	@Override
	public FAuthCodeModel getAuthCodeWithUserId(String authCode, Integer userId) {

		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("authCode", authCode);
		paramMap.put("userId", userId);
		return dao.getAuthCodeWithUserId(paramMap);
	}

	@Override
	public FAuthCodeModel queryCodeIsUser(String code) {

		return dao.queryCodeIsUser(code);
	}

}
