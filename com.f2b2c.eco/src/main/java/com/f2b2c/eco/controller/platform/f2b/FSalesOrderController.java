package com.f2b2c.eco.controller.platform.f2b;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b2c.eco.bean.platform.FDifferentOrderBean;
import com.f2b2c.eco.model.common.DataGridModel;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.common.AreaService;
import com.f2b2c.eco.service.platform.BShopService;
import com.f2b2c.eco.service.platform.FSalesOrderService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.MessageType;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.ConstantUtil.RoleType;
import com.f2b2c.eco.util.PropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jodd.util.StringUtil;
import net.sf.json.JSONObject;

@Controller(value = "fSalesOrderController")
@RequestMapping(value = "/farm/order")
public class FSalesOrderController {

	/**
	 * 日志工具类
	 */
	private static final Logger logger = LoggerFactory.getLogger(FSalesOrderController.class);

	/**
	 * 订单服务类
	 */
	@Autowired
	private FSalesOrderService fSalesOrderService;
	@Autowired
	private FUserService fUserService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private BShopService bShopService;
	
	/**
	 * 显示订单列表界面
	 * 
	 * @param request
	 * @param response
	 * @return 返回登录视图名称
	 */
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public final String index(HttpServletRequest request, HttpServletResponse response) {
		return "platform/orderlist";
	}

	@RequestMapping(value = "/querybypages", method = RequestMethod.POST)
	@ResponseBody
	public final Page<FSalesOrderModel> queryByPages(final Pageable pageable, HttpServletRequest request,
			HttpServletResponse response) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		String status = request.getParameter("status");
		String orderNo = request.getParameter("orderNo");
		String shopName = request.getParameter("shopName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		List<Integer> areaListAll = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		List<Integer> bUserList = new ArrayList<Integer>();
		if (!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID) && !user.getRoleId().equals(RoleType.FINANCE_ROLE_ID)) {
			if (user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)) { // 省合伙人
				// 获取所有的市id
				areaListAll.add(user.getProvinceid());
			} else if (user.getRoleId().equals(RoleType.CITY_ROLE_ID)) {
				areaListAll.add(user.getCityid());
			} else if (user.getRoleId().equals(RoleType.AREA_ROLE_ID)) {
				areaListAll.add(user.getAreaId());
			} else if (user.getRoleId().equals(RoleType.COUNSELOR_ROLE_ID)) {
				userList.add(user.getId());
			}
			if (areaListAll != null && areaListAll.size() > 0) {
				userList = fUserService.queryUserIdByAreaId(areaListAll);
			}
			if (userList != null && userList.size() > 0)
			bUserList = bShopService.queryBUserIdByList(userList);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageable", pageable);
		paramMap.put("bUserList", bUserList);
		paramMap.put("orderStatus", status);
		paramMap.put("orderNo", orderNo);
		paramMap.put("shopName", shopName);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		Page<FSalesOrderModel> pages = fSalesOrderService.findWithPaginationByUser(paramMap);
		return pages;

	}

	@RequestMapping(value = "/modifyprepare", method = RequestMethod.GET)
	public final String modifyPrepare(HttpServletRequest request, HttpServletResponse response, Model model,
			String id) {
		FSalesOrderModel fSalesOrderModel = null;
		try {
			fSalesOrderModel = fSalesOrderService.queryById(id);
		} catch (Exception e) {
			logger.error("Excepiton happend when update order, Excepiton message is " + e.getMessage());
		}
		if (null != fSalesOrderModel) {
			model.addAttribute("orderId", fSalesOrderModel.getOrderId());
			model.addAttribute("detailsMap",
					JSONObject.fromObject(getParamMap(fSalesOrderModel.getfSalesOrderDetailsModel()).toString()));
		}

		model.addAttribute("fSalesOrderModel", fSalesOrderModel);

		return "platform/orderadd";
	}

	@RequestMapping(value = "/updateorder", method = RequestMethod.POST)
	@ResponseBody
	public final Boolean updateOrder(HttpServletRequest request, HttpServletResponse response, DataGridModel params) {

		Object detailsArr = params.getParams().get("detailsArr");
		Gson gson = new Gson();
		List<FSalesOrderDetailsModel> details = gson.fromJson((String) detailsArr,
				new TypeToken<List<FSalesOrderDetailsModel>>() {
				}.getType());

		List<String> deleteIds = new ArrayList<String>();
		List<FSalesOrderDetailsModel> detailsModels = new ArrayList<FSalesOrderDetailsModel>();
		if (CollectionUtils.isNotEmpty(details)) {
			Iterator<FSalesOrderDetailsModel> iterator = details.iterator();

			while (iterator.hasNext()) {
				FSalesOrderDetailsModel model = iterator.next();
				if (NumberUtils.INTEGER_ZERO.equals(model.getGoodsQty())) {
					deleteIds.add(model.getId());
				} else {
					detailsModels.add(model);
				}
			}
		}
		try {
			if (CollectionUtils.isNotEmpty(deleteIds)) {
				fSalesOrderService.deleteOrderDetails(deleteIds);
			}

			if (CollectionUtils.isNotEmpty(detailsModels)) {
				fSalesOrderService.updateDetailsModel(detailsModels);
			}

		} catch (Exception e) {
			logger.error("Excepiton happend when update order, Excepiton message is " + e.getMessage());
			return false;
		}

		return true;
	}

