package com.f2b2c.eco.controller.platform.f2b;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.BNoticeModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.market.BNoticeService;
import com.f2b2c.eco.status.StorageStatus;


/**
 * 商品操作
 * 
 * @author jing.liu
 *
 */
@Controller("BNoticeController")
@RequestMapping(value = "/farm/bnotice")
public class BNoticeController {
    /**
	 * 日志记录器
	 */

	@Autowired
	private BNoticeService bNoticeService;
    
	/**
	 * b端公告
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public final String publishBnotice(HttpServletRequest request, HttpServletResponse response) {
		return "farm/bnotice";
    }


	/**
	 * b端公告列表
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public final Page<BNoticeModel> queryByPages(final Pageable pageable,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		if (user != null) {
			Page<BNoticeModel> pages = bNoticeService.findBNoticePage(pageable);
			return pages;
		} else {
			return null;
		}

	}

	/**
	 * 发布b端公告页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public final String publishGoodsPage(HttpServletRequest request, HttpServletResponse response) {
		return "farm/new";
	}

	/**
	 * 发布b端公告
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Map<String, Object>> addBnotice(BNoticeModel bNoticeModel, HttpServletRequest request,
			HttpServletResponse response) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		if (user != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("model", bNoticeModel);
			int num = bNoticeService.insertBnotice(map);
			if (num > 0) {
				return new DataToCResultModel<Map<String, Object>>(200, "发布成功", null);
			} else {
				return new DataToCResultModel<Map<String, Object>>(400, "发布失败", null);
			}
		} else {
			return new DataToCResultModel<Map<String, Object>>(400, "请先登录", null);
		}

	}

	/**
	 * 发布b端公告
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Map<String, Object>> delBnotice(BNoticeModel bNoticeModel, HttpServletRequest request,
			HttpServletResponse response) {
		FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
		if (user != null && bNoticeModel != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("model", bNoticeModel);
			int num = bNoticeService.delBnotice(map);
			if (num > 0) {
				return new DataToCResultModel<Map<String, Object>>(200, "删除成功", null);
			} else {
				return new DataToCResultModel<Map<String, Object>>(400, "删除失败", null);
			}
		} else {
			return new DataToCResultModel<Map<String, Object>>(400, "请先登录", null);
		}
	}


}