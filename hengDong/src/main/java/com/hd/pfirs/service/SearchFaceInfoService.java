/**
 * 
 */
package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.FaceComResult;
import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.ParamSet;
import com.hd.pfirs.model.SearchFaceInfo;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 29, 2015 3:56:26 PM
 */
public interface SearchFaceInfoService {
	
	public List<SearchFaceInfo> searchFaceInfo(int page,String collectTimeStart,String collectTimeEnd,String collectSite,String faceSimilarity);
	
	public int getCount(String collectTimeStart,String collectTimeEnd,String collectSite,String faceSimilarity);
	
	public ParamSet getParamSet();
	
	public FaceInfo getFaceInfo(String faceId);
	
	public FaceInfo getFaceInfoQ(String groupCode);
	
	public FaceInfo getFaceInfoZ(String groupCode);
	
	public IdCardInfoModel getIdCardInfo(String cardId);
	
	public FaceComResult getFaceComResult(String facecomprltid);
	
	public List<SearchFaceInfo> getFaceInfoBygf(String groupCode,String faceS);
	
	public List<SearchFaceInfo> getFaceBygf(String groupCode);

}
