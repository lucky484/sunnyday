package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.InquiryIdCardInfoModel;

public interface InquiryIdCardInfoDao {

	public List<InquiryIdCardInfoModel> getIdCardInfo(
			@Param(value = "start") int start,
			@Param(value = "pageSize") int PageSize,
			@Param(value = "collectTimeStart") String collectTimeStart,
			@Param(value = "collectTimeEnd") String collectTimeEnd,
			@Param(value = "collectSite") String collectSite,
			@Param(value = "idCardName") String idCardName,
			@Param(value = "sexSelected") String sexSelected,
			@Param(value = "idCardNo") String idCardNo,
			@Param(value = "isCardInfo")String isCardInfo,
			@Param(value = "isFaceAndCard")String isFaceAndCard,
			@Param(value = "faceCardCompAlarmVal")Integer faceCardCompAlarmVal);

	public int getIdCardInfoCount(
			@Param(value = "collectTimeStart") String collectTimeStart,
			@Param(value = "collectTimeEnd") String collectTimeEnd,
			@Param(value = "collectSite") String collectSite,
			@Param(value = "idCardName") String idCardName,
			@Param(value = "sexSelected") String sexSelected,
			@Param(value = "idCardNo") String idCardNo,
			@Param(value = "isCardInfo")String isCardInfo,
			@Param(value = "isFaceAndCard")String isFaceAndCard,
			@Param(value = "faceCardCompAlarmVal")Integer faceCardCompAlarmVal);

}