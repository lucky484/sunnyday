/*
 * @Title: FSliderWapServiceImpl.java
 * @Package com.f2b2c.eco.service.platform.impl
 * @Description: wap管理
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年9月14日 下午2:29:09
 * @version V1.0
 */
package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FSliderWapDao;
import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FSliderWapService;

/**
 * @ClassName: FSliderWapServiceImpl
 * @Description: wap管理
 * @author ligang.yang@softtek.com
 * @date 2016年9月14日 下午2:29:09
 *
 */
@Service
public class FSliderWapServiceImpl implements FSliderWapService
{

    @Autowired
    private FSliderWapDao fSliderWapDao;
    /**
     * 分页查询
     */
    @Override
    public Page<FSliderWapsModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap)
    {
    	int total = fSliderWapDao.queryCountByCondition(paramMap);
        paramMap.put("pageable", pageable);
        List<FSliderWapsModel> list = fSliderWapDao.findWithPagination(paramMap);

        Page<FSliderWapsModel> pages = new Pagination<>(list, pageable, total);
        return pages;
    }
    
    /**
     * 根据id查询
     */
	@Override
	public FSliderWapsModel querySliderWapById(Integer id) {
		
		return fSliderWapDao.querySliderWapById(id);
	}
    
	/**
	 * 修改wap
	 */
	@Override
	public int update(FSliderWapsModel model) {
		model.setUpdateTime(new Date());
		return fSliderWapDao.update(model);
	}
	
	/**
	 * 添加wap
	 */
	@Override
	public int insert(FSliderWapsModel model) {
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return fSliderWapDao.insert(model);
	}

	/**
	 * 根据主键ID删除
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		Integer integer = null;
		if(StringUtils.isNotBlank(id)){
			integer = Integer.valueOf(id);
		}
		return fSliderWapDao.delete(integer);
	}

	@Override
	public List<FSliderWapsModel> queryAllSliderWap() {
		return fSliderWapDao.queryAllSliderWap();
	}

}
