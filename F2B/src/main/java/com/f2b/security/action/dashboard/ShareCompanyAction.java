/**
* @ClassName: ShareRecordAction
* @Description: 其他分享平台
* @author Jacob Shen
* @date Oct 25, 2016 9:27:46 AM
*/
package com.f2b.security.action.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/web/share/company")
public class ShareCompanyAction {
	private static final Logger logger = LoggerFactory.getLogger(ShareCompanyAction.class);
	
	/**
	 * 分佣记录查询
	 * @param request
	 * @param shareOpenId
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/pages/shareCompany/login");
		return mv;
	}
	
	/**
	 * 分页获取JSON数据
	 */
	@RequestMapping("/g")
	@ResponseBody
	public JSONObject getShareRecordListJSON(@RequestParam(value = "page", required = false) String pageNowParam, @RequestParam(value = "rows", required = false) String pageSizeParam) {

		return null;
	}
}
