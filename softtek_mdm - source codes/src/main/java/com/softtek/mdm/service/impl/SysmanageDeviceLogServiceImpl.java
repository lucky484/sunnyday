package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.dao.DeviceFluxDao;
import com.softtek.mdm.dao.FluxDao;
import com.softtek.mdm.dao.SysmanageDeviceLogDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.DeviceFluxModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SysmanageDeviceLog;
import com.softtek.mdm.service.SysmanageDeviceLogService;

@Service
public class SysmanageDeviceLogServiceImpl implements SysmanageDeviceLogService {

	@Autowired
	private SysmanageDeviceLogDao sysmanageDeviceLogDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	@Autowired
	private FluxDao fluxDao;

    @Autowired
    private DeviceFluxDao deviceFluxDao;

	@Override
	public List<String> getAllDeviceLogType() {
		
		List<String> deviceLogType = sysmanageDeviceLogDao.getAllDeviceLogType();
		return deviceLogType;
	}

	@Override
	public Page queryLogsByParams(Map<String, Object> paramMap) {
		
		Page page = new Page();
		Integer count = sysmanageDeviceLogDao.queryAllCountByParams(paramMap);
		List<SysmanageDeviceLog> list = sysmanageDeviceLogDao.queryDeviceLogList(paramMap);
		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return page;
	}

	@Override
	public Page queryDeviceLog(Map<String, Object> map) {
		Page p = new Page();
		int total = sysmanageDeviceLogDao.queryDeviceLogCountByParams(map);
		p.setData(sysmanageDeviceLogDao.queryDeviceLog(map)); 
		
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	@Override
	public int insertSelective(SysmanageDeviceLog record) {
		return sysmanageDeviceLogDao.insertSelective(record);
	}

	@Override
	public String queryLogIsExite(Integer deviceId) {
		
		return sysmanageDeviceLogDao.queryLogIsExite(deviceId);
	}

	@Override
	public Page findRecordWithDeviceId(Integer did, Integer start, Integer length) {
		if(did!=null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("did", did);
			map.put("pageNum", start);
			map.put("pageSize", length);
			List<SysmanageDeviceLog> list=sysmanageDeviceLogDao.findRecordsByMap(map);
			Page page = new Page();
			page.setData(list);
			int total=sysmanageDeviceLogDao.findRecordsCountByDeviceId(did);
			page.setRecordsFiltered(total);
			page.setRecordsTotal(total);
			return page;
		}
		return null;
	}
	
    // 每天23：55执行定时任务
		@Override
		public void deleteLogJob() {
        // 删除1个月前日志
			sysmanageDeviceLogDao.deleteSysmanagelogJob();
			sysmanageDeviceLogDao.deleteDevicelogJob();
			sysmanageDeviceLogDao.deleteSecurityLogJob();
			sysmanageDeviceLogDao.deleteNetbehaviorlogJob();
			
        // 自动备份数据统计
			userDao.autoBackupUsers();
			deviceBasicInfoDao.autoBackupDevice();
			fluxDao.autoBackupFlux();
        // 删除30天前的数据
        fluxDao.deleteFluxStatisJob();
        deviceBasicInfoDao.deleteDeviceStatisJob();
        userDao.deleteUserStatisJob();
        // ============================分析数据查看是否有异常流量的人员并作标=====================
        // 首先查询出符合分析数据的人员list
        List<DeviceFluxModel> list = deviceFluxDao.findAllTodyFlux();
        deviceFluxDao.deleteFluxJob();
        // 判断是否有符合的List
        if (CollectionUtils.isNotEmpty(list)) {
            // 然后针对每个人的30天数据进行计算是否流量异常(最少10天)
            for (DeviceFluxModel model : list) {
                // 获得sn号和用户名 根据sn号查询当月所有的流量记录
                List<Double> fluxlist = deviceFluxDao.findAllFluxBySnAndUserId(model.getSn(), model.getUserId());
                if (CollectionUtils.isNotEmpty(fluxlist)) {
                    // 当天的流量
                    Double todayflux = fluxlist.get(0);
                    // 除了当天之前的所有流量总和
                    Double sumFlux = 0.00;
                    for (int i = 1; i < fluxlist.size(); i++) {
                        sumFlux += fluxlist.get(i);
                    }
                    // 平均值
                    Double averageFlux = sumFlux / (fluxlist.size() - 1);
                    // 安全值是平均值的两倍 低于2倍不作异常 可配置
                    // =======================================配置异常倍率处================================
                    Double safeFlux =averageFlux * 2;
                    // =======================================配置异常倍率处================================
                    // 这里等待以后需求改变
                    if (todayflux > safeFlux) {
                        // 如果当天值大于安全值 则在当天做好异常标记
                        deviceFluxDao.addAbnormal(model.getSn(), model.getUserId());
                    }
                }
            }
        }
        // ============================分析数据查看是否有异常流量的人员并作标========================
		}
	
}
