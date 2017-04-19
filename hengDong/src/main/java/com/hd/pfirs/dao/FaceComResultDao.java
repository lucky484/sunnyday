package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.FaceComResult;

public interface FaceComResultDao {

	public void insertFaceComResult(FaceComResult faceComResult);

	public FaceComResult getSimiliarity(String faceCode);

	//查询人脸对比数据
	public FaceComResult getFaceBySimilarity(@Param( value = "deviceCode")String deviceCode,@Param( value = "warningTime")String warningTime);

	/**
	 * 
	  * @Description: 查询人脸比对预警数
	  * @param      : 
	  * @return     : int
	  * @throws  
	  * @data       : 2016年1月19日 下午3:03:55   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	int queryFaceComWarnNum(String deviceCode);
	
	public void updateFlag(long faceCompRltID);
	
	/**
	 * 预警完了显示最新的一条
	 * @return
	 */
	public FaceComResult getLastFaceBySimilarity(@Param(value="deviceCode")String deviceCode,@Param(value="warningTime")String warningTime);

	/**
	 * index 预警
	 * @param warningTime
	 * @return
	 */
	public List<FaceComResult> indexfaceWarningInfo(String warningTime);
}
