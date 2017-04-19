package com.hd.pfirs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.JobModelDao;
import com.hd.pfirs.model.JobModel;
import com.hd.pfirs.service.JobModelService;
/**
 * 
 * @author curry.su
 *
 */
@Service
public class JobModelServiceImpl implements JobModelService {

	@Autowired
	private JobModelDao jobModelDao;
	
	@Override
	public JobModel getJobModel() {
		// TODO Auto-generated method stub
		return jobModelDao.getJobModel();
	}

}
