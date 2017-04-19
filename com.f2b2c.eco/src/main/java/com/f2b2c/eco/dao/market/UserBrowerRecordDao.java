package com.f2b2c.eco.dao.market;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.UserBrowerRecordModel;

public interface UserBrowerRecordDao {
      
	  /**
	 * 新增浏览记录
	 * 
	 * @param userBrowerRecord
	 * @return
	 */
	   int insertBrowerRecord(UserBrowerRecordModel userBrowerRecord);
	  
	  /**
	 * 查询商品是否存在
	 * 
	 * @param goodsId
	 * @return
	 */
	   int queryGoodsIsExists(Integer goodsId);
	  
	  /**
	 * 修改浏览记录
	 * 
	 * @param userBrowerRecord
	 * @return
	 */
	   int updateBrowerRecord(UserBrowerRecordModel userBrowerRecord);
	  
	  /**
	 * 删除100以后的记录
	 */
	void deleteBrowerRecord(Map<String, Object> map);
	   
	   /**
	 * 浏览记录的分页查询
	 * 
	 * @param map
	 * @return
	 */
	   List<BGoodsModel> queryAllBrowerRecord(Map<String,Object> map); 
	   
	   /**
	 * 浏览记录的总记录数
	 * 
	 * @param map
	 * @return
	 */
	   int queryAllBrowerRecordCount(Map<String,Object> map);
	   
	   /**
	 * 用户浏览过的商品
	 * 
	 * @param userId
	 * @return
	 */
	   List<BGoodsModel> queryBrowerRecordByUserId(Map<String,Object> map);
	   
	   /**
	 * 附近有店铺，没有匹配上浏览记录的数据
	 * 
	 * @param map
	 * @return
	 */
	   List<BGoodsModel> queryShopGoods(Map<String,Object> map);
	   
	   int queryShopGoodsCount(Map<String,Object> map);
	   
	   /**
	 * 查询所有和浏览记录匹配的水果
	 * 
	 * @param map
	 * @return
	 */
	   List<BGoodsModel> queryAllFeFruit(Map<String,Object> map);
	   
	   int queryAllFeFruitCount(Map<String,Object> map);
	   
	   /**
	 * 查询所有的非水果
	 * 
	 * @param map
	 * @return
	 */
	   List<BGoodsModel> queryFeFruit(Map<String,Object> map);
	   
	   int queryFeFruitCount(@Param(value="kindId") Integer kindId);
	   
	   int queryBrowerRecordCountByUserId(Map<String,Object> map);
}
