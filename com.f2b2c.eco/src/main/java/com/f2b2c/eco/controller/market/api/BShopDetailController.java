package com.f2b2c.eco.controller.market.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.service.market.CFavoriteShopService;
import com.f2b2c.eco.util.DistanceUtil;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.PropertiesUtil;

/**
 * @Description
 * @author Josen.yang
 * @date Dec 1, 2016 3:01:03 PM
 */

@Controller
@RequestMapping(value = "/api/bshopdetail")
public class BShopDetailController {

    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(BShopDetailController.class);

    @Autowired
    private BShopInfoService bShopInfoService;

    @Autowired
    private CFavoriteShopService cFavoriteShopService;

    /**
     * 进入店铺首页
     */
    @RequestMapping(value = "/bshopinfo", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> bshopinfo(Integer shopId, Integer userId, HttpServletRequest request) {
        try {
            if (shopId != null) {
                Map<String, Object> map = bShopInfoService.findShopInfoById(userId, shopId);
                return new DataToCResultModel<Object>(200, "请求成功!", map);
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商店信息失败", null);
        }
    }

    /**
     * 根据商品类型查询商品类型
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/bgoodsinfo", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> bgoodsinfo(Integer shopId, Integer kindId, HttpServletRequest request) {
        try {
            if (shopId != null) {
                JsonUtil jsonUtil = new JsonUtil();
                List<BGoodsInfoToCModel> list = bShopInfoService.findBgoodsListByKindId(shopId, kindId);
                // 换图片地址
                String path = PropertiesUtil.getValue("path");
                for (int i = 0; i < list.size(); i++) {
                    String[] url = list.get(i).getLogoUrl().split("\\|");
                    list.get(i).setLogoUrl(path + url[0]);
                }
                return new DataToCResultModel<Object>(200, "请求成功!", jsonUtil.parseToNoEmptyStr(list));
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商店信息失败", null);
        }
    }

    /**
     * 附近商店-高德地图
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/bshop-info-map", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> shopMap(HttpServletRequest request) {
        try {

            // 所有商店
            List<BShopInfoToCModel> shopList = bShopInfoService.queryAllShop();
            // 符合距离要求的List集合
            List<BShopInfoToCModel> shopListTo = new ArrayList<BShopInfoToCModel>();
            // 获得500米之类的商店Id
            Integer shopId = null;
            // 获得坐标
            String locationX = request.getParameter("locationX");
            String locationY = request.getParameter("locationY");

            // 遍历商店 当匹配距离小于1000米 跳出循环
            for (int i = 0; i < shopList.size(); i++) {
                double length = DistanceUtil.distance(Double.valueOf(locationX), Double.valueOf(locationY),
                        Double.valueOf(shopList.get(i).getLocationX()), Double.valueOf(shopList.get(i).getLocationY()));
                if (length - Double.valueOf(1000) <= 0) {
                    shopList.get(i).setLength(length);
                    shopListTo.add(shopList.get(i));
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
            if (shopId != null) {
                BShopInfoToCModel bShopInfoToCModel = bShopInfoService.queryShopInfo(shopId);
                JsonUtil jsonUtil = new JsonUtil();
                return new DataToCResultModel<Object>(200, "请求成功!", jsonUtil.parseToNoEmptyStr(bShopInfoToCModel));
            } else {
                return new DataToCResultModel<Object>(400, "附近没有商店", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商店信息失败", null);
        }
    }

    /**
     * 收藏商店
     */
    @RequestMapping(value = "/favorite-shop-by-userid", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> insertFavoriteGoods(Integer userId, Integer shopId, Integer cancel,
            HttpServletRequest request) {
        try {
            if (userId != null && shopId != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                map.put("shopId", shopId);
                int count;
                if (cancel == 1) {
                    count = cFavoriteShopService.deleteFavoriteShop(map);
                } else {
                    // 收藏商店
                    count = cFavoriteShopService.insertFavoriteShop(map);
                }
                if (count > 0) {
                    if (cancel == 1) {
                        return new DataToCResultModel<Object>(200, "取消商店收藏成功!", null);
                    } else {
                        return new DataToCResultModel<Object>(200, "商店收藏成功!", null);
                    }
                } else {
                    if (cancel == 1) {
                        return new DataToCResultModel<Object>(400, "取消商店收藏失败", null);
                    } else {
                        return new DataToCResultModel<Object>(400, "商店收藏失败", null);
                    }
                }
            } else {
                if (userId == null) {
                    return new DataToCResultModel<Object>(400, "请登录后操作", null);
                }
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "服务器内部错误", null);
        }
    }

    /**
     * 收藏商店列表接口
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-favorite-shop", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryFavoriteShop(Integer userId, Integer page, Integer pageSize,
            HttpServletRequest request) {
        try {
            if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
                // 准备参数
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pageSize", pageSize);
                map.put("page", page);
                map.put("userId", userId);
                Page p = bShopInfoService.queryFavoriteShop(map);
                JsonUtil jsonUtil = new JsonUtil();
                return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(p));
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商品数据失败", null);
        }
    }
}
