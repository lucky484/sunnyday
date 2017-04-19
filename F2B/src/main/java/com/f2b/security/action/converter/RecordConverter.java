package com.f2b.security.action.converter;

import java.util.List;

import com.f2b.security.domain.Record;
import com.f2b.sugar.tools.Base64;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.web.WebLayerUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/3/24.
 */
public class RecordConverter {

	public static JSONObject getJson(List<Record> recordList, Long totalNum) {
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (Record record : recordList) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("recordId", WebLayerUtil.getDefaultByNullLong(record.getRecordId(), 0L));
			jsonObject.put("lotteryDate", WebLayerUtil.getDefaultByNullString(DateTimeUtils.formatDateToStringWithTime(record.getLotteryDate()), ""));
			jsonObject.put("award", WebLayerUtil.getDefaultByNullString(record.getAward(), ""));
			jsonObject.put("openId", WebLayerUtil.getDefaultByNullString(record.getOpenId(), ""));
			jsonObject.put("nickname", WebLayerUtil.getDefaultByNullString(Base64.getFromBase64(record.getNickname()), ""));
			jsonObject.put("ip", WebLayerUtil.getDefaultByNullString(record.getIp(), ""));
			jsonObject.put("drawStatus", WebLayerUtil.getDefaultByNullInteger(record.getDrawStatus(), 0));
			jsonObject.put("orderNo", WebLayerUtil.getDefaultByNullString(record.getOrderNo(), ""));

			jsonArray.add(jsonObject);
		}
		result.put("total", totalNum);
		result.put("rows", jsonArray);
		return result;
	}
}
