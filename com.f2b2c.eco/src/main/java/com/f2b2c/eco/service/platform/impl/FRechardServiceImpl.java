package com.f2b2c.eco.service.platform.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.f2b2c.eco.dao.market.BUserBalanceDao;
import com.f2b2c.eco.dao.market.BUserBalanceRecordDao;
import com.f2b2c.eco.dao.platform.BRechardOrderDao;
import com.f2b2c.eco.dao.platform.BRechardPayRecordDao;
import com.f2b2c.eco.dao.platform.BWxRechardPayRecordDao;
import com.f2b2c.eco.dao.platform.Page;
import com.f2b2c.eco.model.market.BUserBalanceModel;
import com.f2b2c.eco.model.market.BUserBalanceRecordModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.BRechardOrderModel;
import com.f2b2c.eco.model.platform.BRechardPayRecordModel;
import com.f2b2c.eco.model.platform.BWxRechardPayRecordModel;
import com.f2b2c.eco.service.platform.BAccountService;
import com.f2b2c.eco.service.platform.FRechardService;
import com.f2b2c.eco.share.pay.alipay.config.BAlipayConfig;
import com.f2b2c.eco.share.pay.alipay.util.BPayUtil;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.share.pay.wechat.config.BWeixinConfig;
import com.f2b2c.eco.share.pay.wechat.util.BWeixinUtil;
import com.f2b2c.eco.status.CRechardOrderStatusEnum;
import com.f2b2c.eco.status.MoneyOperateEnum;
import com.f2b2c.eco.status.MoneyTagEnum;
import com.f2b2c.eco.status.SequenceType;
import com.f2b2c.eco.util.SequenceUtil;
import com.f2b2c.eco.util.ConstantUtil.PayType;
import jodd.util.StringUtil;

/**
 * 返回我的帐户余额
 * @author jane.hui
 *
 */
@Service("fRechardService")
public class FRechardServiceImpl implements FRechardService{

    @Autowired
    private BUserBalanceDao bUserBalanceDao;
    
    @Autowired
    private BUserBalanceRecordDao bUserBalanceRecordDao;
    
    @Autowired
    private BRechardOrderDao bRechardOrderDao;
    
    @Autowired
    private BRechardPayRecordDao bRechardPayRecordDao;
    
    @Autowired
    private BWxRechardPayRecordDao bWxRechardPayRecordDao;
    
    @Autowired
    private BAccountService bAccountService;
    
    /**
     * 获取我的帐户余额
     * @param userId:用户外键
     * @return 返回余额
     */
    @Override
    public Integer getMyBalance(Integer userId) {
        BUserBalanceModel bUserBalance = bUserBalanceDao.selectByBUserId(userId);
        if(bUserBalance!=null){
            return bUserBalance.getAccountBalance();
        }
        return 0;
    }

    /**
     * 获取我的佣金记录
     * @param userId:用户外键
     * @param start:起始页
     * @param length:页数大小
     * @return 获取我的佣金记录
     */
    @Override
    public List<BUserBalanceRecordModel> getMyCommissionRechord(String userId, Integer start, Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        page.getParams().put("operateType", 5);
        return bUserBalanceRecordDao.selectBalanceRecordByUserId(page);
    }

    /**
     * 余额变动记录
     * @param userId:用户外键
     * @param start:起始页
     * @param lenght:分页大小
     * @return 返回余额变动记录
     */
    @Override
    public List<BUserBalanceRecordModel> balanceRecord(String userId, Integer start, Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        return bUserBalanceRecordDao.selectBalanceRecordByUserId(page);
    }

