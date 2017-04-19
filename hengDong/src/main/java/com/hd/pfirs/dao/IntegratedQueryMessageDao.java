package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IntegratedQueryMessageModel;

public interface IntegratedQueryMessageDao {
	
	/**
	 * 根据cardcode得到综合查询对象
	 * @return
	 */
	public IntegratedQueryMessageModel getIntegratedQueryMessageModelByCardCode(@Param(value = "cardCode")String cardCode);
	/**
	 * 根据facecode得到综合查询对象
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getIntegratedQueryMessageModelListByFaceCode(@Param(value = "facecode")String facecode);
	
	/**
	 * 根据facecode得到人脸对比信息T_QB_RY_ZTRYJBXX
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByFaceCode(@Param(value = "facecode")String facecode);
	/**
	 * 根据facecode得到人脸对比信息T_QB_RY_CKRYJBXX
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByT_QB_RY_CKRYJBXX(@Param(value = "facecode")String facecode);
	/**
	 * 根据facecode得到人脸对比信息T_QB_LK_LKBK
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByT_QB_LK_LKBK(@Param(value = "facecode")String facecode);
	/**
	 * 根据facecode得到3个库的人脸对比信息
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByjk(@Param(value = "facecode")String facecode);
	
	
}
