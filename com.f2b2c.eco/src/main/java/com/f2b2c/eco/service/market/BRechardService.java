package com.f2b2c.eco.service.market;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.f2b2c.eco.model.market.CRechardOrderModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.model.market.DataToCResultModel;

/**
 * 充值service
 * @author jane.hui
 *
 */
public interface BRechardService {
    
    /**
     * 充值
     * @param money:充值金额
     * @return 返回充值状态
     */
    DataToCResultModel<String> save(HttpServletRequest request,HttpServletResponse response,String money,String payType,Integer userId);
    
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
    void notifyUrl(String app_id,String out_trade_no,String body,String buyer_id,String seller_id,String subject,String total_amount,String trade_no,String tradeContent);

    /**
     * 微信回调URL
     * @param record
     */
    void wxNotifyUrl(Map<String, String> map);
    
    /**
     * 根据用户id湖获取充值记录信息
     * @param userId:用户id
     * @return 返回充值记录信息
     */
    List<CRechardOrderModel> rechardRecord(String userId,Integer start,Integer length);
    
    /**
     * 根据用户id获取余额变动记录信息
     * @param userId:用户外键
     * @return 返回余额变动记录
     */
    List<CUserBalanceRecordModel> balanceRecord(String userId,Integer start,Integer length);
    
    /**
     * 获取我的佣金记录
     * @param userId:用户外键
     * @return 返回余额变动记录
     */
    List<CUserBalanceRecordModel> getMyCommissionRechord(String userId,Integer start,Integer length);
    
    /**
     * 根据用户id获取我的帐户余额
     * @param userId
     * @return
     */
    Integer getMyBalance(Integer userId);
}