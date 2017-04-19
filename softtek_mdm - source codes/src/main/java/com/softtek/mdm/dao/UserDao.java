package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.UserExportModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.util.DataGridModel;

public interface UserDao extends CrudMapper<UserModel, Integer> {
	
	UserModel findUser(String name,Integer orgId);
	
	/**
     * 查询user
     * 
     * @param oid
     *            机构id
     * @param structureId
     *            部门id集合包含子部门
     * @return
     */
	Collection<UserModel> findUserBysIds(Map<String, Object> map);
	
	/**
     * 查询user
     * 
     * @param oid
     *            机构id
     * @param structureId
     *            部门id集合包含子部门
     * @return
     */
	Collection<UserModel> findUserByMap(Map<String, Object> map);

	Collection<UserModel> findUsersInfoByMap(Map<String, Object> map);
	
    /**
     * 根据搜索条件和部门id查询出所有的用户记录 部不分页
     * 
     * @param map
     * @return
     */
	Collection<Integer> findIdsByMap(Map<String, Object> map);
	
	Collection<UserModel> findRecordsByMap(Map<String, Object> map);
	
	int deleteWithPKs(List<Integer> ids);
	
	int truncateWithPKs(List<Integer> ids);
	
	int truncateWithPk(Integer id);
	
    /**
     * 获取该机构下该部门下的总页数
     * 
     * @param orgId
     * @return
     */
	int findAllCount(Map<String, Object> map);
	
	/**
     * 获取该机构下该部门下的总页数
     * 
     * @param orgId
     * @return
     */
	int findCountWithMap(Map<String, Object> map);
	
	int findUserInfoCountWithMap(Map<String, Object> map);
	
	
	int updateActiveBatch(Integer[] ids);

	
	/**
     * 检测用户名，密码，组织机构是否正确
     * 
     * @param username
     * @param password
     * @param orgId
     * @return
     */
	Integer checkUser(String username, Integer orgId);

	Collection<UserModel> queryExistMember(Map<String, Object> map);

	Collection<UserModel> queryNoExistMember(Map<String, Object> map); 
	
	Collection<UserModel> queryNoExistMemberRadio(Map<String, Object> map); 
	
    /**
     * 根据组id和查询查询不存在的用户的最大页数
     * 
     * @param id
     * @return
     */
	int queryNeMemberMaxPage(Map<String, Object> map);
	
    /**
     * 根据组id和查询查询不存在的用户的最大页数（单选）
     * 
     * @param id
     * @return
     */
	int queryNeMemberMaxPageRadio(Map<String, Object> map);
	
    /**
     * 根据组id和查询查询存在的用户的最大页数
     * 
     * @param id
     * @return
     */
	int queryEMemberMaxPage(Map<String, Object> map);
	
    /**
     * 根据组id和index查询指定的10条记录
     * 
     * @param id
     * @return
     */
	Collection<UserModel> queryNoExistMemberPage(Map<String, Object> map); 
	
    /**
     * 根据组id和index查询指定的10条记录
     * 
     * @param id
     * @return
     */
	Collection<UserModel> queryExistMemberPage(Map<String, Object> map);

	Collection<UserModel> queryNoExistMemberPageRadio(Map<String, Object> map);
    
    void updatePolicyById(UserModel user);
    
	/**
     * 根据orgId和部门id查询所属当前用户的数目
     * 
     * @param map
     * @return
     */
	public int findCountByMap(Map<String, Integer> map);
	
	public int findCountByMaps(Map<String, Object> map);
	
	/**
     * 根据orgId和部门id查询所属当前用户信息
     * 
     * @param map
     * @return
     */
	public Collection<UserModel> findUsersByMap(Map<String, Integer> map);
	
	/**
     * 根据用户名查询用户
     * 
     * @param params
     * @return
     */
	List<UserModel> findUserByName(DataGridModel params);
	
	/**
     * 根据部门id查询用户数目
     * 
     * @param ids
     * @return
     */
	int findCountByDid(Map<String, Object> map);
	
