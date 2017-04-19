package com.f2b2c.eco.controller.platform.f2b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.netbeans.lib.cvsclient.commandLine.command.update;
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

import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FHelpModel;
import com.f2b2c.eco.service.platform.BShopService;
import com.f2b2c.eco.service.platform.FHelpService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.MessageType;
import com.f2b2c.eco.util.PropertiesUtil;

import jodd.util.StringUtil;

/**
 * 帮助管理部分
 * 
 * @author mozzie.chu
 *
 */
@Controller(value = "BHelpContriller")
@RequestMapping(value = "/farm/bhelp")
public class FHelpController {

	@Autowired
	private FHelpService fHelpService;
	
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(FHelpController.class);
	
	/**
	 * PC - 帮助中心
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/helpcenter", method = RequestMethod.GET)
	public final String helpCenter(HttpServletRequest request, HttpServletResponse response) {
		return "platform/help/fHelpCenter";
	}
	
	/**
	 * 帮助中心 - datatable
	 * @param pageable
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querypage", method = RequestMethod.GET)
	@ResponseBody
	public final Page<FHelpModel> queryDatatable(final Pageable pageable, HttpServletRequest request,
            HttpServletResponse response) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String pic = request.getParameter("pic");
		String style = request.getParameter("style");
		paramMap.put("question", question);
		paramMap.put("answer", answer);
		paramMap.put("type", type);
		paramMap.put("status", status);
		paramMap.put("pic", pic);
		paramMap.put("style", style);
		Page<FHelpModel> pages = fHelpService.findWithPagination(pageable, paramMap);
		//List<FHelpModel> list = pages.getContent();
		return pages;		
	}
	
	/**
	 *  PC - 购物咨询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shoppingadvice", method = RequestMethod.GET)
	public final String shoppingAdvice(HttpServletRequest request, HttpServletResponse response) {
		return "platform/help/fShoppingAdvice";	
	}
	
	/**
	 * PC - 支付问题
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/paymentproblem", method = RequestMethod.GET)
	public final String PaymentProblem(HttpServletRequest request, HttpServletResponse response) {
		return "platform/help/fPaymentProblem";
	}
	
	/**
	 * PC - 物流与售后
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logisticsandcustomer", method = RequestMethod.GET)
	public final String LogisticsAndCustomer(HttpServletRequest request, HttpServletResponse response) {
		return "platform/help/fLogisticsAndCustomer";	
	}
	
	/**
	 * PC - 其他问题
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/otherproblem", method = RequestMethod.GET)
	public final String OtherProblem(HttpServletRequest request, HttpServletResponse response) {
		return "platform/help/fOtherProblem";	
	}
	
	/**
	 * PC - 问答详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qanda", method = RequestMethod.GET)
	public final String QandADetails(HttpServletRequest request, HttpServletResponse response) {
		return "platform/help/fQAndA";
	}
	
	/**
	 * 添加帮助
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	private DataToCResultModel<Map<String, Object>> insert(FHelpModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", model);
		int num = fHelpService.insert(model);
		if(num >0) {
			return new DataToCResultModel<Map<String, Object>>(200, "success", null);
		}else{
			return new DataToCResultModel<Map<String, Object>>(400, "error", null);
		}
		
	}
	
	/**
	 * 删除帮助
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	private ApiResultModel<String> delete(HttpServletRequest request) {
		ApiResultModel<String> result = new ApiResultModel<String>();
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			int insert = fHelpService.delete(id);
			
			try {
				if (insert == 0) {
					result.setStatus(400);
					result.setMsg("该条帮助---删除失败！");
					return result;
				}
			} catch (Exception e) {
				logger.error("删除帮助时出现异常，异常信息为：" + e.toString());
				result.setStatus(400);
				result.setMsg("该条帮助---删除失败！");
				return result;
			}
			
		} else {
			result.setStatus(400);
			result.setMsg("该条帮助---删除失败！");
			return result;
		}
		result.setStatus(200);
        result.setMsg("该条帮助---删除成功。");
		return result;
		
	}
	
	/**
	 * 修改帮助
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	private DTOResult update(HttpServletRequest request, HttpServletResponse response, FHelpModel model) {
		DTOResult result = new DTOResult();
		int update = fHelpService.update(model);
		if (update == 1) {
			result.setType(MessageType.success);
			result.setMessage("修改该条帮助成功");
		} else {
			result.setType(MessageType.error);
			result.setMessage("修改该条帮助失败");
		}
		return result;
	}
	
	/**
	 * 查看
	 * @param request
	 * @param response
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/show", method = RequestMethod.GET)
	private final String show(HttpServletRequest request, HttpServletResponse response, Integer id) {
		FHelpModel fHelpModel = fHelpService.queryHelpById(Integer.valueOf(id));
		request.setAttribute("data", fHelpModel);
		return "platform/help/show";
		
	}
	
	/**
	 * 修改页
	 * @param request
	 * @param response
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/show-update", method = RequestMethod.GET)
	private final String showupdate(HttpServletRequest request, HttpServletResponse response, Integer id) {
		FHelpModel fHelpModel = fHelpService.queryHelpById(Integer.valueOf(id));
		request.setAttribute("data", fHelpModel);
		return "platform/help/update";
		
	}
}
