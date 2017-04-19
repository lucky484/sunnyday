package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.bean.NetBean;
import com.softtek.mdm.model.Page;

/**
 * 上网统计service
 * 
 * @author jane.hui
 *
 */
public interface NetStatisticsService {

	/**
     * 获取每个网站分类对应的网站访问次数
     * 
     * @param depardIds
     * 
     * @param orgId:机构id
     * @return 返回网站分类对应的访问次数
     */
    List<NetBean> getCountByWebsite(Integer orgId, List<Integer> depardIds);
	
    /**
     * 根据机构id上网统计
     * 
     * @param depardIds
     * @param orgId:机构id
     * @return 返回上网统计
     */
    Page getNetList(Integer orgId, String type, Integer begin, Integer num, List<Integer> depardIds);
    
    /**
     * 返回url列表
     * 
     * @param depardIds
     * 
     * @param orgId:机构id
     * @param name：用户名称
     * @param type:网站分类
     * @param begin:开始页
     * @param num:分页大小
     * @return 返回
     */
    Page find(Integer orgId, String name, Integer type, Integer begin, Integer num, List<Integer> depardIds);
}