package com.softtek.mdm.service;

import java.util.List;

import com.softtek.mdm.model.UserDepartmentModel;

public interface UserDepartmentService {

	int save(UserDepartmentModel entity);
	
	int truncateWithUserId(Integer id);
	
	List<UserDepartmentModel> findByFId(Integer fid);

	int truncateWithUrdId(Integer urdid);

	String findNameById(int parseInt);
}
