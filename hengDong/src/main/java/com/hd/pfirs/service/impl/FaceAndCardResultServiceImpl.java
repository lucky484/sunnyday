package com.hd.pfirs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.dao.FaceAndCardResultDao;
import com.hd.pfirs.dao.ParamSetDao;
import com.hd.pfirs.model.FCResultModel;
import com.hd.pfirs.model.FaceAndCardResult;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.service.FaceAndCardResultService;
import com.hd.pfirs.util.CommUtil;

import sun.misc.BASE64Encoder;

@Service
public class FaceAndCardResultServiceImpl implements FaceAndCardResultService {

	@Autowired
	private FaceAndCardResultDao faceAndCardResultDao;

	@Autowired
	private ParamSetDao paramSetDao;

	private FCResultModel tempFCModel = new FCResultModel();

	@Transactional
	public void insertFaceAndCardResult(String cardCode, String deviceCode, String faceCode, String groupCode,
			Float similarity, String featureID) {
		FaceAndCardResult faceAndCardResult = new FaceAndCardResult();
		faceAndCardResult.setCiID(CommUtil.getPrimaryKey());
		faceAndCardResult.setReceiveTime(new Date());
		faceAndCardResult.setReceiveTimeStamp(String.valueOf(new Date().getTime()));
		faceAndCardResult.setDeleteStatus("0");
		faceAndCardResult.setCardCode(cardCode.equals("") && cardCode == null ? "" : cardCode);
		faceAndCardResult.setFaceCode(faceCode.equals("") && faceCode == null ? "" : faceCode);
		faceAndCardResult.setSimilarity(similarity == null ? null : similarity);
		faceAndCardResult.setDeviceCode(deviceCode.equals("") && deviceCode == null ? "" : deviceCode);
		faceAndCardResultDao.insertFaceAndCardResult(faceAndCardResult);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FCResultModel queryFCResult(String warningTime,String deviceCode) {
		
		ParamSet params = paramSetDao.getParamSet();
		Integer similarity = params.getFaceCardCompAlarmVal();
		FCResultModel model = faceAndCardResultDao.queryFCResult(warningTime,deviceCode);

		if (model == null || model.getSimilarity() == null || model.getSimilarity() >= similarity) {
			//			FCResultModel timeModel = tempFCModel;
			//			timeModel.setFCtimeStamp(timestamp);
			return tempFCModel;
		}
		String cardDate = model.getCardDateStr();
		if (cardDate != null) {
			model.setfCardDate(changeDateStr2FDate(cardDate));
			model.setCardDateStr(null);
		} else {
			model.setfCardDate(null);
		}

		String faceDate = model.getFaceDateStr();
		if (faceDate != null) {
			model.setfFaceDate(changeDateStr2FDate(faceDate));
		} else {
			model.setfFaceDate(null);
		}
		byte[] temp = model.getFacePic();
		if (temp != null) {
			model.setBase64FacePic(changePic2Base64(temp));
			model.setFacePic(null);
		}
		temp = model.getIdCardPic();
		if (temp != null) {
			model.setBase64IdCardPic(changePic2Base64(temp));
			model.setIdCardPic(null);
		}
		// 缓存model
		//		this.tempFCModel = model;

		return model;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int queryFCWarning(String deviceCode) {
		ParamSet params = paramSetDao.getParamSet();
		Integer similarity = 0;
		if (params == null || (similarity = params.getFaceCardCompAlarmVal()) == 0) {
			return similarity;
		}
		int FCWarnNum = faceAndCardResultDao.queryFCWarning(similarity,deviceCode);
		return FCWarnNum;
	};

	private String changeDateStr2FDate(String DateStr) {
		Long time = Long.valueOf(DateStr);
		Date date = new Date(time);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	private String changePic2Base64(byte[] temp) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(temp);
	}

	@Override
	public List<FCResultModel> indexFaceCardWarningInfo(String warningTime) {
		// TODO Auto-generated method stub
		return faceAndCardResultDao.indexFaceCardWarningInfo(warningTime);
	}

}
