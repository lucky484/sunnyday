package com.f2b2c.eco.controller.platform.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.BKindModel;
import com.f2b2c.eco.model.platform.FKindModel;
import com.f2b2c.eco.service.market.FKindService;
import com.f2b2c.eco.service.platform.F2BbKindService;
import com.f2b2c.eco.util.JsonUtil;

/**
 * 
 * @author jackson.zhu
 *
 */
@Controller("fKindApiController")
@RequestMapping("/api/farm/fkind")
public class FKindController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FKindController.class);
	@Autowired
	private FKindService fKindService;
	@Autowired
	private F2BbKindService bKindService;
	
	// 加载平台上所有的水果分类
	@RequestMapping(value="/get-all-kind",method=RequestMethod.GET)
	@ResponseBody
	public DataToCResultModel<Map<String,Object>> getAllKinds(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			List<FKindModel> lists = fKindService.getAllKinds();
			List<FKindModel> subLists = new ArrayList<FKindModel>();
			// 再根据获取的所有父分类或者第一个父分类的所有子分类
			if(CollectionUtils.isNotEmpty(lists)){
				Integer firstId = lists.get(0).getId();
				subLists = fKindService.getSubKindByParentId(firstId);
			}
			map.put("lists", JsonUtil.parseToNoEmptyStr(lists));
			map.put("firstLists", JsonUtil.parseToNoEmptyStr(subLists));
			return new DataToCResultModel<Map<String, Object>>(200, "获取平台商品列表成功", map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new DataToCResultModel<Map<String, Object>>(400, "获取平台商品列表失败", null);
		}
		
	}
	
	// 加载B2C平台上所有的水果分类
	@RequestMapping(value = "/get-btc-kind", method = RequestMethod.GET)
	@ResponseBody
	public DataToCResultModel<Map<String, Object>> getAllB2CKinds(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<BKindModel> lists = bKindService.getAllKinds();
			List<BKindModel> subLists = new ArrayList<BKindModel>();
			// 再根据获取的所有父分类或者第一个父分类的所有子分类
			if (CollectionUtils.isNotEmpty(lists)) {
				Integer firstId = lists.get(0).getId();
				subLists = bKindService.getSubKindByParentId(firstId);
			}
			map.put("lists", JsonUtil.parseToNoEmptyStr(lists));
			map.put("firstLists", JsonUtil.parseToNoEmptyStr(subLists));
			return new DataToCResultModel<Map<String, Object>>(200, "获取平台商品列表成功", map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new DataToCResultModel<Map<String, Object>>(400, "获取平台商品列表失败", null);
		}

	}

	@RequestMapping(value="/get-sub-kind",method=RequestMethod.GET)
	@ResponseBody
	public DataToCResultModel<Map<String,Object>> getSubKind(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String kindId = request.getParameter("kindId");
			List<FKindModel> lists = fKindService.getSubKindByParentId(Integer.parseInt(kindId));
			map.put("lists", JsonUtil.parseToNoEmptyStr(lists));
			return new DataToCResultModel<Map<String, Object>>(200, "获取平台商品列表成功", map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new DataToCResultModel<Map<String, Object>>(400, "获取平台商品列表失败", null);
		}
		
	}
	
	
	
}
