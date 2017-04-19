package com.softtek.pst.dao;

import java.util.List;
import com.softtek.pst.model.ProjectFinancialModel;

public interface ProjectFinancialDao {
    
	      public int addProjectFinancial(ProjectFinancialModel projectFinancial);
	        
	      public List<ProjectFinancialModel> queryFinancialReceive(Integer projectId);
	      
	      public List<ProjectFinancialModel> queryFinancialPayment(Integer projectId);
}
