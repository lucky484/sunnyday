package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.dao.market.BGoodsDao; 
import com.f2b2c.eco.dao.market.BSalesOrderDao;
import com.f2b2c.eco.dao.market.BSalesOrderDetailsDao;
import com.f2b2c.eco.dao.market.CLogisticsCodeDao;
import com.f2b2c.eco.dao.market.CUserBalanceDao;
import com.f2b2c.eco.dao.market.CUserBalanceRecordDao;
import com.f2b2c.eco.model.market.BGoodsToCModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.CLogisticsCodeModel;
import com.f2b2c.eco.model.market.CUserBalanceModel;
import com.f2b2c.eco.model.market.CUserBalanceRecordModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.service.market.BSalesOrderService;
import com.f2b2c.eco.service.market.BScoreService;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.util.ConstantUtil.OrderStatus;

@Service
public class BSalesOrderServiceImpl implements BSalesOrderService {

    @Autowired
    private BSalesOrderDao bSalesOrderDao;
 
    @Autowired
    private BSalesOrderDetailsDao bSalesOrderDetailsDao;
    
    @Autowired
    private BGoodsDao bGoodsDao;
    
    @Autowired 
    private CLogisticsCodeDao cLogisticsCodeDao;
    
    @Autowired
    private CUserBalanceDao cUserBalanceDao;
    
    @Autowired
    private CUserBalanceRecordDao cUserBalanceRecordDao;
    
    @Autowired
    private BScoreService bScoreService;
    
    @Autowired
    private AccountService accountService;
    
    @Override
    public List<BToCOrderModel> queryAllOrder(Integer num, Integer pageSize,
            Integer userId,Integer status,Integer returnStatus) {

        return bSalesOrderDao.queryAllOrder(num, pageSize, userId,status,returnStatus);
    }

    @Override
    public int queryAllOrderCount(Integer userId,Integer status,Integer returnStatus) {

        return bSalesOrderDao.queryAllOrderCount(userId,status,returnStatus);
    }

    @Override
    public int deleteOrderById(String id,Integer status) {
        if(status == 2){
            //如果订单是待支付的状态，删除订单时，要将订单的数量还回去
            List<BSalesOrderDetailsModel> list = bSalesOrderDetailsDao.queryGoodsByOrderId(id);
            bGoodsDao.updateBatchGoodsQuantityPlus(list);  //批量更新订单数量
        }
        int result = bSalesOrderDao.deleteOrderById(id);  //删除订单
        bSalesOrderDetailsDao.updateOrderDetailsByOrderId(id); //删除订单详情
        return result;
    }

    @Override
    public int updateStatusById(BSalesOrderModel bSalesOrder) {
        int state = 0;
        int status = bSalesOrder.getStatus();
        List<BSalesOrderDetailsModel> list = new ArrayList<BSalesOrderDetailsModel>();
        //如果订单的状态为待评价，说明已经确认收货了，需要释放商品库存中锁定的数量
        if(status == 1){
            bSalesOrder.setReceiveTime(new Date());
            list = bSalesOrderDetailsDao.queryGoodsByOrderId(bSalesOrder.getId());
            state = bGoodsDao.updateBatchGoodsAllocate(list);
            if(state==0){
                return state;
            }
        }
        if(status==1){
            int size = 0;
            BToCOrderModel btc = bSalesOrderDao.queryAllOrderDetail(bSalesOrder.getId());
            // 用户购买商品 一元一个积分，在数据库中保存以分为准。只有金额大于100才算积分
            Integer total  = btc.getRealPay() - btc.getFreight();
            // 获取订单表catalog标识
//            String catalog = String.valueOf(btc.getCatalog());
            if(total >= 100){
                // 计算积分
                size = bScoreService.settleScore(btc.getUserId(), total);
                if(size==0){
                    return size;
                }
            }     
            // 卖家金额小于1分钱则不计算佣金
            if(total >= 1){
                // 计算佣金
/*                if(CatalogEnum.nonfruit.toString().equals(catalog)){*/
                    list = bSalesOrderDetailsDao.queryGoodsByOrderId(bSalesOrder.getId());
                    size = accountService.settleCommission(list, btc.getUserId(),btc.getOrderNo());
/*                }*/
            }
        }
        return bSalesOrderDao.updateStatusById(bSalesOrder);
    }

