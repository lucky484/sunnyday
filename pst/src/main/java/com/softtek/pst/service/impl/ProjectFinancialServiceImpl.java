package com.softtek.pst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.ProjectFinancialDao;
import com.softtek.pst.model.ProjectFinancialModel;
import com.softtek.pst.service.ProjectFinancialService;

@Service
public class ProjectFinancialServiceImpl implements ProjectFinancialService{
    
	
	@Autowired
	private ProjectFinancialDao projectFinancialDao;
	
	@Override
	public int addProjectFinancial(ProjectFinancialModel projectFinancial) {
		
		return projectFinancialDao.addProjectFinancial(projectFinancial);
	}

	@Override
	public List<ProjectFinancialModel> queryFinancialReceive(Integer projectId) {
		
		return projectFinancialDao.queryFinancialReceive(projectId);
	}

	@Override
	public List<ProjectFinancialModel> queryFinancialPayment(Integer projectId) {
		
		return projectFinancialDao.queryFinancialPayment(projectId);
	}

}
