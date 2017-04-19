package com.softtek.mdm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.FluxDao;
import com.softtek.mdm.model.FluxModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.FluxStatisticsService;
import com.softtek.mdm.util.CommUtil;

@Service
public class FluxStatisticsServiceImpl implements FluxStatisticsService {

	@Autowired
	private FluxDao fluxDao;
	
	@Override
	public Page getAllFluxsListsByMap(Map<String, Object> paramMap) {
		
		Page page = new Page();
		Integer count = fluxDao.getFluxsCountByMap(paramMap);
		List<FluxModel> lists = fluxDao.getFluxsListsByMap(paramMap);
        int start = (int) paramMap.get("start");
        if (start == 0) {
            FluxModel model = fluxDao.selectTodayFlux(paramMap);
            String endTime = (String) paramMap.get("endTime");
            if (StringUtils.isNotBlank(endTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(endTime);
                    Date now = new Date();
                    String nowStr = sdf.format(now);
                    Date nowDate = sdf.parse(nowStr);
                    if (date.getTime() >= nowDate.getTime()) {
                        lists.add(0, model);
                        count += 1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                if (model != null) {
                    lists.add(0, model);
                    count += 1;
                }
            }
        }
		page.setRecordsTotal(count);
		page.setData(lists);
		page.setRecordsFiltered(count);
		return page;
		
	}

	@Override
	public List<FluxModel> getFluxsListsByMap(Map<String, Object> paramMap) {
        // List<FluxModel> lists = fluxDao.getChartFluxsListsByMap(paramMap);
		return fluxDao.getExportFluxsListsByMap(paramMap);
	}

    @Override
    public List<FluxModel> getChartFluxsListsByMap(Map<String, Object> paramMap) {
        List<FluxModel> lists = fluxDao.getExportFluxsListsByMap(paramMap);
        FluxModel model = fluxDao.selectTodayFlux(paramMap);
        String endTime = (String) paramMap.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(endTime);
                Date now = new Date();
                String nowStr = sdf.format(now);
                Date nowDate = sdf.parse(nowStr);
                if (date.getTime() >= nowDate.getTime()) {
                    lists.add(0, model);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            lists.add(0, model);
        }
        return lists;
    }

	@Override
	public Page getAllTotalFluxsListsByMap(Map<String, Object> paramMap,String date) {
		
		Page page = new Page();
        // 获取当前的日期，如果是当天就从device_basic_info表中去加载，否则就是从flux表中去加载
		String time = CommUtil.getCurrentDay();
		Integer count = null;
		List<FluxModel> lists = null;
		if(StringUtils.equals(time, date)){
			count = fluxDao.getTotalFluxCountByMap(paramMap);
			lists = fluxDao.getTotalFluxListsByMap(paramMap);
		}else{
			count = fluxDao.getTotalFluxsCountByMap(paramMap);
			lists = fluxDao.getTotalFluxsListsByMap(paramMap);
		}
		page.setRecordsTotal(count);
		page.setData(lists);
		page.setRecordsFiltered(count);
		return page;
	}

	@Override
	public List<FluxModel> getTotalFluxsListsByMap(Map<String, Object> paramMap,String create_time) {
		
		String time = CommUtil.getCurrentDay();
		List<FluxModel> lists = null;
		if(StringUtils.equals(time, create_time)){
			lists = fluxDao.getExportTotalFluxListsByMap(paramMap);
		}else{
			lists = fluxDao.getExportTotalFluxsListsByMap(paramMap);
		}
		return lists;
	}

	@Override
	public List<FluxModel> getFluxsChartListsByMap(Map<String, Object> paramMap) {
        List<FluxModel> lists = fluxDao.getChartFluxsListsByMap(paramMap);
        FluxModel model = fluxDao.selectTodayFlux(paramMap);
        String endTime = (String) paramMap.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(endTime);
                Date now = new Date();
                String nowStr = sdf.format(now);
                Date nowDate = sdf.parse(nowStr);
                if (date.getTime() >= nowDate.getTime()) {
                    lists.add(lists.size(), model);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (model != null) {
                lists.add(lists.size(), model);
            }
        }
        return lists;
	}

}
