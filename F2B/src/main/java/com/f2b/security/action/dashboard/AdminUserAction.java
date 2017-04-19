package com.f2b.security.action.dashboard;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.action.converter.AdminUserConverter;
import com.f2b.security.business.biz.AdminUserBiz;
import com.f2b.security.domain.AdminUser;
import com.f2b.sugar.tools.StringConverters;

import java.util.List;

/**
 * Author: val.jzp
 */
@Controller
@RequestMapping("/web/admin/adminUser")
@SuppressWarnings("unused")
public class AdminUserAction {

	private final static Logger logger = LoggerFactory.getLogger(AdminUserAction.class);
	private final static Integer DEFAULT_PAGE_LIST_NUM = 20;

	@Autowired
	private AdminUserBiz adminUserBiz;

	// ******************************************************************************
	// ********************************* CRUD START *********************************
	// ******************************************************************************

	/**
	 * 打开列表页面
	 */
	@RequestMapping("/getAdminUserListPage")
	public ModelAndView getAdminUserListPage() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/adminUser/adminUserList");

		return modelAndView;
	}

	/**
	 * 分页获取JSON数据
	 */
	@RequestMapping("/getAdminUserListJSON")
	@ResponseBody
	public JSONObject getAdminUserListJSON(@RequestParam(value = "page", required = false) String pageNowParam,
	                                       @RequestParam(value = "rows", required = false) String pageSizeParam) {

		Integer pageNow = StringConverters.ToInteger(pageNowParam);
		Integer pageSize = StringConverters.ToInteger(pageSizeParam);

		if (pageNow == null || pageSize == null) {
			pageNow = 1;
			pageSize = DEFAULT_PAGE_LIST_NUM;
		}

		List<AdminUser> adminUserList = adminUserBiz.findList(pageNow, pageSize);
		Long totalNum = adminUserBiz.totalRecord();

		return AdminUserConverter.getJson(adminUserList, totalNum);
	}

	/**
	 * 获取详情页面
	 */
	@RequestMapping("/getAdminUserViewPage")
	public ModelAndView getAdminUserViewPage(@RequestParam(value = "adminUserId", required = false) String adminUserIdParam) {

		Long adminUserId = StringConverters.ToLong(adminUserIdParam);

		AdminUser adminUser = null;
		if (adminUserId != null) {
			adminUser = adminUserBiz.findModel(adminUserId);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/adminUser/adminUserViewPart");
		modelAndView.addObject("adminUser", adminUser);
		return modelAndView;
	}

	/**
	 * 获取编辑页面
	 */
	@RequestMapping("/getAdminUserEditPage")
	public ModelAndView getAdminUserEditPage(@RequestParam(value = "adminUserId", required = false) String adminUserIdParam) {

		Long adminUserId = StringConverters.ToLong(adminUserIdParam);

		AdminUser adminUser = null;
		if (adminUserId != null) {
			adminUser = adminUserBiz.findModel(adminUserId);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/adminUser/adminUserEditPart");
		modelAndView.addObject("adminUser", adminUser);
		return modelAndView;
	}

	/**
	 * 执行提交的新增或修改请求
	 */
	@RequestMapping("/executeAdminUserEdit")
	@ResponseBody
	public String executeAdminUserEdit(AdminUser adminUser) {

		adminUserBiz.addOrUpdate(adminUser);
		return "1";
	}

	/**
	 * 逻辑删除机构用户信息
	 */
	@RequestMapping("/logicRemoveAdminUser")
	@ResponseBody
	public String logicRemoveAdminUser(@RequestParam(value = "adminUserId", required = false) String adminUserIdParam,
	                                   @RequestParam(value = "isFakeDelete", required = false) String isFakeDelete) {

		Long adminUserId = StringConverters.ToLong(adminUserIdParam);

		adminUserBiz.delete(adminUserId);

		return "1";
	}

	// ******************************************************************************
	// ********************************** CRUD END **********************************
	// ******************************************************************************
}