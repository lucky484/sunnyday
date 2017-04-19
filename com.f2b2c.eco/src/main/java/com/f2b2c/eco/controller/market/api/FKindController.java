package com.f2b2c.eco.controller.market.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BKindToCModel;
import com.f2b2c.eco.model.market.BNoticeModel;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FSliderWapsModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.market.BNoticeService;
import com.f2b2c.eco.service.market.CDeliveryAddressService;
import com.f2b2c.eco.service.market.FKindService;
import com.f2b2c.eco.service.platform.BSliderWapService;
import com.f2b2c.eco.service.platform.F2BbKindService;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.PropertiesUtil;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * @author josen.yang
 *
 */
@Controller(value = "bKindController")
@RequestMapping(value = "/api/fkind")
public class FKindController {

	/**
     * 日志记录器
     */
	private final static Logger logger=LoggerFactory.getLogger(FKindController.class);
	@Autowired
	private FKindService fKindService;
	
	@Autowired
	private F2BbKindService bKindService;


	@Autowired
	private BSliderWapService bSliderWapService;

	@Autowired
	private CDeliveryAddressService cDeliveryAddressService;
	@Autowired
	private BNoticeService bNoticeService;
	@Autowired
	private BGoodsService bGoodsService;
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query-all-kind", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Map<String,Object>> queryAllKind(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
        // 首页轮播图
		List<FSliderWapsModel> sliderWapList = bSliderWapService.queryAllSliderWap();
		if(sliderWapList != null && sliderWapList.size() > 0){
			for(FSliderWapsModel FSliderWaps : sliderWapList){
				FSliderWaps.setWapImgUrl(PropertiesUtil.getValue("path") + FSliderWaps.getWapImgUrl());
                // 如果是wap图类型是商品详情 那么根据商品编号查到商品id set进wap图的model
				if (FSliderWaps.getType() == 0) {
					BGoodsModel goodsModel = bGoodsService.bgoogsNoIsTrue(FSliderWaps.getContent());
					FSliderWaps.setContent(String.valueOf(goodsModel.getId()));
				}
			}
		}
        // 商品分类
		List<BKindToCModel> list = fKindService.queryAllKindTo();
        // 把list的第一个写死写成水果分类
		/*
         * BKindToCModel model = new BKindToCModel(); model.setId(1000);
         * model.setKindName("水果"); model.setCatalog("0");
         * model.setRemark("水果分类"); model.setIconUrl("kind/fruitlogo.png");
         * list.add(0, model);
         */
		if(list != null && list.size() > 0){
			for(BKindToCModel bKindToC : list){
				if(bKindToC.getIconUrl() != null && !bKindToC.getIconUrl().equals("")){
					String[] iconUrl = bKindToC.getIconUrl().split("\\|");
					bKindToC.setIconUrl(PropertiesUtil.getValue("path") + iconUrl[0]);
				}
			}
		}
		if(StringUtils.isNotBlank(userId)){
			CDeliveryAddressModel cDeliveryAddress = cDeliveryAddressService.getDefaultDeliveryAddress(Integer.valueOf(userId));
			map.put("cDeliveryAddress", JsonUtil.parseToNoEmptyStr(cDeliveryAddress));
		}
		map.put("sliderWapList", JsonUtil.parseToNoEmptyStr(sliderWapList));
		map.put("list",JsonUtil.parseToNoEmptyStr(list));
		List<BNoticeModel> noticeList = bNoticeService.findAllNotice();
		map.put("noticeList", JsonUtil.parseToNoEmptyStr(noticeList));
		return new DataToCResultModel<Map<String,Object>>(200,"success",map);
	}
	
	/**
     * 获取一级分类或者默认二级和三级分类
     */
	@RequestMapping(value = "/query-first-kind", method = RequestMethod.GET)
	@ResponseBody
	public DataToCResultModel<Map<String,Object>> queryFirstKind() {
		try {
			return fKindService.queryFirstKind(-1);
		} catch (Exception e){
			logger.error(e.getMessage());
            return new DataToCResultModel<Map<String, Object>>(400, "获取一级分类失败", null);
		}
	}
	
	/**
     * 获取以及分类或者默认二级和三级分类
     * 
     * @return
     */
	@RequestMapping(value = "/query-sub-kind", method = RequestMethod.GET)
	@ResponseBody
	public DataToCResultModel<List<BKindToCModel>> querySubKind(HttpServletRequest request) {
		try {
            // 获取父类
			String pId = request.getParameter("parentId");
			if(StringUtil.isNotEmpty(pId)){
                return new DataToCResultModel<List<BKindToCModel>>(200, "获取数据成功",
						fKindService.getSubKindList(Integer.valueOf(pId)));
			} else {
                return new DataToCResultModel<List<BKindToCModel>>(400, "获取数据失败", null);
			}
		} catch (Exception e){
			logger.error(e.getMessage());
            return new DataToCResultModel<List<BKindToCModel>>(400, "获取数据失败", null);
		}
	}
	
	@RequestMapping(value = "/get-kinds-for-jstree")
	@ResponseBody
	public JSONArray getKindsForJsTree() {
		JSONArray j = bKindService.getKindsForJsTree();
		return j;
	}

	/**
     * 根据分类Id查询子分类列表(2级分类列表)
     */
	@RequestMapping(value = "/query-kindlist-by-kindid", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Map<String, Object>> queryBGoodsByKindId(Integer kindId,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        // 商品分类
		List<BKindToCModel> kindList = fKindService.queryKindListByKindId(kindId);
		if (kindList != null && kindList.size() > 0) {
			List<BKindToCModel> chdList = fKindService.queryKindListByKindId(kindList.get(0).getId());
			for (BKindToCModel bKindToC : kindList) {
				if (bKindToC.getIconUrl() != null && !bKindToC.getIconUrl().equals("")) {
					String[] iconUrl = bKindToC.getIconUrl().split("\\|");
					bKindToC.setIconUrl(PropertiesUtil.getValue("path") + iconUrl[0]);
				}
				bKindToC.setPicUrl(PropertiesUtil.getValue("path") + bKindToC.getPicUrl());
			}
			if (chdList != null && chdList.size() > 0) {
				for (BKindToCModel bKindToC : chdList) {
					if (bKindToC.getIconUrl() != null && !bKindToC.getIconUrl().equals("")) {
						String[] iconUrl = bKindToC.getIconUrl().split("\\|");
						bKindToC.setIconUrl(PropertiesUtil.getValue("path") + iconUrl[0]);
					}
					bKindToC.setPicUrl(PropertiesUtil.getValue("path") + bKindToC.getPicUrl());
				}
			}
			kindList.get(0).setSubList(chdList);
		}
		map.put("kindList", kindList);
		return new DataToCResultModel<Map<String, Object>>(200, "success", map);
	}

	/**
     * 根据商品编号查询分类是否计算佣金
     * 
     * @param goodsNo
     * @return
     */
	@RequestMapping(value = "/bkind-isfruit", method = RequestMethod.POST)
	@ResponseBody
	public boolean bgoogsNoIsTrue(Integer id) {
		boolean istrue = false;
		if (id != null) {
            String catalog = fKindService.findIsCommissionById(id);
			if (Integer.valueOf(catalog) == 0) {
				istrue = true;
			}
			return istrue;
		} else {
			return istrue;
		}

	}
}
