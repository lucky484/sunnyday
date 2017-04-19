/**
 * 
 */
package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.model.IdCardInfoModel;

/**
 * @ClassName:
 * @Description:
 * @author 
 * @date Dec 23, 2015 4:20:56 PM
 */
public interface FaceCompService {
	
	public FaceInfo getfaceInfo(String faceCode);
	
	public List<FaceInfo> getfaceInfoByGroup(String groupcode);
	
	public IdCardInfoModel getIdCardInfo(String groupcode);
	
	public FaceInfo getFaceByCardCode(String cardCode);

}
