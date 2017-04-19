/**
 * Project Name:com.softtek.mdm
 * File Name:NetBehaviorLogServiceImpl.java
 * Package Name:com.softtek.mdm.service.impl
 * Date:Apr 15, 201612:47:20 AM
 * Copyright (c) 2016, brave.chen@softtek.com All Rights Reserved.【请根据具体情况修改模板】
 *
 */

package com.softtek.mdm.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softtek.mdm.dao.NetBehaviorLogDao;
import com.softtek.mdm.model.NetBehaviorLogInfoModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.service.NetBehaviorLogService;

/**
 * 上网行为日志服务实现类
 * date: Apr 15, 2016 12:47:20 AM
 *
 * @author brave.chen
 */
@Service("netBehaviorLogService")
@Transactional
public class NetBehaviorLogServiceImpl  implements NetBehaviorLogService
{

    /**
     * 上网服务日志dao
     */
    @Autowired
    private NetBehaviorLogDao netBehaviorLogDao;
    
    /**
     * 保存上网行为日志
     * @see com.softtek.mdm.service.NetBehaviorLogService#saveNetBehaviorLogInfo(com.softtek.mdm.model.NetBehaviorLogInfoModel)
     */
    @Override
    public void saveNetBehaviorLogInfo(List<NetBehaviorLogInfoModel> infos)
    {
        netBehaviorLogDao.saveNetBehaviorLogInfo(infos);

    }

    /**
     * 查询上网行为日志
     * @see com.softtek.mdm.service.NetBehaviorLogService#queryLogsByParams(java.util.Map)
     */
    @Override
    public Page queryLogsByParams(Map<String, Object> paramMap)
    {
        Page page = new Page();
        Integer count = netBehaviorLogDao.queryAllCountByParams(paramMap);
        List<NetBehaviorLogInfoModel> list = (List<NetBehaviorLogInfoModel>) netBehaviorLogDao
                .queryNetBehaviorLogList(paramMap);
        page.setData(list);
        page.setRecordsFiltered(count);
        page.setRecordsTotal(count);
        return page;
    }
}

