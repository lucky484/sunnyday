package com.f2b2c.eco.controller.platform.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.bean.FkindBean;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FGoodsToBModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.service.platform.FGoodsService;
import com.f2b2c.eco.service.platform.FKindService;
import com.f2b2c.eco.service.platform.FSliderWapService;
import com.f2b2c.eco.util.LogoUrlUtil;
import com.f2b2c.eco.util.PageUtil;

import jodd.util.StringUtil;

/**
 * F2B首页
 * 
 * @author jane.hui
 *
 */
@Controller(value="fIndexController")
@RequestMapping(value="/api/index")
public class FIndexController {

     /**
     * 日志记录器
     */
     private Logger logger=LoggerFactory.getLogger(FIndexController.class);
     @Autowired
     private FKindService fKindService;
     
     @Autowired
     private FGoodsService fGoodsService;
     
     @Autowired
     private FSliderWapService fSliderWapService;
     
	/**
     * 获取首页水果分类商品
     * 
     * @param request
     * @return
     */
     @RequestMapping(value = "/get-index", method = RequestMethod.GET)
     @ResponseBody
     public DataToCResultModel<Map<String, Object>> index(HttpServletRequest request) {
          DataToCResultModel<Map<String, Object>> result = new DataToCResultModel<Map<String, Object>>();
          Map<String, Object> map = new HashMap<String, Object>();
          try {
               List<FSliderWapsModel> sliderWapList = fSliderWapService.queryAllSliderWap();
               List<FSliderWapsModel> newSliderList = new ArrayList<FSliderWapsModel>();
                
               for (FSliderWapsModel fSliderWapsModel : sliderWapList) {
                    fSliderWapsModel.setWapImgUrl(LogoUrlUtil.splitOneImgUrl(fSliderWapsModel.getWapImgUrl()));
                    newSliderList.add(fSliderWapsModel);
               }
               List<FKindModel> list = fKindService.selectParentKindByFruit();
               if(list.size()>8){
                   list.subList(0, 8);
               }
               List<FkindBean> kindList = new ArrayList<FkindBean>();
               FkindBean fkindBean = null;
            // 将list转换为kindList
               for (FKindModel fKindModel : list) {
                    fkindBean = new FkindBean();
                    fkindBean.setId(fKindModel.getId());
                    fkindBean.setIconUrl(LogoUrlUtil.splitOneImgUrl(fKindModel.getIconUrl()));
                    fkindBean.setKindName(fKindModel.getKindName());
                    fkindBean.setWeight(fKindModel.getWeight());
                    kindList.add(fkindBean);
               }
               map.put("kindList", kindList);
               map.put("sliderWapList", newSliderList);
               result.setData(map);
            result.setMsg("获取数据成功");
               result.setStatus(200);
               return result;
         } catch (Exception e){
              logger.error(e.getMessage());
            result.setMsg("获取数据失败");
              result.setStatus(400);
               return result;
         }

     }
     
