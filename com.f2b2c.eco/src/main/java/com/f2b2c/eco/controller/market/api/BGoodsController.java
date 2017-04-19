package com.f2b2c.eco.controller.market.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.BGoodsInfoToCModel;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.market.CComment;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.model.market.PageResultModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.service.market.CCommentService;
import com.f2b2c.eco.service.market.CFavoriteGoodsService;
import com.f2b2c.eco.service.market.FKindService;
import com.f2b2c.eco.util.DistanceUtil;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.LogoUrlUtil;
import com.f2b2c.eco.util.PropertiesUtil;

/**
 * 商品操作
 * 
 * @author jing.liu
 *
 */
@Controller("BGoodsApiController")
@RequestMapping(value = "/api/bgoods")
public class BGoodsController {
    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(BGoodsController.class);

    @Autowired
    private BGoodsService bGoodsService;

    @Autowired
    private FKindService fKindService;

    @Autowired
    private BShopInfoService bShopInfoService;

    @Autowired
    private CCommentService cCommentService;

    @Autowired
    private CFavoriteGoodsService cFavoriteGoodsService;

    private Integer total;

    private List<BGoodsToCModel> resultListTo;

    private List<BShopInfoToCModel> list;

    /**
     * 根据商品名称查询商品(搜索框)
     * 
     * @param name商品名称
     * @param locationX经度
     * @param locationY纬度
     * @param page页数
     * @param pageSize页容量
     * @return DataToCResultModel
     */
    @RequestMapping(value = "/query-bgoods-by-name", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Map<String, Object>> queryBGoodsByName(HttpServletRequest request) {
        String name = request.getParameter("name");
        String locationX = request.getParameter("locationX");
        String locationY = request.getParameter("locationY");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        Map<String, Object> map = new HashMap<String, Object>();
        PageResultModel pageResult = new PageResultModel();
        if (page != null && !page.equals("") && Integer.valueOf(page) == 1) {
            resultListTo = new ArrayList<BGoodsToCModel>();
            total = 0;
            List<BGoodsToCModel> resultList = new ArrayList<BGoodsToCModel>();
            // 查询所有店铺
            List<BShopInfoToCModel> shopList = bShopInfoService.queryAllShop();
            List<BShopInfoToCModel> shopListTo = new ArrayList<BShopInfoToCModel>();
            List<BShopInfoToCModel> shop = new ArrayList<BShopInfoToCModel>();
            // List<BGoodsToCModel> list =
            // bGoodsService.queryBGoodsByName(name);
            for (int i = 0; i < shopList.size(); i++) {
                double length = DistanceUtil.distance(Double.valueOf(locationX), Double.valueOf(locationY),
                        Double.valueOf(shopList.get(i).getLocationX()), Double.valueOf(shopList.get(i).getLocationY()));
                if (length - Double.valueOf(1000) <= 0) {
                    shopList.get(i).setLength(length);
                    shopListTo.add(shopList.get(i));
                }
            }
            // 根据算好的距离排序
            Collections.sort(shopListTo, new Comparator<BShopInfoToCModel>() {
                public int compare(BShopInfoToCModel o1, BShopInfoToCModel o2) {
                    return new Double(o1.getLength()).compareTo(new Double(o2.getLength()));
                }
            });
            if (shopListTo.get(0).getShopName().toLowerCase().indexOf(name.toLowerCase()) > -1) {
                shop.add(shopListTo.get(0));
            }
            list = shop;
            resultList = bGoodsService.queryGoodsByShopId(name, shopListTo.get(0).getId());
            // 在去搜索和搜索文本相关的商品
            List<BGoodsToCModel> list = bGoodsService.queryBGoodsByName(name);
            resultList.addAll(list);
            if (resultList != null && resultList.size() > 0) {
                for (BGoodsToCModel btc : resultList) {
                    String[] logoUrl = btc.getLogoUrl().split("\\|");
                    btc.setLogoUrl(PropertiesUtil.getValue("path") + logoUrl[0]);
                }
                resultListTo = resultList;
                total = resultList.size();
                Page p = new Page(resultList.size(), Integer.valueOf(page), Integer.valueOf(pageSize));
                if (resultList.size() >= p.getPageSize()) {
                    pageResult.setRows(resultList.subList(p.getStart(), p.getPageSize()));
                } else {
                    pageResult.setRows(resultList.subList(p.getStart(), resultList.size()));
                }
                pageResult.setTotalCount(resultList.size());
            } else {
                pageResult.setRows(resultList);
                pageResult.setTotalCount(resultList.size());
            }
        } else {
            Page p = new Page(total, Integer.valueOf(page), Integer.valueOf(pageSize));
            if (resultListTo.size() >= p.getPageSize() * Integer.valueOf(page)) {
                pageResult.setRows(resultListTo.subList(p.getStart(), p.getPageSize() * Integer.valueOf(page)));
            } else {
                pageResult.setRows(resultListTo.subList(p.getStart(), resultListTo.size()));
            }
            pageResult.setTotalCount(total);
        }
        map.put("list", list);
        map.put("pageResult", pageResult);
        return new DataToCResultModel<Map<String, Object>>(200, "success", map);
    }

    /**
     * 查询所有商品
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-all-bgoods", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryAllBGoods(Integer page, Integer pageSize, HttpServletRequest request) {
        try {
            if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
                // 查询总记录条数
                int totalCount = bGoodsService.findAllBGoodsCount();
                // 分页对象构造
                Page p = new Page(totalCount, page, pageSize);
                List<BGoodsToCModel> rows = bGoodsService.queryAllBGoods(p.getStart(), pageSize);
                JsonUtil jsonUtil = new JsonUtil();
                p.setRows(rows);
                return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(p));
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商品数据失败", null);
        }
    }

    /**
     * 根据商品分类id查询商品列表(分页)
     * 
     * @param pageable
     *            //分页对象
     * @param kindId
     *            //类型Id
     * @return dataToCResult
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-bgoods-info-by-kindid", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryBGoodsByKindId(Integer kindId, Integer page, Integer pageSize,
            HttpServletRequest request) {
        try {
            if (page != null && pageSize != null) {
                JsonUtil jsonUtil = new JsonUtil();
                // 查询类型下所有的类型
                List<String> list = fKindService.findChdKind(kindId);
                list.add(String.valueOf(kindId));

                // 判断是否是水果
                String catalog = fKindService.findcatalogById(kindId);
                boolean isFruit = false;
                if (catalog == null) {
                    return new DataToCResultModel<Object>(400, "商品分类不存在", null);
                }
                if (Integer.valueOf(catalog) == 0) {
                    isFruit = true;
                }
                if (isFruit) {
                    // 所有商店
                    List<BShopInfoToCModel> shoplist = bShopInfoService.queryAllShop();
                    // 获得500米之类的商店Id
                    Integer shopId = null;
                    // 符合距离要求的List集合
                    List<BShopInfoToCModel> shopListTo = new ArrayList<BShopInfoToCModel>();
                    // 获得坐标
                    String locationX = request.getParameter("locationX");
                    String locationY = request.getParameter("locationY");

                    // 遍历商店 当匹配距离小于1000米 跳出循环
                    for (int i = 0; i < shoplist.size(); i++) {
                        double length = DistanceUtil.distance(Double.valueOf(locationX), Double.valueOf(locationY),
                                Double.valueOf(shoplist.get(i).getLocationX()),
                                Double.valueOf(shoplist.get(i).getLocationY()));
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
                        int totalCount = bGoodsService.findGoodsByKindListCount(list, shopId);
                        Page p = new Page(totalCount, page, Integer.valueOf(pageSize));
                        List<BGoodsToCModel> rows = bGoodsService.findGoodsByKindList(list, shopId, p.getStart(),
                                pageSize);
                        for (int i = 0; i < rows.size(); i++) {
                            String url[] = rows.get(i).getLogoUrl().split("\\|");
                            rows.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
                        }
                        p.setRows(rows);
                        p.setParams(null);
                        return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(p));
                    } else {
                        return new DataToCResultModel<Object>(400, "附近没有商店", null);
                    }
                } else {
                    // 查询总记录条数
                    int totalCount = bGoodsService.findAllBGoodsByKindIdCount(list, kindId);
                    if (totalCount > 0) {
                        Page p = new Page(totalCount, page, Integer.valueOf(pageSize));
                        // 根据商品类型,起始数字,每页尺寸
                        List<BGoodsToCModel> rows = bGoodsService.queryBGoodsByKindId(list, kindId, p.getStart(),
                                pageSize);
                        for (int i = 0; i < rows.size(); i++) {
                            if (StringUtils.isNotBlank(rows.get(i).getLogoUrl())) {
                                String url[] = rows.get(i).getLogoUrl().split("\\|");
                                rows.get(i).setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
                            }
                        }
                        p.setRows(rows);
                        p.setParams(null);
                        return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(p));
                    } else {
                        return new DataToCResultModel<Object>(400, "该类型没有商品", null);
                    }
                }
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商品数据失败", null);
        }
    }

    /**
     * @Description 根据商品id查询商品介绍
     * @author Josen.yang
     * @param id
     * @param request
     * @return
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-bgoods-info-byid", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryBGoodsInfoById(Integer id, HttpServletRequest request) {
        String userId = request.getParameter("userId");
        try {
            if (id != null) {
                // 查询商品对象
                BGoodsInfoToCModel bGoodsInfoToCModel = bGoodsService.queryBGoodsInfoById(id,
                        userId == null || userId.equals("") ? null : Integer.valueOf(userId));
                String url = LogoUrlUtil.splitImgUrl(bGoodsInfoToCModel.getLogoUrl());
                bGoodsInfoToCModel.setLogoUrl(url);
                // 判断是否已经被收藏
                if (StringUtils.isNotBlank(userId)) {
                    int count = bGoodsService.isFavorite(Integer.valueOf(userId), bGoodsInfoToCModel.getGoodsNo());
                    if (count > 0) {
                        bGoodsInfoToCModel.setIsFavorite(1);
                    } else {
                        bGoodsInfoToCModel.setIsFavorite(0);
                    }
                } else {
                    bGoodsInfoToCModel.setIsFavorite(0);
                }
                JsonUtil jsonUtil = new JsonUtil();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("goodsModel", jsonUtil.parseToNoEmptyStr(bGoodsInfoToCModel));
                return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(bGoodsInfoToCModel));
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商品数据失败", null);
        }
    }

    /**
     * 
     * @Description 商品详情页面下方的商品具体详情接口
     * @author Josen.yang
     * @param 商品id
     * @return 适配手机视图html
     */
    @RequestMapping(value = "/query-bgoods-parameter-byid", method = RequestMethod.GET)
    public final String queryParameterById(Integer id, HttpServletRequest request, HttpServletResponse response) {
        BGoodsModel bGoodsModel = bGoodsService.findBgoodsById(Integer.valueOf(id));
        request.setAttribute("model", bGoodsModel);
        return "market/bGoodsParameter";
    }

    /**
     * @Description 商品详情页面下方的商品规格接口
     * @author Josen.yang
     * @param 商品id
     * @return 适配手机视图html
     */
    @RequestMapping(value = "/query-bgoods-details-byid", method = RequestMethod.GET)
    public final String queryDetailsById(Integer id, HttpServletRequest request, HttpServletResponse response) {
        BGoodsModel bGoodsModel = bGoodsService.findBgoodsById(Integer.valueOf(id));
        request.setAttribute("model", bGoodsModel);
        return "market/bGoodsDetails";
    }

    /**
     * 
     * @Description 热销商品接口
     * @author Josen.yang
     * @param page页数
     * @param pageSize页容量
     * @return
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-hot-bgoods", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryHotBGoodsByKindId(Integer page, Integer pageSize,
            HttpServletRequest request) {
        try {
            if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
                // 获得坐标
                String locationX = request.getParameter("locationX");
                String locationY = request.getParameter("locationY");
                // 准备参数到service
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pageSize", pageSize);
                map.put("page", page);
                // 业务逻辑层返回Page对象
                Page p = bGoodsService.queryHotBGoodsByKindId(map, Double.valueOf(locationX),
                        Double.valueOf(locationY));
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

    /**
     * 
     * @Description 根据商品编号查询商品评价列表(分页)
     * @author Josen.yang
     * @param goodsNo
     *            商品编号
     * @param page
     *            页数
     * @param pageSize
     *            页容量
     * @return 分页对象
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-goods-comment-by-goodsno", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryGoodsCommentByGoodsNo(String goodsNo, Integer page, Integer pageSize,
            HttpServletRequest request) {
        try {
            if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
                // 查询总记录条数
                int totalCount = cCommentService.selectCountByGoodsNo(goodsNo);
                // 分页对象构造
                Page p = new Page(totalCount, page, pageSize);
                // 准备查询参数
                Map<String, Object> map = new HashMap<>();
                map.put("goodsNo", goodsNo);
                map.put("start", p.getStart());
                map.put("length", pageSize);
                List<CComment> rows = cCommentService.selectByGoodsNo(map);
                // 给用户头像拼接文件服务器地址
                for (int i = 0; i < rows.size(); i++) {
                    if (StringUtils.isNotBlank(rows.get(i).getUserPic())) {
                        String url = rows.get(i).getUserPic();
                        rows.get(i).setUserPic(PropertiesUtil.getValue("path") + url);
                    }
                    // 格式化创建时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String date = sdf.format(rows.get(i).getCreateTime());
                    rows.get(i).setCreateTimeStr(date);
                }
                JsonUtil jsonUtil = new JsonUtil();
                p.setRows(rows);
                return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(p));
            } else {
                return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "获取商品评价失败", null);
        }
    }

    /**
     * @Description 添加商品评价
     * @author Josen.yang
     * @param userId
     *            用户id
     * @param content
     *            评价内容
     * @param syntheticalScore
     *            综合分数
     * @param qualityScore
     *            质量得分
     * @param speedScore
     *            速度分数
     * @param serviceScore
     *            服务分数
     * @param goodsId
     *            商品id
     * @param orderDetailsId
     *            订单详情id
     */
    @RequestMapping(value = "/insert-goods-comment", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> insertGoodsComment(Integer userId, Integer syntheticalScore,
            Integer qualityScore, Integer speedScore, Integer serviceScore, Integer goodsId, String orderDetailsId,
            HttpServletRequest request) {
        try {
            String content = request.getParameter("content");
            if (userId != null && StringUtils.isNotBlank(content) && syntheticalScore != null
                    && qualityScore != null
                    && speedScore != null && serviceScore != null && goodsId != null
                    && StringUtils.isNotBlank(orderDetailsId)) {
                content = content.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
                // 根据商品Id查询商品编号
                BGoodsModel bGoodsModel = bGoodsService.findBgoodsInfoById(goodsId);
                // 准备评价模型
                CComment model = new CComment();
                model.setGoodsNo(bGoodsModel.getGoodsNo());
                model.setUserId(userId);
                model.setContent(content);
                model.setSyntheticalScore(syntheticalScore);
                model.setQualityScore(qualityScore);
                model.setServiceScore(serviceScore);
                model.setSpeedScore(speedScore);
                model.setOrderDetailsId(orderDetailsId);
                model.setCreateTime(new Date());
                // count 为更新订单详情(已评价)+添加评价 默认必须是2 小于2则失败
                int count = cCommentService.insert(model, orderDetailsId);
                if (count > 1) {
                    return new DataToCResultModel<Object>(200, "评价成功!", null);
                } else {
                    return new DataToCResultModel<Object>(400, "评价商品失败", null);
                }
            } else {
                return new DataToCResultModel<Object>(400, "商品订单异常不可以添加评价", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400, "添加商品评价失败", null);
        }
    }

    /**
     * 
     * @Description 收藏/取消收藏
     * @author Josen.yang
     * @param userId
     *            用户Id
     * @param goodsNo
     *            商品编号
     * @param cancel
     *            是否取消收藏
     */
    @RequestMapping(value = "/favorite-goods-by-userid", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> insertFavoriteGoods(Integer userId, String goodsNo, Integer cancel,
            HttpServletRequest request) {
        try {
            if (userId != null && StringUtils.isNotBlank(goodsNo) && cancel != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                map.put("goodsNo", goodsNo);
                int count;
                if (cancel == 1) {
                    // 如果取消标志为1 取消收藏
                    count = cFavoriteGoodsService.deleteFavoriteGoods(map);
                } else {
                    // 如果取消标志为0 收藏
                    count = cFavoriteGoodsService.insertFavoriteGoods(map);
                }
                if (count > 0) {
                    if (cancel == 1) {
                        return new DataToCResultModel<Object>(200, "取消收藏成功!", null);
                    } else {
                        return new DataToCResultModel<Object>(200, "收藏成功!", null);
                    }
                } else {
                    if (cancel == 1) {
                        return new DataToCResultModel<Object>(400, "取消收藏商品失败", null);
                    } else {
                        return new DataToCResultModel<Object>(400, "收藏商品失败", null);
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
     * 
     * @Description 收藏商品列表接口
     * @author Josen.yang
     * @param userId
     *            用户Id
     * @param page
     *            页数
     * @param pageSize
     *            页容量
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/query-favorite-bgoods", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> queryFavoriteBgoods(Integer userId, Integer page, Integer pageSize,
            HttpServletRequest request) {
        try {
            if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
                // 准备参数
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pageSize", pageSize);
                map.put("page", page);
                map.put("userId", userId);
                Page p = bGoodsService.queryFavoriteBgoods(map);
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