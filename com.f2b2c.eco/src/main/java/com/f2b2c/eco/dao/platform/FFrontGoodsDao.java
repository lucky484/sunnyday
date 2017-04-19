package com.f2b2c.eco.dao.platform;

import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.platform.FGoodsToBModel;
import java.util.List;

/**
 * 前台商品搜索
 * @author jane.hui
 *
 */
public interface FFrontGoodsDao {

	/**
	 * 根据名称模糊查询商品
	 * @param page :搜索参数
	 * @return 返回查询后的商品
	 */
	List<FGoodsToBModel> queryFGoodByName(Page page);
	
	/**
	 * 根据名称模糊查询商品
	 * @param name 搜索名称
	 * @return 返回商品总条数
	 */
	Integer queryCountFGoodByName(Page page);
	
	/**
	 * 根据分类list查询商品
	 * @param page 搜索参数
	 * @return 返回商品list
	 */
	List<FGoodsToBModel> queryFGoodByKindList(Page page);
	
	/**
	 * 根据名称模糊查询商品
	 * @param name 搜索名称
	 * @return 返回商品总条数
	 */
	Integer queryFGoodCountByKindList(Page page);
	
	/**
	 * 根据商品主键获取商品详情
	 * @param id:商品主键
	 * @return :返回商品详情
	 */
	FGoodsToBModel queryFGoodsDetailById(Integer id);
	
	/**
	 * 根据商品编号获取商品详情
	 * @param id:商品编号
	 * @return :返回商品编号
	 */
	List<FGoodsToBModel> queryFGoodsDetailByGoodsNo(String goodsNo);
	
	/**
	 * 根据城市名称查询城市id
	 * @param name
	 * @return
	 */
	Integer getCityIdByCityName(String name);
}