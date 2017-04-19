package com.f2b2c.eco.controller.platform.f2b;

import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.f2b2c.eco.constant.platform.Constant.ShopType;
import com.f2b2c.eco.controller.BaseController;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.platform.FAuthCodeModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.service.market.BGoodsService;
import com.f2b2c.eco.service.platform.BShopService;
import com.f2b2c.eco.service.platform.FAuthService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.share.dto.DTOResult;
import com.f2b2c.eco.status.MessageType;
import com.f2b2c.eco.status.StorageStatus;
import com.f2b2c.eco.util.ConstantUtil.RoleType;

import jodd.util.StringUtil;

/**
 * @author jzhu
 *
 */
@Controller("fShopController")
@RequestMapping("/farm/shop")
public class BShopController extends BaseController {

    private static final Logger Logger = LoggerFactory.getLogger(BShopController.class);
    
    @Autowired
    private BShopService bShopService;
    @Autowired
    private FAuthService fAuthService;
    @Autowired
    private BGoodsService bGoodsService;
    @Autowired
    private FUserService fUserService;
    
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response){
        
        return "platform/shop";
        
    }
    
    /**
     * @return
     */
    @RequestMapping(value="/page",method=RequestMethod.POST)
    @ResponseBody
    public final Page<BShopInfoModel> pagination(final Pageable pageable,HttpServletRequest request, HttpServletResponse response,HttpSession session){
        FUserModel user = (FUserModel) request.getSession().getAttribute(StorageStatus.USER_INSESSION.name());
        List<Integer> areaListAll = new ArrayList<Integer>();
        List<Integer> userList = new ArrayList<Integer>();
        if (!user.getRoleId().equals(RoleType.ADMIN_ROLE_ID)) {
            if (user.getRoleId().equals(RoleType.PROVINCE_ROLE_ID)) { // 省合伙人
//                List<Integer> list = areaService.queryCityIdByProvinceId(user.getProvinceid()); // 获取所有的市id
//                List<Integer> areaList = areaService.queryAreaIdByCityId(list);
                areaListAll.add(user.getProvinceid());
//                areaListAll.addAll(list);
//                areaListAll.addAll(areaList);
            } else if (user.getRoleId().equals(RoleType.CITY_ROLE_ID)) {
//                List<Integer> cityList = new ArrayList<Integer>();
//                cityList.add(user.getCityid());
//                List<Integer> areaList = areaService.queryAreaIdByCityId(cityList);
                areaListAll.add(user.getCityid());
//                areaListAll.addAll(areaList);
            } else if (user.getRoleId().equals(RoleType.AREA_ROLE_ID)) {
                areaListAll.add(user.getAreaId());
            } else if (user.getRoleId().equals(RoleType.COUNSELOR_ROLE_ID)) {
                userList.add(user.getId());
            }
            if (areaListAll != null && areaListAll.size() > 0)
            userList = fUserService.queryUserIdByAreaId(areaListAll);
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String name = request.getParameter("name");
        FUserModel loginUser = (FUserModel) session.getAttribute("USER_INSESSION");
        paramMap.put("name", name);
        paramMap.put("account", loginUser.getAccountName());
        paramMap.put("userList", userList);
        Page<BShopInfoModel> pages = bShopService.findWithPagination(pageable, paramMap);
        
        return pages;
    }
    
    @RequestMapping(value="/validauthcode",method=RequestMethod.GET)
    @ResponseBody
    public DTOResult validAuthCode(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        DTOResult result = new DTOResult();
        String authCode = request.getParameter("authCode");
        FUserModel loginUser = (FUserModel) session.getAttribute("USER_INSESSION");
        FAuthCodeModel authCodeModel = fAuthService.getAuthCodeWithUserId(authCode,loginUser.getId());
        if(authCodeModel!=null){
            if(authCodeModel.getIsUsed() == 1){
                result.setType(MessageType.warning);
                result.setMessage("授权码已被使用");
            }else{
                result.setType(MessageType.success);
                result.setMessage("授权码验证成功");
            }
        }else{
            result.setType(MessageType.error);
            result.setMessage("授权码不存在");
        }
        return result;
    }
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult add(HttpServletRequest request,HttpServletResponse response,HttpSession session,BShopInfoModel shopModel){
        
        DTOResult result = new DTOResult();
        FUserModel userModel = (FUserModel) session.getAttribute("USER_INSESSION");
        int count = bShopService.saveBShopInfo(shopModel,userModel);
        if(count == 1){
            // 当店铺成功创建的时候需要把原来的授权码的状态修改为已用
            int total = fAuthService.updateAuthCode(shopModel.getAuthCode());
            if(total == 1){
                result.setType(MessageType.success);
                result.setMessage("添加店铺成功");
            }else{
                result.setType(MessageType.error);
                result.setMessage("添加店铺失败");
            }
        }else{
            result.setType(MessageType.error);
            result.setMessage("添加店铺失败");
        }
        return result;
    }
    
    @RequestMapping(value="/update",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult update(HttpServletRequest request,HttpServletResponse response,HttpSession session,BShopInfoModel shopModel){
        
        DTOResult result = new DTOResult();
        FUserModel userModel = (FUserModel) session.getAttribute("USER_INSESSION");
        int count = bShopService.updateBShopInfo(shopModel,userModel);
        if(count == 1){
            // 店铺修改的时候是不能修改授权码
            result.setType(MessageType.success);
            result.setMessage("修改店铺成功");
        }else{
            result.setType(MessageType.error);
            result.setMessage("修改店铺失败");
        }
        return result;
    }
    
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult delete(HttpServletRequest request,HttpServletResponse response){
        String strId = request.getParameter("id");
        String userId = request.getParameter("userId");
        DTOResult result = new DTOResult();
        if(StringUtil.isNotEmpty(strId)){
            Integer id = Integer.valueOf(strId);
            int size = bGoodsService.updateGoodsByShopId(id,Integer.valueOf(userId));
            if(size == 0){
                result.setType(MessageType.error);
                result.setMessage("删除店铺失败！");
                return result;
            }
            result.setType(MessageType.success);
            result.setMessage("删除店铺成功");
        }
        return result;    
    }
    
    /**
     * 显示编辑数据
     * 
     * @param request:请求request
     * @return 返回店铺信息
     */
    @RequestMapping(value="/get",method=RequestMethod.POST)
    @ResponseBody
    public Object get(HttpServletRequest request){
        String id = request.getParameter("id");
        if(StringUtil.isNotEmpty(id)){
            return bShopService.get(Integer.valueOf(id));
        }
        return null;
    }
    
    /**
     * 修改店铺信息
     * 
     * @param request:request参数
     * @return 返回修改店铺操作状态
     */
    @RequestMapping(value="/modify",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult modify(HttpServletRequest request){
        String id = request.getParameter("id");
        String shopName = request.getParameter("shopName");
        String remark = request.getParameter("remark");
        String address = request.getParameter("address");
        String areaCode = request.getParameter("areaCode");
/*        String lnglat = request.getParameter("lnglat");*/
        String phone = request.getParameter("phone");
        DTOResult result = new DTOResult();
        try {
            if(StringUtil.isNotEmpty(id)){
                Integer size = bShopService.modify(id,shopName,remark,address,areaCode,phone);
                if(size==0){
                    result.setType(MessageType.error);
                    result.setMessage("修改店铺信息操作失败！");
                }
            }
        } catch (Exception e){
            Logger.error(e.getMessage());
            result.setType(MessageType.error);
            result.setMessage("修改店铺信息操作失败！");
        }
        result.setType(MessageType.success);
        return result;
    }
    
    /**
     * 选择省
     * 
     * @param request:request参数
     * @return 返回该省下面的所有市和区域
     */
    @RequestMapping(value="/chooseProvince",method=RequestMethod.POST)
    @ResponseBody
    public Object chooseProvince(HttpServletRequest request){
        String code = request.getParameter("code");
        if(StringUtil.isNotEmpty(code)){
            return bShopService.chooseProvince(code);
        }
        return null;
    }
    
    /**
     * 选择省
     * 
     * @param request:request参数
     * @return 返回该省下面的所有市和区域
     */
    @RequestMapping(value="/chooseCity",method=RequestMethod.POST)
    @ResponseBody
    public Object chooseCity(HttpServletRequest request){
        String code = request.getParameter("code");
        if(StringUtil.isNotEmpty(code)){
            return bShopService.chooseCity(code);
        }
        return null;
    }
    
    /**
     * 启用禁用门店
     * 
     * @param request:request参数(id:门店id,tag:启用禁用状态(1.启用
     *            0.禁用))
     * @return 返回启用禁用操作状态
     */
    @RequestMapping(value="/enable",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult enable(HttpServletRequest request){
        String id = request.getParameter("id");
        String tag = request.getParameter("tag");
        DTOResult result = new DTOResult();
        try {
            if(StringUtil.isNotEmpty(id)&&StringUtil.isNotEmpty(tag)){
                Integer size = bShopService.enable(id, tag);
                if(size==0){
                    result.setType(MessageType.error);
                    if(ShopType.YES.equals(tag)){
                        result.setMessage("启用店铺操作失败！");
                    } else {
                        result.setMessage("禁用店铺操作失败！");
                    }
                }
            } else {
                result.setType(MessageType.error);
                result.setMessage("请求参数不能为空！");
            }
        } catch (Exception e){
            Logger.error(e.getMessage());
            result.setType(MessageType.error);
            if(ShopType.YES.equals(tag)){
                result.setMessage("启用店铺操作失败！");
            } else {
                result.setMessage("禁用店铺操作失败！");
            }
        }
        return result;
    }
    
    @RequestMapping(value="/show/{id}",method=RequestMethod.GET)
    public String show(HttpServletRequest request,HttpServletResponse response,@PathVariable Integer id,Model model){
        
        BShopInfoModel bShopInfoModel = bShopService.getBShopInfoById(id);
        model.addAttribute("shopInfo", bShopInfoModel);
        return "platform/shop/show";
        
    }
    
    // 富文本上传图片时使用的方法，方法支持多张图片上传
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    @ResponseBody
    public DTOResult uploadPic(HttpServletRequest request,HttpServletResponse response,MultipartFile file){
        
        DTOResult result = new DTOResult();
        
        return result;
    }
    
    
}