package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.platform.BKindDao;
import com.f2b2c.eco.dao.platform.FKindDao;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.service.market.FKindService;
import com.f2b2c.eco.util.PropertiesUtil;

@Service
public class BKindServiceImpl implements FKindService {

    @Autowired
	private FKindDao fKindDao;
    
    @Autowired
    private BKindDao bKindDao;

    @Override
    public List<BKindToCModel> queryAllKind() {
		return fKindDao.queryAllKindOnPhone();
    }

    @Override
    public List<String> findChdKind(Integer id) {
        return bKindDao.findChdKind(id);
    }

    /**
     * 获取一级分类或默认分类
     */
    @Override
    public DataToCResultModel<Map<String, Object>> queryFirstKind(Integer pId) {
        DataToCResultModel<Map<String, Object>> result = new DataToCResultModel<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<BKindToCModel> fList = bKindDao.queryFrontKindByPId(pId);
        if (fList.size() > 0) {
            map.put("firstKind", fList);
            map.put("subKindList", getSubKindList(fList.get(0).getId()));
        }
        result.setStatus(200);
        result.setMsg("获取分类成功");
        result.setData(map);
        return result;
    }

    /**
     * 根据父级获取子分类
     * 
     * @param pId
     * @return
     */
    @Override
    public List<BKindToCModel> getSubKindList(Integer pId) {
        // 获取二级分类
        List<BKindToCModel> sList = bKindDao.queryFrontKindByPId(pId);
        // 获取三级分类
        List<BKindToCModel> tList = bKindDao.queryFrontSecondKindByPId(pId);
        List<BKindToCModel> secondList = new ArrayList<BKindToCModel>();
        List<BKindToCModel> thirdList = null;
        // 将三级分类对应父id二级id 组织成list
        for (BKindToCModel sKind : sList) {
            thirdList = new ArrayList<BKindToCModel>();
            for (BKindToCModel bKind : tList) {
                if(sKind.getId().equals(bKind.getParentId())){
                    thirdList.add(bKind);
                }
            }
            sKind.setSubList(thirdList);
            secondList.add(sKind);
        }
        return secondList;
    }

    @Override
	public List<FKindModel> getAllKinds() {
        // 将所有的水果分类拿出来，做图片的拼接
        String path = PropertiesUtil.getValue("path");
		List<FKindModel> lists = fKindDao.getAllKinds();
		List<FKindModel> returnLists = new ArrayList<FKindModel>();
        if(CollectionUtils.isNotEmpty(lists)){
			for (FKindModel fKindModel : lists) {
				fKindModel.setIconUrl(path + fKindModel.getIconUrl());
				returnLists.add(fKindModel);
            }
        }
        return returnLists;
    }

    @Override
	public List<FKindModel> getSubKindByParentId(Integer kindId) {
        String path = PropertiesUtil.getValue("path");
		List<FKindModel> lists = fKindDao.getSubKindByParentId(kindId);
		List<FKindModel> returnLists = new ArrayList<FKindModel>();
        if(CollectionUtils.isNotEmpty(lists)){
			for (FKindModel fKindModel : lists) {
				fKindModel.setIconUrl(path + fKindModel.getIconUrl());
				returnLists.add(fKindModel);
            }
        }
		return returnLists;
    }

    @Override
    public List<BKindToCModel> queryAllKindTo() {
        
        return bKindDao.queryAllKind();
    }

    @Override
    public String findcatalogById(Integer kindId) {
		return bKindDao.findcatalogById(kindId);
    }

	@Override
	public List<BKindToCModel> queryKindListByKindId(Integer kindId) {
		return bKindDao.queryKindListByKindId(kindId);
	}

    @Override
    public String findIsCommissionById(Integer id) {
        // TODO Auto-generated method stub
        return bKindDao.findIsCommissionById(id);
    }
}