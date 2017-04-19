package com.f2b2c.eco.service.platform.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.f2b2c.eco.bean.platform.FDifferentOrderBean;
import com.f2b2c.eco.constant.platform.Constant.CheckStatus;
import com.f2b2c.eco.constant.platform.Constant.OrderType;
import com.f2b2c.eco.dao.market.BShopCarDao;
import com.f2b2c.eco.dao.market.PayRecordDao;
import com.f2b2c.eco.dao.market.WxPayRecordDao;
import com.f2b2c.eco.dao.platform.FDiffenceDao;
import com.f2b2c.eco.dao.platform.FOrderDiffenceImgDao;
import com.f2b2c.eco.dao.platform.FSalesOrderDao;
import com.f2b2c.eco.dao.platform.FSalesOrderDetailsDao;
import com.f2b2c.eco.dao.platform.FShopCarDao;
import com.f2b2c.eco.model.market.BToCOrderModel;
import com.f2b2c.eco.model.market.PayRecordModel;
import com.f2b2c.eco.model.market.WxPayRecordModel;
import com.f2b2c.eco.model.platform.FAgentProfitModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.model.platform.FUserModel;
import com.f2b2c.eco.param.Pagination;
import com.f2b2c.eco.service.platform.FAgentProfitService;
import com.f2b2c.eco.service.platform.FSalesOrderService;
import com.f2b2c.eco.service.platform.FUserService;
import com.f2b2c.eco.status.RoleTypeEnum;
import com.f2b2c.eco.status.SequenceType;
import com.f2b2c.eco.util.CommonUtil;
import com.f2b2c.eco.util.ConstantUtil.OrderStatus;
import com.f2b2c.eco.util.LogoUrlUtil;
import com.f2b2c.eco.util.SequenceUtil;

import jodd.util.StringUtil;

/**
 * 平台订单服务实现类
 * 
 * @author brave.chen
 *
 */
@Service
public class FSalesOrderServiceImpl implements FSalesOrderService {

    @Autowired
    private FSalesOrderDao fSalesOrderDao;
    
    @Autowired
    private FSalesOrderDetailsDao fSalesOrderDetailsDao;
    
    @Autowired
    private FShopCarDao fShopCarDao;
    
    @Autowired
    private PayRecordDao payRecordDao;
    
    @Autowired
    private BShopCarDao bShopCarDao;
    
    @Autowired
    private WxPayRecordDao wxPayRecordDao;
    
    @Autowired
    private FDiffenceDao fDiffenceDao;
    
    @Autowired
    private FOrderDiffenceImgDao fOrderDiffenceImgDao;
    
    @Autowired
    private FUserService fUserService;
    
    @Autowired
    private FAgentProfitService fAgentProfitService;
    
    
    
    @Override
    public List<FSalesOrderModel> queryAllOrders() {
        return fSalesOrderDao.findAll();
    }

    @Override
    public void updateFSalesOrderModel(FSalesOrderModel fSalesOrderModel) {

        if (null != fSalesOrderModel) {
            fSalesOrderDao.update(fSalesOrderModel);
            fSalesOrderDao.deleteOrderDetailsByOrderId(fSalesOrderModel.getOrderId());
            if (CollectionUtils.isNotEmpty(fSalesOrderModel.getfSalesOrderDetailsModel())) {
                fSalesOrderDao.addOrderDetails(fSalesOrderModel.getfSalesOrderDetailsModel());
            }
        }
    }

