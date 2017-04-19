package com.hd.pfirs.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao2.CollectCountDao;
import com.hd.pfirs.model.CollectCountModel;
import com.hd.pfirs.model.IntegratedQueryMessageModel;
import com.hd.pfirs.service.CollectCountService;

@Service
@Transactional(value = "insurance", rollbackFor = Exception.class)
public class CollectCountServiceImpl implements CollectCountService {
	
	@Autowired
	private CollectCountDao collectCountDao;

	@Override
	public int getidCardInfoCompareResult(String idCardNo,String compareBaseID,String colum) {
		// TODO Auto-generated method stub
		return collectCountDao.getidCardInfoCompareResult(idCardNo,compareBaseID,colum);
	}

	@Override
	public List<CollectCountModel> getidCardInfoCompareResultList(String idCardNoList){
		// TODO Auto-generated method stub
		return collectCountDao.getidCardInfoCompareResultList(idCardNoList);
	}

	@Override
	public List<CollectCountModel> getIdCardInfoCompareResultType(String idCardNo) {
		// TODO Auto-generated method stub
		return collectCountDao.getIdCardInfoCompareResultType(idCardNo);
	}

	@Override
	public List<IntegratedQueryMessageModel> getCompareResult(String ctrlBaseId) {
		// TODO Auto-generated method stub
		return collectCountDao.getCompareResult(ctrlBaseId);
	}

	@Override
	public List<CollectCountModel> getIdCardInfoCompareResultTypeByT_QB_LK_LKBK(String idCardNo){
		return collectCountDao.getIdCardInfoCompareResultTypeByT_QB_LK_LKBK(idCardNo);
	}

	@Override
	public List<CollectCountModel> getIdCardInfoCompareResultTypeByT_QB_RY_CKRYJBXX(String idCardNo){
		return collectCountDao.getIdCardInfoCompareResultTypeByT_QB_RY_CKRYJBXX(idCardNo);
	}

	@Override
	public List<CollectCountModel> getIdCardInfoCompareResultTypeByjk(String idCardNo,String compareBaseID) {
		// TODO Auto-generated method stub
		return collectCountDao.getIdCardInfoCompareResultTypeByjk(idCardNo,compareBaseID);
	}
	
	

}
