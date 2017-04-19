/**
 * 
 */
package com.hd.pfirs.dao2;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pfirs.model.IdCardComPoliceModel;
import com.hd.pfirs.model.T_QB_RY_ZTRYJBXX;

/**
 * @ClassName: FugitivesDao
 * @Description: 在逃人员
 * @author light.chen
 * @date Dec 23, 2015 4:51:20 PM
 */
public interface FugitivesDao {

	public T_QB_RY_ZTRYJBXX getT_QB_RY_ZTRYJBXX(@Param(value = "id") String id);

	public T_QB_RY_ZTRYJBXX getJiKong(String id);

	public T_QB_RY_ZTRYJBXX queryT_QB_RY_ZTRYJBXXByCardNo(String cardno);

	public List<IdCardComPoliceModel> queryAtCtlLibsByCardNo(@Param(value = "cardno") String cardno);

	public List<T_QB_RY_ZTRYJBXX> queryFugitivesById(@Param(value = "list") List list, @Param(value = "xm") String xm,
			@Param(value = "xb") String xb, @Param(value = "ysfzh") String ysfzh);

	public List<IdCardComPoliceModel> queryIdCardCompareResult(@Param(value = "cardno") String cardno);
}
