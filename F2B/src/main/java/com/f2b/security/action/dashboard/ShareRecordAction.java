/**
* @ClassName: ShareRecordAction
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Oct 25, 2016 9:27:46 AM
*/
package com.f2b.security.action.dashboard;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.f2b.security.action.converter.ShareRecordConverter;
import com.f2b.security.business.biz.ShareRecordBiz;
import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.PropertiesUtil;
import com.f2b.sugar.tools.SignUtil;
import com.f2b.sugar.tools.StringConverters;
import com.f2b.sugar.tools.pay.http.UrlHelp;
import com.f2b.sugar.wxlib.ParamesAPI.AccessToken;
import com.f2b.sugar.wxlib.ParamesAPI.ParamesAPI;
import com.f2b.sugar.wxlib.ParamesAPI.WeixinUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/web/share")
public class ShareRecordAction {
	private static final Logger logger = LoggerFactory.getLogger(ShareRecordAction.class);
	@Autowired
	private ShareRecordBiz shareRecordBiz;

	/**
	 * 分佣记录查询
	 * @param request
	 * @param shareOpenId
	 * @return
	 */
	@RequestMapping("/getShareRecord")
	public ModelAndView getShareRecord(HttpServletRequest request, String shareOpenId) {
		// 每箱苹果可获得的分润
		int profit = Integer.valueOf(PropertiesUtil.getValue("per_profit"));
		ModelAndView mv = new ModelAndView();
		logger.info("分享人的openid：{}",shareOpenId);
		List<ShareRecord> list = shareRecordBiz.getMyShare(shareOpenId);
		// 总获利，已发放利润，未发放利润
		int total = 0,sended = 0,notSend = 0;
		for (ShareRecord record : list) {
			total += record.getNumber() * Integer.valueOf(profit);
			if (record.getSendRedPack() == 0) {
				notSend += record.getNumber() * Integer.valueOf(profit);
			} else {
				sended += record.getNumber() * Integer.valueOf(profit);
			}
			record.setNickname(Base64.getFromBase64(record.getNickname()));
		}
		mv.addObject("list", list);
		mv.addObject("total", total);
		mv.addObject("sended", sended);
		mv.addObject("notSend", notSend);
		mv.addObject("openid", shareOpenId);
		
		// 分享参数
		AccessToken accessToken = WeixinUtil.getAccessToken(ParamesAPI.appId, ParamesAPI.secret);
		String jsapi_ticket = WeixinUtil.getJsapiTicket(accessToken.getToken(), request);
		String url2 = UrlHelp.getFullRequestURL(request);
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url2);

		mv.addObject("timestamp", ret.get("timestamp"));
		mv.addObject("nonceStr", ret.get("nonceStr"));
		mv.addObject("signature", ret.get("signature"));
		
		mv.setViewName("/pages/shareRecord/shareRecord");
		return mv;
	}
	
	/**
	 * 打开列表页面
	 */
	@RequestMapping("/getShareRecordListPage")
	public ModelAndView getShareRecordListPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/shareRecord/shareRecordList");
		List<ShareRecord> recordList = shareRecordBiz.findList(0, 0);
		Integer totalReward = 0;
		for (ShareRecord record : recordList) {
			if (record.getSendRedPack() == 1) {
				totalReward += Integer.valueOf(record.getNumber() * Integer.valueOf(PropertiesUtil.getValue("per_profit")));
			}
		}
		modelAndView.addObject("totalReward", totalReward);
		return modelAndView;
	}
	
	/**
	 * 打开列表页面,对外
	 */
	@RequestMapping("/getShareRecordListPageOuter")
	public ModelAndView getShareRecordListPageOuter() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pages/shareRecord/shareRecordList2");
		List<ShareRecord> recordList = shareRecordBiz.findList(0, 0);
		Integer totalReward = 0;
		for (ShareRecord record : recordList) {
			if (record.getSendRedPack() == 1) {
				totalReward += Integer.valueOf(record.getNumber() * Integer.valueOf(PropertiesUtil.getValue("per_profit")));
			}
		}
		modelAndView.addObject("totalReward", totalReward);
		return modelAndView;
	}

	/**
	 * 分页获取JSON数据
	 */
	@RequestMapping("/getShareRecordListJSON")
	@ResponseBody
	public JSONObject getShareRecordListJSON(@RequestParam(value = "page", required = false) String pageNowParam, @RequestParam(value = "rows", required = false) String pageSizeParam) {

		Integer pageNow = StringConverters.ToInteger(pageNowParam);
		Integer pageSize = StringConverters.ToInteger(pageSizeParam);

		if (pageNow == null || pageSize == null) {
			pageNow = 1;
			pageSize = 20;
		}

		List<ShareRecord> recordList = shareRecordBiz.findList(pageNow, pageSize);
		Long totalNum = shareRecordBiz.totalRecord();

		return ShareRecordConverter.getJson(recordList, totalNum);
	}
	
	/**
	 * 导出线下支付
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/ShareRecordExcl1", method = RequestMethod.GET)
	@ResponseBody
	public void getShareRecordExcl1(HttpServletResponse response) {
		shareRecordBiz.getShareRecordExcl1(response);

	}

	/**
	 * 导出全部正常分享的
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/ShareRecordExcl", method = RequestMethod.GET)
	@ResponseBody
	public void getShareRecordExcl(HttpServletResponse response) {
		shareRecordBiz.getShareRecordExcl(response);

	}
}
