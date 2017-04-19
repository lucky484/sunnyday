package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FLogisticsDao;
import com.f2b2c.eco.model.platform.FLogisticsModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FLogisticsService;

/**
 * 
 * @author mozzie.chu
 *
 */
@Service
public class FLogisticsServiceImpl implements FLogisticsService{

	@Autowired
	private FLogisticsDao fLogisticsDao;
	
	/**
	 * 查物流方式
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	@Override
	public Page<FLogisticsModel> queryLogisticsModel(Pageable pageable, Map<String, Object> paramMap) {
		int total = fLogisticsDao.queryLogisticsCount(paramMap);
//		paramMap.put("start", pageable.getPageNumber());
//		paramMap.put("offset", pageable.getPageSize());
		paramMap.put("pageable", pageable);
		List<FLogisticsModel> list = fLogisticsDao.findWithPagination(paramMap);
		
		Page<FLogisticsModel> pages = new Pagination<>(list,pageable,total);
		return pages;
	}
	@Override
	public FLogisticsModel queryLogisticsById(Integer id){		
		return fLogisticsDao.queryLogisticsById(id);
	}
	/**
	 * 修改功能
	 * @param name
	 * @return
	 */
	@Override
	public int update( FLogisticsModel model) {
		model.setUpdatedTime(new Date());
		return fLogisticsDao.update(model);
	}

	/**
	 * 添加功能
	 */
	@Override
	public int insert(FLogisticsModel model) {
		model.setCreatedTime(new Date());
		return fLogisticsDao.insert(model);
	}
	
	/**
	 * 删除功能
	 */
	@Override
	public int delete(String id) {
		Integer integer = null;
		if(StringUtils.isNotBlank(id)){
			integer = Integer.valueOf(id);
		}
		return fLogisticsDao.delete(integer);
	}

}
