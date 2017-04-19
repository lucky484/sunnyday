/**
 * 
 */
package com.hd.pfirs.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.FaceInfoDao;
import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.service.FaceInfoService;
import com.hd.pfirs.util.CommUtil;

/**
 * @ClassName: FaceInfoService
 * @Description: 人脸采集信息
 * @author light.chen
 * @date Dec 16, 2015 9:47:30 AM
 */
@Service
public class FaceInfoServiceImpl implements FaceInfoService {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(FaceInfoServiceImpl.class);

	@Autowired
	private FaceInfoDao faceInfoDao;

	/**
	 * 时间转换
	 */
	@Autowired
	private CommUtil util;

	public String saveFaceInfo(FaceInfo faceInfo) {
		try {
			faceInfo.setFaceId(CommUtil.getPrimaryKey());
			faceInfoDao.saveFaceInfo(faceInfo);
			return "2";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "-1";
		}
	}

	public FaceInfo getFaceInfo() {
		return faceInfoDao.getFaceInfo();
	}

	/**
	 * 更新状态
	 */
	public void updateFaceInfo(long faceId, String relayFlag,Date relayDate,Date updateTime) {
		faceInfoDao.updateFaceInfo(faceId, relayFlag,relayDate,updateTime);
	}

	/**
	 * 前台实时监控查询
	 * 
	 * @author curry.su
	 */
	@Override
	public List<FaceInfo> getFaceInfoListWeb(String deviceCode) {
		// TODO Auto-generated method stub
		return faceInfoDao.getFaceInfoListWeb(deviceCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getFaceInfoCountByTime(String deviceCode) {
		return faceInfoDao.getFaceInfoCountByTime(util.getNowTime(),deviceCode);
	}

}
