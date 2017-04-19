/**
 * 
 */
package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.User;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Jan 6, 2016 3:07:34 PM
 */
public interface UserService {
	
	public List<User> getUser(int page,String diId,String username,String name,String department);
	
	public int getCount(String diId,String username,String name,String department);
	
	public int addUser(User user);
	
	public int deleteUser(long diid);
	
	public int changeStatus(long diid,String status);
	
	public User getuser(String diId);
	
	public int updateUser(User user);

	public User getUserByUserId(String userId);

	public User getUserByUserName(String userName);

	public int getCountByRoleId(String roleID);

}
