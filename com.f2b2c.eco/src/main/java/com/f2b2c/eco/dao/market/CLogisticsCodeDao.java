
package com.f2b2c.eco.dao.market;

import java.util.List;
import java.util.Map;

import com.f2b2c.eco.model.market.CLogisticsCodeModel;

/**
 * 物流dao
 * @author jane.hui
 *
 */
public interface CLogisticsCodeDao {
    /**
     * 根据主键删除物流信息
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入物流信息
     * @mbggenerated
     */
    int insert(CLogisticsCodeModel record);

    /**
     * 插入可选的物流信息
     * @mbggenerated
     */
    int insertSelective(CLogisticsCodeModel record);

    /**
     * 根据主键查询物流信息表
     * @mbggenerated
     */
    CLogisticsCodeModel selectByPrimaryKey(Integer id);

    /**
     * 更新可选的物流信息表
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CLogisticsCodeModel record);

    /**
     * 更新物流信息表
     * @mbggenerated
     */
    int updateByPrimaryKey(CLogisticsCodeModel record);
    
    /**
     * 获取物流信息总条数
     * @return
     */
    Integer getLogisticsCodeSize(Map<String,Object> map);
    
    /**
     * 获取物流信息数据
     * @param map
     * @return
     */
    List<CLogisticsCodeModel> queryLogisticsCode(Map<String,Object> map);
    
    /**
     * 根据物流编码查找物流公司
     * @param code:物流编码
     * @return 返回物流公司
     */
    CLogisticsCodeModel queryLogisticsByCode(String code);
}