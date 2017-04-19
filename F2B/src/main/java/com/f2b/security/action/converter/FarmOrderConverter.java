package com.f2b.security.action.converter;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.f2b.security.domain.FarmOrder;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.web.WebLayerUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author mozzie.chu
 *
 */
public class FarmOrderConverter {

	public static JSONObject getJson(List<FarmOrder> farmOrderList, Long totalNum) {
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		for (FarmOrder farmOrder : farmOrderList) {
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("orderId", WebLayerUtil.getDefaultByNullLong(farmOrder.getOrderId(), 0L));
			jsonObject.put("produceName", WebLayerUtil.getDefaultByNullString(farmOrder.getProduceName(), ""));//商品名
			jsonObject.put("sku", WebLayerUtil.getDefaultByNullString(farmOrder.getSku(), ""));//订单号
			jsonObject.put("merchant", WebLayerUtil.getDefaultByNullString(farmOrder.getMerchant(), ""));//买家-姓名
			jsonObject.put("phone", WebLayerUtil.getDefaultByNullString(farmOrder.getPhone(), ""));//联系方式
			jsonObject.put("total", WebLayerUtil.getDefaultByNullString(farmOrder.getTotal().toString(),""));//总价
			jsonObject.put("weight", WebLayerUtil.getDefaultByNullString(farmOrder.getWeight(), ""));//数量
			jsonObject.put("address", WebLayerUtil.getDefaultByNullString(farmOrder.getAddress(), ""));//地址
			jsonObject.put("freight", WebLayerUtil.getDefaultByNullString(farmOrder.getFreight().toString(), ""));//运费
			jsonObject.put("unitPrice", WebLayerUtil.getDefaultByNullString(farmOrder.getUnitPrice().toString(), ""));//单价
			jsonObject.put("createDate", WebLayerUtil.getDefaultByNullString(DateTimeUtils.formatDateToStringWithTime(farmOrder.getCreateDate()), ""));//创建时间
			if(farmOrder.getStatus() == 0) {
				jsonObject.put("status", WebLayerUtil.getDefaultByNullString("已发货",""));				
			}else if(farmOrder.getStatus() == -1){
				jsonObject.put("status", WebLayerUtil.getDefaultByNullString("未发货",""));				
			} else if(farmOrder.getStatus() == -2) {
				jsonObject.put("status", WebLayerUtil.getDefaultByNullString("未付款",""));				
			}
			jsonObject.put("nickname", WebLayerUtil.getDefaultByNullString(Base64.getFromBase64(farmOrder.getNickname()), ""));//昵称
			jsonObject.put("shareOpenId", WebLayerUtil.getDefaultByNullString(farmOrder.getShareOpenId(), ""));//昵称
			jsonObject.put("shareNickname", WebLayerUtil.getDefaultByNullString(Base64.getFromBase64(farmOrder.getShareNickname()), ""));//昵称
			jsonObject.put("openId", WebLayerUtil.getDefaultByNullString(farmOrder.getOpenId(), ""));//微信号
			jsonObject.put("comments", WebLayerUtil.getDefaultByNullString(farmOrder.getComments(), ""));//备注
//			jsonObject.put("wholesaler", WebLayerUtil.getDefaultByNullString(farmOrder.getWholesaler(), ""));//卖家
			
			jsonArray.add(jsonObject);
		}
		
		result.put("total", totalNum);
		result.put("rows", jsonArray);
		return result;
	}
}
