package com.f2b.security.action.converter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import com.f2b.security.domain.AdminUser;
import com.f2b.sugar.tools.CommonSugar;
import com.f2b.sugar.tools.DateTimeUtils;

/**
 * Author: val.jzp
 */
public class AdminUserConverter {

	public static JSONObject getJson(List<AdminUser> adminUserList, Long totalNum) {
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (AdminUser adminUser : adminUserList) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("adminUserId", CommonSugar.getTypedDefault(adminUser.getAdminUserId(), 0L));
			jsonObject.put("loginName", CommonSugar.getTypedDefault(adminUser.getLoginName(), ""));
			jsonObject.put("loginPassword", CommonSugar.getTypedDefault(adminUser.getLoginPassword(), ""));
			jsonObject.put("realName", CommonSugar.getTypedDefault(adminUser.getRealName(), ""));
			jsonObject.put("email", CommonSugar.getTypedDefault(adminUser.getEmail(), ""));
			jsonObject.put("createDate", CommonSugar.getTypedDefault(DateTimeUtils.formatDateToString(adminUser.getCreateDate()), ""));
			jsonObject.put("updateDate", CommonSugar.getTypedDefault(DateTimeUtils.formatDateToString(adminUser.getUpdateDate()), ""));

			jsonArray.add(jsonObject);
		}

		result.put("total", totalNum);
		result.put("rows", jsonArray);

		return result;
	}
}