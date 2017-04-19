package com.hd.client.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.dao.FaceAndIdcardDao;
import com.hd.client.service.FaceAndIdcardService;

/**
 * 将人脸信息和身份证信息查询出来的服务
 * 
 * @author ligang.yang
 *
 */
@Service
public class FaceAndIdcardServiceImpl implements FaceAndIdcardService {

	@Autowired
	private FaceAndIdcardDao dao;

	/**
	 * 将查询的人脸信息和身份证信息放在map中
	 */
	@Override
	public Map<String, Object> queryFaceAndIdcardInfo() {
		return dao.getFaceAndIdcardMap();
	}

	@Override
	public Map<String, Object> queryFCInfo() {
		return dao.getFCInfoMap();
	}

}