    @Override
    public BSalesOrderModel queryOrderDetailById(String id) {
        
        return bSalesOrderDao.queryOrderDetailById(id);
    }

    @Override
    public List<BSalesOrderModel> queryTradInfo(Integer userId,Integer page,Integer pageSize) {
        
        return bSalesOrderDao.queryTradInfo(userId,page,pageSize);
    }

    @Override
    public int queryTradInfoCount(Integer userId) {
    
        return bSalesOrderDao.queryTradInfoCount(userId);
    }

    @Override
    public List<BToCOrderModel> queryAllSalesOrder(Integer num,
            Integer pageSize, Integer userId, Integer status) {
        
        return bSalesOrderDao.queryAllSalesOrder(num, pageSize, userId, status);
    }

    @Override
    public int queryAllSalesOrderCount(Integer userId, Integer status) {
        
        return bSalesOrderDao.queryAllSalesOrderCount(userId, status);
    }

    @Override
    public int deleteSalesOrderById(String id) {
            int result = bSalesOrderDao.deleteSalesOrderById(id);  //删除订单
            bSalesOrderDetailsDao.updateSalesOrderDetailsByOrderId(id); //删除订单详情
            return result;
    }

    @Override
    public BToCOrderModel queryAllOrderDetail(String orderId) {
        
        return bSalesOrderDao.queryAllOrderDetail(orderId);
    }

