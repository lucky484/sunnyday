package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeviceAppInfoModel;

public interface DeviceAppInfoDao extends CrudMapper<DeviceAppInfoModel, Integer> {

	Collection<DeviceAppInfoModel> findByPage(Map<String, Object> maps);
	
	/**
     * 根据设备id查询总记录数
     * 
     * @param did
     * @return
     */
	Integer findCountByDid(Integer did);
	
	/**
     * 根据设备id删除设备应用信息
     * 
     * @param did
     * @return
     */
	Integer truncateWithDeviceId(Integer did);
	
	/**
     * 批量保存记录
     * 
     * @param list
     * @return 受影响的行数
     */
	int saveRecordsBatch(List<DeviceAppInfoModel> list);

    /**
     * 获得APP记录总计
     * 
     * @Description
     * @author josen.yang
     * @param paramMap
     * @return
     */
    Integer getAppFluxLsitCount(Map<String, Object> paramMap);

    /**
     * 获得APP记录(分页)
     * 
     * @Description
     * @author josen.yang
     * @param paramMap
     * @return
     */
    List<DeviceAppInfoModel> getAppFluxLsit(Map<String, Object> paramMap);

}
