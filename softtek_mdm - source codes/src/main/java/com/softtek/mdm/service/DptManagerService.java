package com.softtek.mdm.service;

import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;

/**
 * @author josen.yang
 *
 */
public interface DptManagerService {
     //根据机构ID查询所有的部门管理员
	/**
	 * 查询user
	 * @param oid 机构id
	 * @param structureId 部门id集合包含子部门
	 * @return
	 */
	public Page findAllByOrgID(Integer orgId,Integer start,Integer length);
	/**
	 * 所有的数据记录根据机构id
	 * @param orgId
	 * @return
	 */
	Integer AllCountByOrgId(Integer orgId);
	/**
	 * 查询某个管理员的信息
	 * @param id
	 * @return
	 */
	UserRoleDepartmentModel findOne(Integer id);
	/**
	 * 保存用户的信息
	 * @param entity
	 * @return
	 */
	Map<String, Object> update(UserRoleDepartmentModel entity,UserModel user);
	
	public int deleteUrdByUrd(Integer id) ;
	
	public int delete(Integer uid,Integer urd) ;
	
	public int deleteUdByUrd(Integer id) ;
	
	public UserRoleDepartmentModel queryRoleByUid(Integer id);
	
	public String findRoleNameById(Integer id);
	
	/**
	 * 根据参数对象查询满足条件的分页对象
	 *
	 * @author brave.chen
	 * @param map 参数map
	 * @return 分页对象
	 */
	public Page queryByParamsMap(Map<String, Object> map);
	/**
	 * @param id
	 * @return
	 */
	public Integer queryAuthByUid(Integer id);
	
}
