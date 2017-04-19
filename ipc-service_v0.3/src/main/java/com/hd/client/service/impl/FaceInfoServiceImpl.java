package com.hd.client.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.dao.FaceInfoDao;
import com.hd.client.model.FaceInfoModel;
import com.hd.client.service.FaceInfoService;

/**
 * 
 * @author curry.su
 *
 */
@Service
public class FaceInfoServiceImpl implements FaceInfoService {

	@Autowired
	private FaceInfoDao faceInfo;

	public void addFaceInfo(FaceInfoModel model) {
		// TODO Auto-generated method stub
		faceInfo.addFaceInfo(model);
		faceInfo.addFCInfo(model);
	}

	@Override
	public FaceInfoModel getFaceInfo() {

		return faceInfo.getFaceInfo();
	}

	@Override
	public List<FaceInfoModel> getfaceInfoModelList() {

		return faceInfo.getfaceInfoModelList();
	}

	@Override
	public void updateFaceInfo(String faceId, String relayFlag) {

		faceInfo.updateFaceInfo(faceId, relayFlag);

	}

	@Override
	public void updateFCInfo(String fc_id, String relayFlag) {

		faceInfo.updateFCInfo(fc_id, relayFlag);

	}

	@Override
	/**
	 * 统计人脸采集数量和转发数量
	 * 
	 * @return
	 */
	public Map<String, Object> countFaceInfo() {
		return faceInfo.countFaceInfo();
	}

}