    /**
     * 充值保存功能
     */
    @Override
    public DataToCResultModel<String> save(HttpServletRequest request, HttpServletResponse response, String money,
            String payType, Integer userId) {
        DataToCResultModel<String> result = new DataToCResultModel<String>();
        String orderInfo = "";
        String mergeOrderNo = SequenceUtil.nextId(SequenceType.R.toString());
        Integer orderPrice = MoneyUtil.fromYuanToIntFen(money.toString());
        // 插入充值订单表数据
        BRechardOrderModel bRechardOrder = new BRechardOrderModel();
        bRechardOrder.setOrderNo(mergeOrderNo);
        bRechardOrder.setPayType(payType);
        bRechardOrder.setStatus(CRechardOrderStatusEnum.WAIT_FOR_PAY.toString());
        bRechardOrder.setTotalFee(orderPrice);
        bRechardOrder.setCreateTime(new Date());
        bRechardOrder.setUserId(userId);
        int size = bRechardOrderDao.insertSelective(bRechardOrder); 
        if(size ==0){
            result.setMsg("充值失败");
            result.setStatus(400);
            return result;
        }
        if(PayType.WEIXIN.toString().equals(payType)){
            orderInfo = BWeixinUtil.pay(request, response, Integer.valueOf(orderPrice), "充值", mergeOrderNo, BWeixinConfig.recharge_notify_url);
        } else if(PayType.ALIPAY.toString().equals(payType)){
            orderInfo = BPayUtil.getInfo("充值", "充值", String.valueOf(money), mergeOrderNo, BAlipayConfig.recharge_notify_url);
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
            BRechardOrderModel bRechardOrder = bRechardOrderDao.selectByOutTradeNo(out_trade_no);
            BRechardOrderModel saveCRechardOrder = new BRechardOrderModel();
            if(bRechardOrder!=null){
                if(CRechardOrderStatusEnum.WAIT_FOR_PAY.toString().equals(bRechardOrder.getStatus())){
                    BRechardOrderModel rechard = bRechardOrderDao.selectByOutTradeNo(out_trade_no);
                    if(rechard!=null){
                        int state = bAccountService.saveBalance(rechard.getUserId(), MoneyUtil.fromYuanToIntFen(total_amount), out_trade_no,MoneyOperateEnum.RECHARD.toString(),"用户用支付宝充值了"+total_amount+"元",MoneyTagEnum.ADD.toString());
                        if(state==200){
                            saveCRechardOrder.setOrderNo(out_trade_no);
                            saveCRechardOrder.setStatus(CRechardOrderStatusEnum.ACCOUNT_PAID.toString());
                            bRechardOrderDao.updateByOrderNo(saveCRechardOrder);
                            BRechardPayRecordModel bRechardPayRecord = new BRechardPayRecordModel();
                            bRechardPayRecord.setOutTradeNo(out_trade_no);
                            bRechardPayRecord.setBody(body);
                            bRechardPayRecord.setBuyerId(buyer_id);
                            bRechardPayRecord.setSellerId(seller_id);
                            bRechardPayRecord.setSubject(subject);
                            bRechardPayRecord.setTotalAmount(total_amount);
                            bRechardPayRecord.setTradeNo(trade_no);
                            bRechardPayRecord.setTradeContent(tradeContent);
                            bRechardPayRecord.setCreateDate(new Date());
                            bRechardPayRecordDao.insertSelective(bRechardPayRecord);
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
            BRechardOrderModel bRechardOrder = bRechardOrderDao.selectByOutTradeNo(out_trade_no);
            BRechardOrderModel saveCRechardOrder = new BRechardOrderModel();
            if(bRechardOrder!=null){
                if(CRechardOrderStatusEnum.WAIT_FOR_PAY.toString().equals(bRechardOrder.getStatus())){
                    BRechardOrderModel rechard = bRechardOrderDao.selectByOutTradeNo(out_trade_no);
                    if(rechard!=null){
                        int state = bAccountService.saveBalance(rechard.getUserId(), Integer.valueOf(total_fee), out_trade_no,MoneyOperateEnum.RECHARD.toString(),"用户用微信充值了" + MoneyUtil.fromFenToYuan(total_fee) + "元",MoneyTagEnum.ADD.toString());
                        if(state==200){
                            saveCRechardOrder.setOrderNo(out_trade_no);
                            saveCRechardOrder.setStatus(CRechardOrderStatusEnum.ACCOUNT_PAID.toString());
                            bRechardOrderDao.updateByOrderNo(saveCRechardOrder);
                            BWxRechardPayRecordModel bWxRechardPayRecord = new BWxRechardPayRecordModel();
                            bWxRechardPayRecord.setOutTradeNo(out_trade_no);
                            bWxRechardPayRecord.setAppid(appid);
                            bWxRechardPayRecord.setMchId(mch_id);
                            bWxRechardPayRecord.setNonceStr(nonce_str);
                            bWxRechardPayRecord.setSign(sign);
                            bWxRechardPayRecord.setTradeType(trade_type);
                            bWxRechardPayRecord.setTotalFee(total_fee);
                            bWxRechardPayRecord.setOpenid(openid);
                            bWxRechardPayRecord.setResultCode(result_code);
                            bWxRechardPayRecord.setCreateDate(new Date());
                            bWxRechardPayRecord.setTransactionId(transaction_id);
                            bWxRechardPayRecordDao.insertSelective(bWxRechardPayRecord);
                        }
                    }
                }
            }
        }
    }
    

    /**
     * 充值记录
     * @param userId:用户外键
     * @param start:起始页
     * @param length:分页大小
     */
	@Override
	public List<BRechardOrderModel> rechardRecord(String userId, Integer start, Integer length) {
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        page.getParams().put("userId", userId);
        return bRechardOrderDao.selectRechardRecordByUserId(page);
	}

}