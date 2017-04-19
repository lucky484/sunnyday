package com.f2b.security.action.converter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import com.f2b.security.domain.Channel;
import com.f2b.sugar.tools.DateTimeUtils;
import com.f2b.sugar.web.WebLayerUtil;

/**
 * Created by Administrator on 2016/4/2.
 */
public class ChannelConverter {

    public static JSONObject getJson(List<Channel> channelList, Long totalNum) {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Channel channel : channelList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("channelId", WebLayerUtil.getDefaultByNullLong(channel.getChannelId(), 0L));
            jsonObject.put("entrance", WebLayerUtil.getDefaultByNullString(channel.getEntrance(), ""));
            jsonObject.put("openid", WebLayerUtil.getDefaultByNullString(channel.getOpenid(), ""));
            jsonObject.put("enterTime", WebLayerUtil.getDefaultByNullString(DateTimeUtils.formatDateToString(channel.getEnterTime()), ""));
            jsonArray.add(jsonObject);
        }

        result.put("total", totalNum);
        result.put("rows", jsonArray);

        return result;
    }
}
