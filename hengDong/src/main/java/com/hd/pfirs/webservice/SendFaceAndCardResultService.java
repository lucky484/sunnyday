package com.hd.pfirs.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.hd.pfirs.model.CompareResult;


/*
 * 发送人证合一比对返回结果
 */
@WebService
public interface SendFaceAndCardResultService{
   
	public String SendFaceAndCardResult(@WebParam(name = "cardCode")String cardCode,
			@WebParam(name = "deviceCode")String deviceCode,@WebParam(name = "faceCode")String faceCode,
			@WebParam(name = "groupCode")String groupCode,@WebParam(name = "similarity")Float similarity,
			@WebParam(name = "featureID")String featureID);
	
	public String SendFaceCompareResult(String groupCode,String faceCode,String deviceCode,String featureID,List<CompareResult> list);
}
