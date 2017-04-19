package com.hd.client.service;

import java.util.List;
import java.util.Map;

import com.hd.client.model.FaceInfoModel;

/**
 * 
 * @author curry.su
 *
 */
public interface FaceInfoService {
	/**
	 * 保存人脸信息
	 * 
	 * @param faceInfo
	 */
	void addFaceInfo(FaceInfoModel model);

	// 获取时间最新的一条人脸采集数据
	public FaceInfoModel getFaceInfo();

	// 获取人脸采集数据
	List<FaceInfoModel> getfaceInfoModelList();

	// 更新人脸状态
	public void updateFaceInfo(String faceId, String relayFlag);

	// 更新人脸状态
	public void updateFCInfo(String fc_id, String relayFlag);

	/**
	 * 统计已转发和采集数量
	 * 
	 * @return
	 */
	Map<String, Object> countFaceInfo();
}
