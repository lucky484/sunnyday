package com.f2b2c.eco.service.market.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.f2b2c.eco.dao.market.CRechardOrderDao;
import com.f2b2c.eco.dao.market.CRechardPayRecordDao;
import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserBalanceRecordDao;
import com.f2b2c.eco.dao.market.CWxRechardPayRecordDao;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.market.CRechardOrderModel;
import com.f2b2c.eco.model.market.CRechardPayRecordModel;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.model.market.CWxRechardPayRecordModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.service.market.BRechardService;
import com.f2b2c.eco.share.pay.alipay.config.AlipayConfig;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.share.pay.alipay.util.PayUtil;
import com.f2b2c.eco.share.pay.wechat.config.WeixinConfig;
import com.f2b2c.eco.share.pay.wechat.util.WeixinUtil;
import com.f2b2c.eco.status.CRechardOrderStatusEnum;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;
import com.f2b2c.eco.status.SequenceType;
import com.f2b2c.eco.util.SequenceUtil;
import com.f2b2c.eco.util.ConstantUtil.PayType;
import jodd.util.StringUtil;

/**
 * 充值Service
 * @author jane.hui
 *
 */
@Service("bRechardService")
public class BRechardServiceImpl implements BRechardService{

    @Autowired
    private CRechardOrderDao cRechardOrderDao;
    
    @Autowired
    private CRechardPayRecordDao cRechardPayRecordDao;
    
    @Autowired
    private CWxRechardPayRecordDao cWxRechardPayRecordDao;
    
    @Autowired
    private CUserBalanceRecordDao cUserBalanceRecordDao;
    
    @Autowired
    private CUserBalanceDao cUserBalanceDao;
    
    @Autowired
    private AccountService accountService;
    
    /**
     * 充值功能
     */
    @Override
    public DataToCResultModel<String> save(HttpServletRequest request,HttpServletResponse response,String money,String payType,Integer userId) {
        DataToCResultModel<String> result = new DataToCResultModel<String>();
        String orderInfo = "";
        String mergeOrderNo = SequenceUtil.nextId(SequenceType.R.toString());
        Integer orderPrice = MoneyUtil.fromYuanToIntFen(money.toString());
        // 插入充值订单表数据
        CRechardOrderModel cRechardOrder = new CRechardOrderModel();
        cRechardOrder.setOrderNo(mergeOrderNo);
        cRechardOrder.setPayType(payType);
        cRechardOrder.setStatus(CRechardOrderStatusEnum.WAIT_FOR_PAY.toString());
        cRechardOrder.setTotalFee(orderPrice);
        cRechardOrder.setCreateTime(new Date());
        cRechardOrder.setUserId(userId);
        int size = cRechardOrderDao.insertSelective(cRechardOrder);
        if(size==0){
            result.setMsg("充值操作失败");
            result.setStatus(400);
            return result;
        }
        if(PayType.WEIXIN.toString().equals(payType)){
            orderInfo = WeixinUtil.pay(request,response,Integer.valueOf(orderPrice), "充值", mergeOrderNo,WeixinConfig.recharge_notify_url);
        } else if(PayType.ALIPAY.toString().equals(payType)){
            orderInfo = PayUtil.getInfo("充值", "充值", String.valueOf(money), mergeOrderNo,AlipayConfig.recharge_notify_url);       
        }
        result.setData(orderInfo);
        result.setMsg("充值成功");
        result.setStatus(200);
        return result;
    }

    /**
     * 修改订单状态
     * @param app_id:应用id
     * @param out_trade_no:商品订单号
     * @param body:商品描述
     * @param buyer_id:买家id
     * @param seller_id:卖家id
     * @param subject:商品名称
     * @param total_amount:支付金额
     * @param trade_no:交易流水号
     * @param traceContent:交易内容
     */
    @Override
    public void notifyUrl(String app_id, String out_trade_no, String body, String buyer_id, String seller_id,
            String subject, String total_amount, String trade_no, String tradeContent) {
        if(StringUtil.isNotEmpty(out_trade_no)){
            CRechardOrderModel cRechardOrder = cRechardOrderDao.selectByOutTradeNo(out_trade_no);
            CRechardOrderModel saveCRechardOrder = new CRechardOrderModel();
            if(cRechardOrder!=null){
                if(CRechardOrderStatusEnum.WAIT_FOR_PAY.toString().equals(cRechardOrder.getStatus())){
                    CRechardOrderModel rechard = cRechardOrderDao.selectByOutTradeNo(out_trade_no);
                    if(rechard!=null){
                        int state = accountService.saveBalance(rechard.getUserId(), MoneyUtil.fromYuanToIntFen(total_amount), out_trade_no,MoneyOperateEnum.RECHARD.toString(),"用户用支付宝充值了"+total_amount+"元",MoneyTagEnum.ADD.toString());
                        if(state==200){
                            saveCRechardOrder.setOrderNo(out_trade_no);
                            saveCRechardOrder.setStatus(CRechardOrderStatusEnum.ACCOUNT_PAID.toString());
                            cRechardOrderDao.updateByOrderNo(saveCRechardOrder);
                            CRechardPayRecordModel cRechardPayRecord = new CRechardPayRecordModel();
                            cRechardPayRecord.setOutTradeNo(out_trade_no);
                            cRechardPayRecord.setBody(body);
                            cRechardPayRecord.setBuyerId(buyer_id);
                            cRechardPayRecord.setSellerId(seller_id);
                            cRechardPayRecord.setSubject(subject);
                            cRechardPayRecord.setTotalAmount(total_amount);
                            cRechardPayRecord.setTradeNo(trade_no);
                            cRechardPayRecord.setTradeContent(tradeContent);
                            cRechardPayRecord.setCreateDate(new Date());
                            cRechardPayRecordDao.insertSelective(cRechardPayRecord);
                        }
                    }
                }
            }
        }
    }

