/*
 * @Title: FAuthorizeController.java
 * @Package com.f2b2c.eco.controller.platform.f2b
 * @Description: 授权码管理
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年9月8日 下午5:02:28
 * @version V1.0
 */
package com.f2b2c.eco.controller.platform.f2b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.platform.FAuthCodeModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.model.platform.FUserToRoleModel;
import com.f2b2c.eco.service.common.AreaService;
import com.f2b2c.eco.service.platform.FAuthService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.service.platform.FUserToRoleService;
import com.f2b2c.eco.status.RoleTypeEnum;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.ConstantUtil.RoleType;

/**
 * @ClassName: FAuthorizeController
 * @Description: 授权码管理
 * @author ligang.yang@softtek.com
 * @date 2016年9月8日 下午5:02:28
 *
 */
@Controller("/fAuthorizeController")
@RequestMapping("/farm/admin/auth")
public class FAuthorizeController
{

    @Autowired
    private FAuthService fAuthSer;
    @Autowired
    private FUserToRoleService fUserToRoleService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private FUserService fUserService;

	/*
	 * 显示登录界面
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return 返回登录视图名称
	 */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public final String page(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model)
    {
    	FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		// 获得该用户的登录角色，如果是专属顾问就有生成授权码的功能，否则就没有生成授权码的功能;1代码能生成，0代码不能生成
    	FUserToRoleModel userToRoleModel = fUserToRoleService.getUserToRoleById(user.getId());
    	if(StringUtils.equals(String.valueOf(userToRoleModel.getRoleId()),RoleTypeEnum.SERVER.toString())){
    		model.addAttribute("token","1");
    	}else{
    		model.addAttribute("token","0");
    	}
    	model.addAttribute("user",user);
        return "platform/auth";
    }

	/*
	 * 显示登录界面
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return 返回登录视图名称
	 */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public final String list(HttpServletRequest request, HttpServletResponse response)
    {
        return "platform/auth";
    }

	/*
	 * 显示登录界面
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return 返回登录视图名称
	 */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public final int generate(@RequestParam("num") Integer num, @RequestParam("userId") Integer userId)
    {
        return fAuthSer.insertAuthCodes(num, userId);
    }
    
    @RequestMapping(value = "/datatable/json", method = RequestMethod.GET)
    @ResponseBody
    public Page<FAuthCodeModel> dataTalbe(final Pageable pageable, HttpServletRequest request,
            HttpServletResponse response, HttpSession session)
    {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		List<Integer> areaListAll = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		if (!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID)) {
			if (user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)) { // 省合伙人
//				List<Integer> list = areaService.queryCityIdByProvinceId(user.getProvinceid()); // 获取所有的市id
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
				userList = fUserService.queryUserIdByAreaId(areaListAll);
			}
		}
        Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", user.getId());
		paramMap.put("userList", userList);
        Page<FAuthCodeModel> pages = fAuthSer.findWithPagination(pageable, paramMap);
        return pages;
    }

}
