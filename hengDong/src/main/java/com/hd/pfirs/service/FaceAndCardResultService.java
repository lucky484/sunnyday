package com.hd.pfirs.service;

import java.util.List;

import com.hd.pfirs.model.FCResultModel;

public interface FaceAndCardResultService {

	public void insertFaceAndCardResult(String cardCode, String deviceCode, String faceCode, String groupCode,
			Float similarity, String featureID);

	/**
	 * 查询人证是否合一
	 * 
	 * @param warningTime
	 * @return
	 */
	FCResultModel queryFCResult(String warningTime,String deviceCode);

	/**
	 * 
	  * @Description: 查询人证合一报警数
	  * @param      : 
	  * @return     : FCResultModel
	  * @throws  
	  * @data       : 2016年1月19日 上午11:29:43   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	public int queryFCWarning(String deviceCode);
	/**
	 * index 预警
	 * @param warningTime
	 * @return
	 */
	public List<FCResultModel> indexFaceCardWarningInfo(String warningTime);
}