    @Override
    public List<FSalesOrderModel> queryOrdersByAgentUserId(FUserModel fUserModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveOrderModels(FSalesOrderModel fSalesOrderModel) {
        fSalesOrderDao.insert(fSalesOrderModel);
    }

    @Override
    public Page<FSalesOrderModel> findWithPaginationByUser(Map<String, Object> map) {
        Pageable pageable = (Pageable) map.get("pageable");
		map.put("pageNumber", pageable.getPageNumber());
		map.put("pageSize", pageable.getPageSize());
        List<FSalesOrderModel> content = new ArrayList<>();
        int recordsTotal = 0;
        
		// 目前没有取消的订单状态，后续有修改对应逻辑
        if (!OrderType.HAD_CANCLE.equals(map.get("orderStatus")))
        {
			map.put("orderStatus", map.get("orderStatus"));
			content = fSalesOrderDao.findOrderListWithStatus(map);
			recordsTotal = fSalesOrderDao.getOrderCountWithStatus(map);
        }

        Page<FSalesOrderModel> page = new Pagination<>(content, pageable, recordsTotal);
        return recordsTotal == 0 ? new Pagination<>(new ArrayList<FSalesOrderModel>()) : page;
    }

    @Override
    public void deleteOrderByOrderId(FSalesOrderModel fSalesOrder) {
		// 如果订单是待支付状态，则要将商品的数量换回去
        if(fSalesOrder.getStatus() == 2){
			// 更新商品的库存
            List<FSalesOrderDetailsModel> list = fSalesOrderDetailsDao.queryAllOrderDetailsByOrderId(fSalesOrder.getId());
            fSalesOrderDetailsDao.updateBatchGoodsQuantityPlus(list);
        }
         fSalesOrderDao.deleteByOrderId(fSalesOrder.getId());
		fSalesOrderDao.deleteDetailsByOrderId(fSalesOrder.getId()); // 删除订单的同时，删除订单详情
    }

    @Override
    public FSalesOrderModel queryById(String id) {
        return fSalesOrderDao.queryOrderById(id);
    }
    
    @Override
    public Page<FSalesOrderModel> findWithPagination(Map<String, Object> map) {
        Pageable pageable = (Pageable) map.get("pageable");
		map.put("pageNumber", pageable.getPageNumber());
		map.put("pageSize", pageable.getPageSize());

		List<FSalesOrderModel> content = fSalesOrderDao.findOrderRecord(map);
		int recordsTotal = fSalesOrderDao.orderRecordCount(map);
        Page<FSalesOrderModel> page = new Pagination<>(content, pageable, recordsTotal);
        return recordsTotal == 0 ? new Pagination<>(new ArrayList<FSalesOrderModel>()) : page;
    }

    public void deteOrderDetails(List<String> deleteIds) {
        fSalesOrderDao.deleteDetails(deleteIds);
    }

    @Override
    public void updateDetailsModel(List<FSalesOrderDetailsModel> detailsModels) {
        fSalesOrderDao.updateDetailsModel(detailsModels);
    }

    @Override
    public int createOrder(FSalesOrderModel fSalesOrder) {
        List<FSalesOrderDetailsModel> orderDetaisList = new ArrayList<FSalesOrderDetailsModel>();
		// 先生成订单
        FSalesOrderModel salesOrder = new FSalesOrderModel();
        salesOrder.setId(CommonUtil.generate32UUID());
        salesOrder.setOrderId(SequenceUtil.nextId(SequenceType.F.toString()));
        salesOrder.setUserId(fSalesOrder.getUserId());
        salesOrder.setCreatedTime(new Date());
        salesOrder.setReceiverName(fSalesOrder.getReceiverName());
        salesOrder.setReceiverPhone(fSalesOrder.getReceiverPhone());
        salesOrder.setTotal(fSalesOrder.getTotal());
        salesOrder.setPayType(fSalesOrder.getPayType());
        salesOrder.setStatus(2);
        salesOrder.setRemark(fSalesOrder.getRemark());
        salesOrder.setUpdatedTime(new Date());
//        salesOrder.setRealPay(fSalesOrder.getTotal());
        salesOrder.setReceiveTimeType(fSalesOrder.getReceiveTimeType());
        salesOrder.setReceiverAddress(fSalesOrder.getReceiverAddress());
		int result = fSalesOrderDao.insert(salesOrder); // 保存订单
		// 这里是从平台买东西，不存在多店家，所以只生成一条订单，由于有多个商品需要生成多个订单的详情
		// 从购物车中生成的订单，在生成订单后，清空购物车
        if(fSalesOrder.getTag().equals("1")){
            for(FSalesOrderDetailsModel FSalesOrderDetails : fSalesOrder.getfSalesOrderDetailsModel()){
                FSalesOrderDetailsModel orderDerails = new FSalesOrderDetailsModel();
                orderDerails.setId(CommonUtil.generate32UUID());
                orderDerails.setOrder(salesOrder);
                orderDerails.setGoodsId(orderDerails.getGoodsId());
                orderDerails.setGoodsName(FSalesOrderDetails.getGoodsName());
                orderDerails.setGoodsQty(FSalesOrderDetails.getGoodsQty());
                orderDerails.setGoodsVersion(FSalesOrderDetails.getGoodsVersion());
                orderDerails.setPrice(FSalesOrderDetails.getPrice());
                orderDerails.setCreatedTime(new Date());
                orderDerails.setCreatedUser(salesOrder.getUserId());
                orderDerails.setUpdatedTime(new Date());
                orderDerails.setUpdateUser(salesOrder.getUserId());
                orderDetaisList.add(orderDerails);
            }
			// 批量插入订单详情
            fSalesOrderDetailsDao.insertBatchOrderDetail(orderDetaisList);
			// 清空购物车
            fShopCarDao.deleteShopCarById(fSalesOrder.getShopCarId());
			// 批量修改库存
            fSalesOrderDetailsDao.updateBatchGoodsQuantity(orderDetaisList);
        }
        return result;
    }

    @Override
    public void deleteOrderDetails(List<String> deleteIds) {
        fSalesOrderDao.deleteOrderDetails(deleteIds);
    }
    
    @Override
    public Map<String, Object> getOrderInfo(String orderId) {
        FSalesOrderModel order=fSalesOrderDao.findOne(orderId);
        
        Map<String, Object> map=new HashMap<>();
        if(null!=order){
            map.put("order", order);
        }
        return map;
    }

    @Override
    public void updateOrderStatus(Map<String, Object> map) {
        fSalesOrderDao.updateOrderStatus(map);
    }

    @Override
    public List<BToCOrderModel> findBusinessPurchesOrder(Map<String, Object> map) {
        List<BToCOrderModel> content=fSalesOrderDao.findBusinessPurchesOrderRecord(map);
//        int recordsTotal=fSalesOrderDao.findBusinessPurchesOrderCount(map);
//        Page<BToCOrderModel> page=new Pagination<>(content, (Pageable)map.get("pageable"), recordsTotal);
        return content;
    }
    
    @Override
    public int findBusinessPurchesOrderCount(Map<String,Object> map){
        
        int recordsTotal=fSalesOrderDao.findBusinessPurchesOrderCount(map);
        
        return recordsTotal;
    }

    @Override
    public FSalesOrderModel findOneOrder(Integer bUserId, String date) {
        if(null==bUserId||null==date){
            return null;
        }
        Map<String, Object> map=new HashMap<>();
        map.put("userId", bUserId);
        map.put("date", date);
        return fSalesOrderDao.findOneOrder(map);
    }

    @Override
    public int save(FSalesOrderModel entity,Integer updateOrInsert) {
        if(null==entity){
            return 0;
        }
        int result = 0;
        List<FSalesOrderDetailsModel> details=entity.getfSalesOrderDetailsModel();
		if (updateOrInsert > 0) { // 如果当前用户存在订单，则不需要新增订单，修改原来的订单详情即可
            for(FSalesOrderDetailsModel fSalesOrderDetails : details){
                int count = fSalesOrderDetailsDao.queryOrderDetailByGoodsId(fSalesOrderDetails.getGoodsId(),fSalesOrderDetails.getOrder().getId());
				// 如果count大于0说明现在加入订单的商品在原来订单中存在，直接修改原订单中该商品的数量
                if(count > 0){
                    fSalesOrderDetails.setUpdatedTime(new Date());
                    fSalesOrderDetailsDao.updateGoodsQtyByGoodsId(fSalesOrderDetails);
				} else { // 如果count不大于0说明，该商品在原来的订单中不存在，直接新增订单详情
                    fSalesOrderDetailsDao.insertOrderDetail(fSalesOrderDetails);
                }
            }
			// 修改订单的总价格和商品的总数量
             result = fSalesOrderDao.updateTotalById(entity);
		} else { // 如果当前用户中不存在订单，则新增订单和批量新增订单详情
            result = fSalesOrderDao.insert(entity);
            fSalesOrderDetailsDao.insertBatchOrderDetail(details);
        }
		// 新增订单的后续业务处理包括删除原来购物车中的商品和修改商品的库存
        if(CollectionUtils.isNotEmpty(details)){
            List<Integer> cartId = new ArrayList<Integer>();
            for (FSalesOrderDetailsModel  dtl : details) {
                if(dtl.getCartId() != null){
                    cartId.add(dtl.getCartId());
                }  
            }
            if(null != cartId && cartId.size() > 0){
                Map<String, Object> map=new HashMap<>();
                map.put("userId", entity.getUserId());
                map.put("list", cartId);
				// 删除根据用户名称goodsid购物车
                bShopCarDao.deleteBatch(map);
            }
			// 批量修改库存
            fSalesOrderDetailsDao.updateBatchGoodsQuantity(details);
        }
        
        return result;
    }
    
	/**
	 * 支付宝支付后的回调处理
	 */
    @Override
    public void notifyUrl(String app_id, String out_trade_no, String body,String buyer_id, String seller_id, String subject,
            String total_amount, String trade_no, String tradeContent) {
        if(StringUtil.isNotEmpty(out_trade_no)){
            List<FSalesOrderModel> list = fSalesOrderDao.queryStatusByOrderNo(out_trade_no);
            int tag = 0;
            for (FSalesOrderModel fSalesOrder : list) {
                if(fSalesOrder.getStatus().equals(OrderStatus.COMPLETED)){
                    tag = 1;
                }
                if(tag == 0){
					// 修改订单的状态，担保付款时如果线上支付时，同时修改订单的状态
                	if(fSalesOrder.getPayType() == 0){
                		fSalesOrder.setStatus(1);
                		fSalesOrder.setPayTime(new Date());
                		fSalesOrder.setMergeOrderNo(out_trade_no);
                		fSalesOrderDao.updateStatusByNo(fSalesOrder);
                		getAgent(out_trade_no,total_amount);
                	}
                }
            }
            if(tag==0){
            //    fSalesOrderDao.updateStatusByOrderNo(out_trade_no);
                PayRecordModel payRecord = new PayRecordModel();
                payRecord.setOutTradeNo(out_trade_no);
                payRecord.setBody(body);
                payRecord.setBuyerId(buyer_id);
                payRecord.setSellerId(seller_id);
                payRecord.setSubject(subject);
                payRecord.setTotalAmount(total_amount);
                payRecord.setTradeNo(trade_no);
                payRecord.setTradeContent(tradeContent);
                payRecordDao.insertSelective(payRecord);
            }
            }
        
        		
        
    }
    
    public void getAgent(String out_trade_no,String total_amount){
		// 2016-10-14 add 分润代码，当订单已经确认收获完成的时候需要进行分润的操作
			/**
		 * 1、找到这个订单--->所属的店老板--->所属的店铺--->所属的专属顾问--->所属的区域管理员--->所属的市级管理员---
		 * >所属的省级管理员 2、找到各级合伙人的分润比例，算出分润的金额。同时加入到合伙人的分润余额中 3、记录一条数据到合伙人分润的表中，
		 * 4、该角色中能唯一标识的是区域id、市级id、省级id加上角色能查询唯一的一条记录
		 */
	    String orderId = fSalesOrderDao.findOrderIdByMergeOrderNo(out_trade_no);
			Integer areaId = fSalesOrderDao.getAreaIdByOrderId(orderId);
			FUserModel districtUser = fUserService.getUserByAreaId(areaId,RoleTypeEnum.DISTRICT.toString());
		// 根据区域id查询出市级id,再根据角色查询出唯一的市级合伙人
			Integer cityId = fUserService.getCityIdByAreaId(areaId);
			FUserModel cityUser = fUserService.getUserByCityId(cityId,RoleTypeEnum.CITY.toString());
		// 根据区域id查询出省级id,再根据角色查询出唯一的省级合伙人
			Integer provinceId = fUserService.getProvinceIdByAreaId(areaId);
			FUserModel provinceUser = fUserService.getUserByProvinceId(provinceId,RoleTypeEnum.PROVINCE.toString());
			if(districtUser!=null){
			// 获取区域合伙人的分润比例
				BigDecimal shareProfit = districtUser.getShareProfitPer();
			// 算出区域合伙人的金额
				BigDecimal districtSum = new BigDecimal(total_amount).multiply(shareProfit);
			// 将分润金额加入区域合伙人的钱包中
				BigDecimal totalSum = districtUser.getBalance().add(districtSum);
			// 同步更新钱包
				districtUser.setBalance(totalSum);
				fUserService.updateFUserModelInfo(districtUser);
			// 向分润的表中添加一条记录
				saveAgentProfit(districtUser,districtSum,orderId,new Double(Double.valueOf(total_amount) * 100).intValue(),shareProfit,RoleTypeEnum.DISTRICT.toString());
				
			}
			if(cityUser!=null){
			// 获取市级合伙人的分润比例
				BigDecimal shareProfit = cityUser.getShareProfitPer();
			// 算出市级合伙人的金额
				BigDecimal citySum = new BigDecimal(total_amount).multiply(shareProfit);
			// 将分润金额加入市级别合伙人的钱包中
				BigDecimal totalSum = districtUser.getBalance().add(citySum);
			// 同步更新钱包
				cityUser.setBalance(totalSum);
				fUserService.updateFUserModelInfo(cityUser);
			// 向分润的表中添加一条记录
				saveAgentProfit(cityUser,citySum,orderId,new Double(Double.valueOf(total_amount) * 100).intValue(),shareProfit,RoleTypeEnum.CITY.toString());
				
			}
			if(provinceUser!=null){
			// 获取省级合伙人的分润比例
				BigDecimal shareProfit = provinceUser.getShareProfitPer();
			// 算出省级合伙人的金额
				BigDecimal provinceSum = new BigDecimal(total_amount).multiply(shareProfit);
			// 将分润金额加入省级别合伙人的钱包中
				BigDecimal totalSum = districtUser.getBalance().add(provinceSum);
			// 同步更新钱包
				provinceUser.setBalance(totalSum);
				fUserService.updateFUserModelInfo(provinceUser);
			// 向分润的表中添加一条记录
				saveAgentProfit(provinceUser,provinceSum,orderId,new Double(Double.valueOf(total_amount) * 100).intValue(),shareProfit,RoleTypeEnum.PROVINCE.toString());
				
			}
    }

    private void saveAgentProfit(FUserModel districtUser, BigDecimal districtSum, String orderId, Integer real,
			BigDecimal shareProfit,String roleId) {
		FAgentProfitModel agentProfitModel = new FAgentProfitModel();
		agentProfitModel.setUserId(districtUser.getId());
		FSalesOrderModel orderModel = new FSalesOrderModel();
		orderModel.setId(orderId);
		agentProfitModel.setOrder(orderModel);
		agentProfitModel.setCommissionAmount(districtSum);
		agentProfitModel.setCreatedtime(new Date());
		agentProfitModel.setOrderAmount(real);
		agentProfitModel.setProfitPercent(shareProfit);
		agentProfitModel.setRoleId(Integer.parseInt(roleId));
		fAgentProfitService.saveAgentProfit(agentProfitModel);
		
	}
    
    @Override
    public BToCOrderModel findBusinessPurchesOrderDetail(String orderId) {
        
        return fSalesOrderDao.findBusinessPurchesOrderDetail(orderId);
    }
    
    
     /**
	 * 微信回调URL
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
            List<FSalesOrderModel> list = fSalesOrderDao.queryStatusByOrderNo(out_trade_no);
            int tag = 0;
            for (FSalesOrderModel fSalesOrder : list) {
                if(fSalesOrder.getStatus().equals(OrderStatus.COMPLETED)){
                    tag = 1;
                }
				// 修改订单的状态，担保付款时如果线上支付时，同时修改订单的状态
                if(tag == 0){
                	if(fSalesOrder.getPayType() == 0){
                    	fSalesOrder.setStatus(1);
                    	fSalesOrder.setPayTime(new Date());
                    	fSalesOrder.setMergeOrderNo(out_trade_no);
                    	fSalesOrderDao.updateStatusByNo(fSalesOrder);
                    	getAgent(out_trade_no,String.valueOf(Double.valueOf(total_fee)/100));
                    }
                }
            }
            if(tag==0){
                WxPayRecordModel record = new WxPayRecordModel();
                record.setOutTradeNo(out_trade_no);
                record.setAppid(appid);
                record.setMchId(mch_id);
                record.setNonceStr(nonce_str);
                record.setSign(sign);
                record.setTradeType(trade_type);
                record.setTotalFee(total_fee);
                record.setOpenid(openid);
                record.setResultCode(result_code);
                record.setCreateDate(new Date());
                record.setTransactionId(transaction_id);
                wxPayRecordDao.insertSelective(record);
            }
        }
        

        
        
    }

    /**
	 * 根据主键查询补差价订单信息
	 */
    @Override
    public FDifferentOrderBean showDifferentOrder(String id) {
        FDifferentOrderBean fDifferentOrderBean = fDiffenceDao.selectOrderDiffenceById(id);
        if(fDifferentOrderBean!=null){
            List<String> list = fOrderDiffenceImgDao.selectImgListByDifferentId(fDifferentOrderBean.getId());
            List<String> imgList = new ArrayList<String>();
            for (String img : list) {
                imgList.add(LogoUrlUtil.splitOneImgUrl(img));
            }
            fDifferentOrderBean.setImgList(imgList);
        }
        return fDifferentOrderBean;
    }

    /**
	 * 审核补差价订单信息
	 * 
	 * @param id:补差价id
	 * @param
	 */
    @Override
    public Integer checkDiffOrder(String id,String type, String orderId,Integer realPay,Integer money) {
        FDifferentOrderBean fDifferentOrderBean = new FDifferentOrderBean();
        fDifferentOrderBean.setId(id);
        if(type.equals(CheckStatus.AUDIT_PASS)){
        	if(realPay.equals(money)){
                fDifferentOrderBean.setOrderId(orderId);
        		fDifferentOrderBean.setStatus("1");
        		fDifferentOrderBean.setRealPay(0);
        	} else {
                fDifferentOrderBean.setOrderId(orderId);
                fDifferentOrderBean.setRealPay(realPay-money);
                fDifferentOrderBean.setStatus(null);
        	}
            int size = fSalesOrderDao.updateOrderPriceByOrderId(fDifferentOrderBean);
            if(size==0){
                return size;
            }
        }    
        fDifferentOrderBean.setStatus(type);
        return fDiffenceDao.updateDiffOrderStatus(fDifferentOrderBean);
    }

	@Override
	public Integer getAreaIdByOrderId(String orderId) {
		
		return fSalesOrderDao.getAreaIdByOrderId(orderId);
	}

	@Override
	public BToCOrderModel findOrderByOrderId(String orderId) {
		return fSalesOrderDao.findOrderByOrderId(orderId);
	}

	@Override
	public FSalesOrderModel getTotalOrders(Map<Object, Object> paramMap) {
		
		return fSalesOrderDao.getTotalOrders(paramMap);
	}

	@Override
	public FSalesOrderModel queryUnPayOrder(Integer userId) {
		
		return fSalesOrderDao.queryUnPayOrder(userId);
	}


	@Override
	public int updateOrderDeleteTimeById(String orderId) {
		
		return fSalesOrderDao.updateOrderDeleteTimeById(orderId);
	}
	
}