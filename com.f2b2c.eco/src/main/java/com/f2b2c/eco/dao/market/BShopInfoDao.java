package com.f2b2c.eco.dao.market;

import java.io.Serializable;
import java.util.List;

import com.f2b2c.eco.apimodel.Area;
import com.f2b2c.eco.apimodel.City;
import com.f2b2c.eco.apimodel.Province;
import com.f2b2c.eco.bean.platform.BShopInfoBean;
import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.platform.FShopToShopCartModel;

public interface BShopInfoDao extends CrudMapper<BShopInfoModel, Serializable>{

	public int insert(BShopInfoModel bShopInfo);
	
	/**
	 * 进入店铺主页头部的店铺信息
	 * 
	 * @param shopId
	 * @return
	 */
	BShopInfoToCModel queryShopInfo(Integer shopId);

	public List<BShopInfoToCModel> queryAllShop();
	
	/**
	 * 根据用户id获取店铺信息
	 * 
	 * @param userId
	 * @return
	 */
	FShopToShopCartModel selectBShopInfoByBUserId(Integer userId);
    

	public BShopInfoModel findShopInfoByUserId(Integer id);
	
	/**
	 * 启用禁用门店操作
	 * 
	 * @param id:门店id
	 * @param tag:标识(1.启用
	 *            0.禁用)
	 * @return 返回启用禁用状态
	 */
	Integer enable(String id,String tag);

	public Integer selectEnableByUserId(Integer userId);
    
    /**
	 * 根据店铺id获取店铺信息
	 * 
	 * @param id:店铺id
	 * @return:返回店铺信息
	 */
    BShopInfoBean selectBShopInfoList(Integer id);
    
    /**
	 * 获取省列表
	 * 
	 * @return 返回省列表
	 */
    List<Province> selectProvinceList();
    
    /**
	 * 获取城市列表
	 * 
	 * @param provincecode:省code
	 * @return 返回城市列表
	 */
    List<City> selectCityList(String provincecode);
    
    /**
	 * 获取区域列表
	 * 
	 * @param citycode:城市code
	 * @return 返回区域列表
	 */
    List<Area> selectAreaList(String citycode);
    
    /**
	 * 根据主键id修改店铺信息
	 * 
	 * @param bShopInfoBean:店铺bean
	 * @return 返回修改是否成功状态
	 */
    Integer modifyShopById(BShopInfoBean bShopInfoBean);
    
    /**
	 * 根据店铺id获取店老板id
	 * 
	 * @param id
	 * @return
	 */
    Integer selectBUserIdBySId(Integer id);

	/**
	 * 根据专属顾问IdList查询店铺创建人list
	 * 
	 * @param list
	 * @return
	 */
	List<Integer> queryBUserIdByList(List<Integer> list);

	int queryFavoriteBgoodsCount(int userId);

	List<BShopInfoBean> queryFavoriteShop(int userId, int start, int length);

	int isFavorite(Integer shopId, Integer userId);
	
	/**
	 * 查询店铺详情
	 * @param userId
	 * @return
	 */
	BShopInfoModel queryShopNameByUserId(Integer userId);
}