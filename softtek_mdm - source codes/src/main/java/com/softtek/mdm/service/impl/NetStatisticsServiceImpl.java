package com.softtek.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.bean.NetBean;
import com.softtek.mdm.dao.NetBehaviorLogDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.NetStatisticsService;
import com.softtek.mdm.util.DataGridModel;

/**
 * 上网统计管理
 * 
 * @author jane.hui
 *
 */
@Service("netStatisticsService")
public class NetStatisticsServiceImpl implements NetStatisticsService{

    @Autowired
    private NetBehaviorLogDao netBehaviorLogDao;
    
    /**
     * 获取上网管理
     * 
     * @param orgId:机构id
     * @return 返回上网行为
     */
    @Override
    public Page getNetList(Integer orgId, String type, Integer begin, Integer num, List<Integer> depardIds) {
        Page p = new Page();
        DataGridModel params = new DataGridModel();
        params.setBegin(begin);
        params.setNum(num);
        params.getParams().put("orgId", orgId);
        params.getParams().put("type", type);
        params.getParams().put("idlist", depardIds);
        int total = netBehaviorLogDao.getNetSize(params);
        p.setData(netBehaviorLogDao.getNetList(params));
        p.setRecordsTotal(total);
        p.setRecordsFiltered(total);
        return p;
    }

    /**
     * 根据机构id获取网站分类
     * 
     * @param orgId:机构id
     * @return 返回网站分类对应的访问次数
     */
    @Override
    public List<NetBean> getCountByWebsite(Integer orgId, List<Integer> depardIds) {
        DataGridModel params = new DataGridModel();
        params.getParams().put("orgId", orgId);
        params.getParams().put("idlist", depardIds);
        List<NetBean> list  = netBehaviorLogDao.getWebsiteCountByOrgId(params);
        return list;
    }

    /**
     * 获取url列表
     * 
     * @param orgId:机构id
     * @param name:用户名称
     * @param type:网站类型
     * @param begin:开始页
     * @param num:页数大小
     * @return 返回url列表
     */
	@Override
    public Page find(Integer orgId, String name, Integer type, Integer begin, Integer num, List<Integer> idlist) {
		Page p = new Page();
		DataGridModel params = new DataGridModel();
        params.setBegin(begin);
        params.setNum(num);
        params.getParams().put("orgId", orgId);
        params.getParams().put("type", type);
        params.getParams().put("name", name);
        params.getParams().put("idlist", idlist);
        int total = netBehaviorLogDao.getUrlSize(params);
        p.setData(netBehaviorLogDao.getUrlList(params));
        p.setRecordsTotal(total);
        p.setRecordsFiltered(total);
        return p;
	}
}