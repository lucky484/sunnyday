package com.f2b.security.action.converter;

import java.util.List;

import com.f2b.security.domain.ShareRecord;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.web.WebLayerUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShareRecordConverter {

	public static JSONObject getJson(List<ShareRecord> recordList, Long totalNum) {
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (ShareRecord record : recordList) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("shareRecordId", WebLayerUtil.getDefaultByNullLong(record.getShareRecordId(), 0L));
			jsonObject.put("createDate", WebLayerUtil.getDefaultByNullString(DateTimeUtils.formatDateToStringWithTime(record.getCreateDate()), ""));
			jsonObject.put("shareOpenId", WebLayerUtil.getDefaultByNullString(record.getShareOpenId(), ""));
			jsonObject.put("shareNickName", WebLayerUtil.getDefaultByNullString(Base64.getFromBase64(record.getShareNickName()), ""));
			jsonObject.put("openId", WebLayerUtil.getDefaultByNullString(record.getOpenId(), ""));
			jsonObject.put("nickname", WebLayerUtil.getDefaultByNullString(Base64.getFromBase64(record.getNickname()), ""));
			jsonObject.put("sku", WebLayerUtil.getDefaultByNullString(record.getSku(), ""));
			jsonObject.put("number", WebLayerUtil.getDefaultByNullInteger(record.getNumber(), 0));
			jsonObject.put("sendRedPack", WebLayerUtil.getDefaultByNullInteger(record.getSendRedPack(), 0));

			jsonArray.add(jsonObject);
		}
		result.put("total", totalNum);
		result.put("rows", jsonArray);
		return result;
	}
}
