package com.softtek.mdm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceBasicInfoDao;
import com.softtek.mdm.model.DeviceStatisticsModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.DeviceStatisticsService;
import com.softtek.mdm.util.Constant;

@Service
public class DeviceStatisticsServiceImpl implements DeviceStatisticsService{
	
    private static Logger logger = LoggerFactory.getLogger(UserStatisticsServiceImpl.class);

	@Autowired
	private DeviceBasicInfoDao deviceBasicInfoDao;
	
	@Override
    public Page getAllDeviceListsByMap(Map<String, Object> paramMap, Integer start, Integer length) {
        try {
            Page page = new Page();
            List<DeviceStatisticsModel> lists = new ArrayList<DeviceStatisticsModel>();
            Integer count = deviceBasicInfoDao.getDeviceCountByMap(paramMap);
            String lastDay = (String) paramMap.get("lastDay");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(lastDay);
            Date now = new Date();
            String nowStr = sdf.format(now);
            Date nowDate = sdf.parse(nowStr);
            if (start == 0) {
                DeviceStatisticsModel model = deviceBasicInfoDao.getTodayDeviceByMap(paramMap);
                    if (date.getTime() >= nowDate.getTime()) {
                        lists.add(0, model);
                        count += 1;
                        paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length - 1);
                        paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
                    } else {
                        paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
                        paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
                    }
            } else {
                if (date.getTime() >= nowDate.getTime()) {
                    count += 1;
                    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
                    paramMap.put(Constant.PageRelatedConstant.START_PAGE, start - 1);
                } else {
                    paramMap.put(Constant.PageRelatedConstant.PAGE_OFFSET, length);
                    paramMap.put(Constant.PageRelatedConstant.START_PAGE, start);
                }
            }
            lists.addAll(deviceBasicInfoDao.getDeviceListsByMap(paramMap));
            page.setRecordsTotal(count);
            page.setData(lists);
            page.setRecordsFiltered(count);
            return page;
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
	}

	@Override
	public List<DeviceStatisticsModel> getDeviceListsByMap(Map<String, Object> paramMap) {
        List<DeviceStatisticsModel> list = deviceBasicInfoDao.getChartDeviceListsByMap(paramMap);
	    DeviceStatisticsModel model = deviceBasicInfoDao.getTodayDeviceByMap(paramMap);
        String endTime = (String) paramMap.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(endTime);
                Date now = new Date();
                String nowStr = sdf.format(now);
                Date nowDate = sdf.parse(nowStr);
                if (date.getTime() >= nowDate.getTime()) {
                    list.add(list.size(), model);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            list.add(list.size(), model);
        }

		return list;
	}

    @Override
    public List<DeviceStatisticsModel> geExportListsByMap(Map<String, Object> paramMap) {
        List<DeviceStatisticsModel> list = deviceBasicInfoDao.getExportDeviceListsByMap(paramMap);
        DeviceStatisticsModel model = deviceBasicInfoDao.getTodayDeviceByMap(paramMap);
        String endTime = (String) paramMap.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(endTime);
                Date now = new Date();
                String nowStr = sdf.format(now);
                Date nowDate = sdf.parse(nowStr);
                if (date.getTime() >= nowDate.getTime()) {
                    list.add(0, model);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            list.add(0, model);
        }
        return list;
    }

}
