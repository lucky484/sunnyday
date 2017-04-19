/**
 * 
 */
package com.hd.pfirs.dao;

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
 * @date Dec 29, 2015 3:53:13 PM
 */
public interface SearchFaceInfoDao {
	
	public List<SearchFaceInfo> searchFaceInfo(@Param(value = "page")int page,
			@Param(value = "collectTimeStart")String collectTimeStart,
			@Param(value = "collectTimeEnd")String collectTimeEnd,
			@Param(value = "collectSite")String collectSite,
			@Param(value = "faceSimilarity")String faceSimilarity);
	
	public int getCount(@Param(value = "collectTimeStart")String collectTimeStart,
			@Param(value = "collectTimeEnd")String collectTimeEnd,
			@Param(value = "collectSite")String collectSite,
			@Param(value = "faceSimilarity")String faceSimilarity);
	
	public ParamSet getParamSet();
	
	public FaceInfo getFaceInfo(String faceId);
	
	public FaceInfo getFaceInfoQ(String groupCode);
	
	public FaceInfo getFaceInfoZ(String groupCode);
	
	public IdCardInfoModel getIdCardInfo(String cardId);
	
	public FaceComResult getFaceComResult(String facecomprltid);
	
	public List<SearchFaceInfo> getFaceInfoBygf(@Param(value = "groupCode")String groupCode,
								@Param(value = "faceS")String faceS);
	
	public List<SearchFaceInfo> getFaceBygf(String groupCode);

}
