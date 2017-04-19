/**
 * 
 */
package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.UserDao;
import com.hd.pfirs.model.User;
import com.hd.pfirs.service.UserService;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Jan 6, 2016 3:08:20 PM
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getUser(int page, String diId, String username,
			String name, String department) {
		return userDao.getUser(page, diId, username, name, department);
	}

	@Override
	public int getCount(String diId, String username, String name,
			String department) {
		return userDao.getCount(diId, username, name, department);
	}

	@Override
	public int deleteUser(long diid) {
		return userDao.deleteUser(diid);
	}

	@Override
	public int changeStatus(long diid, String status) {
		return userDao.changeStatus(diid, status);
	}

	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}
	
	@Override
	public User getuser(String diId) {
		return userDao.getuser(diId);
	}
	
	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public User getUserByUserId(String userId)
	{
		return userDao.getuserByUserId(userId);
	}

	@Override
	public User getUserByUserName(String userName)
	{
		return userDao.getUserByUserName(userName);
	}

	@Override
	public int getCountByRoleId(String roleID)
	{
		return userDao.getCountByRoleId(roleID);
	}

}
