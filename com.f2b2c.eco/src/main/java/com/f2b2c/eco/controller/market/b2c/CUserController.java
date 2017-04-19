package com.f2b2c.eco.controller.market.b2c;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.SmsValidCodeModel;
import com.f2b2c.eco.model.platform.BConfigModel;
import com.f2b2c.eco.service.market.CDeliveryAddressService;
import com.f2b2c.eco.service.market.CUserService;
import com.f2b2c.eco.service.market.SmsValidCodeService;
import com.f2b2c.eco.service.platform.BConfigService;
import com.f2b2c.eco.util.Base64Util;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.Pbkdf2Encryption;
import com.f2b2c.eco.util.PropertiesUtil;

/**
 * C端用户相关操作
 * 
 * @author jacob.shen
 *
 */
@Controller("/CUserController")
@RequestMapping(value = "/customer/cuser")
public class CUserController {

	private Logger logger = LoggerFactory.getLogger(CUserController.class);

	@Autowired
	private CUserService cUserService;
	
	@Autowired
	private SmsValidCodeService smsValidCodeService;
	
	@Autowired
	private CDeliveryAddressService cDeliveryAddressService;
	
    @Autowired
    private BConfigService bConfigService;
	
	/**
     * C端用户注册
     * 
     * @return
     */
	@RequestMapping(value = "/insert-cuser",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> insertCUser(HttpServletRequest request, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("phone") String phone, @RequestParam("smsCode") String smsCode) {
        // 登录帐号为手机号码，需要做一个唯一性的校验
		 CUserModel cUserModel = cUserService.getCUserByPhone(phone);
//		 BUserModel bUserModel = bUserService.getBUserByPhone(phone);
		if(null != cUserModel){// || null != bUserModel){
            return new DataToCResultModel<Object>(400, "该手机号码已被注册", null);
		}else{
			SmsValidCodeModel code = smsValidCodeService.queryIsExits(phone);
            // 获取短信验证码
			if (code == null) {
                return new DataToCResultModel<Object>(400, "请获取短信验证码", null);
			} else {
				if (code.getUpdateTime() != null
						&& new Date().getTime() - code.getUpdateTime().getTime() > 5 * 60 * 1000) {
                    return new DataToCResultModel<Object>(400, "您的验证码已失效，请重新获取", null);
				} else {
					if (!smsCode.equals(code.getValidCode())) {
                        return new DataToCResultModel<Object>(400, "验证码错误", null);
					} 
				}
				if (confirmPassword != null && password != null && !password.equals(confirmPassword)) {
                    return new DataToCResultModel<Object>(400, "密码不一致，请检查", null);
				}
				CUserModel cUser = new CUserModel();
				try {
					cUser.setPassword(Pbkdf2Encryption.encode(password));
				} catch (NoSuchAlgorithmException e) {
					logger.error(e.getMessage());
				} catch (InvalidKeySpecException e) {
					logger.error(e.getMessage());
				}
				cUser.setPhone(phone);
                String nickName = "用户" + phone.substring(0, 3) + "****" + phone.substring(6, phone.length());
				cUser.setNickName(nickName);
				cUserService.insertCUser(cUser);
				BConfigModel bConfigModel = new BConfigModel();
				bConfigModel.setUserId(cUser.getId());
				bConfigService.addUserConfig(bConfigModel);
                logger.info("手机号为{}的用户注册成功", phone);
                return new DataToCResultModel<Object>(200, "注册成功", JsonUtil.parseToNoEmptyStr(cUser));
			}
		}
	}

	/**
     * C端用户登录
     * 
     * @param request
     * @return
     */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> login(HttpServletRequest request) {
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
            return new DataToCResultModel<Object>(400, "请输入手机号和密码", null);
		} else {
			CUserModel cUser = new CUserModel();
			cUser = cUserService.login(phone.trim());
			SmsValidCodeModel smsCode = smsValidCodeService.queryIsExits(phone);
			if(cUser != null){
                // 密码匹配成功
				if(Pbkdf2Encryption.checkPassword(password,cUser.getPassword())){
					request.getSession().setAttribute("cUser", cUser);
                    return new DataToCResultModel<Object>(200, "登录成功", JsonUtil.parseToNoEmptyStr(cUser));
				}
                // 验证码不为空的时候
				if (smsCode != null) {
                    // 如果验证码已经超时 直接返回错误
					if (new Date().getTime() - smsCode.getUpdateTime().getTime() > 5 * 60 * 1000) {
                        return new DataToCResultModel<Object>(400, "密码错误", null);
					} else if (password.equals(smsCode.getValidCode())) {
                        return new DataToCResultModel<Object>(200, "登录成功", JsonUtil.parseToNoEmptyStr(cUser));
					}
				}
                return new DataToCResultModel<Object>(400, "密码错误", null);
			} else {
				SmsValidCodeModel code = smsValidCodeService.queryIsExits(phone);
                // 获取短信验证码
				if (code == null) {
                    return new DataToCResultModel<Object>(400, "请获取短信验证码", null);
				} else {
					if (code.getUpdateTime() != null
							&& new Date().getTime() - code.getUpdateTime().getTime() > 5 * 60 * 1000) {
                        return new DataToCResultModel<Object>(400, "您的验证码已失效，请重新获取", null);
					}
					if (!password.equals(code.getValidCode())) {
                        return new DataToCResultModel<Object>(400, "验证码错误", null);
					}
					CUserModel newCUser = new CUserModel();
					newCUser.setPhone(phone);
					newCUser.setAccountName(phone);
					newCUser.setRegisterTime(new Date());
					newCUser.setUpdatedTime(new Date());
					cUserService.insertCUser(newCUser);
					BConfigModel bConfigModel = new BConfigModel();
					bConfigModel.setUserId(newCUser.getId());
					bConfigService.addUserConfig(bConfigModel);
                    logger.info("手机号为{}的用户注册成功", phone);
                    return new DataToCResultModel<Object>(200, "登录成功", JsonUtil.parseToNoEmptyStr(newCUser));
				}
			}
		}
	}

