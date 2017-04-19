package com.softtek.pst.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.pst.dao.UserDao;
import com.softtek.pst.model.UserModel;
import com.softtek.pst.service.UserService;
import com.softtek.pst.util.Page;

/**
 * 
 * @ClassName: AdminUserServiceImpl
 * @Description: TODO
 * @author: cliff.fan
 * @date: Apr 8, 2016 9:37:53 AM
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserModel login(String userName, String userPassword) {
		return userDao.login(userName, userPassword);
	}

	@Override
	public int addUser(UserModel um) {
		return userDao.addUser(um);
	}

	@Override
	public int deleteUser(long userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public int updateUser(UserModel um) {
		return userDao.updateUser(um);
	}

	@Override
	public Page<UserModel> getUsers(Integer start, Integer length,
			String column, String dir, String search) {
		Page<UserModel> page = new Page<>();
		page.setData(userDao.getUsers(start, length, column, dir, search));
		int total = userDao.getUsersNum(search);
		page.setRecordsTotal(total);
		page.setRecordsFiltered(total);
		return page;
	}

	@Override
	public UserModel getUserById(long userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public List<UserModel> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public int updateLastLoginTime(Date lastLoginTime,String userName) {
		return userDao.updateLastLoginTime(lastLoginTime,userName);
	}

	@Override
	public List<UserModel> getAuthorityByUserId(long userId) {
		
		return userDao.getAuthorityByUserId(userId);
	}

	@Override
	public List<UserModel> queryNotificationUser() {
		
		return userDao.queryNotificationUser();
	}
	
	
}
