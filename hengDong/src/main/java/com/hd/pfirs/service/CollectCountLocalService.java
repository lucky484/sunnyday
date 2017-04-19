package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.CollectCountModel;

/**
 * 采集统计查询本地数据库
 * @author curry.su
 *
 */
public interface CollectCountLocalService {
	/**
	 * 身份证号码为空的查询
	 * @param page
	 * @param collectSite
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CollectCountModel> getCollectCountModellList(int page,String collectSite,String startDate,String endDate,int fys);
	
	public int getCollectCountModelCount(String collectSite,String startDate,String endDate);
	
	/**
	 * 身份证号码不为空的
	 * @param page
	 * @param idCardNo
	 * @param collectSite
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CollectCountModel> getCollectCountModelListByidCardNo(int page,String idCardNo,String collectSite,String startDate,String endDate,int fys);
	
	public int getCollectCountModelCountByidCardNo(String idCardNo,String collectSite,String startDate,String endDate);
	/**
	 * 得到详情信息
	 * @param idCardNo
	 * @param collectSite
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CollectCountModel> getCollectCountModelListByidCardNoDetails(String idCardNo,String collectSite,String startDate,String endDate,String compareBaseID);
	/**
	 * 得到综合信息身份证信息
	 * @param idCardNo
	 * @param collectSite
	 * @param collectTime
	 * @return
	 */
	public CollectCountModel getCollectCountModel(String idCardNo,String collectSite,String collectTime);
	
	/**
	 * 根据身份证号码得到身份信息
	 * @param idCardNo
	 * @return
	 */
	public CollectCountModel getCollectCountModelByIdCardNo(String idCardNo);
	
	/**
	 * 根据cardcode得到身份信息
	 * @param cardCode
	 * @return
	 */
	public CollectCountModel getCollectCountModelByCardCode(String cardCode);
}
