package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.FCResultModel;
import com.hd.pfirs.model.FaceAndCardResult;

public interface FaceAndCardResultDao {

	public void insertFaceAndCardResult(FaceAndCardResult faceAndCardResult);

	/**
	 * 查询人证是否合一
	 * 
	 * @param timestamp
	 * @return
	 */
	public FCResultModel queryFCResult(@Param(value="warningTime")String warningTime,@Param(value="deviceCode")String deviceCode);

	/**
	 * 
	  * @Description: 查询人证合一预警数
	  * @param      : 
	  * @return     : int
	  * @throws  
	  * @data       : 2016年1月19日 上午11:38:07   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	int queryFCWarning(@Param(value="similarity")Integer similarity,@Param(value="deviceCode")String deviceCode);
	
	/**
	 * index 预警
	 * @param warningTime
	 * @return
	 */
	public List<FCResultModel> indexFaceCardWarningInfo(String warningTime);

}
