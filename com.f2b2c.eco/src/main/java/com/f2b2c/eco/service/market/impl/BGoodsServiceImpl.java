package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BGoodsDao;
import com.f2b2c.eco.dao.market.BShopInfoDao;
import com.f2b2c.eco.dao.market.BUserDao;
import com.f2b2c.eco.dao.market.UserBrowerRecordDao;
import com.f2b2c.eco.dao.platform.BKindDao;
import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.UserBrowerRecordModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.status.BGoodsStatusEnum;
import com.f2b2c.eco.util.DistanceUtil;
import com.f2b2c.eco.util.PropertiesUtil;

@Service
public class BGoodsServiceImpl implements BGoodsService {

	@Autowired
	private BGoodsDao bGoodsDao;

	@Autowired
	private BKindDao bKindDao;
	
	@Autowired
	private UserBrowerRecordDao userBrowerRecordDao;

	@Autowired
	private BShopInfoDao bShopInfoDao;
	
	@Autowired
	private BUserDao bUserDao;

	@Override
	public List<BGoodsToCModel> queryBGoodsByName(String name) {

		return bGoodsDao.queryBGoodsByName(name);
	}

	@Override
	public List<BGoodsToCModel> queryAllBGoods(Integer start, Integer length) {
		return bGoodsDao.queryAllBGoods(start, length);
	}

	@Override
	public List<BGoodsToCModel> queryBGoodsByKindId(List<String> list, Integer kindId, Integer start, Integer length) {
		return bGoodsDao.queryBGoodsByKindId(list, kindId, start, length);
	}

	@Override
	public int findAllBGoodsCount() {
		return bGoodsDao.findAllBGoodsCount();
	}

	@Override
	public int findAllBGoodsByKindIdCount(List<String> list, Integer kindId) {
		return bGoodsDao.findAllBGoodsByKindIdCount(list, kindId);
	}

	@Override
	public int findGoodsByKindListCount(List<String> list, Integer shopId) {
		return bGoodsDao.findGoodsByKindListCount(list, shopId);
	}

	@Override
	public int queryBGoodsByNameCount(String name) {

		return bGoodsDao.queryBGoodsByNameCount(name);
	}

	@Override
	public BGoodsInfoToCModel queryBGoodsInfoById(Integer id,Integer userId) {
		if(userId != null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
            // 查询所有的浏览记录
			int totalCount = userBrowerRecordDao.queryBrowerRecordCountByUserId(map);
			if(totalCount > 100){
				map.put("totalCount", totalCount - 100);
                userBrowerRecordDao.deleteBrowerRecord(map); // 如果浏览记录超过100条，删除100条以后的记录
			}
            int result = userBrowerRecordDao.queryGoodsIsExists(id); // 插叙当前商品是否已经浏览过
			UserBrowerRecordModel userBrowerRecord = new UserBrowerRecordModel();
			if(result > 0){
				userBrowerRecord.setUpdatedTime(new Date());
				userBrowerRecord.setGoodsId(id);
                userBrowerRecordDao.updateBrowerRecord(userBrowerRecord); // 如果当前商品浏览过，修改浏览时间
			}else{
				userBrowerRecord.setGoodsId(id);
				userBrowerRecord.setUserId(userId);
				userBrowerRecord.setCreatedUser(userId);
				userBrowerRecord.setCreatedTime(new Date());
				userBrowerRecord.setUpdatedUser(userId);
				userBrowerRecord.setUpdatedTime(new Date());
                userBrowerRecordDao.insertBrowerRecord(userBrowerRecord); // 如果当前商品没有浏览过，保存到数据库
			}
		}
		return bGoodsDao.queryBGoodsInfoById(id);
	}

	@Override
	public Page<BGoodsModel> findWithPagination(Pageable pageable, Map<String, Object> paramMap) {
		int total = bGoodsDao.countWithMap(paramMap);
		paramMap.put("pageable", pageable);
		List<BGoodsModel> lists = bGoodsDao.findWithPagination(paramMap);
		Page<BGoodsModel> pages = new Pagination<>(lists, pageable, total);
		return pages;

	}

	@Override
	public int updateReply(BGoodsModel bGoodsModel) {

		BGoodsModel goodsModel = new BGoodsModel();
		goodsModel.setId(bGoodsModel.getId());
		goodsModel.setReason(bGoodsModel.getReason());
        goodsModel.setStatus(Integer.parseInt(BGoodsStatusEnum.NO_PASS.toString()));// 将商品状态设置为不通过
		return bGoodsDao.updateStatus(goodsModel);

	}

	@Override
	public int updateStatus(String id, String status) {

		BGoodsModel goodsModel = new BGoodsModel();
		goodsModel.setId(Integer.parseInt(id));
		goodsModel.setStatus(Integer.parseInt(status));
		return bGoodsDao.updateStatus(goodsModel);
	}

