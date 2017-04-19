/**
 * 
 */
package com.hd.pfirs.service;

import java.util.Date;
import java.util.List;

import com.hd.pfirs.model.FaceInfo;

/**
 * @ClassName: faceInfoService
 * @Description: 人脸采集信息
 * @author light.chen
 * @date Dec 16, 2015 9:42:45 AM
 */
public interface FaceInfoService {

	public String saveFaceInfo(FaceInfo faceInfo);

	public FaceInfo getFaceInfo();
	
	/**
	 * 前台实时监控查询 
	 * @author curry.su
	 */
	public List<FaceInfo> getFaceInfoListWeb(String deviceCode);

	/**
	 * 更新状态
	 */
	public void updateFaceInfo(long faceId, String relayFlag,Date relayDate,Date updateTime);

	/**
	 * 
	 * @Description: 查询当天人脸数据的采集量
	 * @return
	 * @return: int
	 */
	public int getFaceInfoCountByTime(String deviceCode);
}
