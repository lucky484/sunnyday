/**
 * 
 */
package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.FaceCompDao;
import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.service.FaceCompService;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 23, 2015 4:21:37 PM
 */
@Service
public class FaceCompServiceImpl implements FaceCompService{
	
	@Autowired
	private FaceCompDao faceCompDao;
	
	@Override
	public FaceInfo getfaceInfo(String faceCode) {
		return faceCompDao.getfaceInfo(faceCode);
	}

	@Override
	public IdCardInfoModel getIdCardInfo(String groupcode) {
		return faceCompDao.getIdCardInfo(groupcode);
	}

	@Override
	public FaceInfo getFaceByCardCode(String cardCode) {
		
		return faceCompDao.getFaceByCardCode(cardCode);
	}

	@Override
	public List<FaceInfo> getfaceInfoByGroup(String groupcode) {
		return faceCompDao.getfaceInfoByGroup(groupcode);
	}

  
}
