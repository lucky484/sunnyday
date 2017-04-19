package com.f2b2c.eco.service.market.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.UserBrowerRecordDao;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.service.market.UserBrowerRecordService;

@Service
public class UserBrowerRecordServicepImpl implements UserBrowerRecordService{
    
	@Autowired
	private UserBrowerRecordDao userBrowerRecordDao;
	
	@Override
	public List<BGoodsModel> queryAllBrowerRecord(Map<String,Object> map) {
		
		return userBrowerRecordDao.queryAllBrowerRecord(map);
	}
	
	@Override
	public int queryAllBrowerRecordCount(Map<String, Object> map) {
	  
		return userBrowerRecordDao.queryAllBrowerRecordCount(map);
	}

	@Override
	public List<BGoodsModel> queryBrowerRecordByUserId(Map<String,Object> map) {
		
		return userBrowerRecordDao.queryBrowerRecordByUserId(map);
	}

	@Override
	public List<BGoodsModel> queryShopGoods(Map<String, Object> map) {
		
		return userBrowerRecordDao.queryShopGoods(map);
	}

	@Override
	public int queryShopGoodsCount(Map<String, Object> map) {
		
		return userBrowerRecordDao.queryShopGoodsCount(map);
	}

	@Override
	public List<BGoodsModel> queryAllFeFruit(Map<String, Object> map) {
		
		return userBrowerRecordDao.queryAllFeFruit(map);
	}

	@Override
	public int queryAllFeFruitCount(Map<String, Object> map) {
		
		return userBrowerRecordDao.queryAllFeFruitCount(map);
	}

	@Override
	public List<BGoodsModel> queryFeFruit(Map<String, Object> map) {
		
		return userBrowerRecordDao.queryFeFruit(map);
	}

	@Override
	public int queryFeFruitCount(Integer kindId) {
		
		return userBrowerRecordDao.queryFeFruitCount(kindId);
	}

}
