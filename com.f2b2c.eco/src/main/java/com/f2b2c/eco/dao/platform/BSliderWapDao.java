package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FSliderWapsModel;

public interface BSliderWapDao extends CrudMapper<FSliderWapsModel, Serializable>
{
    /**
     * 查询分页条数
     * 
     * @return int
     * @throws 
     * @author:ligang.yang@softtek.com
     * @version: 2016/9/14
     */
    int queryCountByCondition(Map<String, Object> paramMap);

    /**
     * 分页查询
     * 
     * @return List<FUserModel>
     * @throws 
     * @author:ligang.yang@softtek.com
     * @version:  2016/9/14
     */
    List<FSliderWapsModel> findWithPagination(Map<String, Object> paramMap);
    FSliderWapsModel querySliderWapById(Integer id);
    /**
     * 修改功能
     * @author mozzie.chu
     */
    int update(FSliderWapsModel model);
    
    /**
     * 添加功能
     * @author mozzie.chu
     */
    int insert(FSliderWapsModel model);

    /**
     * 根据主键ID删除
     * @param id
     * @return
     */
    int  delete(Integer id);
    
    List<FSliderWapsModel> queryAllSliderWap();
}