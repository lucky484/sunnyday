package com.hd.client.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hd.client.model.IdCardInfo;
import com.hd.client.service.IDcardWriterService;
import com.hd.client.service.IdCardReadTaskService;
import com.hd.client.util.IDCardUtil;
import com.hd.client.util.clibrary.CLibrary;
import com.hd.client.util.clibrary.SLibrary;
import com.sun.jna.Memory;

/**
 * 
 * @ClassName: IdCardReadTaskImpl
 * @Description: 这是一个身份证读卡器读取卡片信息的类
 * @author: ligang.yang
 * @date: 2016年1月9日 下午5:04:35
 */
@Service
public class IdCardReadTaskImpl implements IdCardReadTaskService {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(IdCardReadTaskImpl.class);

	/**
	 * 暂时存放图片地址
	 */
	@Value("${bmpPath_path_suffer}")
	private String BMPPATH_PATH_SUFFER;

	@Autowired
	private IDcardWriterService writer;

	public static int OK = -1;

	public static boolean SERVICE_STATE_OFF = false;

	private final Memory pBmpDate = new Memory(77725);
	private final Memory pName = new Memory(100);
	private final Memory pSex = new Memory(100);
	private final Memory pNation = new Memory(100);
	private final Memory pBirth = new Memory(100);
	private final Memory pAddress = new Memory(100);
	private final Memory pCertNo = new Memory(100);
	private final Memory pDepartment = new Memory(100);
	private final Memory pEffectData = new Memory(100);
	private final Memory pExpire = new Memory(100);

	/**
	 * 这是一个身份证设备定时将数据采集下来的方法
	 */
	public void run() {
		if (SERVICE_STATE_OFF)
			return;
		try {
			if (IDCardUtil.IDCARD_READ_BUTTON_STATE.equals(IDCardUtil.IDCARD_READ_BUTTON_MODE1)) {
				OK = CLibrary.getInstance.HD_InitComm(IDCardUtil.IDCARDREADER_PORT);
				// 关闭连接
				logger.info("IdCardReaderInitCode: " + OK);
				// 初始化身份证读卡器
				// 当验证不通过的话需要阻塞300ms重新进行安全验证
				Thread.sleep(280);
				// 是否通过安全验证
				if (OK == 0// 默认端口号为1001
						&& CLibrary.getInstance.HD_Authenticate() == 0) {
					// 安全通过后将数据采集下来
					String pBmpFile = BMPPATH_PATH_SUFFER + String.valueOf(System.currentTimeMillis());
					// 读取身份证信息到IDCard
					CLibrary.getInstance.HD_Read_BaseInfo(pBmpFile, pBmpDate, pName, pSex, pNation, pBirth, pAddress,
							pCertNo, pDepartment, pEffectData, pExpire);
					String nowTime = System.currentTimeMillis() + "";
					// 将采集的信息存放到bean中
					IdCardInfo card = new IdCardInfo(/* 自增长序列 */null, /* 编号（设备id_时间戳_顺番） */
							/* k.getString(0).trim()+ */"_" + nowTime, /* 采集时间时间戳 */nowTime, /* 采集地点 */"采集地点自己设?",
							/* 身份证照片数组 */null, /* 姓名 */pName.getString(0).trim(),
							/* 身份证号码 */pCertNo.getString(0).trim(), /* 性别 */pSex.getString(0).trim(),
							/* 生日 */pBirth.getString(0).trim(), /* 民族 */pNation.getString(0).trim(),
							/* 地址 */pAddress.getString(0).trim(), /* 发证单位 */pDepartment.getString(0).trim(),
							/* 发证日 */pEffectData.getString(0).trim(), /* 截至日 */pExpire.getString(0).trim(),
							/* 身份证16位ID */"16为id先不管", /* 转发时间戳 */null, /* 转发标记（0-未发送；1-发送中；2-发送成功；-1-发送失败） */"0",
							/* 备注 */"备注", /* 删除状态(0-未删除；1-已删除) */"0", /* 创建人 */"system", /* 创建时间 */nowTime,
							/* 更新人 */null, /* 更新时间 */null, pBmpFile, pBmpDate.getString(0).trim());
					cleanMemory();
					System.out.println(card);
					writer.writeOut2DB(card);
					CLibrary.getInstance.HD_CloseComm();
				}

			} else if (IDCardUtil.IDCARD_READ_BUTTON_STATE.equals(IDCardUtil.IDCARD_READ_BUTTON_MODE2)) {
				int readerHandle = -1;
				readerHandle = SLibrary.getInstance.ICC_Reader_Open("USB1");
				byte[] getdata = new byte[9];

				int tempCode = -1;
				tempCode = SLibrary.getInstance.PICC_Reader_ID_Person(readerHandle, getdata);
				if (tempCode > 0) {
					writer.write16IdMsg(getdata);
					// todo 循环调用sql写入数据库
				} else {
					logger.error("IDCardReader state is error!");
				}
				SLibrary.getInstance.ICC_Reader_Close(readerHandle);
			}
		} catch (Exception e) {
			logger.error("IDCardReader state is error!");
			logger.error(e);
		}
	}

	/**
	 * @Description: 清空内存
	 * @return: void
	 */
	private void cleanMemory() {
		pBmpDate.clear();
		pName.clear();
		pSex.clear();
		pNation.clear();
		pBirth.clear();
		pAddress.clear();
		pCertNo.clear();
		pDepartment.clear();
		pEffectData.clear();
		pExpire.clear();
	}

}
