package com.f2b2c.eco.controller.market.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.SmsValidCodeModel;
import com.f2b2c.eco.service.market.SmsValidCodeService;
import com.f2b2c.eco.util.CommonUtil;
import com.f2b2c.eco.util.JavaSmsApi;

/**
 * 手机验证码
 * 
 * @author jing.liu
 *
 */
@Controller
@RequestMapping(value = "/api/smsvalidcode")
public class SmsValidCodeController {

	/**
	 * 日志记录器
	 */
	private Logger logger = LoggerFactory.getLogger(SmsValidCodeController.class);
	public final static long tpl_id = 1503298;

	public final static String APIKEY = "3f839541f982b9626fc5eae27a58885a";

	@Autowired
	private SmsValidCodeService smsValidCodeService;

	@RequestMapping(value = "/insert-smsvalid-code")
	@ResponseBody
	public <T> DataToCResultModel<T> insertSmsValidCode(HttpServletRequest request) {
		String mobilePhone = request.getParameter("mobilePhone");
		if (!StringUtils.isNotBlank(mobilePhone)) {
			return new DataToCResultModel<T>(400, "请输入手机号", null);
		}
		int validCode = CommonUtil.nextInt();
		int result = 0;
		SmsValidCodeModel smsValidCode = new SmsValidCodeModel();
		smsValidCode.setValidCode(String.valueOf(validCode));
		smsValidCode.setMobilePhone(mobilePhone);
		SmsValidCodeModel smsCode = smsValidCodeService.queryIsExits(mobilePhone);
		// 如果数据库中存在该手机的验证码则更新，不存在则新增
		if (smsCode != null) {
			result = smsValidCodeService.updateCodeByPhone(smsValidCode);
		} else {
			result = smsValidCodeService.addSmsValidCode(smsValidCode);
		}
		// 将短信验证码发送到手机
		if (result == 1) {
			try {
				String tpl_value = URLEncoder.encode("#code#", "UTF-8") + "="
						+ URLEncoder.encode(String.valueOf(validCode), "UTF-8");
				JavaSmsApi.tplSendSms(APIKEY, tpl_id, tpl_value, mobilePhone);
				return new DataToCResultModel<T>(200, "success", null);
			} catch (IOException e) {
				logger.error(e.getMessage());
				return new DataToCResultModel<T>(500, "failed", null);
			}
		} else {
			return new DataToCResultModel<T>(400, "failed", null);
		}
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/check-invalidcode", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<SmsValidCodeModel> checkInValidCode(HttpServletRequest request) {
		String mobilePhone = request.getParameter("mobilePhone");
		String validCode = request.getParameter("validCode");
		SmsValidCodeModel smsCode = smsValidCodeService.queryIsExits(mobilePhone);
		if (smsCode != null) {
			if (new Date().getTime() - smsCode.getUpdateTime().getTime() > 5 * 60 * 1000) {
				return new DataToCResultModel<SmsValidCodeModel>(400, "您的验证码已失效", null);
			} else {
				if (validCode.equals(smsCode.getValidCode())) {
					return new DataToCResultModel<SmsValidCodeModel>(200, "验证通过", null);
				} else {
					return new DataToCResultModel<SmsValidCodeModel>(400, "验证码错误", null);
				}
			}
		} else {
			return new DataToCResultModel<SmsValidCodeModel>(400, "请发送验证码", null);
		}
	}
}
