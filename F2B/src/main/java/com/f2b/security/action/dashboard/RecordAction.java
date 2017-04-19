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

import com.f2b.security.action.converter.RecordConverter;
import com.f2b.security.business.biz.RecordBiz;
import com.f2b.security.business.biz.ShareRecordBiz;
import com.f2b.security.domain.Record;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.PropertiesUtil;
import com.f2b.sugar.tools.StringConverters;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/3/24.
 */
@Controller
@RequestMapping("/web/record")
public class RecordAction {
	private final static Logger logger = LoggerFactory.getLogger(RecordAction.class);
	private final static Integer DEFAULT_PAGE_LIST_NUM = 20;

	@Autowired
	private RecordBiz recordBiz;
	
	@Autowired
	private ShareRecordBiz shareRecordBiz;

	/**
	 * 发放奖品
	 * 
	 * @return
	 */
	@RequestMapping("/sendRedPack")
	@ResponseBody
	public String sendRedPack(String openId, Integer award, Long recordId) {
		recordBiz.payMoney(openId, award);
		Record record = recordBiz.findModel(recordId);
		record.setDrawStatus(1);
		recordBiz.addOrUpdate(record);
		logger.info("确认无误给{}发放奖品：{}", openId, award);
		return "success";
	}
	
	/**
	 * 发放佣金
	 * 
	 * @return
	 */
	@RequestMapping("/sendProfit")
	@ResponseBody
	public void sendProfit(String shareOpenId, Integer number, Long shareRecordId) {
		ShareRecord record = shareRecordBiz.findModel(shareRecordId);
		recordBiz.payProfit(record);
		record.setSendRedPack(1);
		shareRecordBiz.addOrUpdate(record);
		logger.info("确认无误给{}发放佣金：{}", shareOpenId, number * Integer.valueOf(PropertiesUtil.getValue("per_profit")));
	}

	/**
	 * 打开列表页面
	 */
	@RequestMapping("/getRecordListPage")
	public ModelAndView getRecordListPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/record/recordList");
		List<Record> recordList = recordBiz.findList(0, 0);
		Integer totalReward = 0;
		for (Record record : recordList) {
			if (record.getDrawStatus() == 1) {
				totalReward += Integer.valueOf(record.getAward());
			}
		}
		modelAndView.addObject("totalReward", totalReward);
		return modelAndView;
	}

	/**
	 * 分页获取JSON数据
	 */
	@RequestMapping("/getRecordListJSON")
	@ResponseBody
	public JSONObject getRecordListJSON(@RequestParam(value = "page", required = false) String pageNowParam, @RequestParam(value = "rows", required = false) String pageSizeParam) {

		Integer pageNow = StringConverters.ToInteger(pageNowParam);
		Integer pageSize = StringConverters.ToInteger(pageSizeParam);

		if (pageNow == null || pageSize == null) {
			pageNow = 1;
			pageSize = DEFAULT_PAGE_LIST_NUM;
		}

		List<Record> recordList = recordBiz.findList(pageNow, pageSize);
		Long totalNum = recordBiz.totalRecord();

		return RecordConverter.getJson(recordList, totalNum);
	}

	/**
	 * 获取详情页面
	 */
	@RequestMapping("/getRecordViewPage")
	public ModelAndView getRecordViewPage(@RequestParam(value = "recordId", required = false) String recordIdParam) {

		Long recordId = StringConverters.ToLong(recordIdParam);

		Record record = null;
		if (recordId != null) {
			record = recordBiz.findModel(recordId);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/record/RecordViewPart");
		modelAndView.addObject("record", record);
		return modelAndView;
	}

	/**
	 * 获取编辑页面
	 */
	@RequestMapping("/getRecordEditPage")
	public ModelAndView getRecordEditPage(@RequestParam(value = "recordId", required = false) String recordIdParam) {

		Long recordId = StringConverters.ToLong(recordIdParam);

		Record record = null;
		if (recordId != null) {
			record = recordBiz.findModel(recordId);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/record/recordEidtPart");
		modelAndView.addObject("record", record);
		return modelAndView;
	}

	/**
	 * 执行提交的新增或修改请求
	 */
	@RequestMapping("/executeRecordEdit")
	@ResponseBody
	public String executeRecordEdit(Record record) {
		recordBiz.addOrUpdate(record);
		return "1";
	}

	/**
	 * 逻辑删除机构用户信息
	 */
	@RequestMapping("/logicRemoveRecord")
	@ResponseBody
	public String logicRemoveRecord(@RequestParam(value = "recordId", required = false) String recordIdParam, @RequestParam(value = "isFakeDelete", required = false) String isFakeDelete) {

		Long recordId = StringConverters.ToLong(recordIdParam);

		recordBiz.delete(recordId);

		return "1";
	}

	// ******************************************************************************
	// ********************************** CRUD END
	// **********************************
	// ******************************************************************************

}
