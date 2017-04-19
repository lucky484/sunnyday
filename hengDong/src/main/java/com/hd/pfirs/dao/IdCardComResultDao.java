package com.hd.pfirs.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IdCardComModel;
import com.hd.pfirs.model.IdCardInfoModel;

/**
 * 顺便存身份证比对表
 * 
 * @author ligang.yang
 *
 */
public interface IdCardComResultDao {

	/**
	 * 保存身份证到比对表
	 * 
	 * @param model
	 */
	public void saveIdCardInfo2ComResult(IdCardInfoModel model);

	/**
	 * 得到比对表中的身份证
	 * 
	 * @return
	 */
	public Map<String, Object> getIdCardComMap();

	/**
	 * 更新比对中的转发状态
	 * 
	 * @param comId
	 */
	public void updateIdCardComInfo(@Param(value = "comId") Long comId);

	/**
	 * 更新比对中的转发状态
	 * 
	 * @param comId
	 */
	public void updateIdCardComModel(IdCardComModel model);
}
