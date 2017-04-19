/**
 * 
 */
package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.User;

/**
 * @ClassName: UserDao
 * @Description: 用户管理界面
 * @author light.chen
 * @date Jan 6, 2016 3:06:16 PM
 */
public interface UserDao {
	//获取用户管理表数据
	public List<User> getUser(@Param(value = "page")int page,
			@Param(value = "diId")String diId,
			@Param(value = "username")String username,
			@Param(value = "name")String name,
			@Param(value = "department")String department);
	//获取用户管理表数据总数
	public int getCount(@Param(value = "diId")String diId,
			@Param(value = "username")String username,
			@Param(value = "name")String name,
			@Param(value = "department")String department);
	//增加用户数据
	public int addUser(User user);
	//删除用户数据
	public int deleteUser(long diid);
	//修改用户管理表状态字段
	public int changeStatus(@Param(value = "diid")long diid,
			@Param(value = "status")String status);
	//查看用户数据
	public User getuser(String diId);
	//修改用户数据
	public int updateUser(User user);
	
	/**
	 * 根据用户ID获取用户对象
	 * @param userId 用户ID
	 * @return 用户对象
	 */
	public User getuserByUserId(String userId);
	
	/**
	 * 根据用户名获取用户对象
	 * @param userName 用户名
	 * @return 用户对象
	 */
	public User getUserByUserName(String userName);
	
	/**
	 * 获取角色关联的用户数量
	 * @param roleID 角色ID
	 * @return 关联的用户数量
	 */
	public int getCountByRoleId(String roleID);

}
