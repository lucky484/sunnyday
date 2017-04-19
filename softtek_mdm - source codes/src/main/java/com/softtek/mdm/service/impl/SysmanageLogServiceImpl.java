package com.softtek.mdm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.SysmanageLogDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.SysmanageLog;
import com.softtek.mdm.service.SysmanageLogService;

@Service
public class SysmanageLogServiceImpl implements SysmanageLogService{
	
	    @Autowired
	    private SysmanageLogDao sysmanageLogDao;
	    
	    /**
	     * 查询所有的操作日志
	     */
	    public Page queryAllOperateLog(Map<String, Object> map) {
			Page p = new Page();
//			int total = sysmanageLogDao.queryAllLogCount(orgId);
//			p.setData(sysmanageLogDao.queryAllLog(map));
			
			int total = sysmanageLogDao.queryOptLogCountByParams(map);
			p.setData(sysmanageLogDao.queryOptLogByParams(map));
			p.setRecordsTotal(total);
			p.setRecordsFiltered(total);
			return p;
		}

		@Override
		public Page queryDetailLogByUserId(Integer begin, Integer num,Integer userId) {
			
			Page p = new Page();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("begin", begin);
			map.put("num", num);
			map.put("userId", userId);
			int total = sysmanageLogDao.queryDetailLogByUserIdCount(userId);
			p.setData(sysmanageLogDao.queryDetailLogByUserId(map)); 
			p.setRecordsTotal(total);
			p.setRecordsFiltered(total);
			return p;
		}
       /**
        * 新增日志
        */
		@Override
		public int insertSelective(SysmanageLog record) {
			
			return sysmanageLogDao.insertSelective(record);
		}
}
