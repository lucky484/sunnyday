package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IntegratedQueryMessageModel;

public interface IntegratedQueryMessageService {

	/**
	 * 根据cardcode得到综合查询对象
	 * @return
	 */
	public IntegratedQueryMessageModel getIntegratedQueryMessageModelByCardCode(String cardCode);
	/**
	 * 根据facecode得到综合查询对象
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getIntegratedQueryMessageModelListByFaceCode(String facecode);
	/**
	 * 根据facecode得到人脸对比信息
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByFaceCode(String facecode);
	/**
	 * 根据facecode得到人脸对比信息T_QB_RY_CKRYJBXX
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByT_QB_RY_CKRYJBXX(String facecode);
	/**
	 * 根据facecode得到人脸对比信息T_QB_LK_LKBK
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByT_QB_LK_LKBK(String facecode);
	/**
	 * 根据facecode得到3个库的人脸对比信息
	 * @param facecode
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareListByjk(String facecode);
}
