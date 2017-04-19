package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;

public interface FGoodsDao extends CrudMapper<FGoodsModel, Serializable> {
	int findAllFGoodsCount();

	List<FGoodsModel> queryAllFGoods(@Param("start") Integer start, @Param("length") Integer length);


	List<FGoodsModel> findFgoodsPage(Map<String, Object> paramMap);

	int findFgoodsCount(Map<String, Object> paramMap);

	FGoodsModel findFgoodsById(Integer id);

	void Copy(Integer id);

	int modifyfgoods(Map<String, Object> map);

	int downOrReleaseFgoods(@Param("id") Integer id, @Param("status") Integer status);
	
	int updateBatchGoodsQuantityPlus(List<FSalesOrderDetailsModel> list);
	
	int updateBatchGoodsQuantity(List<FSalesOrderDetailsModel> list);

	void downAllFgoodsByKindId(Map<String, Object> map);
	
	/**
     * 批量删除商品
     * 
     * @param list:分类list
     * @return 返回删除状态
     */
    int delBatchFGoodsByKindList(List<String> list);

	FGoodsModel fgoogsNoIsTrue(String goodsNo);

    FGoodsModel findFgoodsInfoById(Integer id);

    int updateFgoodsWeight(Map<String, Object> map);
}