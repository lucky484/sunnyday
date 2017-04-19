package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.FluxModel;
import com.softtek.mdm.model.Page;

public interface FluxStatisticsService {

	Page getAllFluxsListsByMap(Map<String, Object> paramMap);
	
	List<FluxModel> getFluxsListsByMap(Map<String, Object> paramMap);

    List<FluxModel> getChartFluxsListsByMap(Map<String, Object> paramMap);

	Page getAllTotalFluxsListsByMap(Map<String, Object> paramMap, String date);

	List<FluxModel> getTotalFluxsListsByMap(Map<String, Object> paramMap, String create_time);

	List<FluxModel> getFluxsChartListsByMap(Map<String, Object> paramMap);

}
