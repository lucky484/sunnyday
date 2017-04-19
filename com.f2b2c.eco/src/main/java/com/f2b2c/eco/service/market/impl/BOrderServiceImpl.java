package com.f2b2c.eco.service.market.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b2c.eco.dao.market.BGoodsDao;
import com.f2b2c.eco.dao.market.BSalesOrderDao;
import com.f2b2c.eco.dao.market.BSalesOrderDetailsDao;
import com.f2b2c.eco.dao.market.BShopFreightDao;
import com.f2b2c.eco.dao.market.CDeliveryAddressDao;
import com.f2b2c.eco.dao.market.CLogisticsCodeDao;
import com.f2b2c.eco.dao.market.CShopCarDao;
import com.f2b2c.eco.dao.market.PayRecordDao;
import com.f2b2c.eco.dao.market.WxPayRecordDao;
import com.f2b2c.eco.model.bean.BGoodsInfoBean;
import com.f2b2c.eco.model.bean.BShopCarBean;
import com.f2b2c.eco.model.bean.BShopInfoBean;
import com.f2b2c.eco.model.bean.CLogisticBean;
import com.f2b2c.eco.model.bean.TracesBean;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BGoodsModel;
import com.f2b2c.eco.model.market.BGoodsToOrderModel;
import com.f2b2c.eco.model.market.BGoodsToPurchaseOrderModel;
import com.f2b2c.eco.model.market.BSalesOrderDetailsModel;
import com.f2b2c.eco.model.market.BSalesOrderModel;
import com.f2b2c.eco.model.market.BShopInfoModel;
import com.f2b2c.eco.model.market.BShopToOrderModel;
import com.f2b2c.eco.model.market.BtoCBSalesOrderModel;
import com.f2b2c.eco.model.market.CDeliveryAddressModel;
import com.f2b2c.eco.model.market.CLogisticsCodeModel;
import com.f2b2c.eco.model.market.CShopCarModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.market.InsertCDeliveryAddressModel;
import com.f2b2c.eco.model.market.Page;
import com.f2b2c.eco.model.market.PayRecordModel;
import com.f2b2c.eco.model.market.WxPayRecordModel;
import com.f2b2c.eco.service.market.AccountService;
import com.f2b2c.eco.service.market.BOrderService;
import com.f2b2c.eco.share.api.KdniaoTrackQueryAPI;
import com.f2b2c.eco.share.pay.alipay.config.AlipayConfig;
import com.f2b2c.eco.share.pay.alipay.util.MoneyUtil;
import com.f2b2c.eco.share.pay.alipay.util.PayUtil;
import com.f2b2c.eco.share.pay.wechat.config.WeixinConfig;
import com.f2b2c.eco.share.pay.wechat.util.WeixinUtil;
import com.f2b2c.eco.status.CatalogEnum;
import com.f2b2c.eco.status.SequenceType;
import com.f2b2c.eco.util.CommonUtil;
import com.f2b2c.eco.util.ConstantUtil.OrderStatus;
import com.f2b2c.eco.util.ConstantUtil.PayType;
import com.f2b2c.eco.util.DistanceUtil;
import com.f2b2c.eco.util.JsonUtil;
import com.f2b2c.eco.util.LogoUrlUtil;
import com.f2b2c.eco.util.SequenceUtil;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * 订单实现类
 * 
 * @author jane.hui
 *
 */
@Service("orderService")
@Transactional
public class BOrderServiceImpl implements BOrderService{

    /**
     * 日志记录器
     */
    private Logger logger=LoggerFactory.getLogger(BOrderServiceImpl.class);
    
    @Autowired 
    private CShopCarDao cShopCarDao;
    
    @Autowired
    private CDeliveryAddressDao cDeliveryAddressDao;
    
    @Autowired
    private BGoodsDao bGoodsDao;
    
    @Autowired 
    private BSalesOrderDao bSalesOrderDao;
    
    @Autowired
    private BSalesOrderDetailsDao bSalesOrderDetailsDao;
    
    @Autowired
    private PayRecordDao payRecordDao;
    
    @Autowired
    private WxPayRecordDao wxPayRecordDao;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private BShopFreightDao bShopFreightDao;
    
    @Autowired
    private CLogisticsCodeDao cLogisticsCodeDao;
    
    /**
     * 获取购物车表数据
     * 
     * @param userId:消费者外键
     * @return 返回购物车中所有的商品
     */
    @Override
    public List<CShopCarModel> getShopCarByUserId(Integer userId,List<Integer> list) {
        Page page = new Page();
        page.getParams().put("userId", userId);
        if(list!=null){
            page.getParams().put("list", list);
        }
        return cShopCarDao.selectShopCarByUserId(page);
    }

