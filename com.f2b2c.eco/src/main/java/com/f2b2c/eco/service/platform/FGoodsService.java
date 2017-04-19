package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FGoodsToBModel;
import com.f2b2c.eco.model.platform.FUserModel;

/**
 * @author mozzie.chu
 * @version 2016/09/09
 */
public interface FGoodsService {

	/**
     * 主页-统计-查询商品数
     * 
     * @return
     */
	// public int selectCount();

	public int findAllFGoodsCount();

	public List<FGoodsModel> queryAllFGoods(Integer start, Integer length);

	public FGoodsModel queryFGoodsInfoById(Integer id);

	int insertFGoods(FGoodsModel fGoods);

	int updateFGoods(FGoodsModel fGoods);

	/**
     * 根据名称模糊查询商品
     * 
     * @param name
     *            :查询名称
     * @param start:起始页
     * @param length:搜索页数大小
     * @return 返回查询后的商品
     */
	List<FGoodsToBModel> queryFGoodsByName(String userId, String cityName, String name, Integer start, Integer length);

	/**
     * 根据名称模糊查询商品
     * 
     * @param name
     *            搜索名称
     * @return 返回商品总条数
     */
	int queryCountFGoodsByName(String userId, String cityName, String name);

	/**
     * 根据名称模糊查询商品
     * 
     * @param name
     *            :查询名称
     * @param start:起始页
     * @param length:搜索页数大小
     * @return 返回查询后的商品
     */
	List<FGoodsToBModel> queryFGoodsByKindId(String cityName, String userId, Integer kindId, Integer start, Integer length, String type);

	/**
     * 根据分类id模糊查询商品
     * 
     * @param name
     *            搜索名称
     * @return 返回商品总条数
     */
	int queryFGoodCountByKindList(String cityName, String userId, Integer kindId, String type);

	/**
     * 根据主键id获取商品详情
     * 
     * @param id
     *            商品主键
     * @return 返回商品详情
     */
	FGoodsToBModel queryFGoodsDetailById(Integer id);

	/**
     * 获取城市id
     * 
     * @param userId:用戶id
     * @param cityName:城市名稱
     * @return 返回城市
     */
	Integer getCityId(String userId, String cityName);

	public org.springframework.data.domain.Page<FGoodsModel> findFgoodsPages(Pageable pageable, Map<String, Object> paramMap);

	public FGoodsModel findFgoodsById(Integer valueOf);

	public int modifyfgoods(FGoodsModel fGoods, FUserModel user);

	public int downOrReleaseFgoods(Integer valueOf, Integer valueOf2);

	/**
     * 获取首页分类和商品列表数据
     * 
     * @return
     */
	Map<String, Object> getFIndex(String userId, String page, String pageSize, String cityName);

	/**
     * 获取首页分类和商品列表数据
     * 
     * @return
     */
	Map<String, Object> filter(String userId, String page, String pageSize, String cityName, String sKindType, String tKindType, String type);

	FGoodsModel fgoogsNoIsTrue(String goodsNo);

    FGoodsModel findFgoodsInfoById(Integer valueOf);

    int updateFgoodsWeight(String goodsNo, Integer valueOf);
	
}