package com.f2b2c.eco.service.platform.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f2b2c.eco.dao.market.BShopCarDao;
import com.f2b2c.eco.dao.market.BShopInfoDao;
import com.f2b2c.eco.dao.platform.FFrontGoodsDao;
import com.f2b2c.eco.dao.platform.FGoodsDao;
import com.f2b2c.eco.dao.platform.FSalesOrderDao;
import com.f2b2c.eco.dao.platform.FSalesOrderDetailsDao;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.BShopCarModel;
import com.f2b2c.eco.model.market.CShopCarModel;
import com.f2b2c.eco.model.market.DataToCResultModel;
import com.f2b2c.eco.model.platform.FGoodsModel;
import com.f2b2c.eco.model.platform.FGoodsToBModel;
import com.f2b2c.eco.model.platform.FGoodsToShopCarModel;
import com.f2b2c.eco.model.platform.FSalesOrderDetailsModel;
import com.f2b2c.eco.model.platform.FSalesOrderModel;
import com.f2b2c.eco.model.platform.FShopToShopCartModel;
import com.f2b2c.eco.service.platform.FOrderService;
import com.f2b2c.eco.util.LogoUrlUtil;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * 购物-订单Service
 * @author jane.hui
 *
 */
@Service("fOrderService")
@Transactional
public class FOrderServiceImpl implements FOrderService{

    @Autowired
    private FFrontGoodsDao fFrontGoodsDao;
    
    @Autowired
    private BShopCarDao bShopCarDao;
    
    @Autowired
    private BShopInfoDao bShopInfoDao;
    
    @Autowired
    private FSalesOrderDetailsDao fSalesOrderDetailsDao;
    
    @Autowired
    private FGoodsDao fGoodsDao;
    
    @Autowired
    private FSalesOrderDao fSalesOrderDao;
    
    /**
     * 加入购物
     * @param id:商品主键
     * @param quantity:购买数量
     * @param userId:用户外键
     * @return 返回加入购物车是否成功
     */
    @Override
    public DataToCResultModel<Object> addShopCar(String goodsNo, Integer quantity, Integer userId) {
        DataToCResultModel<Object> result = new DataToCResultModel<Object>();
        FSalesOrderModel unPayOrder = fSalesOrderDao.queryUnPayOrder(userId);
        if(unPayOrder != null){
            result.setStatus(400);
            result.setMsg("您有未完成的订单，订单号为"+unPayOrder.getOrderId()+",请先完成订单再下单");
            return result;
        }
        // 根据商品编号获取商品详情
        List<FGoodsToBModel> goodsList = fFrontGoodsDao.queryFGoodsDetailByGoodsNo(goodsNo);
        if(goodsList.size()>0){
            FGoodsToBModel goods = goodsList.get(0);
            Integer shopQuantity = bShopCarDao.selectGoodsQtyByGoodsNo(goodsNo);
            if(shopQuantity==null){
                shopQuantity = 0;
            }
            int remain = goods.getRemain();
            int purchaseQty = shopQuantity + quantity;
            if(purchaseQty>remain){
                result.setMsg("购买商品数量不能大于"+remain);
                result.setStatus(403);
                return result;
            }
        }
        // 根据用户id和商品编号获取购物车是否存在，如果存在，则更新数量，如果不存在则插入数据
        BShopCarModel paramShopCar = new BShopCarModel();
        paramShopCar.setGoodsId(goodsNo);
        paramShopCar.setUserId(userId);
        // 根据参数获取购物车对
        BShopCarModel oldShopCar = bShopCarDao.selectShopCarByParams(paramShopCar);
        BShopCarModel shopCar = null;
        int size = 0;
        if(oldShopCar!=null){
            shopCar = oldShopCar;
            shopCar.setGoodsQty(oldShopCar.getGoodsQty() + quantity);
             size = bShopCarDao.updateShopCarById(shopCar);
        } else {
            shopCar = new BShopCarModel();
            shopCar.setGoodsId(goodsNo);
            shopCar.setUserId(userId);
            shopCar.setGoodsQty(quantity);
            shopCar.setCreatedTime(new Date());
            shopCar.setGoodsVersion(1);
            size = bShopCarDao.insertShopCar(shopCar);
        }
        if(size ==0){
            result.setStatus(400);
            result.setMsg("更新购物车失败");
        }
        result.setStatus(200);
        result.setMsg("加入购物车成功");
        return result;
    }

