package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.BlackWhiteListUrl;
import com.softtek.mdm.model.DeptNetBwListRelation;
import com.softtek.mdm.model.NetBehaviorBlackWhiteList;
import com.softtek.mdm.util.DataGridModel;

/**
 * 上网行为dao处理类
 * 
 * date: Apr 12, 2016 5:07:42 PM
 *
 * @author brave.chen
 */
public interface NetBehaviorDao
{

    /**
     * 新增黑白名单对象
     *
     * @author brave.chen
     * @param netBehaviorBlackWhiteList 黑白名单对象
     * @return 
     */
    Integer addNetBehaviorBlackWhiteList(NetBehaviorBlackWhiteList netBehaviorBlackWhiteList);
    
    /**
     * 新增黑白名单监测url对象列表
     *
     * @author brave.chen
     * @param blackWhiteListUrls 黑白名单监测url对象列表
     */
    void addBlackWhiteListUrls(List<BlackWhiteListUrl> blackWhiteListUrls);
    
    /**
     * 根据黑白名单对象id删除黑白名单对象
     *
     * @author brave.chen
     * @param bwListId 黑白名单对象ID
     * @return 返回影响行数
     */
    int delNetBehaviorBlackWhiteList(String bwListId);
    
    /**
     * 根据黑白名单id删除监测的url列表
     * @author brave.chen
     * @param blackWhiteListId 黑白名单id
     */
    void delBlackWhiteListUrlsById(String blackWhiteListId);
    
    /**
     * 更新黑白名单对象
     *
     * @author brave.chen
     * @param netBehaviorBlackWhiteList
     */
    void updateNetBehaviorBlackWhiteList(NetBehaviorBlackWhiteList netBehaviorBlackWhiteList);
    
    /**
     * 根据黑白名单类型查询所有的黑名单或者白名单 
     *
     * @author brave.chen
     * @return 根据黑白名单类型和组织机构id返回对应的黑白名单列表
     */
    List<NetBehaviorBlackWhiteList> queryBwListWithCondition(Integer type, Integer organizationId);
    
    /**
     * 根据黑白名单id查询黑白名单对象信息
     *
     * @author brave.chen
     * @param bwListId 黑白名单对象id
     * @return 黑白名单对象
     */
    NetBehaviorBlackWhiteList queryNetBehaviorBlackWhiteList(String bwListId);
    
    /**
     * 新增黑白名单监测url对象列表
     *
     * @author brave.chen
     * @param blackWhiteListUrls 黑白名单监测url对象列表
     */
    void updateBlackWhiteListUrls(List<BlackWhiteListUrl> blackWhiteListUrls);
    
    /**
     * 分页查询黑白名单列表
     *
     * @author brave.chen
     * @return 返回黑白名单那列表
     */
    List<NetBehaviorBlackWhiteList> queryBlackWhiteListByPageParams(Map<String, Object> paramMap);

    /**
     * 根据参数查询符合条件黑白名单列表数
     * @author brave.chen
     * @param paramMap 参数map
     * @return 满足条件的数量
     */
    Integer queryAllCountByParams(Map<String, Object> paramMap);
    
    /**
     * 根据黑名单idlist获取url地址
     * @param list
     * @return
     */
    List<BlackWhiteListUrl> selectBWUrlListByIdList(DataGridModel params);
    
    /**
     * 根据策略id获取最新的黑白名单url list
     * @param policyId:策略id
     * @return 返回url列表
     */
    List<BlackWhiteListUrl> selectBWUrlListByPolicyId(Integer policyId);
    
    /**
     * 根据黑白名单名称获取黑白名单数量查询黑白名单对象
     * @param bWListName 黑白名单名称
     * @return 黑白名单对象
     */
    NetBehaviorBlackWhiteList queryBwListByName(String bWListName);
    
    /**
     * 根据黑白名单名称获取黑白名单数量查询黑白名单对象
     * @param bWListName 黑白名单名称
     * @return 黑白名单对象
     */
    NetBehaviorBlackWhiteList queryBwListByNameAndOrgId(String bWListName,Integer orgId);

    /**
     * 
     * 添加部门和网页黑白名单关联关系
     *
     * @author brave.chen
     * @param relationList 关联关系对象
     */
	void addDeptBwListRelation(List<DeptNetBwListRelation> relationList);
	
	/**
	 * 查询黑白名单部门关联关系对象列表
	 *
	 * @author brave.chen
	 * @param deptIdList 部门ID列表
	 * @return 黑白名单部门关联关系对象列表
	 */
	List<DeptNetBwListRelation> queryDeptBwListRelation(List<Integer> deptIdList);
	
	/**
	 * 根据黑白名名单ID删除关联关系对象
	 *
	 * @author brave.chen
	 * @param blackWhiteListId 黑白名单ID
	 */
	void deleteDeptBwListRelation(Integer blackWhiteListId);
}