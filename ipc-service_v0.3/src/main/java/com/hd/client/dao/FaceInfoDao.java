package com.hd.client.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.client.model.FaceInfoModel;

/**
 * 
 * @author curry.su
 *
 */
public interface FaceInfoDao {

	// 新增数据
	public int addFaceInfo(FaceInfoModel model);

	public int addFCInfo(FaceInfoModel model);

	// 获取时间最新的一条人脸采集数据
	public FaceInfoModel getFaceInfo();

	// 获取人脸采集数据
	List<FaceInfoModel> getfaceInfoModelList();

	// 更新人脸状态
	public void updateFaceInfo(@Param(value = "faceId") String faceId, @Param(value = "relayFlag") String relayFlag);

	// 更新人脸状态
	public void updateFCInfo(@Param(value = "fc_id") String faceId, @Param(value = "relayFlag") String relayFlag);

	/**
	 * 统计已转发和采集数量
	 * 
	 * @return
	 */
	public Map<String, Object> countFaceInfo();

}
