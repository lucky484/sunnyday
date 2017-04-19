package com.softtek.pst.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.softtek.pst.model.UserModel;

/**
 * 
 * @ClassName: AdminUserDao
 * @Description: 登录
 * @author: cliff.fan
 * @date: Apr 8, 2016 9:33:59 AM
 */
public interface UserDao {

	public UserModel login(@Param(value = "username") String userName, @Param(value = "password") String userPassword);

	public UserModel getUserById(long userId);
	
	public List<UserModel> getUsersByRole(long roleId);
	
	public List<UserModel> getAllUser();
	
	public int addUser(UserModel um); 
	
	public int deleteUser(long userId);
	
	public int updateUser(UserModel um);
	
	public int getUsersNum(String search);
	
	public List<UserModel> getUsers(@Param(value = "start") Integer start, @Param(value = "length") Integer length, @Param(value = "col") String column, @Param(value = "dir") String dir,
			@Param(value = "search") String search);
	
	public int updateLastLoginTime(@Param(value = "lastLoginTime")Date lastLoginTime,@Param(value = "userName")String userName);

	public List<UserModel> getAuthorityByUserId(long userId);
	
	public List<UserModel> queryNotificationUser();
}
