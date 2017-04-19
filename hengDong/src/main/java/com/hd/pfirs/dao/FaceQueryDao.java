package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.ComplexQueryModel;
import com.hd.pfirs.model.IdCardInfoModel;

public interface FaceQueryDao {
    
	  public List<ComplexQueryModel> getComplexQueryList(@Param(value = "start")int start,@Param(value = "pageSize")int PageSize,@Param(value = "collectTimeStart")String collectTimeStart,
			  @Param(value = "collectTimeEnd")String collectTimeEnd,@Param(value = "collectSite")String collectSite,
			  @Param(value = "idCardName")String idCardName,@Param(value = "idCardSex")String idCardSex,@Param(value = "idCardNo")String idCardNo,
			  @Param(value = "faceCompName")String faceCompName,@Param(value = "sexComp")String sexComp,@Param(value = "faceCompNo")String faceCompNo,
			  @Param(value = "isControlled")String isControlled,@Param(value = "faceAndIdCardComp")String faceAndIdCardComp,
			  @Param(value = "faceAndIdCardNotMatch")String faceAndIdCardNotMatch,@Param(value = "faceCompSimilarity")String faceCompSimilarity,
			  @Param(value = "notIdCard")String notIdCard,@Param(value = "faceCardCompAlarmVal")Integer faceCardCompAlarmVal,@Param(value = "faceCompAlarmVal")Integer faceCompAlarmVal);
	  
	  public int getComplexQueryCount(@Param(value = "collectTimeStart")String collectTimeStart,
			  @Param(value = "collectTimeEnd")String collectTimeEnd,@Param(value = "collectSite")String collectSite,
			  @Param(value = "idCardName")String idCardName,@Param(value = "idCardSex")String idCardSex,@Param(value = "idCardNo")String idCardNo,
			  @Param(value = "faceCompName")String faceCompName,@Param(value = "sexComp")String sexComp,@Param(value = "faceCompNo")String faceCompNo,
			  @Param(value = "isControlled")String isControlled,@Param(value = "faceAndIdCardComp")String faceAndIdCardComp,
			  @Param(value = "faceAndIdCardNotMatch")String faceAndIdCardNotMatch,@Param(value = "faceCompSimilarity")String faceCompSimilarity,
			  @Param(value = "notIdCard")String notIdCard,@Param(value = "faceCardCompAlarmVal")Integer faceCardCompAlarmVal,@Param(value = "faceCompAlarmVal")Integer faceCompAlarmVal);
	  
	  public List<ComplexQueryModel> queryFaceById(@Param(value="faceId")Long faceId);
	  
	  public ComplexQueryModel queryCardInfoById(@Param(value = "faceId")Long faceId);
	  
	  public ComplexQueryModel queryIdCardCompResult(@Param(value = "faceId")Long faceId);
	  
	  public List<ComplexQueryModel> queryFaceInfoCompResult(@Param(value = "faceId")Long faceId);
	  
	  public ComplexQueryModel queryFaceAndCardResult(@Param(value = "faceId")Long faceId);
}

