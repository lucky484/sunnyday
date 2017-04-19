package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceStatisticsModel;
import com.softtek.mdm.model.Page;
/**
 * 
 * @author josen.yang
 *
 */
public interface DeviceStatisticsService {

    Page getAllDeviceListsByMap(Map<String, Object> paramMap, Integer start, Integer length);

	/**
	 * @param paramMap
	 * @return
	 */
	List<DeviceStatisticsModel> getDeviceListsByMap(Map<String, Object> paramMap);

    List<DeviceStatisticsModel> geExportListsByMap(Map<String, Object> paramMap);

}
