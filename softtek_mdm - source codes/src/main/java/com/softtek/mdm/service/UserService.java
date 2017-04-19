package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.ExcelInsertUserModel;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserExportModel;
import com.softtek.mdm.model.UserModel;

public interface UserService {
	
	UserModel findUser(String name,Integer orgId);
	
	List<Integer> findIdsBy(List<StructureModel> list,Integer departId);
	
	int save(UserModel entity);
	
	/**
     * 查询user
     * 
     * @param oid
     *            机构id
     * @param structureId
     *            部门id集合包含子部门
     * @return
     */
	Page findUserBysIds(Integer orgId,Integer structureId,Integer start,Integer length);
	
	/**
	 * 
	 * @param list
	 * @param structureId
	 * @param start
	 * @param length
	 * @return
	 */
	Page findUserBysIds(List<StructureModel> list,Integer orgId,Integer structureId,Integer start,Integer length);
	
	/**
	 * 
	 * @param list
	 * @param structureId
	 * @param start
	 * @param length
	 * @return
	 */
	Page findUserBysExpression(List<StructureModel> list, Map<String, Object> paramMap);
	
	/**
     * 查询某个用户的信息
     * 
     * @param id
     * @return
     */
	UserModel findOne(Integer id);
	
	/**
     * 保存用户的信息
     * 
     * @param entity
     * @return
     */
	int update(UserModel entity);
	
	/**
     * 根据主键删除用户
     * 
     * @param id
     * @return
     */
	int deleteWithPk(Integer id);
	
	/**
     * 批量删除用户
     * 
     * @param ids
     * @return
     */
	int deleteWithPKs(List<Integer> ids);
	
	/**
     * 批量激活用户
     * 
     * @param ids
     * @return
     */
	int updateActiveBatch(Integer[] ids);
	
	/**
     * 检测用户名和密码使用正确
     * 
     * @param username
     * @param password
     * @return
     */
	Integer checkUser(String username,Integer orgId);
	
	/**
     * 根据orgId和末级部门id查询所属当前用户的数目
     * 
     * @param map
     * @return
     */
	public int findCountByMap(Integer steId,Integer orgId);
	
	public int findCountByMaps(Integer steId,Integer orgId,UserModel user);
	
	/**
     * 根据orgId和部门id查询所属当前用户信息
     * 
     * @param steId
     * @param orgId
     * @return
     */
	public List<UserModel> findUsersByMap(Integer steId,Integer orgId);

	int findCountByDid(Map<String, Object> map);

	void updatePolicyById(UserModel user);
	
	int findCountByOrgId(Integer orgId,List<Integer> idlist);
	
	List<UserModel> findUserNamesByMaps(Integer orgId,List<Integer> steIds);
	
	UserModel queryUserInfoById(Integer id);
	
	/**
     * 保存用户
     * 
     * @param map
     * @return
     */
	int create(Map<String, Object> map);
	
	int update(Map<String, Object> map);

	int updateUser(UserModel newUser);
	
	int deleteUserRelationInfo(Integer id,Integer orgId);
	
	int deleteUserBatch(List<Integer> ids,Integer orgId);
	
	int promotion(Map<String, Object> map);
	
    /**
     * 将部门管理员降为普通用户
     * 
     * @param mid
     * @param uid
     * @return
     */
	void truncateDepartToCustomer(int mid,int uid);
	
	/**
     * 根据部门ID获得所有用户信息
     * 
     * @param idList
     * @return
     */
	List<UserExportModel> exportUsersById(List<Integer> idList);

	List<String> findAllMember(Integer orgid);
	
    /**
     * 批量导入用户
     * 
     * @param excelList
     * @return
     */
	int importUsers(List<ExcelInsertUserModel> excelList);
	
	int deleteAllByMap(List<StructureModel> list,Map<String, Object> map);
	
	int activeAllByMap(List<StructureModel> list,Map<String, Object> map);

	List<UserModel> getUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getTotalUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getActiveUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getInActiveUsersListsByMap(Map<String, Object> paramMap);

	List<UserModel> getDeleteUsersListsByMap(Map<String, Object> paramMap);
	
	/**
     * 获取用户信息列表
     *
     * @author brave.chen
     * @param userIds
     *            （用户id字符串以,分隔）
     * @return 用户信息列表
     */
	List<UserModel> getUserModels(String userIds);
	
	/**
     * 校验密码
     * 
     * @param userName
     * @param orgId
     * @return
     */
	UserModel checkPassword(String userName,Integer orgId);
	
	public int updateDeviceLoginCount(Integer deviceLoginCount,Integer id);

    List<UserModel> getChartUsersListsByMap(Map<String, Object> paramMap);
    
    /**
     * 尝试进行登录操作
     * @param account 登录操作
     * @param encryPass 登录明文密码
     * @param orgId 所属机构编号
     * @return
     */
    UserModel attemp(String account,String pass,Integer orgId);
}
