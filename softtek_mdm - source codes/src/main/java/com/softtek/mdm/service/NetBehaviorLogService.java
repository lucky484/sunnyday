package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.NetBehaviorLogInfoModel;
import com.softtek.mdm.model.Page;

/**
 * 上网行为日志服务接口
 * date: Apr 13, 2016 9:20:11 AM
 *
 * @author brave.chen
 */
public interface NetBehaviorLogService
{
    /**
     * 保存上网行为日志信息 
     *
     * @author brave.chen
     * @param info 上网行为日志对象
     */
    void saveNetBehaviorLogInfo(List<NetBehaviorLogInfoModel> infos);
    
    /**
     * 根据上网行为日志对象（内部属性）查询页数和每页显示条数查询上网行为日志列表
     *
     * @author brave.chen
     * @param paramMap 参数map
     * @return 页面对象
     */
    Page queryLogsByParams(Map<String, Object> paramMap);
}

