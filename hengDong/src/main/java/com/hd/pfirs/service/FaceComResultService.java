package com.hd.pfirs.service;

import java.io.IOException;
import java.util.List;

import com.hd.pfirs.model.CompareResult;
import com.hd.pfirs.model.FaceComResult;

public interface FaceComResultService {

	public void insertFaceComResult(String groupCode, String faceCode, String deviceCode, String featureID,
			List<CompareResult> list) throws IOException;

	//	public FaceComResult getSimiliarity(String faceCode);

	//查询人脸对比数据
	public FaceComResult getFaceBySimilarity(String deviceCode,String warningTime);

	/**
	 * 
	  * @Description: 查询人脸比对预警数
	  * @param      : 
	  * @return     : FaceComResult
	  * @throws  
	  * @data       : 2016年1月19日 下午3:02:23   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	public int queryFaceComWarnNum(String deviceCode);
	
	/**
	 * 更新状态
	 * @param faceCompRltID
	 */
	public void updateFlag(long faceCompRltID);
	
	/**
	 * 预警完了显示最新的一条
	 * @return
	 */
	public FaceComResult getLastFaceBySimilarity(String deviceCode,String warningTime);
	
	/**
	 * index 预警
	 * @param warningTime
	 * @return
	 */
	public List<FaceComResult> indexfaceWarningInfo(String warningTime);
}
