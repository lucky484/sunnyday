package com.f2b2c.eco.dao.market;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BGoodsToPurchaseOrderModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BShopInfoModel;

public interface BGoodsDao extends CrudMapper<BGoodsModel, Integer>{
    
	public List<BGoodsToCModel> queryBGoodsByName(String name);
	
	public int queryBGoodsByNameCount(String name);

	public List<BGoodsToCModel> queryBGoodsByKindId(@Param(value = "idlist") List<String> list,
			@Param(value = "kindId") Integer kindId,
			@Param(value = "start") Integer start, @Param(value = "length") Integer length);

	public List<BGoodsToCModel> findGoodsByKindList(@Param(value = "idlist") List<String> idlist,
			@Param(value = "shopId") Integer shopId, @Param(value = "start") int i, @Param(value = "length") int j);

	public List<BGoodsToCModel> queryAllBGoods(Integer start, Integer length);

    // 查询所有商品总条数
	public int findAllBGoodsCount();

    // 查询所有商品总条数(根据类别Id)
	public int findAllBGoodsByKindIdCount(@Param(value = "idlist") List<String> list,
			@Param(value = "kindId") Integer kindId);

	public int findGoodsByKindListCount(@Param(value = "idlist") List<String> idlist,
			@Param(value = "shopId") Integer shopId);

	public BGoodsInfoToCModel queryBGoodsInfoById(Integer id);

	public List<BGoodsInfoToCModel> queryGoodsListByShopId(Integer shopId);
	
	/**
     * 批量更新商品数量
     * 
     * @param list
     * @return
     */
	int updateBatchGoodsQuantity(List<BSalesOrderDetailsModel> list);

	public int updateStatus(BGoodsModel bGoodsModel);

	public List<BGoodsInfoToCModel> queryGoodsListByShopIdAndKindId(Integer shopId, Integer kindId);

	/**
     * 根据商品id获取商品信息
     * 
     * @param id
     * @return
     */
	BGoodsToPurchaseOrderModel getGoodsInfoById(Integer id);

	public BGoodsModel selectByGoodsNo(String goodsNo);

	/**
     * 发布商品
     */
	public int insertBGoods(BGoodsModel bGoodsModel);

	public int findBgoodsByShopIdCount(Map<String, Object> paramMap);

	public List<BGoodsModel> findBgoodsByShopId(Map<String, Object> paramMap);

	public BGoodsModel findBgoodsById(Integer id);

	public void downBgoodsAndIsCopy(Integer id);

	public int modifybgoods(Map<String, Object> map);

	public int nextshelfbgoods(Integer id);
	
  	/**
     * 根据商品id列表获取商品名称
     * 
     * @param list
     *            : id列表
     * @return 返回商品名称
     */
	String getGoodsNameByIdList(List<Integer> list);
	
	/**
     * 根据商品id列表找到店家的xy坐标
     * 
     * @param list
     * @return
     */
	List<BShopInfoModel> getlocationByIdList(List<Integer> list);
	
	int updateBatchGoodsQuantityPlus(List<BSalesOrderDetailsModel> list);
	
	/**
     * 根据主键id更新销量
     * 
     * @param bGoodsModel
     * @return
     */
	int updateSalesById(BGoodsModel bGoodsModel);
	
	/**
     * 更新商品锁定的数量
     * 
     * @param list
     * @return
     */
	int updateBatchGoodsAllocate(List<BSalesOrderDetailsModel> list);
	
	/**
     * 查询某个商店中的水果
     */
	List<BGoodsToCModel> queryGoodsByShopId(@Param(value="name")String name,@Param("shopId")Integer shopId);
	
	/**
     * 根据店铺id获取商品数量
     * 
     * @param id:店铺id
     * @return 返回商铺数量
     */
	Integer selectGoodsCountByShopId(Integer id);
	
	List<BGoodsToCModel> findHotGoodsByShopId(@Param("shopId") Integer shopId,
			@Param("start") Integer start, @Param("length") Integer length);

	int findHotGoodsByShopIdCount(@Param("shopId") Integer shopId);

	int findHotNotFruitCount();

	List<BGoodsToCModel> findHotNotFruit(@Param("start") Integer start, @Param("length") Integer length);
	
	/**
     * 查询商店下的所有的水果
     * 
     * @param shopId
     * @return
     */
	List<BGoodsModel> queryAllGoodsByShopId(Map<String,Object> map);
	
	int queryAllGoodsCountByShopId(Map<String,Object> map);
	
	/**
     * 查询所有非水果商品
     * 
     * @param map
     * @return
     */
	List<BGoodsModel> queryAllGoods(Map<String,Object> map);
	
	int queryAllGoodsCount(Integer kindId);

	BGoodsModel bgoogsNoIsTrue(String goodsNo);

	List<BGoodsToCModel> queryFavoriteBgoods(@Param("userId") int userId, @Param("start") int start,
			@Param("length") int length);

	int queryFavoriteBgoodsCount(int userId);

	int isFavorite(Integer userId, String goodsNo);
	
	/**
     * 根据店铺id下架所有商品
     * 
     * @param shopId:店铺id
     * @return 返回下架操作结果状态
     */
	int updateGoodsByShopId(Integer shopId);

    BGoodsModel findBgoodsInfoById(Integer goodsId);

    int updateRemain(Map<String, Object> map);

    int findGoodsCountByKindIdList(@Param("kindidlists") List<Integer> kindidlists);
}