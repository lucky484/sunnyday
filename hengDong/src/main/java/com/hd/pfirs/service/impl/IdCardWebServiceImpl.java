package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.IdCardWebDao;
import com.hd.pfirs.model.IdCardWebModel;
import com.hd.pfirs.service.IdCardWebService;

@Service
public class IdCardWebServiceImpl implements IdCardWebService {
	
	@Autowired
	private IdCardWebDao idCardWebDao;

	@Override
	public List<IdCardWebModel> getIdCardWebModelList(int page, String collectSite,int fys) {
		// TODO Auto-generated method stub
		return idCardWebDao.getIdCardWebModelList(page, collectSite,fys);
	}

	@Override
	public int getIdCardWebModelCount(String collectSite) {
		// TODO Auto-generated method stub
		return idCardWebDao.getIdCardWebModelCount(collectSite);
	}

	@Override
	public List<IdCardWebModel> getIdCardWebModelListByidCardNo(int page, String idCardNo, String collectSite,int fys) {
		// TODO Auto-generated method stub
		return idCardWebDao.getIdCardWebModelListByidCardNo(page, idCardNo, collectSite,fys);
	}

	@Override
	public int getIdCardWebModelCountByidCardNo(String idCardNo, String collectSite) {
		// TODO Auto-generated method stub
		return idCardWebDao.getIdCardWebModelCountByidCardNo(idCardNo, collectSite);
	}
	
	

}
