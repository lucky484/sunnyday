package com.hd.client.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.client.model.IdCardInfo;
import com.hd.client.service.IDInfoService;
import com.hd.client.util.IDCardUtil;

/**
 * 
 * @author cliff.fan
 *
 */
@Controller
@RequestMapping("/IdCardInfo")
public class IdCardInfoController {

	@Autowired
	private IDInfoService idCardInfoService;
	@Autowired
	private IDCardUtil util;

	@RequestMapping(value = "getIdCardInfoList")
	@ResponseBody
	public List<IdCardInfo> get(HttpServletRequest request) {
		List<IdCardInfo> list = idCardInfoService.getIdCardInfoList();
		return list;
	}

	@RequestMapping(value = "close")
	@ResponseBody
	public void close(HttpServletRequest request) {
		util.closeServiceReader();
	}

	@RequestMapping(value = "change")
	@ResponseBody
	public void change(HttpServletRequest request) {
		if (IDCardUtil.IDCARD_READ_BUTTON_STATE.equals("1")) {
			IDCardUtil.IDCARD_READ_BUTTON_STATE = "2";
		} else {
			IDCardUtil.IDCARD_READ_BUTTON_STATE = "1";
		}
	}

	@RequestMapping(value = "open")
	@ResponseBody
	public void open(HttpServletRequest request) {
		System.out.println("open");
		util.openServiceReader();
	}

	@RequestMapping(value = "getIdCardInfo")
	@ResponseBody
	public IdCardInfo get1(HttpServletRequest request) {
		IdCardInfo idcardinfo = idCardInfoService.getIdCardInfo();
		return idcardinfo;
	}

	@RequestMapping(value = "updateIDInfoDao")
	public void updateFaceInfo(String cardId, String relayFlag) {
		idCardInfoService.updateIDInfoDao(cardId, relayFlag);
	}
}
