package com.f2b2c.eco.controller.market.b2c;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.model.market.PageResultModel;
import com.f2b2c.eco.model.market.ReturnPayModel;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.service.market.BOrderService;
import com.f2b2c.eco.service.market.BSalesOrderService;
import com.f2b2c.eco.service.market.CUserService;
import com.f2b2c.eco.service.market.ReturnPayService;
import com.f2b2c.eco.share.pay.alipay.config.AlipayConfig;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.share.pay.alipay.util.PayUtil;
import com.f2b2c.eco.share.pay.wechat.config.WeixinConfig;
import com.f2b2c.eco.share.pay.wechat.util.WeixinUtil;
import com.f2b2c.eco.util.DateUtil;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.Pbkdf2Encryption;
import com.f2b2c.eco.util.PropertiesUtil;
import com.f2b2c.eco.websocket.SystemWebSocketHandler;

import net.sf.json.JSONObject;

/**
 * 商品订单
 * 
 * @author jing.liu
 *
 */
@Controller
@RequestMapping(value = "/api/bsalesorder")
public class BSalesOrderController {

    @Autowired
    private BSalesOrderService bSalesOrderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ReturnPayService returnPayService;

    @Autowired
    private BOrderService bOrderService;
    
    @Autowired
    private CUserService cUserService;

