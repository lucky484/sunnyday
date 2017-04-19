package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.bean.platform.BShopInfoBean;
import com.f2b2c.eco.dao.market.BGoodsDao;
import com.f2b2c.eco.dao.market.BShopInfoDao;
import com.f2b2c.eco.model.market.BGoodsInfoInShopToCModel;
import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.PropertiesUtil;

@Service
public class BShopInfoServiceImpl implements BShopInfoService {
   
	@Autowired
	private BGoodsDao bGoodsDao;
	@Autowired
	private BShopInfoDao bShopInfoDao;

	/**
	 * 查询商铺对应
	 */
	@SuppressWarnings("static-access")
	@Override
	public Map<String, Object> findShopInfoById(Integer userId, Integer shopId) {
		Map<String, Object> map = new HashMap<String, Object>();
		JsonUtil jsonUtil = new JsonUtil();
		if (shopId != null) {
			// 查询店铺基本信息
			BShopInfoToCModel bShopInfoToCModel = bShopInfoDao.queryShopInfo(shopId);
			if (userId != null) {
				int count = bShopInfoDao.isFavorite(userId, shopId);
				if (count > 0) {
					bShopInfoToCModel.setIsFavorite(1);
				} else {
					bShopInfoToCModel.setIsFavorite(0);
				}
			} else {
				bShopInfoToCModel.setIsFavorite(0);
			}
				map.put("shopInfo", jsonUtil.parseToNoEmptyStr(bShopInfoToCModel));
		}
		// 准备发到前台的List
		List<BGoodsInfoInShopToCModel> datalist = new ArrayList<>();
		// 商户下面所有的商品List
		List<BGoodsInfoToCModel> list = bGoodsDao.queryGoodsListByShopId(shopId);
		// 默认的第一个商品信息
		List<BGoodsInfoToCModel> defaultlist = new ArrayList<>();

		if(list.size()>0){
			int flagId = 0;
			for (int i = 0; i < list.size(); i++) {
				// sql已经按照权重排序,第一个商品信息的类型一致的商品需要获得数据
				if (i == 0) {
					BGoodsInfoInShopToCModel bGoodsInfoInShopToCModel = new BGoodsInfoInShopToCModel();
					String kindName = list.get(i).getKindName();
					bGoodsInfoInShopToCModel.setKindId(list.get(i).getKindId());
					bGoodsInfoInShopToCModel.setKindName(kindName);
					datalist.add(bGoodsInfoInShopToCModel);
					defaultlist.add(list.get(i));
					flagId = list.get(i).getKindId();
				}
				// 如果不是第一个商品 并且 类型Id不等于标记Id
				if (i != 0 && !list.get(i).getKindId().equals(flagId)) {
					BGoodsInfoInShopToCModel bGoodsInfoInShopToCModel2 = new BGoodsInfoInShopToCModel();
					bGoodsInfoInShopToCModel2.setKindId(list.get(i).getKindId());
					bGoodsInfoInShopToCModel2.setKindName(list.get(i).getKindName());
					datalist.add(bGoodsInfoInShopToCModel2);
					flagId = list.get(i).getKindId();
				} else if (list.get(i).getKindId().equals(defaultlist.get(0).getKindId()) && i != 0) {
					defaultlist.add(list.get(i));
					flagId = list.get(i).getKindId();
				}
			}
			for (int i = 0; i < defaultlist.size(); i++) {
				String[] url = defaultlist.get(i).getLogoUrl().split("\\|");
				defaultlist.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
			}
			map.put("kindList", jsonUtil.parseToNoEmptyStr(datalist));
			map.put("defaultlist", jsonUtil.parseToNoEmptyStr(defaultlist));
		}
		return map;
	}

	@Override
	public List<BGoodsInfoToCModel> findBgoodsListByKindId(Integer shopId, Integer kindId) {
		List<BGoodsInfoToCModel> list = bGoodsDao.queryGoodsListByShopIdAndKindId(shopId, kindId);
		return list;
	}

	@Override
	public List<BShopInfoToCModel> queryAllShop() {
		return bShopInfoDao.queryAllShop();
	}

	@Override
	public BShopInfoModel findShopInfoByUserId(Integer id) {
		return bShopInfoDao.findShopInfoByUserId(id);
	}

	@Override
	public Integer selectEnableByUserId(Integer userId) {
		return bShopInfoDao.selectEnableByUserId(userId);
	}

	@Override
	public BShopInfoToCModel queryShopInfo(Integer shopId) {
		return bShopInfoDao.queryShopInfo(shopId);
	}

	@Override
	public Page queryFavoriteShop(Map<String, Object> map) {
		int page = (int) map.get("page");
		int pageSize = (int) map.get("pageSize");
		// 查询收藏总条数
		int totalCount = bShopInfoDao.queryFavoriteBgoodsCount((int) map.get("userId"));
		// 构造分页对象
		com.f2b2c.eco.model.market.Page p = new com.f2b2c.eco.model.market.Page(totalCount, page,
				Integer.valueOf(pageSize));
		// 查询非水果信息排序的商品list
		List<BShopInfoBean> rows = bShopInfoDao.queryFavoriteShop((int) map.get("userId"), p.getStart(), pageSize);

		/*
		 * // 获取配置文件地址商品图片 for (int i = 0; i < rows.size(); i++) { String url[]
		 * = rows.get(i).getLogoUrl().split("\\|");
		 * rows.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]); }
		 */
		p.setRows(rows);
		return p;
	}
	
	@Override
	public BShopInfoModel queryShopNameByUserId(Integer userId) {
		
		return bShopInfoDao.queryShopNameByUserId(userId);
	}


}
