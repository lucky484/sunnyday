package com.hd.client.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.dao.ParamSetDao;
import com.hd.client.model.ParamSet;
import com.hd.client.service.ParamSetService;

@Service
public class ParamSetServiceImpl implements ParamSetService {

	@Autowired
	private ParamSetDao dao;

	@Override
	public ParamSet getParamSet() {
		return dao.getParamSet();
	}

	@Override
	public Map<String, Object> countDBSize() {
		return dao.countDBSize();
	}
}
