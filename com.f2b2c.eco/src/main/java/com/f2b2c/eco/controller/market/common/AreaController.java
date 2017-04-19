package com.f2b2c.eco.controller.market.common;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.apimodel.Area;
import com.f2b2c.eco.apimodel.City;
import com.f2b2c.eco.apimodel.Province;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BNoticeModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.service.market.BNoticeService;
import com.f2b2c.eco.util.AreaUtil;
import com.f2b2c.eco.util.JsonUtil;
/**
 * 
 * @author color.wu
 *
 */
@Controller(value="areaController")
@RequestMapping(value="/common")
public class AreaController {

	/**
	 * 日志服务类
	 */
	private final Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private BNoticeService bNoticeService;
	
	/**
	 * 获取身份信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getprovinces", method = RequestMethod.POST)
	@ResponseBody
	public ApiResultModel<List<Province>> getProvinces(HttpServletRequest request, HttpServletResponse response) {
		ApiResultModel<List<Province>> apiResultModel = new ApiResultModel<List<Province>>();

		try {
			List<Province> provinces = AreaUtil.getProvinces();
			apiResultModel.setStatus(200);
			apiResultModel.setMsg("success");
			apiResultModel.setData(provinces);
		} catch (Exception e) {
			logger.error("Get province list failed, Excepiton message is " + e.getMessage());
			apiResultModel.setMsg("failed");
			apiResultModel.setStatus(400);
		}

		return apiResultModel;
	}

	/**
	 * 获取城市信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getcitys", method = RequestMethod.POST)
	@ResponseBody
	public ApiResultModel<List<City>> getCitys(HttpServletRequest request, HttpServletResponse response,
			String provinceid) {
		ApiResultModel<List<City>> apiResultModel = new ApiResultModel<List<City>>();
		try {
			List<City> citys = AreaUtil.getCitysByProvinceCode(Integer.valueOf(provinceid));
			apiResultModel.setStatus(200);
			apiResultModel.setMsg("success");
			apiResultModel.setData(citys);
		} catch (Exception e) {
			logger.error("Get city list failed, Excepiton message is " + e.getMessage());
			apiResultModel.setMsg("failed");
			apiResultModel.setStatus(400);
		}

		return apiResultModel;
	}

	/**
	 * 获取地域信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getareas", method = RequestMethod.POST)
	@ResponseBody
	public ApiResultModel<List<Area>> getAreas(HttpServletRequest request, HttpServletResponse response,
			String cityid) {
		ApiResultModel<List<Area>> apiResultModel = new ApiResultModel<List<Area>>();

		try {
			List<Area> areas = AreaUtil.getAreaByCityCode(Integer.valueOf(cityid));
			apiResultModel.setStatus(200);
			apiResultModel.setMsg("success");
			apiResultModel.setData(areas);
		} catch (Exception e) {
			logger.error("Get area list failed, Exception message is " + e.getMessage());
			apiResultModel.setMsg("failed");
			apiResultModel.setStatus(400);
		}

		return apiResultModel;
	}

	/**
	 * 查询公告列表(分页)
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/query-notice-page", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> queryGoodsCommentByGoodsNo(Integer page, Integer pageSize,
			HttpServletRequest request) {
		try {
			if (page != null && !page.equals("") && pageSize != null && !pageSize.equals("")) {
				// 查询总记录条数
				int totalCount = bNoticeService.findAllNoticeCount();
				// 分页对象构造
				Page p = new Page(totalCount, page, pageSize);
				Map<String, Object> map = new HashMap<>();
				map.put("start", p.getStart());
				map.put("length", pageSize);
				List<BNoticeModel> rows = bNoticeService.findAllNoticePage(p.getStart(), pageSize);
				for (int i = 0; i < rows.size(); i++) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String date = sdf.format(rows.get(i).getCreateTime());
					rows.get(i).setCreateTime(null);
					rows.get(i).setCreateTimeStr(date);
				}
				JsonUtil jsonUtil = new JsonUtil();
				p.setRows(rows);
				return new DataToCResultModel<Object>(200, "success", jsonUtil.parseToNoEmptyStr(p));
			} else {
				return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new DataToCResultModel<Object>(400, "获取通知列表失败", null);
		}
	}
}
