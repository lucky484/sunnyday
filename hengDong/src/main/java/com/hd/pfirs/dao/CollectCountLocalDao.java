package com.hd.pfirs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.CollectCountModel;

/**
 * 
 * @author curry.su
 *
 */
public interface CollectCountLocalDao {
	
	/**
	 * 身份证号码为空的查询
	 * @param page
	 * @param collectSite
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CollectCountModel> getCollectCountModellList(@Param(value = "page")int page,@Param(value = "collectSite")String collectSite,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,@Param(value = "fys")int fys);
	
	public int getCollectCountModelCount(@Param(value = "collectSite")String collectSite,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate);
	
	/**
	 * 身份证号码不为空的
	 * @param page
	 * @param idCardNo
	 * @param collectSite
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CollectCountModel> getCollectCountModelListByidCardNo(@Param(value = "page")int page,@Param(value = "idCardNo")String idCardNo,@Param(value = "collectSite")String collectSite,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,@Param(value = "fys")int fys);
	
	public int getCollectCountModelCountByidCardNo(@Param(value = "idCardNo")String idCardNo,@Param(value = "collectSite")String collectSite,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate);
	
	public List<CollectCountModel> getCollectCountModelListByidCardNoDetails(@Param(value = "idCardNo")String idCardNo,@Param(value = "collectSite")String collectSite,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,@Param(value = "compareBaseID")String compareBaseID);
	
	/**
	 * 得到综合信息身份证信息
	 * @param idCardNo
	 * @param collectSite
	 * @param collectTime
	 * @return
	 */
	public CollectCountModel getCollectCountModel(@Param(value = "idCardNo")String idCardNo,@Param(value = "collectSite")String collectSite,@Param(value = "collectTime")String collectTime);
	
	/**
	 * 根据身份证号码得到身份信息
	 * @param idCardNo
	 * @return
	 */
	public CollectCountModel getCollectCountModelByIdCardNo(@Param(value = "idCardNo")String idCardNo);
	
	/**
	 * 根据cardcode得到身份信息
	 * @param cardCode
	 * @return
	 */
	public CollectCountModel getCollectCountModelByCardCode(@Param(value = "cardCode")String cardCode);
}
