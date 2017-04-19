package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.IntegratedQueryMessageDao;
import com.hd.pfirs.model.IntegratedQueryMessageModel;
import com.hd.pfirs.service.IntegratedQueryMessageService;

@Service
public class IntegratedQueryMessageServiceImpl implements IntegratedQueryMessageService {
	@Autowired
	private IntegratedQueryMessageDao integratedQueryMessageDao;

	@Override
	public IntegratedQueryMessageModel getIntegratedQueryMessageModelByCardCode(String cardCode) {
		// TODO Auto-generated method stub
		return integratedQueryMessageDao.getIntegratedQueryMessageModelByCardCode(cardCode);
	}

	@Override
	public List<IntegratedQueryMessageModel> getIntegratedQueryMessageModelListByFaceCode(String facecode) {
		// TODO Auto-generated method stub
		return integratedQueryMessageDao.getIntegratedQueryMessageModelListByFaceCode(facecode);
	}

	@Override
	public List<IntegratedQueryMessageModel> getCompareListByFaceCode(String facecode) {
		// TODO Auto-generated method stub
		return integratedQueryMessageDao.getCompareListByFaceCode(facecode);
	}

	@Override
	public List<IntegratedQueryMessageModel> getCompareListByT_QB_RY_CKRYJBXX(String facecode) {
		// TODO Auto-generated method stub
		return integratedQueryMessageDao.getCompareListByT_QB_RY_CKRYJBXX(facecode);
	}

	@Override
	public List<IntegratedQueryMessageModel> getCompareListByT_QB_LK_LKBK(String facecode) {
		// TODO Auto-generated method stub
		return integratedQueryMessageDao.getCompareListByT_QB_LK_LKBK(facecode);
	}

	@Override
	public List<IntegratedQueryMessageModel> getCompareListByjk(String facecode) {
		// TODO Auto-generated method stub
		return integratedQueryMessageDao.getCompareListByjk(facecode);
	}

}
