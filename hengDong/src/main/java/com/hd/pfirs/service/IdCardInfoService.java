package com.hd.pfirs.service;

import java.util.Date;
import java.util.List;

import com.hd.pfirs.model.IdCardCompWarn;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.IdCardInfoStrModel;

/**
 * 
 * @author cliff.fan
 *
 */
public interface IdCardInfoService {

	public String saveIdCardInfo(IdCardInfoModel model);

	public IdCardInfoModel getIdCardInfoModel();

	public void updateIdCardInfo(long cardId, String relayFlag,Date relayTime,Date updateTime);

	public List<IdCardInfoStrModel> getIdCardInfoByCollectTimeList(String deviceCode);

	public IdCardCompWarn getIdCardInfoCompResult(String warningTime,String deviceCode);

	/**
	 * @Description: 得到身份证当天采集端次数
	 * @return
	 * @return: int
	 */
	public int getIdCardInfoCountByTime(String deviceCode);

	/**
	 * 
	  * @Description: 查看身份证对比预警数
	  * @param      : 
	  * @return     : int
	  * @throws  
	  * @data       : 2016年1月19日 下午2:56:29   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	public int queryIdCardWarnNum(String deviceCode);

	/**
	 * 预警完了更新状态
	 * @param cardComprltid
	 */
	public void updateFlag(String cardComprltid);

	/**
	 * 预警完了之后显示最新的一条
	 * @return
	 */
	public IdCardCompWarn getLastIdCardInfoCompResult(String warningTime,String deviceCode);

	/**
	  * @Description: 根据16位id查全信息
	  * @param      : String cardID16
	  * @return     : List<IdCardInfoModel>
	  * @throws  
	  * @data       : 2016年1月29日 上午10:07:29   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	public List<IdCardInfoModel> queryCardIdInfoByCard16(String cardID16);
	
	/**
	 * index 身份证预警
	 * @param warningTime
	 * @return
	 */
	public List<IdCardCompWarn> indexIdcardWarningInfo(String warningTime);
}
