package com.f2b2c.eco.controller.market.api;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.FToBUserModel;
import com.f2b2c.eco.model.market.SmsValidCodeModel;
import com.f2b2c.eco.model.platform.FAuthCodeModel;
import com.f2b2c.eco.service.market.BShopInfoService;
import com.f2b2c.eco.service.market.BUserService;
import com.f2b2c.eco.service.market.SmsValidCodeService;
import com.f2b2c.eco.service.platform.FAuthService;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.Pbkdf2Encryption;

/**
 * 
 * @author jing.liu
 *
 */
@Controller(value = "bUserController")
@RequestMapping(value = "api/buser")
public class BUserController {

    @Autowired
    private BUserService bUserService;

    @Autowired
    private SmsValidCodeService smsValidCodeService;

    @Autowired
    private BShopInfoService bShopInfoService;

    @Autowired
    private FAuthService fAuthService;

    /**
     * 注册用户
     * 
     * @param request
     * @param bUser
     */
    @RequestMapping(value = "/insertuser", method = RequestMethod.POST)
    @ResponseBody
    public <T> DataToCResultModel<T> insertUser(HttpServletRequest request, BUserModel bUser) {
        // 登录帐号为手机号码，需要做一个唯一性的校验
        // CUserModel cUserModel =
        // cUserService.getCUserByPhone(bUser.getPhone());
        BUserModel bUserModel = bUserService.getBUserByPhone(bUser.getPhone());
        if (null != bUserModel) {// || null != cUserModel){
            return new DataToCResultModel<T>(400, "该手机号码已被注册", null);
        } else {
            SmsValidCodeModel smsCode = smsValidCodeService.queryIsExits(bUser.getPhone());
            if (smsCode != null) {
                if (smsCode.getUpdateTime().getTime() - new Date().getTime() > 5 * 60 * 1000) {
                    return new DataToCResultModel<T>(400, "您的验证码已失效", null);
                } else {
                    if (bUser.getValidCode().equals(smsCode.getValidCode())) {
                        // 验证通过后执下面的逻辑,去验证授权码
                        FAuthCodeModel fAuthCode = fAuthService.queryCodeIsUser(bUser.getAuthCode());
                        // 如果FAuthCodeModel对象不为空，说明授权码可用，执行下面的逻辑
                        if (fAuthCode != null) {
                            // 保存注册的信息
                            bUser.setAccountName(bUser.getPhone());// 将手机号码复制到登录帐号里面去
                            bUser.setCreatedUser(fAuthCode.getCreatedUser());
                            bUser.setUpdatedUser(fAuthCode.getCreatedUser());
                            bUser.setIsActive(0);// 将用户设置为激活
                            try {
                                bUser.setPassword(Pbkdf2Encryption.encode(bUser.getPassword()));
                            } catch (NoSuchAlgorithmException e) {

                            } catch (InvalidKeySpecException e) {

                            }

                            int result = bUserService.insertUser(bUser);
                            if (result > 0) {
                                return new DataToCResultModel<T>(200, "注册成功", null);
                            } else {
                                return new DataToCResultModel<T>(400, "注册失败", null);
                            }
                        } else {
                            return new DataToCResultModel<T>(400, "授权码不可用", null);
                        }
                    } else {
                        return new DataToCResultModel<T>(400, "验证码错误", null);
                    }
                }
            } else {
                return new DataToCResultModel<T>(400, "请发送验证码", null);
            }
        }
    }

    /**
     * 用户登录验证用户名密码
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> login(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        // 先根据用户名查询有没有该用户
        map.put("userName", userName);
        BUserModel bUser = bUserService.queryUserIsExist(map);
        // 如果用户存在，验证密码
        if (bUser != null) {
            if (Pbkdf2Encryption.checkPassword(password, bUser.getPassword())) {
                Integer isEnable = bShopInfoService.selectEnableByUserId(bUser.getId());
                if (isEnable == 1) {
                    return new DataToCResultModel<Object>(200, "登录成功", JsonUtil.parseToNoEmptyStr(bUser));
                } else {
                    return new DataToCResultModel<Object>(400, "帐号被禁用", null);
                }
            } else {
                return new DataToCResultModel<Object>(400, "密码错误", null);
            }
        } else {
            return new DataToCResultModel<Object>(400, "用户不存在", null);
        }
    }

    /**
     * 修改密码
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
    @ResponseBody
    public <T> DataToCResultModel<T> updatePassword(HttpServletRequest request) {
        String mobilePhone = request.getParameter("mobilePhone");
        String newPassWord = request.getParameter("password");
        BUserModel bUser = new BUserModel();
        bUser.setPhone(mobilePhone);
        try {
            bUser.setPassword(Pbkdf2Encryption.encode(newPassWord));
        } catch (NoSuchAlgorithmException e) {

        } catch (InvalidKeySpecException e) {

        }
        int result = bUserService.updatePassword(bUser);
        if (result > 0) {
            return new DataToCResultModel<T>(200, "修改成功", null);
        } else {
            return new DataToCResultModel<T>(400, "修改失败", null);
        }
    }

    /**
     * 个人中心的所有信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/querybuserbyuserId", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<FToBUserModel> queryBUserByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId != null && !userId.equals("")) {
            FToBUserModel fToBUser = bUserService.queryBUserByUserId(Integer.valueOf(userId), new Date());
            if (fToBUser != null) {
                if (fToBUser.getProvinceCode().equals("110000") || fToBUser.getProvinceCode().equals("120000")
                        || fToBUser.getProvinceCode().equals("310000") || fToBUser.getProvinceCode().equals("500000")
                        || fToBUser.getProvinceCode().equals("810000") || fToBUser.getProvinceCode().equals("820000")) {
                    fToBUser.setAddress(fToBUser.getCityName() + fToBUser.getAreaName() + fToBUser.getAddress());
                } else {
                    fToBUser.setAddress(fToBUser.getProvinceName() + fToBUser.getCityName() + fToBUser.getAreaName()
                            + fToBUser.getAddress());
                }
                return new DataToCResultModel<FToBUserModel>(200, "success", fToBUser);
            } else {
                return new DataToCResultModel<FToBUserModel>(200, "用户不存在", null);
            }
        } else {
            return new DataToCResultModel<FToBUserModel>(400, "请求参数不能为空", null);
        }
    }
}
