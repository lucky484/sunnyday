package com.hd.pfirs.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.ComplexQueryDao;
import com.hd.pfirs.dao.FaceQueryDao;
import com.hd.pfirs.dao.IdCardInfoDao;
import com.hd.pfirs.dao2.ConstantDao;
import com.hd.pfirs.dao2.FugitivesDao;
import com.hd.pfirs.dao2.TemporaryDao;
import com.hd.pfirs.model.ComplexQueryModel;
import com.hd.pfirs.model.Constant;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.Temporary;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;
import com.hd.pfirs.service.ComplexQueryService;
import com.hd.pfirs.service.FaceQueryService;

@Service
public class FaceQueryServiceImpl implements FaceQueryService{
     
	
	@Autowired
	private FaceQueryDao faceQueryDao;
	
	@Autowired
	private ConstantDao constantDao;
	
	@Autowired
	private TemporaryDao temporaryDao;
	
	@Autowired
	private FugitivesDao fugitivesDao;
	
	@Override
	public List<ComplexQueryModel> getComplexQueryList(int start, int PageSize,String collectTimeStart,String collectTimeEnd,String collectSite,
			  String idCardName,String idCardSex,String idCardNo,String faceCompName,String sexComp,String faceCompNo,String isControlled,
			  String faceAndIdCardComp,String faceAndIdCardNotMatch,String faceCompSimilarity,String notIdCard,Integer faceCardCompAlarmVal,Integer faceCompAlarmVal) {
		return faceQueryDao.getComplexQueryList(start, PageSize,collectTimeStart,collectTimeEnd,collectSite,
	    		idCardName,idCardSex,idCardNo,faceCompName,sexComp,faceCompNo,isControlled,faceAndIdCardComp,faceAndIdCardNotMatch,faceCompSimilarity,notIdCard,faceCardCompAlarmVal,faceCompAlarmVal);
	}

	@Override
	public int getComplexQueryCount(String collectTimeStart,String collectTimeEnd,String collectSite,
			  String idCardName,String idCardSex,String idCardNo,String faceCompName,String sexComp,String faceCompNo,String isControlled,
			  String faceAndIdCardComp,String faceAndIdCardNotMatch,String faceCompSimilarity,String notIdCard,Integer faceCardCompAlarmVal,Integer faceCompAlarmVal) {
		return faceQueryDao.getComplexQueryCount(collectTimeStart,collectTimeEnd,collectSite,
	    		idCardName,idCardSex,idCardNo,faceCompName,sexComp,faceCompNo,isControlled,faceAndIdCardComp,faceAndIdCardNotMatch,faceCompSimilarity,notIdCard,faceCardCompAlarmVal,faceCompAlarmVal);
	}
    /**
     * 根据faceId查询人脸
     * @param faceId
     * @return
     */
	public List<ComplexQueryModel> queryFaceById(Long faceId){
		return faceQueryDao.queryFaceById(faceId);
	}
	
	@Override
	public List<Constant> queryConstantById(List list,String xm,String xb,String sfzh) {
		
		return constantDao.queryConstantById(list,xm,xb,sfzh);
	}

	@Override
	public List<T_QB_RY_ZTRYJBXX> queryFugitivesById(List list,String xm,String xb,String ysfzh) {
		return fugitivesDao.queryFugitivesById(list,xm,xb,ysfzh);
	}

	@Override
	public List<Temporary> queryTemporaryById(List list,String bbkrxm,String bbkrxb,String bbkrzjhm) {
		
		return temporaryDao.queryTemporaryById(list,bbkrxm,bbkrxb,bbkrzjhm);
	}

	@Override
	public ComplexQueryModel queryCardInfoById(Long faceId) {

		return faceQueryDao.queryCardInfoById(faceId);
	}

	@Override
	public ComplexQueryModel queryIdCardCompResult(Long faceId) {
		
		return faceQueryDao.queryIdCardCompResult(faceId);
	}

	@Override
	public List<ComplexQueryModel> queryFaceInfoCompResult(Long faceId) {
		
		return faceQueryDao.queryFaceInfoCompResult(faceId);
	}

	@Override
	public ComplexQueryModel queryFaceAndCardResult(Long faceId) {
		
		return faceQueryDao.queryFaceAndCardResult(faceId);
	}
    
	
}
