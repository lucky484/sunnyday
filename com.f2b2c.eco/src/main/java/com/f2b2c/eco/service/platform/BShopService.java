package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.platform.FUserModel;

public interface BShopService {

	Page<BShopInfoModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);

	int saveBShopInfo(BShopInfoModel shopModel,FUserModel userModel);

	int updateBShopInfo(BShopInfoModel shopModel, FUserModel userModel);

	int deleteBShopInfoById(Integer id,Integer userId);

	BShopInfoModel getBShopInfoById(Integer id);

	/**
	 * 启用禁用门店
	 * 
	 * @param id:门店id
	 * @param tag:标识(1.启用门店
	 *            0.禁用门店)
	 * @return 返回操作结果返回
	 */
	Integer enable(String id,String tag);
	
	/**
	 * 根据店铺id获取店铺信息
	 * 
	 * @param id:店铺id
	 * @return 返回店铺信息
	 */
	Object get(Integer id);
	
	/**
	 * 修改店铺信息
	 * 
	 * @param id:主键
	 * @param shopName:店铺名称
	 * @param remark:描述
	 * @param address:地址
	 * @param locationX:经度
	 * @param locationY:维度
	 * @return 返回修改是否成功状态
	 */
	Integer modify(String id,String shopName,String remark,String address,String areaCode,String phone);
	
	/**
	 * 选择省
	 * 
	 * @param code:省code
	 * @return 返回省下面的城市
	 */
	Object chooseProvince(String code);
	
	/**
	 * 选择市
	 * 
	 * @param code
	 * @return
	 */
	Object chooseCity(String code);

	/**
	 * 根据专属顾问IdList查询店铺创建人list
	 * 
	 * @param list
	 * @return
	 */
	List<Integer> queryBUserIdByList(List<Integer> list);
}