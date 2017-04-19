package com.f2b2c.eco.dao.market;

import java.util.List;

import com.f2b2c.eco.model.bean.BFreightBean;
import com.f2b2c.eco.model.market.BShopFreightModel;
import com.f2b2c.eco.model.market.Page;

/**
 * 店铺运费设置
 * @author jane.hui
 *
 */
public interface BShopFreightDao {
    
    /**
     * 根据主键删除店铺运费表
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入店铺运费表
     * @mbggenerated
     */
    int insert(BShopFreightModel record);

    /**
     * 插入可选字段的店铺运费表
     * @mbggenerated
     */
    int insertSelective(BShopFreightModel record);

    /**
     * 根据主键查询店铺运费表
     * @mbggenerated
     */
    BShopFreightModel selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新可选的店铺运费表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BShopFreightModel record);

    /**
     * 更新店铺运费表
     * @mbggenerated
     */
    int updateByPrimaryKey(BShopFreightModel record);
    
    /**
     * 根据店铺id获取运费
     * @param shopId:店铺id
     * @return 返回运费bean
     */
    List<BFreightBean> selectFreightByShopId(Integer shopId);
    
    /**
     * 批量修改运费设置
     * @param list:店铺运费设置
     * @return 返回操作结果
     */
    int updateBatchFreight(List<BShopFreightModel> list);
    
    /**
     * 批量插入修改运费设置
     * @param list：店铺运费设置
     * @return 返回操作结果
     */
    int insertBatchFreight(List<BShopFreightModel> list);
    
    /**
     * 根据店铺id和省id获取运费
     * @return
     */
    Integer getFreightByShopIdAndPId(Page page);
}