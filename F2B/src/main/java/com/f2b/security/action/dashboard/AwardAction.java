package com.f2b.security.action.dashboard;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.action.converter.AwardConverter;
import com.f2b.security.business.biz.AwardBiz;
import com.f2b.security.domain.Award;
import com.f2b.sugar.tools.StringConverters;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/3/24.
 */
@Controller
@RequestMapping("/web/award")
public class AwardAction {
	private final static Logger logger = LoggerFactory.getLogger(RecordAction.class);
	private final static Integer DEFAULT_PAGE_LIST_NUM = 20;

	@Autowired
	private AwardBiz awardBiz;

	/**
	 * 打开列表页面
	 */
	@RequestMapping("/getAwardListPage")
	public ModelAndView getAwardListPage() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/award/awardList");

		return modelAndView;
	}

	/**
	 * 分页获取JSON数据
	 */
	@RequestMapping("/getAwardListJSON")
	@ResponseBody
	public JSONObject getAwardListJSON(@RequestParam(value = "page", required = false) String pageNowParam, @RequestParam(value = "rows", required = false) String pageSizeParam) {

		Integer pageNow = StringConverters.ToInteger(pageNowParam);
		Integer pageSize = StringConverters.ToInteger(pageSizeParam);

		if (pageNow == null || pageSize == null) {
			pageNow = 1;
			pageSize = DEFAULT_PAGE_LIST_NUM;
		}

		List<Award> awardBizList = awardBiz.findList(pageNow, pageSize);
		Long totalNum = awardBiz.totalRecord();

		return AwardConverter.getJson(awardBizList, totalNum);
	}

	/**
	 * 获取详情页面
	 */
	@RequestMapping("/getAwardViewPage")
	public ModelAndView getAwardViewPage(@RequestParam(value = "recordId", required = false) String recordIdParam) {

		Long recordId = StringConverters.ToLong(recordIdParam);

		Award record = null;
		if (recordId != null) {
			record = awardBiz.findModel(recordId);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/record/RecordViewPart");
		modelAndView.addObject("record", record);
		return modelAndView;
	}

	/**
	 * 获取编辑页面
	 */
	@RequestMapping("/getAwardEditPage")
	public ModelAndView getAwardEditPage(@RequestParam(value = "awardId", required = false) String awardIdParam) {

		Long awardId = StringConverters.ToLong(awardIdParam);

		Award award = null;
		if (awardId != null) {
			award = awardBiz.findModel(awardId);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/award/awardEidtPart");
		modelAndView.addObject("award", award);
		return modelAndView;
	}

	/**
	 * 执行提交的新增或修改请求
	 */
	@RequestMapping("/executeAwardEdit")
	@ResponseBody
	public String executeAwardEdit(Award record) {
		awardBiz.addOrUpdate(record);
		return "1";
	}

	/**
	 * 逻辑删除机构用户信息
	 */
	@RequestMapping("/logicRemoveAward")
	@ResponseBody
	public String logicRemoveAward(@RequestParam(value = "awardId", required = false) String awardIdParam, @RequestParam(value = "isFakeDelete", required = false) String isFakeDelete) {
		Long awardId = StringConverters.ToLong(awardIdParam);
		awardBiz.delete(awardId);
		return "1";
	}

}
