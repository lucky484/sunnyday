package com.hd.client.service.impl;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hd.client.model.IdCardInfo;
import com.hd.client.model.ParamSet;
import com.hd.client.service.IDInfoService;
import com.hd.client.service.IDcardWriterService;
import com.hd.client.service.ParamSetService;

/**
 * 
 * @ClassName: IDcardWriterImpl
 * @Description: 这是一个定时将身份证信息写入数据库的类
 * @author: ligang.yang
 * @date: 2016年1月9日 下午5:01:41
 */
@Service
public class IDcardWriterImpl implements IDcardWriterService {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IDcardWriterImpl.class);

	/**
	 * 身份证基础信息
	 */
	@Autowired
	private ParamSetService paramInfo;

	/**
	 * 暂时存放图片地址
	 */
	@Value("${bmpPath_path_suffer}")
	private String BMPPATH_PATH_SUFFER;

	private final Base64 encoder = new Base64();

	/**
	 * 注入数据库操作的类
	 */
	@Autowired
	private IDInfoService service;

	public void writeOut2DB(IdCardInfo card) {
		try {
			byte[] imgdata = encoder.decode(card.getBmpData());
			if (imgdata == null || imgdata.length == 0) {
				// 处理为采集到的数据
				return;
			}
			card.setIdCardPic(imgdata);
			this.setBaseInfo(card);
			service.save(card);
			deletefile();
		} catch (Exception e) {
			logger.error("read idcard info error...");
		}
		logger.debug("---------------------此处将数据写入数据库----------------------");
	}

	public boolean deletefile() {
		String path = BMPPATH_PATH_SUFFER;
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("文件夹不存在" + BMPPATH_PATH_SUFFER);
		}
		if (!file.isDirectory()) {
			System.out.println("不是文件夹" + BMPPATH_PATH_SUFFER);
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
		}
		return flag;
	}

	public String write16IdMsg(byte[] msg) {
		System.out.println(byte2HexStr(msg));
		return byte2HexStr(msg);
	}

	private void setBaseInfo(IdCardInfo card) {
		ParamSet param = null;
		try {
			param = paramInfo.getParamSet();
		} catch (Exception e) {
			logger.error(e);
		}
		if (param == null)
			return;
		card.setCardCode(param.getIdCardDvNo() + card.getCardCode());
		card.setCollectSite(param.getCollectSite());
		card.setRemarkDesc(param.getRemarkDesc());
	}

	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}
}
