package com.f2b2c.eco.dao.platform;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.f2b2c.eco.dao.common.CrudMapper;
import com.f2b2c.eco.model.platform.FHelpModel;
/**
 * 
 * @author mozzie.chu
 *
 */
public interface FHelpDao extends CrudMapper<FHelpModel, Serializable>{

	/**
     * 查询分页条数
     * 
     * @return int
     * @throws 
     * @author: mozzie.chu@softtek.com
     * @version: 2016/9/14
     */
    int queryCountByCondition(Map<String, Object> paramMap);

    /**
     * 分页查询
     * 
     * @return List<FUserModel>
     * @throws 
     * @author: mozzie.chu@softtek.com
     * @version:  2016/9/14
     */
    List<FHelpModel>  findWithPagination(Map<String, Object> paramMap);
    FHelpModel queryHelpById(Integer id);
    
    /**
     * 修改功能
     * @author mozzie.chu
     */
    int update(FHelpModel model);
    
    /**
     * 添加功能
     * @author mozzie.chu
     */
    int insert(FHelpModel model);
    //int insert(Map<String, Object> map);
    
    /**
     * 根据主键ID删除
     * @param id
     * @return
     */
    int  delete(Integer id);
    
    /**
     * 热点问题
     * @param style
     * @return
     */
    List<FHelpModel> queryDetailByStyle(String style);
    
    /**
     * 具体问题
     * @param style
     * @return
     */
    List<FHelpModel> queryDetailByType(String type);
}
