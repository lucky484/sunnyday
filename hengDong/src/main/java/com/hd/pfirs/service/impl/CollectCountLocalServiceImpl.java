package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.CollectCountLocalDao;
import com.hd.pfirs.model.CollectCountModel;
import com.hd.pfirs.service.CollectCountLocalService;

@Service
public class CollectCountLocalServiceImpl implements CollectCountLocalService {
	

	@Autowired
	private CollectCountLocalDao collectCountLocalDao;

	@Override
	public List<CollectCountModel> getCollectCountModellList(int page, String collectSite, String startDate,
			String endDate,int fys) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModellList(page, collectSite, startDate, endDate,fys);
	}

	@Override
	public int getCollectCountModelCount(String collectSite, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModelCount(collectSite, startDate, endDate);
	}

	@Override
	public List<CollectCountModel> getCollectCountModelListByidCardNo(int page, String idCardNo, String collectSite,
			String startDate, String endDate,int fys) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModelListByidCardNo(page, idCardNo, collectSite, startDate, endDate,fys);
	}

	@Override
	public int getCollectCountModelCountByidCardNo(String idCardNo, String collectSite, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModelCountByidCardNo(idCardNo, collectSite, startDate, endDate);
	}

	@Override
	public List<CollectCountModel> getCollectCountModelListByidCardNoDetails(String idCardNo, String collectSite,
			String startDate, String endDate,String compareBaseID) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModelListByidCardNoDetails(idCardNo, collectSite, startDate, endDate,compareBaseID);
	}

	@Override
	public CollectCountModel getCollectCountModel(String idCardNo, String collectSite, String collectTime) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModel(idCardNo, collectSite, collectTime);
	}

	@Override
	public CollectCountModel getCollectCountModelByIdCardNo(String idCardNo) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModelByIdCardNo(idCardNo);
	}

	@Override
	public CollectCountModel getCollectCountModelByCardCode(String cardCode) {
		// TODO Auto-generated method stub
		return collectCountLocalDao.getCollectCountModelByCardCode(cardCode);
	}
}
