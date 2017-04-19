package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FHelpModel;
import com.f2b2c.eco.model.platform.FSliderWapsModel;

/**
 * 
 * @author mozzie.chu
 *
 */
public interface FHelpService {

	/**
	 * 分页查询
	 * @param pageable
	 * @param paramMap
	 * @author mozzie.chu
	 * @return
	 */
	Page<FHelpModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);
	FHelpModel queryHelpById(Integer id);
	
	/**
	 * 修改功能
	 * @param name
	 * @author mozzie.chu
	 * @return
	 */
	int update( FHelpModel model);
	
	/**
	 * 添加功能
	 * @param id
	 * @param name
	 * @author mozzie.chu
	 * @return
	 */
	public int insert(FHelpModel model);
//	public int insert(Map<String, Object> map);

	/**
	 * 根据主键ID删除
	 * @param id
	 * @author mozzie.chu
	 * @return
	 */
	public int delete(String id);
	
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
