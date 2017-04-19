package com.f2b2c.eco.service.platform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.constant.platform.Constant;
import com.f2b2c.eco.constant.platform.Constant.IndexType;
import com.f2b2c.eco.dao.market.BShopInfoDao;
import com.f2b2c.eco.dao.platform.FFrontGoodsDao;
import com.f2b2c.eco.dao.platform.FGoodsDao;
import com.f2b2c.eco.dao.platform.FKindDao;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.bean.FkindBean;
import com.f2b2c.eco.model.bean.GoodsTypeBean;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FGoodsToBModel;
import com.f2b2c.eco.model.platform.FShopToShopCartModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FGoodsService;
import com.f2b2c.eco.util.LogoUrlUtil;
import com.f2b2c.eco.util.PageUtil;
import com.f2b2c.eco.util.PropertiesUtil;

import jodd.util.StringUtil;

/**
 * @author mozzie.chu
 * @version 2016/09/09
 */
@Service
public class FGoodsServiceImpl implements FGoodsService {

    @Autowired
    private FGoodsDao fGoodsDao;

    @Autowired
    private FFrontGoodsDao fFrontGoodsDao;

    @Autowired
    private FKindDao fKindDao;

    @Autowired
    private BShopInfoDao bShopInfoDao;

    @Override
    public int findAllFGoodsCount() {
        return fGoodsDao.findAllFGoodsCount();
    }

    @Override
    public List<FGoodsModel> queryAllFGoods(Integer start, Integer length) {
        return fGoodsDao.queryAllFGoods(start, length);
    }

    @Override
    public FGoodsModel queryFGoodsInfoById(Integer id) {
        return fGoodsDao.select(id);
    }

    @Override
    public int insertFGoods(FGoodsModel fGoods) {
        return fGoodsDao.insert(fGoods);
    }

    @Override
    public int updateFGoods(FGoodsModel fGoods) {
        return fGoodsDao.updateByPrimaryKeySelective(fGoods);
    }

    /**
     * 根据名称模糊查询商品
     * 
     * @param name
     *            :查询名称
     * @param start:起始页
     * @param length:搜索页数大小
     * @return 返回查询后的商品
     */
    @Override
    public List<FGoodsToBModel> queryFGoodsByName(String userId, String cityName, String name, Integer start, Integer length) {
        Integer cityId = getCityId(userId, cityName);
        Page page = new Page();
        page.setStart(0);
        page.setLength(length);
        page.getParams().put("name", name);
        page.getParams().put("cityId", cityId);
        return fFrontGoodsDao.queryFGoodByName(page);
    }

    /**
     * 根据名称模糊查询商品
     * 
     * @param name
     *            搜索名称
     * @return 返回商品总条数
     */
    @Override
    public int queryCountFGoodsByName(String userId, String cityName, String name) {
        Integer cityId = getCityId(userId, cityName);
        Page page = new Page();
        page.getParams().put("name", name);
        page.getParams().put("cityId", cityId);
        return fFrontGoodsDao.queryCountFGoodByName(page);
    }

    /**
     * 根据分类id查找商品
     */
    @Override
    public List<FGoodsToBModel> queryFGoodsByKindId(String cityName, String userId, Integer kindId, Integer start, Integer length, String type) {
        Integer cityId = getCityId(userId, cityName);
        // 查询类型下所有的类型
        List<String> kindList = fKindDao.findChdKind(kindId);
        kindList.add(String.valueOf(kindId));
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        if (kindList.size() > 0) {
            page.getParams().put("list", kindList);
        } else {
            page.getParams().put("list", null);
        }
        if(Constant.IndexType.ALL.equals(type)){
            page.getParams().put("type", null);
        } else {
            page.getParams().put("type", type);
        }
        page.getParams().put("cityId", cityId);
        return fFrontGoodsDao.queryFGoodByKindList(page);
    }

    /**
     * 根据分类id获取商品列表
     */
    @Override
    public int queryFGoodCountByKindList(String cityName, String userId, Integer kindId, String type) {
        Integer cityId = getCityId(userId, cityName);
        List<String> list = fKindDao.findChdKind(kindId);
        list.add(String.valueOf(kindId));
        Page page = new Page();
        if (list.size() > 0) {
            page.getParams().put("list", list);
        } else {
            page.getParams().put("list", null);
        }
        if(Constant.IndexType.ALL.equals(type)){
            page.getParams().put("type", null);
        } else {
            page.getParams().put("type", type);
        }
        page.getParams().put("cityId", cityId);
        return fFrontGoodsDao.queryFGoodCountByKindList(page);
    }