	/**
     * 根据orgId查询所属的用户总数
     * 
     * @param orgId
     * @return
     */
	int findCountByOrgId(Map<String,Object> map);
	
	/**
     * 根据组织机构和部门id集合查询用户的id,username,realname
     * 
     * @param map
     * @return
     */
	List<UserModel> findUserNamesByMaps(Map<String, Object> map);

	UserModel queryUserInfoById(Integer id);
	
    /**
     * 根据策略的id查询用户
     * 
     * @param policyId
     * @return
     */
	List<UserModel> queryUserByPolicy(Integer policyId);
	
	UserModel queryPolicyByUserId(Integer userId);

	int updateUser(UserModel newUser);

	List<UserModel> findUserIdByUserName(Map<String, Object> map1);


	List<UserExportModel> exportUsersById(Map<String, Object> map);


	List<String> findAllMember(Integer orgid);

	
	/**
     * 获取激活的用户数
     * 
     * @author brave.chen
     * @return
     */
	int getActiveUserCount();

	int importUsers(Map<String, Object> map);
	
	/**
     * 运维统计部分根据条件查询个数
     * 
     * @param paramMap
     * @return
     */
	Integer getUsersCountByMap(Map<String, Object> paramMap);
	
    /**
     * 运维统计部分根据条件查询
     * 
     * @param paramMap
     * @return
     */
	List<UserModel> getUsersListsByMap(Map<String, Object> paramMap);
	
    UserModel getTodayUsersListsByMap(Map<String, Object> paramMap);

    /**
     * 运维统计部分用户的导出
     * 
     * @param paramMap
     * @return
     */
	List<UserModel> getExportUsersListsByMap(Map<String, Object> paramMap);
	
    /**
     * 用户总数按条件查询个数
     * 
     * @param paramMap
     * @return
     */
	Integer getTotalUsersCountByMap(Map<String, Object> paramMap);
	
    /**
     * 用户总数按条件查询
     * 
     * @param paramMap
     * @return
     */
	List<UserModel> getTotalUsersListsByMap(Map<String, Object> paramMap);
	
    /**
     * 激活用户数条件查询个数
     * 
     * @param paramMap
     * @return
     */
	Integer getActiveUsersCountByMap(Map<String, Object> paramMap);
	
    /**
     * 激活用户数条件查询
     * 
     * @param paramMap
     * @return
     */
	List<UserModel> getActiveUsersListsByMap(Map<String, Object> paramMap);
	
    /**
     * 未激活用户数条件查询个数
     * 
     * @param paramMap
     * @return
     */
	Integer getInActiveUsersCountByMap(Map<String, Object> paramMap);
	
    /**
     * 未激活用户数条件查询
     * 
     * @param paramMap
     * @return
     */
	List<UserModel> getInActiveUsersListsByMap(Map<String, Object> paramMap);
	
    /**
     * 删除用户数条件查询个数
     * 
     * @param paramMap
     * @return
     */
	Integer getDeleteUsersCountByMap(Map<String, Object> paramMap);
	
    /**
     * 删除用户数条件查询
     * 
     * @param paramMap
     * @return
     */
	List<UserModel> getDeleteUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getExportTotalUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getExportActiveUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getExportInActiveUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getExportDeleteUsersListsByMap(Map<String, Object> paramMap);
	
	/**
     * 获取用户信息列表
     *
     * @author brave.chen
     * @param userIdList
     *            （用户id列表）
     * @return 用户信息列表
     */
	List<UserModel> getUserModels(List<String> userIdList);

	/**
     * 
     * @author brave.chen
     * @param map
     *            参数map
     * @return 用户id列表
     */
	List<Integer> getUserIdsByDeptIds(Map<String,Object> map);
	
	public UserModel checkPassword(Map<String,Object> map);
	
	public int updateDeviceLoginCount(Map<String,Object> map);

	/**
     * 自动备份用户运维统计
     */
	void autoBackupUsers();

    List<UserModel> getChartUsersListsByMap(Map<String, Object> paramMap);
    
    int updateConfigFile(UserModel user);

    void deleteUserStatisJob();

	UserModel attemp(Map<String, Object> map);
}