	/**
     * 获取个人信息
     * 
     * @param request
     * @return
     */
	@RequestMapping(value = "/get-cuser-info",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> getCUserInfo(MultipartHttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
            return new DataToCResultModel<Object>(400, "获取个人信息失败", null);
		} else {
			CUserModel cUser = cUserService.getCUserInfo(Integer.valueOf(id));
			if (cUser != null) {
				if (StringUtils.isNotBlank(cUser.getPicUrl())) {
					String picUrl = PropertiesUtil.getValue("path") + cUser.getPicUrl();
					cUser.setPicUrl(picUrl);
				}
			}
			if (cUser == null) {
                return new DataToCResultModel<Object>(400, "无此人信息", null);
			} else {
                return new DataToCResultModel<Object>(200, "获取个人信息成功", JsonUtil.parseToNoEmptyStr(cUser));
			}
		}
	}

	/**
     * 更新个人信息
     * 
     * @param request
     * @return
     */
	@RequestMapping(value = "/update-cuser",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> updateCUser(HttpServletRequest request) {
		String picBase64 = request.getParameter("picture");
        String nickName = request.getParameter("nickName"); // 昵称
        String email = request.getParameter("email"); // 邮箱
        String phone = request.getParameter("phone"); // 手机
		String id = request.getParameter("id"); // cUser id
		if (StringUtils.isBlank(id)) {
            return new DataToCResultModel<Object>(400, "id为空", null);
		}
		CUserModel cUser = cUserService.getCUserInfo(Integer.valueOf(id));
		cUser.setNickName(nickName);
		cUser.setEmail(email);
		cUser.setPhone(phone);
		String picUrl = null;
		if (StringUtils.isNotBlank(picBase64)) {
			picUrl = Base64Util.GenerateImage(picBase64, PropertiesUtil.getValue("fileSavePath"), "cUser");
			cUser.setPicUrl(picUrl);
		}
		int result = cUserService.updateCUser(cUser);
		if (result > 0) {
			if(StringUtils.isNoneBlank(cUser.getPicUrl())){
				cUser.setPicUrl(PropertiesUtil.getValue("path") + cUser.getPicUrl());
			}
            return new DataToCResultModel<Object>(200, "修改个人信息成功", JsonUtil.parseToNoEmptyStr(cUser));
		} else {
            return new DataToCResultModel<Object>(400, "修改个人信息失败", JsonUtil.parseToNoEmptyStr(cUser));
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<CUserModel> updatePassword(HttpServletRequest request) {
		String mobilePhone = request.getParameter("mobilePhone");
		String password = request.getParameter("newPassword");
		if (StringUtils.isNoneBlank(mobilePhone) && StringUtils.isNoneBlank(password)) {
			CUserModel cUser = new CUserModel();
			cUser.setPhone(mobilePhone);
			try {
				cUser.setPassword(Pbkdf2Encryption.encode(password));
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
			} catch (InvalidKeySpecException e) {
				logger.error(e.getMessage());
			}
			int result = cUserService.updatePassword(cUser);
			if (result > 0) {
                return new DataToCResultModel<CUserModel>(200, "修改成功", null);
			} else {
                return new DataToCResultModel<CUserModel>(400, "修改失败", null);
			}
		} else {
            return new DataToCResultModel<CUserModel>(400, "请求参数不能为空", null);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getpersoninfo",method=RequestMethod.POST)
	@ResponseBody
	public DataToCResultModel<Object> getPersonInfo(HttpServletRequest request){
		String userId = request.getParameter("userId");
		if(StringUtils.isNotBlank(userId)){
			CUserModel cUser = new CUserModel();
			cUser = cUserService.queryInfoById(Integer.valueOf(userId));
			if(cUser != null){
				if (StringUtils.isNotBlank(cUser.getPicUrl())) {
					String picUrl = PropertiesUtil.getValue("path") + cUser.getPicUrl();
					cUser.setPicUrl(picUrl);
				}
				List<CDeliveryAddressModel> list = new ArrayList<CDeliveryAddressModel>();
				list = cDeliveryAddressService.getUserDeliveryAddresses(Integer.valueOf(userId));
				if(list != null && list.size() > 0){
					cUser.setList(list);
				}
				return new DataToCResultModel<Object>(200,"success",JsonUtil.parseToNoEmptyStr(cUser));
			}else{
                return new DataToCResultModel<Object>(400, "用户不存在", null);
			}
		}else{
            return new DataToCResultModel<Object>(400, "请求参数不能为空", null);
		}
	}

}
