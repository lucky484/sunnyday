package com.f2b.security.action.converter;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import com.f2b.security.domain.Award;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.web.WebLayerUtil;


/**
 * Created by Administrator on 2016/3/24.
 */
public class AwardConverter {

    public static JSONObject getJson(List<Award> awardList, Long totalNum) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Award award : awardList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("awardId", WebLayerUtil.getDefaultByNullLong(award.getAwardId(), 0L));
            jsonObject.put("awards", WebLayerUtil.getDefaultByNullString(award.getAwards(), ""));
            jsonObject.put("number", WebLayerUtil.getDefaultByNullInteger(award.getNumber(), 0));
            jsonObject.put("createDate", WebLayerUtil.getDefaultByNullString(DateTimeUtils.formatDateToString(award.getCreateDate()), ""));
            jsonObject.put("updateDate", WebLayerUtil.getDefaultByNullString(DateTimeUtils.formatDateToString(award.getUpdateDate()), ""));
            jsonArray.add(jsonObject);
        }

        result.put("total", totalNum);
        result.put("rows", jsonArray);

        return result;
    }
}
