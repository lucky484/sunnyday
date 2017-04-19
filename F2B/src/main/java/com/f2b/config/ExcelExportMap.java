package com.f2b.config;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: 居泽平  Date: 13-11-24, 下午11:20
 */
public class ExcelExportMap {

	public static Map<String, String> CUSTOMER_EXPORT_MAP(String selectFields) {

		Map<String, String> columnPropertyMap = new LinkedHashMap<String, String>();
		columnPropertyMap.put("account", "一米帐号");
		columnPropertyMap.put("mobile", "手机号码");
		columnPropertyMap.put("reference", "推荐人");
		columnPropertyMap.put("lng", "gps经度");
		columnPropertyMap.put("lat", "gps纬度");
		columnPropertyMap.put("status", "用户状态");
		columnPropertyMap.put("create_time", "注册日期");

		columnPropertyMap.put("user_name", "用户姓名");
		columnPropertyMap.put("sex", "用户性别");
		columnPropertyMap.put("birthday", "出生日期");
		columnPropertyMap.put("sfz_id", "身份证号");
		columnPropertyMap.put("home_province", "家乡省份代码");
		columnPropertyMap.put("home_province_detail", "家乡省份");
		columnPropertyMap.put("home_city", "家乡城市代码");
		columnPropertyMap.put("home_city_detail", "家乡城市");
		columnPropertyMap.put("home_district", "家乡区县代码");
		columnPropertyMap.put("home_district_detail", "家乡区县");
		columnPropertyMap.put("live_province", "居住省份代码");
		columnPropertyMap.put("live_province_detail", "居住省份");
		columnPropertyMap.put("live_city", "居住城市代码");
		columnPropertyMap.put("live_city_detail", "居住城市");
		columnPropertyMap.put("live_district", "居住区县代码");
		columnPropertyMap.put("live_district_detail", "居住区县");
		columnPropertyMap.put("address", "详细地址");
		columnPropertyMap.put("email", "电子邮箱");
		columnPropertyMap.put("weibo", "微博");
		columnPropertyMap.put("qq", "QQ");
		columnPropertyMap.put("is_married", "婚姻状况");
		columnPropertyMap.put("job_status", "求职状态");
		columnPropertyMap.put("job_status_detail", "求职状态");
		columnPropertyMap.put("self_evaluation", "自我评价");
		columnPropertyMap.put("work_years", "工作经历年数");
		columnPropertyMap.put("resume_status", "简历状态");
		columnPropertyMap.put("allow_chat", "允许企业聊天");
		columnPropertyMap.put("allow_mobile", "允许手机号查找");
		columnPropertyMap.put("my_qiye", "企业ID");
		columnPropertyMap.put("nick_name", "用户昵称");
		columnPropertyMap.put("signature", "个性签名");
		columnPropertyMap.put("update_time", "更新时间");

		columnPropertyMap.put("degree", "学历");
		columnPropertyMap.put("school", "学校名称");
		columnPropertyMap.put("profession", "专业名称");
		columnPropertyMap.put("starttime", "开始时间");
		columnPropertyMap.put("endtime", "结束时间");

		columnPropertyMap.put("area", "意向城市");
		columnPropertyMap.put("area_detail", "意向城市");
		columnPropertyMap.put("trade", "意向行业");
		columnPropertyMap.put("trade_detail", "意向行业");
		columnPropertyMap.put("position", "意向岗位");
		columnPropertyMap.put("position_detail", "意向岗位");
		columnPropertyMap.put("salary", "期望薪资");
		//columnPropertyMap.put("status", "当前状态");

		Map<String, String> columnPropertySelectMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(selectFields)) {
			String[] fields = selectFields.split(",");
			for (String field : fields) {
				columnPropertySelectMap.put(field, columnPropertyMap.get(field));
			}
		}

		return columnPropertySelectMap;
	}


	public static Map<String, String> CUSTOMER_APPLY_EXPORT_MAP(String selectFields) {
		Map<String, String> columnPropertyMap = new LinkedHashMap<String, String>();
		columnPropertyMap.put("apply_id", "申请id");
		columnPropertyMap.put("user_name", "用户名");
		columnPropertyMap.put("sex_detail", "性别");
		columnPropertyMap.put("mobile", "手机号码");
		columnPropertyMap.put("job_name", "申请职位");
		columnPropertyMap.put("qiye_name", "申请公司");
		columnPropertyMap.put("address", "公司地区");
		columnPropertyMap.put("add_time", "申请日期");
		columnPropertyMap.put("status_name", "申请状态");

		Map<String, String> columnPropertySelectMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(selectFields)) {
			String[] fields = selectFields.split(",");
			for (String field : fields) {
				columnPropertySelectMap.put(field, columnPropertyMap.get(field));
			}
		}

		return columnPropertySelectMap;

	}

	public static Map<String, String> COMPANY_EXPORT_MAP(String selectFields) {

		Map<String, String> columnPropertyMap = new LinkedHashMap<String, String>();
		columnPropertyMap.put("account", "登录帐号");
		columnPropertyMap.put("qiye_name", "企业名称");
		columnPropertyMap.put("nick_name", "企业昵称");
		columnPropertyMap.put("signature", "个性签名");
		columnPropertyMap.put("description", "企业介绍");
		columnPropertyMap.put("company_type_detail", "企业类型");
		columnPropertyMap.put("trade_detail", "行业类别");
		columnPropertyMap.put("company_size_detail", "公司规模");
		columnPropertyMap.put("province_name", "所在省份");
		columnPropertyMap.put("city_name", "所在城市");
		columnPropertyMap.put("district_name", "所在县区");
		columnPropertyMap.put("address", "详细地址");
		columnPropertyMap.put("contact", "联系人");
		columnPropertyMap.put("telephone", "联络电话");
		columnPropertyMap.put("lng", "gps经度");
		columnPropertyMap.put("lat", "gps纬度");
		columnPropertyMap.put("company_level", "公司评级");
		columnPropertyMap.put("evaluate", "概要评价");
		columnPropertyMap.put("email", "邮件地址");
		columnPropertyMap.put("weibo", "企业微博");
		columnPropertyMap.put("qq", "联系QQ号");
		columnPropertyMap.put("status", "企业状态");
		columnPropertyMap.put("creater", "创建人");
		columnPropertyMap.put("video_flag", "是否需要视频");
		columnPropertyMap.put("review_time", "审核通过时间");
		columnPropertyMap.put("create_time", "创建时间");
		columnPropertyMap.put("update_time", "更新时间");

		Map<String, String> columnPropertySelectMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(selectFields)) {
			String[] fields = selectFields.split(",");
			for (String field : fields) {
				columnPropertySelectMap.put(field, columnPropertyMap.get(field));
			}
		}

		return columnPropertySelectMap;
	}

	public static Map<String, String> UNKNOWN_QUESTION_EXPORT_MAP() {
		Map<String, String> columnPropertyMap = new LinkedHashMap<String, String>();
		columnPropertyMap.put("question", " 问题内容");
		columnPropertyMap.put("createDate", "插入时间");
		columnPropertyMap.put("status", "是否被回答");

		return columnPropertyMap;

	}
}
