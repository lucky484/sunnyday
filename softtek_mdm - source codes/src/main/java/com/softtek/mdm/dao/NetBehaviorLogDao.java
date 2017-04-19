/**
 * Project Name:com.softtek.mdm
 * File Name:NetBehaviorLogDao.java
 * Package Name:com.softtek.mdm.dao
 * Date:Apr 15, 201612:49:39 AM
 * Copyright (c) 2016, brave.chen@softtek.com All Rights Reserved.【请根据具体情况修改模板】
 *
 */

package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.bean.NetBean;
import com.softtek.mdm.model.NetBehaviorLogInfoModel;
import com.softtek.mdm.util.DataGridModel;

/**
 * Description: 类描述
 * date: Apr 15, 2016 12:49:39 AM
 *
 * @author brave.chen
 * @version 版本迭代编号，不同迭代版本请自行在模板中替换
 * @since JDK 1.6【请根据程序依赖jdk版本到模板中自行修改】
 */
public interface NetBehaviorLogDao
{

    /**
     * 查询日志总数
     *
     * @author brave.chen
     * @param paramMap 参数map
     * @return 数量
     */
    Integer queryAllCountByParams(Map<String, Object> paramMap);

    /**
     * 查询日志列表
     *
     * @author brave.chen
     * @param paramMap 参数map
     * @return 日志对象列表
     */
    List<NetBehaviorLogInfoModel> queryNetBehaviorLogList(Map<String, Object> paramMap);

    /**
     * 保存日志对象
     *
     * @author brave.chen
     * @param info 日志对象信息
     */
    void saveNetBehaviorLogInfo(List<NetBehaviorLogInfoModel> infos);
    
    /**
     * 返回上网列表
     * @param params:参数
     * @return 返回上网bean列表
     */
    List<NetBean> getNetList(DataGridModel params);
    
    /**
     * 获取上网列表大小
     * @param params:参数
     * @return 返回上网统计大小
     */
    int getNetSize(DataGridModel params);
    
    /**
     * 根据机构id获取网站分类对应的访问次数
     * @param params:参数
     * @return 返回网站分类对应的访问次数
     */
    List<NetBean> getWebsiteCountByOrgId(DataGridModel params);
    
    /**
     * 获取地址列表
     * @param params:参数
     * @return 返回url列表
     */
    List<NetBean> getUrlList(DataGridModel params);
     
    /**
     * 根据参数获取url列表大小
     * @param params:参数
     * @return 返回url列表大小
     */
    int getUrlSize(DataGridModel params);
    
    /**
     * 根据上网行为列表
     * @return 返回上网行为列表
     */
    List<NetBehaviorLogInfoModel> getNetBehaviorLog();
    
    /**
     * 批量更新上网行为
     * @param list:上网行为列表
     * @return 返回更新结果
     */
    int updateNetBehaviorLogInfo(List<NetBehaviorLogInfoModel> list);
}