    /**
     * 微信回调URL
     * @param record
     */
    @Override
    public void wxNotifyUrl(Map<String, String> map) {
        String appid = map.get("appid");
        String mch_id = map.get("mch_id");
        String nonce_str = map.get("nonce_str");
        String sign = map.get("sign");
        String trade_type = map.get("trade_type");
        String total_fee = map.get("total_fee");
        String transaction_id = map.get("transaction_id");
        String out_trade_no = map.get("out_trade_no");
        String openid = map.get("openid");
        String result_code = map.get("result_code");
        if(StringUtil.isNotEmpty(out_trade_no)){
            CRechardOrderModel cRechardOrder = cRechardOrderDao.selectByOutTradeNo(out_trade_no);
            CRechardOrderModel saveCRechardOrder = new CRechardOrderModel();
            if(cRechardOrder!=null){
                if(CRechardOrderStatusEnum.WAIT_FOR_PAY.toString().equals(cRechardOrder.getStatus())){
                    CRechardOrderModel rechard = cRechardOrderDao.selectByOutTradeNo(out_trade_no);
                    if(rechard!=null){
                        int state = accountService.saveBalance(rechard.getUserId(), Integer.valueOf(total_fee), out_trade_no,MoneyOperateEnum.RECHARD.toString(),"用户用微信充值了" + MoneyUtil.fromFenToYuan(total_fee) + "元",MoneyTagEnum.ADD.toString());
                        if(state==200){
                            saveCRechardOrder.setOrderNo(out_trade_no);
                            saveCRechardOrder.setStatus(CRechardOrderStatusEnum.ACCOUNT_PAID.toString());
                            cRechardOrderDao.updateByOrderNo(saveCRechardOrder);
                            CWxRechardPayRecordModel cWxRechardPayRecord = new CWxRechardPayRecordModel();
                            cWxRechardPayRecord.setOutTradeNo(out_trade_no);
                            cWxRechardPayRecord.setAppid(appid);
                            cWxRechardPayRecord.setMchId(mch_id);
                            cWxRechardPayRecord.setNonceStr(nonce_str);
                            cWxRechardPayRecord.setSign(sign);
                            cWxRechardPayRecord.setTradeType(trade_type);
                            cWxRechardPayRecord.setTotalFee(total_fee);
                            cWxRechardPayRecord.setOpenid(openid);
                            cWxRechardPayRecord.setResultCode(result_code);
                            cWxRechardPayRecord.setCreateDate(new Date());
                            cWxRechardPayRecord.setTransactionId(transaction_id);
                            cWxRechardPayRecordDao.insertSelective(cWxRechardPayRecord);
                        }
                    }
                }
            }
        }
    }

    /**
     * 充值记录信息
     */
    @Override
    public List<CRechardOrderModel> rechardRecord(String userId,Integer start,Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        return cRechardOrderDao.selectRechardRecordByUserId(page);
    }

    /**
     * 余额变动信息
     */
    @Override
    public List<CUserBalanceRecordModel> balanceRecord(String userId,Integer start,Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        return cUserBalanceRecordDao.selectBalanceRecordByUserId(page);
    }

    /**
     * 根据用户id获取我的帐户余额
     */
    @Override
    public Integer getMyBalance(Integer userId) {
        CUserBalanceModel cUserBalance = cUserBalanceDao.selectByCUserId(userId);
        if(cUserBalance!=null){
            return cUserBalance.getAccountBalance();
        }
        return 0;
    }

    /**
     * 获取我的佣金记录
     * @param userId:用户外键
     */
    @Override
    public List<CUserBalanceRecordModel> getMyCommissionRechord(String userId, Integer start, Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        page.getParams().put("operateType", 5);
        return cUserBalanceRecordDao.selectBalanceRecordByUserId(page);
    }
}