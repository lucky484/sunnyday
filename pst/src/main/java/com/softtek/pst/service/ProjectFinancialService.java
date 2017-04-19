package com.softtek.pst.service;

import java.util.List;

import com.softtek.pst.model.ProjectFinancialModel;

public interface ProjectFinancialService {
   
	 public int addProjectFinancial(ProjectFinancialModel projectFinancial);
	 
	 public List<ProjectFinancialModel> queryFinancialReceive(Integer projectId);
     
     public List<ProjectFinancialModel> queryFinancialPayment(Integer projectId);
}
