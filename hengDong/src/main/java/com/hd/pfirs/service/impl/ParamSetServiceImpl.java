package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.ParamSetDao;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.service.ParamSetService;

/**
 * 配置一些预警阀值
 * @author ligang.yang
 */
@Service
public class ParamSetServiceImpl implements ParamSetService{

	@Autowired
	private ParamSetDao dao;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParamSet getParamSet() {
		return dao.getParamSet();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateParamSet(ParamSet set) {
		dao.updateParamSet(set);
	}

	@Override
	public void insertParamSet(ParamSet set) {
		dao.insertParamSet(set);
	}

}
