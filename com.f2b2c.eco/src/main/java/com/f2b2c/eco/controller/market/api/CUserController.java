package com.f2b2c.eco.controller.market.api;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.bean.CUserBean;
import com.f2b2c.eco.model.market.BUserModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.PageResultModel;
import com.f2b2c.eco.model.market.SmsValidCodeModel;
import com.f2b2c.eco.model.platform.BConfigModel;
import com.f2b2c.eco.service.market.CUserService;
import com.f2b2c.eco.service.market.SmsValidCodeService;
import com.f2b2c.eco.service.platform.BConfigService;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.LogoUrlUtil;
import com.f2b2c.eco.util.PageUtil;
import com.f2b2c.eco.util.Pbkdf2Encryption;
import com.f2b2c.eco.util.UrlUtil;

import jodd.util.StringUtil;

@Controller(value="cUserController")
@RequestMapping(value="/api/user") 
public class CUserController {

    private Logger logger = LoggerFactory.getLogger(CUserController.class);
    @Autowired
    private CUserService cUserService;
    @Autowired
    private SmsValidCodeService smsValidCodeService;
    @Autowired
    private BConfigService bConfigService;
    
    /**
     * 获取我的粉丝
     * 
     * @param userId:用户id
     * @return 返回我的帐户余额值
     */
    @RequestMapping(value = "/getMyFans", method = RequestMethod.GET)
    @ResponseBody
    public DataToCResultModel<PageResultModel> getMyFans(HttpServletRequest request){
        DataToCResultModel<PageResultModel> result = new DataToCResultModel<PageResultModel>();
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String type = request.getParameter("type");
        if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(page)&&StringUtil.isNotEmpty(pageSize)&&StringUtil.isNotEmpty(type)){
            Integer intPage = Integer.valueOf(page);
            Integer intPageSize = Integer.valueOf(pageSize);
            Integer start = PageUtil.getStart(intPage, intPageSize);
            Integer length = intPageSize;
            PageResultModel pageResult = new PageResultModel();
            // 获取我的总粉丝数(type:(0代表用户推荐 1.代表商家推荐)
            Integer totalCount = cUserService.getMyFansCount(Integer.valueOf(userId),0);
            // 获取我的总粉丝列表数据
            List<CUserBean> fanList = cUserService.getMyFans(Integer.valueOf(userId),type,start,length);
            for (CUserBean cUserBean : fanList) {
                if(StringUtil.isNotEmpty(cUserBean.getPicUrl())){
                    cUserBean.setPicUrl(LogoUrlUtil.splitImgUrl(cUserBean.getPicUrl()));
                } else {
                    cUserBean.setPicUrl("");
                }
            }
            pageResult.setRows(fanList);
            pageResult.setTotalCount(totalCount);
            result.setMsg("获取我的帐户余额操作成功");
            result.setStatus(200);
            result.setData(pageResult);
        } else {
            result.setMsg("请求参数为空");
            result.setStatus(400);
        }
        return result;
    }

    /**
     * 店老板的粉丝列表
     */
    @RequestMapping(value = "/btoc-recommend-page", method = RequestMethod.POST)
    @ResponseBody
    public final Page<CUserBean> recommendPage(final Pageable pageable, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        BUserModel user = (BUserModel) session.getAttribute(StorageStatus.MARKET_USER.name());
        if (user != null) {
            Page<CUserBean> pages = cUserService.getMyFans(user.getId(), pageable);
            return pages;
        } else {
            return null;
        }

    }

    /**
     * 我的二维码
     * 
     * @param userId:用户id
     */
    @RequestMapping(value = "/get-introducer-qrcode", method = RequestMethod.GET)
    public final String getMyQrcode(Integer userId, Integer userType, HttpServletRequest request,
            HttpServletResponse response) {
        // userType：0表示消费者推荐，1表示商户推荐
        String url = UrlUtil.getUrlPathWithContext(request) + "/api/user/regist"
                + (userId == null ? "" : "?userId=" + userId + "&userType=" + userType);
        request.setAttribute("url", url);
        return "market/myQrcode";
    }

    /**
     * 注册分享页面
     * 
     * @param userId:用户id
     */
    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public final String regist(Integer userId, Integer userType, HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("userId", userId);
        // userType：0表示消费者推荐，1表示商户推荐
        request.setAttribute("userType", userType);
        return "market/regist";
    }

    /**
     * web端注册页面
     * 
     * @param introducerId
     * @param smsCode
     * @param password
     * @param phone
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/regist-user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registuser(Integer introducerId, Integer introducerType, Integer smsCode,
            String password, String phone, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        // 根据手机号查询c端用户对象
        CUserModel cUserModel = cUserService.getCUserByPhone(phone.trim());
        if (null != cUserModel) {
            map.put("status", 400);
            map.put("msg", "用户已经存在");
            return map;
        } else {
            // 校验验证码
            SmsValidCodeModel code = smsValidCodeService.queryIsExits(phone);
            if (code == null) {
                map.put("status", 400);
                map.put("msg", "请获取短信验证码");
                return map;
            } else {
                // 验证码30分钟失效
                if (code.getUpdateTime() != null
                        && new Date().getTime() - code.getUpdateTime().getTime() > 5 * 60 * 1000) {
                    map.put("status", 400);
                    map.put("msg", "您的验证码已失效，请重新获取");
                    return map;
                } else {
                    if (!String.valueOf(smsCode).equals(code.getValidCode())) {
                        map.put("status", 400);
                        map.put("msg", "验证码错误");
                        return map;
                    }
                }
                // 如果前面的校验没有问题 那么实例化新的用户对象
                CUserModel cUser = new CUserModel();
                try {
                    cUser.setPassword(Pbkdf2Encryption.encode(password));
                } catch (NoSuchAlgorithmException e) {
                    logger.error(e.getMessage());
                } catch (InvalidKeySpecException e) {
                    logger.error(e.getMessage());
                }
                cUser.setPhone(phone);
                cUser.setRecommendUser(introducerId);
                // introducerType：0表示消费者推荐，1表示商户推荐
                cUser.setRecommendType(introducerType);
                cUser.setAccountName(phone);
                cUser.setUpdatedTime(new Date());
                // 自定义生成昵称
                String nickName = "用户" + phone.substring(0, 3) + "****" + phone.substring(6, phone.length());
                cUser.setNickName(nickName);
                cUserService.insertCUser(cUser);

                BConfigModel bConfigModel = new BConfigModel();
                bConfigModel.setUserId(cUser.getId());
                bConfigService.addUserConfig(bConfigModel);

                logger.info("手机号为{}的用户注册成功", phone);
                map.put("status", 200);
                map.put("msg", "注册成功");
                return map;
            }
        }
    }
}
