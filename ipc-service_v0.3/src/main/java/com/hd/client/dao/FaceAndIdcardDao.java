package com.hd.client.dao;

import java.util.Map;

/**
 * 将人脸信息和身份证信息最新的一条数据查询出来
 * 
 * @author ligang.yang
 *
 */
public interface FaceAndIdcardDao {
	/**
	 * 获取人脸信息和身份证信息
	 * 
	 * @return
	 */
	Map<String, Object> getFaceAndIdcardMap();

	Map<String, Object> getFCInfoMap();
}
