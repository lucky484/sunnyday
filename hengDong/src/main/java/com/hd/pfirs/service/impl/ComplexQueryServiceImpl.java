package com.hd.pfirs.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.ComplexQueryDao;
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

@Service
public class ComplexQueryServiceImpl implements ComplexQueryService{
     
	
	@Autowired
	private ComplexQueryDao complexQueryDao;
	
	@Autowired
	private ConstantDao constantDao;
	
	@Autowired
	private TemporaryDao temporaryDao;
	
	@Autowired
	private FugitivesDao fugitivesDao;
	
	@Override
	public List<ComplexQueryModel> getComplexQueryList(int start, int PageSize,String collectTimeStart,String collectTimeEnd,String collectSite,
			  String idCardName,String idCardSex,String idCardNo,String faceCompName,String sexComp,String faceCompNo,String faceComp,String isControlled,
			  String faceAndIdCardComp,String cardCompName,String cardSexComp,String cardCompNo,Integer faceCardCompAlarmVal,Integer faceCompAlarmVal) {
		return complexQueryDao.getComplexQueryList(start, PageSize,collectTimeStart,collectTimeEnd,collectSite,
	    		idCardName,idCardSex,idCardNo,faceCompName,sexComp,faceCompNo,faceComp,isControlled,faceAndIdCardComp,cardCompName,cardSexComp,cardCompNo,faceCardCompAlarmVal,faceCompAlarmVal);
	}

	@Override
	public int getComplexQueryCount(String collectTimeStart,String collectTimeEnd,String collectSite,
			  String idCardName,String idCardSex,String idCardNo,String faceCompName,String sexComp,String faceCompNo,String faceComp,String isControlled,
			  String faceAndIdCardComp,String cardCompName,String cardSexComp,String cardCompNo,Integer faceCardCompAlarmVal,Integer faceCompAlarmVal) {
		return complexQueryDao.getComplexQueryCount(collectTimeStart,collectTimeEnd,collectSite,
	    		idCardName,idCardSex,idCardNo,faceCompName,sexComp,faceCompNo,faceComp,isControlled,faceAndIdCardComp,cardCompName,cardSexComp,cardCompNo,faceCardCompAlarmVal,faceCompAlarmVal);
	}
    /**
     * 根据faceId查询人脸
     * @param faceId
     * @return
     */
	public List<ComplexQueryModel> queryFaceById(Long faceId){
		return complexQueryDao.queryFaceById(faceId);
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

		return complexQueryDao.queryCardInfoById(faceId);
	}

	@Override
	public ComplexQueryModel queryIdCardCompResult(Long faceId,String compareBaseId) {
		
		return complexQueryDao.queryIdCardCompResult(faceId,compareBaseId);
	}

	@Override
	public List<ComplexQueryModel> queryFaceInfoCompResult(Long faceId,Integer faceCompAlarmVal) {
		
		return complexQueryDao.queryFaceInfoCompResult(faceId,faceCompAlarmVal);
	}

	@Override
	public ComplexQueryModel queryFaceAndCardResult(Long faceId) {
		
		return complexQueryDao.queryFaceAndCardResult(faceId);
	}
    
	
}
