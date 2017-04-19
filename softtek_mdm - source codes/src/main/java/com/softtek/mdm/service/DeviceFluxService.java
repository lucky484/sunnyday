package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceFluxModel;
import com.softtek.mdm.model.Page;

/**
 * @Description 设备流量
 * @author josen.yang
 * @date Jan 5, 2017 5:52:25 PM
 */

public interface DeviceFluxService {
	
	/**
     * 保存/更新总流量
     */
    int saveSumFlux(DeviceFluxModel model);

    DeviceFluxModel findDeviceIsAbnormalAndIsReport(DeviceFluxModel model);

    int updaloadFluxDetail(Map<String, Object> map);

    void updateIsReport(DeviceBasicInfoModel basic);

    Page getAllFluxAbListsByMap(Map<String, Object> paramMap);

    List<DeviceFluxModel> getFluxABListsByMap(Map<String, Object> paramMap);

    Page getAppFluxLsit(Map<String, Object> paramMap);

    DeviceFluxModel findPriKey(DeviceBasicInfoModel basic);

}