     /**
     * 商品搜索
     * 
     * @param request
     * @return
     */
     @RequestMapping(value = "/search", method = RequestMethod.POST)
     @ResponseBody
     public DataToCResultModel<Map<String, Object>> search(HttpServletRequest request) {
          try {
                request.setCharacterEncoding("utf-8");
                String name = request.getParameter("name");
                String page = request.getParameter("page");
                String pageSize = request.getParameter("pageSize");
                String userId = request.getParameter("userId");
                String cityName = request.getParameter("cityName");
            // 存放搜索后的商品图片转换
               List<FGoodsToBModel> convertList = new ArrayList<FGoodsToBModel>();
            // 起始页
               Integer start = 0;
            // 长度
               Integer length = 0;
            // 总条数
               Integer total = 0;
              if(StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)){
                   Map<String,Object> map = new HashMap<String,Object>();
                   Integer intPage = Integer.valueOf(page);
                   Integer intPageSize = Integer.valueOf(pageSize);
                   start = PageUtil.getStart(intPage, intPageSize);
                   length = intPageSize;
                   total = fGoodsService.queryCountFGoodsByName(userId,cityName,name);
                   map.put("totalCount", total);
                   List<FGoodsToBModel> list = fGoodsService.queryFGoodsByName(userId,cityName,name, start, length);
                   String logoUrl = "";
                // 将搜索后的商品图片转换后添加到新的list
                   for (FGoodsToBModel fGoodsToBModel : list) {
                        logoUrl = fGoodsToBModel.getLogoUrl();
                        fGoodsToBModel.setLogoUrl(LogoUrlUtil.splitOneImgUrl(logoUrl));
                        convertList.add(fGoodsToBModel);
                   }
                   map.put("goodsList", convertList);
                return new DataToCResultModel<Map<String, Object>>(200, "搜索成功", map);
              } else {
                return new DataToCResultModel<Map<String, Object>>(400, "搜索失败", null);
              }
          } catch (Exception e) {
               logger.error(e.getMessage());
            return new DataToCResultModel<Map<String, Object>>(400, "搜索失败", null);
          }
     }
     
     /**
     * 商品列表
     * 
     * @param request
     * @return
     */
     @RequestMapping(value = "/list", method = RequestMethod.POST)
     @ResponseBody
     public DataToCResultModel<Map<String, Object>> list(HttpServletRequest request) {
          String userId = request.getParameter("userId");
          String kindId = request.getParameter("kindId");
          String page = request.getParameter("page");
          String pageSize = request.getParameter("pageSize");
          String cityName = request.getParameter("cityName");
          try {
            // 存放搜索后的商品图片转换
                List<FGoodsToBModel> convertList = new ArrayList<FGoodsToBModel>();
            // 起始页
                Integer start = 0;
            // 长度
                Integer length = 0;
            // 总条数
                Integer total = 0;
               if(StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)&&StringUtil.isNotEmpty(kindId)){
                    Map<String,Object> map = new HashMap<String,Object>();
                    Integer intPage = Integer.valueOf(page);
                    Integer intPageSize = Integer.valueOf(pageSize);
                    start = PageUtil.getStart(intPage, intPageSize);
                    length = intPageSize;
                    total = fGoodsService.queryFGoodCountByKindList(cityName,userId,Integer.valueOf(kindId),null);
                    map.put("totalCount", total);
                    List<FGoodsToBModel> list = fGoodsService.queryFGoodsByKindId(cityName,userId,Integer.valueOf(kindId), start, length,null);
                    String logoUrl = "";
                // 将搜索后的商品图片转换后添加到新的list
                    for (FGoodsToBModel fGoodsToBModel : list) {
                         logoUrl = fGoodsToBModel.getLogoUrl();
                         fGoodsToBModel.setLogoUrl(LogoUrlUtil.splitOneImgUrl(logoUrl));
                         convertList.add(fGoodsToBModel);
                     }
                    map.put("goodsList", convertList);
                    List<FSliderWapsModel> sliderWapList = fSliderWapService.queryAllSliderWap();
                    for (FSliderWapsModel fSliderWapsModel : sliderWapList) {
                         fSliderWapsModel.setWapImgUrl(LogoUrlUtil.splitOneImgUrl(fSliderWapsModel.getWapImgUrl()));
                    }
                    map.put("sliderWapList", sliderWapList);
                return new DataToCResultModel<Map<String, Object>>(200, "搜索成功", map);
               } else {
                return new DataToCResultModel<Map<String, Object>>(400, "搜索失败", null);
               }
          } catch (Exception e) {
               logger.error(e.getMessage());
            return new DataToCResultModel<Map<String, Object>>(400, "搜索失败", null);
          }
     }
     
     /**
     * 根据商品id获取商品详情
     * 
     * @param request
     * @return
     */
     @RequestMapping(value = "/goods-detail", method = RequestMethod.GET)
     @ResponseBody
     public DataToCResultModel<FGoodsToBModel> goodsDetail(HttpServletRequest request) {
          DataToCResultModel<FGoodsToBModel> result = new DataToCResultModel<FGoodsToBModel>();
          String id = request.getParameter("id");
          if(StringUtil.isNotEmpty(id)){
               try {
                    if (StringUtils.isNotBlank(id)) {
                    // 获取商品详情
                         FGoodsToBModel goods = fGoodsService.queryFGoodsDetailById(Integer.valueOf(id));
                         if(goods!=null){
                        // 设置封面图
                              goods.setLogoUrl(LogoUrlUtil.splitImgUrl(goods.getLogoUrl()));
                              result.setData(goods);
                         } else {
                              result.setData(new FGoodsToBModel());
                         }
                         result.setStatus(200);
                         result.setMsg("success");

                    } else {
                         result.setStatus(400);
                    result.setMsg("请求参数不能为空");
                         return result;
                    }
               } catch (Exception e) {
                    logger.error(e.getMessage());
                    result.setStatus(400);
                result.setMsg("获取商品数据失败");
                    return result;
               }
          }
          return result;
     }
     
     /**
     * 改版后的新首页
     * 
     * @param request
     * @return
     */
     @RequestMapping(value = "/fIndex", method = RequestMethod.POST)
     @ResponseBody
     public DataToCResultModel<Map<String, Object>> newIndex(HttpServletRequest request) {
         String userId = request.getParameter("userId");
         String page = request.getParameter("page");
         String pageSize = request.getParameter("pageSize");
         String cityName = request.getParameter("cityName");
         DataToCResultModel<Map<String, Object>> result = new DataToCResultModel<Map<String, Object>>();
         if(StringUtil.isNotEmpty(page)&&StringUtils.isNotEmpty(pageSize)){
             try {
                 Map<String, Object> map = fGoodsService.getFIndex(userId, page, pageSize, cityName);
                 List<FSliderWapsModel> sliderWapList = fSliderWapService.queryAllSliderWap();
                List<FSliderWapsModel> waplist = new ArrayList<>();
                 for (FSliderWapsModel fSliderWapsModel : sliderWapList) {
                    fSliderWapsModel.setWapImgUrl(LogoUrlUtil.splitOneImgUrl(fSliderWapsModel.getWapImgUrl()));
					if (fSliderWapsModel.getType() == 0) {
						FGoodsModel goodsModel = fGoodsService.fgoogsNoIsTrue(fSliderWapsModel.getContent());
                        if (goodsModel != null) {
                            fSliderWapsModel.setContent(String.valueOf(goodsModel.getId()));
                            waplist.add(fSliderWapsModel);
                        }
                    } else {
                        waplist.add(fSliderWapsModel);
					}
                 }
                map.put("sliderWapList", waplist);
                 result.setData(map);
             } catch (Exception e){
                result.setMsg("获取数据失败");
                 result.setStatus(400);
             }
         } else {
            result.setMsg("请求数据为空");
             result.setStatus(400);
         }
         result.setStatus(200);
        result.setMsg("获取数据成功");
         return result;
     } 
     
     /**
     * 根据商品分类和商品类型筛选出商品
     * 
     * @param request
     * @return
     */
     @RequestMapping(value = "/filter", method = RequestMethod.POST)
     @ResponseBody
     public DataToCResultModel<Map<String, Object>> filter(HttpServletRequest request){
         String userId = request.getParameter("userId");
         String page = request.getParameter("page");
         String pageSize = request.getParameter("pageSize");
         String cityName = request.getParameter("cityName");
         String sKindType = request.getParameter("sKindType");
         String tKindType = request.getParameter("tKindType");
         String type = request.getParameter("type");
         DataToCResultModel<Map<String, Object>> result = new DataToCResultModel<Map<String, Object>>();
         if(StringUtil.isNotEmpty(page)&&StringUtils.isNotEmpty(pageSize)){
             try {
                 result.setData(fGoodsService.filter(userId, page, pageSize, cityName,sKindType,tKindType,type));
             } catch (Exception e){
                result.setMsg("筛选数据失败");
                 result.setStatus(400);
             }
         } else {
            result.setMsg("筛选数据为空");
             result.setStatus(400);
         }
         result.setStatus(200);
        result.setMsg("筛选数据成功");
         return result;
     }
}