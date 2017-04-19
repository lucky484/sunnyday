/**
 * 
 */
package com.hd.pfirs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IdCardComPoliceModel;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;

/**
 * @ClassName:
 * @Description:
 * @author
 * @date Dec 23, 2015 4:55:32 PM
 */
public interface FugitivesService {

	public T_QB_RY_ZTRYJBXX getT_QB_RY_ZTRYJBXX(String id);

	public T_QB_RY_ZTRYJBXX getJiKong(String id);

	public List<IdCardComPoliceModel> comAtCtlLibs(String IdCardNo);

	String comAtCtlLib(String idCardNo);
	
	public List<IdCardComPoliceModel> queryIdCardCompareResult(@Param(value = "cardno") String cardno);

}
