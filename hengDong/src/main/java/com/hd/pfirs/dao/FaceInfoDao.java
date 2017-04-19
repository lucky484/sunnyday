/**
 * 
 */
package com.hd.pfirs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.FaceInfo;

/**
 * @ClassName: faceInfoDao
 * @Description: 人脸采集信息
 * @author light.chen
 * @date Dec 16, 2015 9:38:42 AM
 */
public interface FaceInfoDao {

	public void saveFaceInfo(FaceInfo faceInfo);

	public FaceInfo getFaceInfo();
	
	/**
	 * 前台实时监控查询
	 */
	public List<FaceInfo> getFaceInfoListWeb(String deviceCode);
	
	/**
	 * 更新状态
	 */
	public void updateFaceInfo(@Param(value = "faceId")long faceId, @Param(value = "relayFlag")String relayFlag,@Param(value = "relayDate")Date relayDate,@Param(value = "updateTime")Date updateTime);

	/**
	 * 
	 * @Description: 查询当天的人脸数据采集量
	 * @param time
	 * @return
	 * @return: int
	 */
	public int getFaceInfoCountByTime(@Param(value = "time") String time,@Param(value = "deviceCode") String deviceCode);

}
