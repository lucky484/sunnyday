package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.InquiryIdCardInfoDao;
import com.hd.pfirs.dao.IntegratedQueryMessageDao;
import com.hd.pfirs.model.InquiryIdCardInfoModel;
import com.hd.pfirs.model.IntegratedQueryMessageModel;
import com.hd.pfirs.service.InquiryIdCardInfoService;

/**
 * @author cliff.fan
 *
 */
@Service
public class InquiryIdCardInfoServiceImpl implements InquiryIdCardInfoService {

	@Autowired
	private InquiryIdCardInfoDao InquiryIdCardInfo;

	@Override
	public List<InquiryIdCardInfoModel> getIdCardInfo(int start, int PageSize,
			String collectTimeStart, String collectTimeEnd, String collectSite,String idCardName, String sexSelected,
			String idCardNo,String isCardInfo,String isFaceAndCard,Integer faceCardCompAlarmVal) {
		return InquiryIdCardInfo.getIdCardInfo(start, PageSize,
				collectTimeStart, collectTimeEnd, collectSite,
				idCardName, sexSelected, idCardNo,isCardInfo,isFaceAndCard,faceCardCompAlarmVal);
	}

	@Override
	public int getIdCardInfoCount(String collectTimeStart,
			String collectTimeEnd, String collectSite,
			String idCardName, String sexSelected, String idCardNo,String isCardInfo,String isFaceAndCard,Integer faceCardCompAlarmVal) {
		return InquiryIdCardInfo.getIdCardInfoCount(collectTimeStart,
				collectTimeEnd, collectSite, idCardName,
				sexSelected, idCardNo,isCardInfo,isFaceAndCard,faceCardCompAlarmVal);
	}

}
