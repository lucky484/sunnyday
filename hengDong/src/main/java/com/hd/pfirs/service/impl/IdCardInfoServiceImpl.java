package com.hd.pfirs.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.controller.IdCardInfoController;
import com.hd.pfirs.dao.IdCardComResultDao;
//import com.hd.pfirs.dao.IdCardComResultDao;
import com.hd.pfirs.dao.IdCardInfoDao;
import com.hd.pfirs.model.IdCardCompWarn;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.IdCardInfoStrModel;
import com.hd.pfirs.service.IdCardInfoService;
import com.hd.pfirs.util.CommUtil;

/**
 * 
 * @author cliff.fan
 *
 */
@Service
public class IdCardInfoServiceImpl implements IdCardInfoService {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IdCardInfoController.class);

	public static int index = 0;

	public static Integer getKey() {
		return index++;
	}

	@Autowired
	private IdCardInfoDao idCardInfoDao;

	@Autowired
	private IdCardComResultDao idCardComResultDao;

	/**
	 * 时间转换
	 */
	@Autowired
	private CommUtil util;

	/**
	 * 这边需要开启事物
	 */
	@Transactional
	public String saveIdCardInfo(IdCardInfoModel model) {
		model.setCardId(CommUtil.getPrimaryKey());
		idCardInfoDao.saveIdCardInfo(model);
		idCardComResultDao.saveIdCardInfo2ComResult(model);
		return null;
	}

	public IdCardInfoModel getIdCardInfoModel() {
		return idCardInfoDao.getIdCardInfoModel();
	}

	public void updateIdCardInfo(long cardId, String relayFlag,Date relayTime,Date updateTime) {
		idCardInfoDao.updateIdCardInfo(cardId, relayFlag,relayTime,updateTime);
	}

	@Override
	public List<IdCardInfoStrModel> getIdCardInfoByCollectTimeList(String deviceCode) {

		return idCardInfoDao.getIdCardInfoByCollectTimeList(deviceCode);
	}

	@Override
	public IdCardCompWarn getIdCardInfoCompResult(String  warningTime,String deviceCode) {
		IdCardCompWarn model = idCardInfoDao.getIdCardInfoCompResult(warningTime,deviceCode);
		return model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getIdCardInfoCountByTime(String deviceCode) {
		return idCardInfoDao.getIdCardInfoCountByTime(util.getNowTime(),deviceCode);
		//		return 99;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int queryIdCardWarnNum(String deviceCode) {
		return idCardInfoDao.queryIdCardWarnNum(deviceCode);
		//		return 99;
	}

	@Override
	public void updateFlag(String cardComprltid) {
		// TODO Auto-generated method stub
		idCardInfoDao.updateFlag(cardComprltid);
	}

	@Override
	public IdCardCompWarn getLastIdCardInfoCompResult(String warningTime,String deviceCode) {
		// TODO Auto-generated method stub
		return idCardInfoDao.getLastIdCardInfoCompResult(warningTime,deviceCode);
	}

	@Override
	public List<IdCardInfoModel> queryCardIdInfoByCard16(String cardID16) {
		return idCardInfoDao.queryCardIdInfoByCard16(cardID16);
	}

	@Override
	public List<IdCardCompWarn> indexIdcardWarningInfo(String warningTime) {
		// TODO Auto-generated method stub
		return idCardInfoDao.indexIdcardWarningInfo(warningTime);
	}
	
}
