package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceFluxModel;

/**
 * @Description
 * @author josen.yang
 * @date Jan 6, 2017 10:35:14 AM
 */

public interface DeviceFluxDao extends CrudMapper<DeviceBasicInfoModel, Integer> {
	
    int saveSumFlux(DeviceFluxModel model);

    int updateSumFlux(DeviceFluxModel model);

    DeviceFluxModel findFluxByUserId(DeviceFluxModel model);

    List<DeviceFluxModel> findAllTodyFlux();

    List<Double> findAllFluxBySnAndUserId(String sn, Integer userId);

    void addAbnormal(@Param(value = "sn") String sn, @Param(value = "userId") Integer userId);

    DeviceFluxModel findDeviceIsAbnormalAndIsReport(DeviceFluxModel model);

    int updaloadFluxDetail(Map<String, Object> map);

    void updateIsReport(DeviceBasicInfoModel basic);

    Integer getFluxAbCountByMap(Map<String, Object> paramMap);

    List<DeviceFluxModel> getFluxAbListsByMap(Map<String, Object> paramMap);

    List<DeviceFluxModel> getFluxAborListsByMap(Map<String, Object> paramMap);

    DeviceFluxModel findPriKey(DeviceBasicInfoModel basic);

    void deleteFluxJob();

}
