package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FHelpDao;
import com.f2b2c.eco.model.platform.FHelpModel;
import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FHelpService;

@Service
public class FHelpServiceImpl implements FHelpService{

	@Autowired
	private FHelpDao fHelpDao;

	/**
	 * @author mozzie.chu
	 * @param 分页查询
	 * @param paramMap
	 * @return
	 */
	@Override
	public Page<FHelpModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
		int total = fHelpDao.queryCountByCondition(paramMap);
		paramMap.put("pageable", pageable);
		List<FHelpModel> list = fHelpDao.findWithPagination(paramMap);
		Page<FHelpModel> pages = new Pagination<>(list, pageable, total);
		return pages;
	}

	/**
	 * 根据id查询
	 * @author mozzie.chu
	 * @param id
	 * @return
	 */
	@Override
	public FHelpModel queryHelpById(Integer id) {
		// TODO Auto-generated method stub
		return fHelpDao.queryHelpById(id);
	}

	/**
	 * 修改
	 * @author mozzie.chu
	 * @param model
	 * @return
	 */
	@Override
	public int update(FHelpModel model) {
		model.setUpdatedTime(new Date());
		return fHelpDao.update(model);
	}

	/**
	 * 添加
	 * @author mozzie.chu
	 * @param model
	 * @return
	 */
	@Override
	public int insert(FHelpModel model) {
		model.setCreatedTime(new Date());
		model.setUpdatedTime(new Date());
		return fHelpDao.insert(model);
	}

	/**
	 * 删除
	 * @author mozzie.chu
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		Integer integer = null;
		if(StringUtils.isNotBlank(id)){
			integer = Integer.valueOf(id);
		}
		return fHelpDao.delete(integer);
	}

	/**
     * 热点问题
     * @author mozzie.chu
     * @param style
     * @return
     */
	@Override
	public List<FHelpModel> queryDetailByStyle(String style) {
		
		return fHelpDao.queryDetailByStyle(style);
	}

	/**
     * 具体问题
     * @author mozzie.chu
     * @param type
     * @return
     */
	@Override
	public List<FHelpModel> queryDetailByType(String type) {
		
		return fHelpDao.queryDetailByType(type);
	}
}
