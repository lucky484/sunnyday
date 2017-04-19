/*
 * @Title: FSliderWapService.java
 * @Package com.f2b2c.eco.service.platform
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年9月14日 下午2:30:09
 * @version V1.0
 */
package com.f2b2c.eco.service.platform;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.f2b2c.eco.model.platform.FSliderWapsModel;

/**
  * @ClassName: FSliderWapService
  * @Description: TODO
  * @author ligang.yang@softtek.com
  * @date 2016年9月14日 下午2:30:09
  *
  */
public interface FSliderWapService
{

    /**
     * @param 分页查询
     * @return Page<FKindModel>
     * @throws @author:
     *             ligang.yang@softtek.com
     * @version: 2016年9月14日 下午3:55:07
     */
    Page<FSliderWapsModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap);
    FSliderWapsModel querySliderWapById(Integer id);
    
    /**
	 * 修改功能
	 * @param name
	 * @return
	 */
	int update( FSliderWapsModel model);
	
	/**
	 * 添加功能
	 * @param id
	 * @param name
	 * @return
	 */
	public int insert(FSliderWapsModel model);

	/**
	 * 根据主键ID删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
	
	List<FSliderWapsModel> queryAllSliderWap();
}