    /**
     * 获取城市id
     * 
     * @param userId:用戶id
     * @param cityName:城市名称
     * @return 返回城市id
     */
    @Override
    public Integer getCityId(String userId, String cityName) {
        Integer cityId = null;
        if (StringUtil.isNotEmpty(userId)) {
            // 根据用户id获取商户信息
            FShopToShopCartModel fShopToShopCartModel = bShopInfoDao.selectBShopInfoByBUserId(Integer.valueOf(userId));
            if (fShopToShopCartModel != null && fShopToShopCartModel.getCityId() != null) {
                cityId = fShopToShopCartModel.getCityId();
            } else {
                cityId = fFrontGoodsDao.getCityIdByCityName(cityName);
            }
        } else {
            cityId = fFrontGoodsDao.getCityIdByCityName(cityName);
        }
        return cityId;
    }

    /**
     * 根据商品主键获取商品详情
     * 
     * @param id:商品主键
     * @return 返回商品详情
     */
    @Override
    public FGoodsToBModel queryFGoodsDetailById(Integer id) {
        return fFrontGoodsDao.queryFGoodsDetailById(id);
    }

    @Override
    public org.springframework.data.domain.Page<FGoodsModel> findFgoodsPages(Pageable pageable, Map<String, Object> paramMap) {
        List<Integer> kindIds = new ArrayList<Integer>();
        if (paramMap.get("kind") != null && !paramMap.get("kind").equals("")) {
            kindIds.add(Integer.valueOf((String) paramMap.get("kind")));
            List<BKindToCModel> kinds = fKindDao.queryAllKindsForTreeByParentId(Integer.valueOf((String) paramMap.get("kind")));
            getKindIds(kinds, kindIds);
            paramMap.put("kindIds", kindIds);
        }
        int total = fGoodsDao.findFgoodsCount(paramMap);
        List<FGoodsModel> lists = fGoodsDao.findFgoodsPage(paramMap);
        for (int i = 0; i < lists.size(); i++) {
            if (StringUtils.isNotBlank(lists.get(i).getLogoUrl())) {
                String[] logUrl = lists.get(i).getLogoUrl().split("\\|");
                lists.get(i).setLogoUrl(PropertiesUtil.getValue("path") + logUrl[0]);
            }
        }
        org.springframework.data.domain.Page<FGoodsModel> pages = new Pagination<>(lists, pageable, total);
        return pages;
    }

    private void getKindIds(List<BKindToCModel> list, List<Integer> ids) {
        for (BKindToCModel kind : list) {
            ids.add(kind.getId());
            if (kind.getSubList() != null && !kind.getSubList().isEmpty()) {
                getKindIds(kind.getSubList(), ids);
            }
        }
    }

    @Override
    public FGoodsModel findFgoodsById(Integer valueOf) {
        return fGoodsDao.findFgoodsById(valueOf);
    }

    @Override
    public int modifyfgoods(FGoodsModel fGoods, FUserModel user) {
        // 查询老数据
        FGoodsModel existFGoods = fGoodsDao.findFgoodsById(fGoods.getId());
        Integer version = existFGoods.getVersion() + 1;
        existFGoods.setIsCopy(1);
        // 下架老数据并且变成拷贝
        Integer oldId = existFGoods.getId();
        fGoodsDao.insert(existFGoods);
        // 拷贝数据Id
        Integer id = existFGoods.getId();
        fGoodsDao.Copy(oldId);
        fGoods.setVersion(version);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fGoods", fGoods);
        map.put("id", id);
        int size = fGoodsDao.modifyfgoods(map);
        return size;
    }

    @Override
    public int downOrReleaseFgoods(Integer valueOf, Integer valueOf2) {
        return fGoodsDao.downOrReleaseFgoods(valueOf, valueOf2);
    }

    /**
     * 获取首页分类和商品列表数据
     */
    @Override
    public Map<String, Object> getFIndex(String userId, String page, String pageSize, String cityName) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取商品类型
        List<GoodsTypeBean> goodsTypeList = new ArrayList<GoodsTypeBean>();
        GoodsTypeBean goodsType = new GoodsTypeBean();
      
        goodsType = new GoodsTypeBean();
        goodsType.setId(0);
        goodsType.setName("全部");
        goodsTypeList.add(goodsType);
        
        goodsType = new GoodsTypeBean();
        goodsType.setId(1);
        goodsType.setName("国产水果");
        goodsTypeList.add(goodsType);
        
        goodsType = new GoodsTypeBean();
        goodsType.setId(2);
        goodsType.setName("进口水果");
        goodsTypeList.add(goodsType);
        
        goodsType = new GoodsTypeBean();
        goodsType.setId(3);
        goodsType.setName("活动商品");
        goodsTypeList.add(goodsType);
        
