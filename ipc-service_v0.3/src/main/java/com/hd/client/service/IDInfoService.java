package com.hd.client.service;

import java.util.List;
import java.util.Map;

import com.hd.client.dao.IDInfoDao;
import com.hd.client.model.BaseInfo;
import com.hd.client.model.IdCardInfo;

/**
 * 保存身份证设备采集下来的信息
 * 
 * @author ligang.yang
 *
 */
public interface IDInfoService {
	/**
	 * 保存设备信息
	 * 
	 * @param idcard
	 */
	public int save(IdCardInfo info);

	public BaseInfo queryIDInfo();

	public List<IdCardInfo> getIdCardInfoList();

	public IdCardInfo getIdCardInfo();

	public int addIDInfoDao(IDInfoDao idInfoDao);

	public int updateIDInfoDao(String cardId, String relayFlag);

	public int updateFCInfoDao(String fc_id, String relayFlag);

	Map<String, Object> countIdCardInfo();

}
