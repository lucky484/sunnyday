package com.softtek.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.AndroidDeviceUsersDao;
import com.softtek.mdm.model.AndroidDeviceUsers;
import com.softtek.mdm.service.AndroidDeviceUsersService;

@Service
public class AndroidDeviceUsersServiceImpl implements AndroidDeviceUsersService {

	@Autowired
	private AndroidDeviceUsersDao androidDeviceUsersDao;
	@Override
	public AndroidDeviceUsers findOneByUserIdLast(Integer uid) {
		if(uid!=null){
			return androidDeviceUsersDao.findOneByUserIdLast(uid);
		}
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(AndroidDeviceUsers record) {
		if(record!=null){
			return androidDeviceUsersDao.updateByPrimaryKeySelective(record);
		}
		return 0;
	}

	@Override
	public int insert(AndroidDeviceUsers record) {
		return androidDeviceUsersDao.insert(record);
	}

}
