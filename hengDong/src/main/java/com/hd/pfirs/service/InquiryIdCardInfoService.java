package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.InquiryIdCardInfoModel;
public interface InquiryIdCardInfoService {

	public List<InquiryIdCardInfoModel> getIdCardInfo(int start, int PageSize,
			String collectTimeStart, String collectTimeEnd, String collectSite, String idCardName, String sexSelected,
			String idCardNo,String isCardInfo,String isFaceAndCard,Integer faceCardCompAlarmVal);

	public int getIdCardInfoCount(String collectTimeStart,
			String collectTimeEnd, String collectSite,
			String idCardName, String sexSelected, String idCardNo,String isCardInfo,String isFaceAndCard,Integer faceCardCompAlarmVal);

}
