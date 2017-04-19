package com.softtek.pst.service;

import java.util.Date;
import java.util.List;

import com.softtek.pst.model.UserModel;
import com.softtek.pst.util.Page;

/**
 * 
 * @ClassName: AdminUserService
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 8, 2016 9:36:15 AM
 */
public interface UserService {
	
	public UserModel login(String userName, String userPassword);
	
	public int addUser(UserModel um); 
	
	public int deleteUser(long userId);
	
	public int updateUser(UserModel um);
	
	public Page<UserModel> getUsers(Integer start,Integer length,String column,String dir,String search);
	
	public UserModel getUserById(long userId);
	
	public List<UserModel> getAllUser();

	public int updateLastLoginTime(Date lastLoginTime,String userName);
	
	public List<UserModel> getAuthorityByUserId(long userId);
	
	public List<UserModel> queryNotificationUser();
}