    /**
     * 显示购物车商牌?
     * @param userId:用户外键
     * @return 返回购物车商品?
     */
    @Override
    public DataToCResultModel<List<FGoodsToShopCarModel>> getShopCar(Integer userId) {
        DataToCResultModel<List<FGoodsToShopCarModel>> result = new DataToCResultModel<List<FGoodsToShopCarModel>>(); 
        try{
            List<FGoodsToShopCarModel> oldList = bShopCarDao.selectShopCartGoodsByUserId(userId);
            List<FGoodsToShopCarModel> newList = new ArrayList<FGoodsToShopCarModel>();
            for (FGoodsToShopCarModel fGoodsToBModel : oldList) {
                fGoodsToBModel.setLogoUrl(LogoUrlUtil.splitOneImgUrl(fGoodsToBModel.getLogoUrl()));
                newList.add(fGoodsToBModel);
            }
            result.setData(newList);
            result.setStatus(200);
            result.setMsg("加载购物车商品成功");
        } catch(Exception e){
            result.setStatus(400);
            result.setMsg("加载购物车商品失败");
        }
        return result;
    }
    
    /**
     * 删除购物车中的商品
     * @param 购物车表主键
     * @param 返回删除购物车中的商品是否成功?
     */
    @Override
    public int deleteShopCartById(String id) {
        String [] array = id.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : array) {
            list.add(Integer.valueOf(s));
        }
        return bShopCarDao.delBatchShopCartByUserId(list);
    }

    /**
     * 修改购物车中的商品
     * 
     * @param 购物车中表主键?
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
        for (CShopCarModel cShopCarModel : cartList) {
            Integer remain = bShopCarDao.queryQuantityByShopCartId(cShopCarModel.getId());
            if(remain!=null){
                 if(cShopCarModel.getGoodsQty()>remain){
                     result.setStatus(403);
                     result.setMsg("购买商品数量不能大于"+remain);
                     result.setData(remain);
                     return result;
                 }
            }
        }
        int size = bShopCarDao.updateBatchShopCart(cartList);
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
     * 校验购物车中的商品是否被编辑
     * 
     * @param cartList:购物车中要结算的商品
     * @return 将返回购物车中的商品是否被编辑，如果被编辑将以字符串的形式返回
     */
    @Override
    public ApiResultModel<Map<String, Object>> checkShopCart(String shopCartStr,Integer userId) {
        ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 主要存购物车商品版本被编辑过的list
        List<Integer> list = new ArrayList<Integer>();
        String[] array = shopCartStr.split(",");
        for (String s : array) {
            list.add(Integer.valueOf(s));
        }
        // 根据购物车中的商品去找BGoods表的目前商品的版本号
        List<FGoodsToShopCarModel> newShopList = new ArrayList<FGoodsToShopCarModel>();
        List<FGoodsToShopCarModel> cShopList = bShopCarDao.selectNewstGoodsByList(list);
        // 如果不为空代表购物车结算成功
        // 计算整个商品的价格
        Integer allPrice = 0;
        Integer quantity = 0;
        
        // 如果购买数量大于库存将存在该字段用于提醒
        String remainStr = "";
        for (FGoodsToShopCarModel goods : cShopList) {
        	if(goods.getRemain()<goods.getGoodsQty()){
        		remainStr += goods.getName()+"购买商品数量不能大于"+goods.getRemain()+",";
        	} else {
	            allPrice += goods.getPrice()*goods.getGoodsQty();
	            quantity += goods.getGoodsQty();
	            goods.setLogoUrl(LogoUrlUtil.splitOneImgUrl(goods.getLogoUrl()));
	            newShopList.add(goods);
        	}
        }
        
        // 如果remainStr不为空，代表购买商品超出库存
        if(StringUtil.isNotEmpty(remainStr)){
            remainStr = remainStr.substring(0,remainStr.length()-1);
            result.setStatus(403);
            result.setMsg(remainStr);
            return result;
        }
        
        // 根据用户id获取商户信息
        FShopToShopCartModel fShopToShopCartModel = bShopInfoDao.selectBShopInfoByBUserId(userId);
        map.put("shopCartGoods", newShopList);
        map.put("allPrice", allPrice);
        map.put("allQuantity", quantity);
        // 如果收货地址不为空，则返回，如果为空，则new个新的空对象回去
        if(fShopToShopCartModel!=null){
            map.put("deliveryAddress", fShopToShopCartModel);
        } else {
            map.put("deliveryAddress", new FShopToShopCartModel());
        }
        result.setStatus(200);
        result.setMsg("结算购物车商品操作成功");
        result.setData(map);
        // 删除购物车中的商品
/*        bShopCarDao.delBatchShopCartByUserId(list);*/
        return result;
    }

    /**
     * 立即购买
     * 
     * @param id:商品主键id
     * @param userId:用户外键
     * @return 返回立即购买跳转的确认订单页面中的商品和收货地址
     */
    @Override
    public ApiResultModel<Map<String, Object>> purchase(Integer id, Integer userId,Integer quantity) {
         ApiResultModel<Map<String, Object>> result = new ApiResultModel<Map<String, Object>>();
         FSalesOrderModel fSalesOrder = fSalesOrderDao.queryUnPayOrder(userId);
         if(fSalesOrder != null){
             result.setStatus(400);
             result.setMsg("您有未完成的订单，订单号为"+fSalesOrder.getOrderId()+",请先完成订单再下单");
             return result;
         }
         Map<String, Object> map = new HashMap<String, Object>();
        FGoodsToShopCarModel newGoods = new FGoodsToShopCarModel();
        FGoodsModel oldGoods = fGoodsDao.findFgoodsById(id);
        if(oldGoods!=null){
            newGoods.setGoodsId(oldGoods.getId());
            newGoods.setGoodsNo(oldGoods.getGoodsNo());
            newGoods.setDetail(oldGoods.getDetail());
            newGoods.setGoodsQty(quantity);
            newGoods.setIntro(oldGoods.getIntro());
            newGoods.setName(oldGoods.getName());
            newGoods.setPrice(oldGoods.getPrice());
            newGoods.setProperties(oldGoods.getProperties());
            newGoods.setRemain(oldGoods.getRemain());
            newGoods.setSellQty(oldGoods.getSell_qty());
            newGoods.setSpec(oldGoods.getSpec());
            newGoods.setVersion(oldGoods.getVersion());
            String unit = oldGoods.getUnit();
            if(StringUtil.isNotEmpty(unit)){
                newGoods.setUnit(Integer.valueOf(unit));
            }
/*            Integer shopQuantity = bShopCarDao.selectGoodsQtyByGoodsNo(oldGoods.getGoodsNo());
            if(shopQuantity==null){
                shopQuantity = 0;
            }*/
            int remain = oldGoods.getRemain();
            int purchaseQty =  quantity;
            if(remain<purchaseQty){
                result.setStatus(403);
                result.setMsg("购买商品数量不能大于"+remain);
                return result;
            }
            String logUrl = oldGoods.getLogoUrl();
            if(StringUtil.isNotBlank(logUrl)){
                newGoods.setLogoUrl(LogoUrlUtil.splitOneImgUrl(logUrl));
            } else {
                newGoods.setLogoUrl("");
            }
            newGoods.setGoodsQty(quantity);
            FShopToShopCartModel fShopToShopCartModel = bShopInfoDao.selectBShopInfoByBUserId(userId);
            List<FGoodsToShopCarModel> cShopList = new ArrayList<FGoodsToShopCarModel>();
            cShopList.add(newGoods);
            map.put("shopCartGoods", cShopList);
            map.put("allPrice", oldGoods.getPrice()*quantity);
            map.put("allQuantity", quantity);
            if(fShopToShopCartModel!=null){
                map.put("deliveryAddress", fShopToShopCartModel);
            } else {
                map.put("deliveryAddress", new FShopToShopCartModel());
            }
        } else {
            result.setStatus(400);
            result.setMsg("立即购买操作失败");
            return result;
        }
        result.setData(map); 
        result.setStatus(200);
        result.setMsg("立即购买操作成功");
        return result;
    }

    @Override
    public int updateOrderStatus(Map<String, Object> map) {
        if(map==null){
            return 0;
        }
        String orderId=(String) map.get("orderId");
        if(StringUtils.isNotEmpty(orderId)){
            //解除商品锁定内容
            List<FSalesOrderDetailsModel> list=fSalesOrderDetailsDao.queryAllOrderDetailsByOrderId(orderId);
            if(CollectionUtils.isNotEmpty(list)){
                fGoodsDao.updateBatchGoodsQuantity(list);
            }
            
            //更新订单状态
            
            FSalesOrderModel order=fSalesOrderDao.queryOrderById(orderId);
            if(null!=order){
                Map<String, Object> mapExpre=new HashMap<>();
                Integer status=(Integer) map.get("status");
                mapExpre.put("status", null==status?1:status);
                mapExpre.put("orderId", order.getOrderId());
                mapExpre.put("receiverTime", new Date());
                fSalesOrderDao.updateOrderStatus(mapExpre);
            }
            
            return 1;
        }
        return 0;
    }

    @Override
    public Map<String, Object> queryCarCountAndKindByUserId(Integer userId) {
        
        return bShopCarDao.queryCarCountAndKindByUserId(userId);
    }

}