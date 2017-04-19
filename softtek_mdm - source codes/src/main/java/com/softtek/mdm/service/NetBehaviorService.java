package com.softtek.mdm.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.BlackWhiteListUrl;
import com.softtek.mdm.model.NetBehaviorBlackWhiteList;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.web.http.ResultDTO;

/**
 * 上网行为服务类 date: Apr 12, 2016 3:53:25 PM
 *
 * @author brave.chen
 */
public interface NetBehaviorService
{
    /**
     * 新增黑白名单对象
     *
     * @author brave.chen
     * @param paramsMap
     *            参数map
     */
	ResultDTO addNetBehaviorBlackWhiteList(Map<String, Object> paramsMap);
    
    /**
     * 新增黑白名单监测url对象列表
     *
     * @author brave.chen
     * @param blackWhiteListUrls
     *            黑白名单监测url对象列表
     */
    void addBlackWhiteListUrls(List<BlackWhiteListUrl> blackWhiteListUrls);
    
    /**
     * 根据黑白名单对象id删除黑白名单对象
     *
     * @author brave.chen
     * @param bwListId
     *            黑白名单对象ID
     */
    ResultDTO delNetBehaviorBlackWhiteList(String bwListId);
    
    /**
     * 根据黑白名单id删除监测的url列表
     * 
     * @author brave.chen
     * @param blackWhiteListId
     *            黑白名单id
     */
    void delBlackWhiteListUrlsById(String blackWhiteListId);
    
    /**
     * 更新黑白名单对象
     *
     * @author brave.chen
     * @param netBehaviorBlackWhiteList
     *            网络行为黑白名单对象
     */
    void updateNetBehaviorBlackWhiteList(NetBehaviorBlackWhiteList netBehaviorBlackWhiteList);
    
    /**
     * 根据黑白名单类型查询所有的黑名单或者白名单
     *
     * @author brave.chen
     * @param type
     *            黑白名单类型（0：黑名单；1：白名单）
     * @param organizationId
     *            组织机构id
     * @return 根据黑白名单类型和组织机构id返回对应的黑白名单列表
     */
    List<NetBehaviorBlackWhiteList> queryBwListWithCondition(Integer type, Integer organizationId);
    
    /**
     * 根据黑白名单id查询黑白名单对象信息
     *
     * @author brave.chen
     * @param bwListId
     *            黑白名单对象id
     * @return 黑白名单对象
     */
    NetBehaviorBlackWhiteList queryNetBehaviorBlackWhiteList(String bwListId);

    /**
     * 根据map参数查询分页对象
     * 
     * @author brave.chen
     * @param paramMap
     *            查询参数
     * @return 分页对象
     */
    Page queryBlackWhiteListByParams(Map<String, Object> paramMap);

    /**
     * 根据黑白名单名称获取黑白名单数量查询黑白名单对象
     * 
     * @param bWListName
     *            黑白名单名称
     * @return 黑白名单对象
     */
    NetBehaviorBlackWhiteList queryBwListByName(String bWListName);
    
    /**
     * 根据黑白名单名称获取黑白名单数量查询黑白名单对象
     * 
     * @param bWListName
     *            黑白名单名称
     * @return 黑白名单对象
     */
    NetBehaviorBlackWhiteList queryBwListByName(String bWListName,Integer orgId);
    
    /**
     * 
     * Description:更新黑白名单对象
     *
     * @author brave.chen
     * @param paramMap
     *            参数map
     * @return 更新结果对象
     */
    ResultDTO updateBwList(Map<String, Object> paramMap);

    Map<String, Object> importExcel(InputStream ins, Integer filetype);
}

