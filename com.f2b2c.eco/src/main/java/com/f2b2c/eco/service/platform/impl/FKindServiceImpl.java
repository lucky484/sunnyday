package com.f2b2c.eco.service.platform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.FGoodsDao;
import com.f2b2c.eco.dao.platform.FKindDao;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FKindService;
import com.f2b2c.eco.util.PropertiesUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class FKindServiceImpl implements FKindService {

	@Autowired
	private FKindDao fKindDao;
	@Autowired
	private FGoodsDao fGoodsDao;

	@Override
	public List<BKindToCModel> queryAllKind() {
		return fKindDao.queryAllKind();
	}

	@Override
	public void queryKindsByParentId(Map<String, Object> map, FKindModel model) {
		Integer id = model.getId();
		Map<String, Object> temp = new HashMap<String, Object>();
		List<FKindModel> childs = fKindDao.queryKindsByParentId(id);
		temp.put("this_node", model);
		temp.put("nodes", childs);
		map.put("data", temp);
	}

	@Override
	public List<FKindModel> queryAllFkindModel() {
		return fKindDao.queryAllFKind();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<FKindModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap, HttpServletRequest request) {
		// paramMap.put("start", pageable.getPageNumber());
		// paramMap.put("offset", pageable.getPageSize());
		paramMap.put("pageable", pageable);
		List<FKindModel> fKinds = fKindDao.findWithPagination(paramMap);
		int total = fKindDao.queryCountByCondition(paramMap);
		for (FKindModel model : fKinds) {
			String iconUrl = model.getIconUrl();
			//String baseUrl = UrlUtil.getUrlPathWithContext(request) + "/F2B2C/files/";
			String baseUrl = PropertiesUtil.getValue("path");
			if (StringUtils.isNotBlank(iconUrl)) {
				iconUrl = baseUrl + iconUrl;
				model.setIconUrl(iconUrl);
			} else {
				model.setIconUrl("");
			}
		}
		Page<FKindModel> pages = new Pagination<FKindModel>(fKinds, pageable, total);
		return pages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertFKindModel(FKindModel modal) {
		fKindDao.insert(modal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteFKindModel(String id) {
		Integer intId = null;
		if (StringUtils.isNotBlank(id)) {
			intId = Integer.valueOf(id);
		}
        // 查看分类下面是否还有子分类，如果有子分类则同样需要禁用
		List<String> cateList = fKindDao.findChdKind(intId);
		cateList.add(id);
		int size = 0;
		if(cateList.size()>0){
            // 批量删除商品
		    fGoodsDao.delBatchFGoodsByKindList(cateList);
            // 批量删除分类
	        size = fKindDao.deleteBatchFKindByIds(cateList);
		}
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int updateFKindModel(FKindModel model) {
		return fKindDao.updateByPrimaryKeySelective(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> queryChilds(Integer id) {
		FKindModel model = fKindDao.selectByPrimaryKey(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 200);
		map.put("msg", "success");
		if (model != null) {
			queryKindsByParentId(map, model);
		} else {
			map.put("status", -1);
			map.put("msg", "Invalid Id");
		}
		return map;
	}

	/**
     * 获取水果分类
     */
	@Override
	public List<FKindModel> selectParentKindByFruit() {
		List<FKindModel> list = fKindDao.selectParentKindByFruit();
		if (list.size() > 0) {
			FKindModel fKindModel = list.get(0);
			List<FKindModel> subList = fKindDao.queryKindsByParentId(fKindModel.getId());
			return subList;
		} else {
			return list;
		}
	}

	@Override
	public JSONArray getKindsForJsTree() {
		return toJsonTree(fKindDao.queryAllKindsForTreeByParentId(-1));
	}

	/**
     * 使用jstree生成树形结构
     * 
     * @param list
     * @return
     */
	private JSONArray toJsonTree(List<BKindToCModel> list) {
		JSONArray array = new JSONArray();
		for (BKindToCModel kind : list) {
			JSONObject object = new JSONObject();
			object.put("id", kind.getId());
			object.put("text", kind.getKindName());
			if (kind.getSubList() != null && !kind.getSubList().isEmpty()) {
				object.put("state","closed");
				object.put("children", toJsonTree(kind.getSubList()));
			}
			array.add(object);
		}
		return array;
	}

	@Override
	public int enable(Integer id, Integer enable, FUserModel user) {
        // 不管是禁用还是启用 都要获得下面所有的分类
		List<FKindModel> list = fKindDao.getAllKindByParentId(id);
		FKindModel fKindModel = new FKindModel();
		fKindModel.setId(id);
		list.add(fKindModel);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enable", enable);
		map.put("list", list);
		map.put("userId", user.getId());
		int number = fKindDao.enable(map);
		if (number > 0) {
            // 禁用分类
			if (enable == 0) {
                // 下架所有商品
				fGoodsDao.downAllFgoodsByKindId(map);
			} else {

			}
		}
		return number;
	}

	@Override
	public int deleteFKind(String id) {
		Integer intId = null;
		if (StringUtils.isNotBlank(id)) {
			intId = Integer.valueOf(id);
		}
        // 查看分类下面是否还有子分类，如果有子分类则同样需要禁用
		List<FKindModel> lists = fKindDao.getAllKindByParentId(intId);
		if(CollectionUtils.isNotEmpty(lists)){
			for(FKindModel kindModel:lists){
				fKindDao.deleteByPrimaryKey(kindModel.getId());
			}
		}
		
		return fKindDao.deleteByPrimaryKey(intId);
	}

    @Override
    public int updateFkindWeight(Integer id, Integer weight) {
        return fKindDao.updateFkindWeight(id, weight);
    }
}