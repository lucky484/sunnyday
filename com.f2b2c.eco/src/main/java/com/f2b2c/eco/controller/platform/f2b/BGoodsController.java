package com.f2b2c.eco.controller.platform.f2b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.platform.BShopService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.MessageType;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.ConstantUtil.RoleType;
import com.f2b2c.eco.util.PropertiesUtil;

@Controller("bGoodsControllerManagement")
@RequestMapping("/farm/terminal/goods")
public class BGoodsController extends BaseController{

	
	@Autowired
	private BGoodsService bGoodsService;
	@Autowired
	private FUserService fUserService;
	@Autowired
	private BShopService bShopService;
	
	/**
     * 业务逻辑，拿到所有的b_goods表中的商品进行审核
     */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		return "platform/goods";
		
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public final Page<BGoodsModel> pagination(final Pageable pageable,HttpServletRequest request, HttpServletResponse response,HttpSession session){
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		List<Integer> areaListAll = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		List<Integer> bUserList = new ArrayList<Integer>();
		if (!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID)) {
            if (user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)) { // 省合伙人
				// List<Integer> list =
				// areaService.queryCityIdByProvinceId(user.getProvinceid()); //
                // 获取所有的市id
//				List<Integer> areaList = areaService.queryAreaIdByCityId(list);
				areaListAll.add(user.getProvinceid());
//				areaListAll.addAll(list);
//				areaListAll.addAll(areaList);
			} else if (user.getRoleId().equals(RoleType.CITY_ROLE_ID)) {
//				List<Integer> cityList = new ArrayList<Integer>();
//				cityList.add(user.getCityid());
//				List<Integer> areaList = areaService.queryAreaIdByCityId(cityList);
				areaListAll.add(user.getCityid());
//				areaListAll.addAll(areaList);
			} else if (user.getRoleId().equals(RoleType.AREA_ROLE_ID)) {
				areaListAll.add(user.getAreaId());
			} else if (user.getRoleId().equals(RoleType.COUNSELOR_ROLE_ID)) {
				userList.add(user.getId());
			}
			if (areaListAll != null && areaListAll.size() > 0) {
				userList = fUserService.queryUserId(areaListAll);
			}
			if (userList != null && userList.size() > 0)
			bUserList = bShopService.queryBUserIdByList(userList);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String goodsNo = request.getParameter("goodsNo");
		String goodsName = request.getParameter("goodsName");
		String shopname = request.getParameter("shopName");
		String goodsStatus = request.getParameter("goodsStatus");
		paramMap.put("goodsNo", goodsNo);
		paramMap.put("goodsName", goodsName);
		paramMap.put("shopName", shopname);
		paramMap.put("goodsStatus", goodsStatus);
		Page<BGoodsModel> pages = bGoodsService.findWithPagination(pageable, paramMap);
		return pages;
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
		
		model.addAttribute("goodId", Integer.valueOf(id));
		return "goods/reply";
	}
	
	/**
     * 添加审核不通过时的原因
     * 
     * @param request
     * @param response
     * @param bGoodsModel
     * @return
     */
	@RequestMapping(value = "/replyTo", method = RequestMethod.POST)
	@ResponseBody
	public DTOResult reply(HttpServletRequest request,HttpServletResponse response,BGoodsModel bGoodsModel){
		
		DTOResult result = new DTOResult();
		int count = bGoodsService.updateReply(bGoodsModel);
		if(count == 1){
			result.setType(MessageType.success);
            result.setMessage("添加审核不通过原因成功");
		}else{
			result.setType(MessageType.error);
            result.setMessage("添加审核不通过原因失败");
		}
		return result;
	}
	
	/**
     * 更新商品的状态
     * 
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value="/updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public DTOResult updateStatus(HttpServletRequest request,HttpServletResponse response){
		
		DTOResult result = new DTOResult();
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		int count = bGoodsService.updateStatus(id,status);
		if(count == 1){
			result.setType(MessageType.success);
            result.setMessage("修改商品状态成功");
		}else{
			result.setType(MessageType.error);
            result.setMessage("修改商品状态失败");
		}
		return result;
	}
	
	/**
     * 根据商品id去获取商品的详细信息
     * 
     * @param request
     * @param response
     * @param id
     * @param model
     * @return
     */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request, HttpServletResponse response, String id, Model model) {
		
		BGoodsModel bGoodsModel = bGoodsService.findBgoodsById(Integer.valueOf(id));
		String path = PropertiesUtil.getValue("path");
		List<String> urllist = new ArrayList<>();
		if (bGoodsModel.getLogoUrl() != null) {
			String[] url = bGoodsModel.getLogoUrl().split("\\|");
			for (int i = 0; i < url.length; i++) {
				urllist.add(path + url[i]);
			}
		}
		;
		model.addAttribute("data", bGoodsModel);
		model.addAttribute("urllist", urllist);
		return "goods/show";
		
	}
}