	@Override
	public List<BGoodsToCModel> findGoodsByKindList(List<String> idlist, Integer shopId, int i, int j) {
		return bGoodsDao.findGoodsByKindList(idlist, shopId, i, j);
	}

	@Override
	public int insertBGoods(BGoodsModel bGoodsModel) {
		return bGoodsDao.insertBGoods(bGoodsModel);
	}

	@Override
	public Page<BGoodsModel> findBgoodsByShopId(Pageable pageable, Map<String, Object> paramMap) {
		paramMap.put("start", pageable.getPageNumber());
		paramMap.put("offset", pageable.getPageSize());
		List<Integer> kindIds = new ArrayList<Integer>();
		if (paramMap.get("kind") != null && !paramMap.get("kind").equals("")) {
			kindIds.add(Integer.valueOf((String) paramMap.get("kind")));
			List<BKindToCModel> kinds = bKindDao
					.queryAllKindsForTreeByParentId(Integer.valueOf((String) paramMap.get("kind")));
			getKindIds(kinds, kindIds);
			paramMap.put("kindIds", kindIds);
		}
		int total = bGoodsDao.findBgoodsByShopIdCount(paramMap);
		List<BGoodsModel> lists = bGoodsDao.findBgoodsByShopId(paramMap);
		String path = PropertiesUtil.getValue("path");
		for (int i = 0; i < lists.size(); i++) {
			if (StringUtils.isNotBlank(lists.get(i).getLogoUrl())) {
				String[] logUrl = lists.get(i).getLogoUrl().split("\\|");
				lists.get(i).setLogoUrl(path + logUrl[0]);
			}
		}
		Page<BGoodsModel> pages = new Pagination<>(lists, pageable, total);
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
	public BGoodsModel findBgoodsById(Integer goodsId) {
		return bGoodsDao.findBgoodsById(goodsId);
	}

	@Override
	public int modifybgoods(BGoodsModel bGoodsModel, BUserModel user) {
        // 查询旧的参数
		BGoodsModel oldBgoodsModel = bGoodsDao.findBgoodsById(Integer.valueOf(bGoodsModel.getId()));
		Integer version = oldBgoodsModel.getVersion() + 1;
		bGoodsDao.downBgoodsAndIsCopy(bGoodsModel.getId());
        // 拷贝一份旧的数据
		bGoodsDao.insertBGoods(oldBgoodsModel);
        // 拷贝数据的Id
		Integer id = oldBgoodsModel.getId();
		String goodsNo = oldBgoodsModel.getGoodsNo();
        // 升级版本
		bGoodsModel.setVersion(version);
		bGoodsModel.setGoodsNo(goodsNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bGoodsModel", bGoodsModel);
		map.put("id", id);
		int size = bGoodsDao.modifybgoods(map);
		return size;
	}

	@Override
	public int nextshelfbgoods(Integer id) {
		int size = bGoodsDao.nextshelfbgoods(id);
		return size;
	}

	@Override
	public List<BGoodsToCModel> queryGoodsByShopId(String name, Integer shopId) {

		return bGoodsDao.queryGoodsByShopId(name, shopId);
	}

	/**
     * 根据店铺id获取商品数量
     */
	@Override
	public Integer selectGoodsCountByShopId(Integer id) {
		return bGoodsDao.selectGoodsCountByShopId(id);
	}
	
	/**
     * 热销商品list
     */
	@Override
	public com.f2b2c.eco.model.market.Page queryHotBGoodsByKindId(Map<String, Object> map, Double locationX, Double locationY) {
		int page = (int) map.get("page");
		int pageSize = (int) map.get("pageSize");
        // 所有商店
        List<BShopInfoToCModel> shoplist = bShopInfoDao.queryAllShop();
        // 符合距离要求的List集合
		List<BShopInfoToCModel> shopListTo = new ArrayList<BShopInfoToCModel>();
        // 获得500米之类的商店Id
        Integer shopId = null;

        // 遍历商店 当匹配距离小于1000米 跳出循环
		for (int i = 0; i < shoplist.size(); i++) {
			double length = DistanceUtil.distance(Double.valueOf(locationX), Double.valueOf(locationY),
					Double.valueOf(shoplist.get(i).getLocationX()), Double.valueOf(shoplist.get(i).getLocationY()));
			if (length - Double.valueOf(1000) <= 0) {
				shoplist.get(i).setLength(length);
				shopListTo.add(shoplist.get(i));
			}
		}
        // 根据算好的距离排序
		if (shopListTo != null && shopListTo.size() > 1) {
			Collections.sort(shopListTo, new Comparator<BShopInfoToCModel>() {
				public int compare(BShopInfoToCModel o1, BShopInfoToCModel o2) {
					return new Double(o1.getLength()).compareTo(new Double(o2.getLength()));
				}
			});
		}
        // 取得第一个商店的id
		if (shopListTo != null && shopListTo.size() > 0) {
			shopId = shopListTo.get(0).getId();
		}
        // 如果附近有商店的话 查询所有水果 否则 返回空
		if (shopId != null) {
            // 查询总记录条数
			int totalCount = bGoodsDao.findHotGoodsByShopIdCount(shopId);

            // 构造分页对象
			com.f2b2c.eco.model.market.Page p = new com.f2b2c.eco.model.market.Page(totalCount, page,
					Integer.valueOf(pageSize));

            // 查询附近商店水果信息和所有其他非水果信息排序的商品list
			List<BGoodsToCModel> rows = bGoodsDao.findHotGoodsByShopId(shopId, p.getStart(), pageSize);

            // 获取配置文件地址商品图片
			for (int i = 0; i < rows.size(); i++) {
				String url[] = rows.get(i).getLogoUrl().split("\\|");
				rows.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
			}
			p.setRows(rows);
			p.setParams(null);
			return p;
		} else {
            // 查询非水果热销总记录条数
			int totalCount = bGoodsDao.findHotNotFruitCount();
            // 构造分页对象
			com.f2b2c.eco.model.market.Page p = new com.f2b2c.eco.model.market.Page(totalCount, page,
					Integer.valueOf(pageSize));

            // 查询非水果信息排序的商品list
			List<BGoodsToCModel> rows = bGoodsDao.findHotNotFruit(p.getStart(), pageSize);

            // 获取配置文件地址商品图片
			for (int i = 0; i < rows.size(); i++) {
				String url[] = rows.get(i).getLogoUrl().split("\\|");
				rows.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
			}
			p.setRows(rows);
			p.setParams(null);
			return p;
		}
	}

	@Override
	public List<BGoodsModel> queryAllGoodsByShopId(Map<String,Object> map) {
		
		return bGoodsDao.queryAllGoodsByShopId(map);
	}

	@Override
	public int queryAllGoodsCountByShopId(Map<String,Object> map){
		
		return bGoodsDao.queryAllGoodsCountByShopId(map);
	}

	@Override
	public List<BGoodsModel> queryAllGoods(Map<String, Object> map) {
		
		return bGoodsDao.queryAllGoods(map);
	}

	@Override
	public int queryAllGoodsCount(Integer KindId) {
		
		return bGoodsDao.queryAllGoodsCount(KindId);
	}

	@Override
	public BGoodsModel bgoogsNoIsTrue(String goodsNo) {
		return bGoodsDao.bgoogsNoIsTrue(goodsNo);
	}

	@Override
	public com.f2b2c.eco.model.market.Page queryFavoriteBgoods(Map<String, Object> map) {
		int page = (int) map.get("page");
		int pageSize = (int) map.get("pageSize");
        // 查询非水果热销总记录条数
		int totalCount = bGoodsDao.queryFavoriteBgoodsCount((int) map.get("userId"));
        // 构造分页对象
			com.f2b2c.eco.model.market.Page p = new com.f2b2c.eco.model.market.Page(totalCount, page,
					Integer.valueOf(pageSize));
        // 查询非水果信息排序的商品list
		List<BGoodsToCModel> rows = bGoodsDao.queryFavoriteBgoods((int) map.get("userId"), p.getStart(), pageSize);

        // 获取配置文件地址商品图片
			for (int i = 0; i < rows.size(); i++) {
				String url[] = rows.get(i).getLogoUrl().split("\\|");
				rows.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
			}
			p.setRows(rows);
			p.setParams(null);
			return p;
	}

	@Override
	public int isFavorite(Integer userId, String goodsNo) {
		return bGoodsDao.isFavorite(userId, goodsNo);
	}

	/**
     * 根据店铺id下架所有商品
     * 
     * @param shopId:店铺id
     * @return 返回下架操作结果状态
     */
	@Override
	public int updateGoodsByShopId(Integer shopId,Integer userId) {
		int size = 0;
		size = bGoodsDao.updateGoodsByShopId(shopId);
		if(size ==0){
			return size;
		}
		size = bShopInfoDao.delete(shopId);
		if(size==0){
			return size;
		}
	    bUserDao.deleteUserById(userId);
		return size;
	}

    @Override
    public BGoodsModel findBgoodsInfoById(Integer goodsId) {
        return bGoodsDao.findBgoodsInfoById(goodsId);
    }

    @Override
    public int modifybgoodsRemain(BGoodsModel bGoodsModel, BUserModel user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", bGoodsModel.getId());
        map.put("remain", bGoodsModel.getRemain());
        map.put("userId", user.getId());
        int size = bGoodsDao.updateRemain(map);
        return size;
    }
}