    /**
     * 删除购物车中的商品
     * 
     * @param 购物车表主键
     * @param 返回删除购物车中的商品是否成功标
     */
    @Override
    public int deleteShopCartById(String id) {
        String [] array = id.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : array) {
            list.add(Integer.valueOf(s));
        }
        return cShopCarDao.delBatchShopCartByUserId(list);
    }

    /**
     * 修改购物车中的商品
     * 
     * @param 购物车中表主键
     * @param 修改某个商品的数量
     * @return 返回修改购物车中的商品是否成功
     */
    @Override
    public ApiResultModel<Integer> modifyShopCart(String strQuantity) {
        ApiResultModel<Integer> result = new ApiResultModel<Integer>();
        JSONArray jsonArray = JSONArray.fromObject(strQuantity);
        @SuppressWarnings({ "unchecked", "deprecation" })
        List<CShopCarModel> cartList = JSONArray.toList(jsonArray,CShopCarModel.class);
        // 其实cartList只有一条数据，前端每修改一个数量的时候就会请求后台
        // 目前修改购物车库存只有一件商品
        for (CShopCarModel cShopCarModel : cartList) {
            Integer remain = cShopCarDao.queryQuantityByShopCartId(cShopCarModel.getId());
            if(remain!=null){
                 if(cShopCarModel.getGoodsQty()>remain){
                    // 如果购买商品大于商品库存，则将商品库存更新购物车中的数量
                     cShopCarModel.setGoodsQty(remain);
                     cShopCarDao.updateBatchShopCart(cartList);
                     result.setStatus(403);
                    result.setMsg("购买商品数量不能大于" + remain);
                     result.setData(remain);
                     return result;
                 }
            }
           
        }
        
        int size = cShopCarDao.updateBatchShopCart(cartList);
        if(size==0){
            result.setStatus(400);
            result.setMsg("修改购物车商品失败");
        } else {
            result.setStatus(200);
            result.setMsg("修改购物车商品成功");
        }
        return result;
    }
  
    /**
     * 校验购物车中的商品是否被编辑过
     * 
     * @param cartList:购物车中要结算的商品
     * @return 将返回购物车中的商品是否被编辑，如果被编辑将以字符串的形式返回
     */
    @Override
    public ApiResultModel<Map<String, Object>> checkShopCart(String shopCartStr,Integer userId,String addressId) {
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 主要存购物车商品版本被编辑过的list
        List<Integer> list = new ArrayList<Integer>();
        String[] array = shopCartStr.split(",");
        for (String s : array) {
            list.add(Integer.valueOf(s));
        }
        // 根据购物车中的商品去找BGoods表的目前商品的版本号
        List<CShopCarModel> cShopList = cShopCarDao.selectNewstGoodsByList(list);
        // 如果不为空代表购物车结算成功。
        // 计算整个商品的价格
        Integer allPrice = 0;
        Integer quantity = 0;
        
        // 如果购买数量大于库存将存在该字段用于提醒
        String remainStr = "";
        // 目前商品库存
        Integer tempRemain = 0;
        // 购物车中购买商品库存
        Integer tempPurchase  = 0;
        BGoodsModel bGoodsModel = null;
        for (CShopCarModel cShopCarModel : cShopList) {
                bGoodsModel = cShopCarModel.getGoods();
                if(bGoodsModel!=null){
                    tempRemain = bGoodsModel.getRemain();
                    tempPurchase = cShopCarModel.getGoodsQty();
                if(tempRemain<tempPurchase){
                    logger.info("商品名称:" + bGoodsModel.getName() + "库存:" + tempRemain + ",购买数量为:" + tempPurchase);
                    remainStr += bGoodsModel.getName() + "购买商品数量不能大于" + tempRemain + ",";
                } else {
                    allPrice += bGoodsModel.getPrice()*tempPurchase;
                    quantity += tempPurchase;
                }
            }
        }
        // 如果remainStr不为空，代表购买商品超出库存
        if(StringUtil.isNotEmpty(remainStr)){
            remainStr = remainStr.substring(0,remainStr.length()-1);
            result.setStatus(403);
            result.setMsg(remainStr);
            return result;
        }
        map = getOrderGoodsAndAddresss(userId,list,addressId,allPrice);
/*        map.put("allPrice", allPrice);*/
        map.put("tag", 1);
        map.put("allQuantity", quantity);
        map.put("balance", accountService.getAccountBalance(userId));
        result.setStatus(200);
        result.setMsg("结算购物车商品操作成功");
        result.setData(map);
        return result;
    }

    
    /**
     * 获取订单页面中的商品和收货地址
     * 
     * @param userId:用户id
     * @param list:购物车主键id列表
     * @param addressId:选择地址外键
     * @return 返回订单页面商品以及收货地址
     */
    public Map<String,Object> getOrderGoodsAndAddresss(Integer userId,List<Integer> list,String addressId,Integer allPrice){
        Map<String,Object> map = new HashMap<String,Object>();
        CDeliveryAddressModel deliveryAddress = null;
        if(StringUtil.isEmpty(addressId)){
            deliveryAddress = getCDeliveryAddressList(userId);
        } else {
            deliveryAddress = cDeliveryAddressDao.select(addressId);
        }
        
        Integer provinceId = 0;
        if(deliveryAddress!=null){
            provinceId = deliveryAddress.getProvinceId();
        }
        // 获取订单商品list
        List<CShopCarModel> cartList = getShopCarByUserId(userId,list);
        if(cartList.size()>0){
            map = parseShopCartToJson(cartList,provinceId,allPrice);
        }
        // 获取订单收货地址list
        map.put("deliveryAddress", deliveryAddress);
        return map;
    }
    
    /**
     * 将购物车商品转换成json
     * 
     * @param list
     * @return 获取购物车商品转换成json格式
     */
    public BShopCarBean parseShopCartToJson(List<CShopCarModel> list){
        BShopCarBean shopCarBean = new BShopCarBean();
        BGoodsInfoBean goodsBean = null;
        BShopInfoBean shopBean = null;
        List<BGoodsInfoBean> goodsBeanList = null;
        List<BShopInfoBean> shopBeanList = new ArrayList<BShopInfoBean>();
        List<BGoodsInfoBean> failureGoodsList = new ArrayList<BGoodsInfoBean>();
        // 获取购物车中对应的店铺
        List<BShopInfoModel> shopList = new ArrayList<BShopInfoModel>();        
        BShopInfoModel bShopInfoModel = new BShopInfoModel();
        for (CShopCarModel cShopCarModel : list) {
            int tag = 0;
            bShopInfoModel = cShopCarModel.getGoods().getShop();
            for (BShopInfoModel shop : shopList) {
                // 判断要添加的店铺是否在shopList中存在，如果粗在则标识1 表示不添加
                if(shop.getId().equals(bShopInfoModel.getId())){
                    tag = 1;
                    break;
                }
            }
            if(0==tag){
                shopList.add(bShopInfoModel);
            }
        }
        
        // 商品图片
        String logUrl = "";
        for (BShopInfoModel shop : shopList) {
            goodsBeanList = new ArrayList<BGoodsInfoBean>();
            shopBean = new BShopInfoBean();
            shopBean.setShopId(shop.getId());
            shopBean.setShopName(shop.getShopName());
            shopBean.setbUserId(shop.getbUserId());
            BShopInfoModel oldShop = null;
            for (CShopCarModel cShopCarModel : list) {
                if(cShopCarModel!=null&&cShopCarModel.getGoods()!=null&&cShopCarModel.getGoods().getShop()!=null){
                    oldShop = cShopCarModel.getGoods().getShop();
                    if(shop.getId().equals(oldShop.getId())) {
                        goodsBean = new BGoodsInfoBean();
                        goodsBean.setCartId(cShopCarModel.getId());
                        goodsBean.setGoodsId(cShopCarModel.getGoods().getId());
                        goodsBean.setGoodsName(cShopCarModel.getGoods().getName());
                        logUrl = cShopCarModel.getGoods().getLogoUrl();
                        if(StringUtil.isNotBlank(logUrl)){
                            goodsBean.setLogoUrl(LogoUrlUtil.splitOneImgUrl(logUrl));
                        } else {
                            goodsBean.setLogoUrl("");
                        }
                        goodsBean.setStatus(cShopCarModel.getGoods().getStatus());
                        goodsBean.setSales(cShopCarModel.getGoods().getSales());
                        goodsBean.setRemain(cShopCarModel.getGoods().getRemain());
                        goodsBean.setAllocate(cShopCarModel.getGoods().getAllocate());
                        goodsBean.setGoodsQuantity(cShopCarModel.getGoodsQty());
                        goodsBean.setGoodsVersion(cShopCarModel.getGoodsVersion());
                        goodsBean.setGoodsUnit(cShopCarModel.getGoods().getUnit());
                        goodsBean.setGoodsPrice(cShopCarModel.getGoods().getPrice());
                        if(cShopCarModel.getGoods().getStatus()!=null&&cShopCarModel.getGoods().getStatus()==1){
                            if(cShopCarModel.getGoods().getRemain()>0){
                                goodsBeanList.add(goodsBean);
                            } else {
                                failureGoodsList.add(goodsBean);
                            }
                        } else {
                            failureGoodsList.add(goodsBean);
                        }
                    }
                }
            }
            if(goodsBeanList.size()>0){
                shopBean.setGoodsList(goodsBeanList);
                shopBeanList.add(shopBean);
            }
        }
        shopCarBean.setShopList(shopBeanList);
        shopCarBean.setFailureGoodsList(failureGoodsList);
        return shopCarBean;
    }
    
    /**
     * 将购物车商品转换成json
     * 
     * @param list
     * @return 获取购物车商品转换成json格式
     */
    public Map<String,Object> parseShopCartToJson(List<CShopCarModel> list,Integer provinceId,Integer allPrice){
        Map<String,Object> map = new HashMap<String,Object>();
        BGoodsInfoBean goodsBean = null;
        BShopInfoBean shopBean = null;
        List<BGoodsInfoBean> goodsBeanList = null;
        List<BShopInfoBean> shopBeanList = new ArrayList<BShopInfoBean>();
        
        // 获取购物车中对应的店铺
        List<BShopInfoModel> shopList = new ArrayList<BShopInfoModel>();        
        BShopInfoModel bShopInfoModel = new BShopInfoModel();
        for (CShopCarModel cShopCarModel : list) {
            int tag = 0;
            bShopInfoModel = cShopCarModel.getGoods().getShop();
            for (BShopInfoModel shop : shopList) {
                // 判断要添加的店铺是否在shopList中存在，如果粗在则标识1 表示不添加
                if(shop.getId().equals(bShopInfoModel.getId())){
                    tag = 1;
                    break;
                }
            }
            if(0==tag){
                shopList.add(bShopInfoModel);
            }
        }
        
        // 商品图片
        String logUrl = "";
        // 算运费
        Integer freight = 0;
        // 临时运费
        Integer tempFreight = 0;
        // 标识，如果tag=1代表该店铺有至少一件商品是非水果的，是需要计算运费，如果是水果则不计算运费
        for (BShopInfoModel shop : shopList) {
            int type = 0;
            tempFreight = 0;
            goodsBeanList = new ArrayList<BGoodsInfoBean>();
            shopBean = new BShopInfoBean();
            shopBean.setShopId(shop.getId());
            shopBean.setShopName(shop.getShopName());
            shopBean.setbUserId(shop.getbUserId());
            BShopInfoModel oldShop = null;
            for (CShopCarModel cShopCarModel : list) {
                if(cShopCarModel!=null&&cShopCarModel.getGoods()!=null&&cShopCarModel.getGoods().getShop()!=null){
                    oldShop = cShopCarModel.getGoods().getShop();
                    if(shop.getId().equals(oldShop.getId())) {
                        if(CatalogEnum.nonfruit.toString().equals(cShopCarModel.getGoods().getCatalog())){
                            type = 1;
                        }
                        goodsBean = new BGoodsInfoBean();
                        goodsBean.setCartId(cShopCarModel.getId());
                        goodsBean.setGoodsId(cShopCarModel.getGoods().getId());
                        goodsBean.setGoodsName(cShopCarModel.getGoods().getName());
                        logUrl = cShopCarModel.getGoods().getLogoUrl();
                        if(StringUtil.isNotBlank(logUrl)){
                            goodsBean.setLogoUrl(LogoUrlUtil.splitOneImgUrl(logUrl));
                        } else {
                            goodsBean.setLogoUrl("");
                        }
                        goodsBean.setStatus(cShopCarModel.getGoods().getStatus());
                        goodsBean.setSales(cShopCarModel.getGoods().getSales());
                        goodsBean.setRemain(cShopCarModel.getGoods().getRemain());
                        goodsBean.setAllocate(cShopCarModel.getGoods().getAllocate());
                        goodsBean.setGoodsQuantity(cShopCarModel.getGoodsQty());
                        goodsBean.setGoodsVersion(cShopCarModel.getGoodsVersion());
                        goodsBean.setGoodsUnit(cShopCarModel.getGoods().getUnit());
                        goodsBean.setGoodsPrice(cShopCarModel.getGoods().getPrice());
                        goodsBean.setCatalog(cShopCarModel.getGoods().getCatalog());
                        goodsBeanList.add(goodsBean);
                    }
                }
            }
            // 计算运费(provinceId如果为空，则代表该买家还未有自己的收货地址，tag==1则代表每个店铺只要有一项是非水果，则参与运费)
            if(!provinceId.equals(0)&&type==1){
                Page page = new Page();
                page.getParams().put("shopId", shop.getId());
                page.getParams().put("provinceId", provinceId);
                tempFreight = bShopFreightDao.getFreightByShopIdAndPId(page);
                shopBean.setFreight(tempFreight);
                if(tempFreight!=null){
                    freight += tempFreight;
                }
            } else {
                shopBean.setFreight(0);
            }
            if(goodsBeanList.size()>0){
                shopBean.setGoodsList(goodsBeanList);
                shopBeanList.add(shopBean);
            }
        }
        map.put("shopCartGoods", shopBeanList);
        // 运费
        map.put("freight", freight);
        map.put("allPrice", freight+allPrice);
        return map;
    }
    
    /**
     * 根据用户外键获取收货地址
     * 
     * @param 用户外键
     * @return 返回收货地址list
     */
    @Override
    public CDeliveryAddressModel getCDeliveryAddressList(Integer userId) {
        Page page = new Page();
        page.getParams().put("userId", userId);
        page.getParams().put("isDefault", 1);
        List<CDeliveryAddressModel> list = cDeliveryAddressDao.selectDeliveryAddressByUserId(page);
        if(list.size()>0){
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 立即购买
     * 
     * @param id:商品主键id
     * @param userId:用户外键
     * @return 返回立即购买跳转的确认订单页面中的商品和收货地址
     */
    @Override
    public ApiResultModel<Map<String, Object>> purchase(Integer id, Integer userId,Integer quantity,String addressId) {
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 根据购物车中的商品去找BGoods表的目前商品的版本号
        BGoodsToPurchaseOrderModel goods = bGoodsDao.getGoodsInfoById(id);
/*        Integer shopQuantity = cShopCarDao.selectGoodsQtyByUserId(goods.getGoodsNo());
        
        if(shopQuantity==null){
            shopQuantity = 0;
        }*/
        int remain = goods.getRemain();
        int purchaseQty = quantity;
        if(remain<purchaseQty){
            logger.warn(goods.getGoodsName() + "购买商品数量不能大于" + remain);
            logger.warn(goods.getGoodsName() + "商品库存:" + remain + ",购买数量:" + purchaseQty);
            result.setStatus(403);
            result.setMsg("购买商品数量不能大于" + remain);
            return result;
        }
        String logUrl = goods.getLogoUrl();
        if(StringUtil.isNotBlank(logUrl)){
            goods.setLogoUrl(LogoUrlUtil.splitOneImgUrl(logUrl));
        } else {
            goods.setLogoUrl("");
        }
        goods.setQuantity(quantity);
        map.put("tag", 0);
        map.put("goods", goods);
        map.put("allQuantity", quantity);
        CDeliveryAddressModel deliveryAddress = null;
        if(StringUtil.isEmpty(addressId)){
            deliveryAddress = getCDeliveryAddressList(userId);
        } else {
            deliveryAddress = cDeliveryAddressDao.select(addressId);
        }
        map.put("deliveryAddress", deliveryAddress);
        if(CatalogEnum.nonfruit.toString().equals(goods.getCatalog())){
            int freight = getFreight(goods.getShopId(), deliveryAddress, goods.getCatalog());
            if(freight==0){
                map.put("freight", 0);
            } else {
                map.put("freight", freight);
            }
            map.put("allPrice", goods.getPrice()*quantity+freight);
        } else {
            map.put("allPrice", goods.getPrice()*quantity);
        }
        map.put("balance", accountService.getAccountBalance(userId));
        result.setData(map);
        result.setStatus(200);
        result.setMsg("立即购买操作成功");
        return result;
    }
    
    /**
     * 获取收货地址
     * 
     * @param userId:用户外键
     * @return 返回收货地址列表
     */
    @Override
    public ApiResultModel<String> getDeliveryAddress(Integer userId) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        Page page = new Page();
        page.getParams().put("userId", userId);
        page.getParams().put("isDefault", null);
        List<CDeliveryAddressModel> list = cDeliveryAddressDao.selectDeliveryAddressByUserId(page);
        result.setStatus(200);
        result.setMsg("获取收货地址列表成功");
        if(list.size()>0){
            result.setData(JsonUtil.parseToStr(list));
        }
        return result;
    }
    
    /**
     * 插入收货地址表对象
     * 
     * @param 收货地址表
     * @return 返回插入是否成功
     */
    @Override
    public ApiResultModel<String> insertDeliveryAddress(InsertCDeliveryAddressModel address,Integer userId) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        // 如果该用户将新增或者更新的对象设为默认地址，该情况下将取消用户对应默认收货地址
        if("1".equals(address.getIsDefault())){
            InsertCDeliveryAddressModel olDeliveryAddress = new InsertCDeliveryAddressModel();
            olDeliveryAddress.setUserId(userId);
            olDeliveryAddress.setIsDefault("0");
            cDeliveryAddressDao.updateDeliveryAddressByUserId(olDeliveryAddress);
            
        }
        int size = 0;
        address.setUpdatedTime(new Date());
        address.setCreatedUser(userId);
        if(address.getId()!=null){
            size = cDeliveryAddressDao.updateDeliveryAddressById(address);
        } else {
            address.setCreatedTime(new Date());
            address.setCreatedUser(userId);
            size = cDeliveryAddressDao.insertDeliveryAddress(address);
        }
        if(size==0){
            result.setStatus(400);
            result.setMsg("保存收货地址操作失败");
            return result;
        }
        result.setStatus(200);
        result.setMsg("保存收货地址操作成功");
        return result;
    }

    /**
     * 加入购物车
     */
    @Override
    public DataToCResultModel<Object> insertBGoodsShopCar(String goodsNo, Integer number, Integer userId) {
        int cShopCarSiz = cShopCarDao.selectcShopCarSizByUserId(userId, goodsNo);
        // 判断购物车中是否已经存在加入的商品
        if (cShopCarSiz == 0) {
            // 获得商品版本
            BGoodsModel bGoodsModel = bGoodsDao.selectByGoodsNo(goodsNo);
            Integer goods_version = bGoodsModel.getVersion();
            if(bGoodsModel.getRemain() < number){
                return new DataToCResultModel<Object>(403, "购买商品数量不能大于" + bGoodsModel.getRemain(), null);
            } else{
                // 购物车模型
                CShopCarModel cShopCarModel = new CShopCarModel();
                cShopCarModel.setCreatedTime(new Date());
                cShopCarModel.setGoodsQty(number);
                cShopCarModel.setGoods(bGoodsModel);
                cShopCarModel.setGoodsVersion(goods_version);
                // 购买者模型
                CUserModel cUserModel = new CUserModel();
                cUserModel.setId(userId);
                cShopCarModel.setUser(cUserModel);
                // 插入购物车
                int size = cShopCarDao.insertInto(cShopCarModel);
                if (size > 0) {
                    return new DataToCResultModel<Object>(200, "加入购物车成功！", null);
                } else {
                    return new DataToCResultModel<Object>(401, "加入购物车失败！", null);
                }
            }
        } else {
            // 购物车中已经存在商品,那么根据商品Id修改数量
            int goodsQty = cShopCarDao.selectGoodsQty(userId, goodsNo);
            // 购买商品数量:
            number = goodsQty + number;
            BGoodsModel bGoodsModel2 = bGoodsDao.selectByGoodsNo(goodsNo);
            if(bGoodsModel2.getRemain() < number){
                logger.info("加入购物车时，卖家商品库存:" + bGoodsModel2.getRemain() + ",购买商品数量:" + number);
                return new DataToCResultModel<Object>(403, "购买商品数量不能大于" + bGoodsModel2.getRemain(), null);
            }
            // 更新购物车数量
            CShopCarModel cShopCarModel = new CShopCarModel();
            cShopCarModel.setGoodsQty(number);
            BGoodsModel bGoodsModel = new BGoodsModel();
            bGoodsModel.setGoodsNo(goodsNo);
            CUserModel cUserModel = new CUserModel();
            cUserModel.setId(userId);
            cShopCarModel.setGoods(bGoodsModel);
            cShopCarModel.setUser(cUserModel);
            int size = cShopCarDao.updateGoodsQty(cShopCarModel);
            if (size > 0) {
                return new DataToCResultModel<Object>(200, "加入购物车成功！", null);
            } else {
                return new DataToCResultModel<Object>(401, "加入购物车失败！", null);
            }
        }
    }
    
    /**
     * 提交订单(按店铺拆分订单，并且按照每家店铺量水果和非水果拆分两个订单)
     * 
     * @param btoCBSalesOrder:订单信息
     * @param list:订单详情list
     * @param:userId 用户外键
     * @return 返回提交订单信息是否成功
     */
    @Override
    public ApiResultModel<String> submitOrder(String payType,HttpServletRequest request,HttpServletResponse response,BtoCBSalesOrderModel btoCBSalesOrder, List<BShopToOrderModel> list,
            Integer userId) {
        // 存放购物车id
        List<Integer> delList = new ArrayList<Integer>();
        // 水果商品列表
        List<Integer> goodsList = new ArrayList<Integer>();
        // 非水果商品列表
        List<Integer> notGoodsList = new ArrayList<Integer>();
        ApiResultModel<String> result = new ApiResultModel<String>();
        BSalesOrderModel bSalesOrder = null;
        List<BSalesOrderModel> orderList = new ArrayList<BSalesOrderModel>();
        BSalesOrderDetailsModel orderDetail = null;
        // 水果分类
        List<BSalesOrderDetailsModel> orderDetailList = new ArrayList<BSalesOrderDetailsModel>();
        
        String mergeOrderNo = SequenceUtil.nextId(SequenceType.M.toString());
        // 水果总价
        Integer orderPrice = 0;
        // 非水果总价
        Integer nonFruitOrderPrice = 0;
        // 总价格
        Integer allPrice = 0;
        // 总运费
        Integer allFreight = 0;
        // 水果数量
        int goodsCount = 0;
        // 非水果数量
        int nonFruitsGoodsCount = 0;
        // 判断是否是水果(0.水果 1.非水果)
        String catalog = CatalogEnum.nonfruit.toString();
        for (BShopToOrderModel order : list) {
            goodsCount = 0;
            orderPrice = 0;
            nonFruitOrderPrice = 0;
            nonFruitsGoodsCount = 0;
            allFreight = 0;
            bSalesOrder = new BSalesOrderModel();
            bSalesOrder.setId(CommonUtil.generate32UUID());
            bSalesOrder.setOrderNo(SequenceUtil.nextId(SequenceType.B.toString()));
            bSalesOrder.setMergeOrderNo(mergeOrderNo);
            // 微信支付
            bSalesOrder.setPayType(Integer.valueOf(payType));
            bSalesOrder.setReceiverAddress(btoCBSalesOrder.getReceiverAddress());
            bSalesOrder.setReceiverName(btoCBSalesOrder.getReceiverName());
            bSalesOrder.setReceiverPhone(btoCBSalesOrder.getReceiverPhone());
            bSalesOrder.setReceiverPostalCode(btoCBSalesOrder.getReceiverPostalCode());
            bSalesOrder.setReceiverPhone(btoCBSalesOrder.getReceiverPhone());
            bSalesOrder.setRemark(btoCBSalesOrder.getRemark());
            bSalesOrder.setStatus(OrderStatus.WAITING_FOR_PAYMENT);
            bSalesOrder.setUserId(userId);
            bSalesOrder.setbUserId(order.getbUserId());
            bSalesOrder.setCreatedTime(new Date());
            bSalesOrder.setUpdatedTime(new Date());
            BSalesOrderModel nonFruitBSalesOrder = new BSalesOrderModel();
            try {
                PropertyUtils.copyProperties(nonFruitBSalesOrder, bSalesOrder);
            } catch (Exception e) {
                result.setStatus(400);
                result.setMsg("提交订单操作失败");
                return result;
            } 
            nonFruitBSalesOrder.setId(CommonUtil.generate32UUID());
            nonFruitBSalesOrder.setOrderNo(SequenceUtil.nextId(SequenceType.B.toString()));
            // 将json转换成订单对象
            JSONArray jsonArray = JSONArray.fromObject(order.getGoodsList());
            @SuppressWarnings({ "unchecked", "deprecation" })
            List<BGoodsToOrderModel> goodslist = JSONArray.toList(jsonArray,BGoodsToOrderModel.class);
            for (BGoodsToOrderModel goods : goodslist) {
                // 获取商品信息
                BGoodsModel bGoodsModel  = bGoodsDao.select(goods.getGoodsId());
                orderDetail = new BSalesOrderDetailsModel();
                orderDetail.setId(CommonUtil.generate32UUID());
                orderDetail.setGoodsId(goods.getGoodsId());
                orderDetail.setGoodsName(bGoodsModel.getName());
                orderDetail.setSharePercent(bGoodsModel.getSharePercent());
                orderDetail.setTotalPrice(goods.getGoodsPrice()*goods.getGoodsQuantity());
                orderDetail.setGoodsQty(goods.getGoodsQuantity());
                orderDetail.setPrice(goods.getGoodsPrice());
                orderDetail.setGoodsVersion(goods.getGoodsVersion());
                orderDetail.setCreatedTime(new Date());
                // 增加订单详情信息表增加是否参与佣金计算 add by jane.hui 2016/12/06
                orderDetail.setIsCommission(bGoodsModel.getIsCommission());
                if(CatalogEnum.fruit.toString().equals(goods.getCatalog())){
                    catalog = CatalogEnum.fruit.toString();
                    orderDetail.setOrderId(bSalesOrder.getId());
                    goodsCount += 1;
                    orderDetailList.add(orderDetail);
                    // 算每个订单的价格
                    orderPrice += goods.getGoodsPrice()*goods.getGoodsQuantity();
                    goodsList.add(goods.getGoodsId());
                } else {
                    orderDetail.setOrderId(nonFruitBSalesOrder.getId());
                    nonFruitsGoodsCount += 1;
                    orderDetailList.add(orderDetail);
                    // 算每个订单的价格
                    nonFruitOrderPrice += goods.getGoodsPrice()*goods.getGoodsQuantity();
                    notGoodsList.add(goods.getGoodsId());
                }
                delList.add(goods.getCartId());
            }
            logger.info("卖家bUserId" + order.getShopName() + ",提交订单时合并订单号:" + mergeOrderNo + ",水果分类商品数量:" + goodsCount
                    + ",非水果商品数量:" + nonFruitsGoodsCount);
            
            // 计算水果分类商品总价和数量
            if(goodsCount>0){
                bSalesOrder.setTotal(orderPrice);
                logger.info(
                        "卖家bUserId" + order.getShopName() + ",提交订单时合并订单号:" + mergeOrderNo + ",水果订单总价格为:" + orderPrice);
                bSalesOrder.setGoodsCount(goodsCount);
                bSalesOrder.setCatalog(CatalogEnum.fruit.toString());
                bSalesOrder.setFreight(0);
                bSalesOrder.setRealPay(orderPrice);
                orderList.add(bSalesOrder);
            }
            // 计算非水果分类商品和数量
            if(nonFruitsGoodsCount>0){
                nonFruitBSalesOrder.setTotal(nonFruitOrderPrice);
                logger.info("卖家bUserId" + order.getShopName() + ",提交订单时合并订单号:" + mergeOrderNo + ",水果订单总价格为:"
                        + nonFruitsGoodsCount);
                nonFruitBSalesOrder.setGoodsCount(nonFruitsGoodsCount);
                nonFruitBSalesOrder.setCatalog(CatalogEnum.nonfruit.toString());
                nonFruitBSalesOrder.setFreight(order.getFreight());
                // 判断订单表的运费是否为空，如果为空则表示0运费
                Integer nonFreight = 0;
                if(order.getFreight()!=null){
                    nonFreight = order.getFreight();
                }
                nonFruitBSalesOrder.setRealPay(nonFruitOrderPrice+nonFreight);
                orderList.add(nonFruitBSalesOrder);
            }
            if(order.getFreight()!=null){
                allFreight += order.getFreight();
            }
            
            allPrice += orderPrice + nonFruitOrderPrice + allFreight;
        }
        if(CatalogEnum.fruit.toString().equals(catalog)){
            // 收货地址经度纬度不为空
        if(StringUtil.isNotEmpty(btoCBSalesOrder.getLongitude())&StringUtil.isNotEmpty(btoCBSalesOrder.getLatitude())){
                // 判断收货地址与店家的距离是否超出范围
            result = isOutOfRange(goodsList, btoCBSalesOrder.getLongitude(),btoCBSalesOrder.getLatitude());
            if(!result.getStatus().equals(200)){
                return result;
            }
        } else {
                result.setStatus(400);
                result.setMsg("提交订单操作失败");
                return result;
        }
        }
        // 批量插入订单
        if(orderList.size()>0){
            bSalesOrderDao.insertBatchOrder(orderList);
        }
        // 批量插入水果以及非水果订单详情表
        if(orderDetailList.size()>0){
            // 批量插入水果订单详情表
            bSalesOrderDetailsDao.insertBatchOrderDetail(orderDetailList);
            // 批量更新商品表数量
            bGoodsDao.updateBatchGoodsQuantity(orderDetailList);    
            // 删除购物车中的商品
            cShopCarDao.delBatchShopCartByUserId(delList);
        }
        
        /**
         * 返回完整的订单信息
         */
         String goodsName = "";
         if(goodsList.size()>0){
             goodsName =  bGoodsDao.getGoodsNameByIdList(goodsList);
         } 
         if(notGoodsList.size()>0){
             goodsName += bGoodsDao.getGoodsNameByIdList(notGoodsList);
         }
         String orderInfo = "";
         if(PayType.WEIXIN.toString().equals(payType)){
            orderInfo = WeixinUtil.pay(request, response, allPrice, "普通用户订单支付", mergeOrderNo, WeixinConfig.notify_url);
            result.setData(orderInfo);
         } else if(PayType.ALIPAY.toString().equals(payType)){
            orderInfo = PayUtil.getInfo("普通用户订单支付", goodsName, MoneyUtil.fromFenToYuan(String.valueOf(allPrice)), mergeOrderNo, AlipayConfig.notify_url);
            result.setData(orderInfo);
         } else if(PayType.WALLET_PAY.toString().equals(payType)){
/*         return accountService.walletPay(userId, allPrice, mergeOrderNo);*/
             result.setData(mergeOrderNo+","+allPrice);
         }
         result.setMsg("提交订单操作成功");
         result.setStatus(200);
         return result;
    }
    
    /**
     * 提交订单(按店铺拆分订单，并且按照每家店铺量水果和非水果拆分两个订单) 下单个订单
     */
    @SuppressWarnings("unused")
    @Override
    public ApiResultModel<String> submitOneOrder(String payType,HttpServletRequest request,HttpServletResponse response, BtoCBSalesOrderModel btoCBSalesOrder,
        BGoodsToPurchaseOrderModel bGoodsInfoToCModel, Integer userId) {
        // 获取运费字段
        String strFreight = request.getParameter("freight");
        ApiResultModel<String> result = new ApiResultModel<String>();
        // 水果订单
        List<BSalesOrderModel> orderList = new ArrayList<BSalesOrderModel>();
        List<BSalesOrderDetailsModel> orderDetailList = new ArrayList<BSalesOrderDetailsModel>();
        String mergeOrderNo = SequenceUtil.nextId(SequenceType.M.toString());
        // 创建订单表
        BSalesOrderModel bSalesOrder = new BSalesOrderModel();
        bSalesOrder.setPayType(Integer.valueOf(payType));
        bSalesOrder.setId(CommonUtil.generate32UUID());
        bSalesOrder.setOrderNo(SequenceUtil.nextId(SequenceType.B.toString()));
        bSalesOrder.setMergeOrderNo(mergeOrderNo);
        bSalesOrder.setReceiverAddress(btoCBSalesOrder.getReceiverAddress());
        bSalesOrder.setReceiverName(btoCBSalesOrder.getReceiverName());
        bSalesOrder.setReceiverPhone(btoCBSalesOrder.getReceiverPhone());
        bSalesOrder.setReceiverPostalCode(btoCBSalesOrder.getReceiverPostalCode());
        bSalesOrder.setReceiverTelephone(btoCBSalesOrder.getReveiverTelephone());
        bSalesOrder.setRemark(btoCBSalesOrder.getRemark());
        bSalesOrder.setStatus(OrderStatus.WAITING_FOR_PAYMENT);
        bSalesOrder.setUserId(userId);
        bSalesOrder.setbUserId(bGoodsInfoToCModel.getbUserId());
        Integer freight = 0;
        if(StringUtil.isNotEmpty(strFreight)){
            freight = Integer.valueOf(strFreight);
        }
        bSalesOrder.setFreight(freight);
        bSalesOrder.setCreatedTime(new Date());
        bSalesOrder.setUpdatedTime(new Date());
        bSalesOrder.setCatalog(bGoodsInfoToCModel.getCatalog());
        BSalesOrderDetailsModel orderDetail = new BSalesOrderDetailsModel();
        orderDetail.setId(CommonUtil.generate32UUID());
        orderDetail.setOrderId(bSalesOrder.getId());
        orderDetail.setGoodsId(bGoodsInfoToCModel.getGoodsId());
        // 获取商品信息
        BGoodsModel bGoodsModel  = bGoodsDao.select(bGoodsInfoToCModel.getGoodsId());
        orderDetail.setGoodsName(bGoodsModel.getName());
        // 小计
        Integer totalPrice = bGoodsInfoToCModel.getPrice()*bGoodsInfoToCModel.getQuantity();
        orderDetail.setTotalPrice(totalPrice);
        // 百分比
        orderDetail.setSharePercent(bGoodsModel.getSharePercent());
        orderDetail.setGoodsQty(bGoodsInfoToCModel.getQuantity());
        orderDetail.setPrice(bGoodsInfoToCModel.getPrice());
        orderDetail.setGoodsVersion(bGoodsInfoToCModel.getVersion());
        orderDetail.setCreatedTime(new Date());
        // 增加订单详情信息表增加是否参与佣金计算 add by jane.hui 2016/12/06
        orderDetail.setIsCommission(bGoodsModel.getIsCommission());
        orderDetailList.add(orderDetail);
        // 订单金额
        bSalesOrder.setTotal(totalPrice);
        // 实际金额
        bSalesOrder.setRealPay(totalPrice+freight);
        orderList.add(bSalesOrder);
        // 商品id列表
        List<Integer> list = new ArrayList<Integer>();
        list.add(orderDetail.getGoodsId());
        String goodsName =  bGoodsDao.getGoodsNameByIdList(list);
        if(CatalogEnum.fruit.toString().equals(bGoodsInfoToCModel.getCatalog())){
            // 经度维度不为空判断
            if(StringUtil.isNotEmpty(btoCBSalesOrder.getLongitude())&StringUtil.isNotEmpty(btoCBSalesOrder.getLatitude())){
                // 判断买家收货地址与卖家店铺地址超出范围判断
              result = isOutOfRange(list, btoCBSalesOrder.getLongitude(), btoCBSalesOrder.getLatitude());
              if(!result.getStatus().equals(200)){
                  return result;
              }
            } else {
                logger.warn("店铺地址未设置！合并订单号:" + mergeOrderNo);
                result.setStatus(400);
                result.setMsg("店铺地址未设置");
                return result;
            }
        }
        // 批量插入订单
        if(orderList.size()>0){
            bSalesOrderDao.insertBatchOrder(orderList);
        }
        // 批量插入订单
        if(orderDetailList.size()>0){
            // 批量插入订单详情表
            bSalesOrderDetailsDao.insertBatchOrderDetail(orderDetailList);
            // 批量更新商品表数量
            bGoodsDao.updateBatchGoodsQuantity(orderDetailList);
        }
        if(totalPrice!=null){
             Integer allPrice = totalPrice + freight;
             String orderInfo = "";
             if(PayType.WEIXIN.toString().equals(payType)){
                 orderInfo = WeixinUtil.pay(request, response, allPrice, "普通用户订单支付", mergeOrderNo,
                        WeixinConfig.notify_url);
                 result.setData(orderInfo);
             } else if(PayType.ALIPAY.toString().equals(payType)){
                 orderInfo = PayUtil.getInfo(goodsName, goodsName, MoneyUtil.fromFenToYuan(String.valueOf(allPrice)), mergeOrderNo,AlipayConfig.notify_url);
                 result.setData(orderInfo);
             } else if(PayType.WALLET_PAY.toString().equals(payType)){
/*                 return accountService.walletPay(userId, allPrice, mergeOrderNo);*/
                 result.setData(mergeOrderNo+","+allPrice);
             }
             result.setMsg("提交订单操作成功");
             result.setStatus(200);
             return result;
        } else {
            result.setMsg("提交订单操作失败");
            result.setStatus(400);
            return result;
        }
    }

    /**
     * 修改订单状态
     * 
     * @param app_id:应用id
     * @param out_trade_no:商品订单号
     * @param body:商品描述
     * @param buyer_id:买家id
     * @param seller_id:卖家id
     * @param subject:商品名称
     * @param total_amount:支付金额
     * @param trade_no:交易流水号
     * @param tradeContent:交易内容
     */
    @Override
    public List<BSalesOrderModel> notifyUrl(String app_id, String out_trade_no, String body, String buyer_id, String seller_id,
            String subject, String total_amount, String trade_no, String tradeContent) {
        List<BSalesOrderModel> returnList = new ArrayList<BSalesOrderModel>();
        if(StringUtil.isNotEmpty(out_trade_no)){
            String prefix = out_trade_no.substring(0,1);
            Page page = new Page();
            if(SequenceType.M.toString().equals(prefix)){
                page.getParams().put("mergeOrderNo", out_trade_no);
            } else {
                page.getParams().put("orderNo", out_trade_no);
            }
            List<BSalesOrderModel> list = bSalesOrderDao.selectSalesOrderByMergeNo(page);
            int tag = 1;
            for (BSalesOrderModel bSalesOrderModel : list) {
                if(bSalesOrderModel.getStatus().equals(OrderStatus.WAITING_FOR_PAYMENT)){
                    tag = 0;
                }
            }
            if(tag==0){
                List<BSalesOrderDetailsModel> orderDetailList = bSalesOrderDetailsDao.selectOrderDetailListByMergeNo(page);
                for (BSalesOrderDetailsModel bSalesOrderDetailsModel : orderDetailList) {
                    // 更新销量
                    getSalesCount(bSalesOrderDetailsModel.getGoodsId(),bSalesOrderDetailsModel.getGoodsQty());
                }
                if(SequenceType.M.toString().equals(prefix)){
                    bSalesOrderDao.updateOrderStatusByMergeNo(out_trade_no);
                } else {
                    bSalesOrderDao.updateOrderStatusByOrderNo(out_trade_no);
                }
                returnList = list;
                PayRecordModel payRecord = new PayRecordModel();
                payRecord.setOutTradeNo(out_trade_no);
                payRecord.setBody(body);
                payRecord.setBuyerId(buyer_id);
                payRecord.setSellerId(seller_id);
                payRecord.setSubject(subject);
                payRecord.setTotalAmount(total_amount);
                payRecord.setTradeNo(trade_no);
                payRecord.setTradeContent(tradeContent);
                payRecord.setCreateDate(new Date());
                payRecordDao.insertSelective(payRecord);
            }
        }
        return returnList;
    }
    
    /**
     * 判断收货地址距离与卖家距离是否超过500米
     * 
     * @param list:商品id列表
     * @return 返回是否超出范围(0.不超出范围 1.超出范围)
     */
    public  ApiResultModel<String> isOutOfRange(List<Integer> list,String oldLocationX,String oldLocationY){
        ApiResultModel<String> result = new ApiResultModel<String>();
         List<BShopInfoModel> shopList = bGoodsDao.getlocationByIdList(list);
        if(shopList.size()==0){
            result.setStatus(400);
            logger.info("店铺不存在");
            result.setMsg("店铺不存在");
            return result;
        } else {
            String locationX = "";
            String locationY = "";
            for (BShopInfoModel shop : shopList) {
                locationX = shop.getLocationX();
                locationY = shop.getLocationY();
                if(StringUtil.isNotEmpty(locationX)&&StringUtil.isNotEmpty(locationY)){
                    double length = DistanceUtil.distance(Double.valueOf(locationX), Double.valueOf(locationY),
                            Double.valueOf(oldLocationX), Double.valueOf(oldLocationY));
                    if (length - Double.valueOf(1000) > 0) {
                        result.setStatus(400);
                        logger.info(shop.getShopName() + "店铺不在配送范围内，请修改收货地址买家地址经纬度:" + oldLocationX + "," + oldLocationY
                                + ",卖家地址经纬度:" + locationX + "," + locationY);
                        result.setMsg(shop.getShopName() + "店铺水果商品不在配送范围内，请修改收货地址");
                        return result;
                    }
                }
            }
        }
        result.setStatus(200);
        result.setMsg("提交订单操作成功");
        return result;
    }
    
    /**
     * 更新商品销量
     * 
     * @param goodsId:商品主键
     * @return:返回销量
     */
    public Integer getSalesCount(Integer goodsId,Integer quantity){
        BGoodsToPurchaseOrderModel goods = bGoodsDao.getGoodsInfoById(goodsId);
        int size = 0;
        if(goods!=null){
            BGoodsModel goodsModel  = bGoodsDao.selectByGoodsNo(goods.getGoodsNo());
            logger.info("更新商品销量->商品编号:" + goodsModel.getGoodsNo() + ",数量:" + quantity);
            if(goodsModel==null||goodsModel.getSales()==null||"".equals(goodsModel.getSales())){} else {
                goodsModel.setSales(goodsModel.getSales() + quantity);
                size = bGoodsDao.updateSalesById(goodsModel);
            }
        }
        return size;
    }

    /**
     * 微信回调URL
     */
    @Override
    public List<BSalesOrderModel> wxNotifyUrl(Map<String, String> map) {
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
        List<BSalesOrderModel> returnList = new ArrayList<BSalesOrderModel>();
        if(StringUtil.isNotEmpty(out_trade_no)){
            String prefix = out_trade_no.substring(0,1);
            Page page = new Page(); 
            if(SequenceType.M.toString().equals(prefix)){
                page.getParams().put("mergeOrderNo", out_trade_no);
            } else {
                page.getParams().put("orderNo", out_trade_no);
            }            
            List<BSalesOrderModel> list = bSalesOrderDao.selectSalesOrderByMergeNo(page);
            int tag = 1;
            for (BSalesOrderModel bSalesOrderModel : list) {
                if(bSalesOrderModel.getStatus().equals(OrderStatus.WAITING_FOR_PAYMENT)){
                    tag = 0;
                }
            }
            if(tag==0){
               List<BSalesOrderDetailsModel> orderDetailList = bSalesOrderDetailsDao.selectOrderDetailListByMergeNo(page);
              for (BSalesOrderDetailsModel bSalesOrderDetailsModel : orderDetailList) {
                    // 更新销量
                  getSalesCount(bSalesOrderDetailsModel.getGoodsId(),bSalesOrderDetailsModel.getGoodsQty());
              }
                logger.info("商户订单号:" + out_trade_no + "状态更新为已发货");
              if(SequenceType.M.toString().equals(prefix)){
                bSalesOrderDao.updateOrderStatusByMergeNo(out_trade_no);
              } else {
                bSalesOrderDao.updateOrderStatusByOrderNo(out_trade_no);
              }
              returnList = list;
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
        return returnList;
    }

    /**
     * 根据店铺id和收货地址id获取收货地址
     * 
     * @param shopId:店铺id
     * @param provinceId:省id
     * @return 返回运费
     */
    @Override
    public int getFreight(Integer shopId, CDeliveryAddressModel deliveryAddress, String catalog) {
        if(CatalogEnum.nonfruit.toString().equals(catalog)){
            // 获取店铺的运s费表
            if(deliveryAddress!=null){
                Page page = new Page();
                page.getParams().put("shopId", shopId);
                page.getParams().put("provinceId", deliveryAddress.getProvinceId());
                Integer freight = bShopFreightDao.getFreightByShopIdAndPId(page);
                logger.info("店铺id：" + shopId + ",provinceId:" + deliveryAddress.getProvinceId() + ",运费为:" + freight);
                // 如果获取运费为空，则用包邮表示
                if(freight==null){
                    return 0;
                } else {
                    return freight;
                }
            } else {// 当买家没有收货地址时,默认显示0作为免运费
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 返回购物车总数量
     * 
     * @param userId:用户外键
     * @return 返回购物车数量
     */
    @Override
    public Map<String, Object> queryCarCountAndKindByUserId(Integer userId) {
        return cShopCarDao.queryCarCountAndKindByUserId(userId);
    }

    /**
     * 根据订单号获取物流信息
     * 
     * @param orderNo:订单id
     */
    @Override
    public CLogisticBean getLogisticsInfo(String orderNo) {
        CLogisticBean cLogisticBean = new CLogisticBean();
        BSalesOrderModel bSalesOrder = bSalesOrderDao.selectLogisticsByOrderNo(orderNo);
        if(bSalesOrder!=null){
            String logisticsCode = bSalesOrder.getLogisticsCode();
            String waybillNumber = bSalesOrder.getWaybillNumber();
            if(StringUtil.isNotEmpty(logisticsCode)&&StringUtil.isNotEmpty(waybillNumber)){
                try {
                    String info = KdniaoTrackQueryAPI.getOrderTracesByJson(orderNo,logisticsCode, waybillNumber);
                    cLogisticBean = com.alibaba.fastjson.JSONObject.parseObject(info, CLogisticBean.class);
                    if("false".equals(cLogisticBean.getSuccess())){
                        cLogisticBean.setTraces(new ArrayList<TracesBean>());
                    }
                    // 根据物流编码获取物流信息
                    CLogisticsCodeModel cLogisticsCode = cLogisticsCodeDao.queryLogisticsByCode(cLogisticBean.getShipperCode());
                    if(cLogisticsCode!=null){
                        cLogisticBean.setShipperCode(cLogisticsCode.getName());
                    }
                    return cLogisticBean;
                } catch (Exception e) {
                    logger.info("快递鸟获取物流信息异常，异常信息为:" + e.toString());
                }
            }
        }
        cLogisticBean.setTraces(new ArrayList<TracesBean>());
        return cLogisticBean;
    }

    @Override
    public List<BSalesOrderModel> selectSalesOrderByMergeNo(String orderNo) {
           String prefix = orderNo.substring(0,1);
           Page page = new Page(); 
           if(SequenceType.M.toString().equals(prefix)){
               page.getParams().put("mergeOrderNo", orderNo);
           } else {
               page.getParams().put("orderNo", orderNo);
           }            
         List<BSalesOrderModel> list = bSalesOrderDao.selectSalesOrderByMergeNo(page);
        return list;
    }

}