package com.f2b2c.eco.service.platform.impl;

import java.util.ArrayList;
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

import com.f2b2c.eco.dao.market.BGoodsDao;
import com.f2b2c.eco.dao.platform.BKindDao;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.BKindModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.F2BbKindService;
import com.f2b2c.eco.util.PropertiesUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class F2BbKindServiceImpl implements F2BbKindService {

	@Autowired
	private BKindDao bKindDao;

    @Autowired
    private BGoodsDao bgoodsDao;

	@Override
	public List<BKindToCModel> queryAllKind() {
		return bKindDao.queryAllKind();
	}

	@Override
	public void queryKindsByParentId(Map<String, Object> map, BKindModel model) {
		Integer id = model.getId();
		Map<String, Object> temp = new HashMap<String, Object>();
		List<BKindModel> childs = bKindDao.queryKindsByParentId(id);
		temp.put("this_node", model);
		temp.put("nodes", childs);
		map.put("data", temp);
	}

	@Override
	public List<BKindModel> queryAllBKindModel() {
		return bKindDao.queryAllFKind();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<BKindModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap,
			HttpServletRequest request) {
		// paramMap.put("start", pageable.getPageNumber());
		// paramMap.put("offset", pageable.getPageSize());
		paramMap.put("pageable", pageable);
		List<BKindModel> fKinds = bKindDao.findWithPagination(paramMap);
		int total = bKindDao.queryCountByCondition(paramMap);
		for (BKindModel model : fKinds) {
			String iconUrl = model.getIconUrl();
			String picUrl = model.getPicUrl();
			//String baseUrl = UrlUtil.getUrlPathWithContext(request) + "/F2B2C/files/";
			String baseUrl = PropertiesUtil.getValue("path");
			if (StringUtils.isNotBlank(iconUrl)) {
				iconUrl = baseUrl + iconUrl;
				model.setIconUrl(iconUrl);
			} else {
				model.setIconUrl("");
			}

			if (StringUtils.isNotBlank(picUrl)) {
				picUrl = baseUrl + picUrl;
				model.setPicUrl(picUrl);
			} else {
				model.setPicUrl("");
			}
		}
		Page<BKindModel> pages = new Pagination<BKindModel>(fKinds, pageable, total);
		return pages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertBKindModel(BKindModel modal) {
		bKindDao.insert(modal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    public DataToCResultModel<Object> deleteBKindModel(String id) {
        DataToCResultModel<Object> dataToCResult = new DataToCResultModel<Object>();
        Integer intId = null;
		if (StringUtils.isNotBlank(id)) {
			intId = Integer.valueOf(id);
		}
        // 查看分类下面是否还有子分类，如果有子分类则同样需要禁用
		List<BKindModel> lists = bKindDao.getAllKindByParentId(intId);
        BKindModel bKindModel = new BKindModel();
        bKindModel.setId(intId);
        lists.add(bKindModel);
        List<Integer> kindidlists = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            kindidlists.add(lists.get(i).getId());
        }
        // 如果分类下面有商品 则不允许禁用
        int number = bgoodsDao.findGoodsCountByKindIdList(kindidlists);
        if (number > 0) {
            dataToCResult.setStatus(400);
            dataToCResult.setMsg("分类下存在商品,不允许删除分类");
            return dataToCResult;
        }
		if(CollectionUtils.isNotEmpty(lists)){
			for (BKindModel kindModel : lists) {
				bKindDao.deleteByPrimaryKey(kindModel.getId());
			}
		}
		
        int size = bKindDao.deleteByPrimaryKey(intId);
        if (size > 0) {
            dataToCResult.setStatus(200);
            dataToCResult.setMsg("删除成功");
        } else {
            dataToCResult.setStatus(400);
            dataToCResult.setMsg("删除失败");
        }
        return dataToCResult;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int updateBKindModel(BKindModel model) {
		return bKindDao.updateByPrimaryKeySelective(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> queryChilds(Integer id) {
		BKindModel model = bKindDao.selectByPrimaryKey(id);
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
	public List<BKindModel> selectParentKindByFruit() {
		List<BKindModel> list = bKindDao.selectParentKindByFruit();
		if (list.size() > 0) {
			BKindModel BKindModel = list.get(0);
			List<BKindModel> subList = bKindDao.queryKindsByParentId(BKindModel.getId());
			return subList;
		} else {
			return list;
		}
	}

	@Override
	public JSONArray getKindsForJsTree() {
		return toJsonTree(bKindDao.queryAllKindsForTreeByParentId(-1));
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
	public List<BKindModel> getAllKinds() {
        // 将所有的水果分类拿出来，做图片的拼接
		String path = PropertiesUtil.getValue("path");
		List<BKindModel> lists = bKindDao.getAllKinds();
		List<BKindModel> returnLists = new ArrayList<BKindModel>();
		if (CollectionUtils.isNotEmpty(lists)) {
			for (BKindModel bKindModel : lists) {
				bKindModel.setIconUrl(path + bKindModel.getIconUrl());
				returnLists.add(bKindModel);
			}
		}
		return returnLists;
	}

	@Override
	public List<BKindModel> getSubKindByParentId(Integer kindId) {
		String path = PropertiesUtil.getValue("path");
		List<BKindModel> lists = bKindDao.getSubKindByParentId(kindId);
		List<BKindModel> returnLists = new ArrayList<BKindModel>();
		if (CollectionUtils.isNotEmpty(lists)) {
			for (BKindModel bKindModel : lists) {
				bKindModel.setIconUrl(path + bKindModel.getIconUrl());
				returnLists.add(bKindModel);
			}
		}
		return returnLists;
	}
}