    @Override
    public List<BSalesOrderModel> queryUnPayOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryUnPayOrder(date, time);
    }

    @Override
    public void deleteTimeoutOrder(List<BSalesOrderModel> list,Integer status) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("status", status);
        if(status == 1){
            map.put("receiveTime", new Date());
        }
        if(status == 6){
            map.put("cancelTime", new Date());
        }
        bSalesOrderDao.deleteTimeoutOrder(map);
       // bSalesOrderDao.deleteTimeoutOrderDetails(list);
    }
    

    @Override
    public Page<BSalesOrderModel> querySaleOrderByBUserId(Pageable pageable,Map<String, Object> map) {
        int total = bSalesOrderDao.querySaleOrderByBUserIdCount(map);
        map.put("num", pageable.getPageNumber());
        map.put("pageSize", pageable.getPageSize());
        List<BSalesOrderModel> list = bSalesOrderDao.querySaleOrderByBUserId(map);
        Page<BSalesOrderModel> pages = new Pagination<>(list, pageable, total);
        return pages;
    }

    /**
     * 获取物流信息
     */
    @Override
    public Page<CLogisticsCodeModel> getLogisticsInfo(Pageable pageable,Map<String, Object> map) {
        int total = cLogisticsCodeDao.getLogisticsCodeSize(map);
        map.put("num", pageable.getPageNumber());
        map.put("pageSize", pageable.getPageSize());
        List<CLogisticsCodeModel> list = cLogisticsCodeDao.queryLogisticsCode(map);
        Page<CLogisticsCodeModel> pages = new Pagination<>(list, pageable, total);
        return pages;
    }

    /**
     * 根据订单号相关参数获取物流信息
     * @param orderNo:订单号
     * @param code:快递公司编码
     * @param number:运单号
     * @return 返回操作结果
     */
    @Override
    public int saveLogisticsInfo(String orderNo, String code, String number) {
        BSalesOrderModel bSalesOrder = new BSalesOrderModel();
        bSalesOrder.setOrderNo(orderNo);
        bSalesOrder.setLogisticsCode(code);
        bSalesOrder.setWaybillNumber(number);
        bSalesOrder.setStatus(OrderStatus.RECEIPT_OF_GOODS);
        bSalesOrder.setDeliverTime(new Date());
        return bSalesOrderDao.updateLogisticByOrderNo(bSalesOrder);
    }

    @Override
    public Page<BSalesOrderDetailsModel> queryReturnOrder(Pageable pageable,Map<String, Object> map) {
            int total = bSalesOrderDetailsDao.queryReturnOrderCount(map);
            map.put("num", pageable.getPageNumber());
            map.put("pageSize", pageable.getPageSize());
            List<BSalesOrderDetailsModel> list = bSalesOrderDetailsDao.queryReturnOrder(map);
            Page<BSalesOrderDetailsModel> pages = new Pagination<>(list, pageable, total);
            return pages;
    }

    @Override
    public int agreeReturn(String orderId,String orderDetailId, Integer returnType,Integer userId, Integer returnAmount) {
        BSalesOrderDetailsModel bSalesOrderDetails = new BSalesOrderDetailsModel();
        if(returnType == 1){
            bSalesOrderDetails.setGoodsStatus(3);
        }else{
            bSalesOrderDetails.setGoodsStatus(4);
        }
        bSalesOrderDetails.setId(orderDetailId);
        bSalesOrderDetails.setUpdatedTime(new Date());
        int count = bSalesOrderDetailsDao.updateGoodsStatus(bSalesOrderDetails);
        if(count == 1){
            BSalesOrderModel bSalesOrder = new BSalesOrderModel();
            bSalesOrder.setId(orderId);
            bSalesOrder.setRealPay(returnAmount);
            bSalesOrder.setReturnTime(new Date());
            int totalCount = bSalesOrderDao.queryReturnCount(orderId);
            if(totalCount == 0){   //订单详情中没有退款或者退货中的订单详情时，改变整条订单的退货退款状态,由于事务还未提交，正在更新的这条数据在数据库中还未更新完成，所以在判断的时候要在正在更新的数据上判断
            	bSalesOrder.setReturnStatus(2);
            }
            bSalesOrderDao.updateTotalById(bSalesOrder); //修改订单的总价
            CUserBalanceModel cUserBalanceModel = cUserBalanceDao.selectByCUserId(userId);
            cUserBalanceModel.setcUserId(userId);
            cUserBalanceModel.setAccountBalance(returnAmount);
            int result = cUserBalanceDao.updateAccountBalanceByUserId(cUserBalanceModel);//将退款余额返回到余额中
            if(result == 1){
                CUserBalanceRecordModel record = new CUserBalanceRecordModel();
                record.setOperateType("3");
                if(returnType == 1){
                    record.setOperateContent("申请退款成功,退款"+MoneyUtil.divide(returnAmount, 100)+"元");
                }else{
                    record.setOperateContent("申请退货成功,退款"+MoneyUtil.divide(returnAmount, 100)+"元");
                }
                record.setcUserBalanceId(cUserBalanceModel.getId());
                record.setMoney(returnAmount);
                record.setTag("1");
                record.setCreateTime(new Date());
                cUserBalanceRecordDao.insertSelective(record);
            }
        }
        return count;
    }
    
    /**
     * 店家拒绝接单
     */
    @Override
    public int refuseOrder(BSalesOrderModel bSalesOrder) {
        int result = bSalesOrderDao.updateStatusById(bSalesOrder);
            if(result == 1){
                if(bSalesOrder.getReceiveOrder() == 2){
                    BToCOrderModel btc = bSalesOrderDao.queryAllOrderDetail(bSalesOrder.getId());
                    // 根据用户的id查询余额
                    CUserBalanceModel cUserBalanceModel = cUserBalanceDao.selectByCUserId(btc.getUserId());
                    cUserBalanceModel.setcUserId(btc.getUserId());
                    cUserBalanceModel.setAccountBalance(btc.getTotal());
                    int count = cUserBalanceDao.updateAccountBalanceByUserId(cUserBalanceModel);//将退款余额返回到余额中
                    if(count == 1){
                        CUserBalanceRecordModel record = new CUserBalanceRecordModel();
                        record.setOperateType("3");
                        record.setOperateContent("店家拒绝接单，退款"+MoneyUtil.divide(btc.getTotal(),100) + "元");
                        record.setcUserBalanceId(cUserBalanceModel.getId());
                        record.setMoney(btc.getTotal());
                        record.setTag("1");
                        record.setCreateTime(new Date());
                        cUserBalanceRecordDao.insertSelective(record);
                }
            }
        }
        return result;
    }

    /**
     * 店家拒绝退款
     */
    @Override
    public int refuseReturn(String orderId,String orderDetailId, Integer returnType) {
        BSalesOrderDetailsModel bSalesOrderDetails = new BSalesOrderDetailsModel();
        if(Integer.valueOf(returnType) == 1){
            bSalesOrderDetails.setGoodsStatus(5);
        }else{
            bSalesOrderDetails.setGoodsStatus(6);
        }
        bSalesOrderDetails.setId(orderDetailId);
        bSalesOrderDetails.setUpdatedTime(new Date());
        int count = bSalesOrderDetailsDao.updateGoodsStatus(bSalesOrderDetails);
        if(count == 1){
        	BSalesOrderModel bSalesOrder = new BSalesOrderModel();
        	int totalCount = bSalesOrderDao.queryReturnCount(orderId);
        	if(totalCount == 0){   //订单详情中没有退款或者退货中的订单详情时，改变整条订单的退货退款状态,由于事务还未提交，正在更新的这条数据在数据库中还未更新完成，所以在判断的时候要在正在更新的数据上判断
            	bSalesOrder.setReturnStatus(2);
            }
            bSalesOrder.setId(orderId);
            bSalesOrder.setReturnTime(new Date());
            bSalesOrderDao.updateTotalById(bSalesOrder); //修改订单的总价
        }
        return count;
    }

    @Override
    public int cancelOrder(Integer type, String orderId, Integer status) {
        BSalesOrderModel bSalesOrder = new BSalesOrderModel();
        BToCOrderModel btc = bSalesOrderDao.queryAllOrderDetail(orderId);//查询订单的详情
        bSalesOrder.setStatus(status);
        bSalesOrder.setId(orderId);
        bSalesOrder.setRealPay(btc.getRealPay());
        bSalesOrder.setUpdatedTime(new Date());
        bSalesOrder.setCancelTime(new Date());
        int result = bSalesOrderDao.updateStatusById(bSalesOrder);
        if(result == 1){
            CUserBalanceModel cUserBalanceModel = null;
            if(type == 1){  //表示商家未接单，用户取消订单
                // 根据用户的id查询余额
                cUserBalanceModel = cUserBalanceDao.selectByCUserId(btc.getUserId());
                cUserBalanceModel.setcUserId(btc.getUserId());
                cUserBalanceModel.setAccountBalance(btc.getTotal());
                int count = cUserBalanceDao.updateAccountBalanceByUserId(cUserBalanceModel);//将退款余额返回到余额中
                if(count == 1){
                    CUserBalanceRecordModel record = new CUserBalanceRecordModel();
                    record.setOperateType("3");
                    record.setOperateContent("用户取消订单，退款"+MoneyUtil.divide(btc.getTotal(),100) + "元");
                    record.setcUserBalanceId(cUserBalanceModel.getId());
                    record.setMoney(btc.getTotal());
                    record.setTag("1");
                    record.setCreateTime(new Date());
                    cUserBalanceRecordDao.insertSelective(record);
               }
            }else if(type == 2){ //表示商家接单，用户取消订单，但是要扣3块钱的配送费
                // 根据用户的id查询余额
                cUserBalanceModel = cUserBalanceDao.selectByCUserId(btc.getUserId());
                cUserBalanceModel.setcUserId(btc.getUserId());
                cUserBalanceModel.setAccountBalance(btc.getTotal() - 3*100);
                int count = cUserBalanceDao.updateAccountBalanceByUserId(cUserBalanceModel);//将退款余额返回到余额中
                if(count == 1){
                    CUserBalanceRecordModel record = new CUserBalanceRecordModel();
                    record.setOperateType("3");
                    record.setOperateContent("商家已接单,用户取消订单,需扣除3元配送费，退款"+MoneyUtil.divideAndMinus(btc.getTotal(), 100, 3) + "元");
                    record.setcUserBalanceId(cUserBalanceModel.getId());
                    record.setMoney(btc.getTotal());
                    record.setTag("1");
                    record.setCreateTime(new Date());
                    cUserBalanceRecordDao.insertSelective(record);
               }
            }
        }
        return result;
    }

    @Override
    public List<BSalesOrderModel> queryFeFruitUnPayOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryFeFruitUnPayOrder(date, time);
    }

    @Override
    public List<BSalesOrderModel> queryFruitPayOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryFruitPayOrder(date, time);
    }

    @Override
    public List<BSalesOrderModel> queryFeFruitPayOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryFeFruitPayOrder(date, time);
    }

    @Override
    public int updateStatusTo(BSalesOrderModel bSalesOrder) {
        
        return bSalesOrderDao.updateStatusById(bSalesOrder);
    }

    @Override
    public List<BSalesOrderModel> queryPayUnReceiveOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryPayUnReceiveOrder(date, time);
    }

    @Override
    public List<BSalesOrderModel> queryCheckOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryCheckOrder(date, time);
    }

    @Override
    public List<BSalesOrderModel> queryCheckFeFruitOrder(Date date, Integer time) {
        
        return bSalesOrderDao.queryCheckFeFruitOrder(date, time);
    }
    
    public void deleteTimeoutUnReceiverOrder(List<BSalesOrderModel> list,Integer status) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("status", status);
        if(status == 6){
            map.put("cancelTime", new Date());
        }
        bSalesOrderDao.deleteTimeoutOrder(map); //取消订单后，给用户 退款
        for(BSalesOrderModel bSalesOrder : list){
            BToCOrderModel btc = bSalesOrderDao.queryAllOrderDetail(bSalesOrder.getId());//查询订单的详情
            CUserBalanceModel cUserBalanceModel = null;
            // 根据用户的id查询余额
            cUserBalanceModel = cUserBalanceDao.selectByCUserId(btc.getUserId());
            cUserBalanceModel.setcUserId(btc.getUserId());
            cUserBalanceModel.setAccountBalance(btc.getTotal());
            int count = cUserBalanceDao.updateAccountBalanceByUserId(cUserBalanceModel);//将退款余额返回到余额中
            if(count == 1){
                CUserBalanceRecordModel record = new CUserBalanceRecordModel();
                record.setOperateType("3");
                record.setOperateContent("商家未接单，退款"+MoneyUtil.divide(btc.getTotal(), 100) + "元");
                record.setcUserBalanceId(cUserBalanceModel.getId());
                record.setMoney(btc.getTotal());
                record.setTag("1");
                record.setCreateTime(new Date());
                cUserBalanceRecordDao.insertSelective(record);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(    );
    }

    @Override
    public List<BGoodsToCModel> queryEvaluateOrderDetails(Map<String, Object> map) {
        
        return bSalesOrderDao.queryEvaluateOrderDetails(map);
    }

    @Override
    public int queryEvaluateOrderDetailsCount(Map<String, Object> map) {

        return bSalesOrderDao.queryEvaluateOrderDetailsCount(map);
    }

    @Override
    public int queryUnDealOrderByUserId(Integer userId) {
        
        return bSalesOrderDao.queryUnDealOrderByUserId(userId);
    }

	@Override
	public int queryReturnCount(String orderId) {
	
		return bSalesOrderDao.queryReturnCount(orderId);
	}

	@Override
	public BSalesOrderModel queryOrderById(String orderId) {
		
		return bSalesOrderDao.queryOrderById(orderId);
	}
}