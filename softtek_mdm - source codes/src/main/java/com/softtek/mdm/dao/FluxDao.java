package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.FluxModel;

public interface FluxDao {

	Integer getFluxsCountByMap(Map<String, Object> paramMap);

	List<FluxModel> getFluxsListsByMap(Map<String, Object> paramMap);

	void saveBatchMap(Map<String, Object> map);

	void autoBackupFlux();

	List<FluxModel> getExportFluxsListsByMap(Map<String, Object> paramMap);

    List<FluxModel> getChartFluxsListsByMap(Map<String, Object> paramMap);

	Integer getTotalFluxCountByMap(Map<String, Object> paramMap);

	List<FluxModel> getTotalFluxListsByMap(Map<String, Object> paramMap);

	Integer getTotalFluxsCountByMap(Map<String, Object> paramMap);

	List<FluxModel> getTotalFluxsListsByMap(Map<String, Object> paramMap);

	List<FluxModel> getExportTotalFluxsListsByMap(Map<String, Object> paramMap);

	List<FluxModel> getExportTotalFluxListsByMap(Map<String, Object> paramMap);

    FluxModel selectTodayFlux(Map<String, Object> paramMap);

    void deleteFluxStatisJob();
	
}
