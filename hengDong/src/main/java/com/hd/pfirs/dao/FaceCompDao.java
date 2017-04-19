/**
 * 
 */
package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.hd.pfirs.model.FaceInfo;
import com.hd.pfirs.model.IdCardInfoModel;

/**
 * @ClassName: FaceCompDao
 * @Description: 查询人脸及时间
 * @author light.chen
 * @date Dec 23, 2015 4:10:49 PM
 */
@Transactional(value = "isap", rollbackFor = Exception.class) 
public interface FaceCompDao {
	
	public FaceInfo getfaceInfo(String faceCode);
	
	public List<FaceInfo> getfaceInfoByGroup(String groupcode);
	
	public IdCardInfoModel getIdCardInfo(String groupcode);
	
	public FaceInfo getFaceByCardCode(@Param(value="cardCode")String cardCode);

}