/*	@RequestMapping(value = "/deleteorder", method = RequestMethod.POST)
	@ResponseBody
	public final Boolean deleteOrder(HttpServletRequest request, HttpServletResponse response, String orderId) {

		try {
			fSalesOrderService.deleteOrderByOrderId(orderId);
		} catch (Exception e) {
			logger.error("Excepiton happend when update order, Excepiton message is " + e.getMessage());
			return false;
		}

		return true;
	}*/
	
	@RequestMapping(value = "/sendorder", method = RequestMethod.POST)
	@ResponseBody
	public final Boolean sendOrder(HttpServletRequest request, HttpServletResponse response, String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("status", 4);
			map.put("orderId", orderId);
			map.put("deliverTime",new Date());
			// 修改状态为待收货
			fSalesOrderService.updateOrderStatus(map);
		} catch (Exception e) {
			logger.error("Excepiton happend when update order, Excepiton message is " + e.getMessage());
			return false;
		}

		return true;
	}
	
	@RequestMapping(value = "/makesurepay", method = RequestMethod.POST)
	@ResponseBody
	public final Boolean makeSurePay(HttpServletRequest request, HttpServletResponse response, String orderId, String realPay) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("status", 1);
			map.put("orderId", orderId);
			Double pay = Double.valueOf(realPay);
			Integer real = (int) (pay * 100);
			map.put("realPay", real);
			map.put("receiverTime", new Date());
			// 确认收货已经完成
			fSalesOrderService.updateOrderStatus(map);
			
		} catch (Exception e) {
			logger.error("Excepiton happend when update order, Excepiton message is " + e.getMessage());
			return false;
		}

		return true;
	}


	private Map<String, Object> getParamMap(List<FSalesOrderDetailsModel> detailsModels) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (CollectionUtils.isNotEmpty(detailsModels)) {
			for (FSalesOrderDetailsModel model : detailsModels) {
				paramMap.put(model.getId(), model.getGoodsQty());
			}
		}

		return paramMap;
	}
	
	/**
	 * 审核补差价金额
	 * 
	 * @return 返回补差价订单信息
	 */
	@RequestMapping(value = "/showDiffOrder", method = RequestMethod.POST)
	@ResponseBody
	public Object showDiffOrder(HttpServletRequest request){
		String id = request.getParameter("id");
		FDifferentOrderBean fDifferentOrderBean = new FDifferentOrderBean();
		if(StringUtil.isNotEmpty(id)){
			fDifferentOrderBean = fSalesOrderService.showDifferentOrder(id);
		}
		return fDifferentOrderBean;
	}
	
	/**
	 * 审核
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/checkOrderMoney",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult checkDiffOrder(HttpServletRequest request){
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String realPay = request.getParameter("realPay");
        String orderId = request.getParameter("orderId");
        String money = request.getParameter("money");
        DTOResult result = new DTOResult();
        try {
            if(StringUtil.isNotEmpty(id)&&StringUtil.isNotEmpty(type)&&StringUtil.isNotEmpty(realPay)&&StringUtil.isNotEmpty(orderId)&&StringUtil.isNotEmpty(money)){
                Integer size = fSalesOrderService.checkDiffOrder(id, type,orderId,Integer.valueOf(realPay),Integer.valueOf(money));
                if(size==0){
                    result.setType(MessageType.error);
					result.setMessage("审核店铺信息操作失败！");
                }
            } else {
                result.setType(MessageType.error);
				result.setMessage("请求参数不能为空！");
            }
        } catch (Exception e){
            result.setType(MessageType.error);
			result.setMessage("审核店铺信息操作失败！");
        }
        result.setType(MessageType.success);
		result.setMessage("审核店铺信息操作成功！");
        return result;
    }

	/**
	 * 查看订单
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public final ModelAndView bgoodsdetail(String orderId, String realPay, HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {
		if (StringUtils.isNotBlank(orderId)) {
			BToCOrderModel bToCOrderModel = fSalesOrderService.findOrderByOrderId(orderId);
			for (BGoodsToCModel bGoodsToC : bToCOrderModel.getList()) {
				if (StringUtils.isNotBlank(bGoodsToC.getLogoUrl())) {
					String[] url = bGoodsToC.getLogoUrl().split("\\|");
					bGoodsToC.setLogoUrl(PropertiesUtil.getValue("path") + url[0]);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(realPay)) {
				bToCOrderModel.setRealTotalPrice(Integer.valueOf(realPay));
			}
			map.put("bToCOrderModel", bToCOrderModel);
			return new ModelAndView("platform/orderdetails", map);
		} else {
			return new ModelAndView("platform/orderlist");
		}
	}
	
	/**
	 * 删除订单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteOrder(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		int result = fSalesOrderService.updateOrderDeleteTimeById(orderId);
		map.put("result", result);
		return map;
	}
}