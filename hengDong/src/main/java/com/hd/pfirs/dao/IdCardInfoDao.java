package com.hd.pfirs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IdCardCompWarn;
import com.hd.pfirs.model.IdCardInfoModel;
import com.hd.pfirs.model.IdCardInfoStrModel;

/**
 * 
 * @author cliff.fan
 *
 */
public interface IdCardInfoDao {

	public void saveIdCardInfo(IdCardInfoModel idCardInfoModel);

	public IdCardInfoModel getIdCardInfoModel();

	public void updateIdCardInfo(@Param(value = "cardId") long cardId, @Param(value = "relayFlag") String relayFlag,@Param(value = "relayTime") Date relayTime,@Param(value = "updateTime") Date updateTime);

	public List<IdCardInfoStrModel> getIdCardInfoByCollectTimeList(String deviceCode);

	public IdCardCompWarn getIdCardInfoCompResult(@Param(value = "warningTime") String warningTime,@Param(value = "deviceCode")String deviceCode);

	/**
	 * 
	 * @Description: 查询身份证采集总数每天
	 * @param time (每天)
	 * @return
	 * @return: int
	 */
	public int getIdCardInfoCountByTime(@Param(value = "time") String time,@Param(value = "deviceCode")String deviceCode);

	/**
	 * 
	  * @Description: 当天身份证对比预警数
	  * @param      : 
	  * @return     : int
	  * @throws  
	  * @data       : 2016年1月19日 下午2:57:21   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	 */
	int queryIdCardWarnNum(String deviceCode);

	/**
	 * 预警完了更新状态
	 * @param cardComprltid
	 */
	public void updateFlag(@Param(value = "cardComprltid") String cardComprltid);

	/**
	 * 预警完了之后显示最新的一条
	 * @return
	 */
	public IdCardCompWarn getLastIdCardInfoCompResult(@Param( value="warningTime")String warningTime,@Param( value="deviceCode")String deviceCode);

	/**
	  * @Description: 根据16id查全身份证全信息
	  * @param      : @Param(value = "cardID16") String cardID16
	  * @return     : List<IdCardInfoModel>
	  * @throws  
	  * @data       : 2016年1月29日 上午10:00:41   
	  * @version:   : v1.0   
	  * @author     : ligang.yang@softtek.com
	  */
	public List<IdCardInfoModel> queryCardIdInfoByCard16(@Param(value = "cardID16") String cardID16);
	
	/**
	 * index 身份证预警
	 * @param warningTime
	 * @return
	 */
	public List<IdCardCompWarn> indexIdcardWarningInfo(String warningTime);
}
