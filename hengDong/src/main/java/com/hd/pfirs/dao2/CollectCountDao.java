package com.hd.pfirs.dao2;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.CollectCountModel;
import com.hd.pfirs.model.IntegratedQueryMessageModel;

/**
 * 
 * @author curry.su
 *
 */
public interface CollectCountDao {
	
	/**
	 * 根据身份证号查找当前身份证是否为逃犯
	 * @param idCardNo
	 * @return
	 */
	public int getidCardInfoCompareResult(@Param(value = "idCardNo")String idCardNo,@Param(value = "compareBaseID")String compareBaseID,@Param(value="colum")String colum);
	
	/**
	 * 根据集合查找所以逃犯的身份证号码并返回
	 * @param idCardNoList（'身份证1','身份证2'....）
	 * @return
	 */
	public List<CollectCountModel> getidCardInfoCompareResultList(@Param(value = "idCardNoList")String idCardNoList);
	
	/**
	 * 根据身份证号查找在逃人员案件类型
	 * @param idCardNo
	 * @return
	 */
	public List<CollectCountModel> getIdCardInfoCompareResultType(@Param(value = "idCardNo")String idCardNo);

	/**
	 * 根据身份证号查找T_QB_LK_LKBK案件类型
	 * @param idCardNo
	 * @return
	 */
	public List<CollectCountModel> getIdCardInfoCompareResultTypeByT_QB_LK_LKBK(@Param(value = "idCardNo")String idCardNo);

	/**
	 * 根据身份证号查找T_QB_RY_CKRYJBXX案件类型
	 * @param idCardNo
	 * @return
	 */
	public List<CollectCountModel> getIdCardInfoCompareResultTypeByT_QB_RY_CKRYJBXX(@Param(value = "idCardNo")String idCardNo);
	/**
	 * 根据身份证号查找T_QB_RY_CKRYJBXX案件类型
	 * @param idCardNo
	 * @return
	 */
	public List<CollectCountModel> getIdCardInfoCompareResultTypeByjk(@Param(value = "idCardNo")String idCardNo,@Param(value = "compareBaseID")String compareBaseID);
	
	
	/**
	 * 根据逃犯id得到逃犯身份信息和案件
	 * @param ctrlBaseId
	 * @return
	 */
	public List<IntegratedQueryMessageModel> getCompareResult(@Param(value = "ctrlBaseId")String ctrlBaseId);
}
