package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.ComplexQueryModel;
import com.hd.pfirs.model.Constant;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.Temporary;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;

public interface FaceQueryService {
	
	  public List<ComplexQueryModel> getComplexQueryList(int start,int PageSize,String collectTimeStart,String collectTimeEnd,String collectSite,
			  String idCardName,String idCardSex,String idCardNo,String faceCompName,String sexComp,String faceCompNo,String isControlled,
			  String faceAndIdCardComp,String faceAndIdCardNotMatch,String faceCompSimilarity,String notIdCard,Integer faceCardCompAlarmVal,Integer faceCompAlarmVal);
	  
	  public int getComplexQueryCount(String collectTimeStart,String collectTimeEnd,String collectSite,
			  String idCardName,String idCardSex,String idCardNo,String faceCompName,String sexComp,String faceCompNo,String isControlled,
			  String faceAndIdCardComp,String faceAndIdCardNotMatch,String faceCompSimilarity,String notIdCard,Integer faceCardCompAlarmVal,Integer faceCompAlarmVal);
	  
	  public  List<Constant> queryConstantById(List list,String xm,String xb,String sfzh); 
	  
	  public List<T_QB_RY_ZTRYJBXX> queryFugitivesById(List list,String xm,String xb,String ysfzh);
	  
	  public List<Temporary> queryTemporaryById(List list,String bbkrxm,String bbkrxb,String bbkrzjhm);
	  
	  public List<ComplexQueryModel> queryFaceById(Long faceId);
	  
	  public ComplexQueryModel queryCardInfoById(Long faceId);
	  
	  public ComplexQueryModel queryIdCardCompResult(Long faceId);
	  
	  public List<ComplexQueryModel> queryFaceInfoCompResult(Long faceId);
	  
	  public ComplexQueryModel queryFaceAndCardResult(Long faceId);
}
