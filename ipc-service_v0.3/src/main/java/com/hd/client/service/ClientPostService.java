package com.hd.client.service;

import java.util.Map;

import com.hd.client.model.FaceInfoModel;
import com.hd.client.model.IdCardInfo;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * 封装一个json的post的请求方法
 * 
 * @author ligang.yang
 *
 */
public interface ClientPostService {

	/**
	 * 这是一个将人脸信息转发到post请求
	 * 
	 * @param faceInfoModel
	 * @param callback
	 */
	@POST("/FaceInfo/saveFaceInfo.do")
	void sendRow(@Body FaceInfoModel faceInfoModel, Callback<String> callback);

	/**
	 * 这是一个将身份证信息转发到post请求
	 * 
	 * @param idCardInfo
	 * @param callback
	 */
	@POST("/IdCardInfo/saveIdCardInfo.do")
	void sendRow(@Body IdCardInfo idCardInfo, Callback<String> callback);

	/**
	 * 这是一个将身份证信息转发到post请求
	 * 
	 * @param idCardInfo
	 * @param callback
	 */
	@POST("/ipc/info.do")
	void sendRow(@Body Map<String, Object> map, Callback<String> callback);

}
