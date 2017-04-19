
package com.softtek.mdm.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softtek.mdm.dao.DeviceAppInfoDao;
import com.softtek.mdm.dao.DeviceFluxDao;
import com.softtek.mdm.model.DeviceAppInfoModel;
import com.softtek.mdm.model.DeviceBasicInfoModel;
import com.softtek.mdm.model.DeviceFluxModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceFluxService;

/**
 * @Description 设备流量
 * @author josen.yang
 * @date Jan 5, 2017 5:15:42 PM
 */

@Service("deviceFluxService")
@Transactional
public class DeviceFluxServiceImpl implements DeviceFluxService {

	private static Logger logger = Logger.getLogger(DeviceFluxServiceImpl.class);
	
    @Autowired
    private DeviceFluxDao deviceFluxDao;

    @Autowired
    private DeviceAppInfoDao deviceAppInfoDao;
	
    @Override
    public int saveSumFlux(DeviceFluxModel model) {
        int number = 0;
        // 查询当天设备是否存储过上流量信息
        DeviceFluxModel basic = deviceFluxDao.findFluxByUserId(model);
        if (basic == null) {
            // 如果没有上传过 新增一条当天流量记录
            number = deviceFluxDao.saveSumFlux(model);
        } else {
            // 如果上传过 实时更新当天流量
            model.setId(basic.getId());
            number = deviceFluxDao.updateSumFlux(model);
        }
        return number;
    }

    /**
     * 查询设备用户上次使用流量是否异常 Description
     * 
     * @param model
     * @return
     * @see com.softtek.mdm.service.DeviceFluxService#FindDeviceIsAbnormalAndIsReport(com.softtek.mdm.model.DeviceFluxModel)
     */
    @Override
    public DeviceFluxModel findDeviceIsAbnormalAndIsReport(DeviceFluxModel model) {
        return deviceFluxDao.findDeviceIsAbnormalAndIsReport(model);
    }

    /**
     * 根据用户ID和sn号 记录 Description
     * 
     * @param userId
     * @param sn
     * @param deviceAppInfoList
     * @return
     * @see com.softtek.mdm.service.DeviceFluxService#updaloadFluxDetail(java.lang.Integer,
     *      java.lang.String, java.util.List)
     */
    @Override
    public int updaloadFluxDetail(Map<String, Object> map) {
        return deviceFluxDao.updaloadFluxDetail(map);
    }

    @Override
    public void updateIsReport(DeviceBasicInfoModel basic) {
        deviceFluxDao.updateIsReport(basic);
    }

    @Override
    public Page getAllFluxAbListsByMap(Map<String, Object> paramMap) {
        Page page = new Page();
        Integer count = deviceFluxDao.getFluxAbCountByMap(paramMap);
        List<DeviceFluxModel> lists = deviceFluxDao.getFluxAbListsByMap(paramMap);
        page.setRecordsTotal(count);
        page.setData(lists);
        page.setRecordsFiltered(count);
        return page;
    }

    @Override
    public List<DeviceFluxModel> getFluxABListsByMap(Map<String, Object> paramMap) {
        return deviceFluxDao.getFluxAborListsByMap(paramMap);
    }

    @Override
    public Page getAppFluxLsit(Map<String, Object> paramMap) {
        Page page = new Page();
        Integer count = deviceAppInfoDao.getAppFluxLsitCount(paramMap);
        List<DeviceAppInfoModel> lists = deviceAppInfoDao.getAppFluxLsit(paramMap);
        page.setRecordsTotal(count);
        page.setData(lists);
        page.setRecordsFiltered(count);
        return page;
    }

    @Override
    public DeviceFluxModel findPriKey(DeviceBasicInfoModel basic) {
        return deviceFluxDao.findPriKey(basic);
    }
	
}