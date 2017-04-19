package com.f2b2c.eco.controller.market.b2c;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.service.market.CDeliveryAddressService;
import com.f2b2c.eco.util.JsonUtil;

/**
 * C端用户相关操作
 * 
 * @author jacob.shen
 *
 */
@Controller("/CDeliveryAddressController")
@RequestMapping(value = "/customer/cdeliveryaddress")
public class CDeliveryAddressController {

	@Autowired
	private CDeliveryAddressService cDeliveryAddressService;

	/**
     * 获取收货地址
     * 
     * @return
     */
	@RequestMapping(value = "/get-delivery-addresses",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> getDeliveryAddresses(HttpServletRequest request) {
        String id = request.getParameter("id"); // 用户id
		if (StringUtils.isEmpty(id)) {
            return new DataToCResultModel<Object>(400, "获取个人收货地址失败，id不能为空", null);
		} else {
			List<CDeliveryAddressModel> list = cDeliveryAddressService.getUserDeliveryAddresses(Integer.valueOf(id));
			if (CollectionUtils.isEmpty(list)) {
                return new DataToCResultModel<Object>(400, "无数据", null);
			} else {
                return new DataToCResultModel<Object>(200, "获取个人收货地址成功", JsonUtil.parseToNoEmptyStr(list));
			}
		}
	}

	/**
     * 添加收货地址
     * 
     * @param request
     * @return
     */
	@RequestMapping(value = "/insert-delivery-address",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> insertDeliveryAddress(HttpServletRequest request) {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String areaId = request.getParameter("areaId");
		String cityId = request.getParameter("cityId");
		String provinceId = request.getParameter("provinceId");
		String userId = request.getParameter("userId");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		
		CDeliveryAddressModel deliveryAddr = new CDeliveryAddressModel();
		deliveryAddr.setAddress(address);
		deliveryAddr.setAreaId(Integer.valueOf(areaId));
		deliveryAddr.setCityId(Integer.valueOf(cityId));
		deliveryAddr.setProvinceId(Integer.valueOf(provinceId));
		deliveryAddr.setConsignee(name);
		deliveryAddr.setCreatedTime(new Date());
		deliveryAddr.setUserId(Integer.valueOf(userId));
		deliveryAddr.setLatitude(latitude);
		deliveryAddr.setLongitude(longitude);
		
		CUserModel cUser = (CUserModel) request.getSession().getAttribute("cUser");
		deliveryAddr.setCreatedUser(cUser == null ? null : cUser.getId());
		List<CDeliveryAddressModel> list = cDeliveryAddressService.getUserDeliveryAddresses(Integer.valueOf(userId));
		if (CollectionUtils.isEmpty(list)) {
            // 当前用户还没有添加收货地址，所以默认此次添加的地址为收货地址
			deliveryAddr.setIsDefault("1");
		} else {
			deliveryAddr.setIsDefault("0");
		}
		deliveryAddr.setMobile(mobile);
		deliveryAddr.setStatus(1);
		int result = cDeliveryAddressService.insertDeliveryAddress(deliveryAddr);
		if (result > 0) {
            return new DataToCResultModel<Object>(200, "添加收货地址成功", JsonUtil.parseToNoEmptyStr(deliveryAddr));
		} else {
            return new DataToCResultModel<Object>(400, "添加收货地址失败", null);
		}
	}

	/**
     * 修改收货地址/默认地址
     * 
     * @param request
     * @return
     */
	@RequestMapping(value = "/update-delivery-address",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> updateDeliveryAddress(HttpServletRequest request) {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String areaId = request.getParameter("areaId");
		String cityId = request.getParameter("cityId");
		String provinceId = request.getParameter("provinceId");
		String id = request.getParameter("id");
		String isDefault = request.getParameter("isDefault");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		
		CDeliveryAddressModel dAddr = cDeliveryAddressService.getDeliveryAddress(Integer.valueOf(id));
        // 修改默认地址操作
		if (isDefault != null && isDefault.equals("1")) {
			dAddr.setIsDefault(isDefault);
			CDeliveryAddressModel defaultAddr = cDeliveryAddressService.getDefaultDeliveryAddress(dAddr.getUserId());
			if (defaultAddr != null) {
                // 取消默认地址
				defaultAddr.setIsDefault("0");
				cDeliveryAddressService.updateDeliveryAddress(defaultAddr);
			}
		}
		dAddr.setAddress(address);
		dAddr.setConsignee(name);
		dAddr.setMobile(mobile);
		dAddr.setLatitude(latitude);
		dAddr.setLongitude(longitude);
		dAddr.setAreaId(Integer.valueOf(areaId));
		dAddr.setCityId(Integer.valueOf(cityId));
		dAddr.setProvinceId(Integer.valueOf(provinceId));
		int result = cDeliveryAddressService.updateDeliveryAddress(dAddr);
		if (result > 0) {
            return new DataToCResultModel<Object>(200, "修改收货地址成功", JsonUtil.parseToNoEmptyStr(dAddr));
		} else {
            return new DataToCResultModel<Object>(400, "修改收货地址失败", null);
		}
	}

	/**
     * 删除收货地址
     * 
     * @param request
     * @return
     */
	@RequestMapping(value = "/delete-delivery-address",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> deleteDeliveryAddress(HttpServletRequest request) {
		String id = request.getParameter("id");
		CDeliveryAddressModel dAddr = cDeliveryAddressService.getDeliveryAddress(Integer.valueOf(id));
		dAddr.setStatus(0);
		dAddr.setDeletedTime(new Date());
		int result = cDeliveryAddressService.updateDeliveryAddress(dAddr);
		if (result > 0) {
            // 删除的是默认地址，则需要从现有地址中再找一个设为默认地址
			if ("1".equals(dAddr.getIsDefault())) {
				List<CDeliveryAddressModel> list = cDeliveryAddressService.getUserDeliveryAddresses(dAddr.getUserId());
                // 将第一个地址设为默认地址
				if (CollectionUtils.isNotEmpty(list)) {
					CDeliveryAddressModel defaultAddr = list.get(0);
					defaultAddr.setIsDefault("1");
					cDeliveryAddressService.updateDeliveryAddress(defaultAddr);
				}
			}
            return new DataToCResultModel<Object>(200, "删除收货地址成功", JsonUtil.parseToNoEmptyStr(dAddr));
		} else {
            return new DataToCResultModel<Object>(400, "删除收货地址失败", null);
		}
	}
}
