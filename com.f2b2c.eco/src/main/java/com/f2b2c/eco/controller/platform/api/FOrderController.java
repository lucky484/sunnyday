package com.f2b2c.eco.controller.platform.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FGoodsToShopCarModel;
import com.f2b2c.eco.service.platform.FOrderService;


/**
 * 购物车-订单管理
 * @author jane.hui
 *
 */
@Controller(value="fOrderController")
@RequestMapping(value="/api/order")  
public class FOrderController {
    /**
     * 日志记录器
     */
    private Logger logger = LoggerFactory.getLogger(FOrderController.class);
    
    @Autowired
    private FOrderService fOrderService;
    
    /**
     * 加入购物车
     */
    @RequestMapping(value = "/add-shop-car", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Object> addShopCar(HttpServletRequest request) {
        try {
            String goodsNo = request.getParameter("goodsNo");
            String quantity = request.getParameter("quantity");
            String userId = request.getParameter("userId");                
            if (StringUtils.isNotEmpty(goodsNo)&&StringUtils.isNotEmpty(quantity)&&StringUtils.isNotEmpty(userId)) {
                return fOrderService.addShopCar(goodsNo, Integer.valueOf(quantity), Integer.valueOf(userId));
            } else {
                return new DataToCResultModel<Object>(400,"请求参数不能为空",null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<Object>(400,"加入购物车失败",null);
        }
    }

    /**
     * 加入购物车
     */
    @RequestMapping(value = "/get-shop-car", method = RequestMethod.GET)
    @ResponseBody
    public DataToCResultModel<List<FGoodsToShopCarModel>> getShopCar(HttpServletRequest request) {
        try {
            String userId = request.getParameter("userId");
            if (StringUtils.isNotEmpty(userId)) {
                return fOrderService.getShopCar(Integer.valueOf(userId));
            } else {
                return new DataToCResultModel<List<FGoodsToShopCarModel>>(401,"用户未授权",null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new DataToCResultModel<List<FGoodsToShopCarModel>>(400,"加载购物车商品失败",null);
        }
    }
    
    /**
     * 删除购物车中的商品
     * @param request
     * @return 返回删除购物车是否成功状态(200:成功,401:失败)
     */
    @RequestMapping(value = "/delete-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<String> deleteShopCart(HttpServletRequest request){
        ApiResultModel<String> result = new ApiResultModel<String>();
        String id = request.getParameter("id");
        if(StringUtils.isNotEmpty(id)){
            try {
                int size = fOrderService.deleteShopCartById(id);
                if(size==0){
                    result.setStatus(400);
                    result.setMsg("删除购物车商品失败");
                    return result;
                }
            } catch(Exception e){
                logger.error(e.getMessage());
                result.setStatus(400);
                result.setMsg("删除购物车商品失败");
                return result;
            }
        } else {
            result.setStatus(400);
            result.setMsg("请求参数为空");
            return result;
        }
        result.setStatus(200);
        result.setMsg("删除购物车商品成功");
        return result;
    }
    
    /**
     * 删除购物车商品成功 修改购物车中的商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/modify-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<Integer> modifyShopCart(HttpServletRequest request){
        ApiResultModel<Integer> result = new ApiResultModel<Integer>();
        String strQuantity = request.getParameter("strQuantity");
        if(StringUtils.isNotEmpty(strQuantity)){
            try {
                return fOrderService.modifyShopCart(strQuantity);
            } catch(Exception e){
                logger.error(e.getMessage());
                result.setStatus(400);
                result.setMsg("修改购物车商品失败");
                return result;
            }
        } else {
            result.setStatus(400);
            result.setMsg("请求参数为空");
            return result;
        }
    }
    
    /**
     * 结算购物车的商品
     * @param request
     * @return 返回订单页面中的商品和收货地址json字符串
     */
    @RequestMapping(value = "/settlement-shop-cart", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<Map<String, Object>> settlementShopCart(HttpServletRequest request){
        String userId = request.getParameter("userId");
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        // 购物车主键
        String shopCartStr = request.getParameter("shopCartStr");
        if(StringUtils.isNotEmpty(shopCartStr)&&StringUtils.isNotEmpty(userId)){
            try {                
                return fOrderService.checkShopCart(shopCartStr,Integer.valueOf(userId));
            } catch (Exception e){
                logger.error(e.getMessage());
                result.setStatus(400);
                result.setMsg("结算购物车商品操作失败");
            }
         } else {
            result.setStatus(401);
            result.setMsg("用户id和结算商品不能为空");
         }
        return result;
    }
    
    /**
     * 立即购买
     * @param request
     * @return 返回订单页面的商品list和收货地址
     */
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    @ResponseBody
    public ApiResultModel<Map<String, Object>> purchase(HttpServletRequest request){
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        String userId = request.getParameter("userId");
        String id = request.getParameter("id");
        String quantity = request.getParameter("quantity");
        if(StringUtils.isEmpty(userId)&&StringUtils.isEmpty(id)){
            result.setStatus(401);
            result.setMsg("用户id不能为空");
        } else if(StringUtils.isEmpty(quantity)){
            result.setStatus(401);
            result.setMsg("购买数量不能为0");
        } else {
            try {
                return fOrderService.purchase(Integer.valueOf(id), Integer.valueOf(userId),Integer.valueOf(quantity));
            } catch (Exception e){
                logger.error(e.getMessage());
                result.setStatus(400);
                result.setMsg("立即购买操作失败");
            }
        } 
        return result;
    }
    
    /**
     * 订单状态跟踪
     * @param request
     * @return
     */
    @RequestMapping(value = "/track", method = RequestMethod.POST)
    @ResponseBody
    public final DataToCResultModel<String> track(HttpServletRequest request){
        String orderId=StringUtils.trimToNull(request.getParameter("orderId"));
        
        //确认收货
        Map<String, Object> map=new HashMap<>();
        map.put("orderId", orderId);
        map.put("status", 1);
        if(fOrderService.updateOrderStatus(map)>0){
            return new DataToCResultModel<String>(200, "已确认收货", null);
        }
        return new DataToCResultModel<String>(400,"未知错误",null);
    }
    
    /**
     * 查询购物车商品的总数量和种类
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCarCount",method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<Map<String,Object>> getCarCount(HttpServletRequest request){
        DataToCResultModel<Map<String,Object>> dataToCResult = new DataToCResultModel<Map<String,Object>>();
        String userId = request.getParameter("userId");
        if(userId != null && !userId.equals("")){
            Map<String,Object> map = fOrderService.queryCarCountAndKindByUserId(Integer.valueOf(userId));
            dataToCResult.setData(map);
            dataToCResult.setMsg("success");
            dataToCResult.setStatus(200);
        }else{
            dataToCResult.setMsg("fail");
            dataToCResult.setStatus(400);
        }
        return dataToCResult;
    }
}