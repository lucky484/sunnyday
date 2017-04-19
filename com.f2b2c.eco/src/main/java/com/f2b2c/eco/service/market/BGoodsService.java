package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BUserModel;

public interface BGoodsService {

	public List<BGoodsToCModel> queryBGoodsByName(String name);
	
	public int queryBGoodsByNameCount(String name);
   
	/**
     * 查询所有商品
     * 
     * @param length
     * @param start
     */
	public List<BGoodsToCModel> queryAllBGoods(Integer start, Integer length);

	/**
     * 根据分类id查询商品
     * 
     * @param list
     * 
     * @param kindId
     */
	public List<BGoodsToCModel> queryBGoodsByKindId(List<String> list, Integer kindId, Integer start, Integer length);

	public int findAllBGoodsCount();

	public int findAllBGoodsByKindIdCount(List<String> list, Integer kindId);

	public BGoodsInfoToCModel queryBGoodsInfoById(Integer id,Integer userId);
	
	/**
     * 查询所有的提交审核的商品
     * 
     * @param pageable
     * @param paramMap
     * @return
     */
	public Page<BGoodsModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);
	
	/**
     * 修改审核不通过时原因
     * 
     * @param bGoodsModel
     * @return
     */
	public int updateReply(BGoodsModel bGoodsModel);

	public int updateStatus(String id, String status);

	public List<BGoodsToCModel> findGoodsByKindList(List<String> list, Integer shopId, int i, int j);

	public int findGoodsByKindListCount(List<String> list, Integer shopId);

	/**
     * 发布B端商品
     * 
     * @param bGoodsModel
     * @return
     */
	public int insertBGoods(BGoodsModel bGoodsModel);

	public org.springframework.data.domain.Page<BGoodsModel> findBgoodsByShopId(Pageable pageable,
			Map<String, Object> paramMap);

	public BGoodsModel findBgoodsById(Integer goodsId);

	public int modifybgoods(BGoodsModel bGoodsModel, BUserModel user);

	public int nextshelfbgoods(Integer integer);
	
	/**
     * 查询店铺中的商品
     * 
     * @param name
     * @param shopId
     * @return
     */
	List<BGoodsToCModel> queryGoodsByShopId(@Param(value="name")String name,@Param("shopId")Integer shopId);
	
	/**
     * 根据店铺id获取商品数量
     * 
     * @param id:店铺主键
     * @return 返回商铺数量
     */
	Integer selectGoodsCountByShopId(Integer id);
	
	/**
     * 根据类型id查询热销商品
     * 
     * @param double2
     * @param double1
     */
	public com.f2b2c.eco.model.market.Page queryHotBGoodsByKindId(Map<String, Object> map, Double double1,
			Double double2);
	   
    /**
     * 查询商店下的所有水果和所有非水果
     * 
     * @param shopId
     * @return
     */
    List<BGoodsModel> queryAllGoodsByShopId(Map<String,Object> map);
    
    /**
     * 查询商店下所有水果和所有非水果的总记录数
     * 
     * @param map
     * @return
     */
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

	public com.f2b2c.eco.model.market.Page queryFavoriteBgoods(Map<String, Object> map);

	int isFavorite(Integer userId, String goodsNo);

	/**
     * 根据店铺id下架所有商品
     * 
     * @param shopId:店铺id
     * @return 返回下架操作结果状态
     */
	int updateGoodsByShopId(Integer shopId,Integer userId);

    BGoodsModel findBgoodsInfoById(Integer goodsId);

    int modifybgoodsRemain(BGoodsModel bGoodsModel, BUserModel user);
}
