package com.softtek.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.UserDepartmentDao;
import com.softtek.mdm.dao.UserRoleDepartmentDao;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.UserDepartmentService;

@Service
public class UserDepartmentServiceImpl implements UserDepartmentService {

	@Autowired
	private UserDepartmentDao userDepartmentDao;
	@Autowired
	private UserRoleDepartmentDao userRoleDepartmentDao;
	
	@Override
	public int save(UserDepartmentModel entity) {
		return userDepartmentDao.save(entity);
	}


	@Override
	public int truncateWithUserId(Integer id) {
		UserRoleDepartmentModel userRoleDepartment=userRoleDepartmentDao.findByUserId(id);
		if(userRoleDepartment!=null){
			return userDepartmentDao.truncateWithFId(userRoleDepartment.getId());
		}
		return 0;
		
	}


	@Override
	public List<UserDepartmentModel> findByFId(Integer fid) {
		return (List<UserDepartmentModel>) userDepartmentDao.findByFId(fid);
	}




	@Override
	public int truncateWithUrdId(Integer urdid) {
		return  userDepartmentDao.truncateWithUrdId(urdid);
	}


	@Override
	public String findNameById(int parseInt) {
		return userDepartmentDao.findDptNameById(parseInt);
	}

	
}
