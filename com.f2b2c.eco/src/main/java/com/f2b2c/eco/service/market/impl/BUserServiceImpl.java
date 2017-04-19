package com.f2b2c.eco.service.market.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BShopInfoDao;
import com.f2b2c.eco.dao.market.BUserDao;
import com.f2b2c.eco.dao.platform.FAuthCodeDao;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.FToBUserModel;
import com.f2b2c.eco.service.market.BUserService;

@Service
public class BUserServiceImpl implements BUserService {

	@Autowired
	private BUserDao bUserDao;
	
	@Autowired
	private FAuthCodeDao fAuthCodeDao;
	
	@Autowired
	private BShopInfoDao bShopInfoDao;
	
	@Override
	public List<BUserModel> getUserListsByfUserId(Integer id) {
		
		return bUserDao.getUserListsByfUserId(id);
	}

	@Override
	public int insertUser(BUserModel bUser) {
		// 保存用户信息
		int result =  bUserDao.insertUser(bUser);
		BShopInfoModel bShopInfo = new BShopInfoModel();
		bShopInfo.setShopName(bUser.getShopName());
		bShopInfo.setAddress(bUser.getAddress());
		bShopInfo.setAuthCode(bUser.getAuthCode());
		bShopInfo.setCreatedUser(bUser.getCreatedUser());
		bShopInfo.setCreatedTime(new Date());
		bShopInfo.setUser(bUser);
		bShopInfo.setUserName(bUser.getRealName());
		bShopInfo.setAreaId(bUser.getAreaId());
		bShopInfo.setLocationX(bUser.getLocationX());
		bShopInfo.setLocationY(bUser.getLocationY());
		bShopInfo.setIsEnable("1");
		bShopInfoDao.insert(bShopInfo);
		if(result == 1){
			// 将授权码变为不可用
			fAuthCodeDao.updateByCode(bUser.getAuthCode());
		}
		return result;
	}

	@Override
	public BUserModel queryUserIsExist(Map<String,Object> map) {
		
		return bUserDao.queryUserIsExist(map);
	}

	@Override
	public int updatePassword(BUserModel bUser) {
		
		return bUserDao.updatePassword(bUser);
	}

	@Override
	public boolean checkIfExist(String account) {
		BUserModel bUserModel = bUserDao.queryUserByAccountName(account);
		if(null != bUserModel){
			return true;
		}else{
			return false;
		}
		
		
	}

	@Override
	public BUserModel queryOne(BUserModel user) {
		if(null != user){
			BUserModel bUserModel = bUserDao.findOne(user);
			return null != bUserModel ? bUserModel : null;
		}
		return null;
	}

	@Override
	public FToBUserModel queryBUserByUserId(Integer userId, Date date) {
		
		return bUserDao.queryBUserByUserId(userId, date);
	}

	@Override
	public BUserModel getBUserByPhone(String phone) {
		
		return bUserDao.queryUserByAccountName(phone);
	}

	/**
	 * 主页统计
	 * 
	 * @return
	 */
	@Override
	public int findAllBUserCount() {
		// TODO Auto-generated method stub
		return bUserDao.findAllBUserCount();
	}

	@Override
	public BUserModel findOneByAccount(String account) {
		return  bUserDao.queryUserByAccountName(account);
	}

	
	
}
