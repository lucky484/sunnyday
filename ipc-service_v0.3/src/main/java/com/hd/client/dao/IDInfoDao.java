package com.hd.client.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.client.model.IdCardInfo;

public interface IDInfoDao {

	/**
	 * 保存身份证信息到数据库
	 * 
	 * @param modal
	 * @return
	 */
	public void saveIDInfo(IdCardInfo modal);

	public void saveFCInfo(IdCardInfo modal);

	// 获取身份证采集数据
	public List<IdCardInfo> getIdCardInfoList();

	// 新增数据
	public int addIDInfoDao(IDInfoDao idInfoDao);

	// 获取一条数据
	public IdCardInfo getIdCardInfo();

	// 更新身份证信息
	public int updateIDInfoDao(@Param(value = "cardId") String cardId, @Param(value = "relayFlag") String relayFlag);

	// 更新身份证信息
	public int updateFCInfoDao(@Param(value = "fc_id") String cardId, @Param(value = "relayFlag") String relayFlag);

	/**
	 * 采集信息和转发信息条数
	 * 
	 * @return
	 */
	public Map<String, Object> countIdCardInfo();
}
