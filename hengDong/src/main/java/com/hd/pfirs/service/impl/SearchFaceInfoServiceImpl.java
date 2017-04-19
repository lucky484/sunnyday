/**
 * 
 */
package com.hd.pfirs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.pfirs.dao.SearchFaceInfoDao;
import com.hd.pfirs.model.FaceComResult;
import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.SearchFaceInfo;
import com.hd.pfirs.service.SearchFaceInfoService;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 29, 2015 3:57:21 PM
 */
@Service
public class SearchFaceInfoServiceImpl implements SearchFaceInfoService{
	
	@Autowired
	private SearchFaceInfoDao searchFaceInfoDao;

	@Override
	public List<SearchFaceInfo> searchFaceInfo(int page,String collectTimeStart,String collectTimeEnd,String collectSite,String faceSimilarity) {
		return searchFaceInfoDao.searchFaceInfo(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
	}

	@Override
	public int getCount(String collectTimeStart,String collectTimeEnd,String collectSite,String faceSimilarity) {
		return searchFaceInfoDao.getCount(collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
	}

	@Override
	public ParamSet getParamSet() {
		return searchFaceInfoDao.getParamSet();
	}

	@Override
	public FaceInfo getFaceInfo(String faceId) {
		return searchFaceInfoDao.getFaceInfo(faceId);
	}

	@Override
	public IdCardInfoModel getIdCardInfo(String cardId) {
		return searchFaceInfoDao.getIdCardInfo(cardId);
	}

	@Override
	public FaceComResult getFaceComResult(String facecomprltid) {
		return searchFaceInfoDao.getFaceComResult(facecomprltid);
	}

	@Override
	public FaceInfo getFaceInfoQ(String groupCode) {
		return searchFaceInfoDao.getFaceInfoQ(groupCode);
	}

	@Override
	public FaceInfo getFaceInfoZ(String groupCode) {
		return searchFaceInfoDao.getFaceInfoZ(groupCode);
	}

	@Override
	public List<SearchFaceInfo> getFaceInfoBygf(String groupCode, String faceS) {
		return searchFaceInfoDao.getFaceInfoBygf(groupCode, faceS);
	}

	@Override
	public List<SearchFaceInfo> getFaceBygf(String groupCode) {
		return searchFaceInfoDao.getFaceBygf(groupCode);
	}

}
