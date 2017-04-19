package com.f2b.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: 居泽平  Date: 13-11-24, 下午11:20
 */
public class ExcelImportMap {

	public static Map<Integer, String> KNOWLEDGE_BASE_ENTER_IMPORT_MAP() {

		Map<Integer, String> cellPropertyMap = new HashMap<Integer, String>();
		int i = 0;
		cellPropertyMap.put(i++, "questionId"); // 业务ID
		cellPropertyMap.put(i++, "keywords"); // 关键词
		cellPropertyMap.put(i++, "question"); // 问题
		cellPropertyMap.put(i++, "answer"); // 回复
		return cellPropertyMap;
	}


	public static Map<Integer, String> WX_USER_ENTER_IMPORT_MAP() {

		Map<Integer, String> cellPropertyMap = new HashMap<Integer, String>();
		int i = 0;
		cellPropertyMap.put(i++, "idForPCProcessing"); // ID为PC的处理
		cellPropertyMap.put(i++, "staffName"); // 员工姓名
		cellPropertyMap.put(i++, "surname"); // 姓
		cellPropertyMap.put(i++, "officialName"); // 名
		cellPropertyMap.put(i++, "surnameTitle"); //  姓称呼（意思就是是Ms还是Mr）
		cellPropertyMap.put(i++, "otherName"); // 其他名字
		cellPropertyMap.put(i++, "email"); // 邮箱
		cellPropertyMap.put(i++, "cellphoneNum"); // 手机号
		cellPropertyMap.put(i++, "staffNo"); // 员工ID

		cellPropertyMap.put(i++, "position"); // 地位（项目经理还是其他等等）
		cellPropertyMap.put(i++, "functionalArea"); // 功能区
//        cellPropertyMap.put(i++, "department"); // 功能区

		cellPropertyMap.put(i + 2, "region"); // 区域
		cellPropertyMap.put(i++, "workingLocation"); // 工作地点
		cellPropertyMap.put(i++, "employeeStatus"); // 员工状态
		cellPropertyMap.put(i++, "employeeGroup"); // 员工组
		cellPropertyMap.put(i++, "userName"); // 用户名
		cellPropertyMap.put(i++, "deptId"); // 部门ID

		return cellPropertyMap;
	}

}
