package com.hd.client.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.dao.IDInfoDao;
import com.hd.client.model.BaseInfo;
import com.hd.client.model.IdCardInfo;
import com.hd.client.service.IDInfoService;

/**
 * 保存身份证信息的实现类
 * 
 * @author ligang.yang
 *
 */
@Service
public class IDInfoServiceImpl implements IDInfoService {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IDInfoServiceImpl.class);

	@Autowired
	private IDInfoDao dao;

	/**
	 * 获取采集到的身份证信息
	 */
	public List<IdCardInfo> getIdCardInfoList() {
		return dao.getIdCardInfoList();
	}

	/**
	 * 保存信息
	 */
	public int save(IdCardInfo info) {
		try {
			this.dao.saveIDInfo(info);
			this.dao.saveFCInfo(info);
		} catch (Exception e) {

			logger.error(e);
		}
		return 0;
	}

	/**
	 * 查询基础信息
	 */
	public BaseInfo queryIDInfo() {
		return new BaseInfo();
	}

	/**
	 * 增加信息
	 */
	public int addIDInfoDao(IDInfoDao idInfoDao) {
		return dao.addIDInfoDao(idInfoDao);
	}

	/**
	 * 更新身份证信息
	 */
	public int updateIDInfoDao(String cardId, String relayFlag) {
		return dao.updateIDInfoDao(cardId, relayFlag);
	}

	/**
	 * 更新身份证信息
	 */
	public int updateFCInfoDao(String fc_id, String relayFlag) {
		return dao.updateFCInfoDao(fc_id, relayFlag);
	}

	public IdCardInfo getIdCardInfo() {
		return dao.getIdCardInfo();
	}

	@Override
	public Map<String, Object> countIdCardInfo() {
		return dao.countIdCardInfo();
	}

}
