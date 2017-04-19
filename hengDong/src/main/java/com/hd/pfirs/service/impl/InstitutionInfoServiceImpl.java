package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.InstitutionInfoDao;
import com.hd.pfirs.model.InstitutionInfoModel;
import com.hd.pfirs.service.InstitutionInfoService;

/**
 * 
 * @author brave.chen
 * @since 2016-02-02
 */
@Service
public class InstitutionInfoServiceImpl implements InstitutionInfoService
{
	@Autowired
	private InstitutionInfoDao workerInfoDao;
	
	@Override
	public InstitutionInfoModel getInstitutionInfoById(String institutionCode)
	{
		return workerInfoDao.getInstitutionInfoById(institutionCode);
	}

	@Override
	public List<InstitutionInfoModel> getInstitutionInfos()
	{
		return workerInfoDao.getInstitutionInfos();
	}

}