        goodsType = new GoodsTypeBean();
        goodsType.setId(4);
        goodsType.setName("其他商品");
        goodsTypeList.add(goodsType);
        
        map.put("goodsType", goodsTypeList);
        List<FGoodsToBModel> goodsList = null;
        Integer id = fKindDao.queryFirstCategoryId();
        List<FkindBean> secondList = new ArrayList<FkindBean>();
        if(id!=null){
            // 根据父类水果分类获取二级分类
            secondList = fKindDao.queryFKindByPid(id);
            // 取二级分类中的第一条数据作为默认二级分类将获取三级三类
            if(secondList.size()>0){
                FkindBean fkindBean = secondList.get(0);
                List<FkindBean> thirdList = fKindDao.queryFKindByPid(fkindBean.getId()); 
                fkindBean.setSubList(thirdList);
                // 获取总条数
                int total = 0;
                if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(cityName)){
                    total = 0;
                } else {
                    total = queryFGoodCountByKindList(cityName,userId,Integer.valueOf(fkindBean.getId()),"0");
                }
                map.put("totalCount", total);
                Integer intPage = Integer.valueOf(page);
                Integer intPageSize = Integer.valueOf(pageSize);
                Integer start = PageUtil.getStart(intPage, intPageSize);
                Integer length = intPageSize;
                // 当用户未登录或者经纬度都为空时候将商品列表数据为空
                if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(cityName)){
                    goodsList = new ArrayList<FGoodsToBModel>();
                } else {
                    goodsList = queryFGoodsByKindId(cityName,userId,fkindBean.getId(),start,length,"0");
                    for (FGoodsToBModel goods : goodsList) {
                        goods.setLogoUrl(LogoUrlUtil.splitOneImgUrl(goods.getLogoUrl()));
                    }
                }
                map.put("goodsList", goodsList);
            } else {
                map.put("totalCount", 0);
            }
         }

         map.put("secondList", secondList);
         return map;
    }

    /**
     * 根据商品类别和类型筛选商品
     * 
     * @param userId:登录用户id
     * @param page:起始页
     * @param pageSize:分页大小
     * @param cityName:城市名称
     * @param sKindType:二级分类id
     * @param tKindType：三级分类id
     * @param type:商品类型
     * @return 返回三级分类以及商品列表
     */
    @Override
    public Map<String, Object> filter(String userId, String page, String pageSize, String cityName, String sKindType, String tKindType, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer category = 0;
        List<FkindBean> thirdList = new ArrayList<FkindBean>();
        if(StringUtil.isNotEmpty(tKindType)&&!Constant.ALL_Category.equals(tKindType)){
            category = Integer.valueOf(tKindType);
        } else if(StringUtil.isNotEmpty(tKindType)&&Constant.ALL_Category.equals(tKindType)){
            category = Integer.valueOf(sKindType);
        } else {
            Page kindPage = new Page();
            if(!IndexType.ALL.equals(type)){
                kindPage.getParams().put("type", type);
            } else {
            	kindPage.getParams().put("type", null);
            }
            kindPage.getParams().put("pId", sKindType);
            thirdList = fKindDao.queryFilterTCateById(kindPage);
            category = Integer.valueOf(sKindType);
        }
        int total = 0;
        if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(cityName)){
            total = 0;
        } else {
            total = queryFGoodCountByKindList(cityName,userId,Integer.valueOf(sKindType),type);
        }
        map.put("totalCount", total);
        Integer intPage = Integer.valueOf(page);
        Integer intPageSize = Integer.valueOf(pageSize);
        Integer start = PageUtil.getStart(intPage, intPageSize);
        Integer length = intPageSize;
        List<FGoodsToBModel> goodsList = null;;
        if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(cityName)){
            goodsList = new ArrayList<FGoodsToBModel>();
        } else {
            goodsList = queryFGoodsByKindId(cityName,userId,category,start,length,type);
            for (FGoodsToBModel goods : goodsList) {
                goods.setLogoUrl(LogoUrlUtil.splitOneImgUrl(goods.getLogoUrl()));
            }
        }
        map.put("goodsList", goodsList);
        map.put("thirdList", thirdList);
        return map;
    }

	@Override
	public FGoodsModel fgoogsNoIsTrue(String goodsNo) {
		return fGoodsDao.fgoogsNoIsTrue(goodsNo);
	}

    @Override
    public FGoodsModel findFgoodsInfoById(Integer id) {
        return fGoodsDao.findFgoodsInfoById(id);
    }

    @Override
    public int updateFgoodsWeight(String goodsNo, Integer weight) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsNo", goodsNo);
        map.put("weight", weight);
        return fGoodsDao.updateFgoodsWeight(map);
    }

}