    @Bean
    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }

    /**
     * 查询所有订单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query-all-order", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<PageResultModel> queryAllOrder(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");
        String returnStatus = request.getParameter("returnStatus");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(pageSize)) {
            int count = bSalesOrderService.queryAllOrderCount(Integer.valueOf(userId),
                    StringUtils.trimToNull(status) == null ? null : Integer.valueOf(status),
                    StringUtils.trimToNull(returnStatus) == null ? null : Integer.valueOf(returnStatus));

            Page p = new Page(count, Integer.valueOf(page), Integer.valueOf(pageSize));

            List<BToCOrderModel> list = bSalesOrderService.queryAllOrder(p.getStart(), Integer.valueOf(pageSize),
                    Integer.valueOf(userId), StringUtils.trimToNull(status) == null ? null : Integer.valueOf(status),
                    StringUtils.trimToNull(returnStatus) == null ? null : Integer.valueOf(returnStatus));
            PageResultModel pageResult = new PageResultModel();
            if (list != null && list.size() > 0) {
                for (BToCOrderModel bToCOrder : list) {
                    for (BGoodsToCModel bGoodsToC : bToCOrder.getList()) {
                        if (bGoodsToC.getLogoUrl() != null && !bGoodsToC.getLogoUrl().equals("")) {
                            String[] logoUrls = bGoodsToC.getLogoUrl().split("\\|");
                            bGoodsToC.setLogoUrl(PropertiesUtil.getValue("path") + logoUrls[0]);
                        }
                    }
                    bToCOrder.setCurrentTime(getWebsiteDatetime());
                }
                pageResult.setRows(JsonUtil.parseToNoEmptyStr(list));
            } else {
                List<BToCOrderModel> listTo = new ArrayList<BToCOrderModel>();
                pageResult.setRows(listTo);
            }
            pageResult.setTotalCount(count);
            return new DataToCResultModel<PageResultModel>(200, "success", pageResult);
        } else {
            return new DataToCResultModel<PageResultModel>(400, "请求参数不能为空", null);
        }
    }

    /**
     * 待评价的所有商品列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryAllEvaluateOrder", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<PageResultModel> queryAllEvaluateOrder(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageResultModel pageResult = new PageResultModel();
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String userId = request.getParameter("userId");
        map.put("userId", userId);
        int count = bSalesOrderService.queryEvaluateOrderDetailsCount(map);
        Page p = new Page(count, Integer.valueOf(page), Integer.valueOf(pageSize));
        map.put("num", p.getStart());
        map.put("pageSize", p.getPageSize());
        List<BGoodsToCModel> list = bSalesOrderService.queryEvaluateOrderDetails(map);
        if (list != null && list.size() > 0) {
            for (BGoodsToCModel btc : list) {
                if (btc.getLogoUrl() != null && !btc.getLogoUrl().equals("")) {
                    String[] logoUrls = btc.getLogoUrl().split("\\|");
                    btc.setLogoUrl(PropertiesUtil.getValue("path") + logoUrls[0]);
                }
            }
        }
        pageResult.setTotalCount(count);
        pageResult.setRows(JsonUtil.parseToNoEmptyStr(list));
        return new DataToCResultModel<PageResultModel>(200, "success", pageResult);
    }

    /**
     * 删除订单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete-order-by-id", method = RequestMethod.POST)
    @ResponseBody
    public <T> DataToCResultModel<T> deleteOrderById(HttpServletRequest request) {
        String id = request.getParameter("orderId");
        BSalesOrderModel bSalesOrder = bSalesOrderService.queryOrderDetailById(id);
        int result = bSalesOrderService.deleteOrderById(id, bSalesOrder.getStatus()); // 删除订单的同时删除订单的详情
        if (result > 0) {
            return new DataToCResultModel<T>(200, "success", null);
        } else {
            return new DataToCResultModel<T>(400, "fail", null);
        }
    }

    /**
     * 修改订单状态
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/update-status-by-id", method = RequestMethod.POST)
    @ResponseBody
    public <T> DataToCResultModel<T> updateStatusById(HttpServletRequest request) {
        Integer status = Integer.valueOf(request.getParameter("status"));
        String id = request.getParameter("orderId");
        if(status == 1){
        	BSalesOrderModel bSalesOrder = bSalesOrderService.queryOrderById(id);
        	if(bSalesOrder.getReturnStatus() != null && bSalesOrder.getReturnStatus() == 1){
        		return new DataToCResultModel<T>(400, "您还有退款或退货中的商品，不能确认收货", null);
        	}
        }
        BSalesOrderModel bSalesOrder = new BSalesOrderModel();
        bSalesOrder.setStatus(status);
        bSalesOrder.setId(id);
        bSalesOrder.setUpdatedTime(new Date());
        if (status == 4) {
            bSalesOrder.setDeliverTime(new Date());
        }
        int result = bSalesOrderService.updateStatusById(bSalesOrder);
        if (result > 0) {
            return new DataToCResultModel<T>(200, "success", null);
        } else {
            return new DataToCResultModel<T>(400, "fail", null);
        }
    }

    /**
     * 交易记录
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/querytradinfo", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<PageResultModel> queryTradInfo(HttpServletRequest request) {
        DataToCResultModel<PageResultModel> dataToCResult = new DataToCResultModel<PageResultModel>();
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        if (userId != null && !userId.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int count = bSalesOrderService.queryTradInfoCount(Integer.valueOf(userId));
            Page p = new Page(count, Integer.valueOf(page), Integer.valueOf(pageSize));
            List<BSalesOrderModel> list = bSalesOrderService.queryTradInfo(Integer.valueOf(userId), p.getStart(),
                    p.getPageSize());
            for (BSalesOrderModel bSalesOrder : list) {
                bSalesOrder.setTradMark(format.format(bSalesOrder.getCreatedTime()) + "消费了"
                        + MoneyUtil.fromFenToYuan(bSalesOrder.getTotal().toString()) + "元");
                // bSalesOrder.setPrice(Double.valueOf(MoneyUtil.fromFenToYuan(bSalesOrder.getTotal().toString())));
            }
            PageResultModel pageResult = new PageResultModel();
            pageResult.setRows(JsonUtil.parseToNoEmptyStr(list));
            pageResult.setTotalCount(count);
            dataToCResult.setData(pageResult);
            dataToCResult.setMsg("success");
            dataToCResult.setStatus(200);
        } else {
            dataToCResult.setMsg("fail");
            dataToCResult.setStatus(400);
        }
        return dataToCResult;
    }

    /**
     * 查询卖家所有的销售单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryallsalesorder", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<PageResultModel> queryAllSalesOrder(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(pageSize)) {
            int count = bSalesOrderService.queryAllSalesOrderCount(Integer.valueOf(userId),
                    status == "" ? null : Integer.valueOf(status));

            Page p = new Page(count, Integer.valueOf(page), Integer.valueOf(pageSize));

            List<BToCOrderModel> list = bSalesOrderService.queryAllSalesOrder(p.getStart(), Integer.valueOf(pageSize),
                    Integer.valueOf(userId), StringUtils.trimToNull(status) == null ? null : Integer.valueOf(status));
            PageResultModel pageResult = new PageResultModel();
            if (list != null && list.size() > 0) {
                for (BToCOrderModel bToCOrder : list) {
                    for (BGoodsToCModel bGoodsToC : bToCOrder.getList()) {
                        if (bGoodsToC.getLogoUrl() != null && !bGoodsToC.getLogoUrl().equals("")) {
                            String[] logoUrls = bGoodsToC.getLogoUrl().split("\\|");
                            bGoodsToC.setLogoUrl(PropertiesUtil.getValue("path") + logoUrls[0]);
                        }
                    }
                }
                pageResult.setRows(JsonUtil.parseToNoEmptyStr(list));
            } else {
                List<BToCOrderModel> listTo = new ArrayList<BToCOrderModel>();
                pageResult.setRows(listTo);
            }
            pageResult.setTotalCount(count);
            return new DataToCResultModel<PageResultModel>(200, "success", pageResult);
        } else {
            return new DataToCResultModel<PageResultModel>(400, "请求参数不能为空", null);
        }
    }

    /**
     * 删除销售单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/deletesalesorderbyid", method = RequestMethod.POST)
    @ResponseBody
    public <T> DataToCResultModel<T> deleteSalesOrderById(HttpServletRequest request) {
        String id = request.getParameter("orderId");
        // 这里卖家删除订单时，不需要把生成订单的商品还回去，只有在买家删除订单的时候才把生成订单的商品还回去
        int result = bSalesOrderService.deleteSalesOrderById(id); // 删除订单的同时删除订单的详情
        if (result > 0) {
            return new DataToCResultModel<T>(200, "success", null);
        } else {
            return new DataToCResultModel<T>(400, "fail", null);
        }
    }

    /**
     * 订单支付
     * 
     * @param request
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/payorder", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<String> payOrder(HttpServletRequest request, HttpServletResponse response)
            throws InterruptedException, ExecutionException {
        DataToCResultModel<String> dataToCResult = new DataToCResultModel<String>();
        String orderId = request.getParameter("orderId");
        String payType = request.getParameter("payType");
        String userId = request.getParameter("userId");
        String payPassword = request.getParameter("payPassword");
        if (orderId != null && !orderId.equals("")) {
            BToCOrderModel bToCOrder = bSalesOrderService.queryAllOrderDetail(orderId);
            String goodsName = "";
            String errorMsg = "";
            for (BGoodsToCModel bGoodsToC : bToCOrder.getList()) {
                if (bGoodsToC.getStatus() == 2) {
                    errorMsg += bGoodsToC.getName() + ",";
                } else {
                    goodsName += bGoodsToC.getName() + ",";
                }
            }
            if (bToCOrder.getIsEnable() != 0) {
                if (errorMsg.equals("")) {
                    String orderInfo = "";
                    if (payType.equals("0")) { // 支付宝支付
                        orderInfo = PayUtil.getInfo("订单支付", goodsName.substring(0, goodsName.length() - 1),
                                MoneyUtil.fromFenToYuan(String.valueOf(bToCOrder.getTotal() + bToCOrder.getFreight())),
                                bToCOrder.getOrderNo(), AlipayConfig.notify_url);
                        dataToCResult.setData(orderInfo);
                        dataToCResult.setMsg("支付成功");
                        dataToCResult.setStatus(200);
                    } else if (payType.equals("1")) { // 微信支付
                        orderInfo = WeixinUtil.pay(request, response, bToCOrder.getTotal() + bToCOrder.getFreight(),
                                "普通用户订单支付", bToCOrder.getOrderNo(), WeixinConfig.notify_url);
                        dataToCResult.setData(orderInfo);
                        dataToCResult.setMsg("支付成功");
                        dataToCResult.setStatus(200);
                    } else if (payType.equals("2")) { // 钱包支付
                    	CUserModel cUser = cUserService.getCUserInfo(Integer.valueOf(userId));
                		if(Pbkdf2Encryption.checkPassword(payPassword, cUser.getPayPassword())){
                			int status = accountService.walletPayTo(bToCOrder.getUserId(),bToCOrder.getTotal() + bToCOrder.getFreight(), bToCOrder.getOrderNo());
                			if (status == 0) {
                				dataToCResult.setMsg("钱包余额不足，支付失败");
                				dataToCResult.setStatus(400);
                			} else if (status == 1) {
                				dataToCResult.setMsg("支付失败");
                				dataToCResult.setStatus(400);
                			} else if (status == 2) {
                				List<BSalesOrderModel> list = bOrderService
                						.selectSalesOrderByMergeNo(bToCOrder.getOrderNo());
                				for (BSalesOrderModel bSalesOrder : list) {
                					if (bSalesOrder.getCatalog().equals("0")) {
                						systemWebSocketHandler().sendMessageToUser(bSalesOrder.getbUserId().toString(),
                								new TextMessage("您有新的订单未接,请前往接单"));
                					}
                				}
                				dataToCResult.setMsg("支付成功");
                				dataToCResult.setStatus(200);
                			}
                		}else{
                			dataToCResult.setMsg("密码错误,请重新输入");
            				dataToCResult.setStatus(400);
                		}
                    } else {
                        dataToCResult.setMsg("支付失败");
                        dataToCResult.setStatus(400);
                    }
                } else {
                    dataToCResult.setMsg("您的订单中" + errorMsg.substring(0, errorMsg.length() - 1) + "已失效，不能购买，请重新下单!");
                    dataToCResult.setStatus(400);
                }
            } else {
                dataToCResult.setMsg("所有商品均已失效，请重新下单!");
                dataToCResult.setStatus(400);
            }
        } else {
            dataToCResult.setMsg("支付失败");
            dataToCResult.setStatus(400);
        }
        return dataToCResult;
    }

    /**
     * 订单详情
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/query-all-order-detail", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<BToCOrderModel> queryOrderDetailById(HttpServletRequest request) {
        String id = request.getParameter("orderId");
        if (id != null && !id.equals("")) {
            BToCOrderModel bToCOrder = bSalesOrderService.queryAllOrderDetail(id);
            if (bToCOrder != null) {
                for (BGoodsToCModel bGoodsToC : bToCOrder.getList()) {
                    String[] logoUrls = bGoodsToC.getLogoUrl().split("\\|");
                    bGoodsToC.setLogoUrl(PropertiesUtil.getValue("path") + logoUrls[0]);
                }
                return new DataToCResultModel<BToCOrderModel>(200, "success", bToCOrder);
            } else {
                return new DataToCResultModel<BToCOrderModel>(400, "订单不存在", null);
            }
        } else {
            return new DataToCResultModel<BToCOrderModel>(400, "请求参数不能为空", null);
        }
    }

    /**
     * 退货流程
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/returnPay", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<String> returnPay(HttpServletRequest request) {
        DataToCResultModel<String> dataToCResult = new DataToCResultModel<String>();
        String returnPay = request.getParameter("returnPay");
        String orderId = request.getParameter("orderId");
        ReturnPayModel returnPayModel = (ReturnPayModel) JSONObject.toBean(JSONObject.fromObject(returnPay),
                ReturnPayModel.class);
        int result = returnPayService.insertReturnPay(returnPayModel, orderId);
        if (result == 1) {
            dataToCResult.setMsg("success");
            dataToCResult.setStatus(200);
        } else {
            dataToCResult.setMsg("fail");
            dataToCResult.setStatus(400);
        }
        return dataToCResult;
    }

    private String getWebsiteDatetime() {
        String webUrl = "http://www.ntsc.ac.cn";// 中国科学院国家授时中心
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户取消订单
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<String> cancelOrder(HttpServletRequest request) {
        DataToCResultModel<String> dataToCResult = new DataToCResultModel<String>();
        String type = request.getParameter("type"); // 1-商家未接单用户取消订单
                                                    // 2-商家接单了用户取消订单
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status"); // 6-订单已取消
        int result = bSalesOrderService.cancelOrder(Integer.valueOf(type), orderId, Integer.valueOf(status));
        if (result == 1) {
            dataToCResult.setMsg("success");
            dataToCResult.setStatus(200);
        } else {
            dataToCResult.setMsg("fail");
            dataToCResult.setStatus(400);
        }
        return dataToCResult;
    }

    /**
     * 用户催单
     * 
     * @return
     */
    @RequestMapping(value = "/remindOrder", method = RequestMethod.POST)
    @ResponseBody
    public DataToCResultModel<String> remindOrder(HttpServletRequest request) {
        DataToCResultModel<String> dataToCResult = new DataToCResultModel<String>();
        String orderId = request.getParameter("orderId");
        BToCOrderModel bToCOrder = bSalesOrderService.queryAllOrderDetail(orderId);
        boolean flag = DateUtil.compareTime(new Date(), DateUtil.strParseDate((bToCOrder.getReceiveOrderTime())));
        if (flag) { // 如果接单时间大于1小时，允许催单
            // JPush jpush = new JPush();
            systemWebSocketHandler().sendMessageToUser(bToCOrder.getbUserId().toString(), new TextMessage(
                    "买家" + bToCOrder.getReceiverName() + "催单，单号是" + bToCOrder.getOrderNo() + ",请及时处理 "));
            dataToCResult.setStatus(200);
            dataToCResult.setMsg("催单成功");
            // try {
            // jpush.buildPayloadWithAliaId(bToCOrder.getbUserId().toString(),"买家"+bToCOrder.getReceiverName()+"催单，单号是"+bToCOrder.getOrderNo()+",请及时处理
            // ","催单提醒");
            // } catch (APIConnectionException e) {
            // e.printStackTrace();
            // } catch (APIRequestException e) {
            // e.printStackTrace();
            // }
        } else {
            dataToCResult.setStatus(400);
            dataToCResult.setMsg("订单生成时间未超过1小时，无法催单，请耐心等待");
        }
        return dataToCResult;
    }
}
