package com.f2b2c.eco.controller.market.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BShopInfoToCModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.model.market.PageResultModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.service.market.UserBrowerRecordService;
import com.f2b2c.eco.util.DateUtil;
import com.f2b2c.eco.util.DistanceUtil;
import com.f2b2c.eco.util.PropertiesUtil;

@Controller(value="recommendGoodsController")
@RequestMapping(value="/api/recommendGoods")
public class RecommendGoodsController {
     
	 @Autowired
     private BShopInfoService bShopInfoService;
	 
	 @Autowired
	 private BGoodsService bGoodsService;
	 
	 @Autowired
	 private UserBrowerRecordService userBrowerRecordService;
	 
	 @RequestMapping(value="/getRecommendGoods",method=RequestMethod.POST)
	 @ResponseBody
	 public DataToCResultModel<PageResultModel> getRecommendGoods(HttpServletRequest request){
		 String userId = request.getParameter("userId");
		 String kindId = request.getParameter("kindId");
		 String locationX = request.getParameter("locationX");
         String locationY = request.getParameter("locationY");
         String page = request.getParameter("page");
         String pageSize = request.getParameter("pageSize");
         DataToCResultModel<PageResultModel> dataToCResult = new DataToCResultModel<PageResultModel>();
         PageResultModel pageResult = new PageResultModel();
         List<BGoodsModel> list = new ArrayList<BGoodsModel>();
         int totalCount = 0;
         if(!locationX.equals("") && locationX != null){
        	 List<BShopInfoToCModel> shopList = bShopInfoService.queryAllShop();
        	 List<BShopInfoToCModel> shopListTo = new ArrayList<BShopInfoToCModel>();
        	 Map<String,Object> map = new HashMap<String,Object>();
        	 for(int i=0;i<shopList.size();i++){
        		 double length = DistanceUtil.distance(Double.valueOf(locationX), Double.valueOf(locationY),
        				 Double.valueOf(shopList.get(i).getLocationX()), Double.valueOf(shopList.get(i).getLocationY()));
        		 if (length - Double.valueOf(1000) <= 0) {
					shopList.get(i).setLength(length);
        			 shopListTo.add(shopList.get(i));
        		 }
        	 }
        	 if(shopListTo != null && shopListTo.size() > 1){
				// 根据算好的距离排序
        		 Collections.sort(shopListTo, new Comparator<BShopInfoToCModel>() {
        			 public int compare(BShopInfoToCModel o1, BShopInfoToCModel o2) {
        				 return new Double(o1.getLength()).compareTo(new Double(o2.getLength()));
        			 }
        		 });
        	 }
        	 if(!userId.equals("") && userId != null){
        		 if(shopListTo != null && shopListTo.size() > 0){
        			 map.put("userId", Integer.valueOf(userId));
        			 map.put("kindId", (kindId == null || kindId.equals("")) ? null : Integer.valueOf(kindId));
        			 List<BGoodsModel> browerList = userBrowerRecordService.queryBrowerRecordByUserId(map); 
        			 if(browerList != null && browerList.size() > 0){
        				 map.put("shopId", shopListTo.get(0).getId());
        				 totalCount = userBrowerRecordService.queryAllBrowerRecordCount(map);
						if (totalCount > 0) { // 附近存在店铺，而又能和浏览记录匹配到数据，
        					 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        					 map.put("num", p.getStart());
        					 map.put("pageSize", p.getPageSize());
        					 list = userBrowerRecordService.queryAllBrowerRecord(map);
						} else { // 附近存在店铺，但不和浏览记录匹配
        					 map.put("shopId", shopListTo.get(0).getId());
        					 totalCount = userBrowerRecordService.queryShopGoodsCount(map);
        					 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        					 map.put("num", p.getStart());
        					 map.put("pageSize", p.getPageSize());
        					 list = userBrowerRecordService.queryShopGoods(map);
        				 }
        			 }else{
        				 map.put("shopId", shopListTo.get(0).getId());
						 totalCount = bGoodsService.queryAllGoodsCountByShopId(map);
        				 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        				 map.put("num", p.getStart());
        				 map.put("pageSize", p.getPageSize());
						// 查询最近店铺的水果类商品和所有非水果类的商品
        				 list = bGoodsService.queryAllGoodsByShopId(map);
        			 }
        		 }else{
        			 map.put("userId", Integer.valueOf(userId));
        			 map.put("kindId", (kindId == null || kindId.equals("")) ? null : Integer.valueOf(kindId));
        			 List<BGoodsModel> browerList = userBrowerRecordService.queryBrowerRecordByUserId(map); 
        			 if(browerList != null && browerList.size() > 0){
        				 totalCount = userBrowerRecordService.queryAllFeFruitCount(map);
        				 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        				 map.put("num", p.getStart());
        				 map.put("pageSize", p.getPageSize());
        				 list = userBrowerRecordService.queryAllFeFruit(map);
        			 }else{
        				 totalCount = userBrowerRecordService.queryFeFruitCount(kindId == null ? null : Integer.valueOf(kindId));
        				 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        				 map.put("num", p.getStart());
        				 map.put("pageSize", p.getPageSize());
        				 list = userBrowerRecordService.queryFeFruit(map);
        			 }
        		 }
        	 }else{
        		 if(shopListTo != null && shopListTo.size() > 0){
        			 map.put("shopId", shopListTo.get(0).getId());
        			 map.put("kindId", (kindId == null || kindId.equals("")) ? null : Integer.valueOf(kindId));
        			 totalCount = bGoodsService.queryAllGoodsCountByShopId(map);
        			 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        			 map.put("num", p.getStart());
        			 map.put("pageSize", p.getPageSize());
					// 查询最近店铺的水果类商品和所有非水果类的商品
        			 list = bGoodsService.queryAllGoodsByShopId(map);
        		 }else{
        			 totalCount = bGoodsService.queryAllGoodsCount(Integer.valueOf(kindId));
        			 Page p = new Page(totalCount, Integer.valueOf(page),Integer.valueOf(pageSize));
        			 map.put("kindId", (kindId == null || kindId.equals("")) ? null : Integer.valueOf(kindId));
        			 map.put("num", p.getStart());
        			 map.put("pageSize", p.getPageSize());
					// 查询所有非水果类商品
        			 list = bGoodsService.queryAllGoods(map);
        		 }
        	 }
        	 if(list != null && list.size() > 0){
        		 for(BGoodsModel bGoods : list){
        			 String[] logoUrl = bGoods.getLogoUrl().split("\\|");
        			 bGoods.setLogoUrl(PropertiesUtil.getValue("path") + logoUrl[0]);
        		 }
        	 }
        	 pageResult.setRows(list);
        	 pageResult.setTotalCount(totalCount);
        	 dataToCResult.setData(pageResult);
        	 dataToCResult.setMsg("success");
        	 dataToCResult.setStatus(200);
         }else{
        	 dataToCResult.setData(pageResult);
        	 dataToCResult.setMsg("fail");
        	 dataToCResult.setStatus(400);
         }
		 return dataToCResult;
	 }
	 public static void main(String[] args) {
		 Timer timer = new Timer();  
	        timer.schedule(new TimerTask() {  
	            public void run() {  
				System.out.println("-------设定要指定任务--------");
	                System.out.println(DateUtil.getyyyyMMddhhmmss());
	            }  
		}, 90000);// 设定指定的时间time,此处为2000毫秒
	}
}
